package edu.cmu.al.ml;

import java.util.ArrayList;
import java.util.List;

import weka.classifiers.functions.LinearRegression;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.Util;

/**
 * Description: The logistic regression classifier
 */
public class Regression extends Classifier {
	private LinearRegression lr;

	public Regression() {
		this.lr = new LinearRegression();
	}

	@Override
	public void train() {
		try {

			//String sql = "select classifier_predict.product_id, f3, f2 from product_feature, classifier_predict where product_feature.product_id=classifier_predict.product_id and classifier_predict.islabeled = 1";
			String sql = "select " + Configuration.getPredictTable()
					+ ".product_id, f1, f3, f4, f5, f6, f7, f8, f2 from "
					+ Configuration.getFeatureTable() + " , "
					+ Configuration.getPredictTable() + " where "
					+ Configuration.getFeatureTable() + ".product_id = "
					+ Configuration.getPredictTable() + ".product_id and "
					+ Configuration.getPredictTable() + ".islabeled = 1";
			Instances data = getData(sql);
			//check whether the training data is load correctly
			System.out.println("First Training data:" + data.instance(0));
			Instances newData;

			// add the filter to filter the text attribute
			Remove remove = new Remove();
			int[] textAttr = new int[1];
			textAttr[0] = 1;
			remove.setAttributeIndicesArray(textAttr);
			remove.setInvertSelection(false);
			remove.setInputFormat(data);
			newData = Filter.useFilter(data, remove);
			

			// training
			newData.setClassIndex(newData.numAttributes() - 1);
			this.lr.buildClassifier(newData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() {
		try {
			//String sql = "select f1 from product_feature, classifier_predict where product_feature.product_id=classifier_predict.product_id and classifier_predict.islabeled = 0";
			String sql = "select " + Configuration.getPredictTable()
					+ ".product_id, f1, f3, f4, f5, f6, f7, f8, f2 from "
					+ Configuration.getFeatureTable() + " , "
					+ Configuration.getPredictTable() + " where "
					+ Configuration.getFeatureTable() + ".product_id = "
					+ Configuration.getPredictTable() + ".product_id and "
					+ Configuration.getPredictTable() + ".islabeled = 0";
			Instances data = getData(sql);
			Instances newData;
			
			Remove remove = new Remove();
			int[] textAttr = new int[1];
			textAttr[0] = 1;
			remove.setAttributeIndicesArray(textAttr);
			remove.setInvertSelection(false);
			remove.setInputFormat(data);
			newData = Filter.useFilter(data, remove);
			newData.setClassIndex(newData.numAttributes() - 1);
			
			List<PredictResult> result = new ArrayList<PredictResult>();
			for (int i = 0; i < newData.numInstances(); i++) {
				double pred = this.lr.classifyInstance(newData.instance(i));
				double predClass = pred > 4.0 ? 1.0 : 0.0;
				PredictResult pp = new PredictResult(data
						.instance(i).stringValue(0), pred, predClass);
				result.add(pp);
			}
			Util.updatePredictTable(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Regression lc = new Regression();
		lc.train();
		lc.test();
	}
}
