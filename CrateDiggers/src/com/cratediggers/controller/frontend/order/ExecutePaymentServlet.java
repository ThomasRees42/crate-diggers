package com.cratediggers.controller.frontend.order;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cratediggers.service.OrderServices;
import com.cratediggers.service.PaymentServices;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;

/**
 * Servlet that handles server requests made to "/execute_payment" URL.
 */
@WebServlet("/execute_payment")
public class ExecutePaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExecutePaymentServlet() {
		super();
	}

	/**
	 * Constructs a PaymentServices object and attempts to execute the PayPal payment.
	 * If PayPalRESTException is thrown then throws a new ServletException.
	 * Otherwise, stores the successful payment within the database (the model) and retrieves the corresponding orderId.
	 * This orderId along with the PayerInfo and Transaction objects stored in the Payment object are set as attributes of the request object.
	 * These are used to generate the "frontend/payment_receipt.jsp" page which is displayed to the customer in view.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PaymentServices paymentServices = new PaymentServices(request, response);
		
		try {
			Payment payment = paymentServices.executePayment();
			
			OrderServices orderServices = new OrderServices(request, response);
			Integer orderId = orderServices.placeOrderPaypal(payment);
			
			HttpSession session = request.getSession();
			session.setAttribute("orderId", orderId);
			
			PayerInfo payerInfo = payment.getPayer().getPayerInfo();
			Transaction transaction = payment.getTransactions().get(0);
			
			session.setAttribute("payer", payerInfo);
			session.setAttribute("transaction", transaction);
			
			String receiptPage = "frontend/payment_receipt.jsp";
			request.getRequestDispatcher(receiptPage).forward(request, response);			
			
		} catch (PayPalRESTException e) {
			e.printStackTrace();
			throw new ServletException("Error in executing payment.");
		}
	}

}
