package edu.cmu.al.feature;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.StringTokenizer;

import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.SqlManipulation;

/**
 * Extract sentiment dictionary based features including the ratio of positive
 * words and the ratio of negative words
 */
public class SentimentFeatureExtractor extends FeatureExtractor {


	HashSet<String> positiveWord = new HashSet<String>();
	HashSet<String> negativeWord = new HashSet<String>();

	private void readPNwords() {
		String sql = "select * from " + Configuration.getSentimentWordTable();
		ResultSet rs = SqlManipulation.query(sql);
		try {
			while (rs.next()) {

				if (rs.getString(3).equals("positive"))
					positiveWord.add(rs.getString(1));
				else {
					negativeWord.add(rs.getString(1));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void numSentimentText(int featureId) {
		
		String sql = "select product_id,  review_text  from " +
				Configuration.getReviewTable() + " order by product_id";
		ResultSet rs = SqlManipulation.query(sql);
		int positiveNum = 0;
		int negativeNum = 0;
		String updateSql = "update " + Configuration.getFeatureTable()
				+ " set f" + featureId + "=? where product_id=?";
		
		
		try {
			int i = 0;
			String text = "";
			String productId = "";
			while (rs.next()) {
				if(productId=="" || productId==rs.getString(1))
				{
					productId = rs.getString(1);
					text = text + rs.getString(2); 
				}
				else {
				
					StringTokenizer st = new StringTokenizer(text);
					while (st.hasMoreTokens()) {
						String word = st.nextToken();
						if (positiveWord.contains(word))
							positiveNum++;
						if(negativeWord.contains(word))
							negativeNum++;
					}
					
					double ratio = (double) (positiveNum+1) / (double) (1+negativeNum);
					SqlManipulation.update(updateSql, ratio, productId);
					text = rs.getString(2); 
					productId= rs.getString(1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

		private void numSentimentSummary(int featureId) {
		
		String sql = "select product_id,  review_summary  from " +
				Configuration.getReviewTable() + " order by product_id";
		ResultSet rs = SqlManipulation.query(sql);
		int positiveNum = 0;
		int negativeNum = 0;
		String updateSql = "update " + Configuration.getFeatureTable()
				+ " set f" + featureId + "=? where product_id=?";
		
		
		try {
			int i = 0;
			String text = "";
			String productId = "";
			while (rs.next()) {
				if(productId=="" || productId==rs.getString(1))
				{
					productId = rs.getString(1);
					text = text + rs.getString(2); 
				}
				else {
				
					StringTokenizer st = new StringTokenizer(text);
					while (st.hasMoreTokens()) {
						String word = st.nextToken();
						if (positiveWord.contains(word))
							positiveNum++;
						if(negativeWord.contains(word))
							negativeNum++;
					}
					
					double ratio = (double) (positiveNum+1) / (double) (1+negativeNum);
					SqlManipulation.update(updateSql, ratio, productId);
					text = rs.getString(2); 
					productId= rs.getString(1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	/**
	 * Extract basic features for each product
	 */
	public int extractFeature(int featureId) {
		readPNwords();
		numSentimentText(featureId);
		featureMap.put(featureId ++, "Number of sentiment positive and negative for a Product for text");
		
		numSentimentSummary(featureId);
		featureMap.put(featureId ++, "Number of sentiment positive and negative for a Product for summary");
		return featureId;
	}
}
