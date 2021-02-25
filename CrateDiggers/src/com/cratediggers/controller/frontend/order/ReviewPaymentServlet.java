package com.cratediggers.controller.frontend.order;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cratediggers.service.PaymentServices;

/**
 * Servlet that handles server requests made to "/review_payment" URL.
 */
@WebServlet("/review_payment")
public class ReviewPaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ReviewPaymentServlet() {
		super();
	}

	/**
	 * Delegates business logic to relevant, modularised orderServices method.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PaymentServices paymentServices = new PaymentServices(request, response);
		paymentServices.reviewPayment();
	}

}
