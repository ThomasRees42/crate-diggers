package com.cratediggers.controller.frontend.shoppingcart;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.cratediggers.entity.Album;

/**
 * ShoppingCart class encapsulates an <Album, Integer> HashMap.
 * This provides the logic of a shopping cart that can contain various different albums for purchase and their corresponding quantity. 
 */
public class ShoppingCart {
	/**
	 * HashMap that maps albums to their quantity within a ShoppingCart object.
	 */
	private Map<Album, Integer> cart = new HashMap<>();
	
	/**
	 * Adds a new album object to the cart with a quantity of 1.
	 * If album already exists within the cart then the quantity of that key-value is increased by one instead.
	 * @param album to be added to cart
	 */
	public void addItem(Album album) {
		if (cart.containsKey(album)) {
			Integer quantity = cart.get(album) + 1;
			cart.put(album, quantity);			
		} else {
			cart.put(album, 1);
		}
	}
	
	/**
	 * Removes an album object from the cart.
	 * @param album to be removed
	 */
	public void removeItem(Album album) {
		cart.remove(album);
	}
	
	/**
	 * Calculates the total quantity of all albums within the cart.
	 * Iterates over key set and returns the sum of all quantity values.
	 * @return The total quantity of all albums within the cart
	 */
	public int getTotalQuantity() {
		int total = 0;
		
		Iterator<Album> iterator = cart.keySet().iterator();
		
		while (iterator.hasNext()) {
			Album next = iterator.next();
			Integer quantity = cart.get(next);
			total += quantity;
		}
		
		return total;
	}
	
	/**
	 * Calculates the total monetary amount of all albums within the cart.
	 * Iterates over key set and returns the sum of all quantity values multiplied by the price of their corresponding albums.
	 * @return The total monetary amount of all albums within the cart
	 */
	public float getTotalAmount() {
		float total = 0.0f;
		
		Iterator<Album> iterator = cart.keySet().iterator();
		
		while (iterator.hasNext()) {
			Album album = iterator.next();
			Integer quantity = cart.get(album);
			double subTotal = quantity * album.getPrice();
			total += subTotal;
		}		
		
		return total;
	}
	
	
	/**
	 * Updates the contents of the cart with an array of albumIds and corresponding array of quantities.
	 * Iterates through arrays, constructing new album objects using the albumIds and storing them within the cart alongside their corresponding quantity of the same index. 
	 * @param albumIds array used to create album objects that are put into cart as the key
	 * @param quantities array that are put into cart as the value
	 */
	public void updateCart(int[] albumIds, int[] quantities) {
		for (int i = 0; i < albumIds.length; i++) {
			Album key = new Album(albumIds[i]);
			Integer value = quantities[i];
			cart.put(key, value);
		}
	}
	
	/**
	 * Clears the contents of the cart.
	 */
	public void clear() {
		cart.clear();
	}
	
	/**
	 * Calculates the number of different albums within the cart.
	 * @return The number of different albums within the cart
	 */
	public int getTotalItems() {
		return cart.size();
	}
	
	/**
	 * Returns the HashMap that the ShoppingCart object encapsulates.
	 * @return The HashMap that the ShoppingCart object encapsulates
	 */
	public Map<Album, Integer> getItems() {
		return this.cart;
	}
	
}
