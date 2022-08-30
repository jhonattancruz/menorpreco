package com.menorvalor.menorpreco.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WebDriverUtil {

    private final String PATH_CHROME_DRIVER = "ChromeWebDriver/chromedriver";

    public WebDriver abrirBrowser() {
        return getChromeWebDriver();
    }

    private WebDriver getChromeWebDriver() {
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);

        System.setProperty("webdriver.chrome.driver", this.PATH_CHROME_DRIVER);
        System.setProperty("webdriver.chrome.silentOutput", "true");

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("test-type");
        options.addArguments("start-maximized");
        options.addArguments("disable-web-security");
        options.addArguments("allow-running-insecure-content");

        capabilities.setCapability("chrome.switches", Arrays.asList("--ignore-certificate-errors"));
        capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);

        Map<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("settings.language.preferred_languages", "pt-BR");
        chromePrefs.put("download.prompt_for_download", false);

        options.setExperimentalOption("prefs", chromePrefs);

        capabilities.setCapability("chrome.binary", this.PATH_CHROME_DRIVER);
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        capabilities.setAcceptInsecureCerts(true);
        capabilities.acceptInsecureCerts();

        WebDriver webDriver = new ChromeDriver(capabilities);
        webDriver.manage().window().maximize();

        return webDriver;
    }

    public void fecharBrowser(WebDriver webDriver) {
        try {
            webDriver.close();
        } catch (Exception ex) {
        }

        try {
            webDriver.quit();
        } catch (Exception ex) {

        }
    }
}
