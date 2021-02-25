package com.cratediggers.controller.admin.review;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cratediggers.service.ReviewServices;

/**
 * Servlet that handles server requests made to "/admin/update_review" URL.
 */
@WebServlet("/admin/update_review")
public class UpdateReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateReviewServlet() {
		super();
	}

	/**
	 * Delegates business logic to relevant, modularised reviewServices method.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ReviewServices reviewServices = new ReviewServices(request, response);
		reviewServices.updateReview();
	}

}
