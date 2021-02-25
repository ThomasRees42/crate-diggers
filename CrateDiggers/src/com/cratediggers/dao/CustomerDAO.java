package com.cratediggers.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.cratediggers.entity.Customer;

/**
 * Customer data access object class used to implement database interaction for customer table.
 * Extends JpaDAO and implements GenericDAO.
 */
public class CustomerDAO extends JpaDAO<Customer> implements GenericDAO<Customer> {

	/**
	 * Creates a new record in database by persisting a provided customer entity object.
	 * Generates date registered on.
	 * @param customer object to be stored in the database
	 * @return The created customer object
	 */
	@Override
	public Customer create(Customer customer) {
		customer.setRegisterDate(new Date());
		return super.create(customer);
	}
	
	/**
	 * Updates a record within the database by merging a provided customer entity object.
	 * @param customer object to be updated
	 * @return The updated customer object
	 */
	@Override
	public Customer update(Customer customer) {
		return super.update(customer);
	}

	/**
	 * Retrieves a specific customer record within the database by customerId. Binary search.
	 * @param customerId of record to be retrieved
	 * @return Customer object corresponding to found record
	 */
	@Override
	public Customer get(Object id) {
		//return super.find(Customer.class, id);
		ArrayList<Customer> listCustomers = (ArrayList<Customer>) super.findWithNamedQuery("Customer.findAll");
		int left = 0, right = listCustomers.size() - 1; 
		
        while (left <= right) { 
        	int mid = left + (right - left) / 2; 
            
            if (listCustomers.get(mid).getCustomerId().equals((int) id)) {
            	return listCustomers.get(mid); 
            }
            else if (listCustomers.get(mid).getCustomerId() < (int) id) {
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
	 * @param id of record to be deleted
	 */
	@Override
	public void delete(Object id) {
		super.delete(Customer.class, id);
	}

	/**
	 * Lists all customers stored in database alphabetically.
	 * @return List of all customers
	 */
	@Override
	public List<Customer> listAll() {
		ArrayList<Customer> listCustomers = (ArrayList<Customer>) super.findWithNamedQuery("Customer.findAll");
		return sortAlphabetically(listCustomers);
	}

	/**
	 * Sorts a list of customers alphabetically. Utilises quick sort.
	 * @param listCustomers to be sorted
	 * @return Sorted list
	 */
	private ArrayList<Customer> sortAlphabetically(ArrayList<Customer> listCustomers) {
		return quickSort(listCustomers, 0, listCustomers.size()-1);
	}
	
	/**
	 * Recursively partitions and quicksorts list of customers.
	 * @param listCustomers to be sorted
	 * @param low starting index
	 * @param high ending index
	 * @return List of customers
	 */
	private ArrayList<Customer> quickSort(ArrayList<Customer> listCustomers, int low, int high) {
		if (low < high) { 
			int pivot = partition(listCustomers, low, high); 
			
			listCustomers = quickSort(listCustomers, low, pivot-1); 
			listCustomers = quickSort(listCustomers, pivot+1, high); 
		} 
		return listCustomers;
	}
	
	/**
	 * Takes last customer as pivot and places all customer last names that occur earlier alphabetically before it and the others after.
	 * @param listCustomers to be sorted
	 * @param low starting index
	 * @param high ending index
	 * @return List of customers
	 */
	private int partition(ArrayList<Customer> listCustomers, int low, int high) { 
		String pivot = listCustomers.get(high).getLastName();  
        int i = (low-1); 
        for (int j=low; j<high; j++) { 
            if (super.greaterThanString(pivot, listCustomers.get(j).getLastName())) { 
                i++; 
                Customer temp = listCustomers.get(i); 
                listCustomers.set(i, listCustomers.get(j)); 
                listCustomers.set(j, temp); 
            } 
        } 
        Customer temp = listCustomers.get(i+1); 
        listCustomers.set(i+1, listCustomers.get(high)); 
        listCustomers.set(high, temp); 
  
        return i+1; 
    } 
  

	/**
	 * Counts the number of customers stored in the database.
	 * @return the number of customers stored in the database
	 */
	@Override
	public long count() {
		return super.findWithNamedQuery("Customer.findAll").size();
	}

	/**
	 * Retrieves a specific customer record within the database by email. Linear search.
	 * @param email of record to be retrieved
	 * @return Customer object corresponding to found record
	 */
	public Customer findByEmail(String email) {
		//List<Customer> result = super.findWithNamedQuery("Customer.findByEmail", "email", email);
		ArrayList<Customer> listCustomers = (ArrayList<Customer>) super.findWithNamedQuery("Customer.findAll");
        for (int i = 0; i < listCustomers.size(); i++) {
            if (listCustomers.get(i).getEmail().equals(email)) {
                return listCustomers.get(i);
            }
        }
        return null;
	}
	
	/**
	 * Checks if customer with a particular email has given password.
	 * @param email of customer to be checked
	 * @param password to be compared
	 * @return true if password matches, false if not
	 */
	public Customer checkLogin(String email, String password) {
		Customer customer = findByEmail(email);
		if (customer != null && customer.getPassword().equals(HashGenerator.generateHash(password))) {
			return customer;
		}
		return null;
	}
	
}
