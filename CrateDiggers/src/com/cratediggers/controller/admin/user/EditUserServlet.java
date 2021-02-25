package com.cratediggers.controller.admin.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cratediggers.service.UserServices;

/**
 * Servlet that handles server requests made to "/admin/edit_user" URL.
 */
@WebServlet("/admin/edit_user")
public class EditUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EditUserServlet() {
		super();
	}

	/**
	 * Delegates business logic to relevant, modularised userServices method.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserServices userServices = new UserServices(request, response);
		userServices.editUser();
	}

}
