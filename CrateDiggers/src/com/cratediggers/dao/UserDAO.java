package com.cratediggers.dao;

import java.util.ArrayList;
import java.util.List;
import com.cratediggers.entity.Users;

/**
 * User data access object class used to implement database interaction for user table.
 * Extends JpaDAO and implements GenericDAO.
 */
public class UserDAO extends JpaDAO<Users> implements GenericDAO<Users> {

	/**
	 * Constructs a UserDAO object.
	 */
	public UserDAO() {
	}

	
	/**
	 * Creates a new record in database by persisting a provided user entity object.
	 * Encrypts password.
	 * @param user object to be stored in the database
	 * @return the created user object
	 */
	public Users create(Users user) {
		String encryptedPassword = HashGenerator.generateHash(user.getPassword());
		user.setPassword(encryptedPassword);	
		return super.create(user);
	}
	
	/**
	 * Updates a record within the database by merging a provided user entity object.
	 * @param user object to be updated
	 * @return the updated user object
	 */
	@Override
	public Users update(Users user) {
		return super.update(user);
	}

	/**
	 * Retrieves a specific user record within the database by userId. Binary search.
	 * @param userId of record to be retrieved
	 * @return User object corresponding to found record
	 */
	@Override
	public Users get(Object userId) {
		//return super.find(Users.class, userId);
		ArrayList<Users> listUsers = (ArrayList<Users>) super.findWithNamedQuery("Users.findAll");
		int left = 0, right = listUsers.size() - 1; 
		
        while (left <= right) { 
        	int mid = left + (right - left) / 2; 
            
            if (listUsers.get(mid).getUserId().equals((int) userId)) {
            	return listUsers.get(mid); 
            }
            else if (listUsers.get(mid).getUserId() < (int) userId) {
            	left = mid + 1; 
            } 
            else {
            	right = mid - 1; 
            }   
        } 
  
        return null;  
	}
	
	/**
	 * Retrieves a specific user record within the database by email. Linear search.
	 * @param email of record to be retrieved
	 * @return User object corresponding to found record
	 */
	public Users findByEmail(String email) {
		//List<Users> listUsers = super.findWithNamedQuery("Users.findByEmail", "email", email);
		ArrayList<Users> listUsers = (ArrayList<Users>) super.findWithNamedQuery("Users.findAll");
        for (int i = 0; i < listUsers.size(); i++) {
            if (listUsers.get(i).getEmail().equals(email)) {
                return listUsers.get(i);
            }
        }
        return null;
	}
	
	/**
	 * Checks if user with a particular email has given password.
	 * @param email of user to be checked
	 * @param password to be compared
	 * @return true if password matches, false if not
	 */
	public boolean checkLogin(String email, String password) {
		Users user = findByEmail(email);
		if (user != null && user.getPassword().equals(HashGenerator.generateHash(password))) {
			return true;
		}
		return false;
	}

	/**
	 * Deletes a specific record within the database.
	 * @param userId of record to be deleted
	 */
	@Override
	public void delete(Object userId) {
		super.delete(Users.class, userId);
	}

	/**
	 * Lists all users stored in database alphabetically.
	 * @return List of all users
	 */
	@Override
	public List<Users> listAll() {
		ArrayList<Users> listUsers = (ArrayList<Users>) super.findWithNamedQuery("Users.findAll");
		return sortAlphabetically(listUsers);
		//return super.findWithNamedQuery("Users.findAll");
	}

	/**
	 * Counts the number of users stored in the database.
	 * @return the number of users stored in the database
	 */
	@Override
	public long count() {
		int total = 0;
		ArrayList<Users> listUsers = (ArrayList<Users>) super.findWithNamedQuery("Users.findAll");
        for (int i = 0; i < listUsers.size(); i++) {
            total += 1;
        }
        return total;
	}
	
	/**
	 * Sorts a list of users alphabetically. Utilises bubble sort.
	 * @param listUsers to be sorted
	 * @return Sorted list
	 */
	private ArrayList<Users> sortAlphabetically(ArrayList<Users> listUsers) {
		String name1, name2;
		boolean swapped;
		
        for (int i = 0; i < listUsers.size()-1; i++) {
        	swapped = false;

            for (int j = 0; j < listUsers.size()-i-1; j++) {
            	name1 = listUsers.get(j).getFullName();
        		name2 = listUsers.get(j+1).getFullName();
        		
        		if (super.greaterThanString(name1, name2)) {
        			Users temp = listUsers.get(j); 
                    listUsers.set(j, listUsers.get(j+1)); 
                    listUsers.set(j+1, temp); 
                    swapped = true;
        		}
            }
            
            if (swapped == false) {
                break; 
            }
        }
        return listUsers;
	}

}
