package edu.cmu.al.util;

import java.sql.*;

/**
 * Description: General sql Manipulations
<<<<<<< HEAD
=======
 * 
 * @author Kuo Liu
>>>>>>> 9c8df1751a5e1c886fe2cf0bca30f577ae100058
 */
public class SqlManipulation {

	private static Connection conn = null;
	private static PreparedStatement pstmt = null;

	static {
		try {
			Class.forName(Configuration.getSqlDriver());
			conn = DriverManager.getConnection(Configuration.getSqlUrl(),
					Configuration.getSqlUserName(),
					Configuration.getSqlPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** reconnect to database **/
	public static void reconnect() {
		try {
			conn = DriverManager.getConnection(Configuration.getSqlUrl(),
					Configuration.getSqlUserName(),
					Configuration.getSqlPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** basic operation: create table **/
	public static boolean createTable(String sql) {
		pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			if (!pstmt.execute())
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/** basic operation: insertion into table **/
	public static boolean insert(String sql, Object... args) {
		pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < args.length; ++i) {
				pstmt.setObject(i + 1, args[i]);
			}
			if (pstmt.executeUpdate() != 1) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/** basic operation: update table **/
	public static boolean update(String sql, Object... args) {
		pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < args.length; ++i) {
				pstmt.setObject(i + 1, args[i]);
			}
			if (pstmt.executeUpdate() != 1) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/** basic operation: query on table **/
	public static ResultSet query(String sql, Object... args) {
		pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < args.length; ++i) {
				pstmt.setObject(i + 1, args[i]);
			}
			rs = pstmt.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
<<<<<<< HEAD
=======
	/** basic operation: deletion on table **/
>>>>>>> 9c8df1751a5e1c886fe2cf0bca30f577ae100058
	public static void delete(String sql, Object... args) {
		pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < args.length; ++i) {
				pstmt.setObject(i + 1, args[i]);
			}
			pstmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}