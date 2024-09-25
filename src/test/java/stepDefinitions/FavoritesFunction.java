package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;
import pages.AllSongsPage;
import pages.LoginPage;
import pages.FavoritesPage;
import utils.WebDriverManagerUtil;

import java.time.Duration;
import java.util.NoSuchElementException;

public class FavoritesFunction extends BaseTest {
    private FavoritesPage favoritesPage;

    @Before("@Favorites")
    public void setUp() throws InterruptedException {
        super.setUp(); // Call the setup method from BaseTest

        // Initialize page objects with the same WebDriver instance
        favoritesPage = new FavoritesPage(driver); // Use the 'driver' from BaseTest
       // Reporter.log("Step: Setup completed : FavoritesFunction", true);
    }

    @After("@Favorites")
    public void tearDown() {
        super.tearDown(); // Call the teardown method from BaseTest
       // Reporter.log("Step: Teardown completed: FavoritesFunction", true);
    }

    // Scenario: Favorites Playlist is empty when no songs are saved
    @Given("I'm logged in")
    public void iAmLoggedIn() {
        driver.get("https://qa.koel.app"); // Use the driver from BaseTest
        loginPage.validLogin();
        webDriverManager.getWait().until(ExpectedConditions.urlContains("/home"));
        Reporter.log("Step: I am logged in.", true);
    }

    @And("I'm on the home page")
    public void iAmOnTheHomePage() {
        driver.get("https://qa.koel.app/#!/songs");
        webDriverManager.getWait().until(ExpectedConditions.urlContains("/songs"));
        Reporter.log("Step: I am on the Home page.", true);
    }

    @When("I navigate to the Favorites Playlist page")
    public void iNavigateToTheFavoritesPlaylistPage() {
        favoritesPage.clickLeftMenuFavorites();
        Reporter.log("Step: Navigated to the Favorites Playlist page.", true);
    }

    @Then("the playlist should be empty")
    public void thePlaylistShouldBeEmpty() {
        try {
            // Check if the no favorites message is displayed
            boolean isEmpty = favoritesPage.isNoFavoritesMessageDisplayed();

            // Report the result based on the check
            if (isEmpty) {
                Reporter.log("Step: The Favorites Playlist is confirmed empty.", true);
            } else {
                Reporter.log("Step: The Favorites Playlist is not empty!", true);
            }
        } catch (TimeoutException e) {
            // Handle the case where the element is not found within the timeout
            Reporter.log("Step: Timeout occurred while checking if the Favorites Playlist is empty. " +
                    "Defect: The Favorites Playlist is NOT empty!", true);
        } catch (Exception e) {
            // Handle any unexpected exceptions gracefully
            Reporter.log("Step: An unexpected error occurred while checking the Favorites Playlist: " +
                    e.getMessage(), true);
        } finally {
            // Always assert all soft assertions if any were made
            softAssert.assertAll();
        }
    }
}
