package edu.cmu.al.main;

import java.io.*;
import java.sql.ResultSet;

import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.FileManipulation;
import edu.cmu.al.util.SqlManipulation;

/**
 * Description: Do some proprocess work on the data set
 * 
 * @author Kuo Liu
 */
public class Preprocess {

	/**
	 * Create tables, parse the data from file format to database records and initiate
	 * feature table
	 */
	public static void run() {
		createTables();
		file2Db();
		initFeatureTable();
	}

	private static void file2Db() {
		FileManipulation fileManip = new FileManipulation();
		BufferedReader br = fileManip
				.getBufferedReader(Configuration.getTrainDataPath(),
						Configuration.getFileFormat());
		String buffer = "";
		int id = 0;
		try {
			while ((buffer = br.readLine()) != null) {
				if (buffer.indexOf("product/productId") >= 0) {
					++id;
					String productId = extractUsefulStr(buffer);
					String title = extractUsefulStr(br.readLine());
					String price = extractUsefulStr(br.readLine());
					String userId = extractUsefulStr(br.readLine());
					String profileName = extractUsefulStr(br.readLine());
					String helpfulness = extractUsefulStr(br.readLine());
					float score = Float.parseFloat(extractUsefulStr(br
							.readLine()));
					String time = extractUsefulStr(br.readLine());
					String summary = extractUsefulStr(br.readLine());
					String text = extractUsefulStr(br.readLine());

					String sql = "insert into "
							+ Configuration.getReviewTable()
							+ " values(?,?,?,?,?,?,?,?,?,?,?)";
					SqlManipulation.insert(sql, id, productId, title, price,
							userId, profileName, helpfulness, score, time,
							summary, text);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void initFeatureTable() {
		String sql = "select distinct product_id from "
				+ Configuration.getReviewTable();
		ResultSet rs = SqlManipulation.query(sql);
		try {
			sql = "insert into " + Configuration.getFeatureTable()
					+ " (product_id) values (?)";
			while (rs.next()) {
				SqlManipulation.insert(sql, rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String extractUsefulStr(String str) {
		int idx = str.indexOf(":");
		return str.substring(idx + 1).trim();
	}

	private static void createTables() {
		String sql = "";
		sql = "CREATE TABLE IF NOT EXISTS "
				+ Configuration.getReviewTable()
				+ " (id SERIAL primary key, product_id varchar(256), product_title text, product_price varchar(256), review_userId varchar(256), review_profileName text, review_helpfulness varchar(256), review_score real, review_time varchar(256), review_summary text, review_text text)";
		SqlManipulation.createTable(sql);
		sql = "CREATE TABLE IF NOT EXISTS "
				+ Configuration.getFeatureTable()
				+ " (product_id varchar(256) primary key, f1 real, f2 real, f3 real)";
		SqlManipulation.createTable(sql);
	}
}
