package edu.cmu.al.sampling;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Random;

import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.SqlManipulation;

/**
 * Random sampling strategy
 * @author yangyuanyuan
 *
 */
public class RandomStrategy extends BasicSampling{

	/**
	 * Randomly sampling k instances
	 */
	@Override
	public void sampling(int k) {
		
		String sql = "select * from "
				+ Configuration.getNotationTable();
		ResultSet rs = SqlManipulation.query(sql);
		
		HashSet<Integer> selected = new HashSet<Integer>(); // result set(line id)
		try {
			int total = rs.getFetchSize();
			Random rnd = new Random();
			while (k >= 0) {
				int num = rnd.nextInt(total)+1;
				if (!selected.contains(num) && !isLabled(num)) {
					selected.add(num);
					k--;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setNotationTable(selected);
		
	}
	

	
}
