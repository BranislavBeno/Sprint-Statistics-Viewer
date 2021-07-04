package com.sprint.repository;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

import javax.sql.DataSource;

public abstract class DatabaseBaseTest {

  @Container
  public static final MySQLContainer<?> DATABASE = new MySQLContainer<>("mysql:8.0.25");

  static {
    DATABASE.withReuse(true).start();
  }

  public DataSource dataSource() {
    MysqlDataSource dataSource = new MysqlDataSource();
    dataSource.setUrl(DATABASE.getJdbcUrl());
    dataSource.setUser(DATABASE.getUsername());
    dataSource.setPassword(DATABASE.getPassword());
    return dataSource;
  }
}
