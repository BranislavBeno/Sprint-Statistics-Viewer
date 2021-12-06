package com.sprint.config;

import com.sprint.repository.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class RepositoryConfiguration {

  @Bean
  public DataSource dataSource(@Value("${spring.datasource.url}") String url,
                               @Value("${spring.datasource.username}") String user,
                               @Value("${spring.datasource.password}") String password) {
    final DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setUrl(url);
    dataSource.setUsername(user);
    dataSource.setPassword(password);

    return dataSource;
  }

  @Bean
  public TeamVelocityDAO teamVelocityDAO(@Autowired DataSource dataSource) {
    return new TeamVelocityDAO(dataSource);
  }

  @Bean
  public TeamRefinementDAO teamRefinementDAO(@Autowired DataSource dataSource) {
    return new TeamRefinementDAO(dataSource);
  }

  @Bean
  public TeamScopeFocusDAO teamScopeFocusDAO(@Autowired DataSource dataSource) {
    return new TeamScopeFocusDAO(dataSource);
  }

  @Bean
  public TeamWorkProportionDAO teamWorkProportionDAO(@Autowired DataSource dataSource) {
    return new TeamWorkProportionDAO(dataSource);
  }

  @Bean
  public SprintGoalDAO sprintGoalDAO(@Autowired DataSource dataSource) {
    return new SprintGoalDAO(dataSource);
  }

  @Bean
  public SprintKpiDAO sprintKpiDAO(@Autowired DataSource dataSource) {
    return new SprintKpiDAO(dataSource);
  }

  @Bean
  public SprintProgressDAO sprintProgressDAO(@Autowired DataSource dataSource) {
    return new SprintProgressDAO(dataSource);
  }

  @Bean
  public SprintRefinementDAO sprintRefinementDAO(@Autowired DataSource dataSource) {
    return new SprintRefinementDAO(dataSource);
  }
}
