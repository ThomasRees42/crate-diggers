package com.cratediggers.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cratediggers.dao.AlbumDAO;
import com.cratediggers.dao.GenreDAO;
import com.cratediggers.entity.Genre;

/**
 * Handles all business logic for the genre module.
 * Follows model-view-controller design architecture by ensuring that the model and view never interact directly.
 */
public class GenreServices {
	/**
	 * GenreDAO object used to interact with the database (the model).
	 */
	private GenreDAO genreDAO;
	/**
	 * Contains parameters received by a HTTP request.
	 */
	private HttpServletRequest request;
	/**
	 * Used to store attributes that are utilised for JSP generation.
	 */
	private HttpServletResponse response;
	
	/**
	 * Constructs an GenreServices object containing the request and response objects provided by a servlet.
	 * Constructs a new genreDAO object used to interact with the database (the model).
	 * @param request object that contains parameters received in HTTP request made to the server.
	 * @param response object that is used to store attributes which are utilised for JSP generation.
	 */
	public GenreServices(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		
		genreDAO = new GenreDAO();
	}
	
	/**
	 * Retrieves a list of all genres from database.
	 * Forwards the list and a specified message onto "genre_list.jsp" to display to users.
	 * @param message to be displayed at top of JSP page
	 * @throws ServletException
	 * @throws IOException
	 */
	public void listGenre(String message) throws ServletException, IOException {
		List<Genre> listGenre = genreDAO.listAll();
		
		request.setAttribute("listGenre", listGenre);
		
		if (message != null) {
			request.setAttribute("message", message);
		}
		
		String listPage = "genre_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);

		requestDispatcher.forward(request, response);		
	}
	
	/**
	 * Provides polymorphism so that listGenre(String message) can be called without a specified message.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void listGenre() throws ServletException, IOException {
		listGenre(null);
	}
	
	/**
	 * Retrieves name and description parameters from request object and find the genre object with the given name.
	 * Checks if genre with given name already exists (name must be unique).
	 * If genre can be created then the GenreDAO creates the record in the database (the model) and displays list of all genres in view.
	 * If not, generates and displays error message.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void createGenre() throws ServletException, IOException {
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		Genre existGenre = genreDAO.findByName(name);
		
		if (existGenre != null) {
			String message = "Could not create genre."
					+ " A genre with name " + name + " already exists.";
			request.setAttribute("message", message);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
			requestDispatcher.forward(request, response);			
		} else {
			Genre newGenre = new Genre(name, description);
			genreDAO.create(newGenre);
			String message = "New category created successfully.";
			listGenre(message);
		}
	}
	
	/**
	 * Obtains genre object that corresponds to the id provided as a parameter in the request object.
	 * If genre exists then display "genre_form.jsp" in the view, setting the genre object as an attribute in the response object
	 *  so that pre-existing genre object information can auto-fill in form fields.
	 * If genre does not exist then displays error message.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void editGenre() throws ServletException, IOException {
		int genreId = Integer.parseInt(request.getParameter("id"));
		Genre genre = genreDAO.get(genreId);
		if (genre != null) {
			request.setAttribute("genre", genre);
			
			String editPage = "genre_form.jsp";
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(editPage);
			requestDispatcher.forward(request, response);	
		}
		else {
			String message = "Another user has deleted genre with ID " + genreId + ".";
			listGenre(message);
		}
		
	}

	/**
	 * Retrieves genreId, name and description parameters from request object.
	 * Finds the genre objects that currently contains the specified genreId and name.
	 * Checks if an genre object with the given name exists that contains a different genreId to that which was specified.
	 * If this is the case, generates and displays in the view an error message since name must be unique.
	 * Otherwise, modifies the object that contains the specified genreId and updates it in the database (the model).
	 * @throws ServletException
	 * @throws IOException
	 */
	public void updateGenre() throws ServletException, IOException {
		int genreId = Integer.parseInt(request.getParameter("genreId"));
		String genreName = request.getParameter("name");
		String description = request.getParameter("description");
		
		Genre genreById = genreDAO.get(genreId);
		Genre genreByName = genreDAO.findByName(genreName);
		
		if (genreByName != null && genreById.getGenreId() != genreByName.getGenreId()) {
			String message = "Could not update genre."
					+ " A genre with name " + genreName + " already exists.";
			
			request.setAttribute("message", message);			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
			requestDispatcher.forward(request, response);			
		} else {
			genreById.setName(genreName);
			genreById.setDescription(description);
			genreDAO.update(genreById);
			String message = "Genre has been updated successfully";
			listGenre(message);
		}
	}

	/**
	 * Obtains genre object that corresponds to the id provided as a parameter in the request object.
	 * If this genre does not exist then an error message is generated and displayed to the user in the view.
	 * Otherwise, the genre table is checked to observe whether this genre has any albums currently being stored.
	 * A genre with albums cannot be deleted as this could entail various runtime errors so an error message is generated and displayed to the user in the view.
	 * For any other genre object, the corresponding record is deleted in the database (the model) and the user informed by a message.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void deleteGenre() throws ServletException, IOException {
		int genreId = Integer.parseInt(request.getParameter("id"));
		if (genreDAO.get(genreId) != null) {
			AlbumDAO albumDAO = new AlbumDAO();
			long numberOfAlbums = albumDAO.countByGenre(genreId);
			String message;
			
			if (numberOfAlbums > 0) {
				message = "Could not delete the genre (ID: %d) because it currently contains some albums.";
				message = String.format(message, genreId);
			} else {
				genreDAO.delete(genreId);		
				message = "The genre with ID " + genreId + " has been removed successfully.";
			}
			
			listGenre(message);
		}
		else {
			String message = "Another user has already deleted genre with ID " + genreId + ".";
			listGenre(message);
		}
		
	}
	
	/**
	 * Retrieves a list of all genres from database.
	 * Forwards the list onto "frontend/genre_list.jsp" to display to customers.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void listGenreFrontend() throws ServletException, IOException {
		List<Genre> listGenre = genreDAO.listAll();		
		request.setAttribute("listGenre", listGenre);
		String page = "frontend/genre_list.jsp";
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
		requestDispatcher.forward(request, response);
	}
	
}
