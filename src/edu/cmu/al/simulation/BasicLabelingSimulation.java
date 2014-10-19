package edu.cmu.al.simulation;

import java.sql.ResultSet;
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
  public void label(Set<String> productIds) {

    Iterator<String> pIdIter = productIds.iterator();

    while (pIdIter.hasNext()) {
      String productId = pIdIter.next();
      String sql = "select f1 from " + Configuration.getFeatureTable() + " where product_id=?";

      ResultSet rs = SqlManipulation.query(sql, productId);
      String updateSql = "update " + Configuration.getPredictTable()
              + " set user_label=? where product_id=?";

      try {
        rs.next();
        SqlManipulation.update(updateSql, Math.round(rs.getFloat(1)), productId);
      } catch (Exception e) {
        e.printStackTrace();
      }

    }

  }
}
