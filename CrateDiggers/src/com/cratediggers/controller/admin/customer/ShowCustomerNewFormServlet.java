package com.cratediggers.controller.admin.customer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cratediggers.service.CustomerServices;

/**
 * Servlet that handles server requests made to "/admin/new_customer" URL.
 */
@WebServlet("/admin/new_customer")
public class ShowCustomerNewFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ShowCustomerNewFormServlet() {
		super();
	}

	/**
	 * Delegates business logic to relevant, modularised customerService method.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CustomerServices customerServices = new CustomerServices(request, response);
		customerServices.newCustomer();
	}

}
