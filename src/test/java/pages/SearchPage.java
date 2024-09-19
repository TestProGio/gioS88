package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchPage extends BasePage {
    public By searchInputField = By.cssSelector("div#searchForm > input[name='q']");
    public By searchBoxButton = By.cssSelector(" //input[@class='dirty']");
    //searchBoxButton: Trouble getting button location. You must input and clear
    public By searchResultsSong = By.xpath("//div/div/section[1]/ul/article/span[2]/span[1]");
    public By searchResultsArtist = By.xpath("//*[@id='searchExcerptsWrapper']/div/div/section[2]/p");
    public By searchResultsAlbum = By.xpath("//*[@id='searchExcerptsWrapper']/div/div/section[3]/p");
    public By searchNoArtistFoundMessage= By.xpath("//*[@id='searchExcerptsWrapper']/div/div/section[2]/p");
    public By searchNoAlbumFoundMessage= By.xpath("//*[@id='searchExcerptsWrapper']/div/div/section[3]/p");
    public By searchNoSongFoundMessage= By.xpath("//*[@id='searchExcerptsWrapper']/div/div/section[1]/p");

    public SearchPage(WebDriver givenDriver) {
        super(givenDriver);
    }

    // Methods
    public String getNoSongSearchResultsText() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Adjust the timeout as needed
            WebElement noSongElement = wait.until(ExpectedConditions.visibilityOfElementLocated(searchNoSongFoundMessage));
            return noSongElement.getText();
        } catch (NoSuchElementException | TimeoutException e) {
            // Return an empty string or a specific message indicating no element found
            return "";
        }
    }

    public String getNoAlbumSearchResultsText() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Adjust the timeout as needed
            WebElement noAlbumElement = wait.until(ExpectedConditions.visibilityOfElementLocated(searchNoAlbumFoundMessage));
            return noAlbumElement.getText();
        } catch (NoSuchElementException | TimeoutException e) {
            // Return an empty string or a specific message indicating no element found
            return "";
        }
    }

    public String getNoArtistSearchResultsText() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Adjust the timeout as needed
            WebElement noArtistElement = wait.until(ExpectedConditions.visibilityOfElementLocated(searchNoArtistFoundMessage));
            return noArtistElement.getText();
        } catch (NoSuchElementException | TimeoutException e) {
            // Return an empty string or a specific message indicating no element found
            return "";
        }
    }

    public void enterAndSearchSong(String searchSong) {
        // Input Song Name to search
        WebElement nameInput = findElement(searchInputField);
        nameInput.sendKeys(searchSong);
        nameInput.sendKeys(Keys.RETURN); // Simulate pressing Enter key
    }
    public WebElement getSongSearchResults() {
        return findElement(searchResultsSong);}

    public void enterAndSearchArtist(String searchArtist) {
        // Input Artist Name to search
        WebElement nameInput = findElement(searchInputField);
        nameInput.sendKeys(searchArtist);
        nameInput.sendKeys(Keys.RETURN); // Simulate pressing Enter key
    }
    public WebElement getArtistSearchResults() {
        return findElement(searchResultsArtist);
    }

    public void enterAndSearchAlbum(String searchAlbum) {
        // Input Album Name to search
        WebElement nameInput = findElement(searchInputField);
        nameInput.sendKeys(searchAlbum);
        nameInput.sendKeys(Keys.RETURN); // Simulate pressing Enter key
    }
    public WebElement getAlbumSearchResults() {
        return findElement(searchResultsAlbum);
    }
}

/*
package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchPage extends BasePage {

    // Locator definitions
    public By searchInputField = By.cssSelector("div#searchForm > input[name='q']");
    public By searchBoxButton = By.cssSelector("input.dirty"); // Update this selector as needed
    public By searchResultsSong = By.xpath("//div/div/section[1]/ul/article/span[2]/span[1]");
    public By searchResultsArtist = By.xpath("//*[@id='searchExcerptsWrapper']/div/div/section[2]/p");
    public By searchResultsAlbum = By.xpath("//*[@id='searchExcerptsWrapper']/div/div/section[3]/p");
    public By searchNoArtistFoundMessage = By.xpath("//*[@id='searchExcerptsWrapper']/div/div/section[2]/p");
    public By searchNoAlbumFoundMessage = By.xpath("//*[@id='searchExcerptsWrapper']/div/div/section[3]/p");
    public By searchNoSongFoundMessage = By.xpath("//*[@id='searchExcerptsWrapper']/div/div/section[1]/p");

    public SearchPage(WebDriver givenDriver) {
        super(givenDriver);
    }

    // Method to get the "No Song Found" message
    public String getNoSongSearchResultsText() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement noSongElement = wait.until(ExpectedConditions.visibilityOfElementLocated(searchNoSongFoundMessage));
            return noSongElement.getText();
        } catch (NoSuchElementException | TimeoutException e) {
            return "";
        }
    }

    // Method to get the "No Album Found" message
    public String getNoAlbumSearchResultsText() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement noAlbumElement = wait.until(ExpectedConditions.visibilityOfElementLocated(searchNoAlbumFoundMessage));
            return noAlbumElement.getText();
        } catch (NoSuchElementException | TimeoutException e) {
            return "";
        }
    }

    // Method to get the "No Artist Found" message
    public String getNoArtistSearchResultsText() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement noArtistElement = wait.until(ExpectedConditions.visibilityOfElementLocated(searchNoArtistFoundMessage));
            return noArtistElement.getText();
        } catch (NoSuchElementException | TimeoutException e) {
            return "";
        }
    }

    // Method to enter a song name and search
    public void enterAndSearchSong(String searchSong) {
        WebElement nameInput = findElement(searchInputField);
        nameInput.sendKeys(searchSong);
        nameInput.sendKeys(Keys.RETURN); // Simulate pressing Enter key
    }

    // Method to get the song search results
    public WebElement getSongSearchResults() {
        return findElement(searchResultsSong);
    }

    // Method to enter an artist name and search
    public void enterAndSearchArtist(String searchArtist) {
        WebElement nameInput = findElement(searchInputField);
        nameInput.sendKeys(searchArtist);
        nameInput.sendKeys(Keys.RETURN); // Simulate pressing Enter key
    }

    // Method to get the artist search results
    public WebElement getArtistSearchResults() {
        return findElement(searchResultsArtist);
    }

    // Method to enter an album name and search
    public void enterAndSearchAlbum(String searchAlbum) {
        WebElement nameInput = findElement(searchInputField);
        nameInput.sendKeys(searchAlbum);
        nameInput.sendKeys(Keys.RETURN); // Simulate pressing Enter key
    }

    // Method to get the album search results
    public WebElement getAlbumSearchResults() {
        return findElement(searchResultsAlbum);
    }
}

 */

