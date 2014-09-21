package edu.cmu.al.ml;
/**
 * Classifier is the base class of all classifiers in training process. It is responsible of
 * training labeled data and making prediction on all test data.                 
 * @author chenying
 *
 */
public abstract class Classifier {
	/**
	 * Train labeled data
	 */
	public abstract void train();
	
	/**
	 * Make prediction on test data based on trained model and write prediction result back to 
	 * database 
	 */
	public abstract void test();

}
