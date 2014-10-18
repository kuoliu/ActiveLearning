package edu.cmu.al.util;

import java.sql.*;

/**
 * Description: General sql Manipulations
 * 
 * @author Kuo Liu
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
	
	/** basic operation: drop table **/
	public static boolean dropTable(String sql) {
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

	/** basic operation: deletion on table **/
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

	/** basic operation: query one integer from database **/
	public static int queryInt(String sql, Object... args) {
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

		int result = 0;

		try {
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/** basic operation: query one integer from database **/
	public static String queryStr(String sql, Object... args) {
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

		String result = "";

		try {
			if (rs.next()) {
				result = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/** basic operation: close the result set **/
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}