package com.sprint.controllers;

import com.codeborne.selenide.WebDriverRunner;
import com.github.dockerjava.api.command.CreateNetworkCmd;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;

public class WebBrowserInitializer {

    private static final FirefoxOptions CAPABILITIES = new FirefoxOptions()
            .addArguments("--no-sandbox")
            .addArguments("--disable-dev-shm-usage");
    private static final BrowserWebDriverContainer<?> WEB_DRIVER_CONTAINER = populateWebDriver();
    static final String URL = "http://host.testcontainers.internal:";
    static final RemoteWebDriver DRIVER;

    private static BrowserWebDriverContainer<?> populateWebDriver() {
        try (BrowserWebDriverContainer<?> driver = new BrowserWebDriverContainer<>(
                DockerImageName.parse("seleniarm/standalone-firefox:100.0.2-20220728")
                        .asCompatibleSubstituteFor("selenium/standalone-firefox"))
        ) {
            return driver.
                    withCapabilities(CAPABILITIES)
                    .withNetwork(buildNetwork());
        }
    }

    private static Network.NetworkImpl buildNetwork() {
        return Network.builder()
                .createNetworkCmdModifier(WebBrowserInitializer::createNetworkCmd)
                .build();
    }

    private static void createNetworkCmd(CreateNetworkCmd cmd) {
        cmd.withOptions(Map.of("com.docker.network.driver.mtu", "1400"));
    }

    static {
        WEB_DRIVER_CONTAINER.start();

        DRIVER = new RemoteWebDriver(WEB_DRIVER_CONTAINER.getSeleniumAddress(), CAPABILITIES);
        WebDriverRunner.setWebDriver(DRIVER);
    }
}
