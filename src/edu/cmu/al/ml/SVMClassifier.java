package edu.cmu.al.ml;

import java.util.ArrayList;
import java.util.List;

import weka.classifiers.functions.LibSVM;
import weka.core.Attribute;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.ScoreDefine;
import edu.cmu.al.util.Util;

/**
 * Description: The SVM classifier
 * @author chenying 
 */
public class SVMClassifier extends Classifier {
	private LibSVM libsvm;

	/*public SVMClassifier() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class svmClass = Class.forName("LibSVM");
		this.svm = (LibSVM)svmClass.newInstance();
	}*/
	public SVMClassifier() {
		this.libsvm = new LibSVM();
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
					+ ".product_id, f4, f7, f10, CASE WHEN f2 < "
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
			this.libsvm.setProbabilityEstimates(true); //configure svm to get the probability 
			this.libsvm.buildClassifier(newData);

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
					+ ".product_id, f4, f7, f10, CASE WHEN f2 < "
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
				double pred = this.libsvm.classifyInstance(newData.instance(i));
				//the probability of being true 
				double confidence = this.libsvm.distributionForInstance(newData.instance(i))[1];
				/* System.out.println("Confidence: " + confidence +		 
				  " " + at.value((int)pred));*/

				PredictResult pp = new PredictResult(data.instance(i)
						.stringValue(0), confidence, pred);
				result.add(pp);
			}
			Util.updatePredictTable(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SVMClassifier svm = new SVMClassifier();
		svm.train();
		svm.test();
	}

}
