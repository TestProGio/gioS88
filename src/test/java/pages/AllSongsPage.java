package pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AllSongsPage extends BasePage{

    //Constructor
    public AllSongsPage(WebDriver givenDriver){

        super(givenDriver);
        this.wait = new WebDriverWait(givenDriver,Duration.ofSeconds(20)); // 20 seconds timeout
    }

    //Locators
    public By firstSong = By.cssSelector(".all-songs tr.song-item:nth-child(1)");
    public By playOption = By.cssSelector("li.playback");
    public By songTitle=  By. xpath("//*[@id='songsWrapper']/div/div/div[1]/table/tr[12]/td[2]");
    public By songArtist=  By. xpath("//*[@id='songsWrapper']/div/div/div[1]/table/tr[12]/td[3]");
    public By songAlbum=  By. xpath("//*[@id='queueWrapper']/div/div/div[1]/table/tr[2]/td[4]");


    //Page Methods
    public String getSongTitle() {
        //WebElement songElement = findElement(songTitle);
        //return songElement.getText();

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
