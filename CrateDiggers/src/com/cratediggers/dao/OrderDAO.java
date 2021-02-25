package com.cratediggers.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cratediggers.entity.AlbumOrder;

/**
 * Order data access object class used to implement database interaction for albumOrder and orderDetail tables.
 * Extends JpaDAO and implements GenericDAO.
 */
public class OrderDAO extends JpaDAO<AlbumOrder> implements GenericDAO<AlbumOrder> {

	/**
	 * Creates a new record in database by persisting a provided albumOrder entity object.
	 * Generates the date for the orderDate field.
	 * Sets the status field to "processing".
	 * @param albumOrder object to be stored in the database
	 * @return The created albumOrder object
	 */
	@Override
	public AlbumOrder create(AlbumOrder order) {
		order.setOrderDate(new Date());		
		order.setStatus("Processing");
		
		return super.create(order);
	}

	/**
	 * Updates a record within the database by merging a provided albumOrder entity object.
	 * @param albumOrder object to be updated
	 * @return The updated albumOrder object
	 */
	@Override
	public AlbumOrder update(AlbumOrder order) {
		return super.update(order);
	}

	/**
	 * Retrieves a specific albumOrder record within the database by orderId. Binary search.
	 * @param orderId of record to be retrieved
	 * @return AlbumOrder object corresponding to found record
	 */
	@Override
	public AlbumOrder get(Object orderId) {
		//return super.find(AlbumOrder.class, orderId);
		ArrayList<AlbumOrder> listOrders = (ArrayList<AlbumOrder>) super.findWithNamedQuery("AlbumOrder.findAll");
		int left = 0, right = listOrders.size() - 1; 
		
        while (left <= right) { 
        	int mid = left + (right - left) / 2; 
            
            if (listOrders.get(mid).getOrderId().equals((int) orderId)) {
            	return listOrders.get(mid); 
            }
            else if (listOrders.get(mid).getOrderId() < (int) orderId) {
            	left = mid + 1; 
            } 
            else {
            	right = mid - 1; 
            }   
        } 
  
        return null; 
	}

	/**
	 * Retrieves a specific albumOrder record within the database by orderId and customerId
	 * @param orderId of record to be retrieved
	 * @param customerId foreign key of record to be retrieved
	 * @return AlbumOrder object corresponding to found record
	 */
	public AlbumOrder get(Integer orderId, Integer customerId) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("orderId", orderId);
		parameters.put("customerId", customerId);
		
		List<AlbumOrder> result = super.findWithNamedQuery("AlbumOrder.findByIdAndCustomer", parameters );
		
		if (!result.isEmpty()) {
			return result.get(0);
		}
		return null;
	}
	
	/**
	 * Deletes a specific record within the database.
	 * @param orderId of record to be deleted
	 */
	@Override
	public void delete(Object orderId) {
		super.delete(AlbumOrder.class, orderId);		
	}

	/**
	 * Lists all albumOrders stored in database.
	 * @return List of all albumOrders
	 */
	@Override
	public List<AlbumOrder> listAll() {
		return super.reverseList(findWithNamedQuery("AlbumOrder.findAll"));
	}

	/**
	 * Counts the number of albumOrders stored in the database.
	 * @return the number of albumOrders stored in the database
	 */
	@Override
	public long count() {	
		return super.countWithNamedQuery("AlbumOrder.countAll");
	}
	
	/**
	 * Retrieves a list of albumOrder records within the database that contain a specific customer. 
	 * @param customerId foreign key to be searched for
	 * @return List of albumOrder objects that contain the specified customerId foreign key
	 */
	public List<AlbumOrder> listByCustomer(Integer customerId) {
		ArrayList<AlbumOrder> listOrders = (ArrayList<AlbumOrder>) listAll();
		ArrayList<AlbumOrder> customerOrders = new ArrayList<AlbumOrder>();
		for (int i = 0; i < listOrders.size(); i++) {
            if (listOrders.get(i).getCustomer().getCustomerId().equals(customerId)) {
                customerOrders.add(listOrders.get(i));
            }
        }
		return customerOrders;
		//return super.findWithNamedQuery("AlbumOrder.findByCustomer", 
		//		"customerId", customerId);
	}
	
	/**
	 * Lists the 3 most recent albumOrders only by order date.
	 * @return List of most recent albumOrder objects of size 3
	 */
	public List<AlbumOrder> listMostRecentSales() {
		ArrayList<AlbumOrder> ordersList =  (ArrayList<AlbumOrder>) listAll();
		return ordersList.subList(0, 3);
		//return super.findWithNamedQuery("AlbumOrder.findAll", 0, 3);
	}
	
}
