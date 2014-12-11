package edu.cmu.al.feature;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.SqlManipulation;
import edu.cmu.al.util.Stanfordnlp;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;

/**
 * Check the negation within review summary
 * @author Kzuo Liu
 */
public class LanguageFeatureExtractor extends FeatureExtractor {

	class CountPair {
		int neg = 0;
		int cnt = 0;

		public void update(int neg, int cnt) {
			this.neg = neg;
			this.cnt = cnt;
		}

		public void update(CountPair pair) {
			this.neg += pair.neg;
			this.cnt += pair.cnt;
		}

		public float getRatio() {
			return ((float) neg / (float) cnt);
		}
	}

	private CountPair seeNeg(String str) {
		CountPair pair = new CountPair();
		Annotation document = Stanfordnlp.AnnotateDoc(str);
		List<CoreMap> sentences = Stanfordnlp.getSentences(document);
		for (CoreMap sentence : sentences) {
			if (Stanfordnlp.isNeg(sentence))
				++pair.neg;
			++pair.cnt;
		}
		return pair;
	}

	public int extractFeature(int featureId) {
		HashMap<String, CountPair> map = new HashMap<String, CountPair>();
		String sql = "select product_id, review_summary from product_review ";
		ResultSet rs = SqlManipulation.query(sql);
		try {
			while (rs.next()) {
				String productId = rs.getString(1);
				String summary = rs.getString(2);
				CountPair pair = seeNeg(summary);
				if (map.containsKey(productId)) {
					map.get(productId).update(pair);
				} else {
					map.put(productId, pair);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (String productId : map.keySet()) {
			CountPair pair = map.get(productId);

			String updateSql = "update " + Configuration.getFeatureTable()
					+ " set f" + featureId + "=? where product_id=?";
			SqlManipulation.update(updateSql, pair.getRatio(), productId);
		}
		linkFeatureidToFeature(featureId++, "negation in summary");
		return ++featureId;
	}
}
