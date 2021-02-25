package com.cratediggers.service;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cratediggers.dao.CustomerDAO;
import com.cratediggers.dao.HashGenerator;
import com.cratediggers.entity.Customer;

/**
 * Handles all business logic for the customer module.
 * Follows model-view-controller design architecture by ensuring that the model and view never interact directly.
 */
public class CustomerServices {
	/**
	 * CustomerDAO object used to interact with the database (the model).
	 */
	private CustomerDAO customerDAO;
	/**
	 * Contains parameters received by a HTTP request.
	 */
	private HttpServletRequest request;
	/**
	 * Used to store attributes that are utilised for JSP generation.
	 */
	private HttpServletResponse response;
	
	/**
	 * Constructs an CustomerServices object containing the request and response objects provided by a servlet.
	 * Constructs new customerDAO objects used to interact with the database (the model).
	 * @param request object that contains parameters received in HTTP request made to the server.
	 * @param response object that is used to store attributes which are utilised for JSP generation.
	 */
	public CustomerServices(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
		this.customerDAO = new CustomerDAO();
	}
	
	/**
	 * Retrieves a list of all customers from database.
	 * Forwards the list and a specified message onto "customer_list.jsp" to display to users.
	 * @param message to be displayed at top of JSP page
	 * @throws ServletException
	 * @throws IOException
	 */
	public void listCustomers(String message) throws ServletException, IOException {
		List<Customer> listCustomer = customerDAO.listAll();
		
		if (message != null) {
			request.setAttribute("message", message);
		}
		
		request.setAttribute("listCustomer", listCustomer);
		
		String listPage = "customer_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
		requestDispatcher.forward(request, response);		
	}
	
	/**
	 * Provides polymorphism so that listCustomers(String message) can be called without a specified message.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void listCustomers() throws ServletException, IOException {
		listCustomers(null);
	}
	
	/**
	 * Retrieves email parameters from request object and find the customer object with the given email.
	 * Checks if customer with given email already exists (email must be unique).
	 * If customer can be created then retrieves all parameters from request object and creates new customer object.
	 * CustomerDAO creates the record in the database (the model) and displays list of all customers in view.
	 * If not, generates and displays error message.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void createCustomer() throws ServletException, IOException {
		String email = request.getParameter("email");
		Customer existCustomer = customerDAO.findByEmail(email);
		
		if (existCustomer != null) {
			String message = "Could not create new customer: the email "
					+ email + " is already registered by another customer.";
			listCustomers(message);
			
		} else {
			Customer newCustomer = new Customer();
			updateCustomerFieldsFromForm(newCustomer);
			customerDAO.create(newCustomer);
			
			String message = "New customer has been created successfully.";
			listCustomers(message);
			
		}
		
	}
	
	/**
	 * Reads all fields of a customer form stored as parameters in request object.
	 * Sets fields of provided customer object equal to the specified parameters.
	 * Encrypts password field.
	 * @param album to have fields set
	 * @throws ServletException
	 * @throws IOException
	 */
	private void updateCustomerFieldsFromForm(Customer customer) {
		String email = request.getParameter("email");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		String addressLine1 = request.getParameter("address1");
		String addressLine2 = request.getParameter("address2");
		String town = request.getParameter("town");
		String county = request.getParameter("county");
		String postcode = request.getParameter("postcode");
		
		if (email != null && !email.equals("")) {
			customer.setEmail(email);
		}
		
		customer.setFirstName(firstname);
		customer.setLastName(lastname);
		
		if (password != null && !password.equals("")) {
			customer.setPassword(HashGenerator.generateHash(password));
		}
		
		customer.setPhone(phone);
		customer.setAddressLine1(addressLine1);
		customer.setAddressLine2(addressLine2);
		customer.setTown(town);
		customer.setCounty(county);
		customer.setPostcode(postcode);	
	}

	/**
	 * Retrieves email parameters from request object and find the customer object with the given email.
	 * Checks if customer with given email already exists (email must be unique).
	 * If customer can be created then retrieves all parameters from request object and creates new customer object.
	 * CustomerDAO creates the record in the database (the model) and displays link to login in view.
	 * If not, generates and displays error message.
	 * @throws ServletException
	 * @throws IOException
	 */
 	public void registerCustomer() throws ServletException, IOException {
		String email = request.getParameter("email");
		Customer existCustomer = customerDAO.findByEmail(email);
		String message = "";
		
		if (existCustomer != null) {
			message = "Could not register. The email "
					+ email + " is already registered by another customer.";
		} else {
			
			Customer newCustomer = new Customer();
			updateCustomerFieldsFromForm(newCustomer);			
			customerDAO.create(newCustomer);
			
			message = "You have registered successfully! Thank you.<br/>"
					+ "<a href='login'>Click here</a> to login";			
		}
		
		String messagePage = "frontend/message.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(messagePage);
		request.setAttribute("message", message);
		requestDispatcher.forward(request, response);		
	}	
	
 	/**
	 * Obtains customer object that corresponds to the id provided as a parameter in the request object.
	 * If album exists then display "customer_form.jsp" in the view, setting the customer object as an attribute in the response object
	 *  so that pre-existing customer object information can auto-fill in form fields.
	 * If album does not exist then displays error message.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void editCustomer() throws ServletException, IOException {
		Integer customerId = Integer.parseInt(request.getParameter("id"));
		Customer customer = customerDAO.get(customerId);
		if (customer != null) {
			customer.setPassword(null);
			request.setAttribute("customer", customer);
			
			//CommonUtility.generateCountryList(request);
			
			String editPage = "customer_form.jsp";
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(editPage);
			requestDispatcher.forward(request, response);	
		} 
		else {
			String message = "Another user has already deleted customer with ID " + customerId + ".";
			listCustomers(message);
		}
	}

	/**
	 * Finds the customer objects that currently contains the specified customerId and email.
	 * Checks if an customer object with the given email exists that contains a different customerId to that which was specified.
	 * If this is the case, generates and displays in the view an error message since email must be unique.
	 * Otherwise, modifies the object that contains the specified customerId by reading all the form fields stored as parameters in the request object and updates it in the database (the model).
	 * @throws ServletException
	 * @throws IOException
	 */
	public void updateCustomer() throws ServletException, IOException {
		Integer customerId = Integer.parseInt(request.getParameter("customerId"));
		String email = request.getParameter("email");
		
		Customer customerByEmail = customerDAO.findByEmail(email);
		String message = null;
		
		if (customerByEmail != null && customerByEmail.getCustomerId() != customerId) {
			message = "Could not update the customer ID " + customerId
					+ "because there's an existing customer having the same email.";
			
		} else {
			
			Customer customerById = customerDAO.get(customerId);
			updateCustomerFieldsFromForm(customerById);
			
			customerDAO.update(customerById);
			
			message = "The customer has been updated successfully.";
		}
		
		listCustomers(message);
	}

	/**
	 * Obtains customer object that corresponds to the id provided as a parameter in the request object.
	 * If this customer does not exist then an error message is generated and displayed to the user in the view.
	 * The corresponding record is deleted in the database (the model) and the user informed by a message.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void deleteCustomer() throws ServletException, IOException {
		Integer customerId = Integer.parseInt(request.getParameter("id"));
		if (customerDAO.get(customerId) != null) {
			customerDAO.delete(customerId);
			
			String message = "The customer has been deleted successfully.";
			listCustomers(message);
		}
		else {
			String message = "Another user has already deleted customer with ID " + customerId + ".";
			listCustomers(message);
		}
	}

	/**
	 * Redirects the customer to the "login.jsp" page.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void showLogin() throws ServletException, IOException {
		String loginPage = "frontend/login.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(loginPage);
		dispatcher.forward(request, response);		
	}

	/**
	 * Retrieves email, and password parameters from request object.
	 * Checks whether login should be allowed (whether customer record with given email contains the specified password).
	 * If true then sets the session loggedCustomer attribute so that the customer can bypass the site's customerLoginFilter.
	 * Displays the page that the customer was redirected from by the customerLoginFilter or the customer profile page if not redirected.
	 * If false, displays login failure message and displays "login.jsp" page to user again.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doLogin() throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		Customer customer = customerDAO.checkLogin(email, password);
		
		if (customer == null) {
			String message = "Login failed. Please check your email and password.";
			request.setAttribute("message", message);
			showLogin();
			
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("loggedCustomer", customer);
			
			Object objRedirectURL = session.getAttribute("redirectURL");
			
			if (objRedirectURL != null) {
				String redirectURL = (String) objRedirectURL;
				session.removeAttribute("redirectURL");
				response.sendRedirect(redirectURL);
			} else {
				showCustomerProfile();
			}
		}
	}
	
	/**
	 * Redirects the customer to the "customer_profile.jsp" page.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void showCustomerProfile() throws ServletException, IOException {
		String profilePage = "frontend/customer_profile.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(profilePage);
		dispatcher.forward(request, response);
	}

	/**
	 * Redirects the customer to the "edit_profile.jsp" page.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void showCustomerProfileEditForm() throws ServletException, IOException {
		//CommonUtility.generateCountryList(request);
		String editPage = "frontend/edit_profile.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(editPage);
		dispatcher.forward(request, response);		
	}

	/**
	 * Retrieves logged in customer object from session.
	 * Updates the object fields according to form fields stored as parameter in request object.
	 * Updates the corresponding record in the database (the model).
	 * @throws ServletException
	 * @throws IOException
	 */
	public void updateCustomerProfile() throws ServletException, IOException {
		Customer customer = (Customer) request.getSession().getAttribute("loggedCustomer");
		updateCustomerFieldsFromForm(customer);
		customerDAO.update(customer);
		
		showCustomerProfile();
		
	}

	/**
	 * Redirects user to "customer_form.jsp" page.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void newCustomer() throws ServletException, IOException {
		//CommonUtility.generateCountryList(request);
		
		String customerForm = "customer_form.jsp";
		request.getRequestDispatcher(customerForm).forward(request, response);
		
	}

	/**
	 * Redirects customer to "register_form.jsp" page.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void showCustomerRegistrationForm() throws ServletException, IOException {
		//CommonUtility.generateCountryList(request);
		
		String registerForm = "frontend/register_form.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(registerForm);
		dispatcher.forward(request, response);		
	}
}
