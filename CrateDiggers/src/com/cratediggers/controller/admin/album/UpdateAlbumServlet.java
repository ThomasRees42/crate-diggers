package com.cratediggers.controller.admin.album;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cratediggers.service.AlbumServices;

/**
 * Servlet that handles server requests made to "/admin/update_album" URL.
 */
@WebServlet("/admin/update_album")
@MultipartConfig(
		fileSizeThreshold = 1024 * 1024 * 15,	// 16 MB
		maxFileSize = 1024 * 1024 * 16,		// 16 MB
		maxRequestSize = 1024 * 1024 * 20	// 20 MB 
)
public class UpdateAlbumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateAlbumServlet() {
	}

	/**
	 * Delegates business logic to relevant, modularised albumService method.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AlbumServices albumServices = new AlbumServices(request, response);
		albumServices.updateAlbum();
	}

}
