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
		String sql = "select product_id, review_summary, review_text from product_review ";
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

			updateSql = "update " + Configuration.getFeatureTable() + " set f"
					+ (featureId + 1) + "=? where product_id=?";
			SqlManipulation.update(updateSql,
					reviewScore.getNegativeRatioSummary(), productId);

			updateSql = "update " + Configuration.getFeatureTable() + " set f"
					+ (featureId + 2) + "=? where product_id=?";
			SqlManipulation.update(updateSql,
					reviewScore.getPositiveRatioText(), productId);

			updateSql = "update " + Configuration.getFeatureTable() + " set f"
					+ (featureId + 3) + "=? where product_id=?";
			SqlManipulation.update(updateSql,
					reviewScore.getNegativeRatioText(), productId);

			updateSql = "update " + Configuration.getFeatureTable() + " set f"
					+ (featureId + 4) + "=? where product_id=?";
			SqlManipulation.update(updateSql,
					reviewScore.getPosNegRatioSummary(), productId);

			updateSql = "update " + Configuration.getFeatureTable() + " set f"
					+ (featureId + 5) + "=? where product_id=?";
			SqlManipulation.update(updateSql, reviewScore.getPosNegRatioText(),
					productId);
			
//			updateSql = "update " + Configuration.getFeatureTable() + " set f"
//					+ (featureId + 6) + "=? where product_id=?";
//			SqlManipulation.update(updateSql, reviewScore.getSumPMISummary(),
//					productId);
//			
//			updateSql = "update " + Configuration.getFeatureTable() + " set f"
//					+ (featureId + 7) + "=? where product_id=?";
//			SqlManipulation.update(updateSql, reviewScore.getSumPMIText(),
//					productId);
			
		}
		linkFeatureidToFeature(featureId++, "positive word ratio for summary");
		linkFeatureidToFeature(featureId++, "negative word ratio for summary");
		linkFeatureidToFeature(featureId++, "positive word ratio for text");
		linkFeatureidToFeature(featureId++, "negative word ratio for text");
		linkFeatureidToFeature(featureId++, "postive/negative for summary");
		linkFeatureidToFeature(featureId++, "positive/negative for text");
//		linkFeatureidToFeature(featureId++, "PMI postive/negative for summary");
//		linkFeatureidToFeature(featureId++, "PMI positive/negative for text");
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
	private static HashMap<String, Integer> oneGramSummaryNum = new HashMap<String, Integer>();
	private static HashMap<String, Integer> twoGramSummaryPositiveNum = new HashMap<String, Integer>();
	private static HashMap<String, Integer> twoGramSummaryNegativeNum = new HashMap<String, Integer>();

	private static HashMap<String, Integer> oneGramTextNum = new HashMap<String, Integer>();
	private static HashMap<String, Integer> twoGramTextPositiveNum = new HashMap<String, Integer>();
	private static HashMap<String, Integer> twoGramTextNegativeNum = new HashMap<String, Integer>();

	public void computeTwoGram(HashMap<String, Integer> twoGramPositiveNum, HashMap<String, Integer> twoGramNegativeNum, String word1, String word2, HashSet<String> posDic,
			HashSet<String> negDic)
	{
		if (posDic.contains(word1))
		{
			if(twoGramPositiveNum.containsKey(word2))
				twoGramPositiveNum.put(word2,
						twoGramPositiveNum.get(word2) + 1);
			else
				twoGramPositiveNum.put(word2, 1);
		}
	
		if (negDic.contains(word1))
		{
			if(twoGramNegativeNum.containsKey(word2))
				twoGramNegativeNum.put(word2,
						twoGramNegativeNum.get(word2) + 1);
			else
				twoGramNegativeNum.put(word2, 1);
		}
	}
	
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

		String previousWord = "";
		for (String word : summaryWords) {
			if (previousWord != "") {
				// summary
				computeTwoGram(twoGramSummaryPositiveNum, twoGramSummaryNegativeNum, previousWord, word, posDic, negDic);
				
				computeTwoGram(twoGramSummaryPositiveNum, twoGramSummaryNegativeNum, word, previousWord, posDic, negDic);
				
			}
			if(oneGramSummaryNum.containsKey(word))
				oneGramSummaryNum.put(word, oneGramSummaryNum.get(word) + 1);
			else
				oneGramSummaryNum.put(word, 1);
			previousWord = word;

		}

		previousWord = "";
		for (String word : reviewWords) {
			if (previousWord != "") {
				computeTwoGram(twoGramTextPositiveNum, twoGramTextNegativeNum, previousWord, word, posDic, negDic);
				
				computeTwoGram(twoGramTextPositiveNum, twoGramTextNegativeNum, word, previousWord, posDic, negDic);
				
			}
			if(oneGramTextNum.containsKey(word))
				oneGramTextNum.put(word, oneGramTextNum.get(word) + 1);
			else
				oneGramTextNum.put(word,  1);
			previousWord = word;

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
		return (float) (positiveCntSummary + 1)
				/ (float) (negativeCntSummary + 1);
	}

	public float getPosNegRatioText() {
		return (float) (positiveCntText + 1) / (float) (negativeCntText + 1);
	}

	private float getPMICompute(int countX, int countY, int countXY, int cnt) {
		float ans = 0;
		ans = (float)Math.log( ((double)cnt *countXY+1.0) / (1.0+(double)countX * countY) );
		return ans;
	}

	public float getSumPMISummary(){
		float ans = 0;
		for(String word: oneGramSummaryNum.keySet())
		{
			float positvePMI = 0;
			if(twoGramSummaryPositiveNum.containsKey(word))
				positvePMI = getPMICompute(positiveCntSummary, oneGramSummaryNum.get(word), twoGramSummaryPositiveNum.get(word), cntSummary);
			else
				positvePMI = getPMICompute(positiveCntSummary, oneGramSummaryNum.get(word), 0, cntSummary);
			
			float negativePMI = 0;
			if(twoGramSummaryNegativeNum.containsKey(word))
				negativePMI = getPMICompute(negativeCntSummary, oneGramSummaryNum.get(word), twoGramSummaryNegativeNum.get(word), cntSummary);
			else
				negativePMI = getPMICompute(negativeCntSummary, oneGramSummaryNum.get(word), 0, cntSummary);
			ans = positvePMI - negativePMI + ans; 
		}
		return ans;
	}

	public float getSumPMIText(){
		float ans = 0;
		for(String word: oneGramTextNum.keySet())
		{
			float positvePMI = 0;
			if(twoGramTextPositiveNum.containsKey(word))
				positvePMI = getPMICompute(positiveCntText, oneGramTextNum.get(word), twoGramTextPositiveNum.get(word), cntText);
			else
				positvePMI = getPMICompute(positiveCntText, oneGramTextNum.get(word), 0, cntText);
			
			float negativePMI = 0;
			if(twoGramTextNegativeNum.containsKey(word))
				negativePMI = getPMICompute(positiveCntText, oneGramTextNum.get(word), twoGramTextNegativeNum.get(word), cntText);
			else
				negativePMI = getPMICompute(positiveCntText, oneGramTextNum.get(word), 0, cntText);
			ans = positvePMI - negativePMI + ans; 
		}
		return ans;
	}
}