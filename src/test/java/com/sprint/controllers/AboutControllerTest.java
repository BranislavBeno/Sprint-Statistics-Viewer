package com.sprint.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.sprint.extension.ScreenCaptureOnFailure;

/**
 * The Class AboutControllerTest.
 */
@Testcontainers
@ExtendWith({ ScreenCaptureOnFailure.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AboutControllerTest {

	/** The port. */
	@LocalServerPort
	private int port;

	/**
	 * Prepare resources.
	 */
	@BeforeEach
	private void prepareResources() {
		ChromeBrowserInitializer.WEB_DRIVER_CONTAINER.getWebDriver()
				.get(ChromeBrowserInitializer.URL + port + "/about");
	}

	/**
	 * Test about page title.
	 */
	@Test
	@DisplayName("Test whether page title is 'About'")
	void testAboutPageTitle() {
		ChromeBrowserInitializer.WEB_DRIVER_CONTAINER.getWebDriver().manage().timeouts().implicitlyWait(17,
				TimeUnit.SECONDS);

		// Get page title
		WebElement pageTitle = ChromeBrowserInitializer.WEB_DRIVER_CONTAINER.getWebDriver()
				.findElementByCssSelector(".blockquote h3");
		// Assert expected and actual content
		assertThat(pageTitle.getText()).isEqualTo("About");
	}

	/**
	 * Test unsorted list of web elements.
	 */
	@Test
	@DisplayName("Test whether unordered web elements list has size 8")
	void testUnsortedWebElementsList() {
		// Get list of web elements
		List<WebElement> webElementList = ChromeBrowserInitializer.WEB_DRIVER_CONTAINER.getWebDriver()
				.findElementsByCssSelector(".lead li");
		// Assert expected and actual content
		assertThat(webElementList.size()).isEqualTo(8);
	}
}
