package com.cratediggers.controller.frontend.genre;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cratediggers.service.GenreServices;

/**
 * Servlet that handles server requests made to "/view_genres" URL.
 */
@WebServlet("/view_genres")
public class ViewGenresServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ViewGenresServlet() {
	}

	/**
	 * Delegates business logic to relevant, modularised genreServices method.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GenreServices genreServices = new GenreServices(request, response);
		genreServices.listGenreFrontend();
	}

}
