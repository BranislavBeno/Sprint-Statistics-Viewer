/**
 * 
 */
package com.sprint.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * The Class SprintConfig.
 *
 * @author benito
 */
@Configuration
@ComponentScan("com.sprint")
public class SprintConfig {

	/**
	 * Mysql data source.
	 *
	 * @return the data source
	 */
	@Bean
	public DataSource mysqlDataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/sprints");
		dataSource.setUsername("sprints");
		dataSource.setPassword("sprints");

		return dataSource;
	}

}
