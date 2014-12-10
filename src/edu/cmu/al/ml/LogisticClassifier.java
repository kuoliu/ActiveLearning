package edu.cmu.al.ml;

import java.util.ArrayList;
import java.util.List;

import weka.classifiers.functions.Logistic;
import weka.core.Attribute;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.ScoreDefine;
import edu.cmu.al.util.Util;

/**
 * Description: The logistic regression classifier
 * @author chenying
 */
public class LogisticClassifier extends Classifier {
	private Logistic logistic;

	public LogisticClassifier() {
		this.logistic = new Logistic();
	}

	/*
	 * 0: false 1: true
	 */

	@Override
	public void train() {
		try {
			// String sql =
			// "select classifier_predict.product_id, f3, f2 from product_feature, classifier_predict where product_feature.product_id=classifier_predict.product_id and classifier_predict.islabeled = 1";
			String sql = "select " + Configuration.getPredictTable()
					+ ".product_id, " + Configuration.getSelectedFeatures() + ", CASE WHEN f2 < "
					+ ScoreDefine.posSocre
					+ " THEN false ELSE true END as class from "
					+ Configuration.getFeatureTable() + " , "
					+ Configuration.getPredictTable() + " where "
					+ Configuration.getFeatureTable() + ".product_id = "
					+ Configuration.getPredictTable() + ".product_id and "
					+ Configuration.getPredictTable() + ".islabeled = 1";
			Instances data = getData(sql);
			
			// check whether the training data is load correctly
			// System.out.println("First Training data:" + data.instance(0));
			Instances newData;

			// add the filter to filter the text attribute
			Remove remove = new Remove();
			int[] textAttr = new int[1];
			textAttr[0] = 0;
			remove.setAttributeIndicesArray(textAttr);
			remove.setInvertSelection(false);
			remove.setInputFormat(data);
			newData = Filter.useFilter(data, remove);

			// training
			newData.setClassIndex(newData.numAttributes() - 1);
			this.logistic.buildClassifier(newData);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() {
		try {
			// String sql =
			// "select f1 from product_feature, classifier_predict where product_feature.product_id=classifier_predict.product_id and classifier_predict.islabeled = 0";
			String sql = "select " + Configuration.getPredictTable()
					+ ".product_id, " + Configuration.getSelectedFeatures() + ", CASE WHEN f2 < "
					+ ScoreDefine.posSocre
					+ " THEN false ELSE true END as class from "
					+ Configuration.getFeatureTable() + " , "
					+ Configuration.getPredictTable() + " where "
					+ Configuration.getFeatureTable() + ".product_id = "
					+ Configuration.getPredictTable() + ".product_id and "
					+ Configuration.getPredictTable() + ".islabeled = 0";
			Instances data = getData(sql);
			Instances newData;

			// Filter the non-numeric attribute
			Remove remove = new Remove();
			int[] textAttr = new int[1];
			textAttr[0] = 0;
			remove.setAttributeIndicesArray(textAttr);
			remove.setInvertSelection(false);
			remove.setInputFormat(data);
			newData = Filter.useFilter(data, remove);
			newData.setClassIndex(newData.numAttributes() - 1);

			// print out this logistic model
			// System.out.println(this.logistic.toString());

			Attribute at = newData.classAttribute();

			List<PredictResult> result = new ArrayList<PredictResult>();
			for (int i = 0; i < newData.numInstances(); i++) {
				double pred = this.logistic.classifyInstance(newData
						.instance(i));
				
				pred = Math.floor(pred * 100000000) / 100000000.0;
				//the probability of being true
				double confidence = this.logistic 
						.distributionForInstance(newData.instance(i))[1]; 
				confidence = Math.floor(confidence * 100000000) / 100000000.0;
				
				/*System.out.println("Confidence: " + confidence+
				 " Distribution: " +
				 this.logistic.distributionForInstance(newData.instance(i))[1]
				 + " " + at.value((int)pred));*/

				PredictResult pp = new PredictResult(data.instance(i)
						.stringValue(0), confidence, pred);
				result.add(pp);
//				System.out.println("confidence " + confidence + " pred " + pred);
			}
			
			Util.updatePredictTable(result, "lr_confidence");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		LogisticClassifier lc = new LogisticClassifier();
		lc.train();
		lc.test();
	}

}
