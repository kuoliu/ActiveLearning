package edu.cmu.al.sampling;

import java.sql.ResultSet;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.SqlManipulation;

public class QBCstrategy extends BasicSampling{

	@Override
	public HashSet<String> sampling(int k, String classifier) {
		HashSet<String> selected = new HashSet<String>();

		String sql = "select * from " + Configuration.getPredictTable();
		ResultSet rs = SqlManipulation.query(sql);
		PriorityQueue<Elem> queue = new PriorityQueue<Elem>(k, new Comparator<Elem>() {

			@Override
			public int compare(Elem o1, Elem o2) {
				return (int) (o1.utility_score - o2.utility_score);
			}
			
		});
		try {
			while (rs.next()) {
				String prod_id = rs.getString(1);
				if (queue.size() < k) {
					queue.add(new Elem(prod_id, utilityScore(prod_id)));
				} else {
					if (queue.peek().utility_score < utilityScore(prod_id)) {
						queue.poll();
						queue.add(new Elem(prod_id, utilityScore(prod_id)));
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (Elem e : queue) {
			selected.add(e.prod_id);
		}
		updatePredictTable(selected);
		return selected;
	}
	
	
	
	public static double utilityScore(String prod_id) {
		String sql = "select reg_confidence, lr_confidence, svm_confidence from " + Configuration.getPredictTable()
				+ " where product_id =  '"  + prod_id + "'";
		
		ResultSet rs = SqlManipulation.query(sql);
		double utilityScore = 0.0;
		try {
			if (rs.next()) {
				double max = Math.max(Math.max(rs.getDouble(1), rs.getDouble(2)), rs.getDouble(3));
				double min = Math.min(Math.min(rs.getDouble(1), rs.getDouble(2)), rs.getDouble(3));
				utilityScore = max - min;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return utilityScore;
	}
	
	static class Elem {
		String prod_id;
		double utility_score;
		Elem(String p_id, double u_s) {
			prod_id = p_id;
			utility_score = u_s;
		}
	}
	

}
