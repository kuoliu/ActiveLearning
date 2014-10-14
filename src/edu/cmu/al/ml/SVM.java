package edu.cmu.al.ml;

<<<<<<< HEAD
public class SVM extends Classifier {

	@Override
	public void train() {
		// TODO Auto-generated method stub
		
=======
import java.util.ArrayList;
import java.util.List;

import edu.cmu.al.util.Configuration;
import weka.classifiers.functions.LibSVM;
import weka.core.Instances;
import weka.experiment.InstanceQuery;

public class SVM implements Classifier {
	private LibSVM svm;

	@Override
	public void train() {
		try {
			InstanceQuery query = new InstanceQuery();
			//load data from databse
			query.setDatabaseURL(Configuration.getSqlUrl());
			query.setUsername(Configuration.getSqlUserName());
			query.setPassword(Configuration.getSqlPassword());
			String sql = "select * from" + Configuration.getPredictTable()
					+ "where label <> \'#\'";
			query.setQuery(sql);
			Instances data = query.retrieveInstances();
			
			// setting class attribute
			data.setClassIndex(data.numAttributes() - 1);
			
			this.svm.buildClassifier(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

>>>>>>> 9c8df1751a5e1c886fe2cf0bca30f577ae100058
	}

	@Override
	public void test() {
		// TODO Auto-generated method stub
<<<<<<< HEAD
		
	}

}
=======
		InstanceQuery query;
		try {
			query = new InstanceQuery();
			String sql = "select * from" + Configuration.getPredictTable() + "where label = \'#\'";
			Instances data = query.retrieveInstances();
			List<Double> predictionValue = new ArrayList<Double>();
			for (int i = 0; i < data.numInstances(); i++) {
				double pred = this.svm.classifyInstance(data.instance(i));
				predictionValue.add(pred);
			}
			//write back to database
			Configuration.updatePredictTable(predictionValue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

>>>>>>> 9c8df1751a5e1c886fe2cf0bca30f577ae100058
