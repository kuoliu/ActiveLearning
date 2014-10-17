package edu.cmu.al.ml;


import edu.cmu.al.util.Configuration;
import weka.core.Instances;
import weka.experiment.InstanceQuery;

/**
 * Classifier is the base class of all classifiers in training process. It is
 * responsible of training labeled data and making prediction on all test data.
 * 
 * @author chenying
 * 
 */
public abstract class Classifier {

	protected static InstanceQuery query = null;

	static {
		try {
			query = new InstanceQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// set the parameter of query
		query.setDatabaseURL(Configuration.getSqlUrl());
		query.setUsername(Configuration.getSqlUserName());
		query.setPassword(Configuration.getSqlPassword());
	}

	protected Instances getData(String sql){
		query.setQuery(sql);
		Instances data = null;
		try {
			data = query.retrieveInstances();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * Train labeled data, it will first retrieve labeled training data from the
	 * data base, then train the data and build the model
	 * 
	 * @exception {@link ClassificationException}
	 */
	public abstract void train();

	/**
	 * Make prediction on test data based on trained model and write prediction
	 * result back to database
	 * 
	 * @exception {@link ClassificationException}
	 */
	public abstract void test();
}
