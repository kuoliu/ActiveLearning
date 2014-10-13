package edu.cmu.al.main;

import edu.cmu.al.feature.BasicFeature;


import edu.cmu.al.feature.Feature;

public class Main {

	public static void main(String[] args) {
		
		Preprocess.run();
		
		int featureId = 1;
		Feature featureExtractor = new BasicFeature();
		featureId = featureExtractor.extractFeature(featureId);
	}
}
