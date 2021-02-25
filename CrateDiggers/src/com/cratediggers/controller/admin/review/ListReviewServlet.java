package com.cratediggers.controller.admin.review;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cratediggers.service.ReviewServices;

/**
 * Servlet that handles server requests made to "/admin/list_review" URL.
 */
@WebServlet("/admin/list_review")
public class ListReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ListReviewServlet() {
		super();
	}

	/**
	 * Delegates business logic to relevant, modularised reviewServices method.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ReviewServices reviewServices = new ReviewServices(request, response);
		reviewServices.listAllReview();
	}

}
