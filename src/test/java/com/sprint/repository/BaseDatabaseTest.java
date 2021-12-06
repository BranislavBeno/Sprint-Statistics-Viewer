package com.sprint.repository;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

abstract class BaseDatabaseTest {

  public static final MySQLContainer<?> DATABASE = new MySQLContainer<>("mysql:8.0.25");

  static {
    DATABASE.withReuse(true).start();
  }

  @DynamicPropertySource
  static void properties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", DATABASE::getJdbcUrl);
    registry.add("spring.datasource.password", DATABASE::getPassword);
    registry.add("spring.datasource.username", DATABASE::getUsername);
  }
}
