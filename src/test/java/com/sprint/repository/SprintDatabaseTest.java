package com.sprint.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;

public abstract class SprintDatabaseTest extends BaseDatabaseTest {

  @BeforeAll
  static void setUpAll() {
    ScriptUtils.runInitScript(new JdbcDatabaseDelegate(DATABASE, ""), "CREATE_AND_INITIALIZE_SPRINT_TABLE.sql");
  }

  @AfterAll
  static void tearDownAll() {
    ScriptUtils.runInitScript(new JdbcDatabaseDelegate(DATABASE, ""), "DROP_SPRINT_TABLE.sql");
  }
}
