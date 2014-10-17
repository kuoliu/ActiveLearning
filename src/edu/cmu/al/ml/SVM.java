package edu.cmu.al.ml;

import java.util.*;

import edu.cmu.al.util.*;
import weka.classifiers.functions.LibSVM;
import weka.core.Instances;

/**
 * Description: The SVM classifier
 */
public class SVM extends Classifier {
	private LibSVM svm;

	@Override
	public void train() {
		try {
			String sql = "select * from" + Configuration.getPredictTable()
					+ "where label <> \'#\'";
			Instances data = getData(sql);
			data.setClassIndex(data.numAttributes() - 1);
			this.svm.buildClassifier(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() {
		try {
			String sql = "select * from" + Configuration.getPredictTable()
					+ "where label = \'#\'";
			Instances data = getData(sql);
			List<Double> predictionValue = new ArrayList<Double>();
			for (int i = 0; i < data.numInstances(); i++) {
				double pred = this.svm.classifyInstance(data.instance(i));
				predictionValue.add(pred);
			}
			Util.updatePredictTable(predictionValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}