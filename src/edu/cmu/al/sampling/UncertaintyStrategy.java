package edu.cmu.al.sampling;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.SqlManipulation;

/**
 * Uncertainty sampling strategy
 * 
 * @author Yuanyuan Yang
 * 
 */
public class UncertaintyStrategy extends BasicSampling {

	/**
	 * Based on uncertainty sampling approach randomly selected the instances
	 * with high positive confidence and at boundary
	 */
	@Override
	public HashSet<String> sampling(int k, String column) {
		HashSet<String> selected = new HashSet<String>();
		HashMap<Integer, String> positive = new HashMap<Integer, String>();
		HashMap<Integer, String> boundary = new HashMap<Integer, String>();

		String sql = "select * from " + Configuration.getPredictTable();
		ResultSet rs = SqlManipulation.query(sql);

		int positiveID = 0;
		int boundaryID = 0;
		try {
			while (rs.next()) {
				String prod_id = rs.getString(1);
				if (!isLabled(prod_id)) {
					if (get_predict_result(prod_id, column) >= 0.4) {
						positive.put(positiveID++, prod_id);
					} else if (get_predict_result(prod_id, column) >= 0.1
							&& get_predict_result(prod_id, column) < 0.4) {
						boundary.put(boundaryID++, prod_id);
					}
				}

			}
			Random rnd = new Random();
			int insCnt = k;
			while (insCnt >= k / 2) {
				int i = rnd.nextInt(positiveID);
				if (!selected.contains(positive.get(i))) {
					selected.add(positive.get(i));
				}
				insCnt--;
			}

			while (insCnt >= 0) {
				int i = rnd.nextInt(boundaryID);
				if (!selected.contains(boundary.get(i))) {
					selected.add(boundary.get(i));
				}
				insCnt--;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		updatePredictTable(selected);
		return selected;
	}

}