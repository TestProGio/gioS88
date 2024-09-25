package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebDriverManagerUtil {
    private static WebDriverManagerUtil instance;
    private WebDriver driver;
    private WebDriverWait wait;

    // Private constructor to prevent instantiation
    private WebDriverManagerUtil() {}

    public static synchronized WebDriverManagerUtil getInstance() {
        if (instance == null) {
            instance = new WebDriverManagerUtil();
        }
        return instance;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    // Modified setup method to accept the browser type
    public void setup(String browser) throws InterruptedException {
        if (driver == null) {
            switch (browser.toLowerCase()) {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    driver = new FirefoxDriver(firefoxOptions);
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                    driver = new EdgeDriver(edgeOptions);
                    break;
                case "chrome":
                default:
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    driver = new ChromeDriver(chromeOptions);
                    break;
            }
            wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            Thread.sleep(1000); // Adding a short delay
        }
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null; // Reset driver instance
            wait = null;   // Reset wait instance
        }
    }
}
