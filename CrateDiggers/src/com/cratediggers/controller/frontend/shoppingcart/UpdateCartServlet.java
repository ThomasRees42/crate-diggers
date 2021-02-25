package com.cratediggers.controller.frontend.shoppingcart;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that handles server requests made to "/update_cart" URL.
 */
@WebServlet("/update_cart")
public class UpdateCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateCartServlet() {
		super();
	}

	/**
	 * Receives an array of albumIds from the request object to update the cart with.
	 * Creates an array of quantities of equal size and iteratively fills the indexes quantities stored as parameters within the request object.
	 * Then converts both String arrays to integer arrays.
	 * The cart stored in session is then updated using these arrays and the user redirected to the "/view_cart" URL.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] arrayAlbumIds = request.getParameterValues("albumId");
		String[] arrayQuantities = new String[arrayAlbumIds.length];
		
		for (int i = 1; i <= arrayQuantities.length; i++) {
			String aQuantity = request.getParameter("quantity" + i);
			arrayQuantities[i - 1] = aQuantity;
		}
		
		int[] albumIds = Arrays.stream(arrayAlbumIds).mapToInt(Integer::parseInt).toArray();
		int[] quantities = Arrays.stream(arrayQuantities).mapToInt(Integer::parseInt).toArray();
		
		ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute("cart");
		cart.updateCart(albumIds, quantities);
		
		String cartPage = request.getContextPath().concat("/view_cart");
		response.sendRedirect(cartPage);		
	}

}
