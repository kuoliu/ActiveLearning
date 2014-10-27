package edu.cmu.al.experiment;

import java.io.IOException;
import java.util.HashSet;

import edu.cmu.al.ml.Regression;
import edu.cmu.al.sampling.BasicSampling;
import edu.cmu.al.sampling.RandomStrategy;
import edu.cmu.al.sampling.UncertaintyStrategy;
import edu.cmu.al.simulation.BasicLabelingSimulation;
import edu.cmu.al.simulation.LabelingSimulation;
import edu.cmu.al.util.Printer;
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
      // System.out.println(precision + "\t" + recall + "\t" + accuracy
      // + "\t" + fMeasure);
      int numberOfInstanceToLabel = ScoreDefine.getNumberOfInstanceToLabel(precision);

      System.out.println(numberOfInstanceToLabel);
      
      LabelingSimulation simulation = new BasicLabelingSimulation();
      // to do number of instance to label
      BasicSampling randomsample = new RandomStrategy();
      
      // BasicSampling uncsample = new UncertaintyStrategy();

      HashSet<String> productIds = randomsample.sampling(numberOfInstanceToLabel);

      // label all the instances in productIds
      simulation.labelProductId(productIds);

      Regression lc = new Regression();
      lc.train();
      lc.test();
      index++;
    }
    storeResult();

    try {
      Runtime.getRuntime().exec("python matlab/plot.py");
    } catch (IOException e) {
      e.printStackTrace();
    }

    // plotResult();
  }

  public void plotResult() {
    Plot plot = new MatPlot();
    plot.barPlot();
    plot.linePlot();
  }

  public void storeResult() {
    storeInFile(DIR + precision, precisions);
    storeInFile(DIR + recall, recalls);
    storeInFile(DIR + accuracy, accuracies);
    storeInFile(DIR + fMeasure, fMeasures);
  }

  public void storeInFile(String fileName, double[] array) {
    Printer printer = new Printer(fileName);
    for (int i = 0; i < round; i++) {
      printer.println(i + " " + array[i]);
    }
    printer.close();
  }
}