package com.cratediggers.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cratediggers.dao.AlbumDAO;
import com.cratediggers.dao.ReviewDAO;
import com.cratediggers.entity.Album;
import com.cratediggers.entity.Customer;
import com.cratediggers.entity.Review;

/**
 * Handles all business logic for the review module.
 * Follows model-view-controller design architecture by ensuring that the model and view never interact directly.
 */
public class ReviewServices {
	/**
	 * ReviewDAO object used to interact with the database (the model).
	 */
	private ReviewDAO reviewDAO;
	/**
	 * Contains parameters received by a HTTP request.
	 */
	private HttpServletRequest request;
	/**
	 * Used to store attributes that are utilised for JSP generation.
	 */
	private HttpServletResponse response;
	
	/**
	 * Constructs an ReviewServices object containing the request and response objects provided by a servlet.
	 * Constructs a new reviewDAO object used to interact with the database (the model).
	 * @param request object that contains parameters received in HTTP request made to the server.
	 * @param response object that is used to store attributes which are utilised for JSP generation.
	 */
	public ReviewServices(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
		this.reviewDAO = new ReviewDAO();
	}
	
	/**
	 * Provides polymorphism so that listAllReview(String message) can be called without a specified message.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void listAllReview() throws ServletException, IOException {
		listAllReview(null);
	}
	
	/**
	 * Retrieves a list of all reviews from database.
	 * Forwards the list and a specified message onto "review_list.jsp" to display to users.
	 * @param message to be displayed at top of JSP page
	 * @throws ServletException
	 * @throws IOException
	 */
	public void listAllReview(String message) throws ServletException, IOException {
		List<Review> listReviews = reviewDAO.listAll();
		
		request.setAttribute("listReviews", listReviews);
		
		if (message != null) {
			request.setAttribute("message", message);
		}
		
		String listPage = "review_list.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(listPage);
		dispatcher.forward(request, response);
		
	}

	/**
	 * Obtains review object that corresponds to the id provided as a parameter in the request object.
	 * If review exists then display "review_form.jsp" in the view, setting the review object as an attribute in the response object
	 *  so that pre-existing review object information can auto-fill in form fields.
	 * If review does not exist then displays error message.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void editReview() throws ServletException, IOException {
		Integer reviewId = Integer.parseInt(request.getParameter("id"));
		Review review = reviewDAO.get(reviewId);
		
		if (review != null) {
			request.setAttribute("review", review);
			
			String editPage = "review_form.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(editPage);
			dispatcher.forward(request, response);		
		}
		else {
			String message = "Another user has already deleted review with ID " + reviewId + ".";
			listAllReview(message);
		}
	}

	/**
	 * Retrieves reviewId, headline and comment parameter from request object.
	 * Finds the review object that contains the specified reviewId.
	 * Modifies the object by setting the new values of headline and comment and updates the corresponding record in the database (the model).
	 * @throws ServletException
	 * @throws IOException
	 */
	public void updateReview() throws ServletException, IOException {
		Integer reviewId = Integer.parseInt(request.getParameter("reviewId"));
		String headline = request.getParameter("headline");
		String comment = request.getParameter("comment");
		
		Review review = reviewDAO.get(reviewId);
		review.setHeadline(headline);
		review.setComment(comment);
		
		reviewDAO.update(review);
		
		String message = "The review has been updated successfully.";
		
		listAllReview(message);
		
	}

	/**
	 * Obtains review object that corresponds to the id provided as a parameter in the request object.
	 * If this review does not exist then an error message is generated and displayed to the user in the view.
	 * Otherwise, the corresponding record is deleted in the database (the model) and the user informed by a message.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void deleteReview() throws ServletException, IOException {
		Integer reviewId = Integer.parseInt(request.getParameter("id"));
		if (reviewDAO.get(reviewId) != null) {
			reviewDAO.delete(reviewId);
			
			String message = "The review has been deleted successfully.";
			
			listAllReview(message);
		}
		else {
			String message = "Another user has already deleted review with ID " + reviewId + ".";
			listAllReview(message);
		}
	}

	/**
	 * Obtains album object that corresponds to the id provided as a parameter in the request object.
	 * Sets this album as a session attribute and retrieves the currently logged in customer from session.
	 * Searches database for a review that already contain the specified albumId and the customerId of the current customer.
	 * If such a review exists then display that review to the customer in view, since a customer can only create one review per album.
	 * Otherwise, display "review_form.jsp" to customer.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void showReviewForm() throws ServletException, IOException {
		System.out.println(request.getParameter("album_id"));
		Integer albumId = Integer.parseInt(request.getParameter("album_id"));
		AlbumDAO albumDao = new AlbumDAO();
		Album album = albumDao.get(albumId);
		
		HttpSession session = request.getSession(); 
		session.setAttribute("album", album);
		
		Customer customer = (Customer) session.getAttribute("loggedCustomer");
		
		Review existReview = reviewDAO.findByCustomerAndAlbum(customer.getCustomerId(), albumId);
		
		String targetPage = "frontend/review_form.jsp";
		
		if (existReview != null) {
			request.setAttribute("review", existReview);
			targetPage = "frontend/review_info.jsp";
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(targetPage);
		dispatcher.forward(request, response);		
	}

	/**
	 * Retrieves all parameters from request object and creates new review object.
	 * Obtains and sets corresponding album object by albumId and customer object from session.
	 * ReviewDAO creates the record in the database (the model) and displays list of all reviews in view. 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void submitReview() throws ServletException, IOException {
		Integer albumId = Integer.parseInt(request.getParameter("albumId"));
		Integer rating = Integer.parseInt(request.getParameter("rating"));
		String headline = request.getParameter("headline");
		String comment = request.getParameter("comment");
		
		Review newReview = new Review();
		newReview.setHeadline(headline);
		newReview.setComment(comment);
		newReview.setRating(rating);
		
		Album album = new Album();
		album.setAlbumId(albumId);
		newReview.setAlbum(album);
		
		Customer customer = (Customer) request.getSession().getAttribute("loggedCustomer");
		newReview.setCustomer(customer);
		
		reviewDAO.create(newReview);
		
		String messagePage = "frontend/review_done.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(messagePage);
		dispatcher.forward(request, response);
		
		
	}
}
