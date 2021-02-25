package com.cratediggers.controller.admin.artist;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cratediggers.service.ArtistServices;

/**
 * Servlet that handles server requests made to "/admin/list_artist" URL.
 */
@WebServlet("/admin/list_artist")
public class ListArtistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ListArtistServlet() {
		super();
	}

	/**
	 * Delegates business logic to relevant, modularised artistService method.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().println("List Artist");
		ArtistServices artistServices = new ArtistServices(request, response);
		artistServices.listArtist();
	}

}
