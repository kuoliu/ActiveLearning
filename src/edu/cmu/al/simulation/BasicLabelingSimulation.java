package edu.cmu.al.simulation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.ScoreDefine;
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
			String sql = "select f2 from "
					+ Configuration.getFeatureTable() + " where product_id=?";

			/* String sql = "select avg(review_score) from "
		          + Configuration.getReviewTable() + " where product_id=?"; */
			
			ResultSet rs = SqlManipulation.query(sql, pId);
			String updateSql = "update "
					+ Configuration.getPredictTable()
					+ " set (user_label, islabeled) = (?, ?) where product_id=?";

			try {
				rs.next();
//				double label = Math.round(rs.getDouble(1)) >= ScoreDefine.posSocre ? 1.0 : 0.0;
		
				// System.out.println("avg score: " + rs.getDouble(1) + "\tlabel: " + label);
				
				SqlManipulation.update(updateSql, Math.round(rs.getDouble(1)),
						(int) 1, pId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // end for
	}

	// label all the instances
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

		shuffle(productIds);
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
		shuffle(productIds);
		List<String> newPIds = productIds.subList(0,
				(int) Math.round(ratio * productIds.size()));
		labelProductId(newPIds);
		return newPIds;
	}

	// get all instances in the database
	/*
	 * private Set<String> getProductSet() { String sql =
	 * "select product_id from " + Configuration.getPredictTable(); ResultSet rs
	 * = SqlManipulation.query(sql);
	 * 
	 * Set<String> productIds = new HashSet<String>(); try { while (rs.next()) {
	 * productIds.add(rs.getString(1)); } } catch (SQLException e) {
	 * e.printStackTrace(); }
	 * 
	 * return productIds; }
	 */

	// get all instances in the database
	private List<String> getProductList() {
		String sql = "select product_id from "
				+ Configuration.getPredictTable();
		ResultSet rs = SqlManipulation.query(sql);

		List<String> productIds = new ArrayList<String>();

		try {
			while (rs.next()) {
				productIds.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return productIds;
	}

	// shuffle the product Id in the list
	private void shuffle(List<String> productIds) {
		if (productIds == null || productIds.size() == 0) {
			return;
		}

		int size = productIds.size();
		Random randomGenerator = new Random();

		for (int i = 0; i < size; i++) {
			int randomInt = randomGenerator.nextInt(size);
			swap(productIds, i, randomInt);
		}
	}

	// swap two productIds in the list
	private void swap(List<String> productIds, int source, int target) {
		String temp = productIds.get(source);
		productIds.set(source, productIds.get(target));
		productIds.set(target, temp);
	}
}
