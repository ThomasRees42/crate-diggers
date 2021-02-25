package com.cratediggers.controller.frontend.customer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cratediggers.service.RecommendationServices;

/**
 * Servlet implementation class PersonalisedRecommendationsServlet
 */
@WebServlet("/personalised_recommendations")
public class PersonalisedRecommendationsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PersonalisedRecommendationsServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RecommendationServices recommendationServices = new RecommendationServices(request, response);
		recommendationServices.recommend();
	}

}
