package edu.cmu.al.feature;

import java.util.HashMap;

/**
 * Description: Abstract class for feature extractor, other feature extractors
 * will extend this class and rewrite the extractFeature function
 * 
 * @author Kuo Liu, Bo Ma
 * 
 * */
public abstract class FeatureExtractor {

	/**
	 * Explain the real meaning of each feature based on the feature id
	 */
	protected static HashMap<Integer, String> featureMap = null;

	static {
		featureMap = new HashMap<Integer, String>();
	}

	public abstract int extractFeature(int featureId);

	/**
	 * Put the <Key, Value> pair (<featureId, feature>) into the HashMap
	 */
	protected static void linkFeatureidToFeature(int featureId, String feature) {
		featureMap.put(featureId, feature);
	}
}
