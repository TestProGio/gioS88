package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;
import pages.AllSongsPage;
import pages.LoginPage;
import pages.SearchPage;
import utils.WebDriverManagerUtil;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class BaseTest {

    protected WebDriver driver;
    protected LoginPage loginPage;
    protected SearchPage searchPage;
    protected AllSongsPage allSongsPage;
    protected SoftAssert softAssert;
    private WebDriverManagerUtil webDriverManager;

    @Before
    public void setUp() throws InterruptedException {
        webDriverManager = WebDriverManagerUtil.getInstance();
        webDriverManager.setup();
        driver = webDriverManager.getDriver();
        loginPage = new LoginPage(driver);
        searchPage = new SearchPage(driver);
        allSongsPage = new AllSongsPage(driver);
        softAssert = new SoftAssert();
        Reporter.log("Step: Setup completed", true);
    }

    @After
    public void tearDown() {
        webDriverManager.tearDown();
        Reporter.log("Step: Teardown completed", true);
    }

    /**
     * Picks a browser based on the provided parameter.
     */
    public static WebDriver pickBrowser(String browser) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        String gridURL = "http://192.168.40.1:4444"; // Replace with your Grid URL

        switch (browser) {
            case "firefox":
                return new FirefoxDriver();
            case "MicrosoftEdge":
                EdgeOptions edgeOptions = new EdgeOptions();
                return new EdgeDriver(edgeOptions);
            // Grid Cases
            case "grid-edge":
                caps.setCapability("browserName", "MicrosoftEdge");
                return new RemoteWebDriver(URI.create(gridURL).toURL(), caps);
            case "grid-firefox":
                caps.setCapability("browserName", "firefox");
                return new RemoteWebDriver(URI.create(gridURL).toURL(), caps);
            case "grid-chrome":
                caps.setCapability("browserName", "chrome");
                return new RemoteWebDriver(URI.create(gridURL).toURL(), caps);
            case "cloud":
                return lambdaTest();
            default:
                ChromeOptions chromeOptions = new ChromeOptions();
                return new ChromeDriver(chromeOptions);
        }
    }

    /**
     * Sets up the LambdaTest capabilities for cloud execution.
     */
    public static WebDriver lambdaTest() throws MalformedURLException {
        String hubUrl = "https://hub.lambdatest.com/wd/hub";
        ChromeOptions browserOptions = new ChromeOptions();
        HashMap<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("username", "muhammadtestpro");
        ltOptions.put("accessKey", "SE8iAUT7KcFw8hrr9shssoC2PQCg4CTki1fpmP3OX6VDNr5ksJ");
        ltOptions.put("project", "CloudExecution");
        ltOptions.put("w3c", true);
        browserOptions.setCapability("LT:Options", ltOptions);
        return new RemoteWebDriver(new URL(hubUrl), browserOptions);
    }

    // Additional utility methods can be added here
    /**
     * A utility method to navigate to a specific URL.
     */
    public void navigateToPage(String url) {
        driver.get(url);
        Reporter.log("Navigated to: " + url, true);
    }

    /**
     * A utility method for waiting until an element is visible.
     */
    public void waitForElementVisible(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Add other methods as needed for your tests...
}
