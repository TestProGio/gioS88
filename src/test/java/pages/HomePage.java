package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

public class HomePage extends BasePage {

    // Constructor
    public HomePage(WebDriver givenDriver) {
        super(givenDriver);
    }

    // Locators
    By userAvatarIcon = By.cssSelector("img.avatar");
    By allSongsList = By.cssSelector("li a.songs");
    By createPlaylist = By.cssSelector("section#playlists i[role='button']");
    By selectNewPlaylist = By.xpath("//*[@id='playlists']/nav/ul/li[1]");
    By selectNewSmartPlaylist = By.xpath("//*[@id='playlists']/nav/ul/li[2]");
    By nameInputField = By.cssSelector("input[name='name']");
    By errorMessage = By.cssSelector("div.error.show");
    By successMessage = By.cssSelector("div.success.show");

    // Methods
    public WebElement getUserAvatar() {
        return findElement(userAvatarIcon);
    }

    public void chooseAllSongsList() {
        findElement(allSongsList).click();
    }

    public void initiateNewPlaylistCreation() {
        // Create an instance of Actions
        Actions actions = new Actions(driver);

        // Click the "Create New Playlist" button
        WebElement playlistButton = findElement(createPlaylist);
        actions.click(playlistButton).perform();
    }

public void selectPlaylistType(boolean isSmartPlaylist) {
    // Select the appropriate option based on the parameter
    By optionToSelect = isSmartPlaylist ? selectNewSmartPlaylist : selectNewPlaylist;

    // Create WebDriverWait object
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    // Wait until the element is clickable
    WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionToSelect));

    // Perform the click action
    Actions actions = new Actions(driver);
    actions.click(option).perform();
}
    public void enterAndSubmitPlaylistName(String playlistName) {
        // Fill in the playlist name
        WebElement nameInput = findElement(nameInputField);
        nameInput.sendKeys(playlistName);
        nameInput.sendKeys(Keys.RETURN); // Simulate pressing Enter key
    }

    // Wait for and check if the error message is displayed
    public boolean isErrorMessageDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
            return error.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Wait for and check if the success message is displayed
    public boolean isSuccessMessageDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement success = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            return success.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}