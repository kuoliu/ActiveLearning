package edu.cmu.al.sampling;

import java.sql.ResultSet;
import java.util.HashSet;

import edu.cmu.al.util.*;
/**
 * Sampling is the base class of all sampling methods. It is responsible for
 * providing some instances for user to label.
 * 
 * @author yuanyuan
 * 
 */
public abstract class BasicSampling {
	ResultSet pool;

	public BasicSampling() {
	}

	/**
	 * Select k instances to mark in next round.
	 */
	public abstract HashSet<String> sampling(int k);

	/**
	 * Extract the class posterior probabilities for the unlabeled observations.
	 * 
	 * @param productId
	 * @return the posterior for a specific instance
	 */
	public Double getPredictResult(String productId) {
		String sql = "select confidence from "
				+ Configuration.getPredictTable() + " where product_id = "
				+ productId;
		ResultSet rs = SqlManipulation.query(sql);
		double confidence = 0.0;
		try {
			if (rs.next()) {
				confidence = rs.getDouble(4);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return confidence;
	}

	/**
	 * Return whether an observation is labeled or not.
	 * 
	 * @param productId
	 * @return true, labeled; false, unlabeled
	 */
	public boolean isLabled(String productId) {
		String sql = "select islabeled from " + Configuration.getPredictTable()
				+ "," + Configuration.getReviewTable() + " where "
				+ Configuration.getPredictTable() + ".product_id = "
				+ Configuration.getReviewTable() + ".product_id and "
				+ Configuration.getPredictTable() + ".product_id = " + productId;

		ResultSet rs = SqlManipulation.query(sql);
		try {
			if (rs.next()) {
				if (rs.getString(3).equals("true")) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}