package edu.cmu.al.feature;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.StringTokenizer;

import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.SqlManipulation;

/**
 * 
 * For the BasicFeature, we choose two feature which are the number of the
 * reviews for each product and average score for each product.
 * 
 * @author Kuo Liu
 */ 

public class BasicFeatureExtractor extends FeatureExtractor {

	private void avgReviewScore(int featureId) {
		String sql = "select product_id, avg(review_score) from "
				+ Configuration.getReviewTable() + " group by product_id";
		ResultSet rs = SqlManipulation.query(sql);
		String updateSql = "update " + Configuration.getFeatureTable()
				+ " set f" + featureId + "=? where product_id=?";
		try {
			while (rs.next()) {
				SqlManipulation.update(updateSql, rs.getFloat(2),
						rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void numReviews(int featureId) {
		String sql = "select product_id, count(*) from "
				+ Configuration.getReviewTable() + " group by product_id";
		ResultSet rs = SqlManipulation.query(sql);
		String updateSql = "update " + Configuration.getFeatureTable()
				+ " set f" + featureId + "=? where product_id=?";
		try {
			while (rs.next()) {
				SqlManipulation
						.update(updateSql, rs.getInt(2), rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Extract basic features for each product
	 */
	public int extractFeature(int featureId) {
		numReviews(featureId);
		featureMap.put(featureId ++, "Number of Reviews for a Product");
		avgReviewScore(featureId);
		featureMap.put(featureId ++, "Average Review Score for a Product");
		
		
		
		return featureId;
	}
}
