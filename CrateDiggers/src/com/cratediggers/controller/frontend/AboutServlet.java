package com.cratediggers.controller.frontend;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that handles server requests made to "/about" URL.
 */
@WebServlet("/about")
public class AboutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AboutServlet() {
		super();
	}

	/**
	 * Displays the sites about page "frontend/about.jsp" to the user in view.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String page = "frontend/about.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		dispatcher.forward(request, response);
	}

}
