package com.cratediggers.controller.admin.customer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cratediggers.service.CustomerServices;

/**
 * Servlet that handles server requests made to "/admin/create_customer" URL.
 */
@WebServlet("/admin/create_customer")
public class CreateCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CreateCustomerServlet() {
		super();
	}

	/**
	 * Delegates business logic to relevant, modularised customerService method.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CustomerServices customerServices = new CustomerServices(request, response);
		customerServices.createCustomer();
	}

}
