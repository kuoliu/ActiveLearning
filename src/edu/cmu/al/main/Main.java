package edu.cmu.al.main;

import edu.cmu.al.experiment.ExperimentResult;
import edu.cmu.al.feature.FeaturePipeline;
import edu.cmu.al.ml.Regression;

/**
 * The main class to run main function
 * 
 */
public class Main {
	public static int round = 10;

	public static void main(String[] args) {
		System.out.println("DB initializing...");
		Preprocess.run();
		System.out.println("Finished");
		FeaturePipeline.produceFeatures();
		
		System.out.println("Experient");
		ExperimentResult experiment = new ExperimentResult(round);
		experiment.doExperiment();
	}
}
