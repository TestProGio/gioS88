/*
package pages;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.time.Duration;
import java.util.Optional;

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

    public By allSongsCount = By.xpath("//span[@class='meta text-secondary']/span[contains(text(), 'songs')]");
    public By allSongsDuration = By.xpath("//span[@class='meta text-secondary']/span[contains(text(), ':')]\n");
    ;
    public By allSongsIDs = By.xpath("//*[@id='songsWrapper']/div/div/div[1]/table/tr/td[1]");
    public By songTitle = By.xpath("//*[@id='songsWrapper']/div/div/div[1]/table/tr/td[2]");
    public By songArtist = By.xpath("//*[@id='songsWrapper']/div/div/div[1]/table/tr/td[3]");
    public By songAlbum = By.xpath("//*[@id='songsWrapper']/div/div/div[1]/table/tr/td[4]");
    public By songsTime = By.xpath("//*[@id='songsWrapper']/div/div/div[1]/table/tr/td[5]");
    public By favButton = By.xpath("//*[@id='songsWrapper']/div/div/div[1]/table/tr/td[6]/button");

    //Page Methods


    public boolean songsHaveID(int index) {
        // Create a dynamic locator for each song row's ID cell using the index
        By songIdLocator = By.xpath("//*[@id='songsWrapper']/div/div/div[1]/table/tr[" + index + "]/td[1]");

        try {
            // Attempt to find the element; if not found, return false
            WebElement songElement = wait.until(ExpectedConditions.presenceOfElementLocated(songIdLocator));
            String idText = songElement.getText().trim();

            // Return true if the ID text is not empty, false otherwise
            return !idText.isEmpty();
        } catch (TimeoutException | NoSuchElementException e) {
            // Return false if the element is not found, allowing the loop to gracefully stop
            return false;
        }
    }

    public int getAllSongsCount() {
        // Wait until the song count is present and then retrieve the WebElement
        WebElement songElement = wait.until(ExpectedConditions.presenceOfElementLocated(allSongsCount));
        String countText = songElement.getText(); // Get the text (e.g., "66 songs")

        // Extract the number from the string
        String countNumber = countText.split(" ")[0]; // Splitting the string to get the number part
        return Integer.parseInt(countNumber); // Convert the string number to an integer
    }

    public String getAllSongsDuration() {
        // Wait until the song duration is present and then retrieve the WebElement
        WebElement songElement = wait.until(ExpectedConditions.presenceOfElementLocated(allSongsDuration));
        return songElement.getText(); // This returns the duration as a string (e.g., "04:32:57")
    }


    public WebElement likeSpecificSong() {
        // Wait until the like button is clickable
        wait.until(ExpectedConditions.elementToBeClickable(specificLikeButtonLocator));
        return driver.findElement(specificLikeButtonLocator);
    }

    // Find the "Like" button associated with a specific song title

   // Note major issues with the path to favorites icon it is blocked by a div element

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

    public String getSongTitle(int index) {
        // Create a dynamic locator for each song row's title cell using the index
        By songTitleLocator = By.xpath("//*[@id='songsWrapper']/div/div/div[1]/table/tr[" + index + "]/td[2]");

        try {
            // Wait until the song title element is present
            WebElement songElement = wait.until(ExpectedConditions.presenceOfElementLocated(songTitleLocator));
            return songElement.getText().trim(); // Return the title text
        } catch (TimeoutException | NoSuchElementException e) {
            return ""; // Return an empty string if the element is not found
        }
    }

    public String getSongArtist(int index) {
        // Create a dynamic locator for each song row's artist cell using the index
        By songArtistLocator = By.xpath("//*[@id='songsWrapper']/div/div/div[1]/table/tr[" + index + "]/td[3]");

        try {
            // Wait until the artist element is present
            WebElement artistElement = wait.until(ExpectedConditions.presenceOfElementLocated(songArtistLocator));
            return artistElement.getText().trim(); // Return the artist's name text
        } catch (TimeoutException | NoSuchElementException e) {
            return ""; // Return an empty string if the element is not found
        }
    }

    public String getSongAlbum(int index) {
        // Create a dynamic locator for each song row's album cell using the index
        By songAlbumLocator = By.xpath("//*[@id='songsWrapper']/div/div/div[1]/table/tr[" + index + "]/td[4]");

        try {
            // Wait until the album element is present
            WebElement albumElement = wait.until(ExpectedConditions.presenceOfElementLocated(songAlbumLocator));
            return albumElement.getText().trim(); // Return the album's name text
        } catch (TimeoutException | NoSuchElementException e) {
            return ""; // Return an empty string if the element is not found
        }
    }
    public String getSongDuration(int index) {
        // Create a dynamic locator for each song row's duration cell using the index
        By songDurationLocator = By.xpath("//*[@id='songsWrapper']/div/div/div[1]/table/tr[" + index + "]/td[5]");

        try {
            // Wait until the song duration element is present
            WebElement durationElement = wait.until(ExpectedConditions.presenceOfElementLocated(songDurationLocator));
            return durationElement.getText().trim(); // Return the duration text
        } catch (TimeoutException | NoSuchElementException e) {
            return ""; // Return an empty string if the element is not found
        }
    }

    public void contextClickFirstSong(){

        actions.contextClick(findElement(firstSong)).perform();
    }

    public void choosePlayOption(){

        findElement(playOption).click();
    }

}

 */

package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.time.Duration;

public class AllSongsPage extends BasePage {

    // Constructor
    public AllSongsPage(WebDriver givenDriver) {
        super(givenDriver);
        this.wait = new WebDriverWait(givenDriver, Duration.ofSeconds(20)); // 20 seconds timeout
    }

    // Locators
    public By specificLikeButtonLocator = By.xpath("/html/body/div[1]/div/div[1]/section[1]/section[3]/div/div/div[1]/table/tr[19]/td[6]/button");
    public By leftMenuAllSongs = By.cssSelector("ul li a.songs");
    public By firstSong = By.cssSelector(".all-songs tr.song-item:nth-child(1)");
    public By playOption = By.cssSelector("li.playback");

    public By allSongsCount = By.xpath("//span[@class='meta text-secondary']/span[contains(text(), 'songs')]");
    public By allSongsDuration = By.xpath("//span[@class='meta text-secondary']/span[contains(text(), ':')]");

    // Use index as a parameter in methods instead of defining it here
    public By songTitle(int index) {
        return By.xpath("//*[@id='songsWrapper']/div/div/div[1]/table/tr[" + index + "]/td[2]");
    }

    public By songArtist(int index) {
        return By.xpath("//*[@id='songsWrapper']/div/div/div[1]/table/tr[" + index + "]/td[3]");
    }

    public By songAlbum(int index) {
        return By.xpath("//*[@id='songsWrapper']/div/div/div[1]/table/tr[" + index + "]/td[4]");
    }

    public By songsTime(int index) {
        return By.xpath("//*[@id='songsWrapper']/div/div/div[1]/table/tr[" + index + "]/td[5]");
    }

    public By allSongsIDs(int index) {
        return By.xpath("//*[@id='songsWrapper']/div/div/div[1]/table/tr[" + index + "]/td[1]");
    }

    public By favButton = By.xpath("//*[@id='songsWrapper']/div/div/div[1]/table/tr/td[6]/button");

    // Page Methods
    public boolean songsHaveID(int index) {
        try {
            WebElement songElement = wait.until(ExpectedConditions.presenceOfElementLocated(allSongsIDs(index)));
            String idText = songElement.getText().trim();
            return !idText.isEmpty();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public int getAllSongsCount() {
        WebElement songElement = wait.until(ExpectedConditions.presenceOfElementLocated(allSongsCount));
        String countText = songElement.getText(); // Get the text (e.g., "66 songs")
        String countNumber = countText.split(" ")[0]; // Splitting the string to get the number part
        return Integer.parseInt(countNumber); // Convert the string number to an integer
    }

    public String getAllSongsDuration() {
        WebElement songElement = wait.until(ExpectedConditions.presenceOfElementLocated(allSongsDuration));
        return songElement.getText(); // This returns the duration as a string (e.g., "04:32:57")
    }

    public WebElement likeSpecificSong() {
        wait.until(ExpectedConditions.elementToBeClickable(specificLikeButtonLocator));
        return driver.findElement(specificLikeButtonLocator);
    }

    public By getLikeButtonBySongTitle(String songTitle) {
        return By.xpath("//tr[td[contains(text(),'" + songTitle + "')]]//button[@data-test='like-btn']");
    }

    public void likeSong(String songTitle) throws InterruptedException {
        By dynamicLikeButtonLocator = By.xpath("//tr[td[contains(text(), '" + songTitle + "')]]//*[@id=\"homeWrapper\"]/div/div[1]/section[1]/ol/li[1]/article/span[2]/span[2]/button/i");

        try {
            wait.until(ExpectedConditions.elementToBeClickable(dynamicLikeButtonLocator));
            WebElement likeButton = driver.findElement(dynamicLikeButtonLocator);
            if (likeButton != null && likeButton.isDisplayed() && likeButton.isEnabled()) {
                likeButton.click();
            } else {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", likeButton);
                likeButton.click();
            }
            Reporter.log("Step: Liked the song: " + songTitle, true);
        } catch (TimeoutException e) {
            Reporter.log("Step: Failed to find the like button for the song: " + songTitle, true);
        } catch (Exception e) {
            Reporter.log("Step: An unexpected error occurred while liking the song: " + songTitle + ". Error: " + e.getMessage(), true);
            e.printStackTrace();
        }

        Thread.sleep(2000);
    }

    public void clickLeftMenuAllSongs() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(leftMenuAllSongs));
        driver.findElement(leftMenuAllSongs).click();
        Thread.sleep(2000); // Pause for 2 seconds
    }

    public String getSongTitle(int index) {
        try {
            WebElement songElement = wait.until(ExpectedConditions.presenceOfElementLocated(songTitle(index)));
            return songElement.getText().trim(); // Return the title text
        } catch (TimeoutException | NoSuchElementException e) {
            return ""; // Return an empty string if the element is not found
        }
    }

    public String getSongArtist(int index) {
        try {
            WebElement artistElement = wait.until(ExpectedConditions.presenceOfElementLocated(songArtist(index)));
            return artistElement.getText().trim(); // Return the artist's name text
        } catch (TimeoutException | NoSuchElementException e) {
            return ""; // Return an empty string if the element is not found
        }
    }

    public String getSongAlbum(int index) {
        try {
            WebElement albumElement = wait.until(ExpectedConditions.presenceOfElementLocated(songAlbum(index)));
            return albumElement.getText().trim(); // Return the album's name text
        } catch (TimeoutException | NoSuchElementException e) {
            return ""; // Return an empty string if the element is not found
        }
    }

    public String getSongDuration(int index) {
        try {
            WebElement durationElement = wait.until(ExpectedConditions.presenceOfElementLocated(songsTime(index)));
            return durationElement.getText().trim(); // Return the duration text
        } catch (TimeoutException | NoSuchElementException e) {
            return ""; // Return an empty string if the element is not found
        }
    }

    public void contextClickFirstSong() {
        actions.contextClick(findElement(firstSong)).perform();
    }

    public void choosePlayOption() {
        findElement(playOption).click();
    }
}
