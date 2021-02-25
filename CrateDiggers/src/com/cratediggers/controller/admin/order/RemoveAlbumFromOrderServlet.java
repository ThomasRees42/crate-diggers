package com.cratediggers.controller.admin.order;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cratediggers.entity.AlbumOrder;
import com.cratediggers.entity.OrderDetail;

/**
 * Servlet that handles server requests made to "/admin/remove_album_from_order" URL.
 */
@WebServlet("/admin/remove_album_from_order")
public class RemoveAlbumFromOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RemoveAlbumFromOrderServlet() {
		super();
	}

	/**
	 * Retrieves the albumId stored as a parameter within the request object and the order object stored in session.
	 * The order's orderDetail set is iterated over until the album with the specified is removed from the order and the total price decreased accordingly.
	 * Finally, the "order_form.jsp" is displayed to the user in view.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int albumId = Integer.parseInt(request.getParameter("id"));
		HttpSession session = request.getSession();
		AlbumOrder order = (AlbumOrder) session.getAttribute("order");

		Set<OrderDetail> orderDetails = order.getOrderDetails();
		Iterator<OrderDetail> iterator = orderDetails.iterator();
		
		int quantity = 0;
		float newSubtotal = order.getSubtotal();
		while (iterator.hasNext()) {
			OrderDetail orderDetail = iterator.next();
			
			if (orderDetail.getAlbum().getAlbumId() == albumId) {
				newSubtotal = order.getSubtotal() - orderDetail.getSubtotal();
				order.setSubtotal(newSubtotal);
				iterator.remove();
			} else {
				quantity++;
			}
		}
		order.setTax(newSubtotal * 0.1f);
		order.setShippingFee(quantity);
		order.setTotal(newSubtotal + order.getTax() + order.getShippingFee());
		
		String editOrderFormPage = "order_form.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(editOrderFormPage);
		dispatcher.forward(request, response);
	}

}
