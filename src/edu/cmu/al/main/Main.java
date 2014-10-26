package edu.cmu.al.main;

import edu.cmu.al.experiment.BasicExperiment;
import edu.cmu.al.experiment.Experiment;
import edu.cmu.al.feature.FeaturePipeline;
import edu.cmu.al.ml.Classifier;
import edu.cmu.al.ml.Regression;
import edu.cmu.al.sampling.BasicSampling;
import edu.cmu.al.sampling.RandomStrategy;
import edu.cmu.al.simulation.BasicLabelingSimulation;
import edu.cmu.al.simulation.LabelingSimulation;

/**
 * The main class to run main function
 * 
 */
public class Main {
  public static int round = 20;

  public static void main(String[] args) {
    System.out.println("DB initializing...");
    Preprocess.run();
    System.out.println("Finished");
    FeaturePipeline.produceFeatures();

    System.out.println("Experiment");

    // train 0.5 * total number of instances in the predict table...
    // too many ---> too slow
    Experiment experiment = new BasicExperiment(round, 0.5);

    BasicSampling randomsample = new RandomStrategy();
    Classifier lr = new Regression();
    LabelingSimulation labeling = new BasicLabelingSimulation();

    experiment.doExperiment(randomsample, lr, labeling, "Round_20_Rate_0.5");

    // experiment.doExperimentWithAllData("all_data");

    /*
     * System.out.println("Experient"); ExperimentResult experiment = new ExperimentResult(round);
     * experiment.doExperiment();
     */
  }
}
