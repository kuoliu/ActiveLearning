package edu.cmu.al.feature;

/**
 * Feature pipeline to extract all the features and store them into database
 * 
 * @author Kuo Liu
 */
public class FeaturePipeline {

	public static void produceFeatures(){
		int featureId = 1;
		FeatureExtractor featureExtractor = new BasicFeatureExtractor();
		featureId = featureExtractor.extractFeature(featureId);

		featureExtractor = new SentimentFeatureExtractor();
		featureId = featureExtractor.extractFeature(featureId);
		
		featureExtractor = new BiasVarianceFeatureExtractor();
		featureId = featureExtractor.extractFeature(featureId);
		
		featureExtractor = new LanguageFeatureExtractor();
//		featureId = featureExtractor.extractFeature(featureId);
	}
}
