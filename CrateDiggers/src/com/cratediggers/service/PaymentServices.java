package com.cratediggers.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cratediggers.entity.Album;
import com.cratediggers.entity.AlbumOrder;
import com.cratediggers.entity.Customer;
import com.cratediggers.entity.OrderDetail;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

/**
 * Handles all business logic necessary for completing purchases via the PayPal payment gateway REST API.
 */
public class PaymentServices {
	
	/**
	 * The unique client ID provided by PayPal to interact with the API
	 */
	private static final String CLIENT_ID = "AUpCkBr3YZmaFcCK4BXUlM6T3OwkKBybzSG_Dnl5hiOiYNkU7IWrV-AMXllfHKnA9PhRjFggBAa7xRXg";
	/**
	 * The unique client secret provided by PayPal to interact with the API
	 */
	private static final String CLIENT_SECRET = "EMas1bth-IBBg240DvKgnjKP3Nf0DnsT0v8srdjv393gzRj6PP7MNJxkl3EdTcR4dNzt-3xgSeqGMaau";
	
	/**
	 * The mode of the API.
	 * "sandbox" does not complete real-life transactions, used for testing.
	 * "live" completes real-life transactions when the site is launched.
	 */
	private String mode = "sandbox";
	
	/**
	 * Contains parameters received by a HTTP request.
	 */
	private HttpServletRequest request;
	/**
	 * Used to store attributes that are utilised for JSP generation.
	 */
	private HttpServletResponse response;
	
	/**
	 * Constructs an AlbumServices object containing the request and response objects provided by a servlet.
	 * @param request object that contains parameters received in HTTP request made to the server.
	 * @param response object that is used to store attributes which are utilised for JSP generation.
	 */
	public PaymentServices(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
	}
	
	/**
	 * Receives an order object that is used to construct a Payer object and list of Transaction objects.
	 * These are used to create a RequestPayment object with intent "authorize" and which also contains redirectURLs which are generated.
	 * A Payment is then attempted to be constructed depending on the CLIENT_ID, CLIENT_SECRET and mode fields.
	 * If there is an error authorising the payment then a ServletException is thrown.
	 * Otherwise, the payment is successfully authorised and the customer is redirected to the approvalURL to complete payment.
	 * @param order to be authorised be PayPal API
	 * @throws ServletException if payment fails to be authorised
	 * @throws IOException
	 */
	public void authorizePayment(AlbumOrder order) throws ServletException, IOException {
		Payer payer = getPayerInformation(order);
		RedirectUrls redirectUrls = getRedirectURLs();		
			
		List<Transaction> transactions = getTransactionInformation(order);
		
		Payment requestPayment = new Payment();
		requestPayment.setPayer(payer)
					  .setRedirectUrls(redirectUrls)
					  .setIntent("authorize")
					  .setTransactions(transactions);
		
		System.out.println("====== REQUEST PAYMENT: ======");
		System.out.println(requestPayment);		
		
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, mode);
		
		try {
			Payment authorizedPayment = requestPayment.create(apiContext);
			System.out.println("====== AUTHORIZED PAYMENT: ======");
			System.out.println(authorizedPayment);
			
			String approvalURL = getApprovalURL(authorizedPayment);
			
			response.sendRedirect(approvalURL);
			
		} catch (PayPalRESTException e) {
			e.printStackTrace();
			throw new ServletException("Error in authorizing payment.");
		}
		
		
		// get approval link
		
		// redirect to Paypal's payment page
	}
	
	
	/**
	 * Performs a linear search on the Links field of the Payment object to obtain the "approval_url" which the customer needs to be redirected to.
	 * @param authorizedPayment which contains list of links
	 * @return The "approval_url"
	 */
	private String getApprovalURL(Payment authorizedPayment) {
		String approvalURL = null;
		
		List<Links> links = authorizedPayment.getLinks();
		
		for (Links link : links) {
			if (link.getRel().equalsIgnoreCase("approval_url")) {
				approvalURL = link.getHref();
				break;
			}
		}
		
		return approvalURL;
	}

	/**
	 * Generates a Transaction object required by the PayPal API.
	 * Includes amount details, shipping address and a list of items being purchased, as specified by the AlbumOrder object. 
	 * @param order to be converted to a Transaction object
	 * @return A Transaction object converted from an AlbumOrder object stored within a list.
	 */
	protected List<Transaction> getTransactionInformation(AlbumOrder order) {
		Transaction transaction = new Transaction();
		transaction.setDescription("Albums ordered on Crate Diggers");
		Amount amount = getAmountDetails(order);
		transaction.setAmount(amount);
		
		ItemList itemList = new ItemList();
		ShippingAddress shippingAddress = getRecipientInformation(order);
		itemList.setShippingAddress(shippingAddress);
		
		List<Item> paypalItems = new ArrayList<>();
		Iterator<OrderDetail> iterator = order.getOrderDetails().iterator();
		
		while (iterator.hasNext()) {
			OrderDetail orderDetail = iterator.next();
			Album album = orderDetail.getAlbum();
			Integer quantity = orderDetail.getQuantity();
			
			Item paypalItem = new Item();
			paypalItem.setCurrency("GBP")
					  .setName(album.getTitle())
					  .setQuantity(String.valueOf(quantity))
					  .setPrice(String.format("%.2f", album.getPrice()));
			
			paypalItems.add(paypalItem);			
		}
		
		itemList.setItems(paypalItems);
		transaction.setItemList(itemList);
		
		List<Transaction> listTransaction = new ArrayList<>();
		listTransaction.add(transaction);
		
		return listTransaction;
	}
	
	
	/**
	 * Generates a ShippingAddress object required by the PayPal API.
	 * Includes information relating to the recipient and their shipping address as specified by the AlbumOrder object.
	 * @param order that contains the data necessary to generate the shippingAddress object
	 * @return A shippingOrder object that contains information relating to the recipient and their shipping address
	 */
	private ShippingAddress getRecipientInformation(AlbumOrder order) {
		ShippingAddress shippingAddress = new ShippingAddress();
		String recipientName = order.getFirstName() + " " + order.getLastName();
		shippingAddress.setRecipientName(recipientName)
					   .setPhone(order.getPhone())
					   .setLine1(order.getAddressLine1())
					   .setLine2(order.getAddressLine2())
					   .setCity(order.getTown())
					   .setState(order.getCounty())
					   .setCountryCode("GB")
					   .setPostalCode(order.getPostcode());
		
		return shippingAddress;
	}
	
	/**
	 * Generates a Payer object required by the PayPal API.
	 * Includes information relating to the payer as specified by the AlbumOrder object.
	 * @param order that contains the data necessary to generate the payer object
	 * @return A payer object that contains information relating to the payer
	 */
	protected Payer getPayerInformation(AlbumOrder order) {
		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");
				
		Customer customer = order.getCustomer();
		
		PayerInfo payerInfo = new PayerInfo();
		payerInfo.setFirstName(customer.getFirstName());
		payerInfo.setLastName(customer.getLastName());
		payerInfo.setEmail(customer.getEmail());
		payer.setPayerInfo(payerInfo);
		
		return payer;
	}
	
	/**
	 * Generates a RedirectUrls object required by the PayPal API.
	 * RedirectUrls object contains a cancelUrl for if the payment fails that ends "/view_cart" and a returnUrl for once the payment gateway is completed that ends "/review_payment".
	 * These concatenated onto the end of a baseURL that is obtained from the request object.
	 * @return redirectUrls object that contains URLs required by the PayPal API to return to the Crate Diggers site.
	 */
	private RedirectUrls getRedirectURLs() {
		String requestURL = request.getRequestURL().toString();
		String requestURI = request.getRequestURI();
		String baseURL = requestURL.replace(requestURI, "").concat(request.getContextPath());
		
		RedirectUrls redirectUrls = new RedirectUrls();
		String cancelUrl = baseURL.concat("/view_cart");
		String returnUrl = baseURL.concat("/review_payment");
		
		System.out.println("Return URL: " + returnUrl);
		System.out.println("Cancel URL: " + cancelUrl);
		
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(returnUrl);
		
		return redirectUrls;
	}
	
	/**
	 * Generates an Amount object required by the PayPal API.
	 * Amount object contains information about price amounts such as shipping, tax and subtotals as specified by the AlbumOrder object.
	 * Also configures additional information such as currency.
	 * @param order that contains the information necessary to generate an Amount object
	 * @return Amount object that contains information relating to cost
	 */
	private Amount getAmountDetails(AlbumOrder order) {
		Details details = new Details();
		details.setShipping(String.format("%.2f", order.getShippingFee()));
		details.setTax(String.format("%.2f", order.getTax()));
		details.setSubtotal(String.format("%.2f", order.getSubtotal()));
		
		Amount amount =  new Amount();
		amount.setCurrency("GBP");
		amount.setDetails(details);
		amount.setTotal(String.format("%.2f", order.getTotal()));
		
		return amount;
	}

	/**
	 * Retrieves the paymentId and payerId from the request object.
	 * A Payment is then attempted to be found depending on the paymentId, CLIENT_ID, CLIENT_SECRET and mode fields.
	 * If there is an error acquiring the payment then a ServletException is thrown.
	 * Payment data fields are set as attributes of the request object.
	 * This is used to generate the "review_payment.jsp" page which is displayed to customers to ensure they approve the payment.
	 * @throws ServletException if there is an error in obtaining payment details from the PayPal API
	 */
	public void reviewPayment() throws ServletException {
		String paymentId = request.getParameter("paymentId");
		String payerId = request.getParameter("PayerID");
		
		if (paymentId == null || payerId == null) {
			throw new ServletException("Error in displaying payment review");
		}
		
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, mode);
		
		try {
			Payment payment = Payment.get(apiContext, paymentId);
			
			PayerInfo payerInfo = payment.getPayer().getPayerInfo();
			Transaction transaction = payment.getTransactions().get(0);
			ShippingAddress shippingAddress = transaction.getItemList().getShippingAddress();
			
			request.setAttribute("payer", payerInfo);
			request.setAttribute("recipient", shippingAddress);
			request.setAttribute("transaction", transaction);
			
			String reviewPage = "frontend/review_payment.jsp?paymentId=" + paymentId + "&PayerID=" + payerId;
			request.getRequestDispatcher(reviewPage).forward(request, response);
			
		} catch (PayPalRESTException | IOException e) {
			e.printStackTrace();
			throw new ServletException("Error in getting payment details from PayPal.");
		}
	}

	/**
	 * Retrieves the paymentId and payerId from the request object and creates a PaymentExecution object with payerID.
	 * A Payment is then attempted to be found depending on the paymentId, CLIENT_ID, CLIENT_SECRET and mode fields.
	 * If there is an error acquiring the payment then a PayPalRESTException is thrown.
	 * The payment is then executed.
	 * @return the payment object
	 * @throws PayPalRESTException if there is an error in obtaining payment details from the PayPal API
	 */
	public Payment executePayment() throws PayPalRESTException {
		String paymentId = request.getParameter("paymentId");
		String payerId = request.getParameter("PayerID");
		
		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(payerId);
		
		Payment payment = new Payment().setId(paymentId);
		
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, mode);
		
		return payment.execute(apiContext, paymentExecution);
		
	}
	
	
	
}
