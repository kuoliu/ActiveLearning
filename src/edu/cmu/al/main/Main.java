package edu.cmu.al.main;

import edu.cmu.al.experiment.ExperimentResult;

/**
 * The main class to run main function
 * 
 */
public class Main {
	public static int round = 100;

	public static void main(String[] args) {
		ExperimentResult experiment = new ExperimentResult(round);
		experiment.doExperiment();
	}
}
