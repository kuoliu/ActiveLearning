package edu.cmu.al.simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import edu.cmu.al.experiment.ExperimentResult;
import edu.cmu.al.experiment.Plot;
import edu.cmu.al.experiment.PyPlot;
import edu.cmu.al.main.Preprocess;

public class testSimulation {
  public static int round = 100;

  public static void main(String[] args) throws IOException {
    /*
     * LabelingSimulation simulation = new BasicLabelingSimulation();
     * 
     * simulation.labelAll();
     */
    
    Plot p = new PyPlot();
    // python python/PylabPlotTool.py test OnlyTest accuracy.txt accuracy precision.txt precision
    p.linePlot("t", "small", "accuracy.txt", "l1", "precision.txt", "l2");

/*    Process p = Runtime.getRuntime().exec("python matlab/plot.py");
    BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

    System.out.println(in.readLine());*/

    /*
     * System.out.println("DB initializing..."); Preprocess.run(); System.out.println("Finished");
     * int featureId = 1; FeatureExtractor featureExtractor = new BasicFeatureExtractor(); featureId
     * = featureExtractor.extractFeature(featureId); featureExtractor = new
     * SentimentFeatureExtractor(); featureId = featureExtractor.extractFeature(featureId);
     */

    // ExperimentResult experiment = new ExperimentResult(round);
    // experiment.doExperiment();

  }
}
