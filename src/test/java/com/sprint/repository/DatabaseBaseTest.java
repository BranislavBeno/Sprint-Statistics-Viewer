package com.sprint.repository;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.testcontainers.containers.MySQLContainer;

import javax.sql.DataSource;

public abstract class DatabaseBaseTest {

  public static final MySQLContainer<?> DATABASE = new MySQLContainer<>("mysql:8.0.25");

  static {
    DATABASE.withReuse(true).start();
  }

  public static DataSource dataSource() {
    MysqlDataSource dataSource = new MysqlDataSource();
    dataSource.setUrl(DATABASE.getJdbcUrl());
    dataSource.setUser(DATABASE.getUsername());
    dataSource.setPassword(DATABASE.getPassword());
    return dataSource;
  }
}
