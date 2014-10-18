package edu.cmu.al.main;

import java.util.HashSet;

import edu.cmu.al.experiment.ExperimentResult;
import edu.cmu.al.feature.BasicFeatureExtractor;
import edu.cmu.al.feature.FeatureExtractor;
import edu.cmu.al.sampling.BasicSampling;
import edu.cmu.al.sampling.RandomStrategy;
import edu.cmu.al.sampling.UncertaintyStrategy;
import edu.cmu.al.feature.SentimentFeatureExtractor;


/**
 * The main class to run main function
 * 
 */
public class Main {
	public static int round = 100;


	public static void main(String[] args) {

	
		System.out.println("DB initializing...");
	    Preprocess.run();
	    System.out.println("Finished");
	    int featureId = 1;
	    FeatureExtractor featureExtractor = new BasicFeatureExtractor();
	    featureId = featureExtractor.extractFeature(featureId);
		
		featureExtractor = new SentimentFeatureExtractor();
		featureId = featureExtractor.extractFeature(featureId);
		
		ExperimentResult experiment = new ExperimentResult(round);
		experiment.doExperiment();
		
	    BasicSampling randomsample = new RandomStrategy();
	    BasicSampling uncsample = new UncertaintyStrategy();
	    
	    for (int i = 0; i < 10; i++) {
	    	HashSet<String> productIds = randomsample.sampling(5);
	    }
    
	    // Preprocess.run();
	    // PreprocessPNWords.run();
	    // int featureId = 1;
	    // FeatureExtractor featureExtractor = new BasicFeatureExtractor();
	    // featureId = featureExtractor.extractFeature(featureId);
	    //
	    // // Please Add the interaction part of user simulation within
	    // // doExperiment() function. In this function, I will determine
	    // // how many instances should be labeled according to the precision
	    // // score;
	    // ExperimentResult experiment = new ExperimentResult(round);
	    // experiment.doExperiment();
	
	  }
}
