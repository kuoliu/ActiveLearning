package edu.cmu.al.sampling;

import java.sql.ResultSet;
import java.util.HashSet;

import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.SqlManipulation;



/**
 * Sampling is the base class of all sampling methods. It is responsible for
 * providing some instances for user to label.
 * @author yuanyuan
 *
 */
public abstract class BasicSampling {
	ResultSet pool;
	
	public BasicSampling() {

	}

	/**
	 * Select k instances to mark in next round.
	 * @param k instances
	 */
	public abstract HashSet<String> sampling(int k);
	
	
	/**
	 * Mark those selected observations in NotationTable.
	 * @param selected, the set of line id
     */
	public void setPredictTable(HashSet<String> selected) {
		String updateSql = "update " + Configuration.getPredictTable()
				+ " set notation = # where product_id=?";
		try {
			for (String pid : selected) {
				SqlManipulation.update(updateSql, pid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	 
	
	/**
	 * Extract the class posterior probabilities for the unlabeled observations.
	 * @param id, line id
	 * @return the posterior for a specific instance
	 */
	public Double get_predict_result(String product_id) {
		String sql = "select confidence from "
				+ Configuration.getPredictTable() 
				+ " where product_id = " + product_id;
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
	 * @param id, line id
	 * @return true, labeled; false, unlabeled
	 */
	public boolean isLabled(String prod_id) {
		String sql = "select islabeled from "
				+ Configuration.getPredictTable() + "," + Configuration.getReviewTable()
				+ " where " + Configuration.getPredictTable() + ".product_id = " 
				+ Configuration.getReviewTable() + ".product_id and "
				+ Configuration.getPredictTable() + ".product_id = "  + prod_id;
		
		ResultSet rs = SqlManipulation.query(sql);
		try {
			if (rs.next()) {
				if(rs.getString(3).equals("true")) {
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
