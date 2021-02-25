package com.cratediggers.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cratediggers.dao.AlbumDAO;
import com.cratediggers.dao.CustomerDAO;
import com.cratediggers.dao.ReviewDAO;
import com.cratediggers.entity.Album;
import com.cratediggers.entity.Customer;
import com.cratediggers.recommendations.Matrix;

public class RecommendationServices {
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private AlbumDAO albumDAO;
	private CustomerDAO customerDAO;
	private ReviewDAO reviewDAO;

	public RecommendationServices(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
		albumDAO = new AlbumDAO();
		customerDAO = new CustomerDAO();
		reviewDAO = new ReviewDAO();
	}
	
	public void recommend() throws ServletException, IOException {
		int customerId;
		int albumId;
		Integer rating;
		
		List<Album> albums = albumDAO.listAll();
		List<Customer> customers = customerDAO.listAll();
		//List<Review> reviews = reviewDAO.listAll();
		
		Matrix ratings = new Matrix(customers.size(), albums.size());
		
		for (int i = 0; i < customers.size(); i++) {
			for (int j = 0; j < albums.size(); j++) {
				customerId = customers.get(i).getCustomerId();
				albumId = albums.get(j).getAlbumId();
				try {
					rating = reviewDAO.findByCustomerAndAlbum(customerId, albumId).getRating();
				} catch (NullPointerException ex) {
					rating = 0;
				}
				ratings.set((double) rating, i, j);
				
			}
		}
		
		ratings.print();
		
		Map<String, Matrix> factors = ratings.factorise(4, 50000, 0.005, 0.02, 0.0001, 0, 5);
		Matrix p = factors.get("A");
		Matrix q = factors.get("B");
		Matrix recommendations = p.dotProduct(q.getTranspose());
		
		recommendations.print();
		
		Customer customer = (Customer) request.getSession().getAttribute("loggedCustomer");
		int customerIndex = -1;
		for (int i = 0; i < customers.size(); i++) {
			System.out.println(customers.get(i).getCustomerId());
			if ((int) customers.get(i).getCustomerId() == (int) customer.getCustomerId()) {
				customerIndex = i;
			}
		}
		System.out.println(customerIndex);
		double[] predictions = recommendations.getRow(customerIndex);
		for (int i = 0; i < predictions.length; i++) {
			if (predictions[i] == ratings.get(customerIndex, i)) {
				predictions[i] = 0;
			}
		}
		
		boolean swapped;
        for (int i = 0; i < predictions.length-1; i++) {
        	swapped = false;
            for (int j = 0; j < predictions.length-i-1; j++) { 
                if (predictions[j] < predictions[j+1]) { 
                    double temp1 = predictions[j]; 
                    Album temp2 = albums.get(j);
                    predictions[j] = predictions[j+1]; 
                    albums.set(j, albums.get(j+1));
                    predictions[j+1] = temp1; 
                    albums.set(j+1, temp2);
                    swapped = true;
                }
            }
        	if (!swapped) {
        		break;
        	}
        }
        
        System.out.println(Arrays.toString(predictions));
        request.setAttribute("recommendations", albums.subList(0, 8));
        RequestDispatcher dispatcher = request.getRequestDispatcher("frontend/recommendations.jsp");
		dispatcher.forward(request, response);
	}	
	
}
