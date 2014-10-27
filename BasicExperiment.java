package edu.cmu.al.experiment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import edu.cmu.al.main.Preprocess;
import edu.cmu.al.ml.Classifier;
import edu.cmu.al.ml.Regression;
import edu.cmu.al.sampling.BasicSampling;
import edu.cmu.al.simulation.BasicLabelingSimulation;
import edu.cmu.al.simulation.LabelingSimulation;
import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.Printer;
import edu.cmu.al.util.SqlManipulation;
import edu.cmu.al.util.Util;

public class BasicExperiment {

  int round;

  double[] accuracies;

  double[] accuraciesCost;

  double[] testSamplingAccuracies;

  double[] testSamplingAccuraciesCost;

  double[] testModelAccuracies;

  double[] testModelAccuraciesCost;

  String DIR = Util.DIR; // "output/";

  String accuracy = Util.accuracy; // "accuracy.txt";

  String testsamplingAccuracy = Util.testSamplingAccuracy; // "testLabelingAccuracy.txt";

  String testModelAccuracy = Util.testModelAccuracy; // "testModelAccuracy.txt";

  Evaluator evaluator;

  double ratio = 0;

  List<String> allProductIds = new ArrayList<String>();

  public BasicExperiment(int round, double ratio) {
    this.round = round;
    this.ratio = ratio;
    this.accuracies = new double[round];
    this.testSamplingAccuracies = new double[round];
    this.testModelAccuracies = new double[round];
    this.accuraciesCost = new double[round];
    this.testSamplingAccuraciesCost = new double[round];
    this.testModelAccuraciesCost = new double[round];
    this.evaluator = new Evaluator();
  }

  public void doExperiment(int i, BasicSampling sampling, Classifier classifier,
          LabelingSimulation labeling) {
    if (i < 0 || i >= round) {
      System.out.println("Experiment error...");
      return;
    }

    int numberOfInstanceToLabel = (int) Math.floor((labeling.getAllNumber() / round) * ratio);

    // print
    System.out.println("Round: " + i + '\t' + "ToLabel: " + numberOfInstanceToLabel + "\t"
            + "Unlabeled: " + labeling.getUnlabeledNumber());

    HashSet<String> productIds = sampling.sampling(numberOfInstanceToLabel);
    allProductIds.addAll(productIds);

    labeling.labelProductId(productIds);

    classifier.train();
    classifier.test();

    evaluator.clear();
    evaluator.evaluateClassification();
    accuracies[i] = evaluator.computeAccuracy();

    if (i == 0) {
      accuraciesCost[i] = numberOfInstanceToLabel;
    } else {
      accuraciesCost[i] = accuraciesCost[i - 1] + numberOfInstanceToLabel;
    }
  }

  public void testSampling(int i, Classifier classifier, LabelingSimulation labeling) {
    if (i < 0 || i >= round) {
      System.out.println("Experiment error...");
      return;
    }

    int numberOfInstanceToLabel = (int) Math.floor((labeling.getAllNumber() / round) * ratio);

    // print
    System.out.println("Round: " + i + '\t' + "ToLabel: " + numberOfInstanceToLabel + "\t"
            + "Unlabeled: " + labeling.getUnlabeledNumber());

    labeling.randomLabelByNum(numberOfInstanceToLabel);

    classifier.train();
    classifier.test();

    evaluator.clear();
    evaluator.evaluateClassification();
    testSamplingAccuracies[i] = evaluator.computeAccuracy();

    if (i == 0) {
      testSamplingAccuraciesCost[i] = numberOfInstanceToLabel;
    } else {
      testSamplingAccuraciesCost[i] = testSamplingAccuraciesCost[i - 1] + numberOfInstanceToLabel;
    }
  }

  public void testModel(Classifier classifier, LabelingSimulation labeling) {
    // print
    // System.out.println("ToLabel: " + labeling.getUnlabeledNumber() + "\t" + "Unlabeled: "
    // + labeling.getUnlabeledNumber());

    // labeling.labelAll();

    // /////////////////////////////////////////////////////////
    int numberOfInstanceToLabel = allProductIds.size();

    // print
    System.out.println("ToLabel: " + numberOfInstanceToLabel + "\t" + "Unlabeled: "
            + labeling.getUnlabeledNumber());

    labeling.labelProductId(allProductIds);
    // //////////////////////////////////////////////////////////////

    classifier.train();
    classifier.test();

    evaluator.clear();
    evaluator.evaluateClassification();
    for (int i = 0; i < this.round; i++) {
      testModelAccuracies[i] = evaluator.computeAccuracy();
      testModelAccuraciesCost[i] = numberOfInstanceToLabel * (i + 1) / round;
    }
  }

  private void testAllData(Classifier classifier, LabelingSimulation labeling) {
    // print
    // System.out.println("ToLabel: " + labeling.getUnlabeledNumber() + "\t" + "Unlabeled: "
    // + labeling.getUnlabeledNumber());

    // print
    System.out.println("ToLabel: " + (labeling.getUnlabeledNumber() - 1) + "\t" + "Unlabeled: "
            + labeling.getUnlabeledNumber());

    // labeling.labelAll();

    labeling.randomLabelByNum(labeling.getUnlabeledNumber() - 1);

    System.out.println("Train...");
    classifier.train();
    classifier.test();
    System.out.println("Test...");

    evaluator.clear();
    evaluator.evaluateClassification();
    for (int i = 0; i < this.round; i++) {
      testModelAccuracies[i] = evaluator.computeAccuracy();
      testModelAccuraciesCost[i] = labeling.getAllNumber() * (i + 1) / round;
    }
  }

  public void plotResult() {
    return;
  }

  // python python/PylabPlotTool.py test OnlyTest accuracy.txt accuracy precision.txt precision
  public void plotResult(String outputFileName, String title, String... files) {
    Plot p = new PyPlot();
    p.linePlot(outputFileName, title, files);
  }

  public void storeInFile(String outputFileName, double[] cost, double[] accuracy) {
    Printer printer = new Printer(outputFileName);
    for (int i = 0; i < round; i++) {
      printer.println(cost[i] + " " + accuracy[i]);
    }
    printer.close();
  }

  public double[] getTestSamplingAccuracies() {
    return testSamplingAccuracies;
  }

  public double[] getTestSamplingAccuraciesCost() {
    return testSamplingAccuraciesCost;
  }

  public double[] getTestModelAccuracies() {
    return testModelAccuracies;
  }

  public double[] getTestModelAccuraciesCost() {
    return testModelAccuraciesCost;
  }

  public double[] getAccuracies() {
    return accuracies;
  }

  public double[] getAccuraciesCost() {
    return accuraciesCost;
  }

  public void storeInFile() {
    storeInFile(DIR + accuracy, getAccuraciesCost(), getAccuracies());
    storeInFile(DIR + testModelAccuracy, getTestModelAccuraciesCost(), getTestModelAccuracies());
    storeInFile(DIR + testsamplingAccuracy, getTestSamplingAccuraciesCost(),
            getTestSamplingAccuracies());

  }

  public void doExperimentWithAllData(String outputFileName) {

    Classifier lr = new Regression();
    LabelingSimulation labeling = new BasicLabelingSimulation();

    testAllData(lr, labeling);
    clearPredictTable();

    storeInFile(DIR + testModelAccuracy, getTestModelAccuraciesCost(), getTestModelAccuracies());

    plotResult(outputFileName, "AllData", Util.testModelAccuracy, "AllData");

    /*
     * BasicSampling randomsample = new RandomStrategy(); Classifier lr = new Regression();
     * LabelingSimulation labeling = new BasicLabelingSimulation();
     * 
     * doExperiment(randomsample, lr, labeling, "result");
     */
  }

  public void doExperiment(BasicSampling sampling, Classifier classifier,
          LabelingSimulation labeling, String outputFileName) {

    for (int i = 0; i < round; i++) {
      doExperiment(i, sampling, classifier, labeling);
    }
    clearPredictTable();

    for (int i = 0; i < round; i++) {
      testSampling(i, classifier, labeling);
    }
    clearPredictTable();

    testModel(classifier, labeling);
    clearPredictTable();

    storeInFile();
    plotResult(outputFileName, "Accuracy", Util.accuracy, "Accuracy", Util.testSamplingAccuracy,
            "RamdomLabel", Util.testModelAccuracy, "LabelAll");
  }

  private static void clearPredictTable() {
    String sql = "DROP TABLE IF EXISTS " + Configuration.getPredictTable();
    SqlManipulation.dropTable(sql);

    sql = "CREATE TABLE IF NOT EXISTS "
            + Configuration.getPredictTable()
            + " (product_id VARCHAR(256) primary key, islabeled INTEGER, user_label REAL, confidence REAL, predict_result REAL)";
    SqlManipulation.createTable(sql);

    Preprocess.initPredictTable();
  }

  public void doExperiment(String outputFileName) {
    // TODO Auto-generated method stub
    
  }

}
