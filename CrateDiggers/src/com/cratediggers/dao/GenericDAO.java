package com.cratediggers.dao;

import java.util.List;

/**
 * Provides a generic interface for DAO classes.
 * @param <E> type of entity
 */
public interface GenericDAO<E> {
	
	public E create(E t);
	
	public E update(E t);
	
	public E get(Object id);
	
	public void delete(Object id);
	
	public List<E> listAll();
	
	public long count();
	
}
