package edu.cmu.al.experiment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import edu.cmu.al.main.Preprocess;
import edu.cmu.al.ml.Classifier;
import edu.cmu.al.sampling.BasicSampling;
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

  private void doExperiment(int i, int round, double ratio, BasicSampling sampling, Classifier classifier,
          LabelingSimulation labeling) {
    if (i < 0 || i >= round) {
      System.out.println("Experiment error...");
      return;
    }

    int numberOfInstanceToLabel = (int) Math.floor((labeling.getAllNumber() / round) * ratio);

    // print
    System.out.println("Round: " + i + '\t' + "ToLabel: " + numberOfInstanceToLabel + "\t"
            + "Unlabeled: " + labeling.getUnlabeledNumber());

    Set<String> productIds = sampling.sampling(numberOfInstanceToLabel);

    labeling.labelProductId(productIds);

    classifier.train();
    classifier.test();

    evaluator.clear();
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
    SqlManipulation.delete(sql);
  }

  @Override
  public void plotResult(String outputFileName, String title, String... files) {
    Plot p = new PyPlot();
    p.linePlot(outputFileName, title, files);
  }

  @Override
  public void doExperiment(int round, double ratio, BasicSampling sampling, Classifier classifier,
          LabelingSimulation labeling, String outputFileName) {

    for (int i = 0; i < round; i++) {
      doExperiment(i, round, ratio, sampling, classifier, labeling);
    }
    clearPredictTable();
    WriteInFile(result_id, outputFileName);
    result_id++;
  }

  private void WriteInFile(int result_id, String outputFileName) {
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
