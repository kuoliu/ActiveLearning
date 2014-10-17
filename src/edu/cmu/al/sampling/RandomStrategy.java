package edu.cmu.al.sampling;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Random;

import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.SqlManipulation;

/**
 * Random sampling strategy
 * @author yuanyuan
 *
 */
public class RandomStrategy extends BasicSampling{

	/**
	 * Randomly sampling k instances
	 */
	@Override
	public HashSet<String> sampling(int k) {
		
		String sql = "select max(id) from "
				+ Configuration.getReviewTable();
		ResultSet rs1 = SqlManipulation.query(sql);
		
		
		sql = "select product_id from " + Configuration.getReviewTable() 
				+ " where id =?";
		
		HashSet<String> selected = new HashSet<String>(); // result set(product id)
		try {
			int total = rs1.getInt(1);
			Random rnd = new Random();
			while (k >= 0) {
				int rowId = rnd.nextInt(total)+1;
				ResultSet rs2 = SqlManipulation.query(sql, rowId);
				String prod_id = rs2.getString(1);
				if (!selected.contains(prod_id) && !isLabled(prod_id)) {
					selected.add(prod_id);
					k--;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return selected;
		
	}
}
