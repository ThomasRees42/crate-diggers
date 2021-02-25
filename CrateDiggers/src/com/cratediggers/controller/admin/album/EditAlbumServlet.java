package com.cratediggers.controller.admin.album;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cratediggers.service.AlbumServices;

/**
 * Servlet that handles server requests made to "/admin/edit_album" URL.
 */
@WebServlet("/admin/edit_album")
public class EditAlbumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EditAlbumServlet() {
	}

	/**
	 * Delegates business logic to relevant, modularised albumService method.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AlbumServices albumServices = new AlbumServices(request, response);
		albumServices.editAlbum();
	}

}
