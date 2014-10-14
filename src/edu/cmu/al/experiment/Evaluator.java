package edu.cmu.al.experiment;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.ScoreDefine;
import edu.cmu.al.util.SqlManipulation;

public class Evaluator {

	private int TRUE_POS;
	private int TRUE_NEG;
	private int FALSE_POS;
	private int FALSE_NEG;

	public void evaluateClassification() {
		clear();
		try {
			getTruePos();
			getTrueNeg();
			getFalsePos();
			getFalseNeg();
			System.out.println(TRUE_POS + "\t" + TRUE_NEG + "\t" + FALSE_POS + "\t" + FALSE_NEG);
		} catch (SQLException e) {
			e.printStackTrace();
		}
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

	public void getTruePos() throws SQLException {
		String sql = "select count(*) from " + Configuration.getReviewTable() + " R," + Configuration.getPredictTable() + " P "
				+ "where R.product_id = P.product_id and P.predict_result = 1 and R.review_score >=" + ScoreDefine.posSocre;
		ResultSet rs = SqlManipulation.query(sql);
		while (rs.next()) {
			TRUE_POS = rs.getInt(1);
		}
	}

	public void getTrueNeg() throws SQLException {
		String sql = "select count(*) from " + Configuration.getReviewTable() + " R," + Configuration.getPredictTable() + " P "
				+ "where R.product_id = P.product_id and P.predict_result = 0 and R.review_score <" + ScoreDefine.posSocre;
		ResultSet rs = SqlManipulation.query(sql);
		while (rs.next()) {
			TRUE_NEG = rs.getInt(1);
		}
	}

	public void getFalsePos() throws SQLException {
		String sql = "select count(*) from " + Configuration.getReviewTable() + " R," + Configuration.getPredictTable() + " P "
				+ "where R.product_id = P.product_id and P.predict_result = 1 and R.review_score <" + ScoreDefine.posSocre;
		ResultSet rs = SqlManipulation.query(sql);
		while (rs.next()) {
			FALSE_POS = rs.getInt(1);
		}
	}

	public void getFalseNeg() throws SQLException {
		String sql = "select count(*) from " + Configuration.getReviewTable() + " R," + Configuration.getPredictTable() + " P "
				+ "where R.product_id = P.product_id and P.predict_result = 0 and R.review_score >=" + ScoreDefine.posSocre;
		ResultSet rs = SqlManipulation.query(sql);
		while (rs.next()) {
			FALSE_NEG = rs.getInt(1);
		}
	}

	public void clear() {
		this.TRUE_NEG = 0;
		this.TRUE_POS = 0;
		this.FALSE_NEG = 0;
		this.FALSE_POS = 0;
	}
}
