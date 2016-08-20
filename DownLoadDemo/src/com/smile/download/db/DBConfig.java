package com.smile.download.db;

/**
 * 数据库配置类
 * @author smile
 *
 */
public interface DBConfig {
	// MySql数据库配置
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://IP地址:端口号/数据库名?useUnicode=true&characterEncoding=utf-8";
	String user = "数据库用户名";
	String password = "数据库密码";
}
