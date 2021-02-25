package com.cratediggers.controller.frontend.shoppingcart;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cratediggers.dao.AlbumDAO;
import com.cratediggers.entity.Album;

/**
 * Servlet that handles server requests made to "/add_to_cart" URL.
 */
@WebServlet("/add_to_cart")
public class AddAlbumToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddAlbumToCartServlet() {
		super();
	}

	/**
	 * Receives albumId of the album to be added from the request object.
	 * Also obtains the cartObject stored in session.
	 * If cartObject is either null or not and instance of ShoppingCart then a new shoppingCart object is constructed and set as a session attribute.
	 * The album object that corresponds to the specified albumId is then retrieved from the database and added to the cart.
	 * Finally, the customer is redirected to the URL "/view_cart". 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer albumId = Integer.parseInt(request.getParameter("album_id"));
		
		Object cartObject = request.getSession().getAttribute("cart");
		
		ShoppingCart shoppingCart = null;
		
		if (cartObject != null && cartObject instanceof ShoppingCart) {
			shoppingCart =  (ShoppingCart) cartObject;
		} else {
			shoppingCart = new ShoppingCart();
			request.getSession().setAttribute("cart", shoppingCart);
		}
		
		AlbumDAO albumDAO = new AlbumDAO();
		Album album = albumDAO.get(albumId);
		
		shoppingCart.addItem(album);
		
		String cartPage = request.getContextPath().concat("/view_cart");
		response.sendRedirect(cartPage);
		
	}

}
