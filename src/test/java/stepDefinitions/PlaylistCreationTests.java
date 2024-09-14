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
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.LoginPage;

import java.time.Duration;
import java.util.Map;
import java.util.NoSuchElementException;

public class PlaylistCreationTests {

    WebDriver driver;
    WebDriverWait wait;
    HomePage homePage;
    LoginPage loginPage;
    SoftAssert softAssert;


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
        softAssert = new SoftAssert(); // Initialize SoftAssert
        Thread.sleep(1000);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /*
    Scenario 1: User creates a new playlist successfully
    */

    @Given("I am logged in")
    public void iAmLoggedIn() throws InterruptedException {
        driver.get("https://qa.koel.app/"); // Replace with actual login URL
        loginPage.validLogin(); // Use the method from LoginPage
        wait.until(ExpectedConditions.urlContains("/home")); // Update with the actual URL or condition for successful login
        Thread.sleep(1000);
    }

    @Given("The user is on the Playlist creation page")
    public void theUserIsOnThePlaylistCreationPage() throws InterruptedException {
        driver.get("https://qa.koel.app/");
        System.out.println("Browser opened website");
        Thread.sleep(1000);

        homePage.initiateNewPlaylistCreation();
        System.out.println("Initiated the 'Create New Playlist' process");
    }

    @When("The user selects the playlist type as New Playlist")
    public void theUserSelectsNewPlaylist() throws InterruptedException {
        homePage.selectPlaylistType(false); // Assuming 'false' represents 'New Playlist'
        Thread.sleep(1000);
    }
    // 4. And The user enters and submits a valid playlist name {string}
    @When("The user enters and submits a valid playlist name {string}")
    public void theUserEntersAndSubmitsAValidPlaylistName(String playlistName) throws InterruptedException {
        homePage.enterAndSubmitPlaylistName(playlistName);
        Thread.sleep(2000);
    }

    @Then("The new playlist should be created successfully")
    public void theNewPlaylistShouldBeCreatedSuccessfully() throws InterruptedException {
        softAssert.assertTrue(homePage.isSuccessMessageDisplayed(), "Success message should be displayed but it is not.");
        Thread.sleep(1000);
        softAssert.assertAll(); // Assert all soft assertions
    }

    @Then("The playlist {string} should be displayed in the app")
    public void thePlaylistShouldBeDisplayedInTheApp(String playlistName) throws InterruptedException {
        WebElement playlistElement = driver.findElement(By.xpath("//a[text()='" + playlistName + "']"));
        softAssert.assertTrue(playlistElement.isDisplayed(), "The playlist '" + playlistName + "' is not displayed.");
        Thread.sleep(1000);
    }

    @When("The user enters and submits an existing playlist name {string}")
    public void theUserEntersAndSubmitsAnExistingPlaylistName(String playlistName) throws InterruptedException {
        homePage.enterAndSubmitPlaylistName(playlistName); // Reuse the method for entering and submitting a playlist name
        Thread.sleep(1000);
    }

    @Then("An error message should be displayed {string}")
    public void anErrorMessageShouldBeDisplayed(String expectedErrorMessage) {
        boolean isErrorMessageDisplayed = homePage.isErrorMessageDisplayed();
        if (!isErrorMessageDisplayed) {
            System.out.println("Defect Found: Error message '" + expectedErrorMessage + "' not displayed as expected.");
        } else {
            System.out.println("Error message '" + expectedErrorMessage + "' is displayed as expected.");
        }
    }

    // Scenario 3: User tries to create a playlist with an invalid name
    @And("The user enters and submits an invalid name with chars less than 3")
    public void theUserEntersAndSubmitsAnInvalidPlaylistName() {
        String[] invalidPlaylistNames = {"A", "B", "Go", "abcdefghijk"};

        for (String playlistName : invalidPlaylistNames) {
            try {
                homePage.initiateNewPlaylistCreation();
                homePage.selectPlaylistType(false);
                homePage.enterAndSubmitPlaylistName(playlistName);


                boolean isNameTooShort = playlistName.length() < 3;
                boolean isNameTooLong = playlistName.length() > 10;

                boolean successMessageDisplayed = homePage.isSuccessMessageDisplayed();
                Thread.sleep(1000);
                boolean errorMessageDisplayed = homePage.isErrorMessageDisplayed();

                if (isNameTooShort || isNameTooLong) {
                    softAssert.assertFalse(successMessageDisplayed, "Playlist with invalid name '" + playlistName + "' should not have been created.");
                    Thread.sleep(1000);
                    softAssert.assertTrue(errorMessageDisplayed, "Error message should be displayed for invalid name '" + playlistName + "'.");
                } else {
                    softAssert.assertTrue(successMessageDisplayed, "Playlist with valid name '" + playlistName + "' should be created.");
                    Thread.sleep(1000);
                    softAssert.assertFalse(errorMessageDisplayed, "Error message should not be displayed for valid name '" + playlistName + "'.");
                }
            } catch (Exception e) {
                System.out.println("Error during test execution for playlist name '" + playlistName + "': " + e.getMessage());
            }
        }
        softAssert.assertAll();
    }



    @Then("The playlist should not be created")
    public void thePlaylistShouldNotBeCreated() {
        boolean isErrorMessageDisplayed = homePage.isErrorMessageDisplayed();
        if (!isErrorMessageDisplayed) {
            System.out.println("Defect Found: Error message not displayed as expected.");
        } else {
            System.out.println("Error message is displayed as expected.");
        }
    }
}


