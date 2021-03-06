package edu.cmu.al.main;

import java.io.*;
import java.sql.ResultSet;

import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.FileManipulation;
import edu.cmu.al.util.SqlManipulation;

/**
 * Description: Do some preprocess work on the data set
 *
 * @author Kuo Liu
 */
public class Preprocess {

  public static void run() {
    createTables();
    file2Db();
    initFeatureTable();
    initPredictTable();
  }

  private static void file2Db() {
    FileManipulation fileManip = new FileManipulation();
    BufferedReader br = fileManip.getBufferedReader(Configuration.getTrainDataPath(),
            Configuration.getFileFormat());
    String buffer = "";
    int id = 0;
    try {
      while ((buffer = br.readLine()) != null) {
        if (buffer.indexOf("product/productId") >= 0) {
          ++id;
          String productId = extractUsefulStr(buffer);
          String title = extractUsefulStr(br.readLine());
          String price = extractUsefulStr(br.readLine());
          String userId = extractUsefulStr(br.readLine());
          String profileName = extractUsefulStr(br.readLine());
          String helpfulness = extractUsefulStr(br.readLine());
          float score = Float.parseFloat(extractUsefulStr(br.readLine()));
          String time = extractUsefulStr(br.readLine());
          String summary = extractUsefulStr(br.readLine());
          String text = extractUsefulStr(br.readLine());

          String sql = "insert into " + Configuration.getReviewTable()
                  + " values(?,?,?,?,?,?,?,?,?,?,?)";
          SqlManipulation.insert(sql, id, productId, title, price, userId, profileName,
                  helpfulness, score, time, summary, text);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void initFeatureTable() {
    String sql = "select distinct product_id from " + Configuration.getReviewTable();
    ResultSet rs = SqlManipulation.query(sql);
    try {
      sql = "insert into " + Configuration.getFeatureTable() + " (product_id) values (?)";
      while (rs.next()) {
        SqlManipulation.insert(sql, rs.getString(1));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void initPredictTable() {
    String sql = "select distinct product_id from " + Configuration.getReviewTable();
    ResultSet rs = SqlManipulation.query(sql);
    try {
      sql = "insert into " + Configuration.getPredictTable()
              + " (product_id, islabeled) values (?, 0)";
      while (rs.next()) {
        SqlManipulation.insert(sql, rs.getString(1));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static String extractUsefulStr(String str) {
    int idx = str.indexOf(":");
    return str.substring(idx + 1).trim();
  }

  private static void createTables() {
    String sql = "";

    // drop tables
    sql = "DROP TABLE IF EXISTS " + Configuration.getReviewTable();
    SqlManipulation.dropTable(sql);

    sql = "DROP TABLE IF EXISTS " + Configuration.getFeatureTable();
    SqlManipulation.dropTable(sql);

    sql = "DROP TABLE IF EXISTS " + Configuration.getPredictTable();
    SqlManipulation.dropTable(sql);

    sql = "DROP TABLE IF EXISTS " + Configuration.getResultTable();
    SqlManipulation.dropTable(sql);

    sql = "CREATE TABLE IF NOT EXISTS "
            + Configuration.getReviewTable()
            + " (id SERIAL primary key, product_id VARCHAR(256), product_title TEXT, product_price VARCHAR(256), review_userId VARCHAR(256), review_profileName TEXT, review_helpfulness VARCHAR(256), review_score REAL, review_time VARCHAR(256), review_summary TEXT, review_text TEXT)";
    SqlManipulation.createTable(sql);

    sql = "CREATE TABLE IF NOT EXISTS "
            + Configuration.getFeatureTable()
            + " (product_id VARCHAR(256) primary key, f1 REAL, f2 REAL, f3 REAL, f4 REAL, f5 REAL, f6 REAL, f7 REAL, f8 REAL, f9 REAL, f10 REAL, f11 REAL)";
    SqlManipulation.createTable(sql);

    sql = "CREATE TABLE IF NOT EXISTS "
            + Configuration.getPredictTable()
            + " (product_id VARCHAR(256) primary key, islabeled INTEGER, user_label REAL, reg_confidence REAL, lr_confidence REAL, svm_confidence REAL, predict_result REAL)";
    SqlManipulation.createTable(sql);

    sql = "CREATE TABLE IF NOT EXISTS "
            + Configuration.getResultTable()
            + " (result_id INTEGER, round INTEGER, accuracy REAL, precision REAL, recall REAL, annotation_cost REAL, PRIMARY KEY(result_id, round))";
    SqlManipulation.createTable(sql);
  }
}
