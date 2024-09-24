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

public class FavoritesFunction {
    private WebDriverManagerUtil webDriverManager;
    private LoginPage loginPage;
    private FavoritesPage favoritesPage;
    private AllSongsPage allSongsPage;
    private SoftAssert softAssert;

    // Instance variable to store song name
    private String songName; // Optional, if needed for additional functionality

@Before
public void setUp() throws InterruptedException {
    webDriverManager = WebDriverManagerUtil.getInstance(); // Get the singleton instance
    webDriverManager.setup(); // Set up WebDriver if not already set up

    // Initialize page objects with the same WebDriver instance
    loginPage = new LoginPage(webDriverManager.getDriver());
    favoritesPage = new FavoritesPage(webDriverManager.getDriver());
    allSongsPage = new AllSongsPage(webDriverManager.getDriver());
    softAssert = new SoftAssert();

    Reporter.log("Step: Setup completed : FavoritesFunction", true);
}


    @After
    public void tearDown() {
        webDriverManager.tearDown();
        Reporter.log("Step: Teardown completed: FavoritesFunction", true);
    }

    // Scenario: Favorites Playlist is empty when no songs are saved
    @Given("I'm logged in")
    public void iAmLoggedIn() {
        webDriverManager.getDriver().get("https://qa.koel.app");
        loginPage.validLogin();
        webDriverManager.getWait().until(ExpectedConditions.urlContains("/home"));
        Reporter.log("Step: I am logged in.", true);
    }

    @And("I'm on the home page")
    public void iAmOnTheHomePage() {
        webDriverManager.getDriver().get("https://qa.koel.app/#!/songs");
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
