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
	String DIR = "matlab/";
	String precision = "precision.txt";
	String recall = "recall.txt";
	String accuracy = "accuracy.txt";

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
			int numberOfInstanceToLabel = ScoreDefine.getNumberOfInstanceToLabel(precision);
			// to do number of instance to label
			// ---------------------------------
			index++;
		}
		plotResult();
	}
	
	public void plotResult(){
		Plot plot = new Plot();
		plot.barPlot();
		plot.linePlot();
	}

	public void storeResult() throws IOException {
		storePrecision();
		storeRecall();
		storeAccuracy();
	}

	public void storePrecision() throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(DIR + precision));
		try {
			for (int i = 0; i < round; i++) {
				out.write(i + " " + precisions[i]);
				out.newLine();
			}
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void storeRecall() throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(DIR + recall));
		try {
			for (int i = 0; i < round; i++) {
				out.write(i + " " + recalls[i]);
				out.newLine();
			}
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void storeAccuracy() throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(DIR + accuracy));
		try {
			for (int i = 0; i < round; i++) {
				out.write(i + " " + accuracies[i]);
				out.newLine();
			}
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
