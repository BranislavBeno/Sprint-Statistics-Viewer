package com.sprint.controllers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.shaded.org.apache.commons.lang.SystemUtils;

public class WebBrowserInitializer {

    public static final String URL = SystemUtils.IS_OS_WINDOWS ? "http://host.docker.internal:" : "http://172.17.0.1:";

    @Container
    public static final BrowserWebDriverContainer<?> WEB_DRIVER_CONTAINER = new BrowserWebDriverContainer<>();

    static {
        WEB_DRIVER_CONTAINER.withCapabilities(new FirefoxOptions()
                        .addArguments("--no-sandbox")
                        .addArguments("--disable-dev-shm-usage"))
                .withReuse(true).start();

        Configuration.browser = "firefox";
        Configuration.timeout = 10000;

        RemoteWebDriver remoteWebDriver = WEB_DRIVER_CONTAINER.getWebDriver();
        WebDriverRunner.setWebDriver(remoteWebDriver);
    }
}
