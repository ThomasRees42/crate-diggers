package com.cratediggers.controller.frontend;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cratediggers.dao.AlbumDAO;
import com.cratediggers.entity.Album;

/**
 * Servlet that handles server requests made to the base URL.
 */
@WebServlet("")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public HomeServlet() {
		super();
	}

	/**
	 * Constructs various DAO objects to retrieve information from the database.
	 * Acquires relevant information required by "frontend/index.jsp".
	 * This included lists of the newest, best-selling and most-favored albums.
	 * Information is set as attributes of the request object used to generate "frontend/index.jsp" which is displayed to the user in view.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AlbumDAO albumDAO = new AlbumDAO();
		
		List<Album> listNewAlbums = albumDAO.listNewAlbums();
		List<Album> listBestSellingAlbums = albumDAO.listBestSellingAlbums();
		List<Album> listFavoredAlbums = albumDAO.listMostFavoredAlbums();
		
		request.setAttribute("listNewAlbums", listNewAlbums);
		request.setAttribute("listBestSellingAlbums", listBestSellingAlbums);
		request.setAttribute("listFavoredAlbums", listFavoredAlbums);

		String homepage = "frontend/index.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);
	}

}
