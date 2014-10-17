package edu.cmu.al.util;

import java.util.List;

/**
 * Description: Some commonly used function across the project can be written
 * here
 * 
 * @author Kuo Liu
 */
public class Util {

	public static void updatePredictTable(List<Double> predictionValue) {
		String updateSql = "update " + Configuration.getPredictTable()
				+ " set predictValue=? where id=?";
		try {
			for (int i = 0; i < predictionValue.size(); i++) {
				SqlManipulation
						.update(updateSql, predictionValue.get(i), i + 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
