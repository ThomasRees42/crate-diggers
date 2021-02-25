package com.cratediggers.controller.admin.order;

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
 * Servlet that handles server requests made to "/admin/add_album_form" URL.
 */
@WebServlet("/admin/add_album_form")
public class ShowAddAlbumFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ShowAddAlbumFormServlet() {
		super();
	}

	/**
	 * Constructs an albumDAO object that retrieves a list of all albums from the database (the model).
	 * This is set as an attribute of the request object to generate the "add_album_form.jsp" page.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AlbumDAO albumDao = new AlbumDAO();
		List<Album> listAlbum = albumDao.listAll();
		request.setAttribute("listAlbum", listAlbum);
		
		String addFormPage = "add_album_form.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(addFormPage);
		dispatcher.forward(request, response);
		
	}

}
