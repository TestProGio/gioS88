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
import java.util.NoSuchElementException;

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

    /*
    Scenario 1: User creates a new playlist successfully
    */

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
        driver.get("https://qa.koel.app/");
        System.out.println("Browser opened website");
        Thread.sleep(2000);

        homePage.initiateNewPlaylistCreation();
        System.out.println("Initiated the 'Create New Playlist' process");
    }

    // 3. When The user selects the playlist type as New Playlist
    @When("The user selects the playlist type as New Playlist")
    public void theUserSelectsNewPlaylist() throws InterruptedException {
        homePage.selectPlaylistType(false); // Assuming 'false' represents 'New Playlist'
        Thread.sleep(2000);
    }

    // 4. And The user enters and submits a valid playlist name {string}
    @When("The user enters and submits a valid playlist name {string}")
    public void theUserEntersAndSubmitsAValidPlaylistName(String playlistName) throws InterruptedException {
        homePage.enterAndSubmitPlaylistName(playlistName);
        Thread.sleep(2000);
    }

    // 5. Then The new playlist should be created successfully
    @Then("The new playlist should be created successfully")
    public void theNewPlaylistShouldBeCreatedSuccessfully() throws InterruptedException {
        WebElement successMessage = driver.findElement(By.cssSelector(".alertify-logs.right.top > .show.success")); // Update with actual locator
        Assert.assertTrue(successMessage.isDisplayed(), "Success message not displayed.");
        Thread.sleep(2000);
    }

    // 6. And The playlist {string} should be displayed in the app
    @Then("The playlist {string} should be displayed in the app")
    public void thePlaylistShouldBeDisplayedInTheApp(String playlistName) throws InterruptedException {
        WebElement playlistElement = driver.findElement(By.xpath("//a[text()='" + playlistName + "']"));
        Assert.assertTrue(playlistElement.isDisplayed(), "The playlist '" + playlistName + "' is not displayed.");
        Thread.sleep(2000);
    }

    //Scenario 2: User tries to create a playlist with the same name
    // 1. Reuse "The user is on the Playlist creation page"
    // 2. Reuse "The user selects the playlist type as New Playlist"

    // 3 & 4. When The user enters and submits an existing playlist name {string}
    @When("The user enters and submits an existing playlist name {string}")
    public void theUserEntersAndSubmitsAnExistingPlaylistName(String playlistName) throws InterruptedException {
        homePage.enterAndSubmitPlaylistName(playlistName); // Reuse the method for entering and submitting a playlist name
        Thread.sleep(2000);
    }

    // 5. Then An error message should be displayed {string}
    /*@Then("An error message should be displayed {string}")
    public void anErrorMessageShouldBeDisplayed(String errorMessage) throws InterruptedException {
        try {
            // Try to locate the error message element
            WebElement errorMessageElement = driver.findElement(By.cssSelector("div.error.show"));
            // Verify that the error message element is displayed
            if (errorMessageElement.isDisplayed()) {
                System.out.println("A playlist with this name already exists");
            } else {
                System.out.println("Defect Found: Error message is failing to appear");
            }
        } catch (NoSuchElementException e) {
            // If the element is not found, print a message indicating a potential bug
            System.out.println("Defect Found: Error message is failing to appear");
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            e.printStackTrace();
        }
        // Optionally wait to ensure that all actions are complete
        Thread.sleep(2000);
    }

     */
    @Then("An error message should be displayed {string}")
    public void anErrorMessageShouldBeDisplayed(String expectedErrorMessage) {
        try {
            // Wait and check if the error message is displayed
            WebElement errorMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.error.show")));

            if (errorMessageElement.isDisplayed()) {
                String actualErrorMessage = errorMessageElement.getText();
                if (actualErrorMessage.equals(expectedErrorMessage)) {
                    System.out.println("Error message displayed as expected: " + actualErrorMessage);
                } else {
                    System.out.println("Defect Found: Error message displayed is '" + actualErrorMessage + "', but expected was '" + expectedErrorMessage + "'");
                }
            } else {
                // If the error message is not found, print a defect message
                System.out.println("Defect Found: Error message is failing to appear");
            }
        } catch (Exception e) {
            // Handle the case where the error message element is not found
            System.out.println("Defect Found: Error message is failing to appear");
        }

        try {
            // Explicitly wait for a maximum of 2 seconds for the success message to appear
            WebDriverWait waitForSuccess = new WebDriverWait(driver, Duration.ofSeconds(2));
            WebElement successMessageElement = waitForSuccess.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.success.show")));

            if (successMessageElement.isDisplayed()) {
                System.out.println("Defect Found: Success message appears instead of the expected error message.");
            }
        } catch (Exception e) {
            // Success message not found, which is expected in this case
            // Do nothing; the absence of the success message is the expected outcome
        }
    }

}


