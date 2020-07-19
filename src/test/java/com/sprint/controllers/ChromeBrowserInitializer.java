package com.sprint.controllers;

import org.openqa.selenium.chrome.ChromeOptions;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.junit.jupiter.Container;

/**
 * The Class ChromeBrowserInitializer.
 */
public class ChromeBrowserInitializer {

	/** The Constant URL. */
	public static final String URL = "http://172.17.0.1:";
	
	/** The web driver container. */
	@Container
	public static BrowserWebDriverContainer<?> WEB_DRIVER_CONTAINER = new BrowserWebDriverContainer<>();

	static{
		WEB_DRIVER_CONTAINER.withCapabilities(new ChromeOptions()).withReuse(true).start();
	}
}
