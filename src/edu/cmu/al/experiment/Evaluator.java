package edu.cmu.al.experiment;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.SqlManipulation;

public class Evaluator {

	private int TRUE_POS;
	private int TRUE_NEG;
	private int FALSE_POS;
	private int FALSE_NEG;

	public void evaluateClassification() {
		clear();
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
	}

	public void clear() {
		this.TRUE_NEG = 0;
		this.TRUE_POS = 0;
		this.FALSE_NEG = 0;
		this.FALSE_POS = 0;
	}
}