package edu.cmu.al.simulation;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Set;

import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.ScoreDefine;
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
      String sql = "select review_score from " + Configuration.getReviewTable() + " where product_id=?";

      ResultSet rs = SqlManipulation.query(sql, productId);
      String updateSql = "update " + Configuration.getPredictTable()
              + " set user_label=? where product_id=?";

      try {
        rs.next();
        float score = rs.getFloat(1);
        int label = 0;
        if (score >= ScoreDefine.posSocre){
        	label = 1;
        }
        SqlManipulation.update(updateSql, label, productId);
      } catch (Exception e) {
        e.printStackTrace();
      }

    }

  }
}
