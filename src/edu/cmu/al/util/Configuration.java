package edu.cmu.al.util;

<<<<<<< HEAD
=======
import java.util.List;

>>>>>>> 9c8df1751a5e1c886fe2cf0bca30f577ae100058
/**
 * Description: Some configurations for the whole project
 */
public class Configuration {

	/** Configuration for Sql **/
	private static String sqlDriver = "org.postgresql.Driver";
	private static String sqlUrl = "jdbc:postgresql://127.0.0.1:5432/active_learning";
<<<<<<< HEAD
	private static String sqlUserName = "mabodx";
=======
	private static String sqlUserName = "lk";
>>>>>>> 9c8df1751a5e1c886fe2cf0bca30f577ae100058
	private static String sqlPassword = "123";

	/** Configuration of database tables **/
	private static String reviewTable = "product_review";
	private static String featureTable = "product_feature";
<<<<<<< HEAD
	private static String positiveWordTable = "positive_word";
	private static String negativeWordTable = "negative_word";
	private static String sentimentwordtable ="sentiment_word";
=======
	private static String predictTable = "classifier_predict";
>>>>>>> 9c8df1751a5e1c886fe2cf0bca30f577ae100058
	
	/** Configuration about file **/
	private static String fileFormat = "utf-8";
	private static String logPath = "";
	private static String tmpPath = "";

	/** Configuration about useful paths **/
	private static String trainDataPath = "./files/Cell_Phones_&_Accessories.txt";
<<<<<<< HEAD
	private static String sentimentpositiveDataPath = "./files/positive-words.txt";
	private static String sentimentnegativeDataPath = "./files/negative-words.txt";
=======
>>>>>>> 9c8df1751a5e1c886fe2cf0bca30f577ae100058
	
	public static String getSqlDriver() {
		return sqlDriver;
	}

	public static void setSqlDriver(String sqlDriver) {
		Configuration.sqlDriver = sqlDriver;
	}

	public static String getSqlUrl() {
		return sqlUrl;
	}

	public static void setSqlUrl(String sqlUrl) {
		Configuration.sqlUrl = sqlUrl;
	}

	public static String getSqlUserName() {
		return sqlUserName;
	}

	public static void setSqlUserName(String sqlUserName) {
		Configuration.sqlUserName = sqlUserName;
	}

	public static String getSqlPassword() {
		return sqlPassword;
	}

	public static void setSqlPassword(String sqlPassword) {
		Configuration.sqlPassword = sqlPassword;
	}

	public static String getFileFormat() {
		return fileFormat;
	}

	public static void setFileFormat(String fileFormat) {
		Configuration.fileFormat = fileFormat;
	}

	public static String getLogPath() {
		return logPath;
	}

	public static void setLogPath(String logPath) {
		Configuration.logPath = logPath;
	}

	public static String getTmpPath() {
		return tmpPath;
	}

	public static void setTmpPath(String tmpPath) {
		Configuration.tmpPath = tmpPath;
	}

	public static String getTrainDataPath() {
		return trainDataPath;
	}

	public static void setTrainDataPath(String trainDataPath) {
		Configuration.trainDataPath = trainDataPath;
	}

<<<<<<< HEAD
	public static String getsentimentpositiveDataPath() {
		return sentimentpositiveDataPath;
	}

	public static void setsentimentpositiveDataPath(String sentimentpositiveDataPath) {
		Configuration.sentimentpositiveDataPath = sentimentpositiveDataPath;
	}
	
	public static String getsentimentnegativeDataPath() {
		return sentimentnegativeDataPath;
	}

	public static void setsentimentnegativeDataPath(String sentimentnegativeDataPath) {
		Configuration.sentimentnegativeDataPath = sentimentnegativeDataPath;
	}
	
=======
>>>>>>> 9c8df1751a5e1c886fe2cf0bca30f577ae100058
	public static String getReviewTable() {
		return reviewTable;
	}

	public static void setReviewTable(String reviewTable) {
		Configuration.reviewTable = reviewTable;
	}

	public static String getFeatureTable() {
		return featureTable;
	}

<<<<<<< HEAD
=======

>>>>>>> 9c8df1751a5e1c886fe2cf0bca30f577ae100058
	public static void setFeatureTable(String featureTable) {
		Configuration.featureTable = featureTable;
	}
	
<<<<<<< HEAD
	
	public static String getPostiveWordTable() {
		return positiveWordTable;
	}

	public static void setPositiveWordTable(String positiveWordTable) {
		Configuration.positiveWordTable = positiveWordTable;
	}
	
	public static String getNegativeWordTable() {
		return negativeWordTable;
	}

	public static void setnegativeWordTable(String negativeWordTable) {
		Configuration.negativeWordTable = negativeWordTable;
	}
	
	public static String getSentimentWordTable() {
		return sentimentwordtable;
	}

	public static void setSentimentWordTable(String SentimentWordTable) {
		Configuration.sentimentwordtable = sentimentwordtable;
	}
	
	
=======
	public static String getPredictTable() {
		return predictTable;
	}
	
	public static void setPredictTable(String predictTable) {
		Configuration.predictTable = predictTable;
	}
	
	
	public static void updatePredictTable(List<Double> predictionValue) {
		String updateSql = "update " + Configuration.getPredictTable()
				+ " set predictValue = ? where id=?";
		try {
			for (int i = 0; i < predictionValue.size(); i++) {
				//base 0 or base 1?
				SqlManipulation.update(updateSql, predictionValue.get(i), i + 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
>>>>>>> 9c8df1751a5e1c886fe2cf0bca30f577ae100058
}