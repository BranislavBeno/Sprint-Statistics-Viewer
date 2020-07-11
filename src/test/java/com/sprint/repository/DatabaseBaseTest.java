package com.sprint.repository;

import javax.sql.DataSource;

import org.jetbrains.annotations.NotNull;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

import com.mysql.cj.jdbc.MysqlDataSource;

/**
 * The Class DatabaseBaseTest.
 */
public abstract class DatabaseBaseTest {

	/** The Constant DATABASE. */
	@Container
	static final MySQLContainer<?> DATABASE = new MySQLContainer<>();

	static {
		DATABASE.withReuse(true).start();
	}

	/**
	 * Data source.
	 *
	 * @return the data source
	 */
	@NotNull
	public DataSource dataSource() {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUrl(DATABASE.getJdbcUrl());
		dataSource.setUser(DATABASE.getUsername());
		dataSource.setPassword(DATABASE.getPassword());
		return dataSource;
	}
}
