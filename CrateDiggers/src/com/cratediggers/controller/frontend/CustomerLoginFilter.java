package com.cratediggers.controller.frontend;

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
 * Filter that determines whether the customer is logged in and can access specific pages in the front-end.
 */
@WebFilter("/*")
public class CustomerLoginFilter implements Filter {
	/**
	 * Array of URLs that require login to access.
	 */
	private static final String[] loginRequiredURLs = {
			"/view_profile", "/edit_profile", "/update_profile", "/write_review",
			"/checkout", "/place_order", "/view_orders", "/show_order_detail",
			"/personalised_recommendations"
	};
	
	public CustomerLoginFilter() {
	}

	public void destroy() {
	}

	/**
	 * Checks if the customer is attempting to access an admin URL in which case passes the request to the AdminLoginFilter.
	 * Attempts to retrieve a HttpSession from the request object.
	 * If the session object exists and contains the "loggedCustomer" attribute then the customer is logged in.
	 * If the customer is not logged and is attempting to access a URL that requires login then displays the login page "login.jsp" instead.
	 * Stores the original server request as an attribute within session so that the customer returns to the page they were originally intending to reach after login.
	 * Otherwise, allows the customer to access the servlet they originally intended to reach.
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(false);
		
		String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
		
		if (path.startsWith("/admin/")) {
			chain.doFilter(request, response);
			return;
		}
		
		boolean loggedIn = session != null && session.getAttribute("loggedCustomer") != null;
		
		String requestURL = httpRequest.getRequestURL().toString();
		
		System.out.println("Path: " + path);
		System.out.println("loggedIn: " + loggedIn);
		
		if (!loggedIn && isLoginRequired(requestURL)) {
			String queryString = httpRequest.getQueryString();
			String redirectURL = requestURL;
			
			if (queryString != null) {
				redirectURL = redirectURL.concat("?").concat(queryString);
			}
			
			session.setAttribute("redirectURL", redirectURL);
			
			String loginPage = "frontend/login.jsp";
			RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(loginPage);
			dispatcher.forward(request, response);
			
			
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * Determines if a given request URL requires login for a customer to access.
	 * Checks if the request URL contains any of the strings within the loginRequiredURLs array.
	 * If it does then login is required.
	 * @param requestURL to check for login requirements
	 * @return True if login is required, false if not.
	 */
	private boolean isLoginRequired(String requestURL) {
		for (String loginRequiredURL : loginRequiredURLs) {
			if (requestURL.contains(loginRequiredURL)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
