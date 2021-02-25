package com.cratediggers.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cratediggers.controller.frontend.shoppingcart.ShoppingCart;
import com.cratediggers.dao.OrderDAO;
import com.cratediggers.entity.Album;
import com.cratediggers.entity.AlbumOrder;
import com.cratediggers.entity.Customer;
import com.cratediggers.entity.OrderDetail;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;

/**
 * Handles all business logic for the order module.
 * Follows model-view-controller design architecture by ensuring that the model and view never interact directly.
 */
public class OrderServices {
	/**
	 * OrderDAO object used to interact with the database (the model).
	 */
	private OrderDAO orderDao;
	/**
	 * Contains parameters received by a HTTP request.
	 */
	private HttpServletRequest request;
	/**
	 * Used to store attributes that are utilised for JSP generation.
	 */
	private HttpServletResponse response;

	/**
	 * Constructs an OrderServices object containing the request and response objects provided by a servlet.
	 * Constructs a new orderDAO object used to interact with the database (the model).
	 * @param request object that contains parameters received in HTTP request made to the server.
	 * @param response object that is used to store attributes which are utilised for JSP generation.
	 */
	public OrderServices(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.orderDao = new OrderDAO();
	}

	/**
	 * Provides polymorphism so that listAllOrder(String message) can be called without a specified message.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void listAllOrder() throws ServletException, IOException {
		listAllOrder(null);
	}
	
	/**
	 * Retrieves a list of all orders from database.
	 * Forwards the list and a specified message onto "order_list.jsp" to display to users.
	 * @param message to be displayed at top of JSP page
	 * @throws ServletException
	 * @throws IOException
	 */
	public void listAllOrder(String message) throws ServletException, IOException {
		List<AlbumOrder> listOrder = orderDao.listAll();
		
		if (message != null) {
			request.setAttribute("message", message);
		}
		
		request.setAttribute("listOrder", listOrder);
		
		String listPage = "order_list.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(listPage);
		dispatcher.forward(request, response);
		
	}

	/**
	 * Retrieves orderId from request object and gets corresponding albumOrder object from database.
	 * Sets the albumOrder order as a request object attribute to be used for generation of the "order_detail.jsp" page.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void viewOrderDetailForAdmin() throws ServletException, IOException {
		int orderId = Integer.parseInt(request.getParameter("id"));
		
		AlbumOrder order = orderDao.get(orderId);
		request.setAttribute("order", order);
		
		String detailPage = "order_detail.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(detailPage);
		dispatcher.forward(request, response);		
	}

	/**
	 * Retrieves the shoppingCart object stored in session.
	 * Calculates the tax, shippingFee and total costs of an order and stores them in session.
	 * These are used to generate the "checkout.jsp" page which is displayed to the customer.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void showCheckoutForm() throws ServletException, IOException {
		HttpSession session = request.getSession();
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("cart");
		
		// tax is 10% of subtotal
		final float tax = shoppingCart.getTotalAmount() * 0.1f;
		
		// shipping fee is 1.0 GBP per copy
		final float shippingFee = shoppingCart.getTotalQuantity() * 1.0f;
		
		float total = shoppingCart.getTotalAmount() + tax + shippingFee;
		
		session.setAttribute("tax", tax);
		session.setAttribute("shippingFee", shippingFee);
		session.setAttribute("total", total);
		
		//CommonUtility.generateCountryList(request);
		
		String checkOutPage = "frontend/checkout.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(checkOutPage);
		dispatcher.forward(request, response);		
	}

	/**
	 * Retrieves all parameters from the request object and constructs a new albumOrder object.
	 * If paymentMethod is equal to "paypal" then constructs a paymentServices object and attempts to authorise the payment.
	 * In this case, the albumOrder is stored in session.
	 * Otherwise, places a cash-on-delivery order that takes the albumOrder object as input.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void placeOrder() throws ServletException, IOException {
		String paymentMethod = request.getParameter("paymentMethod");
		AlbumOrder order = readOrderInfo();
		
		if (paymentMethod.equals("paypal")) {
			PaymentServices paymentServices = new PaymentServices(request, response);
			request.getSession().setAttribute("order4Paypal", order);
			paymentServices.authorizePayment(order);
		} else {	// Cash on Delivery
			placeOrderCOD(order);
		}			
	}
	
	/**
	 * Stores the details of a PayPal payment within the database once successfully executed by the REST API.
	 * Receives a payment object which is converted to an AlbumOrder object which can be stored within the database.
	 * Once the record is created in the database then the id of the order object is returned.
	 * @param payment to be converted into an AlbumOrder object and stored
	 * @return The stored AlbumOrder object's id
	 */
	public Integer placeOrderPaypal(Payment payment) {
		AlbumOrder order = (AlbumOrder) request.getSession().getAttribute("order4Paypal");
		ItemList itemList = payment.getTransactions().get(0).getItemList();
		ShippingAddress shippingAddress = itemList.getShippingAddress();
		String shippingPhoneNumber = itemList.getShippingPhoneNumber();
		
		String recipientName = shippingAddress.getRecipientName();
		String[] names = recipientName.split(" ");
		
		order.setFirstName(names[0]);
		order.setLastName(names[1]);
		order.setAddressLine1(shippingAddress.getLine1());
		order.setAddressLine2(shippingAddress.getLine2());
		order.setTown(shippingAddress.getCity());
		order.setCounty(shippingAddress.getState());
		//order.setCountry(shippingAddress.getCountryCode());
		order.setPhone(shippingPhoneNumber);
		
		return saveOrder(order);
		
	}
	
	/**
	 * Stores a provided albumOrder in the database (the model).
	 * Clears the shipping cart stored in session.
	 * @param order to be stored
	 * @return The Id of the created order record.
	 */
	private Integer saveOrder(AlbumOrder order) {
		AlbumOrder savedOrder = orderDao.create(order);
		
		ShoppingCart shoppingCart = (ShoppingCart) request.getSession().getAttribute("cart");
		shoppingCart.clear();	
		
		return savedOrder.getOrderId();
	}
	

	/**
	 * Reads all fields of an albumOrder form stored as parameters in request object.
	 * Constructs a new albumOrder object and sets the fields equal to the specified parameters.
	 * Acquires customer object from the logged customer stored in session.
	 * Iterates through each item in shopping cart and constructs a new orderDetails object for each, setting their fields and calculating subtotals according to quantity.
	 * @return AlbumOrder object based on form fields
	 */
	private AlbumOrder readOrderInfo() {
		String paymentMethod = request.getParameter("paymentMethod");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String phone = request.getParameter("phone");
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		String town = request.getParameter("town");
		String county = request.getParameter("county");
		String postcode = request.getParameter("postcode");
		//String country = request.getParameter("country");		
		
		AlbumOrder order = new AlbumOrder();
		order.setFirstName(firstname);
		order.setLastName(lastname);
		order.setPhone(phone);
		order.setAddressLine1(address1);
		order.setAddressLine2(address2);
		order.setTown(town);
		order.setCounty(county);
		//order.setCountry(country);
		order.setPostcode(postcode);
		order.setPaymentMethod(paymentMethod);
		
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loggedCustomer");
		order.setCustomer(customer);
		
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("cart");
		Map<Album, Integer> items = shoppingCart.getItems();
		
		Iterator<Album> iterator = items.keySet().iterator();
		
		Set<OrderDetail> orderDetails = new HashSet<>();
		
		while (iterator.hasNext()) {
			Album album = iterator.next();
			Integer quantity = items.get(album);
			float subtotal = quantity * album.getPrice();
			
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setAlbum(album);
			orderDetail.setAlbumOrder(order);
			orderDetail.setQuantity(quantity);
			orderDetail.setSubtotal(subtotal);
			
			orderDetails.add(orderDetail);
		}
		
		order.setOrderDetails(orderDetails);
		
		float tax = (Float) session.getAttribute("tax");
		float shippingFee = (Float) session.getAttribute("shippingFee");
		float total = (Float) session.getAttribute("total");
		
		order.setSubtotal(shoppingCart.getTotalAmount());
		order.setTax(tax);
		order.setShippingFee(shippingFee);
		order.setTotal(total);		
		
		return order;
	}
	
	/**
	 * Places a cash-on-delivery type order by storing the order in the database (the model).
	 * Displays confirmation message to customer.
	 * @param order to be placed
	 * @throws ServletException
	 * @throws IOException
	 */
	private void placeOrderCOD(AlbumOrder order) throws ServletException, IOException {
		int orderId = saveOrder(order);

		HttpSession session = request.getSession();
		session.setAttribute("orderId", orderId);
		
		PaymentServices paymentServices = new PaymentServices(request, response);
		PayerInfo payerInfo = paymentServices.getPayerInformation(order).getPayerInfo();
		Transaction transactions = paymentServices.getTransactionInformation(order).get(0);
		
		session.setAttribute("payer", payerInfo);
		session.setAttribute("transaction", transactions);
		
		String receiptPage = "frontend/payment_receipt.jsp";
		request.getRequestDispatcher(receiptPage).forward(request, response);
	}

	/**
	 * Obtains the genreId provided as a session attribute.
	 * Searches the database (the model) for a list of all albumOrders containing the specified customerId as a foreign key.
	 * Sets this list as an attribute of the request object.
	 * This is used to generate the "order_list.jsp" page for a given customer.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void listOrderByCustomer() throws ServletException, IOException {
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loggedCustomer");
		List<AlbumOrder> listOrders = orderDao.listByCustomer(customer.getCustomerId());
		
		request.setAttribute("listOrders", listOrders);
		
		String historyPage = "frontend/order_list.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(historyPage);
		dispatcher.forward(request, response);		
	}

	/**
	 * Obtains id provided as a parameter in the request object and customerId of the logged in customer from session.
	 * Searches the database (the model) for an album containing the specified id and customerId as a foreign key.
	 * Sets the retrieved albumOrder object as an attribute of the request object.
	 * This is used to generate the "order_detail.jsp" page for a given albumOrder.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void showOrderDetailForCustomer() throws ServletException, IOException {
		int orderId = Integer.parseInt(request.getParameter("id"));
		
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loggedCustomer");
		
		AlbumOrder order = orderDao.get(orderId, customer.getCustomerId());
		request.setAttribute("order", order);
		
		String detailPage = "frontend/order_detail.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(detailPage);
		dispatcher.forward(request, response);			
	}

	/**
	 * Obtains albumOrder object that corresponds to the id provided as a parameter in the request object.
	 * If album does not exist then displays error message.
	 * If album does exist then display "order_form.jsp" in the view, setting the albumOrder object as an attribute in the response object
	 *  so that pre-existing albumOrder object information can auto-fill in form fields.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void showEditOrderForm() throws ServletException, IOException {
		Integer orderId = Integer.parseInt(request.getParameter("id"));
		if (orderDao.get(orderId) != null) {
			HttpSession session = request.getSession();
			Object isPendingAlbum = session.getAttribute("NewAlbumPendingToAddToOrder");
			
			if (isPendingAlbum == null) {
				AlbumOrder order = orderDao.get(orderId);
				session.setAttribute("order", order);
			} else {
				session.removeAttribute("NewAlbumPendingToAddToOrder");
			}
			
			//CommonUtility.generateCountryList(request);
			
			String editPage = "order_form.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(editPage);
			dispatcher.forward(request, response);	
		}
		else {
			String message = "Another user has already deleted order with ID " + orderId + ".";
			listAllOrder(message);
		}
	}

	/**
	 * Retrieves all form fields stored as parameters in the request object.
	 * Obtains the AlbumOrder object stored in session and sets all fields according to the specified parameters.
	 * Updates the corresponding record in the database (the model).
	 * Displays confirmation message to user in the view.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void updateOrder() throws ServletException, IOException {
		HttpSession session = request.getSession();
		AlbumOrder order = (AlbumOrder) session.getAttribute("order");
		
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String phone = request.getParameter("phone");
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		String town = request.getParameter("town");
		String county = request.getParameter("county");
		String postcode = request.getParameter("postcode");
		//String country = request.getParameter("country");
		
		float shippingFee = Float.parseFloat(request.getParameter("shippingFee"));
		float tax = Float.parseFloat(request.getParameter("tax"));
		
		String paymentMethod = request.getParameter("paymentMethod");
		String orderStatus = request.getParameter("orderStatus");
		
		order.setFirstName(firstname);
		order.setLastName(lastname);
		order.setPhone(phone);
		order.setAddressLine1(address1);
		order.setAddressLine2(address2);
		order.setTown(town);
		order.setCounty(county);
		order.setPostcode(postcode);
		//order.setCountry(country);
		order.setShippingFee(shippingFee);
		order.setTax(tax);
		order.setPaymentMethod(paymentMethod);
		order.setStatus(orderStatus);
		
		String[] arrayAlbumId = request.getParameterValues("albumId");
		String[] arrayPrice = request.getParameterValues("price");
		String[] arrayQuantity = new String[arrayAlbumId.length];
		
		for (int i = 1; i <= arrayQuantity.length; i++) {
			arrayQuantity[i - 1] = request.getParameter("quantity" + i);
		}
		
		Set<OrderDetail> orderDetails = order.getOrderDetails();
		orderDetails.clear();
		
		float totalAmount = 0.0f;
		
		for (int i = 0; i < arrayAlbumId.length; i++) {
			int albumId = Integer.parseInt(arrayAlbumId[i]);
			int quantity = Integer.parseInt(arrayQuantity[i]);
			float price = Float.parseFloat(arrayPrice[i]);
			
			float subtotal = price * quantity;
			
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setAlbum(new Album(albumId));
			orderDetail.setQuantity(quantity);
			orderDetail.setSubtotal(subtotal);
			orderDetail.setAlbumOrder(order);
			
			orderDetails.add(orderDetail);
			
			totalAmount += subtotal;
		}
		
		order.setSubtotal(totalAmount);
		totalAmount += shippingFee;
		totalAmount += tax;
		
		order.setTotal(totalAmount);
		
		orderDao.update(order);
		
		String message = "The order " + order.getOrderId() + " has been updated successfully";
		
		listAllOrder(message);		
	}

	/**
	 * Obtains albumOrder object that corresponds to the id provided as a parameter in the request object.
	 * If this albumOrder does not exist then an error message is generated and displayed to the user in the view.
	 * Otherwise, the corresponding record is deleted in the database (the model) and the user informed by a message.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void deleteOrder() throws ServletException, IOException {
		Integer orderId = Integer.parseInt(request.getParameter("id"));
		if (orderDao.get(orderId) != null) {
			orderDao.delete(orderId);
			
			String message = "The order ID " + orderId + " has been deleted.";
			listAllOrder(message);
		}
		else {
			String message = "Another user has already deleted order with ID " + orderId + ".";
			listAllOrder(message);
		}
	}
}
