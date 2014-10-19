package edu.cmu.al.feature;

import java.util.*;
import java.sql.*;

import edu.cmu.al.util.*;

/**
 * Extract sentiment dictionary based features including the ratio of positive
 * words and the ratio of negative words
 * 
 */
public class SentimentFeatureExtractor extends FeatureExtractor {

	private static HashSet<String> positiveWords = null;
	private static HashSet<String> negativeWords = null;

	static {
		positiveWords = Util.loadFileToHashSet(Configuration
				.getSentimentPositiveDicPath());
		negativeWords = Util.loadFileToHashSet(Configuration
				.getSentimentNegativeDicPath());
	}

	public int extractFeature(int featureId) {

		HashMap<String, ReviewScore> map = new HashMap<String, ReviewScore>();
		String sql = "select product_id, review_summary, review_text from product_review";
		ResultSet rs = SqlManipulation.query(sql);
		try {
			while (rs.next()) {
				String productId = rs.getString(1);
				String summary = rs.getString(2);
				String review = rs.getString(3);
				if (map.containsKey(productId)) {
					map.get(productId).run(summary, review, positiveWords,
							negativeWords);
				} else {
					ReviewScore reviewScore = new ReviewScore();
					reviewScore.run(summary, review, positiveWords,
							negativeWords);
					map.put(productId, reviewScore);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (String productId : map.keySet()) {
			ReviewScore reviewScore = map.get(productId);

			String updateSql = "update " + Configuration.getFeatureTable()
					+ " set f" + featureId + "=? where product_id=?";
			SqlManipulation.update(updateSql,
					reviewScore.getPositiveRatioSummary(), productId);

			updateSql = "update " + Configuration.getFeatureTable()
					+ " set f" + (featureId + 1) + "=? where product_id=?";
			SqlManipulation.update(updateSql,
					reviewScore.getNegativeRatioSummary(), productId);

			updateSql = "update " + Configuration.getFeatureTable()
					+ " set f" + (featureId + 2) + "=? where product_id=?";
			SqlManipulation.update(updateSql,
					reviewScore.getPositiveRatioText(), productId);

			updateSql = "update " + Configuration.getFeatureTable()
					+ " set f" + (featureId + 3) + "=? where product_id=?";
			SqlManipulation.update(updateSql,
					reviewScore.getNegativeRatioText(), productId);

			updateSql = "update " + Configuration.getFeatureTable()
					+ " set f" + (featureId + 4) + "=? where product_id=?";
			SqlManipulation.update(updateSql,
					reviewScore.getPosNegRatioSummary(), productId);

			updateSql = "update " + Configuration.getFeatureTable()
					+ " set f" + (featureId + 5) + "=? where product_id=?";
			SqlManipulation.update(updateSql, reviewScore.getPosNegRatioText(),
					productId);
		}
		linkFeatureidToFeature(featureId++, "positive word ratio for summary");
		linkFeatureidToFeature(featureId++, "negative word ratio for summary");
		linkFeatureidToFeature(featureId++, "positive word ratio for text");
		linkFeatureidToFeature(featureId++, "negative word ratio for text");
		linkFeatureidToFeature(featureId++, "postive/negative for summary");
		linkFeatureidToFeature(featureId++, "positive/negative for text");
		return featureId;
	}
}

class ReviewScore {
	private int positiveCntSummary;
	private int negativeCntSummary;
	private int positiveCntText;
	private int negativeCntText;
	private int cntSummary;
	private int cntText;

	public void run(String summary, String review, HashSet<String> posDic,
			HashSet<String> negDic) {
		String[] summaryWords = summary.split("\\s+");
		String[] reviewWords = review.split("\\s+");
		for (String word : summaryWords) {
			if (posDic.contains(word))
				++positiveCntSummary;
			if (negDic.contains(word))
				++negativeCntSummary;
			++cntSummary;
		}
		for (String word : reviewWords) {
			if (posDic.contains(word))
				++positiveCntText;
			if (negDic.contains(word))
				++negativeCntText;
			++cntText;
		}
	}

	public float getPositiveRatioSummary() {
		return (float) positiveCntSummary / (float) cntSummary;
	}

	public float getNegativeRatioSummary() {
		return (float) negativeCntSummary / (float) cntSummary;
	}

	public float getPositiveRatioText() {
		return (float) positiveCntText / (float) cntText;
	}

	public float getNegativeRatioText() {
		return (float) negativeCntText / (float) cntText;
	}

	public float getPosNegRatioSummary() {
		return (float) (positiveCntSummary + 1) / (float) (negativeCntSummary + 1);
	}

	public float getPosNegRatioText() {
		return (float) (positiveCntText + 1) / (float) (negativeCntText + 1);
	}
}