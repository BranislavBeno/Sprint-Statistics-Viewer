package com.sprint.controllers;

import org.openqa.selenium.firefox.FirefoxOptions;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.shaded.org.apache.commons.lang.SystemUtils;

public class WebBrowserInitializer {

  public static final int TIMEOUT = 2000;
  public static final String URL = SystemUtils.IS_OS_WINDOWS ? "http://host.docker.internal:" : "http://172.17.0.1:";
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
