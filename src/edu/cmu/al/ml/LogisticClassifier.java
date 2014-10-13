//package edu.cmu.al.ml;
//
//import weka.classifiers.functions.Logistic;
//import weka.core.Instances;
//import weka.experiment.InstanceQuery;
//
//public class LogisticClassifier extends Classifier {
//	private Logistic logistic;
//
//	@Override
//	public void train() {
//		try {
//			InstanceQuery query = new InstanceQuery();
//			query.setQuery("select * from some_table");
//			Instances data = query.retrieveInstances();
//			this.logistic.buildClassifier(data);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//	@Override
//	public void test() {
//		// TODO Auto-generated method stub
//		InstanceQuery query;
//		try {
//			query = new InstanceQuery();
//			query.setQuery("select * from some_table");
//			Instances data = query.retrieveInstances();
//			for (int i = 0; i < data.numInstances(); i++) {
//				double pred = this.logistic.classifyInstance(data.instance(i));
//				//write back to database
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//
//	}
//
//}
