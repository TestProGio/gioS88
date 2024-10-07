//Version 1
/*
package com.practicetestautomation.base;

import java.util.logging.Level;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BrowserDriverFactory {

    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private String browser;
    private Logger log;

    public BrowserDriverFactory(String browser, Logger log) {
        this.browser = browser.toLowerCase();
        this.log = log;
    }
    public WebDriver createDriver() {
        log.info("Create driver: " + browser);

        switch (browser) {

            case "chrome":
                // We no longer need to manually download driver files and set path to them
                System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
                driver.set(new ChromeDriver());
                break;


            case "firefox":
                // Automatically manage the geckodriver binary
                WebDriverManager.firefoxdriver().setup(); // This line manages the geckodriver

                // Set up Firefox options
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                // If you still need logging, you can set the log file here
                firefoxOptions.setLogLevel(FirefoxDriverLogLevel.DEBUG); // Set log level to DEBUG
                firefoxOptions.addPreference("devtools.console.stdout.content", true); // Log console output to stdout

                driver.set(new FirefoxDriver(firefoxOptions));
                break;

            default:
                log.debug("Do not know how to start: " + browser + ", starting chrome.");
                System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
                // Use ChromeDriver without specific options if the browser is unknown
                driver.set(new ChromeDriver());
                break;
        }

        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);
        return driver.get();
    }



}

 */
package com.practicetestautomation.base;

import java.util.logging.Level;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BrowserDriverFactory {

    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private String browser;
    private Logger log;

    public BrowserDriverFactory(String browser, Logger log) {
        this.browser = browser.toLowerCase();
        this.log = log;
    }

    public WebDriver createDriver() {
        log.info("Create driver: " + browser);
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup(); // Automatically manage ChromeDriver binary
                System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-debugging-port=9222"); // Optional, if needed
                driver.set(new ChromeDriver(chromeOptions));
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup(); // Automatically manage the geckodriver
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setLogLevel(FirefoxDriverLogLevel.DEBUG);
                firefoxOptions.addPreference("devtools.console.stdout.content", true);
                driver.set(new FirefoxDriver(firefoxOptions));
                break;

            default:
                log.debug("Do not know how to start: " + browser + ", starting chrome.");
                driver.set(new ChromeDriver());
                break;
        }

        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);
        return driver.get();
    }
}
