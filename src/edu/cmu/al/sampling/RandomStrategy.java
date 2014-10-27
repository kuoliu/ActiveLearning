package edu.cmu.al.sampling;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Random;

import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.SqlManipulation;

/**
 * Random sampling strategy
 * 
 * @author Yuanyuan Yang
 * 
 */
public class RandomStrategy extends BasicSampling {

	/**
	 * Randomly sampling k instances
	 */
	@Override
	public HashSet<String> sampling(int k) {

		String sql1 = "select count(*) from " + Configuration.getReviewTable();

		String sql2 = "select product_id from "
				+ Configuration.getReviewTable() + " limit 1 offset ?";

		HashSet<String> selected = new HashSet<String>(); // result set(product
															// id)
		try {
			ResultSet rs1 = SqlManipulation.query(sql1);
			int total = 0;
			if (rs1.next()) {
				total = rs1.getInt(1);
			}
			Random rnd = new Random();
			while (k > 0) {
				int rowId = rnd.nextInt(total);
				ResultSet rs2 = SqlManipulation.query(sql2, rowId);
				if (rs2.next()) {
					String prod_id = rs2.getString(1);
					if (!selected.contains(prod_id) && !isLabled(prod_id)) {
						selected.add(prod_id);
						k--;
					}/*
					 * else { if (selected.contains(prod_id)) {
					 * System.out.println(prod_id + " contains in this round");
					 * } else { System.out.println(prod_id + " labeled"); } }
					 */
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		updatePredictTable(selected);
		return selected;
	}
}