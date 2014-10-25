package edu.cmu.al.experiment;

import java.util.HashSet;

import edu.cmu.al.main.Preprocess;
import edu.cmu.al.ml.Classifier;
import edu.cmu.al.ml.Regression;
import edu.cmu.al.sampling.BasicSampling;
import edu.cmu.al.sampling.RandomStrategy;
import edu.cmu.al.simulation.BasicLabelingSimulation;
import edu.cmu.al.simulation.LabelingSimulation;
import edu.cmu.al.util.Printer;
import edu.cmu.al.util.Util;

public class BasicExperiment implements Experiment {

  int round;

  double[] accuracies;

  double[] testSamplingAccuracies;

  double[] testModelAccuracies;

  String DIR = Util.DIR; // "output/";

  String accuracy = Util.accuracy; // "accuracy.txt";

  String testsamplingAccuracy = Util.testSamplingAccuracy; // "testLabelingAccuracy.txt";

  String testModelAccuracy = Util.testModelAccuracy; // "testModelAccuracy.txt";

  Evaluator evaluator;

  double ratio = 0;

  public BasicExperiment(int round, double ratio) {
    this.round = round;
    this.ratio = ratio;
    this.accuracies = new double[round];
    this.testSamplingAccuracies = new double[round];
    this.testModelAccuracies = new double[round];
    this.evaluator = new Evaluator();
  }

  @Override
  public void doExperiment(int i, BasicSampling sampling, Classifier classifier,
          LabelingSimulation labeling) {
    if (i < 0 || i >= round) {
      System.out.println("Experiment error...");
    }

    int numberOfInstanceToLabel = (int) Math.floor((labeling.getAllNumber() / round) * ratio);

    // print
    System.out.println("Round: " + i + '\t' + "ToLabel: " + numberOfInstanceToLabel + "\t"
            + "Unlabeled: " + labeling.getUnlabeledNumber());

    HashSet<String> productIds = sampling.sampling(numberOfInstanceToLabel);
    labeling.labelProductId(productIds);

    classifier.train();
    classifier.test();

    evaluator.clear();
    evaluator.evaluateClassification();
    accuracies[i] = evaluator.computeAccuracy();
  }

  @Override
  public void testSampling(int i, Classifier classifier) {
    if (i < 0 || i >= round) {
      System.out.println("Experiment error...");
    }

    LabelingSimulation labeling = new BasicLabelingSimulation();

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
  }

  @Override
  public void testModel(Classifier classifier) {
    LabelingSimulation labeling = new BasicLabelingSimulation();

    // print
    // System.out.println("ToLabel: " + labeling.getUnlabeledNumber() + "\t" + "Unlabeled: "
    // + labeling.getUnlabeledNumber());

    // labeling.labelAll();

    // /////////////////////////////////////////////////////////
    int numberOfInstanceToLabel = (int) Math.floor(labeling.getAllNumber() * ratio);

    // print
    System.out.println("ToLabel: " + numberOfInstanceToLabel + "\t" + "Unlabeled: "
            + labeling.getUnlabeledNumber());

    labeling.randomLabelByNum(numberOfInstanceToLabel);
    // //////////////////////////////////////////////////////////////

    classifier.train();
    classifier.test();

    evaluator.clear();
    evaluator.evaluateClassification();
    for (int i = 0; i < this.round; i++) {
      testModelAccuracies[i] = evaluator.computeAccuracy();
    }
  }

  @Override
  public void plotResult() {
    return;
  }

  // python python/PylabPlotTool.py test OnlyTest accuracy.txt accuracy precision.txt precision
  @Override
  public void plotResult(String outputFileName, String title, String... files) {
    Plot p = new PyPlot();
    p.linePlot(outputFileName, title, files);
  }

  @Override
  public void storeInFile(String outputFileName, double[] array) {
    Printer printer = new Printer(outputFileName);
    for (int i = 0; i < round; i++) {
      printer.println(i + " " + array[i]);
    }
    printer.close();
  }

  public double[] getTestSamplingAccuracies() {
    return testSamplingAccuracies;
  }

  public double[] getTestModelAccuracies() {
    return testModelAccuracies;
  }

  public double[] getAccuracies() {
    return accuracies;
  }

  @Override
  public void storeInFile() {
    storeInFile(DIR + accuracy, getAccuracies());
    storeInFile(DIR + testModelAccuracy, getTestModelAccuracies());
    storeInFile(DIR + testsamplingAccuracy, getTestSamplingAccuracies());

  }

  @Override
  public void doExperiment() {
    BasicSampling randomsample = new RandomStrategy();
    Classifier lr = new Regression();
    LabelingSimulation labeling = new BasicLabelingSimulation();

    doExperiment(randomsample, lr, labeling);
  }

  @Override
  public void doExperiment(BasicSampling sampling, Classifier classifier,
          LabelingSimulation labeling) {

    for (int i = 0; i < round; i++) {
      doExperiment(i, sampling, classifier, labeling);
    }
    Preprocess.clearPredictTable();

    for (int i = 0; i < round; i++) {
      testSampling(i, classifier);
    }
    Preprocess.clearPredictTable();

    testModel(classifier);

    storeInFile();
    plotResult("result", "Accuracy", Util.accuracy, "Accuracy", Util.testSamplingAccuracy,
            "RamdomLabel", Util.testModelAccuracy, "LabelAll");
  }
}
