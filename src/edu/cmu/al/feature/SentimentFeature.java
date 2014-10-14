package edu.cmu.al.feature;

import java.util.ArrayList;

import edu.cmu.al.util.AmazonReview;

/**
 *  We use some of positive and negative words and match that as 
 * */
public class SentimentFeature extends Feature {
	/**
	   *  We parse some of the sentence in the review summary and review text
	   *  Compute the Sentimentfeature score with some NLP package
	   * @param reviews
	   *          All people's reviews for each product
	   * @return  Sentimentfeature score for certain product.
	   */
	public Double computeSentimentScore(ArrayList<AmazonReview> reviews) {
		return null;
	}
	
	public int extractFeature(int featureId) {
		return 0;
	}
}
