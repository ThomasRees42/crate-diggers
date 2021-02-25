package com.cratediggers.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cratediggers.dao.HashGenerator;
import com.cratediggers.dao.UserDAO;
import com.cratediggers.entity.Users;

/**
 * Handles all business logic for the user module.
 * Follows model-view-controller design architecture by ensuring that the model and view never interact directly.
 */
public class UserServices {
	/**
	 * UserDAO object used to interact with the database (the model).
	 */
	private UserDAO userDAO;
	/**
	 * Contains parameters received by a HTTP request.
	 */
	private HttpServletRequest request;
	/**
	 * Used to store attributes that are utilised for JSP generation.
	 */
	private HttpServletResponse response;
	
	/**
	 * Constructs a UserServices object containing the request and response objects provided by a servlet.
	 * Constructs a new userDAO object used to interact with the database (the model).
	 * @param request object that contains parameters received in HTTP request made to the server.
	 * @param response object that is used to store attributes which are utilised for JSP generation.
	 */
	public UserServices(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		userDAO = new UserDAO();
	}

	/**
	 * Provides polymorphism so that listUser(String message) can be called without a specified message.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void listUser()
			throws ServletException, IOException {
		listUser(null);
	}

	/**
	 * Retrieves a list of all users from database.
	 * Forwards the list and a specified message onto "user_list.jsp" to display to users.
	 * @param message to be displayed at top of JSP page
	 * @throws ServletException
	 * @throws IOException
	 */
	public void listUser(String message)
			throws ServletException, IOException {
		List<Users> listUsers = userDAO.listAll();

		request.setAttribute("listUsers", listUsers);

		if (message != null) {
			request.setAttribute("message", message);
		}

		String listPage = "user_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);

		requestDispatcher.forward(request, response);

	}

	/**
	 * Retrieves email, fullName and password parameters from request object and generates a corresponding user object.
	 * Checks if user with given email already exists (email must be unique for login).
	 * If user can be created then the UserDAO creates the record in the database (the model) and displays list of all users in view.
	 * If not, generates and displays error message.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void createUser() throws ServletException, IOException {
		String email = request.getParameter("email");
		String fullName = request.getParameter("fullname");
		String password = request.getParameter("password");

		Users existUser = userDAO.findByEmail(email);
		
		if (existUser != null) {
			String message = "Could not create user. A user with email " 
								+ email + " already exists";
			request.setAttribute("message", message);
			RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
			dispatcher.forward(request, response);
			
		} else {		
			Users newUser = new Users(email, fullName, password);
			userDAO.create(newUser);
			listUser("New user created successfully");
		}

	}

	/**
	 * Obtains user object that corresponds to the id provided as a parameter in the request object.
	 * If user exists then display "user_form.jsp" in the view, setting the user object as an attribute in the response object
	 *  so that pre-existing user object information can auto-fill in form fields.
	 * If user does not exist then displays error message.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void editUser() throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("id"));
		Users user = userDAO.get(userId );
		
		if (user != null) {
			String editPage = "user_form.jsp";
			request.setAttribute("user", user);
			
			user.setPassword(null);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(editPage);
			requestDispatcher.forward(request, response);
		}
		else {
			String message = "Another user has deleted user with ID " + userId + ".";
			listUser(message);
		}
	}

	/**
	 * Retrieves userId, email, fullName and password parameters from request object.
	 * Finds the user objects that currently contains the specified userId and email.
	 * Checks if a user object with the given email exists that contains a different userId to that which was specified.
	 * If this is the case, generates and displays in the view an error message since email must be unique.
	 * Otherwise, modifies the object that contains the specified userId and updates it in the database (the model).
	 * Password field is encrypted.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void updateUser() throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("userId"));
		String email = request.getParameter("email");
		String fullName = request.getParameter("fullname");
		String password = request.getParameter("password");
		
		Users userById = userDAO.get(userId);
		
		Users userByEmail = userDAO.findByEmail(email);
		
		if (userByEmail != null && userByEmail.getUserId() != userById.getUserId()) {
			String message = "Could not update user. User with email " + email + " already exists.";
			request.setAttribute("message", message);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
			requestDispatcher.forward(request, response);			
			
		} else {
			userById.setEmail(email);
			userById.setFullName(fullName);
			
			if (password != null & !password.isEmpty()) {
				String encryptedPassword = HashGenerator.generateHash(password);
				userById.setPassword(encryptedPassword);				
			}
			
			userDAO.update(userById);


			String message = "User has been updated successfully";
			listUser(message);
		}
		
	}

	/**
	 * Obtains user object that corresponds to the id provided as a parameter in the request object.
	 * If this user does not exist then an error message is generated and displayed to the user in the view.
	 * Otherwise, the userId is checked to observe whether it is equal to 1.
	 * Record with userId cannot be deleted otherwise access to the site back-end could be lost so an error message is generated and displayed to the user in the view.
	 * For any other user object, the corresponding record is deleted in the database (the model) and the user informed by a message.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void deleteUser() throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("id"));
		if (userDAO.get(userId) != null) {
			if (userId == 1) {
				String message = "Default user cannot be deleted.";
				listUser(message);
			}
			else {
				userDAO.delete(userId);
				
				String message = "User has been deleted successfully";
				listUser(message);
			}
		}
		else {
			String message = "Another user has already deleted user with ID " + userId + ".";
			listUser(message);
		}
	}
	
	/**
	 * Retrieves email, and password parameters from request object.
	 * Checks whether login should be allowed (whether user record with given email contains the specified password).
	 * If true then sets the session useremail attribute so that the user can bypass the site's adminLoginFilter.
	 * Displays the admin home in the view.
	 * If false, displays login failure message and displays "login.jsp" page to user again.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void login() throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		boolean loginResult = userDAO.checkLogin(email, password);
		
		if (loginResult) {
			request.getSession().setAttribute("useremail", email);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/");
			dispatcher.forward(request, response);
			
		} else {
			String message = "Login failed!";
			request.setAttribute("message", message);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);			
		}
	}
}
