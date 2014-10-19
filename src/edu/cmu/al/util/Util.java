package edu.cmu.al.util;

import java.util.List;

import edu.cmu.al.ml.ProductIDPredictionPair;

/**
 * Description: Some commonly used function across the project can be written
 * here
 * 
 * @author Kuo Liu
 */
public class Util {

	public static void updatePredictTable(List<ProductIDPredictionPair> predictionValue) {
		String updateSql = "update " + Configuration.getPredictTable()
				+ " set predict_result=? where product_id=?";
		try {
			for (int i = 0; i < predictionValue.size(); i++) {
				SqlManipulation
						.update(updateSql, predictionValue.get(i).getPrediction(), predictionValue.get(i).getProductId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
