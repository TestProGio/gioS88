package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.HomePage;
import pages.LoginPage;

import java.time.Duration;

public class PlaylistCreationTests {

    WebDriver driver;
    WebDriverWait wait;
    HomePage homePage;
    LoginPage loginPage;

    @Before
    public void setUp() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        homePage = new HomePage(driver); // Initialize HomePage
        loginPage = new LoginPage(driver); // Initialize LoginPage
        Thread.sleep(1000);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // 1. Given I am logged in
    @Given("I am logged in")
    public void iAmLoggedIn() throws InterruptedException {
        driver.get("https://qa.koel.app/"); // Replace with actual login URL
        loginPage.validLogin(); // Use the method from LoginPage
        wait.until(ExpectedConditions.urlContains("/home")); // Update with the actual URL or condition for successful login
        Thread.sleep(1000);
    }

    // 2. And The user is on the Playlist creation page
    @Given("The user is on the Playlist creation page")
    public void theUserIsOnThePlaylistCreationPage() throws InterruptedException {
        // Navigate to the home page
        driver.get("https://qa.koel.app/");
        System.out.println("Browser opened website");
        Thread.sleep(2000);

        // Initiate the creation of a new playlist
        homePage.initiateNewPlaylistCreation();
        System.out.println("Initiated the 'Create New Playlist' process");
    }

    // 3. When The user selects the playlist type as New Playlist
    @When("The user selects the playlist type as New Playlist")
    public void theUserSelectsNewPlaylist() throws InterruptedException {
        homePage.selectPlaylistType(false); // Assuming true represents 'New Playlist'
        Thread.sleep(2000);
    }

    // 4. And The user enters a valid playlist name {string}
    @When("The user enters and submits a valid playlist name {string}")
    public void theUserEntersAndSubmitsAValidPlaylistName(String playlistName) throws InterruptedException {
        homePage.enterAndSubmitPlaylistName(playlistName);
        Thread.sleep(2000); // Optional: Add if you need to wait for processing
    }

    // 5. Then The new playlist should be created successfully
    @Then("The new playlist should be created successfully")
    public void theNewPlaylistShouldBeCreatedSuccessfully() throws InterruptedException {
        WebElement successMessage = driver.findElement(By.cssSelector(".alertify-logs.right.top > .show.success")); // Update with actual locator
        Assert.assertTrue(successMessage.isDisplayed());
        Thread.sleep(2000);
    }

    // 6. And The playlist {string} should be displayed in the app
    @Then("The playlist {string} should be displayed in the app")
    public void thePlaylistShouldBeDisplayedInTheApp(String playlistName) throws InterruptedException {
        WebElement playlistElement = driver.findElement(By.xpath("//a[text()='" + playlistName + "']"));
        Assert.assertTrue(playlistElement.isDisplayed(), "The playlist '" + playlistName + "' is not displayed.");
        Thread.sleep(2000);
    }
}

    /*

    @When("The user enters a valid playlist name {string}")
    public void theUserEntersAValidPlaylistName(String playlistName) throws InterruptedException {

    }

    @When("The user enters an existing playlist name {string}")
    public void theUserEntersAnExistingPlaylistName(String playlistName) throws InterruptedException {
        homePage.enterPlaylistName(playlistName);
        Thread.sleep(1000);
    }

    @When("The user enters an invalid playlist name {string}")
    public void theUserEntersAnInvalidPlaylistName(String playlistName) throws InterruptedException {
        homePage.enterPlaylistName(playlistName);
        Thread.sleep(1000);
    }


    @Then("An error message should be displayed {string}")
    public void anErrorMessageShouldBeDisplayed(String errorMessage) throws InterruptedException {
        WebElement errorElement = driver.findElement(By.id("errorMessage")); // Update with actual locator
        Assert.assertEquals(errorElement.getText(), errorMessage);
        Thread.sleep(1000);
    }

    @Then("A red border should be displayed around the name input field")
    public void aRedBorderShouldBeDisplayedAroundTheNameInputField() throws InterruptedException {
        WebElement nameInput = driver.findElement(By.cssSelector("input[name='name']")); // Update with actual locator
        String borderColor = nameInput.getCssValue("border-color");
        Assert.assertEquals(borderColor, "rgb(255, 0, 0)"); // Assuming red border color
        Thread.sleep(1000);
    }

    @Then("The playlist should not be created")
    public void thePlaylistShouldNotBeCreated() {
        // Check that the playlist was not created; could be done by checking if a new playlist exists or some other method
        Assert.fail("Verification for playlist not created is not implemented");
    }

    @Then("The playlist name should be displayed correctly in the database")
    public void thePlaylistNameShouldBeDisplayedCorrectlyInTheDatabase() {
        // Database verification logic should be implemented here, possibly using JDBC or another method
        Assert.fail("Database verification is not implemented");
    }

     */

