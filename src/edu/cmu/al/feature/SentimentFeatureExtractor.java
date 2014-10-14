package edu.cmu.al.feature;

import java.util.ArrayList;

import edu.cmu.al.util.AmazonReview;

/**
 * Extract sentiment dictionary based features including the ratio of positive words
 * and the ratio of negative words
 */
public class SentimentFeatureExtractor extends FeatureExtractor {
    
	private Double computeSentimentScore(ArrayList<AmazonReview> reviews) {
		return null;
	}
	
	public int extractFeature(int featureId) {
		return 0;
	}
}
