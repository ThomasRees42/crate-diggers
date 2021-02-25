package com.cratediggers.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cratediggers.dao.AlbumDAO;
import com.cratediggers.dao.ArtistDAO;
import com.cratediggers.entity.Artist;

/**
 * Handles all business logic for the artist module.
 * Follows model-view-controller design architecture by ensuring that the model and view never interact directly.
 */
public class ArtistServices {
	/**
	 * ArtistDAO object used to interact with the database (the model).
	 */
	private ArtistDAO artistDAO;
	/**
	 * Contains parameters received by a HTTP request.
	 */
	private HttpServletRequest request;
	/**
	 * Used to store attributes that are utilised for JSP generation.
	 */
	private HttpServletResponse response;
	
	/**
	 * Constructs an ArtistServices object containing the request and response objects provided by a servlet.
	 * Constructs a new artistDAO object used to interact with the database (the model).
	 * @param request object that contains parameters received in HTTP request made to the server.
	 * @param response object that is used to store attributes which are utilised for JSP generation.
	 */
	public ArtistServices(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		
		artistDAO = new ArtistDAO();
	}
	
	/**
	 * Retrieves a list of all artists from database.
	 * Forwards the list and a specified message onto "artist_list.jsp" to display to users.
	 * @param message to be displayed at top of JSP page
	 * @throws ServletException
	 * @throws IOException
	 */
	public void listArtist(String message) throws ServletException, IOException {
		List<Artist> listArtist = artistDAO.listAll();
		
		request.setAttribute("listArtist", listArtist);
		
		if (message != null) {
			request.setAttribute("message", message);
		}
		
		String listPage = "artist_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);

		requestDispatcher.forward(request, response);		
	}
	
	/**
	 * Provides polymorphism so that listArtist(String message) can be called without a specified message.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void listArtist() throws ServletException, IOException {
		listArtist(null);
	}
	
	/**
	 * Retrieves name and description parameters from request object and find the artist object with the given name.
	 * Checks if artist with given name already exists (name must be unique).
	 * If artist can be created then the ArtistDAO creates the record in the database (the model) and displays list of all artists in view.
	 * If not, generates and displays error message.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void createArtist() throws ServletException, IOException {
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		System.out.println();
		Artist existArtist = artistDAO.findByName(name);
		
		if (existArtist != null) {
			String message = "Could not create artist."
					+ " An artist with name " + name + " already exists.";
			request.setAttribute("message", message);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
			requestDispatcher.forward(request, response);			
		} else {
			Artist newArtist = new Artist(name, description);
			artistDAO.create(newArtist);
			String message = "New artist created successfully.";
			listArtist(message);
		}
	}
	
	/**
	 * Obtains artist object that corresponds to the id provided as a parameter in the request object.
	 * If artist exists then display "artist_form.jsp" in the view, setting the artist object as an attribute in the response object
	 *  so that pre-existing artist object information can auto-fill in form fields.
	 * If artist does not exist then displays error message.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void editArtist() throws ServletException, IOException {
		int artistId = Integer.parseInt(request.getParameter("id"));
		Artist artist = artistDAO.get(artistId);
		if (artist != null) {
			request.setAttribute("artist", artist);
			
			String editPage = "artist_form.jsp";
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(editPage);
			requestDispatcher.forward(request, response);		
		}
		else {
			String message = "Another user has deleted artist with ID " + artistId + ".";
			listArtist(message);
		}
		
	}

	/**
	 * Retrieves artistId, name and description parameters from request object.
	 * Finds the artist objects that currently contains the specified artistId and name.
	 * Checks if an artist object with the given name exists that contains a different artistId to that which was specified.
	 * If this is the case, generates and displays in the view an error message since name must be unique.
	 * Otherwise, modifies the object that contains the specified artistId and updates it in the database (the model).
	 * @throws ServletException
	 * @throws IOException
	 */
	public void updateArtist() throws ServletException, IOException {
		int artistId = Integer.parseInt(request.getParameter("artistId"));
		String artistName = request.getParameter("name");
		String description = request.getParameter("description");
		
		Artist artistById = artistDAO.get(artistId);
		Artist artistByName = artistDAO.findByName(artistName);
		
		if (artistByName != null && artistById.getArtistId() != artistByName.getArtistId()) {
			String message = "Could not update artist."
					+ " A artist with name " + artistName + " already exists.";
			
			request.setAttribute("message", message);			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
			requestDispatcher.forward(request, response);			
		} else {
			artistById.setName(artistName);
			artistById.setDescription(description);
			artistDAO.update(artistById);
			String message = "Artist has been updated successfully";
			listArtist(message);
		}
	}

	/**
	 * Obtains artist object that corresponds to the id provided as a parameter in the request object.
	 * If this artist does not exist then an error message is generated and displayed to the user in the view.
	 * Otherwise, the album table is checked to observe whether this artist has any albums currently being stored.
	 * An artist with albums cannot be deleted as this could entail various runtime errors so an error message is generated and displayed to the user in the view.
	 * For any other artist object, the corresponding record is deleted in the database (the model) and the user informed by a message.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void deleteArtist() throws ServletException, IOException {
		int artistId = Integer.parseInt(request.getParameter("id"));
		if (artistDAO.get(artistId) != null) {
			AlbumDAO albumDAO = new AlbumDAO();
			long numberOfAlbums = albumDAO.countByArtist(artistId);
			String message;
			
			if (numberOfAlbums > 0) {
				message = "Could not delete the artist (ID: %d) because it currently contains some albums.";
				message = String.format(message, artistId);
			} else {
				artistDAO.delete(artistId);		
				message = "The artist with ID " + artistId + " has been removed successfully.";
			}
			
			listArtist(message);
		}
		else {
			String message = "Another user has already deleted artist with ID " + artistId + ".";
			listArtist(message);
		}
		
	}
	
}
