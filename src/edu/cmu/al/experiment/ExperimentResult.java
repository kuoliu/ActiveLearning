package edu.cmu.al.experiment;

import edu.cmu.al.util.*;

import edu.cmu.al.util.ScoreDefine;

public class ExperimentResult {

	private int round;
	private double[] precisions;
	private double[] recalls;
	private double[] accuracies;
	private String DIR = "matlab/";
	private String precision = "precision.txt";
	private String recall = "recall.txt";
	private String accuracy = "accuracy.txt";

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
			System.out.println(precision + "\t" + recall + "\t" + accuracy);
			int numberOfInstanceToLabel = ScoreDefine
					.getNumberOfInstanceToLabel(precision);
			// to do number of instance to label
			// ---------------------------------
			index++;
		}
		plotResult();
	}

	public void plotResult() {
		Plot plot = new Plot();
		plot.barPlot();
		plot.linePlot();
	}

	public void storeResult() {
		storePrecision();
		storeRecall();
		storeAccuracy();
	}

	public void storePrecision() {
		Printer printer = new Printer(DIR + precision);
		for (int i = 0; i < round; i++) {
			printer.println(i + " " + precisions[i]);
		}
		printer.close();
	}

	public void storeRecall() {
		Printer printer = new Printer(DIR + recall);
		for (int i = 0; i < round; i++) {
			printer.println(i + " " + recalls[i]);
		}
		printer.close();
	}

	public void storeAccuracy() {
		Printer printer = new Printer(DIR + accuracy);
		for (int i = 0; i < round; i++) {
			printer.println(i + " " + accuracies[i]);
		}
		printer.close();
	}
}