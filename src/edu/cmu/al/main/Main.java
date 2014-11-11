package edu.cmu.al.main;

import edu.cmu.al.experiment.Experiment;
import edu.cmu.al.experiment.TableExperiment;
import edu.cmu.al.feature.FeaturePipeline;
import edu.cmu.al.ml.Classifier;
import edu.cmu.al.ml.LogisticClassifier;
import edu.cmu.al.ml.Regression;
import edu.cmu.al.ml.SVMClassifier;
import edu.cmu.al.sampling.BasicSampling;
import edu.cmu.al.sampling.RandomStrategy;
import edu.cmu.al.sampling.UncertaintyStrategy;
import edu.cmu.al.simulation.BasicLabelingSimulation;
import edu.cmu.al.simulation.LabelingSimulation;
import edu.cmu.al.util.Constant;

/**
 * The main class to run main function
 * 
 */
public class Main {
	public static int round = 10;

	public static double ratio = 0.5;

	public static void main(String[] args) {

		 System.out.println("DB initializing...");
		 Preprocess.run();
		 System.out.println("Finished");
		 FeaturePipeline.produceFeatures();
		
		 System.out.println("Experiment");

		// random
		BasicSampling sampling = new UncertaintyStrategy();
		Classifier classifier1 = new Regression();
		Classifier classifier2 = new SVMClassifier();
		Classifier classifier3 = new LogisticClassifier();
		LabelingSimulation labeling = new BasicLabelingSimulation();
		
		Experiment experiment = new TableExperiment();
		experiment.doExperiment(30, 5, sampling, classifier1, labeling, null, Constant.LR_FILE.getName());
		experiment.doExperiment(30, 5, sampling, classifier2, labeling, null, Constant.SVM_FILE.getName());
		experiment.doExperiment(30, 5, sampling, classifier3, labeling, null, Constant.LR_FILE.getName());
		experiment.plotResult("Random", "ThreeClassifiers","regression.txt", "LiR", "svm.txt", "SVM", "logistic.txt", "LR");
		
		sampling = new UncertaintyStrategy();

		int numberOfInstanceToLabel = (int) Math.floor((labeling.getAllNumber() / round) * ratio);

		experiment.doExperiment(30, 5, sampling, classifier1, labeling, Constant.REG_COL.getName(), Constant.REG_FILE.getName());
		experiment.doExperiment(30, 5, sampling, classifier2, labeling, Constant.SVM_COL.getName(), Constant.SVM_FILE.getName());
		experiment.doExperiment(30, 5, sampling, classifier3, labeling, Constant.LR_COL.getName(), Constant.LR_FILE.getName());
		
		 experiment.plotResult("Uncertain", "ThreeClassifiers",
		 "regression.txt", "LiR", "svm.txt", "SVM", "logistic.txt", "LR");

		// experiment.doExperiment("Round_10_Rate_0.5");

		// experiment.doExperimentWithAllData("all_data");

		/*
		 * System.out.println("Experient"); ExperimentResult experiment = new
		 * ExperimentResult(round); experiment.doExperiment();
		 */
	}
}
