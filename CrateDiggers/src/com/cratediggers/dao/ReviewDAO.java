package com.cratediggers.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.cratediggers.entity.Review;

/**
 * Review data access object class used to implement database interaction for review table.
 * Extends JpaDAO and implements GenericDAO.
 */
public class ReviewDAO extends JpaDAO<Review> implements GenericDAO<Review> {

	/**
	 * Creates a new record in database by persisting a provided review entity object.
	 * @param review object to be stored in the database
	 * @return The created review object
	 */
	@Override
	public Review create(Review review) {
		review.setReviewTime(new Date());
		return super.create(review);
	}
	
	/**
	 * Retrieves a specific review record within the database by reviewId. Binary search.
	 * @param reviewId of record to be retrieved
	 * @return Review object corresponding to found record
	 */
	@Override
	public Review get(Object reviewId) {
		//return super.find(Review.class, reviewId);
		ArrayList<Review> listReviews = (ArrayList<Review>) super.findWithNamedQuery("Review.listAll");
		int left = 0, right = listReviews.size() - 1; 
		
        while (left <= right) { 
        	int mid = left + (right - left) / 2; 
            
            if (listReviews.get(mid).getReviewId().equals((int) reviewId)) {
            	return listReviews.get(mid); 
            }
            else if (listReviews.get(mid).getReviewId() < (int) reviewId) {
            	left = mid + 1; 
            } 
            else {
            	right = mid - 1; 
            }   
        } 
  
        return null;  
	}

	/**
	 * Deletes a specific record within the database.
	 * @param reviewId of record to be deleted
	 */
	@Override
	public void delete(Object reviewId) {
		super.delete(Review.class, reviewId);
	}

	/**
	 * Lists all reviews stored in database.
	 * @return List of all reviews
	 */
	@Override
	public List<Review> listAll() {
		return super.reverseList(findWithNamedQuery("Review.listAll"));
	}

	/**
	 * Counts the number of customers stored in the database.
	 * @return the number of customers stored in the database
	 */
	@Override
	public long count() {
		return listAll().size();
	}
	
	/**
	 * Retrieves a specific review record within the database using customer and album foreign keys. 
	 * Only retrieved if review contains both.
	 * Linear search.
	 * @param customerId foreign key of record to be retrieved
	 * @param albumId foreign key of record to be retrieved
	 * @return Customer object corresponding to found record
	 */
	public Review findByCustomerAndAlbum(Integer customerId, Integer albumId) {
		ArrayList<Review> listReviews = (ArrayList<Review>) listAll();
		for (Review review : listReviews) {
			if (review.getCustomer().getCustomerId().equals(customerId) && review.getAlbum().getAlbumId().equals(albumId)) {
				return review;
			}
		}
		
		return null;
	}
	
	/**
	 * Lists the 3 newest review only by review time.
	 * @return List of newest review objects of size 3
	 */
	public List<Review> listMostRecent() {
		ArrayList<Review> reviewsList =  (ArrayList<Review>) listAll();
		return reviewsList.subList(0, 3);
		//return super.findWithNamedQuery("Review.listAll", 0, 3).;
	}

}
