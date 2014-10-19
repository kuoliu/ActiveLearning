package edu.cmu.al.simulation;

import static org.junit.Assert.*;

import org.junit.Test;

public class testBasicSimulation {

	public static int round = 100;

	@Test
	public void test() {
		LabelingSimulation simulation = new BasicLabelingSimulation();

		simulation.labelAll();
		/*
		 * // System.out.println("DB initializing..."); Preprocess.run(); //
		 * System.out.println("Finished"); // int featureId = 1; //
		 * FeatureExtractor featureExtractor = new BasicFeatureExtractor(); //
		 * featureId = featureExtractor.extractFeature(featureId); // //
		 * featureExtractor = new SentimentFeatureExtractor(); // featureId =
		 * featureExtractor.extractFeature(featureId);
		 * 
		 * ExperimentResult experiment = new ExperimentResult(round);
		 * experiment.doExperiment();
		 */
	}
}