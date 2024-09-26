/*package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class WebDriverManagerUtil {

    private WebDriver driver;
    private WebDriverWait wait;

    public WebDriver getDriver() {

        return driver;
    }

    public WebDriverWait getWait() {

        return wait;
    }

    // Method to set up WebDriver with FirefoxOptions
    public void setup() {
        try {
            WebDriverManager.firefoxdriver().setup(); // Setting up Firefox driver

            // Create Firefox options
            FirefoxOptions options = new FirefoxOptions();

            // Create a new profile
            FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("dom.webnotifications.enabled", false); // Disable notifications
            options.setProfile(profile); // Set the profile

            // Uncomment the following line to run Firefox in headless mode
            // options.setHeadless(true);

            // Initialize the FirefoxDriver with options
            driver = new FirefoxDriver(options);
            wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Use an explicit wait of 10 seconds
        } catch (Exception e) {
            System.err.println("Error during WebDriver setup: " + e.getMessage());
            // Handle logging or rethrow exception as needed
        }
    }

    // Method to tear down the WebDriver
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Ensure the driver quits properly
        }
    }
}
*/
//Chrome Version of WebDriver***********************
package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebDriverManagerUtil {

    private WebDriver driver;
    private WebDriverWait wait;

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    // Method to set up WebDriver with ChromeOptions
    public void setup() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Thread.sleep(1000); // Adding a short delay
    }

    // Method to tear down the WebDriver
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}


