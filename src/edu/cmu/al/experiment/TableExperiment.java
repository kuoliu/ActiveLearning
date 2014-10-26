package edu.cmu.al.experiment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import edu.cmu.al.main.Preprocess;
import edu.cmu.al.ml.Classifier;
import edu.cmu.al.sampling.BasicSampling;
import edu.cmu.al.simulation.LabelingSimulation;
import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.SqlManipulation;

public class TableExperiment implements Experiment {

  int round;

  Evaluator evaluator;

  double ratio;

  List<String> allProductIds;

  int result_id;

  public TableExperiment(int round, double ratio, int result_id) {
    clearPredictTable();
    this.round = round;
    this.ratio = ratio;
    this.evaluator = new Evaluator();
    this.allProductIds = new ArrayList<String>();
    this.result_id = result_id;
  }

  @Override
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
    insertResultTable(result_id, i, evaluator.computePrecision(), evaluator.computeAccuracy(),
            evaluator.computeRecall(), numberOfInstanceToLabel);
  }

  @Override
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
    insertResultTable(result_id, i, evaluator.computePrecision(), evaluator.computeAccuracy(),
            evaluator.computeRecall(), numberOfInstanceToLabel);
  }

  @Override
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
    insertResultTable(result_id, 0, evaluator.computePrecision(), evaluator.computeAccuracy(),
            evaluator.computeRecall(), numberOfInstanceToLabel);
  }

  @Override
  public void doExperiment(BasicSampling sampling, Classifier classifier,
          LabelingSimulation labeling, String outputFileName) {

    for (int i = 0; i < round; i++) {
      doExperiment(i, sampling, classifier, labeling);
    }
    clearPredictTable();
    result_id++;

    for (int i = 0; i < round; i++) {
      testSampling(i, classifier, labeling);
    }
    clearPredictTable();
    result_id++;

    testModel(classifier, labeling);
    clearPredictTable();
    result_id++;
  }

  private void insertResultTable(int rid, int round, double precision, double accuracy,
          double recall, double annotation_cost) {
    String sql = "insert into "
            + Configuration.getResultTable()
            + " (result_id, round, accuracy, precision, recall, annotation_cost) values (?, ?, ?, ?, ?, ?)";

    // System.out.println(sql);
    SqlManipulation.insert(sql, rid, round, precision, accuracy, recall, annotation_cost);
  }

  private void clearPredictTable() {
    String sql = "DROP TABLE IF EXISTS " + Configuration.getPredictTable();
    SqlManipulation.dropTable(sql);

    sql = "CREATE TABLE IF NOT EXISTS "
            + Configuration.getPredictTable()
            + " (product_id VARCHAR(256) primary key, islabeled INTEGER, user_label REAL, confidence REAL, predict_result REAL)";
    SqlManipulation.createTable(sql);

    Preprocess.initPredictTable();
  }

  private void clearResultTable() {
    String sql = "DELETE FROM " + Configuration.getResultTable();
    SqlManipulation.query(sql);
  }

  @Override
  public void doExperimentWithAllData(String outputFileName) {
    // TODO Auto-generated method stub

  }

  @Override
  public void storeInFile() {
    // TODO Auto-generated method stub

  }

  @Override
  public double[] getTestSamplingAccuracies() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public double[] getTestModelAccuracies() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public double[] getAccuracies() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void plotResult() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void plotResult(String outputFileName, String title, String... files) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void storeInFile(String outputFileName, double[] cost, double[] accuracy) {
    // TODO Auto-generated method stub
    
  }
}
