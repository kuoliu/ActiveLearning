package edu.cmu.al.main;

import java.io.BufferedReader;

import edu.cmu.al.util.Configuration;
import edu.cmu.al.util.FileManipulation;
import edu.cmu.al.util.SqlManipulation;

public class PreprocessPNWords {
	public static void run() {
		createTables();
//		file2DbPositive();
//		file2DbNegative();
		file2DbSentiment();
		// initFeatureTable();
	}

	private static void file2DbPositive() {
		FileManipulation fileManip = new FileManipulation();
		BufferedReader br = fileManip.getBufferedReader(
				Configuration.getsentimentpositiveDataPath(),
				Configuration.getFileFormat());
		String buffer = "";
		int id = 0;
		try {
			char ch = ';';
			while ((buffer = br.readLine()) != null) {

				if (buffer.length() < 1 || buffer.charAt(0) == ch)
					continue;
				++id;
				String sql = "insert into "
						+ Configuration.getPostiveWordTable() + " values(?,?)";
				SqlManipulation.insert(sql, id, buffer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void file2DbNegative() {
		FileManipulation fileManip = new FileManipulation();
		BufferedReader br = fileManip.getBufferedReader(
				Configuration.getsentimentnegativeDataPath(),
				Configuration.getFileFormat());
		String buffer = "";
		int id = 0;
		try {
			char ch = ';';
			while ((buffer = br.readLine()) != null) {

				if (buffer.length() < 1 || buffer.charAt(0) == ch)
					continue;
				++id;
				String sql = "insert into "
						+ Configuration.getSentimentWordTable()
						+ " values(?,?)";
				SqlManipulation.insert(sql, id, buffer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void file2DbSentiment() {
		FileManipulation fileManip = new FileManipulation();
		BufferedReader br = fileManip.getBufferedReader(
				Configuration.getsentimentnegativeDataPath(),
				Configuration.getFileFormat());
		String buffer = "";
		int id = 0;
		try {
			char ch = ';';
			while ((buffer = br.readLine()) != null) {

				if (buffer.length() < 1 || buffer.charAt(0) == ch)
					continue;
				++id;
				String sentiment = "negative";
				String sql = "insert into "
						+ Configuration.getSentimentWordTable()
						+ " values(?,?,?)";
				SqlManipulation.insert(sql, id, buffer, sentiment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		br = fileManip.getBufferedReader(
				Configuration.getsentimentpositiveDataPath(),
				Configuration.getFileFormat());

		try {
			char ch = ';';
			while ((buffer = br.readLine()) != null) {

				if (buffer.length() < 1 || buffer.charAt(0) == ch)
					continue;
				++id;
				String sentiment = "positive";
				String sql = "insert into "
						+ Configuration.getSentimentWordTable()
						+ " values(?,?,?)";
				SqlManipulation.insert(sql, id, buffer, sentiment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createTables() {
		String sql = "";
		sql = "CREATE TABLE IF NOT EXISTS "
				+ Configuration.getPostiveWordTable()
				+ " (id SERIAL primary key, words varchar(256) )";
		SqlManipulation.createTable(sql);
		sql = "CREATE TABLE IF NOT EXISTS "
				+ Configuration.getNegativeWordTable()
				+ " (id SERIAL primary key, words varchar(256) )";
		SqlManipulation.createTable(sql);

		sql = "CREATE TABLE IF NOT EXISTS "
				+ Configuration.getSentimentWordTable()
				+ " (id SERIAL primary key, words varchar(256), sentiment varchar(256))";
		SqlManipulation.createTable(sql);
	}
}
