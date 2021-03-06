package com.cratediggers.controller.frontend.album;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cratediggers.service.AlbumServices;

/**
 * Servlet that handles server requests made to "/view_genre" URL.
 */
@WebServlet("/view_genre")
public class ViewAlbumsByGenreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ViewAlbumsByGenreServlet() {
	}

	/**
	 * Delegates business logic to relevant, modularised albumServices method.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AlbumServices albumServices = new AlbumServices(request, response);
		albumServices.listAlbumsByGenre();
	}

}
