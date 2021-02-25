package com.cratediggers.controller.frontend.shoppingcart;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that handles server requests made to "/view_cart" URL.
 */
@WebServlet("/view_cart")
public class ViewCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ViewCartServlet() {
		super();
	}

	/**
	 * Obtains the cartObject stored in session.
	 * If this object does not exist then generates a new ShoppingCart object and sets that as an attribute of session.
	 * Then displays the page "frontend/shopping_cart.jsp" to customers in view.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Object cartObject = request.getSession().getAttribute("cart");
		
		if (cartObject == null) {
			ShoppingCart shoppingCart = new ShoppingCart();
			request.getSession().setAttribute("cart", shoppingCart);					
		}	

		
		String cartPage = "frontend/shopping_cart.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(cartPage);
		dispatcher.forward(request, response);
	}

}
