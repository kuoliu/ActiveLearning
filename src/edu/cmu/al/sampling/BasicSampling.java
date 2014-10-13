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
	public abstract void sampling(int k);
	
	
	/**
	 * Mark those selected observations in NotationTable.
	 * @param selected, the set of line id
	 */
	public void setNotationTable(HashSet<Integer> selected) {
		String updateSql = "update " + Configuration.getNotationTable()
				+ " set notation = # where id=?";
		try {
			for (int line : selected) {
				SqlManipulation.update(updateSql, line);
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
	public Double get_predict_result(int id) {
		String sql = "select confidence from "
				+ Configuration.getPredictTable() 
				+ " where id = " + id;
		ResultSet rs = SqlManipulation.query(sql);
		double confidence = 0.0;
		try {
			if (rs.next()) {
				confidence = rs.getFloat(1);
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
	public boolean isLabled(int id) {
		String sql = "select label from "
				+ Configuration.getPredictTable() 
				+ " where id = " + id;
		ResultSet rs = SqlManipulation.query(sql);
		try {
			if (rs.next()) {
				if(rs.getString(3).equals("#")) {
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
