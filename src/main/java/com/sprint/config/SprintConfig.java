/**
 * 
 */
package com.sprint.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * The Class SprintConfig.
 *
 * @author benito
 */
@Configuration
@PropertySource("classpath:persistence-mysqldb.properties")
public class SprintConfig {

	/**
	 * Mysql data source.
	 *
	 * @return the data source
	 */
	@Bean
	public DataSource mysqlDataSource(@Autowired Environment env) {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.pass"));

		return dataSource;
	}

}
