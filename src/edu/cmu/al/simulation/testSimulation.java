package edu.cmu.al.simulation;

public class testSimulation {
  public static int round = 100;

  public static void main(String[] args) {
    LabelingSimulation simulation = new BasicLabelingSimulation();

    simulation.labelAll();
    /*
     * // System.out.println("DB initializing..."); Preprocess.run(); //
     * System.out.println("Finished"); // int featureId = 1; // FeatureExtractor featureExtractor =
     * new BasicFeatureExtractor(); // featureId = featureExtractor.extractFeature(featureId); // //
     * featureExtractor = new SentimentFeatureExtractor(); // featureId =
     * featureExtractor.extractFeature(featureId);
     * 
     * ExperimentResult experiment = new ExperimentResult(round); experiment.doExperiment();
     */

  }
}
