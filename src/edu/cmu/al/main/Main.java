package edu.cmu.al.main;

import edu.cmu.al.experiment.Experiment;
import edu.cmu.al.experiment.TableExperiment;
import edu.cmu.al.feature.FeaturePipeline;
import edu.cmu.al.ml.Classifier;
import edu.cmu.al.ml.LogisticClassifier;
import edu.cmu.al.ml.Regression;
import edu.cmu.al.ml.SVMClassifier;
import edu.cmu.al.sampling.BasicSampling;
import edu.cmu.al.sampling.QBCstrategy;
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
	public static int round = 50;
	public static int instance = 2;
	public static double ratio = 0.5;

	public static void main(String[] args) {

//		 System.out.println("DB initializing...");
//		 Preprocess.run();
//		 System.out.println("Finished");
//		 FeaturePipeline.produceFeatures();

		System.out.println("Experiment");

		// random
		BasicSampling sampling = new RandomStrategy();
		Classifier classifier1 = new Regression();
		Classifier classifier2 = new SVMClassifier();
		Classifier classifier3 = new LogisticClassifier();
		LabelingSimulation labeling = new BasicLabelingSimulation();
		//
		Experiment experiment = new TableExperiment();
		// experiment.doExperiment(round, instance, sampling, classifier1,
		// labeling, null, Constant.REG_FILE.getName());
//		experiment.doExperiment(round, instance, sampling, classifier2, labeling, null, Constant.SVM_FILE.getName());
//		experiment.doExperiment(round, instance, sampling, classifier3, labeling, null, Constant.LR_FILE.getName());
//		experiment.plotResult("Random", "RandomSampling", "svm.txt", "SVM", "logistic.txt", "LR");

		sampling = new UncertaintyStrategy();

		// experiment.doExperiment(round, instance, sampling, classifier1,
		// labeling, Constant.REG_COL.getName(), Constant.REG_FILE.getName());
		experiment.doExperiment(round, instance, sampling, classifier2, labeling, Constant.SVM_COL.getName(), Constant.SVM_FILE.getName());
		experiment.doExperiment(round, instance, sampling, classifier3, labeling, Constant.LR_COL.getName(), Constant.LR_FILE.getName());

		experiment.plotResult("Uncertain", "UncertainSampling", "svm.txt", "SVM", "logistic.txt", "LR");

		sampling = new QBCstrategy();

		// experiment.doExperiment(round, instance, sampling, classifier1,
		// labeling, Constant.REG_COL.getName(), Constant.REG_FILE.getName());
		experiment.doExperiment(round, instance, sampling, classifier2, labeling, Constant.SVM_COL.getName(), Constant.SVM_FILE.getName());
		experiment.doExperiment(round, instance, sampling, classifier3, labeling, Constant.LR_COL.getName(), Constant.LR_FILE.getName());

		experiment.plotResult("QBC", "QBCSampling", "svm.txt", "SVM", "logistic.txt", "LR");

		// experiment.doExperimentWithAllData("all_data");

		/*
		 * System.out.println("Experient"); ExperimentResult experiment = new
		 * ExperimentResult(round); experiment.doExperiment();
		 */
	}
}