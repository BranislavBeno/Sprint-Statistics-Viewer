package com.sprint.repository;

import com.sprint.repository.impl.*;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@TestConfiguration
public class DAOTestConfiguration extends DatabaseBaseTest {

  @Bean
  public DataSource getDataSource() {
    return dataSource();
  }

  @Bean
  public SprintGoalDAO getSprintGoalDAO() {
    return new SprintGoalDAO();
  }

  @Bean
  public SprintKpiDAO getSprintKpiDAO() {
    return new SprintKpiDAO();
  }

  @Bean
  public SprintProgressDAO getSprintProgressDAO() {
    return new SprintProgressDAO();
  }

  @Bean
  public SprintRefinementDAO getSprintRefinementDAO() {
    return new SprintRefinementDAO();
  }

  @Bean
  public TeamRefinementDAO getTeamRefinementDAO() {
    return new TeamRefinementDAO();
  }

  @Bean
  public TeamScopeFocusDAO getTeamScopeFocusDAO() {
    return new TeamScopeFocusDAO();
  }

  @Bean
  public TeamVelocityDAO getTeamVelocityDAO() {
    return new TeamVelocityDAO();
  }
}
