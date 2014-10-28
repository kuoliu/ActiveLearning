package edu.cmu.al.experiment;

import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.ScoreDefine;
import edu.cmu.al.util.SqlManipulation;

public class Evaluator implements EvaluatorInterface {

  private int TRUE_POS;

  private int TRUE_NEG;

  private int FALSE_POS;

  private int FALSE_NEG;

  public void evaluateClassification() {
    clear();
    getTruePos();
    getTrueNeg();
    getFalsePos();
    getFalseNeg();
    
    System.out.println(TRUE_POS + "\t" + TRUE_NEG + "\t" + FALSE_POS + "\t" + FALSE_NEG + "\t"
            + (TRUE_POS + TRUE_NEG + FALSE_POS + FALSE_NEG));
  }

  @Override
  public double computeAccuracy() {
    return (TRUE_NEG + TRUE_POS) / (double) (TRUE_NEG + TRUE_POS + FALSE_NEG + FALSE_POS);
  }

  @Override
  public double computePrecision() {
    return (TRUE_POS) / (double) (FALSE_POS + TRUE_POS);
  }

  @Override
  public double computeRecall() {
    return (TRUE_POS) / (double) (TRUE_POS + FALSE_NEG);
  }

  @Override
  public double computeFMeasure() {
    double a = 0.5;
    return 1 / (a / computePrecision() + (1 - a) / computeRecall());
  }

  public void getTruePos() {

    String sql = "select count(P.product_id) from "
            + Configuration.getFeatureTable()
            + " F,"
            + Configuration.getPredictTable()
            + " P where F.product_id = P.product_id and P.islabeled = 0 and P.predict_result = 1 and F.f2 >= 3";

    TRUE_POS = SqlManipulation.queryInt(sql);
    // System.out.println(sql);
  }

  public void getTrueNeg() {

    String sql = "select count(P.product_id) from "
            + Configuration.getFeatureTable()
            + " F,"
            + Configuration.getPredictTable()
            + " P where F.product_id = P.product_id and P.islabeled = 0 and P.predict_result = 0 and F.f2 < 3";

    TRUE_NEG = SqlManipulation.queryInt(sql);
    // System.out.println(sql);
  }

  public void getFalsePos() {

    String sql = "select count(P.product_id) from "
            + Configuration.getFeatureTable()
            + " F,"
            + Configuration.getPredictTable()
            + " P where F.product_id = P.product_id and P.islabeled = 0 and P.predict_result = 1 and F.f2 < 3";

    FALSE_POS = SqlManipulation.queryInt(sql);
    // System.out.println(sql);
  }

  public void getFalseNeg() {

    String sql = "select count(P.product_id) from "
            + Configuration.getFeatureTable()
            + " F,"
            + Configuration.getPredictTable()
            + " P where F.product_id = P.product_id and P.islabeled = 0 and P.predict_result = 0 and F.f2 >= 3";

    FALSE_NEG = SqlManipulation.queryInt(sql);
    // System.out.println(sql);
  }

  public void clear() {
    this.TRUE_NEG = 0;
    this.TRUE_POS = 0;
    this.FALSE_NEG = 0;
    this.FALSE_POS = 0;
  }
}
