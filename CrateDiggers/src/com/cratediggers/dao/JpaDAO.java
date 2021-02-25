package com.cratediggers.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * Superclass for other type-specific DAO classes.
 * Implements all functionality of the entityManager that controls interaction with the database.
 * @param <E> type of entity
 */
public class JpaDAO<E> {
	/**
	 * Used to create EntityManager objects that interact with the database using HQL (Hibernate Query Language).
	 */
	private static EntityManagerFactory entityManagerFactory;
	
	static {
		entityManagerFactory = Persistence.createEntityManagerFactory("BookStoreWebsite");
	}
	
	/**
	 * Constructs a new JpaDAO object.
	 */
	public JpaDAO() {
	}
	
	/**
	 * Creates a new record in database by persisting a provided entity object.
	 * @param entity the object to be stored in the database.
	 * The type of entity determines which table it is stored in.
	 * @return the created entity
	 */
	public E create(E entity) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		entityManager.persist(entity);
		entityManager.flush();
		entityManager.refresh(entity);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return entity;
	}
	
	/**
	 * Updates a record within the database by merging a provided entity object.
	 * @param entity the object to be updates in the database.
	 * The type of entity determines which table it is updates in.
	 * @return the updated entity
	 */
	public E update(E entity) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		entity = entityManager.merge(entity);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return entity;
	}
	
	/**
	 * Finds a specific record within the database and retrieves it as an entity object.
	 * @param type of the entity to be found. 
	 * Determines which table should be searched. 
	 * @param id of entity to be found.
	 * @return the found entity object or null if not found
	 */
	public E find(Class<E> type, Object id) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		E entity = entityManager.find(type, id);
		
		if (entity != null) {
			entityManager.refresh(entity);
		}
		entityManager.close();
		
		return entity;
	}
	
	/**
	 * Deletes a specific record within the database.
	 * @param type of entity to be deleted.
	 * Determines which table contains record to be deleted.
	 * @param id of record to be deleted.
	 */
	public void delete(Class<E> type, Object id) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		Object reference = entityManager.getReference(type, id);
		entityManager.remove(reference);
		
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
	/**
	 * Retrieves a list of a entity objects of a common type from the database according to a provided HQL query.
	 * @param queryName to be executed on database
	 * @return Results of the executed query
	 */
	@SuppressWarnings("unchecked")
	public List<E> findWithNamedQuery(String queryName) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		Query query = entityManager.createNamedQuery(queryName);		
		List<E> result = query.getResultList();
		
		entityManager.close();
		
		return result;
	}
	
	/**
	 * Retrieves a list of a entity objects of a common type from the database according to a provided HQL query.
	 * List size is limited by retrieving only a subset of the results.
	 * @param queryName to be executed on database
	 * @param firstResult to be included in query results
	 * @param maxResult to be included in query results
	 * @return Results of the executed query
	 */
	@SuppressWarnings("unchecked")
	public List<E> findWithNamedQuery(String queryName, int firstResult, int maxResult) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		Query query = entityManager.createNamedQuery(queryName);		
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		
		List<E> result = query.getResultList();
		
		entityManager.close();
		
		return result;
	}

	/**
	 * Retrieves a list of a object arrays from the database according to a provided HQL query.
	 * List size is limited by retrieving only a subset of the results.
	 * @param queryName to be executed on database
	 * @param firstResult to be included in query results
	 * @param maxResult to be included in query results
	 * @return Results of the executed query
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> findWithNamedQueryObjects(String queryName, int firstResult, int maxResult) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		Query query = entityManager.createNamedQuery(queryName);		
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		
		List<Object[]> result = query.getResultList();
		
		entityManager.close();
		
		return result;
	}
	
	/**
	 * Retrieves a list of a entity objects of a common type from the database according to a provided HQL query.
	 * Query takes parameters.
	 * @param queryName to be executed on database
	 * @param paramName to identify placeholder within query
	 * @param paramValue to insert parameter value into query
	 * @return Results of the executed query
	 */
	@SuppressWarnings("unchecked")
	public List<E> findWithNamedQuery(String queryName, String paramName, Object paramValue) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery(queryName);
		
		query.setParameter(paramName, paramValue);
		
		List<E> result = query.getResultList();
		
		entityManager.close();
		
		return result;
	}

	/**
	 * Retrieves a list of a entity objects of a common type from the database according to a provided HQL query.
	 * Query takes parameters.
	 * @param queryName to be executed on database
	 * @param parameters maps parameter names to parameter values.
	 * Parameter names used to identify placeholders within query.
	 * Parameter values to insert parameter values into query.
	 * @return Results of the executed query
	 */
	@SuppressWarnings("unchecked")
	public List<E> findWithNamedQuery(String queryName, Map<String, Object> parameters) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery(queryName);
		
		Set<Entry<String, Object>> setParameters = parameters.entrySet();
		
		for (Entry<String, Object> entry : setParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		
		List<E> result = query.getResultList();
		
		entityManager.close();
		
		return result;
	}
	
	/**
	 * Retrieves the quantity of something stored within the database according to a provided HQL query.
	 * @param queryName to be executed on database
	 * @return Quantity within the database
	 */
	public long countWithNamedQuery(String queryName) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery(queryName);
		
		long result = (long) query.getSingleResult();
		entityManager.close();
		
		return result;
	}
	
	/**
	 * Retrieves the quantity of something stored within the database according to a provided HQL query.
	 * Query takes parameters.
	 * @param queryName to be executed on database
	 * @param paramName to identify placeholder within query
	 * @param paramValue to insert parameter value into query
	 * @return Quantity within the database
	 */
	public long countWithNamedQuery(String queryName, String paramName, Object paramValue) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery(queryName);
		query.setParameter(paramName, paramValue);
		
		long result = (long) query.getSingleResult();
		entityManager.close();
		
		return result;
	}	
	
	/**
	 * Closes entityManagerFactory for garbage collection.
	 */
	public void close() {
		if (entityManagerFactory != null) {
			entityManagerFactory.close();
		}
	}
	
	
	/**
	 * Determines if the first of two strings occur first alphabetically.
	 * Useful for sorting algorithms.
	 * @param str1 first string for comparison
	 * @param str2 second string for comparison
	 * @return true if the first string occurs first alphabetically, false if not
	 */
	protected boolean greaterThanString(String str1, String str2) {
		for (int i = 0; i < (str1.length() < str2.length() ? str1.length() : str2.length()); i++) {
			if ((int) Character.toLowerCase(str1.charAt(i)) > (int) Character.toLowerCase(str2.charAt(i))) { 
                return true;
            } else if (!(str1.charAt(i) == str2.charAt(i))) {
            	return false;
            }
		}
		return false;
	}
	
	protected ArrayList<E> reverseList(List<E> list) {
		for (int i = 0; i < list.size() / 2; i++) { 
            E temp = list.get(i); 
            list.set(i, list.get(list.size() - i - 1)); 
            list.set(list.size() - i - 1, temp); 
        } 
		return (ArrayList<E>) list;
	}
	
}
