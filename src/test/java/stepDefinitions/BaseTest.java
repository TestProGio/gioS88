package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;
import pages.AllSongsPage;
import pages.LoginPage;
import pages.SearchPage;
import utils.WebDriverManagerUtil;

public class BaseTest {

    protected WebDriver driver;
    protected LoginPage loginPage;
    protected SearchPage searchPage;
    protected AllSongsPage allSongsPage;
    protected SoftAssert softAssert;
    protected WebDriverManagerUtil webDriverManager;

    // Add a browser type variable
    private String browser = "chrome"; // Default browser, can be modified

    // Ensure WebDriverManagerUtil is initialized in the constructor
    public BaseTest() {
        if (webDriverManager == null) {
            webDriverManager = WebDriverManagerUtil.getInstance(); // Ensure singleton instance
        }
    }

    @Before
    public void setUp() throws InterruptedException {
        if (webDriverManager == null) {
            webDriverManager = WebDriverManagerUtil.getInstance(); // Safety check
        }
        webDriverManager.setup(browser); // Pass the browser type to setup
        driver = webDriverManager.getDriver(); // Get WebDriver instance
        loginPage = new LoginPage(driver);
        searchPage = new SearchPage(driver);
        allSongsPage = new AllSongsPage(driver);
        softAssert = new SoftAssert();
        Reporter.log("Step: Setup completed", true);
    }

    @After
    public void tearDown() {
        if (webDriverManager != null) {
            webDriverManager.tearDown(); // Ensure proper cleanup
        }
        Reporter.log("Step: Teardown completed", true);
    }

    // Method to dynamically change browser type
    public void setBrowser(String browserType) {
        this.browser = browserType;
    }

    // Remaining methods...
}
