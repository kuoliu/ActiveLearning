package edu.cmu.al.sampling;

import java.util.*;

import edu.cmu.al.util.*;

/**
 * Random sampling strategy
 * 
 * @author yuanyuan
 * 
 */
public class RandomStrategy extends BasicSampling {

	/**
	 * Randomly sampling k instances
	 */
	@Override
	public HashSet<String> sampling(int k) {

		
		String sql = "select max(id) from " + Configuration.getReviewTable();
		int total = SqlManipulation.queryInt(sql);

		sql = "select product_id from " + Configuration.getReviewTable()
				+ " where id =?";

		HashSet<String> selected = new HashSet<String>();
		try {
			Random rand = new Random();
			while (k >= 0) {
				int rowId = rand.nextInt(total) + 1;
				String prodId = SqlManipulation.queryStr(sql, rowId);
				if (!selected.contains(prodId) && !isLabled(prodId)) {
					selected.add(prodId);
					k--;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return selected;
	}
}
