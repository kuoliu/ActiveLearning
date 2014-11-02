package edu.cmu.al.experiment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import edu.cmu.al.main.Preprocess;
import edu.cmu.al.ml.Classifier;
import edu.cmu.al.sampling.BasicSampling;
import edu.cmu.al.sampling.RandomStrategy;
import edu.cmu.al.simulation.LabelingSimulation;
import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.Printer;
import edu.cmu.al.util.SqlManipulation;

public class TableExperiment implements Experiment {

  int result_id;

  Evaluator evaluator;

  public TableExperiment() {
    clearPredictTable();
    clearResultTable();
    this.evaluator = new Evaluator();
    this.result_id = 0;
  }

  private void doExperiment(int i, int round, int numberOfInstanceToLabel, BasicSampling sampling,
          Classifier classifier, LabelingSimulation labeling, String column) {
    if (i < 0 || i >= round) {
      System.out.println("Experiment error...");
      return;
    }

    // int numberOfInstanceToLabel = (int) Math.floor((labeling.getAllNumber() / round) * ratio);

    // print
    System.out.println("Round: " + i + '\t' + "ToLabel: " + numberOfInstanceToLabel + "\t"
            + "Unlabeled: " + labeling.getUnlabeledNumber());

    if (i == 0){
    	sampling = new RandomStrategy();
    }
    Set<String> productIds = sampling.sampling(numberOfInstanceToLabel ,column);




    labeling.labelProductId(productIds);

    classifier.train();
    classifier.test();

    evaluator.evaluateClassification();
    insertResultTable(result_id, i, evaluator.computePrecision(), evaluator.computeAccuracy(),
            evaluator.computeRecall(), numberOfInstanceToLabel);
  }

  private void insertResultTable(int rid, int round, double precision, double accuracy,
          double recall, double annotation_cost) {
    String sql = "insert into "
            + Configuration.getResultTable()
            + " (result_id, round, accuracy, precision, recall, annotation_cost) values (?, ?, ?, ?, ?, ?)";

    // System.out.println(sql);
    SqlManipulation.insert(sql, rid, round, accuracy, precision, recall, annotation_cost);
  }

  private void clearPredictTable() {
    String sql = "DELETE FROM " + Configuration.getPredictTable();
    SqlManipulation.delete(sql);
    Preprocess.initPredictTable();
  }

  private void clearResultTable() {
    String sql = "DELETE FROM " + Configuration.getResultTable();
    SqlManipulation.delete(sql);
  }

  @Override
  public void plotResult(String outputFileName, String title, String... files) {
    Plot p = new PyPlot();
    p.linePlot(outputFileName, title, files);
  }

  @Override
  public void doExperiment(int round, int numberOfInstanceToLabel, BasicSampling sampling,
          Classifier classifier, LabelingSimulation labeling, String column,  String outputFileName) {

    for (int i = 0; i < round; i++) {
      doExperiment(i, round, numberOfInstanceToLabel, sampling, classifier, labeling, column);
    }
    clearPredictTable();
    WriteInFile(result_id, outputFileName);
    result_id++;
  }

  private void WriteInFile(int result_id, String outputFileName) {
    if (outputFileName == null || outputFileName.length() == 0) {
      return;
    }

    Printer print = new Printer("output/" + outputFileName);
    String sql = "select * from " + Configuration.getResultTable()
            + " where result_id=? order by round asc";

    ResultSet rs = SqlManipulation.query(sql, result_id);

    double totalCost = 0;

    try {
      while (rs.next()) {
        totalCost += rs.getDouble(6);
        print.println(totalCost + " " + rs.getDouble(3) + " " + rs.getDouble(4) + " "
                + rs.getDouble(5));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    print.close();
  }

}
