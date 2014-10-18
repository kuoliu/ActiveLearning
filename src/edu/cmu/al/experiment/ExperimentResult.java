package edu.cmu.al.experiment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import edu.cmu.al.util.ScoreDefine;

public class ExperimentResult {

	int round;
	double[] precisions;
	double[] recalls;
	double[] accuracies;
	double[] fMeasures;
	String DIR = "matlab/";
	String precision = "precision.txt";
	String recall = "recall.txt";
	String accuracy = "accuracy.txt";
	String fMeasure = "fMeasure.txt";

	public ExperimentResult(int round) {
		this.round = round;
		this.precisions = new double[round];
		this.recalls = new double[round];
		this.accuracies = new double[round];
		this.fMeasures = new double[round];
	}

	public void doExperiment() {
		Evaluator evaluator = new Evaluator();
		int index = 0;
		while (index < round) {
			evaluator.evaluateClassification();
			double precision = evaluator.computePrecision();
			double accuracy = evaluator.computeAccuracy();
			double recall = evaluator.computeRecall();
			double fMeasure = evaluator.computeFMeasure();
			precisions[index] = precision;
			recalls[index] = recall;
			accuracies[index] = accuracy;
			fMeasures[index] = fMeasure;
			System.out.println(precision + "\t" + recall + "\t" + accuracy + "\t" + fMeasure);
			int numberOfInstanceToLabel = ScoreDefine.getNumberOfInstanceToLabel(precision);
			// to do number of instance to label
			// ---------------------------------
			index++;
		}
		// storeResult();
		plotResult();
	}

	public void plotResult() {
		Plot plot = new Plot();
		plot.barPlot();
		plot.linePlot();
	}

	public void storeResult() {
		try {
			storeInFile(DIR + precision, precisions);
			storeInFile(DIR + recall, recalls);
			storeInFile(DIR + accuracy, accuracies);
			storeInFile(DIR + fMeasure, fMeasures);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void storeInFile(String fileName, double[] array) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
		try {
			for (int i = 0; i < round; i++) {
				out.write(i + " " + array[i]);
				out.newLine();
			}
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
