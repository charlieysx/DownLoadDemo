package com.smile.download.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库帮助类
 * @author smile
 *
 */
public class DBHelper implements DBConfig {
	// 创建Connection类型的引用
	private static Connection conn;
	// 创建Statement类型的引用
	private static Statement stat;
	// 创建ResultSet类型的引用
	private static ResultSet rs;

	/**
	 * 获得Connection类型的对象
	 * @return
	 */
	public static Connection getConnection() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			return conn;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 获得Statement类型的对象
	 * @return
	 */
	public static Statement openStatement() {
		try {
			stat = getConnection().createStatement();
			return stat;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 完成数据的更新操作，可以进行数据的增（insert）、删(delete)、改（update）
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public static int update(String sql) throws SQLException {
		return openStatement().executeUpdate(sql);
	}

	/**
	 * 完成数据的查询操作，可以进行数据的查询select
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet query(String sql) throws SQLException {
		return openStatement().executeQuery(sql);
	}

	/**
	 * 释放各种资源
	 */
	public static void close() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stat != null) {
			try {
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
