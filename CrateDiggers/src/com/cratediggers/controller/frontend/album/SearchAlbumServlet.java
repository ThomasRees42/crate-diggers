package com.cratediggers.controller.frontend.album;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cratediggers.service.AlbumServices;

/**
 * Servlet that handles server requests made to "/search" URL.
 */
@WebServlet("/search")
public class SearchAlbumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SearchAlbumServlet() {
	}

	/**
	 * Delegates business logic to relevant, modularised albumServices method.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AlbumServices albumServices = new AlbumServices(request, response);
		albumServices.search(false);
	}

}
