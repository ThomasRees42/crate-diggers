package com.cratediggers.controller.frontend.order;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cratediggers.service.OrderServices;

/**
 * Servlet that handles server requests made to "/checkout" URL.
 */
@WebServlet("/checkout")
public class CheckOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CheckOutServlet() {
		super();
	}

	/**
	 * Delegates business logic to relevant, modularised orderServices method.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		OrderServices orderServices = new OrderServices(request, response);
		orderServices.showCheckoutForm();
	}

}
