package edu.cmu.al.feature;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import javax.xml.soap.Text;

import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.SqlManipulation;
import edu.cmu.al.util.Util;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

public class StandfordNLPSentimentFeature extends FeatureExtractor {
	public HashMap<String, Integer> productSentimentSummaryscore = new HashMap<String, Integer>();
	public HashMap<String, Integer> productSentimentTextscore = new HashMap<String, Integer>();

	public void sentimentword(String str) {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		Annotation annotation = pipeline.process(str);
		List<CoreMap> sentences = annotation
				.get(CoreAnnotations.SentencesAnnotation.class);

		for (CoreMap sentence : sentences) {
			String sentiment = sentence
					.get(SentimentCoreAnnotations.ClassName.class);
			System.out.println(sentiment + "\t" + sentence);
		}

	}

	public int findSentiment(String line) {

		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		int mainSentiment = 0;
		if (line != null && line.length() > 0) {
			int longest = 0;
			Annotation annotation = pipeline.process(line);
			for (CoreMap sentence : annotation
					.get(CoreAnnotations.SentencesAnnotation.class)) {
				Tree tree = sentence
						.get(SentimentCoreAnnotations.AnnotatedTree.class);
				int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
				String partText = sentence.toString();
				if (partText.length() > longest) {
					mainSentiment += sentiment;
					longest = partText.length();
				}

			}
		}

		return mainSentiment;

	}

	public int extractFeature(int featureId) {

		
		String sql = "select product_id, review_summary, review_text from product_review order by product_id";

		ResultSet rs = SqlManipulation.query(sql);
		try {
			String productId, summary, review;
			productId = "";
			int sentimentSummaryScore = 0;
			int sentimentTextScore = 0;
			while (rs.next()) {
				summary = rs.getString(2);
				review = rs.getString(3);
				if (rs.getString(1) != productId) {

					if (!productId.equals("")) {
						productSentimentSummaryscore.put(productId,
								sentimentSummaryScore);
						productSentimentSummaryscore.put(productId,
								sentimentTextScore);

						sentimentSummaryScore = findSentiment(summary);
						sentimentTextScore = findSentiment(review);
					}
					productId = rs.getString(1);
				} else {
					sentimentSummaryScore += findSentiment(summary);
					sentimentTextScore += findSentiment(review);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (String productId : productSentimentSummaryscore.keySet()) {
			int summaryScore = productSentimentSummaryscore.get(productId);
			int textScore = productSentimentTextscore.get(productId);
			
			String updateSql = "update " + Configuration.getFeatureTable()
					+ " set f" + featureId + "=? where product_id=?";
			SqlManipulation.update(updateSql,
					summaryScore, productId);

			updateSql = "update " + Configuration.getFeatureTable() + " set f"
					+ (featureId ) + "=? where product_id=?";
			SqlManipulation.update(updateSql,
					textScore, productId);
			
			updateSql = "update " + Configuration.getFeatureTable() + " set f"
					+ (featureId + 1) + "=? where product_id=?";

		}
		
		linkFeatureidToFeature(featureId++, " sentiment score for summary by Standford");
		linkFeatureidToFeature(featureId++, " sentiment score for text by Standford");

		return featureId + 2;
	}
}
