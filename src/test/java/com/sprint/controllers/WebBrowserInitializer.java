package com.sprint.controllers;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;

public class WebBrowserInitializer {

    public static final String URL = "http://host.testcontainers.internal:";
    public static final RemoteWebDriver DRIVER;

    public static final BrowserWebDriverContainer<?> WEB_DRIVER_CONTAINER = new BrowserWebDriverContainer<>()
            .withCapabilities(new FirefoxOptions()
                    .addArguments("--no-sandbox")
                    .addArguments("--disable-dev-shm-usage"));

    static {
        WEB_DRIVER_CONTAINER.withReuse(true).start();

        DRIVER = WEB_DRIVER_CONTAINER.getWebDriver();
        WebDriverRunner.setWebDriver(DRIVER);
    }
}
