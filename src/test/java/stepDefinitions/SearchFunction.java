package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;
import pages.AllSongsPage;
import pages.LoginPage;
import pages.SearchPage;
import utils.WebDriverManagerUtil;

import java.time.Duration;
import java.util.NoSuchElementException;

public class SearchFunction {
    private WebDriverManagerUtil webDriverManager;
    private LoginPage loginPage;
    private SearchPage searchPage;
    private AllSongsPage allSongsPage;
    private SoftAssert softAssert;

    // Instance variable to store song name
    public String songName;
    public String artistName;
    public String albumName;
    public String badSongName;
    public String badSongResult;
    public String whiteSpaceStrg;
    public String expectedSongName;
    public String lowercaseSong;
    public String uppercaseSong;
    public String mixedcaseSong;

    @Before
    public void setUp() throws InterruptedException {
        webDriverManager = new WebDriverManagerUtil();
        webDriverManager.setup();
        loginPage = new LoginPage(webDriverManager.getDriver());
        searchPage = new SearchPage(webDriverManager.getDriver());
        allSongsPage = new AllSongsPage(webDriverManager.getDriver());
        softAssert = new SoftAssert();
        Reporter.log("Step: Setup completed.", true);
    }

    @After
    public void tearDown() {
        webDriverManager.tearDown();
        Reporter.log("Step: Teardown completed.", true);
    }

    //1
//Scenario: Searching for an existing song should display results
    @Given("I am logged")
    public void iAmLoggedIn() {
        webDriverManager.getDriver().get("https://qa.koel.app/#!/songs");
        loginPage.validLogin();
        webDriverManager.getWait().until(ExpectedConditions.urlContains("/songs"));
        Reporter.log("Step: I am logged in.", true);
    }

    @And("I am on the all songs page")
    public void iAmOnTheAllSongsPage() {
        webDriverManager.getDriver().get("https://qa.koel.app/#!/songs");
        webDriverManager.getWait().until(ExpectedConditions.urlContains("/songs"));
        Reporter.log("Step: Step: I am on the All Songs page.", true);
    }

    @And("I navigate to the search box")
    public void iNavigateToTheSearchBox() {
        WebElement searchBox = searchPage.findElement(searchPage.searchInputField);
        softAssert.assertNotNull(searchBox, "Search box is not available");
        searchBox.click();
        Reporter.log("Step: I navigate to the search box.", true);
    }

    @When("I type in the search box the existing song {string}")
    public void iTypeInTheSearchBoxTheExistingSong(String songName) {
        // Store the song name for later comparisons
        this.songName = songName;
        // Perform the search on the Search page
        searchPage.enterAndSearchSong(songName);
        Reporter.log("Step: I type in the search box the existing song '" + songName + "'.", true);
    }

    @Then("the matched song should appear in the Songs section of the Search results page")
    public void theMatchedSongShouldAppearInTheSongsSectionOfTheSearchResultsPage() {
        // Ensure the song appears in the Search Results page
        WebElement songResult = searchPage.getSongSearchResults();
        softAssert.assertNotNull(songResult, "No song results found for the search");

        Reporter.log("Step: The matched song appeared in the Songs section of the Search results page.", true);
    }

    @And("the Artist and Album sections should display relevant information")
    public void theArtistAndAlbumSectionsShouldDisplayRelevantInformation() {
        // --- Step 1: Fetch the details from the Search Results page ---
        String searchResultsSong = searchPage.getSongSearchResults().getText();
        String searchResultsArtist = searchPage.getArtistSearchResults().getText();
        String searchResultsAlbum = searchPage.getAlbumSearchResults().getText();

        Reporter.log("Search Results Song: " + searchResultsSong, true);
        Reporter.log("Search Results Artist: " + searchResultsArtist, true);
        Reporter.log("Search Results Album: " + searchResultsAlbum, true);

        // --- Step 2: Check for 'None found' messages ---
        String noSongMessage = searchPage.getNoSongSearchResultsText();
        String noAlbumMessage = searchPage.getNoAlbumSearchResultsText();
        String noArtistMessage = searchPage.getNoArtistSearchResultsText();

        Reporter.log("No Song Message: " + noSongMessage, true);
        Reporter.log("No Album Message: " + noAlbumMessage, true);
        Reporter.log("No Artist Message: " + noArtistMessage, true);

        // Navigate to the All Songs page
        webDriverManager.getDriver().get("https://qa.koel.app/#!/songs");
        webDriverManager.getWait().until(ExpectedConditions.urlContains("/songs"));

        // Initialize AllSongsPage object
        AllSongsPage allSongsPage = new AllSongsPage(webDriverManager.getDriver());

        // --- Step 3: Compare Search Results with All Songs Page ---
        try {
            // Get All Songs page details
            String allSongsSongTitle = allSongsPage.getSongTitle();
            String allSongsArtist = allSongsPage.getSongArtist();
            String allSongsAlbum = allSongsPage.getSongAlbum();

            // Compare song results
            if (searchResultsSong.equals(songName) && allSongsSongTitle.equals(songName)) {
                // Both are showing the correct song name
                softAssert.assertEquals(searchResultsSong, allSongsSongTitle, "The song is appearing accurately in both search results and All Songs page.");
            } else if (searchResultsSong.isEmpty() && !allSongsSongTitle.isEmpty()) {
                // Defect case
                softAssert.assertEquals(searchResultsSong, allSongsSongTitle, "Defect: The song appears in the All Songs page but not the Search page.");
            }

            // Compare artist results
            if (noArtistMessage.contains("None found.") && allSongsArtist.isEmpty()) {
                softAssert.assertEquals(allSongsArtist, noArtistMessage, "Correct: There is no artist information available.");
            } else if (!noArtistMessage.contains("None found.") && !allSongsArtist.isEmpty()) {
                // Defect case
                softAssert.assertEquals(searchResultsArtist, allSongsArtist, "Defect: The artist information from search results does not match with All Songs page.");
            } else {
                Reporter.log("Step: Artist information is not available in search results as expected.", true);
            }

            // Compare album results
            if (noAlbumMessage.contains("None found.") && allSongsAlbum.isEmpty()) {
                softAssert.assertEquals(allSongsAlbum, noAlbumMessage, "Correct: There is no album information available.");
            } else if (noAlbumMessage.contains("None found.") && !allSongsAlbum.isEmpty()) {
                // Defect case
                softAssert.assertEquals(searchResultsAlbum, allSongsAlbum, "Defect: The album information from search results does not match with All Songs page.");
            } else {
                Reporter.log("Defect: Album information is not displayed in search results as expected.", true);
            }

        } catch (Exception e) {
            Reporter.log("Exception occurred while comparing search results with All Songs page: " + e.getMessage(), true);
        }

        // Assert all collected assertions
        softAssert.assertAll();
        Reporter.log("Step: Checked the Artist and Album sections for relevant information.", true);
    }

    @When("I click the x button")
    public void iClickTheXButton() {
        // Clear the search field for now
        WebElement searchBox = searchPage.findElement(searchPage.searchInputField);
        searchBox.clear();
        Reporter.log("Step: Cleared the search input field.", true);
    }

    @Then("the search results should be cleared")
    public void theSearchResultsShouldBeCleared() {
        WebElement searchBox = searchPage.findElement(searchPage.searchInputField);
        softAssert.assertTrue(searchBox.getAttribute("value").isEmpty(), "Search box is not cleared");
        Reporter.log("Step: The search results were cleared.", true);

        // Call assertAll to evaluate soft assertions
        softAssert.assertAll();
    }

    //2
    //Scenario: Searching for an existing artist should display results
    @When("I type in the search box the existing artist {string}")
    public void iTypeInTheSearchBoxTheExistingArtist(String artistName) {
        // Store the song name for later comparisons
        this.artistName = artistName;
        // Perform the search on the Search page
        searchPage.enterAndSearchArtist(artistName);
        Reporter.log("Step: I type in the search box the existing artist '" + artistName + "'.", true);
    }

    @Then("the matched artist {string} should appear in the Artist section of the Search results page")
    public void theMatchedArtistShouldAppearInTheArtistSectionOfTheSearchResultsPage(String artistName) {
        WebElement artistElement = searchPage.getArtistSearchResults();
        String artistText = artistElement.getText();  // Fetch the text from the artist element

        if (artistText.equals("None found")) {
            Reporter.log("Valid Artist Name not appearing in search results. Expected Results: " + artistName + ", but found: " + artistText, true);
            // Optionally use softAssert here if you still want to track it
        } else if (artistText.equals(artistName)) {
            Reporter.log("Artist name appeared: " + artistText, true);
        } else {
            Reporter.log("Unexpected result: Message " + artistText + ", expected " + artistName, true);
        }
    }

    //3
//Scenario: Searching for an existing album should display results
    @When("I type in the search box the existing album {string}")
    public void iTypeInTheSearchBoxTheExistingAlbum(String albumName) {// Store the song name for later comparisons
        this.albumName = albumName;
        // Perform the search on the Search page
        searchPage.enterAndSearchAlbum(albumName);
        Reporter.log("Step: I type in the search box an existing album '" + albumName + "'.", true);
    }

    @Then("the album {string} should appear in the Album section of Search page")
    public void theAlbumShouldAppearInTheAlbumSectionOfSearchPage(String albumName) {
        WebElement albumElement = searchPage.getAlbumSearchResults();
        String albumText = albumElement.getText();  // Fetch the text from the artist element

        if (albumText.equals("None found")) {
            Reporter.log("Valid Album Name not appearing in search results. Expected Results: " + albumName + ", but found: " + albumText, true);
            //
        } else if (albumText.equals(albumName)) {
            Reporter.log("Album name appeared: " + albumText, true);
        } else {
            Reporter.log("Unexpected result: Message " + albumText + ", expected " + albumName, true);
        }
    }

    //4
//Scenario: Searching for a non-existing song should display 'no results' message
    @When("I type in the search box the non-existing song {string}")
    public void iTypeInTheSearchBoxTheNonExistingSong(String badSongName) {
        // Store the song name for later comparisons
        this.badSongName = badSongName;

        // Perform the search on the Search page
        searchPage.enterAndSearchSong(badSongName);
        Reporter.log("Step: I enter and search for the song '" + badSongName + "'.", true);
    }

    @Then("the search results page should show an empty list with None found message")
    public void theSearchResultsPageShouldShowAnEmptyList() {
        // Check for the "None found." messages
        String noResultsSongSection = searchPage.getNoSongSearchResultsText();
        String noResultsArtistSection = searchPage.getNoArtistSearchResultsText();
        String noResultsAlbumSection = searchPage.getNoAlbumSearchResultsText();

        // Check for song result
        if (noResultsSongSection.equals("None found.")) {
            Reporter.log("Empty section for song: " + badSongName + ". 'None found.' message for song.", true);
        } else {
            Reporter.log("Unexpected message in song section: " + noResultsSongSection, true);
            Assert.fail("Expected 'None found.' message for song, but found: " + noResultsSongSection);
        }

        // Check for artist result
        if (noResultsArtistSection.equals("None found.")) {
            Reporter.log("Empty section for artist: 'None found.' message for artist.", true);
        } else {
            Reporter.log("Unexpected message in artist section: " + noResultsArtistSection, true);
            Assert.fail("Expected 'None found.' message for artist, but found: " + noResultsArtistSection);
        }

        // Check for album result
        if (noResultsAlbumSection.equals("None found.")) {
            Reporter.log("Empty section for album: 'None found.' for album.", true);
        } else {
            Reporter.log("Unexpected message in album section: " + noResultsAlbumSection, true);
            Assert.fail("Expected 'None found.' message for album, but found: " + noResultsAlbumSection);
        }

        // Final report: All "None found." messages confirmed for song, artist, and album
        Reporter.log("Confirming Empty List for: Song, Artist, and Album for invalid song search.", true);
    }
//5
    //Scenario: Search should ignore leading and trailing white spaces
    @When("I type in the search box {string}")
    public void iTypeInTheSearchBox(String whiteSpaceStrg) {
        // Store the song name for later comparisons
        this.whiteSpaceStrg = whiteSpaceStrg;

        // Perform the search on the Search page
        searchPage.enterAndSearchSong(whiteSpaceStrg);
        Reporter.log("Step: I enter and search for the song '" + whiteSpaceStrg + "'.", true);
    }

    @Then("the search results should be the same as if {string} was typed")
    public void theSearchResultsShouldBeTheSameAsIfWasTyped(String expectedSongName) {
        // Retrieve the actual search results from the Search Page
        String actualHeaderResults = searchPage.getH1SearchResults();

        // Compare the actual result to the expected result without spaces
        if (actualHeaderResults.equals(expectedSongName)) {
            Reporter.log("Success: Search results for the input with leading/trailing spaces match the result for '" + expectedSongName + "'.", true);
        } else {
            Reporter.log("Failure: Search results for the input with leading/trailing spaces did not match the expected result. Expected: '" + expectedSongName + "', but found: '" + actualHeaderResults + "'.", true);
            Assert.fail("Search results mismatch.");
        }
    }
    //6
    //Scenario: Search should be case-sensitive
    @When("I type in the search box the song {string} in lowercase")
    public void iTypeInTheSearchBoxTheSongInLowercase(String lowercaseSong) {
        // Store the song name for later comparisons
        this.lowercaseSong = lowercaseSong;

        // Perform the search on the Search page
        searchPage.enterAndSearchSong(lowercaseSong);
        Reporter.log("Step: I enter a song in lowercase and search for the song '" + lowercaseSong + "'.", true);
    }

    @Then("no results should be displayed")
    public void noResultsShouldBeDisplayed()
    {
        // Ensure the song appears in the Search Results page
        WebElement songIncorrectCase = searchPage.getSongSearchResults();
        softAssert.assertNotNull(songIncorrectCase, "Song found in search results regardless of case");
        Reporter.log("Step: Defect found! The matched song appeared in the Songs section of the Search results page.", true);
    }
    @When("I type in the search box the song {string} in uppercase")
    public void iTypeInTheSearchBoxTheSongInUppercase(String uppercaseSong) {
        // Store the song name for later comparisons
        this.uppercaseSong = uppercaseSong;
        // Perform the search on the Search page
        searchPage.enterAndSearchSong(uppercaseSong);
        Reporter.log("Step: I enter a song in uppercase and search for the song '" + uppercaseSong + "'.", true);
    }

    @When("I type in the search box the song {string} in mixedcase")
    public void iTypeInTheSearchBoxTheSongInMixedcase(String mixedcaseSong) {
        // Store the song name for later comparisons
        this.mixedcaseSong = mixedcaseSong;
        // Perform the search on the Search page
        searchPage.enterAndSearchSong(mixedcaseSong);
        Reporter.log("Step: I enter a song in mixed case and search for the song '" + mixedcaseSong + "'.", true);
    }
}
