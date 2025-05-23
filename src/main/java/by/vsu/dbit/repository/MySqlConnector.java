package by.vsu.dbit.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnector {
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/dbit_db";
	private static final String JDBC_USER = "root";
	private static final String JDBC_PASSWORD = "";

	public static Connection connect() throws ClassNotFoundException, SQLException {
		Class.forName(JDBC_DRIVER);
		return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
	}
}
