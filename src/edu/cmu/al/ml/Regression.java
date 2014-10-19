package edu.cmu.al.ml;

import java.util.*;

import edu.cmu.al.util.*;
import weka.classifiers.functions.LinearRegression;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

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
			 
			String sql = "select classifier_predict.product_id, f3, f2 from product_feature, classifier_predict where product_feature.product_id=classifier_predict.product_id and classifier_predict.islabeled = 1";
			/*String sql = "select " + Configuration.getPredictTable() + ".f2 from + Configuration.getFeatureTable() + " , " 
					 + Configuration.getPredictTable() + " where " + Configuration.getFeatureTable() + ".product_id = "
					+ Configuration.getPredictTable() + ".product_id and " 
					+ Configuration.getPredictTable() + ".islabeled = true";*/
			Instances data = getData(sql);
			//System.out.println(data.numAttributes());
			//System.out.println(data.numClasses());
			/*String sql = "select f2, f3, f4, f1 from" + Configuration.getFeatureTable() + " , " 
					 + Configuration.getFeatureTable() + " where " + Configuration.getFeatureTable() + ".product_id = "
					+ Configuration.getPredictTable() + ".product_id) and " 
					+ Configuration.getPredictTable() + ".islabeled = true";
			Instances data = getData(sql);*/
			Remove remove = new Remove();
			int[] textAttr = new int[1];
			textAttr[0] = 1;
		    remove.setAttributeIndicesArray(textAttr);
		    remove.setInvertSelection(false);
		    remove.setInputFormat(data);
		    Instances newData;
		    newData = Filter.useFilter(data, remove);
			newData.setClassIndex(newData.numAttributes() - 1);
			System.out.println(newData.numClasses());
			this.lr.buildClassifier(newData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() {
		try {
			String sql = "select classifier_predict.product_id, f3, f2 from product_feature, classifier_predict where product_feature.product_id=classifier_predict.product_id and classifier_predict.islabeled = 0";
			/*String sql = "select product_id, f3, f2 from " + Configuration.getFeatureTable() + " , " 
					 + Configuration.getPredictTable() + " where " + Configuration.getFeatureTable() + ".product_id = "
					+ Configuration.getPredictTable() + ".product_id and " 
					+ Configuration.getPredictTable() + ".islabeled = 0";*/
			Instances data = getData(sql);
			List<ProductIDPredictionPair> predictionValue = new ArrayList<ProductIDPredictionPair>();
			for (int i = 0; i < data.numInstances(); i++) {
				double pred = this.lr.classifyInstance(data.instance(i));
				System.out.println(data.instance(i).stringValue(0));
				ProductIDPredictionPair pp = new ProductIDPredictionPair(data.instance(i).stringValue(0), pred);
				predictionValue.add(pp);
			}
			Util.updatePredictTable(predictionValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
