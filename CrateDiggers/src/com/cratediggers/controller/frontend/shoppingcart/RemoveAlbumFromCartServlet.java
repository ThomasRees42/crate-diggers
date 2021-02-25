package com.cratediggers.controller.frontend.shoppingcart;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cratediggers.entity.Album;

/**
 * Servlet that handles server requests made to "/remove_from_cart" URL.
 */
@WebServlet("/remove_from_cart")
public class RemoveAlbumFromCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RemoveAlbumFromCartServlet() {
		super();
	}

	/**
	 * Receives albumId of the album to be removed from the request object.
	 * Also obtains the cartObject stored in session.
	 * Removes the album from cart that contains the same albumId.
	 * Finally, the customer is redirected to the URL "/view_cart". 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer albumId = Integer.parseInt(request.getParameter("album_id"));
		
		Object cartObject = request.getSession().getAttribute("cart");
		
		ShoppingCart shoppingCart =  (ShoppingCart) cartObject;
		
		shoppingCart.removeItem(new Album(albumId));
		
		String cartPage = request.getContextPath().concat("/view_cart");
		response.sendRedirect(cartPage);
	}

}
