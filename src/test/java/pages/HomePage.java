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
    public By userAvatarIcon = By.cssSelector("img.avatar");
    public By allSongsList = By.cssSelector("li a.songs");
    public By createPlaylist = By.cssSelector("section#playlists i[role='button']");
    public By selectNewPlaylist = By.xpath("//*[@id='playlists']/nav/ul/li[1]");
    public By selectNewSmartPlaylist = By.xpath("//*[@id='playlists']/nav/ul/li[2]");

    public By selectFirstSmartPlaylist = By.xpath("//*[@id='playlists']/ul/li[3]/a");
    public By selectDeleteSmartPlaylist = By.xpath("//*[@id='playlists']/ul/li[3]/nav/ul/li[2]");
    public By selectEditSmartPlaylist = By.xpath("//*[@id='playlists']/ul/li[3]/nav/ul/li[1]");

    public By nameInputField = By.cssSelector("input[name='name']");
    public By errorMessage = By.cssSelector("div.error.show");
    public By successMessage = By.cssSelector("div.success.show");
    public By specificLikeButtonLocator = By.xpath("//*[@id='homeWrapper']/div/div[1]/section[1]/ol/li[1]/article/span[2]/span[2]/button");

    //Methods
    public WebElement likeSpecificSong() {
        // Wait until the like button is clickable
        wait.until(ExpectedConditions.elementToBeClickable(specificLikeButtonLocator));
        return driver.findElement(specificLikeButtonLocator);
    }

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
    public void hoverOverSmartPlaylistMenu() {
        // Create an instance of Actions
        WebElement smartPlaylistMenu = findElement(selectFirstSmartPlaylist);

        // Create WebDriverWait object to ensure the element is visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOf(smartPlaylistMenu));

        // Create a new Actions instance
        Actions actions = new Actions(driver);

        // Hover over the first playlist
        actions.moveToElement(smartPlaylistMenu).perform();

        // Adding a short wait to ensure the hover action has taken effect
        try {
            Thread.sleep(2000); // Wait for 2 seconds after hovering
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
        }
    }

    public void rightClickToDeleteSmartPlaylist() {
        // Create an instance of Actions
        WebElement smartPlaylistMenu = findElement(selectFirstSmartPlaylist);

        // Right-click the first playlist to open the context menu
        Actions actions = new Actions(driver);
        actions.contextClick(smartPlaylistMenu).perform();

        // Determine the delete option
        By optionToDelete = selectDeleteSmartPlaylist;

        // Create WebDriverWait object
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Wait until the delete element is clickable
        WebElement deleteOption = wait.until(ExpectedConditions.elementToBeClickable(optionToDelete));

        // Perform the click action (delete)
        deleteOption.click();
    }

    public void rightClickToEditSmartPlaylist() {
        // Create an instance of Actions
        WebElement smartPlaylistMenu = findElement(selectFirstSmartPlaylist);

        // Right-click the first playlist to open the context menu
        Actions actions = new Actions(driver);
        actions.contextClick(smartPlaylistMenu).perform();

        // Determine the edit option
        By optionToEdit = selectEditSmartPlaylist;

        // Create WebDriverWait object
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Wait until the edit element is clickable
        WebElement editOption = wait.until(ExpectedConditions.elementToBeClickable(optionToEdit));

        // Perform the click action (edit)
        editOption.click();
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

    public boolean isSuccessMessageDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement success = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            return success.isDisplayed();
        } catch (TimeoutException e) {
            return false; // If the message is not displayed, return false
        }
    }

    public String getSuccessMessageText() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            // Wait for the success message to be visible
            WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            String messageText = successElement.getText(); // Get the text of the success message
            System.out.println("Success Message: " + messageText); // Log the success message
            return messageText; // Return the text of the success message
        } catch (TimeoutException e) {
            System.out.println("Success message not found."); // Log a message if not found
            return ""; // Return empty string if the message is not found
        }
    }
}