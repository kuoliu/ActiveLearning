package edu.cmu.al.ml;

import java.util.ArrayList;
import java.util.List;

import weka.classifiers.functions.LibSVM;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.Util;

/**
 * Description: The SVM classifier
 */
public class SVM extends Classifier {
	private LibSVM svm;

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
			System.out.println(data.instance(0));
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
			this.svm.buildClassifier(newData);
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
			System.out.println(newData.numInstances());
			for (int i = 0; i < newData.numInstances(); i++) {
				double pred = this.svm.classifyInstance(newData.instance(i));
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
}


