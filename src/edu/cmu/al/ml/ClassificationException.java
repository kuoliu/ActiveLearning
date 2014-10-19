package edu.cmu.al.ml;

/**
 * ClassificationException is the exception related with the training and test
 * process
 * 
 * @author chenying
 * 
 */
public class ClassificationException extends Exception {
	private static final long serialVersionUID = 4516345339191911506L;

	// Parameterless Constructor
	public ClassificationException() {
	}

	// Constructor that accepts a message
	public ClassificationException(String message) {
		super(message);
	}
}
