package com.cratediggers.controller.admin;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Filter that determines whether admin user is logged in and can access pages in the back-end.
 */
@WebFilter("/admin/*")
public class AdminLoginFilter implements Filter {

	public AdminLoginFilter() {
	}

	public void destroy() {
	}

	/**
	 * Attempts to retrieve a HttpSession from the request object.
	 * If the session object exists and contains the "useremail" attribute then the user is logged in.
	 * Also determines whether the user is making a request to the login page or making a login request.
	 * If successfully logged in and either making a login request or request to the login page then the home page "/admin/" is displayed.
	 * If either logged in or the user is making a login request then the filter allows the user to access the servlet they were attempting to.
	 * Otherwise, the user is not logged and so is displayed the login page at "login.jsp".
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(false);
		
		boolean loggedIn = session != null && session.getAttribute("useremail") != null;
		String loginURI = httpRequest.getContextPath() + "/admin/login";
		boolean loginRequest = httpRequest.getRequestURI().equals(loginURI);
		boolean loginPage = httpRequest.getRequestURI().endsWith("login.jsp");
		
		if (loggedIn && (loginRequest || loginPage)) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/");
			dispatcher.forward(request, response);			
			
		} else if (loggedIn || loginRequest) {
			System.out.println("user logged in");
			chain.doFilter(request, response);	
		} else {
			System.out.println("user not logged in");
			RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
			
		}
		
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
