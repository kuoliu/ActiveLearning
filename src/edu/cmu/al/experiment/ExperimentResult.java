package edu.cmu.al.experiment;

import edu.cmu.al.util.ScoreDefine;

public class ExperimentResult {

	int round;
	double[] precisions;
	double[] recalls;
	double[] accuracies;

	public ExperimentResult(int round) {
		this.round = round;
		this.precisions = new double[round];
		this.recalls = new double[round];
		this.accuracies = new double[round];
	}

	public void doExperiment() {
		Evaluator evaluator = new Evaluator();
		int index = 0;
		while (index < round) {
			evaluator.evaluateClassification();
			double precision = evaluator.computePrecision();
			double accuracy = evaluator.computeAccuracy();
			double recall = evaluator.computeRecall();
			precisions[index] = precision;
			recalls[index] = recall;
			accuracies[index] = accuracy;
			int numberOfInstanceToLabel = ScoreDefine.getNumberOfInstanceToLabel(precision);
			// to do number of instance to label
			index++;
		}
	}
}
