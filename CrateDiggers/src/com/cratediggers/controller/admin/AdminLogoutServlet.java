package com.cratediggers.controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet that handles server requests made to "/admin/logout" URL.
 */
@WebServlet("/admin/logout")
public class AdminLogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Logs out the current user by removing the "useremail" attribute from session.
	 * Displays the login page "login.jsp" to the user in view.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("useremail");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
		dispatcher.forward(request, response);		
	}

}
