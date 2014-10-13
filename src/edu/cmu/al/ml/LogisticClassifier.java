package edu.cmu.al.ml;

import java.util.ArrayList;
import java.util.List;

import edu.cmu.al.util.Configuration;
import weka.classifiers.functions.Logistic;
import weka.core.Instances;
import weka.experiment.InstanceQuery;

public class LogisticClassifier implements Classifier {
	private Logistic logistic;

	@Override
	public void train() {
		try {
			InstanceQuery query = new InstanceQuery();
			// load data from databse
			query.setDatabaseURL(Configuration.getSqlUrl());
			query.setUsername(Configuration.getSqlUserName());
			query.setPassword(Configuration.getSqlPassword());
			String sql = "select * from" + Configuration.getPredictTable()
					+ "where label <> \'#\'";
			query.setQuery(sql);
			Instances data = query.retrieveInstances();

			// setting class attribute
			data.setClassIndex(data.numAttributes() - 1);

			this.logistic.buildClassifier(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void test() {
		// TODO Auto-generated method stub
		InstanceQuery query;
		try {
			query = new InstanceQuery();
			String sql = "select * from" + Configuration.getPredictTable()
					+ "where label = \'#\'";
			Instances data = query.retrieveInstances();
			List<Double> predictionValue = new ArrayList<Double>();
			for (int i = 0; i < data.numInstances(); i++) {
				double pred = this.logistic.classifyInstance(data.instance(i));
				predictionValue.add(pred);
			}
			// write back to database
			Configuration.updatePredictTable(predictionValue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
