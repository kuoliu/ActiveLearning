package edu.cmu.al.experiment;

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
		getTruePos();
		getTrueNeg();
		getFalsePos();
		getFalseNeg();
		System.out.println(TRUE_POS + "\t" + TRUE_NEG + "\t" + FALSE_POS + "\t" + FALSE_NEG);
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

	public void getTruePos() {
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
		FALSE_NEG = SqlManipulation.queryInt(sql);
	}

	public void clear() {
		this.TRUE_NEG = 0;
		this.TRUE_POS = 0;
		this.FALSE_NEG = 0;
		this.FALSE_POS = 0;
	}
}