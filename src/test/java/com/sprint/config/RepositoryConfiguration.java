package com.sprint.config;

import com.sprint.repository.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
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
    @DependsOnDatabaseInitialization
    public TeamVelocityDAO teamVelocityDAO(@Autowired DataSource dataSource) {
    return new TeamVelocityDAO(dataSource);
  }

    @Bean
    @DependsOnDatabaseInitialization
    public TeamRefinementDAO teamRefinementDAO(@Autowired DataSource dataSource) {
    return new TeamRefinementDAO(dataSource);
  }

    @Bean
    @DependsOnDatabaseInitialization
    public TeamScopeFocusDAO teamScopeFocusDAO(@Autowired DataSource dataSource) {
    return new TeamScopeFocusDAO(dataSource);
  }

    @Bean
    @DependsOnDatabaseInitialization
    public TeamWorkProportionDAO teamWorkProportionDAO(@Autowired DataSource dataSource) {
    return new TeamWorkProportionDAO(dataSource);
  }

    @Bean
    @DependsOnDatabaseInitialization
    public SprintGoalDAO sprintGoalDAO(@Autowired DataSource dataSource) {
    return new SprintGoalDAO(dataSource);
  }

    @Bean
    @DependsOnDatabaseInitialization
    public SprintKpiDAO sprintKpiDAO(@Autowired DataSource dataSource) {
    return new SprintKpiDAO(dataSource);
  }

    @Bean
    @DependsOnDatabaseInitialization
    public SprintProgressDAO sprintProgressDAO(@Autowired DataSource dataSource) {
    return new SprintProgressDAO(dataSource);
  }

    @Bean
    @DependsOnDatabaseInitialization
    public SprintRefinementDAO sprintRefinementDAO(@Autowired DataSource dataSource) {
    return new SprintRefinementDAO(dataSource);
  }
}
