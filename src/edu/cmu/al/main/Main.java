package edu.cmu.al.main;

<<<<<<< HEAD
=======

import edu.cmu.al.experiment.ExperimentResult;
>>>>>>> 8327d5b2156ac1eb7b8822233ef80a44b326bb7f
import edu.cmu.al.feature.BasicFeatureExtractor;
import edu.cmu.al.feature.FeatureExtractor;

/**
 * The main class to run main function
 * 
 * @author Kuo Liu
 */
public class Main {
	public static int round = 100;
	public static void main(String[] args) {
		
<<<<<<< HEAD
		Preprocess.run();
		
		int featureId = 1;
		FeatureExtractor featureExtractor = new BasicFeatureExtractor();
		featureId = featureExtractor.extractFeature(featureId);
=======
//		Preprocess.run();
//		PreprocessPNWords.run();
//		int featureId = 1;
//		FeatureExtractor featureExtractor = new BasicFeatureExtractor();
//		featureId = featureExtractor.extractFeature(featureId);
//		
//		// Please Add the interaction part of user simulation within
//		// doExperiment() function. In this function, I will determine 
//		// how many instances should be labeled according to the precision
//		// score;
//		ExperimentResult experiment = new ExperimentResult(round);
//		experiment.doExperiment();
 
>>>>>>> 8327d5b2156ac1eb7b8822233ef80a44b326bb7f
	}
}
