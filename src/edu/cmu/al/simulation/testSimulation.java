package edu.cmu.al.simulation;

import java.util.Random;

import edu.cmu.al.experiment.ExperimentResult;
import edu.cmu.al.main.Preprocess;

public class testSimulation {
  public static int round = 100;

  public static void main(String[] args) {
    
    Random randomGenerator = new Random();
    for (int i = 0; i < 100; i++) {
      int randomInt = randomGenerator.nextInt(10);
      System.out.println(randomInt);
    }
    


/*    // System.out.println("DB initializing...");
    Preprocess.run();
    // System.out.println("Finished");
    // int featureId = 1;
    // FeatureExtractor featureExtractor = new BasicFeatureExtractor();
    // featureId = featureExtractor.extractFeature(featureId);
    //
    // featureExtractor = new SentimentFeatureExtractor();
    // featureId = featureExtractor.extractFeature(featureId);

    ExperimentResult experiment = new ExperimentResult(round);
    experiment.doExperiment();*/

  }
}
