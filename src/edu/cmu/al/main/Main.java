package edu.cmu.al.main;

import edu.cmu.al.experiment.ExperimentResult;
import edu.cmu.al.feature.BasicFeatureExtractor;
import edu.cmu.al.feature.FeatureExtractor;
import edu.cmu.al.feature.SentimentFeatureExtractor;
import edu.cmu.al.ml.LogisticClassifier;
import edu.cmu.al.ml.Regression;

/**
 * The main class to run main function
 * 
 * @author Kuo Liu
 */
public class Main {
	public static int round = 100;
	public static void main(String[] args) {
		
		/*Preprocess.run();
		PreprocessPNWords.run();
		System.out.println("1");
		int featureId = 1;

		FeatureExtractor featureExtractor = new BasicFeatureExtractor();
		featureId = featureExtractor.extractFeature(featureId);
		
		featureExtractor = new SentimentFeatureExtractor();
		featureId = featureExtractor.extractFeature(featureId);*/
		
		Regression lc = new Regression();
		lc.train();
		lc.test();
//		
//		// Please Add the interaction part of user simulation within
//		// doExperiment() function. In this function, I will determine 
//		// how many instances should be labeled according to the precision
//		// score;
//		ExperimentResult experiment = new ExperimentResult(round);
//		experiment.doExperiment();
 
	}

}
