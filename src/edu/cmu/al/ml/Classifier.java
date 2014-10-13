package edu.cmu.al.ml;

import java.util.HashSet;
import java.util.List;

import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.SqlManipulation;

/**
 * Classifier is the base class of all classifiers in training process. It is
 * responsible of training labeled data and making prediction on all test data.
 * 
 * @author chenying
 *
 */
public abstract interface Classifier {
	/**
	 * Train labeled data, it will first retrieve labeled training data from the data base, then 
	 * train the data and build the model
	 * @exception {@link ClassificationException}
	 */
	public abstract void train();

	/**
	 * Make prediction on test data based on trained model and write prediction
	 * result back to database
	 * @exception {@link ClassificationException}
	 */
	public abstract void test();

}
