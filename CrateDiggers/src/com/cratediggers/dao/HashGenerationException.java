package com.cratediggers.dao;

/**
 * Used to construct hash generation exceptions.
 */
@SuppressWarnings("serial")
public class HashGenerationException extends RuntimeException {
	/**
	 * Constructs a HashGenerationException with an error message to be displayed on console.
	 * @param message to be displayed
	 */
	public HashGenerationException(String message) {
		super(message);
	}

	/**
	 * Constructs a HashGenerationException with an error message to be displayed on console and cause.
	 * @param message to be displayed
	 * @param cause of exception
	 */
	public HashGenerationException(String message, Throwable cause) {
		super(message, cause);
	}

}
