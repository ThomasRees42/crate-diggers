package com.cratediggers.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.cratediggers.dao.AlbumDAO;
import com.cratediggers.dao.ArtistDAO;
import com.cratediggers.dao.GenreDAO;
import com.cratediggers.entity.Album;
import com.cratediggers.entity.Artist;
import com.cratediggers.entity.Genre;

/**
 * Handles all business logic for the album module.
 * Follows model-view-controller design architecture by ensuring that the model and view never interact directly.
 */
public class AlbumServices {
	/**
	 * AlbumDAO object used to interact with the database (the model).
	 */
	private AlbumDAO albumDAO;
	/**
	 * GenreDAO object used to interact with the database (the model).
	 */
	private GenreDAO genreDAO;
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
	 * Constructs an AlbumServices object containing the request and response objects provided by a servlet.
	 * Constructs new albumDAO, genreDAO and artistDAO objects used to interact with the database (the model).
	 * @param request object that contains parameters received in HTTP request made to the server.
	 * @param response object that is used to store attributes which are utilised for JSP generation.
	 */
	public AlbumServices(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
		albumDAO = new AlbumDAO();
		genreDAO = new GenreDAO();
		artistDAO = new ArtistDAO();
	}

	/**
	 * Provides polymorphism so that listAlbum(String message) can be called without a specified message.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void listAlbums() throws ServletException, IOException {
		listAlbums(null);
	}
	
	/**
	 * Retrieves a list of all albums from database.
	 * Forwards the list and a specified message onto "album_list.jsp" to display to users.
	 * @param message to be displayed at top of JSP page
	 * @throws ServletException
	 * @throws IOException
	 */
	public void listAlbums(String message) throws ServletException, IOException {
		List<Album> listAlbums = albumDAO.listAll();
		request.setAttribute("listAlbums", listAlbums);
		
		if (message != null) {
			request.setAttribute("message", message);
		}
		
		String listPage = "album_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
		requestDispatcher.forward(request, response);
		
	}
	
	
	/**
	 * Displays a form ("album_form.jsp") in view for users to create a new album.
	 * Lists of all genres and artists are generated and sent in the request object to be displayed as options for the user.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void showAlbumNewForm() throws ServletException, IOException {
		List<Genre> listGenre = genreDAO.listAll();
		request.setAttribute("listGenre", listGenre);
		List<Artist> listArtist = artistDAO.listAll();
		request.setAttribute("listArtist", listArtist);
		
		String newPage = "album_form.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(newPage);
		requestDispatcher.forward(request, response);		
	}

	/**
	 * Checks if album with given title already exists (title must be unique).
	 * If not, generates and displays error message.
	 * Otherwise, retrieves all parameters from request object and creates new album object.
	 * AlbumDAO creates the record in the database (the model) and displays list of all albums in view.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void createAlbum() throws ServletException, IOException {
		String title = request.getParameter("title");
		
		Album existAlbum = albumDAO.findByTitle(title);
		
		if (existAlbum != null) {
			String message = "Could not create new album because the title '"
					+ title + "' already exists.";
			listAlbums(message);
			return;
		}
		
		Album newAlbum = new Album();
		readAlbumFields(newAlbum);
		
		Album createdAlbum = albumDAO.create(newAlbum);
		
		if (createdAlbum.getAlbumId() > 0) {
			String message = "A new album has been created successfully.";
			listAlbums(message);
		}
	}

	/**
	 * Reads all fields of an album form stored as parameters in request object.
	 * Sets fields of provided album object equal to the specified parameters.
	 * Parses releaseDate parameter into Date type.
	 * Parses albumImage into byte array.
	 * Uses artistDAO and genreDAO objects to retrieve the artist and genre objects corresponding to the specified id parameters.
	 * @param album to have fields set
	 * @throws ServletException
	 * @throws IOException
	 */
	/**
	 * @param album
	 * @throws ServletException
	 * @throws IOException
	 */
	public void readAlbumFields(Album album) throws ServletException, IOException {
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		float price = Float.parseFloat(request.getParameter("price"));
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date releaseDate = null;
		
		try {
			releaseDate = dateFormat.parse(request.getParameter("releaseDate"));
		} catch (ParseException ex) {
			ex.printStackTrace();
			throw new ServletException("Error parsing release date (format is dd/MM/yyyy)");
		}
				
		album.setTitle(title);
		album.setDescription(description);
		album.setReleaseDate(releaseDate);
		
		Integer genreId = Integer.parseInt(request.getParameter("genre"));
		Genre genre = genreDAO.get(genreId);
		album.setGenre(genre);
		
		Integer artistId = Integer.parseInt(request.getParameter("artist"));
		Artist artist = artistDAO.get(artistId);
		album.setArtist(artist);
		
		album.setPrice(price);
		
		Part part = request.getPart("albumImage");
		
		if (part != null && part.getSize() > 0) {
			long size = part.getSize();
			byte[] imageBytes = new byte[(int) size];
			
			InputStream inputStream = part.getInputStream();
			inputStream.read(imageBytes);
			inputStream.close();
			
			album.setImage(imageBytes);
		}
		
	}
	
	/**
	 * Obtains album object that corresponds to the id provided as a parameter in the request object.
	 * If album exists then display "album_form.jsp" in the view, setting the album object as an attribute in the response object
	 *  so that pre-existing album object information can auto-fill in form fields.
	 *  Lists of all genres and artists are generated and sent in the request object to be displayed as options for the user.
	 * If album does not exist then displays error message.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void editAlbum() throws ServletException, IOException {
		Integer albumId = Integer.parseInt(request.getParameter("id"));
		Album album = albumDAO.get(albumId);
		if (album != null) {
			List<Genre> listGenre = genreDAO.listAll();
			List<Artist> listArtist = artistDAO.listAll();
			
			request.setAttribute("album", album);
			request.setAttribute("listGenre", listGenre);
			request.setAttribute("listArtist", listArtist);
			
			String editPage = "album_form.jsp";
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(editPage);
			requestDispatcher.forward(request, response);	
		}
		else {
			String message = "Another user has already deleted album with ID " + albumId + ".";
			listAlbums(message);
		}
	}

	/**
	 * Finds the album objects that currently contains the specified albumId and title.
	 * Checks if an album object with the given title exists that contains a different albumId to that which was specified.
	 * If this is the case, generates and displays in the view an error message since title must be unique.
	 * Otherwise, modifies the object that contains the specified albumId by reading all the form fields stored as parameters in the request object and updates it in the database (the model).
	 * @throws ServletException
	 * @throws IOException
	 */
	public void updateAlbum() throws ServletException, IOException {
		Integer albumId = Integer.parseInt(request.getParameter("albumId"));
		String title = request.getParameter("title");
		
		Album existAlbum = albumDAO.get(albumId);
		Album albumByTitle = albumDAO.findByTitle(title);
		if (albumByTitle != null && existAlbum.getAlbumId() != albumByTitle.getAlbumId()) {
			String message = "Could not update album because there's another album with the same title.";
			listAlbums(message);
			return;
		}
		
		readAlbumFields(existAlbum);
		
		albumDAO.update(existAlbum);
		
		String message = "The album has been updated successfully.";
		listAlbums(message);
	}

	/**
	 * Obtains album object that corresponds to the id provided as a parameter in the request object.
	 * If this album does not exist then an error message is generated and displayed to the user in the view.
	 * Otherwise, the corresponding record is deleted in the database (the model) and the user informed by a message.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void deleteAlbum() throws ServletException, IOException {
		Integer albumId = Integer.parseInt(request.getParameter("id"));
		if (albumDAO.get(albumId) != null) {
			albumDAO.delete(albumId);
			
			String message = "The album has been deleted successfully.";
			listAlbums(message);	
		}
		else {
			String message = "Another album has already deleted user with ID " + albumId + ".";
			listAlbums(message);
		}
	}

	/**
	 * Obtains the genreId provided as a parameter in the request object.
	 * Searches the database (the model) for a list of all albums containing the specified genreId as a foreign key.
	 * Sets this list and the genre object corresponding to the genreId as attributes of the request object.
	 * These are used to generate the "albums_list_by_genre.jsp" page.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void listAlbumsByGenre() throws ServletException, IOException {
		int genreId = Integer.parseInt(request.getParameter("id"));
		List<Album> listAlbums = albumDAO.listByGenre(genreId);
		Genre genre = genreDAO.get(genreId);
		
		request.setAttribute("listAlbums", listAlbums);
		request.setAttribute("genre", genre);
		
		String listPage = "frontend/albums_list_by_genre.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
		requestDispatcher.forward(request, response);		
	}
	
	/**
	 * Obtains the artistId provided as a parameter in the request object.
	 * Searches the database (the model) for a list of all albums containing the specified artistId as a foreign key.
	 * Sets this list and the genre object corresponding to the artistId as attributes of the request object.
	 * These are used to generate the "albums_list_by_artist.jsp" page.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void listAlbumsByArtist() throws ServletException, IOException {
		int artistId = Integer.parseInt(request.getParameter("id"));
		List<Album> listAlbums = albumDAO.listByArtist(artistId);
		Artist artist = artistDAO.get(artistId);
		
		request.setAttribute("listAlbums", listAlbums);
		request.setAttribute("artist", artist);
		
		String listPage = "frontend/albums_list_by_artist.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
		requestDispatcher.forward(request, response);		
	}

	/**
	 * Obtains album object that corresponds to the id provided as a parameter in the request object.
	 * These are used to generate the "albums_detail.jsp" page.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void viewAlbumDetail() throws ServletException, IOException {
		Integer albumId = Integer.parseInt(request.getParameter("id"));
		Album album = albumDAO.get(albumId);
		
		request.setAttribute("album", album);

		String detailPage = "frontend/album_detail.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(detailPage);
		requestDispatcher.forward(request, response);
	}

	/**
	 * Searches database for all albums that contain a given keyword.
	 * If no keyword is given then lists all albums available.
	 * Uses list of results to generate "search_result.jsp" and display in customer's view.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void search(boolean filter) throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
		String sortBy = request.getParameter("sortBy");
		request.setAttribute("sortBy", sortBy);
		List<Album> result = null; 
				
		if (keyword.equals("")) {
			result = albumDAO.listAll();
		} 
		if (!filter) {
			System.out.println("A");
			result = albumDAO.search(keyword, "");
		} else {
			result = albumDAO.search(keyword, sortBy);
			System.out.println("B");
		}
		
		ArrayList<Genre> listGenre = new ArrayList<Genre>();
		ArrayList<Artist> listArtist = new ArrayList<Artist>();
		float minPrice = Float.MAX_VALUE;
		float maxPrice = 0f;
		
		for (Album album : result) {
			if (!listGenre.contains(album.getGenre())) {
				listGenre.add(album.getGenre());
			}
			if (!listArtist.contains(album.getArtist())) {
				listArtist.add(album.getArtist());
			}
			if (album.getPrice() < minPrice) {
				minPrice = album.getPrice();
			}
			if (album.getPrice() > maxPrice) {
				maxPrice = album.getPrice();
			}
		}
		request.setAttribute("listGenre", listGenre);
		request.setAttribute("listArtist", listArtist);
		request.setAttribute("minPrice", minPrice);
		request.setAttribute("maxPrice", maxPrice);
		
		if (filter) {
			ArrayList<Album> removeList = new ArrayList<Album>();
			
			String genreId = request.getParameter("genre");
			if (!genreId.equals("-1")) {
				for (Album album : result) {
					if (!album.getGenre().getGenreId().toString().equals(genreId)) {
						removeList.add(album);
					}
				}
			}
			result.removeAll(removeList);
			removeList = new ArrayList<Album>();
			
			String artistId = request.getParameter("artist");
			if (!artistId.equals("-1")) {
				for (Album album : result) {
					if (!album.getArtist().getArtistId().toString().equals(artistId)) {
						removeList.add(album);
					}
				}
			}
			result.removeAll(removeList);
			removeList = new ArrayList<Album>();
			
			minPrice = Float.parseFloat(request.getParameter("minPrice"));
			maxPrice = Float.parseFloat(request.getParameter("maxPrice"));
			for (Album album : result) {
				if (album.getPrice() < minPrice || album.getPrice() > maxPrice) {
					removeList.add(album);
				}
			}
			result.removeAll(removeList);
			
			request.removeAttribute("minPrice");
			request.removeAttribute("maxPrice");
			request.setAttribute("minPrice", minPrice);
			request.setAttribute("maxPrice", maxPrice);
			
			request.setAttribute("genreId", genreId);
			request.setAttribute("artistId", artistId);
		}
		
		request.setAttribute("keyword", keyword);
		request.setAttribute("result", result);
		
		String resultPage = "frontend/search_result.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(resultPage);
		requestDispatcher.forward(request, response);		
	}
}
