package pages;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.time.Duration;

public class AllSongsPage extends BasePage {

    //Constructor
    public AllSongsPage(WebDriver givenDriver) {
        super(givenDriver);
        this.wait = new WebDriverWait(givenDriver, Duration.ofSeconds(20)); // 20 seconds timeout
    }

    //Locators:
    public By specificLikeButtonLocator = By.xpath("/html/body/div[1]/div/div[1]/section[1]/section[3]/div/div/div[1]/table/tr[19]/td[6]/button");
    public By leftMenuAllSongs = By.cssSelector("ul li a.songs");
    public By firstSong = By.cssSelector(".all-songs tr.song-item:nth-child(1)");
    public By playOption = By.cssSelector("li.playback");
    public By songTitle = By.xpath("//*[@id='songsWrapper']/div/div/div[1]/table/tr[*]/td[*]");
    public By songArtist = By.xpath("//*[@id='songsWrapper']/div/div/div[1]/table/tr[12]/td[3]");
    public By songAlbum = By.xpath("//*[@id='queueWrapper']/div/div/div[1]/table/tr[2]/td[4]");

    //Page Methods
    public WebElement likeSpecificSong() {
        // Wait until the like button is clickable
        wait.until(ExpectedConditions.elementToBeClickable(specificLikeButtonLocator));
        return driver.findElement(specificLikeButtonLocator);
    }

    // Find the "Like" button associated with a specific song title
    /*
    Note major issues with the path to favorites icon
    it is blocked by a div element
     */
    public By getLikeButtonBySongTitle(String songTitle) {
        // This XPath assumes the "Like" button is in the same row as the song title
        return By.xpath("//tr[td[contains(text(),'" + songTitle + "')]]//button[@data-test='like-btn']");
    }


public void likeSong(String songTitle) throws InterruptedException {
    // Dynamic CSS selector to find the like button associated with the song title
    By dynamicLikeButtonLocator = By.xpath("//tr[td[contains(text(), '" + songTitle + "')]]//*[@id=\"homeWrapper\"]/div/div[1]/section[1]/ol/li[1]/article/span[2]/span[2]/button/i\n");

    try {
        // Wait until the like button is clickable
        wait.until(ExpectedConditions.elementToBeClickable(dynamicLikeButtonLocator));

        // Find the button
        WebElement likeButton = driver.findElement(dynamicLikeButtonLocator);

        // Ensure the button is displayed and enabled
        if (likeButton != null && likeButton.isDisplayed() && likeButton.isEnabled()) {
            // Click the button
            likeButton.click();
        } else {
            // Scroll into view and click if it's obscured
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", likeButton);
            likeButton.click();
        }

        Reporter.log("Step: Liked the song: " + songTitle, true);
    } catch (TimeoutException e) {
        Reporter.log("Step: Failed to find the like button for the song: " + songTitle, true);
    } catch (Exception e) {
        Reporter.log("Step: An unexpected error occurred while liking the song: " + songTitle + ". Error: " + e.getMessage(), true);
        e.printStackTrace(); // Print stack trace for debugging
    }

    Thread.sleep(2000);
}

    public void clickLeftMenuAllSongs() throws InterruptedException {
        // Wait until the Favorites link is visible and clickable
        wait.until(ExpectedConditions.elementToBeClickable(leftMenuAllSongs));
        // Find the Favorites link and click on it
        driver.findElement(leftMenuAllSongs).click();
        Thread.sleep(2000); // Pause for 2 seconds
    }

    public String getSongTitle() {

        // Wait until the song title is present and then retrieve the WebElement
        WebElement songElement = wait.until(ExpectedConditions.presenceOfElementLocated(songTitle));
        return songElement.getText();
    }

    public String getSongArtist() {
        //WebElement artistElement = findElement(songArtist);
        WebElement artistElement = wait.until(ExpectedConditions.presenceOfElementLocated(songArtist));
        return artistElement.getText();
    }

    public String getSongAlbum() {
        WebElement albumElement = findElement(songAlbum);
        return albumElement.getText();
    }
    public void contextClickFirstSong(){
        actions.contextClick(findElement(firstSong)).perform();
    }
    public void choosePlayOption(){
        findElement(playOption).click();
    }

}
