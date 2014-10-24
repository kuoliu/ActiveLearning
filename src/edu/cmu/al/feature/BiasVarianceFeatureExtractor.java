package edu.cmu.al.feature;

import java.sql.ResultSet;
import java.util.*;

import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.SqlManipulation;

/**
 * Extract feature to consider the bias variance
 * 
 * @author Kuo Liu
 */
public class BiasVarianceFeatureExtractor extends FeatureExtractor {

	class Score {
		int cnt;
		float sumScore;

		public Score(float score) {
			cnt = 1;
			sumScore = score;
		}

		public void add(float score) {
			++cnt;
			sumScore += score;
		}

		public float avg() {
			return sumScore / cnt;
		}
	}

	private void reviewScoreVar(int featureId) {
		String sql = "select product_id, variance(review_score) from "
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

	private void userBasedFeature(int featureId, String sql) {
		ResultSet rs = SqlManipulation.query(sql);
		HashMap<String, Float> userAvgScore = new HashMap<String, Float>();
		try {
			while (rs.next()) {
				userAvgScore.put(rs.getString(1), rs.getFloat(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlManipulation.closeResultSet(rs);
		}

		sql = "select product_id, review_userid from "
				+ Configuration.getReviewTable();
		rs = SqlManipulation.query(sql);
		HashMap<String, Score> productUserAvg = new HashMap<String, Score>();
		try {
			while (rs.next()) {
				String productId = rs.getString(1);
				String userId = rs.getString(2);
				float score = userAvgScore.get(userId);
				if (!productUserAvg.containsKey(productId)) {
					Score newscore = new Score(score);
					productUserAvg.put(productId, newscore);
				} else {
					productUserAvg.get(productId).add(score);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlManipulation.closeResultSet(rs);
		}

		String updateSql = "update " + Configuration.getFeatureTable()
				+ " set f" + featureId + "=? where product_id=?";
		for (String pId : productUserAvg.keySet()) {
			SqlManipulation.update(updateSql, productUserAvg.get(pId).avg(),
					pId);
		}
	}

	private void userBias(int featureId) {
		String sql = "select review_userid, avg(review_score) from "
				+ Configuration.getReviewTable() + " group by review_userid";
		userBasedFeature(featureId, sql);
		sql = "select review_userid, variance(review_score) from "
				+ Configuration.getReviewTable() + " group by review_userid";
		userBasedFeature(featureId + 1, sql);
	}

	public int extractFeature(int featureId) {
		reviewScoreVar(featureId);
		featureMap.put(featureId++, "variance in review score");
		userBias(featureId);
		featureMap.put(featureId++, "user bias in review score");
		featureMap.put(featureId++, "user variance in review score");
		return featureId;
	}
}