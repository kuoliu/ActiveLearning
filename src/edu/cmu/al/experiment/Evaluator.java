package edu.cmu.al.experiment;

import edu.cmu.al.util.Configuration;
<<<<<<< HEAD
=======
import edu.cmu.al.util.ScoreDefine;
>>>>>>> 5d31dc96a03b17593ca0bbef066ba6008f5e3187
import edu.cmu.al.util.SqlManipulation;

public class Evaluator {

	private int TRUE_POS;
	private int TRUE_NEG;
	private int FALSE_POS;
	private int FALSE_NEG;

	public void evaluateClassification() {
		clear();
<<<<<<< HEAD
		String sql = "select real_label, predict_label from " + Configuration.getFeatureTable();
		ResultSet rs = SqlManipulation.query(sql);
		try {
			while (rs.next()) {
				int realLabel = rs.getInt(1);
				int predictLabel = rs.getInt(2);
				if (realLabel == 1 && predictLabel == 1) {
					incTruePos(); // d
				} else if (realLabel == 0 && predictLabel == 0) {
					incTrueNeg(); // a
				} else if (realLabel == 1 && predictLabel == 0) {
					incFalseNeg(); // c
				} else if (realLabel == 0 && predictLabel == 1) {
					incFalsePos(); // b
				}
			}
		try {
			getTruePos();
			getTrueNeg();
			getFalsePos();
			getFalseNeg();
		} catch (SQLException e) {
			e.printStackTrace();
		}
=======
		getTruePos();
		getTrueNeg();
		getFalsePos();
		getFalseNeg();
		System.out.println(TRUE_POS + "\t" + TRUE_NEG + "\t" + FALSE_POS + "\t" + FALSE_NEG);
>>>>>>> 8327d5b2156ac1eb7b8822233ef80a44b326bb7f
	}

	public double computeAccuracy() {
		return (TRUE_NEG + TRUE_POS) / (double) (TRUE_NEG + TRUE_POS + FALSE_NEG + FALSE_POS);
	}

	public double computePrecision() {
		return (TRUE_POS) / (double) (FALSE_POS + TRUE_POS);
	}

	public double computeRecall() {
		return (TRUE_POS) / (double) (TRUE_POS + FALSE_NEG);
	}

<<<<<<< HEAD
	public void incTruePos() {
		TRUE_POS++;
	}

	public void incTrueNeg() {
		TRUE_NEG++;
	}

	public void incFalsePos() {
		FALSE_POS++;
	}

	public void incFalseNeg() {
		FALSE_NEG++;

	public void getTruePos() throws SQLException {
=======
	public void getTruePos() {
>>>>>>> 8327d5b2156ac1eb7b8822233ef80a44b326bb7f
		String sql = "select count(*) from " + Configuration.getReviewTable() + " R," + Configuration.getPredictTable() + " P "
				+ "where R.product_id = P.product_id and P.predict_result = 1 and R.review_score >=" + ScoreDefine.posSocre;
		TRUE_POS = SqlManipulation.queryInt(sql);
	}

	public void getTrueNeg() {
		String sql = "select count(*) from " + Configuration.getReviewTable() + " R," + Configuration.getPredictTable() + " P "
				+ "where R.product_id = P.product_id and P.predict_result = 0 and R.review_score <" + ScoreDefine.posSocre;
		TRUE_NEG = SqlManipulation.queryInt(sql);
	}

	public void getFalsePos() {
		String sql = "select count(*) from " + Configuration.getReviewTable() + " R," + Configuration.getPredictTable() + " P "
				+ "where R.product_id = P.product_id and P.predict_result = 1 and R.review_score <" + ScoreDefine.posSocre;
		FALSE_POS = SqlManipulation.queryInt(sql);
	}

	public void getFalseNeg() {
		String sql = "select count(*) from " + Configuration.getReviewTable() + " R," + Configuration.getPredictTable() + " P "
				+ "where R.product_id = P.product_id and P.predict_result = 0 and R.review_score >=" + ScoreDefine.posSocre;
<<<<<<< HEAD
		ResultSet rs = SqlManipulation.query(sql);
		while (rs.next()) {
			FALSE_NEG = rs.getInt(1);
		}

=======
		FALSE_NEG = SqlManipulation.queryInt(sql);
>>>>>>> 8327d5b2156ac1eb7b8822233ef80a44b326bb7f
	}

	public void clear() {
		this.TRUE_NEG = 0;
		this.TRUE_POS = 0;
		this.FALSE_NEG = 0;
		this.FALSE_POS = 0;
	}
}
