package com.cratediggers.controller.frontend.customer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cratediggers.service.CustomerServices;

/**
 * Servlet that handles server requests made to "/edit_profile" URL.
 */
@WebServlet("/edit_profile")
public class EditCustomerProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EditCustomerProfileServlet() {
		super();
	}

	/**
	 * Delegates business logic to relevant, modularised customerServices method.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CustomerServices customerServices = new CustomerServices(request, response);
		customerServices.showCustomerProfileEditForm();
	}

}
