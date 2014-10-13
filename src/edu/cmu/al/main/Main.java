package edu.cmu.al.main;

import edu.cmu.al.feature.BasicFeatureExtractor;
import edu.cmu.al.feature.FeatureExtractor;

/**
 * The main class to run main function
 * 
 * @author Kuo Liu
 */
public class Main {

	public static void main(String[] args) {
		
		Preprocess.run();
		
		int featureId = 1;
		FeatureExtractor featureExtractor = new BasicFeatureExtractor();
		featureId = featureExtractor.extractFeature(featureId);
	}
}
