package edu.cmu.al.sampling;

import java.util.List;

/**
 * The baseline of all sampling methods
 * @author yuanyuan
 *
 */
public class UncertaintySampling extends Sampling {
	/**
	 * Determines which observations (rows) are labeled.
	 * @param instances
	 * @return list of id of instances to be labeled
	 */
	@Override
	public List<Integer> label(List<String> instances) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Extracts the class posterior probabilities for the unlabeled observations.
	 * @param instance
	 * @return the posterior for a specific instance
	 */
	
	@Override
	public Double get_predict_result(String instance) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Computes the specified uncertainty for each of the unlabeled observations
	 * based on the posterior probabilities of class membership.
	 * @param instance
	 * @return
	 */
	@Override
	public Double compute_uncertainty(String instance, Double least_confidence,
			Double margin, Double entropy) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Determines the order of the unlabeled observations by uncertainty measure.
	 * @param instances
	 * @param decreasing true for decreasing rank and false for increasing rank
	 * @return
	 */
	@Override
	public List<String> rank_uncertainty(List<String> instances,
			boolean decreasing) {
		// TODO Auto-generated method stub
		return null;
	}	
}
