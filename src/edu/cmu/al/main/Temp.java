package edu.cmu.al.main;

import edu.cmu.al.feature.*;
import edu.cmu.al.util.SqlManipulation;

/**
 * Description: The class is used to add some temporary change. exp: we add a table, we can
 * write a function and add it to the main function and run it separately instead of running
 * the whole project
 * 
 * @author Kuo Liu
 */
public class Temp {

	/** update sentiment feature **/
	public static void fun1() {
		String sql = "alter table product_feature add column f4 real";
		SqlManipulation.update(sql);
		sql = "alter table product_feature add column f5 real";
		SqlManipulation.update(sql);
		sql = "alter table product_feature add column f6 real";
		SqlManipulation.update(sql);
		sql = "alter table product_feature add column f7 real";
		SqlManipulation.update(sql);
		sql = "alter table product_feature add column f8 real";
		SqlManipulation.update(sql);
		FeatureExtractor featureExt = new SentimentFeatureExtractor();
		System.out.println(featureExt.extractFeature(3));
	}

	/** update bias variance feature **/
	public static void fun2() {
		String sql = "alter table product_feature add column f9 real";
		SqlManipulation.update(sql);
		sql = "alter table product_feature add column f10 real";
		SqlManipulation.update(sql);
		sql = "alter table product_feature add column f11 real";
		SqlManipulation.update(sql);
	}
	
	public static void fun3() {
		FeatureExtractor featureExt = new BiasVarianceFeatureExtractor();
		featureExt.extractFeature(9);
	}

	public static void main(String[] args) {
		//Temp.fun1();
		Temp.fun2();
		Temp.fun3();
	}
}