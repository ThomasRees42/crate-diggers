package com.cratediggers.dao;

import java.util.ArrayList;
import java.util.List;

import com.cratediggers.entity.Artist;

/**
 * Artist data access object class used to implement database interaction for artist table.
 * Extends JpaDAO and implements GenericDAO.
 */
public class ArtistDAO extends JpaDAO<Artist> implements GenericDAO<Artist> {
	
	/**
	 * Constructs an artistDAO object.
	 */
	public ArtistDAO() {
	}

	/**
	 * Creates a new record in database by persisting a provided artist entity object.
	 * @param artist object to be stored in the database
	 * @return The created artist object
	 */
	@Override
	public Artist create(Artist artist) {
		return super.create(artist);
	}

	/**
	 * Updates a record within the database by merging a provided artist entity object.
	 * @param artist object to be updated
	 * @return The updated artist object
	 */
	@Override
	public Artist update(Artist artist) {
		return super.update(artist);
	}

	/**
	 * Retrieves a specific artist record within the database by artistId. Binary search.
	 * @param artistId of record to be retrieved
	 * @return Artist object corresponding to found record
	 */
	@Override
	public Artist get(Object id) {
		//return super.find(Artist.class, id);
		ArrayList<Artist> listArtists = (ArrayList<Artist>) super.findWithNamedQuery("Artist.findAll");
		int left = 0, right = listArtists.size() - 1; 
		
        while (left <= right) { 
        	int mid = left + (right - left) / 2; 
            
            if (listArtists.get(mid).getArtistId().equals((int) id)) {
            	return listArtists.get(mid); 
            }
            else if (listArtists.get(mid).getArtistId() < (int) id) {
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
		super.delete(Artist.class, id);
	}

	/**
	 * Lists all artists stored in database alphabetically.
	 * @return List of all artists
	 */
	@Override
	public List<Artist> listAll() {
		ArrayList<Artist> listArtists = (ArrayList<Artist>) super.findWithNamedQuery("Artist.findAll");
		return sortAlphabetically(listArtists);
		//return super.findWithNamedQuery("Artist.findAll");
	}

	/**
	 * Counts the number of artists stored in the database.
	 * @return the number of artists stored in the database
	 */
	@Override
	public long count() {
		int total = 0;
		ArrayList<Artist> listArtists = (ArrayList<Artist>) super.findWithNamedQuery("Artist.findAll");
        for (int i = 0; i < listArtists.size(); i++) {
            total += 1;
        }
        return total;
	}

	/**
	 * Retrieves a specific artist record within the database by artistName. Linear search.
	 * @param artistName of record to be retrieved
	 * @return Artist object corresponding to found record
	 */
	public Artist findByName(String artistName) {
		//List<Artist> result = super.findWithNamedQuery("Artist.findByName", "name", artistName);
		ArrayList<Artist> listArtists = (ArrayList<Artist>) super.findWithNamedQuery("Artist.findAll");
        for (int i = 0; i < listArtists.size(); i++) {
            if (listArtists.get(i).getName().equals(artistName)) {
                return listArtists.get(i);
            }
        }
        return null;
	}
	
	/**
	 * Sorts a list of artists alphabetically. Utilises selection sort.
	 * @param listArtists to be sorted
	 * @return Sorted list
	 */
	private ArrayList<Artist> sortAlphabetically(ArrayList<Artist> listArtists) {
        for (int i = 0; i < listArtists.size()-1; i++) {
            int min_idx = i; 
            for (int j = i + 1; j < listArtists.size(); j++) {
                if (super.greaterThanString(listArtists.get(min_idx).getName(), listArtists.get(j).getName())) {
                	min_idx = j;
                }
            }
            Artist temp = listArtists.get(min_idx); 
            listArtists.set(min_idx, listArtists.get(i)); 
            listArtists.set(i, temp); 
        } 
        return listArtists;
	}
}
