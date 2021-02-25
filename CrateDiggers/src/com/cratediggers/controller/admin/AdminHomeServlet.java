package com.cratediggers.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cratediggers.dao.AlbumDAO;
import com.cratediggers.dao.ArtistDAO;
import com.cratediggers.dao.CustomerDAO;
import com.cratediggers.dao.GenreDAO;
import com.cratediggers.dao.OrderDAO;
import com.cratediggers.dao.ReviewDAO;
import com.cratediggers.dao.UserDAO;
import com.cratediggers.entity.AlbumOrder;
import com.cratediggers.entity.Review;

/**
 * Servlet that handles server requests made to "/admin/" URL.
 */
@WebServlet("/admin/")
public class AdminHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminHomeServlet() {
		super();
	}

	/**
	 * Calls doGet(HttpServletRequest request, HttpServletResponse response) method so both POST and GET requests behave the same.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	/**
	 * Constructs various DAO objects to retrieve information from the database.
	 * Acquires relevant information required by "index.jsp" such as the total number of users, albums, orders, etc.
	 * Also retrieves lists of the most recent orders and reviews.
	 * Information is set as attributes of the request object used to generate "index.jsp" which is displayed to the user in view.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		UserDAO userDao = new UserDAO();
		OrderDAO orderDao = new OrderDAO();
		ReviewDAO reviewDao = new ReviewDAO();
		AlbumDAO albumDao = new AlbumDAO();
		CustomerDAO customerDao = new CustomerDAO();
		GenreDAO genreDao = new GenreDAO();
		ArtistDAO artistDao = new ArtistDAO();
		
		List<AlbumOrder> listMostRecentSales = new ArrayList<AlbumOrder>();
		List<Review> listMostRecentReviews = new ArrayList<Review>();
		try {
			listMostRecentSales = orderDao.listMostRecentSales();
			listMostRecentReviews = reviewDao.listMostRecent();
		} catch (IndexOutOfBoundsException e) {}
		
		long totalUsers = userDao.count();
		long totalAlbums = albumDao.count();
		long totalCustomers = customerDao.count();
		long totalReviews = reviewDao.count();
		long totalOrders = orderDao.count();
		long totalGenres = genreDao.count();
		long totalArtists = artistDao.count();
		
		request.setAttribute("listMostRecentSales", listMostRecentSales);
		request.setAttribute("listMostRecentReviews", listMostRecentReviews);
		
		request.setAttribute("totalUsers", totalUsers);
		request.setAttribute("totalAlbums", totalAlbums);
		request.setAttribute("totalCustomers", totalCustomers);
		request.setAttribute("totalReviews", totalReviews);
		request.setAttribute("totalOrders", totalOrders);
		request.setAttribute("totalGenres", totalGenres);
		request.setAttribute("totalArtists", totalArtists);
		
		String homepage = "index.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);
	}

}
