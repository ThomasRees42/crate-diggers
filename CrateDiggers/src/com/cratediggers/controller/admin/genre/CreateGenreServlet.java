package com.cratediggers.controller.admin.genre;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cratediggers.service.GenreServices;

/**
 * Servlet that handles server requests made to "/admin/create_genre" URL.
 */
@WebServlet("/admin/create_genre")
public class CreateGenreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CreateGenreServlet() {
	}

	/**
	 * Delegates business logic to relevant, modularised genreServices method.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GenreServices genreServices = new GenreServices(request, response);
		genreServices.createGenre();
	}

}
