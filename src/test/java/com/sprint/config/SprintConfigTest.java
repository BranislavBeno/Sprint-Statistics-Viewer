/**
 * 
 */
package com.sprint.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * @author benito
 *
 */
@Testcontainers
class SprintConfigTest {

	@Container
	private static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>();

	@Test
	void testContainerAvailbility() {
		assertThat(MY_SQL_CONTAINER.isRunning()).isTrue();
	}
}
