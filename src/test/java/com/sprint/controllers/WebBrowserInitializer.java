package com.sprint.controllers;

import org.openqa.selenium.firefox.FirefoxOptions;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.junit.jupiter.Container;

public class WebBrowserInitializer {

  public static final int TIMEOUT = 2000;
  public static final String URL = "http://172.17.0.1:";
  public static final String BROWSER = "firefox";

  @Container
  public static final BrowserWebDriverContainer<?> WEB_DRIVER_CONTAINER = new BrowserWebDriverContainer<>();

  static {
    WEB_DRIVER_CONTAINER.withCapabilities(new FirefoxOptions()
        .addArguments("--no-sandbox")
        .addArguments("--disable-dev-shm-usage"))
        .withReuse(true).start();
  }
}
