package edu.cmu.al.sampling;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.SqlManipulation;

/**
 * Uncertainty sampling strategy
 * @author yuanyuan
 *
 */
public class UncertaintyStrategy extends BasicSampling {

	/**
	 * Based on uncertainty sampling approach randomly selected the instances 
	 * with high positive confidence and at boundary
	 */
	@Override
	public void sampling(int k) {
		HashSet<Integer> selected = new HashSet<Integer>();
		HashMap<Integer, Integer> positive = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> boundary = new HashMap<Integer, Integer>();
		String sql = "select * from "
				+ Configuration.getPredictTable();
		ResultSet rs = SqlManipulation.query(sql);
		
		int positiveID = 0;
		int boundaryID = 0;
		try {
			int total = rs.getFetchSize();
			int line = 1;
			while (line <= total) {
				if (!isLabled(line)) {
					if (get_predict_result(line) >= 0.7) {
						positive.put(positiveID++, line);
					} else if (get_predict_result(line) >= 0.4 && get_predict_result(line) <= 0.6){
						boundary.put(boundaryID++, line);
					}
				}
				
			}
			Random rnd = new Random();
			int insCnt = k;
			while (insCnt >= k/2) {
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
		
		setNotationTable(selected);

	}
	
}
