package edu.cmu.al.sampling;

import java.util.List;

/**
 * Sampling is the base class of all sampling methods. It is responsible for
 * providing some instances for user to label.
 * @author yuanyuan
 *
 */
public abstract class Sampling {
	
	public Sampling() {
		
	}
	/**
	 * Determines which observations (rows) are labeled.
	 * @param instances
	 * @return list of id of instances to be labeled
	 */
	public abstract List<Integer> label(List<String> instances);
	

	/**
	 * Extracts the class posterior probabilities for the unlabeled observations.
	 * @param instance
	 * @return the posterior for a specific instance
	 */
	public abstract Double get_predict_result(String instance);
	
	/**
	 * Computes the specified uncertainty for each of the unlabeled observations
	 * based on the posterior probabilities of class membership.
	 * @param instance
	 * @return
	 */
	public abstract Double compute_uncertainty(String instance, Double least_confidence, Double margin, Double entropy);
	
	/**
	 * Determines the order of the unlabeled observations by uncertainty measure.
	 * @param instances
	 * @param decreasing true for decreasing rank and false for increasing rank
	 * @return
	 */
	public abstract List<String> rank_uncertainty(List<String> instances, boolean decreasing);
	
}
