package com.cratediggers.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cratediggers.entity.Album;

/**
 * Album data access object class used to implement database interaction for album table.
 * Extends JpaDAO and implements GenericDAO.
 */
public class AlbumDAO extends JpaDAO<Album> implements GenericDAO<Album> {

	/**
	 * Constructs a AlbumDAO object.
	 */
	public AlbumDAO() {
	}

	/**
	 * Creates a new record in database by persisting a provided album entity object.
	 * @param user object to be stored in the database
	 * @return the created user object
	 */
	@Override
	public Album create(Album album) {
		album.setLastUpdateTime(new Date());
		return super.create(album);
	}

	/**
	 * Updates a record within the database by merging a provided album entity object.
	 * @param album object to be updated
	 * @return The updated album object
	 */
	@Override
	public Album update(Album album) {
		album.setLastUpdateTime(new Date());
		return super.update(album);
	}

	/**
	 * Retrieves a specific album record within the database by albumId. Binary search.
	 * @param albumId of record to be retrieved
	 * @return Album object corresponding to found record
	 */
	@Override
	public Album get(Object albumId) {
		//return super.find(Album.class, albumId);
		ArrayList<Album> listAlbums = (ArrayList<Album>) super.findWithNamedQuery("Album.findAll");
		int left = 0, right = listAlbums.size() - 1; 
		
        while (left <= right) { 
        	int mid = left + (right - left) / 2; 
            
            if (listAlbums.get(mid).getAlbumId().equals((int) albumId)) {
            	return listAlbums.get(mid); 
            }
            else if (listAlbums.get(mid).getAlbumId() < (int) albumId) {
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
	public void delete(Object albumId) {
		super.delete(Album.class, albumId);
	}

	/**
	 * Lists all albums stored in database alphabetically.
	 * @return List of all customers
	 */
	@Override
	public List<Album> listAll() {		
		ArrayList<Album> listAlbums = (ArrayList<Album>) super.findWithNamedQuery("Album.findAll");
		return sort(listAlbums, "alphabetical");
	}
	
	public List<Album> listAll(String sortBy) {		
		ArrayList<Album> listAlbums = (ArrayList<Album>) super.findWithNamedQuery("Album.findAll");
		System.out.println(sortBy);
		if (sortBy.equals("ascendingPrice") || sortBy.equals("descendingPrice") || sortBy.equals("bestSelling") || 
				sortBy.equals("highestRated") || sortBy.equals("newest")) {
			System.out.println(sortBy);
			return sort(listAlbums, sortBy);
		}
		return sort(listAlbums, "alphabetical");
	}
	
	/**
	 * Sorts a list of customers alphabetically. Utilises merge sort.
	 * @param listCustomers to be sorted
	 * @return Sorted list
	 */
	private ArrayList<Album> sort(ArrayList<Album> listAlbums, String type) {
		return mergeSort(listAlbums, 0, listAlbums.size() - 1, type);
	}
	
	/**
	 * Recursively finds the mid point to split and merge sorts halves separately. 
	 * Then merges each half of customer list in alphabetical.
	 * @param listAlbums to be sorted
	 * @param left starting index of list to be merge sorted
	 * @param right end index of list to be merge sorted
	 * @return Partially sorted list of customers if incomplete, full sorted list of customers when complete.
	 */
	private ArrayList<Album> mergeSort(ArrayList<Album> listAlbums, int left, int right, String type) {
		if (left < right) {
	        int mid = (left + right) / 2;
	        
	        listAlbums = mergeSort(listAlbums, left, mid, type);
	        listAlbums = mergeSort(listAlbums, mid + 1, right, type);
	 
	        return merge(listAlbums, left, mid, right, type);
		}
		return listAlbums;
	}
	
	
	/**
	 * Merges two sublists of listAlbums, ordered alphabetically.
	 * First subarray is from index left to mid.
	 * Second subarray is from index mid+1 to right.
	 * @param listAlbums to be sorted
	 * @param left specifies location of first subarray
	 * @param mid specifies location of both first and second subarrays
	 * @param right specifies location of second subarray
	 * @return Merged list of customers
	 */
	private ArrayList<Album> merge(ArrayList<Album> listAlbums, int left, int mid, int right, String type) {
 
        ArrayList<Album> leftList = new ArrayList<Album>();
        ArrayList<Album> rightList = new ArrayList<Album>();

        for (int i = 0; i < mid - left + 1; ++i) {
        	leftList.add(listAlbums.get(left + i));
        }
        for (int j = 0; j < right - mid; ++j) {
        	rightList.add(listAlbums.get(mid + 1 + j));
        }
        
        int i = 0, j = 0;
        
        int k = left;
        while (i < mid - left + 1 && j < right - mid) {
        	if (type.equals("alphabetical")) {
	        	String name1 = leftList.get(i).getTitle();
	        	String name2 = rightList.get(j).getTitle();
	        	
	            if (super.greaterThanString(name2, name1)) {
	            	listAlbums.set(k, leftList.get(i));
	                i++;
	            }
	            else {
	            	listAlbums.set(k, rightList.get(j));
	                j++;
	            }
	            k++;
        	} else if (type.equals("ascendingPrice")) {
	        	float price1 = leftList.get(i).getPrice();
	        	float price2 = rightList.get(j).getPrice();
	        	
	            if (price2 > price1) {
	            	listAlbums.set(k, leftList.get(i));
	                i++;
	            }
	            else {
	            	listAlbums.set(k, rightList.get(j));
	                j++;
	            }
	            k++;
        	} else if (type.equals("descendingPrice")) {
	        	float price1 = leftList.get(i).getPrice();
	        	float price2 = rightList.get(j).getPrice();
	        	
	            if (price1 > price2) {
	            	listAlbums.set(k, leftList.get(i));
	                i++;
	            }
	            else {
	            	listAlbums.set(k, rightList.get(j));
	                j++;
	            }
	            k++;
        	}	else if (type.equals("bestSelling")) {
	        	int orders1 = leftList.get(i).getSales();
	        	int orders2 = rightList.get(j).getSales();
	        	
	            if (orders2 < orders1) {
	            	listAlbums.set(k, leftList.get(i));
	                i++;
	            }
	            else {
	            	listAlbums.set(k, rightList.get(j));
	                j++;
	            }
	            k++;
        	}	else if (type.equals("highestRated")) {
	        	float rating1 = leftList.get(i).getAverageRating();
	        	float rating2 = rightList.get(j).getAverageRating();
	        	
	            if (rating2 < rating1) {
	            	listAlbums.set(k, leftList.get(i));
	                i++;
	            }
	            else {
	            	listAlbums.set(k, rightList.get(j));
	                j++;
	            }
	            k++;
        	} else if (type.equals("newest")) {
	        	Date date1 = leftList.get(i).getReleaseDate();
	        	Date date2 = rightList.get(j).getReleaseDate();
	        	
	            if (date2.before(date1)) {
	            	listAlbums.set(k, leftList.get(i));
	                i++;
	            }
	            else {
	            	listAlbums.set(k, rightList.get(j));
	                j++;
	            }
	            k++;
        	}
        }
 
        while (i < mid - left + 1) {
        	listAlbums.set(k, leftList.get(i));
            i++;
            k++;
        }
        
        while (j < right - mid) {
        	listAlbums.set(k, rightList.get(j));
            j++;
            k++;
        }
        
        return listAlbums;
	}

	/**
	 * Retrieves a specific album record within the database by title. Linear search.
	 * @param title of record to be retrieved
	 * @return Album object corresponding to found record
	 */
	public Album findByTitle(String title) {
		//List<Album> result = super.findWithNamedQuery("Album.findByTitle", "title", title);
		ArrayList<Album> listAlbums = (ArrayList<Album>) super.findWithNamedQuery("Album.findAll");
        for (int i = 0; i < listAlbums.size(); i++) {
            if (listAlbums.get(i).getTitle().equals(title)) {
                return listAlbums.get(i);
            }
        }
        return null;
	}
	
	/**
	 * Retrieves a list of album records within the database that contain a specific genre. Linear search.
	 * @param genreId foreign key to be searched for
	 * @return List of album objects that contain the specified genreId foreign key
	 */
	public List<Album> listByGenre(int genreId) {
		//return super.findWithNamedQuery("Album.findByGenre", "genId", genreId);
		ArrayList<Album> listAlbums = (ArrayList<Album>) listAll();
		ArrayList<Album> genreAlbums = new ArrayList<Album>();
		for (int i = 0; i < listAlbums.size(); i++) {
            if (listAlbums.get(i).getGenre().getGenreId().equals(genreId)) {
                genreAlbums.add(listAlbums.get(i));
            }
        }
		return genreAlbums;
	}
	
	/**
	 * Retrieves a list of album records within the database that contain a specific artist. Linear search.
	 * @param artistId foreign key to be searched for
	 * @return List of album objects that contain the specified artistId foreign key
	 */
	public List<Album> listByArtist(int artistId) {
		//return super.findWithNamedQuery("Album.findByGenre", "genId", genreId);
		ArrayList<Album> listAlbums = (ArrayList<Album>) super.reverseList(listAll("newest"));
		ArrayList<Album> artistAlbums = new ArrayList<Album>();
		for (int i = 0; i < listAlbums.size(); i++) {
            if (listAlbums.get(i).getArtist().getArtistId().equals(artistId)) {
                artistAlbums.add(listAlbums.get(i));
            }
        }
		return artistAlbums;
	}
	
	/**
	 * Searches all album records within the database for a list of album objects that contain a specified keyword.
	 * Album title, description, artist name and genre name are checked.
	 * If any contain the keyword specified the album object is appended to the returned list.
	 * Not case sensitive.
	 * @param keyword to be searched for in album records
	 * @return List of album records that contain the specified keyword
	 */
	public List<Album> search(String keyword, String sortBy) {
		//return super.findWithNamedQuery("Album.search", "keyword", keyword);
		String title, artist, genre, description;
		ArrayList<Album> listAlbums = (ArrayList<Album>) listAll(sortBy);
		ArrayList<Album> searchAlbums = new ArrayList<Album>();
		for (int i = 0; i < listAlbums.size(); i++) {
			title = listAlbums.get(i).getTitle();
			artist = listAlbums.get(i).getArtist().getName();
			genre = listAlbums.get(i).getGenre().getName();
			description = listAlbums.get(i).getDescription();
			if (contains(title, keyword) || contains(artist, keyword) || contains(genre, keyword) || contains(description, keyword)) {
				searchAlbums.add(listAlbums.get(i));
			}
		}
		return searchAlbums;
	}
	
	/**
	 * Checks if a given string contains a given substring.
	 * @param str string to be checked
	 * @param sub substring to be searched for
	 * @return true if string contains substring, false if not
	 */
	private boolean contains(String str, String sub) {
		boolean contains;
		for (int chr1 = 0; chr1 < str.length() - sub.length(); chr1++) {
			contains = true;
			for (int chr2 = 0; chr2 < sub.length(); chr2++) {
				if (str.toLowerCase().charAt(chr1 + chr2) != sub.toLowerCase().charAt(chr2)) {
					contains = false;
					break;
				}
			}
			if (contains) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Lists the 4 newest albums only by release date.
	 * @return List of newest album objects of size 4
	 */
	public List<Album> listNewAlbums() {	
		ArrayList<Album> listAlbums = (ArrayList<Album>) listAll("newest");
		return listAlbums.subList(0, 4);
		//return super.findWithNamedQuery("Album.listNew", 0, 4);
	}
	
	/**
	 * Counts the number of albums stored in the database.
	 * @return the number of albums stored in the database
	 */
	@Override
	public long count() {
		return super.findWithNamedQuery("Album.findAll").size();
	}
	
	/**
	 * Counts the number of albums with a specified genre that are stored in the database.
	 * @return the number of albums with a specified genre that are stored in the database
	 */
	public long countByGenre(int genreId) {
		//return super.countWithNamedQuery("Album.countByGenre", "genId", genreId);
		int total = 0;
		ArrayList<Album> listAlbums = (ArrayList<Album>) super.findWithNamedQuery("Album.findAll");
        for (Album album : listAlbums) {
        	if (album.getGenre().getGenreId() == genreId) {
            	total += 1;
        	}
        }
        return total;
	}
	
	/**
	 * Counts the number of albums with a specified artist that are stored in the database.
	 * @return the number of albums with a specified artist that are stored in the database
	 */
	public long countByArtist(int artistId) {
		//return super.countWithNamedQuery("Album.countByArtist", "artId", artistId);
		int total = 0;
		ArrayList<Album> listAlbums = (ArrayList<Album>) super.findWithNamedQuery("Album.findAll");
        for (Album album : listAlbums) {
        	if (album.getArtist().getArtistId() == artistId) {
            	total += 1;
        	}
        }
        return total;
	}

	/**
	 * Lists the 4 best-selling albums according to the number of orders made.
	 * @return List of best-selling album objects of size 4
	 */
	public List<Album> listBestSellingAlbums() {
		ArrayList<Album> listAlbums = (ArrayList<Album>) listAll("bestSelling");
		return listAlbums.subList(0, 4);
		//return super.findWithNamedQuery("OrderDetail.bestSelling", 0, 4);
	}	
	
	/**
	 * Lists the 4 most-favored albums only, according to the average review score.
	 * @return List of most-favored album objects of size 4
	 */
	public List<Album> listMostFavoredAlbums() {
		ArrayList<Album> listAlbums = (ArrayList<Album>) listAll("highestRated");
		return listAlbums.subList(0, 4);
		/**List<Album> mostFavoredAlbums = new ArrayList<>();
		
		List<Object[]> result = super.findWithNamedQueryObjects("Review.mostFavoredAlbums", 0, 4);
		
		if (!result.isEmpty()) {
			for (Object[] elements : result) {
				Album album = (Album) elements[0];
				mostFavoredAlbums.add(album);
			}
		} 
		
		return mostFavoredAlbums;**/
	}
}
