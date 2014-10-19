package edu.cmu.al.simulation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.SqlManipulation;

/**
 * @author zhengxiong
 *
 */
public class BasicLabelingSimulation implements LabelingSimulation {

  @Override
  public void labelProductId(Set<String> productIds) {

    for (String pId : productIds) {

      String sql = "select avg(review_score) from " + Configuration.getReviewTable()
              + " where product_id=?";
      
      ResultSet rs = SqlManipulation.query(sql, pId);
      String updateSql = "update " + Configuration.getPredictTable()
              + " set (user_label, islabeled) = (?, ?) where product_id=?";
      
      try {
        rs.next();
        SqlManipulation.update(updateSql, Math.round(rs.getFloat(1)), true, pId);
      } catch (Exception e) {
        e.printStackTrace();
      }

    }

  }

  @Override
  public void labelAll() {
    String sql = "select product_id from " + Configuration.getPredictTable();
    ResultSet rs = SqlManipulation.query(sql);

    Set<String> productIds = new HashSet<String>();

    try {
      
      while (rs.next()) {
        productIds.add(rs.getString(1));
      }
      
    } catch (SQLException e) {
      e.printStackTrace();
    }
    
    labelProductId(productIds);
  }
}
