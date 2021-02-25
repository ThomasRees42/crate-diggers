package com.cratediggers.dao;

import java.util.ArrayList;
import java.util.List;

import com.cratediggers.entity.Genre;

/**
 * Genre data access object class used to implement database interaction for genre table.
 * Extends JpaDAO and implements GenericDAO.
 */
public class GenreDAO extends JpaDAO<Genre> implements GenericDAO<Genre> {

	/**
	 * Constructs a genreDAO object.
	 */
	public GenreDAO() {
	}

	/**
	 * Creates a new record in database by persisting a provided genre entity object.
	 * @param genre object to be stored in the database
	 * @return The created genre object
	 */
	@Override
	public Genre create(Genre genre) {
		return super.create(genre);
	}

	/**
	 * Updates a record within the database by merging a provided genre entity object.
	 * @param genre object to be updated
	 * @return The updated genre object
	 */
	@Override
	public Genre update(Genre genre) {
		return super.update(genre);
	}

	/**
	 * Retrieves a specific genre record within the database by genreId. Binary search.
	 * @param genreId of record to be retrieved
	 * @return Genre object corresponding to found record
	 */
	@Override
	public Genre get(Object id) {
		//return super.find(Genre.class, id);
		ArrayList<Genre> listGenres = (ArrayList<Genre>) super.findWithNamedQuery("Genre.findAll");
		int left = 0, right = listGenres.size() - 1; 
		
        while (left <= right) { 
        	int mid = left + (right - left) / 2; 
            
            if (listGenres.get(mid).getGenreId().equals((int) id)) {
            	return listGenres.get(mid); 
            }
            else if (listGenres.get(mid).getGenreId() < (int) id) {
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
		super.delete(Genre.class, id);
	}

	/**
	 * Lists all genres stored in database alphabetically.
	 * @return List of all genres
	 */
	@Override
	public List<Genre> listAll() {
		ArrayList<Genre> listGenres = (ArrayList<Genre>) super.findWithNamedQuery("Genre.findAll");
		return sortAlphabetically(listGenres);
		//return super.findWithNamedQuery("Genre.findAll");
	}

	/**
	 * Counts the number of genres stored in the database.
	 * @return the number of genres stored in the database
	 */
	@Override
	public long count() {
		return super.findWithNamedQuery("Genre.findAll").size();
	}
	
	/**
	 * Retrieves a specific genre record within the database by genreName. Linear search.
	 * @param genreName of record to be retrieved
	 * @return Genre object corresponding to found record
	 */
	public Genre findByName(String genreName) {
		ArrayList<Genre> listGenres = (ArrayList<Genre>) super.findWithNamedQuery("Genre.findAll");
        for (int i = 0; i < listGenres.size(); i++) {
            if (listGenres.get(i).getName().equals(genreName)) {
                return listGenres.get(i);
            }
        }
        return null;
	}
	
	/**
	 * Sorts a list of genres alphabetically. Utilises insertion sort.
	 * @param listGenres to be sorted
	 * @return Sorted list
	 */
	private ArrayList<Genre> sortAlphabetically(ArrayList<Genre> listGenres) {
		Genre key; 
		int j;
		
        for (int i = 0; i < listGenres.size(); ++i) {
        	key = listGenres.get(i);
        	j = i - 1;
        	
        	while (j >= 0 && super.greaterThanString(listGenres.get(j).getName(), key.getName())) {
        		listGenres.set(j + 1, listGenres.get(j));
        		j--;
        	}
        	listGenres.set(j + 1, key);
        }
        return listGenres;
	}
}
