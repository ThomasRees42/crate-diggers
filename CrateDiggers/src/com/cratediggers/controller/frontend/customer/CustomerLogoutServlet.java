package com.cratediggers.controller.frontend.customer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that handles server requests made to "/logout" URL.
 */
@WebServlet("/logout")
public class CustomerLogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CustomerLogoutServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().removeAttribute("loggedCustomer");
		
		response.sendRedirect(request.getContextPath());
	}

}
