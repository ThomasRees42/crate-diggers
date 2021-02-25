package com.cratediggers.controller.frontend.shoppingcart;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that handles server requests made to "/clear_cart" URL.
 */
@WebServlet("/clear_cart")
public class ClearCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ClearCartServlet() {
		super();
	}

	/**
	 * Obtains the ShoppingCart object stored in session and clears its contents.
	 * Redirects the customer to the "/view_cart" URL.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute("cart");
		cart.clear();
		
		String cartPage = request.getContextPath().concat("/view_cart");
		response.sendRedirect(cartPage);		
	}

}
