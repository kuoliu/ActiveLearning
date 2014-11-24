package edu.cmu.al.util;

/**
 * Description: Some configurations for the whole project
 * 
 * @author Kuo Liu
 */
public class Configuration {

  /** Configuration for Sql **/
  private static String sqlDriver = "org.postgresql.Driver";

  private static String sqlUrl = "jdbc:postgresql://127.0.0.1:5432/active_learning";

  private static String sqlUserName = "lk";


  private static String sqlPassword = "123";

  /** Configuration of database tables **/
  private static String reviewTable = "product_review";

  private static String featureTable = "product_feature";

  private static String predictTable = "classifier_predict";

  private static String resultTable = "classifier_result";

  /** Configuration about file **/
  private static String fileFormat = "utf-8";

  private static String logPath = "";

  private static String tmpPath = "";

  /** Configuration about useful paths **/
  private static String trainDataPath = "./files/Baby.txt";

  private static String sentimentPositiveDicPath = "./files/positive-words.txt";

  private static String sentimentNegativeDicPath = "./files/negative-words.txt";

  /** Configuration for tools **/
  private static String stanfordnlpComponent = "tokenize, ssplit, pos, parse";
  
  /** Configuration for ml **/
  private static String selectedFeatures = "f1, f2, f3, f4, f5, f6, f7, f8, f9, f11";
  
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

  public static String getReviewTable() {
    return reviewTable;
  }

  public static void setReviewTable(String reviewTable) {
    Configuration.reviewTable = reviewTable;
  }

  public static String getFeatureTable() {
    return featureTable;
  }

  public static void setFeatureTable(String featureTable) {
    Configuration.featureTable = featureTable;
  }

  public static String getPredictTable() {
    return predictTable;
  }

  public static String getResultTable() {
    return resultTable;
  }

  public static void setPredictTable(String predictTable) {
    Configuration.predictTable = predictTable;
  }

  public static String getSentimentPositiveDicPath() {
    return sentimentPositiveDicPath;
  }

  public static void setSentimentPositiveDicPath(String sentimentPositiveDicPath) {
    Configuration.sentimentPositiveDicPath = sentimentPositiveDicPath;
  }

  public static String getSentimentNegativeDicPath() {
    return sentimentNegativeDicPath;
  }

  public static void setSentimentNegativeDicPath(String sentimentNegativeDicPath) {
    Configuration.sentimentNegativeDicPath = sentimentNegativeDicPath;
  }

public static String getStanfordnlpComponent() {
	return stanfordnlpComponent;
}

public static void setStanfordnlpComponent(String stanfordnlpComponent) {
	Configuration.stanfordnlpComponent = stanfordnlpComponent;
}

public static String getSelectedFeatures() {
	return selectedFeatures;
}

public static void setSelectedFeatures(String selectedFeatures) {
	Configuration.selectedFeatures = selectedFeatures;
}
}
