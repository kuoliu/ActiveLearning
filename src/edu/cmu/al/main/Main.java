package edu.cmu.al.main;

import edu.cmu.al.experiment.ExperimentResult;
import edu.cmu.al.feature.BasicFeatureExtractor;
import edu.cmu.al.feature.FeatureExtractor;
import edu.cmu.al.feature.SentimentFeatureExtractor;
import edu.cmu.al.ml.Regression;

/**
 * The main class to run main function
 * 
 */
public class Main {
	public static int round = 30;

	public static void main(String[] args) {

//		System.out.println("DB initializing...");
//		Preprocess.run();
//		
		System.out.println("Finished");
		int featureId = 1;
		FeatureExtractor featureExtractor = new BasicFeatureExtractor();
		featureId = featureExtractor.extractFeature(featureId);

		featureExtractor = new SentimentFeatureExtractor();
		featureId = featureExtractor.extractFeature(featureId);

		System.out.println("Experiment");
		ExperimentResult experiment = new ExperimentResult(round);
		experiment.doExperiment();

		System.out.println("Experiment finish");
	}
}
