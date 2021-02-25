package com.cratediggers.controller.admin.order;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cratediggers.dao.AlbumDAO;
import com.cratediggers.entity.Album;
import com.cratediggers.entity.AlbumOrder;
import com.cratediggers.entity.OrderDetail;

/**
 * Servlet that handles server requests made to "/admin/add_album_to_order" URL.
 */
@WebServlet("/admin/add_album_to_order")
public class AddAlbumToOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddAlbumToOrderServlet() {
		super();
	}

	/**
	 * Retrieves albumId and quantity stored as parameters of the request object.
	 * Then obtains the album object that corresponds to this albumId and the AlbumOrder currently being edited from session. 
	 * The album price and quantity are utilised to calculate a subtotal for the OrderDetail object.
	 * The album, quantity and subtotal fields of a new OrderDetail object are then all set.
	 * This OrderDetail object is added to the AlbumOrder.
	 * The "NewAlbumPendingToAddToOrder" session attribute is then set and "add_album_result.jsp" is displayed to the user
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int albumId = Integer.parseInt(request.getParameter("albumId"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		
		AlbumDAO albumDao = new AlbumDAO();
		Album album = albumDao.get(albumId);
		
		HttpSession session = request.getSession();
		AlbumOrder order = (AlbumOrder) session.getAttribute("order");
		
		float subtotal = quantity * album.getPrice();
		
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setAlbum(album);
		orderDetail.setQuantity(quantity);
		orderDetail.setSubtotal(subtotal);
		
		float newSubtotal = order.getSubtotal() + subtotal;
		order.setSubtotal(newSubtotal);
		order.setTax(subtotal * 0.1f);
		order.setShippingFee(order.getShippingFee() + quantity);
		order.setTotal(newSubtotal + order.getTax() + order.getShippingFee());
		
		order.getOrderDetails().add(orderDetail);
		
		request.setAttribute("album", album);
		session.setAttribute("NewAlbumPendingToAddToOrder", true);
		
		String resultPage = "add_album_result.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);
	}

}
