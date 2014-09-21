package edu.cmu.al.main;

import java.io.*;

import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.FileManipulation;
import edu.cmu.al.util.SqlManipulation;

/**
 * Description: Do some proprocess work on the data set
 */
public class Preprocess {

	public static void run() {
		createTables();
		file2Db();
	}

	private static void file2Db() {
		FileManipulation fileManip = new FileManipulation();
		BufferedReader br = fileManip
				.getBufferedReader(Configuration.getTrainDataPath(),
						Configuration.getFileFormat());
		String buffer = "";
		try {
			while ((buffer = br.readLine()) != null) {
				// please finish to load the file to database
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createTables() {
		String sql = "";
		sql = "CREATE TABLE IF NOT EXISTS product_review (id SERIAL primary key, product_id varchar(256), product_title varchar(256), product_price varchar(256), review_userId varchar(256), review_profileName varchar(256), review_helpfulness varchar(256), review_score real, review_time varchar(256), review_summary text, review_text text)";
		SqlManipulation.createTable(sql);
		sql = "CREATE TABLE IF NOT EXISTS product_feature (product_id varchar(256) primary key, f1 real, f2 real, f3 real)";
		SqlManipulation.createTable(sql);
	}
}
