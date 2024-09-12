package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
        WebElement option = findElement(optionToSelect);
        Actions actions = new Actions(driver);
        actions.click(option).perform();
    }

    public void enterAndSubmitPlaylistName(String playlistName) {
        // Fill in the playlist name
        WebElement nameInput = findElement(nameInputField);
        nameInput.sendKeys(playlistName);
        nameInput.sendKeys(Keys.RETURN); // Simulate pressing Enter key
    }

}
