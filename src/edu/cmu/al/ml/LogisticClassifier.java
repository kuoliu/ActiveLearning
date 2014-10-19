package edu.cmu.al.ml;

import java.util.*;

import edu.cmu.al.util.*;
import weka.classifiers.functions.Logistic;
import weka.core.Instances;

/**
 * Description: The logistic regression classifier
 */
public class LogisticClassifier extends Classifier {
	private Logistic logistic;

	@Override
	public void train() {
		try {
			String sql = "select f3, f2 from product_feature, classifier_predict where product_feature.product_id=classifier_predict.product_id and classifier_predict.islabeled = 1";
			/*String sql = "select " + Configuration.getPredictTable() + ".f2 from + Configuration.getFeatureTable() + " , " 
					 + Configuration.getPredictTable() + " where " + Configuration.getFeatureTable() + ".product_id = "
					+ Configuration.getPredictTable() + ".product_id and " 
					+ Configuration.getPredictTable() + ".islabeled = true";*/
			Instances data = getData(sql);
			data.setClassIndex(data.numAttributes() - 1);
			this.logistic.buildClassifier(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() {
		try {
			String sql = "select * from" + Configuration.getFeatureTable() + " , " 
					 + Configuration.getPredictTable() + " where " + Configuration.getFeatureTable() + ".product_id = "
					+ Configuration.getPredictTable() + ".product_id and " 
					+ Configuration.getPredictTable() + ".islabeled = false";
			Instances data = getData(sql);
			List<Double> predictionValue = new ArrayList<Double>();
			for (int i = 0; i < data.numInstances(); i++) {
				double pred = this.logistic.classifyInstance(data.instance(i));
				predictionValue.add(pred);
			}
			Util.updatePredictTable(predictionValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}