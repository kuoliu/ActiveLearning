package edu.cmu.al.simulation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.SqlManipulation;

/**
 * @author zhengxiong
 * 
 */
public class BasicLabelingSimulation implements LabelingSimulation {

  // label the instance by product ID
  @Override
  public void labelProductId(Collection<String> productIds) {
    if (productIds == null || productIds.size() == 0) {
      return;
    } // end if

    for (String pId : productIds) {
      String sql = "select f2 from " + Configuration.getFeatureTable() + " where product_id=?";

      /*
       * String sql = "select avg(review_score) from " + Configuration.getReviewTable() +
       * " where product_id=?";
       */

      ResultSet rs = SqlManipulation.query(sql, pId);
      String updateSql = "update " + Configuration.getPredictTable()
              + " set (user_label, islabeled) = (?, ?) where product_id=?";

      try {
        rs.next();
        // double label = Math.round(rs.getDouble(1)) >= ScoreDefine.posSocre ? 1.0 : 0.0;

        // System.out.println("avg score: " + rs.getDouble(1) + "\tlabel: " + label);

        SqlManipulation.update(updateSql, Math.round(rs.getDouble(1)), (int) 1, pId);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } // end for
  }

/*  // label all the instances
  @Override
  public void labelAll() {
    List<String> productIds = getProductList();
    labelProductId(productIds);
  }

  // random label n instances
  @Override
  public List<String> randomLabelByNum(int n) {
    List<String> productIds = getProductList();

    if (n > productIds.size() || n <= 0) {
      return null;
    }

    Collections.shuffle(productIds);
    List<String> newPIds = productIds.subList(0, n);
    labelProductId(newPIds);
    return newPIds;
  }

  // random label ration of intances
  @Override
  public List<String> randomLabelByRatio(double ratio) {
    if (ratio < 0 || ratio > 1) {
      return null;
    }

    List<String> productIds = getProductList();
    Collections.shuffle(productIds);
    List<String> newPIds = productIds.subList(0, (int) Math.round(ratio * productIds.size()));
    labelProductId(newPIds);
    return newPIds;
  }*/

  // get all instances in the database
  /*
   * private Set<String> getProductSet() { String sql = "select product_id from " +
   * Configuration.getPredictTable(); ResultSet rs = SqlManipulation.query(sql);
   * 
   * Set<String> productIds = new HashSet<String>(); try { while (rs.next()) {
   * productIds.add(rs.getString(1)); } } catch (SQLException e) { e.printStackTrace(); }
   * 
   * return productIds; }
   */

  public int getUnlabeledNumber() {
    
    String sql = "select COUNT(product_id) from " + Configuration.getPredictTable()
            + " where islabeled=0";
    return SqlManipulation.queryInt(sql);
  }

  // get all instances in the database
  private List<String> getProductList() {
    String sql = "select product_id from " + Configuration.getPredictTable() + " where islabeled=0";
    ResultSet rs = SqlManipulation.query(sql);

    List<String> productIds = new ArrayList<String>();

    try {
      while (rs.next()) {
        productIds.add(rs.getString(1).trim());
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return productIds;
  }

  @Override
  public int getAllNumber() {
    
    String sql = "select COUNT(product_id) from " + Configuration.getPredictTable();
    return SqlManipulation.queryInt(sql);
  }
}
