package pages;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;

public class SmartPlayListPage extends BasePage {

   //NAME THE PLAYLIST
    public By playlistName = By.cssSelector("input[name='name']");

    // FIRST DROWN DOWN - RULES
    public By dropdownTitle = By.cssSelector("select[name='model[]'] option:nth-child(1)");
    public By dropdownAlbum = By.cssSelector("select[name=‘model[]’] option:nth-child(2)");
    public By dropdownArtist = By.cssSelector("select[name=‘model[]’] option:nth-child(3)");
    public By dropdownPlays = By.cssSelector("select[name=‘model[]’] option:nth-child(4)");
    public By dropdownLastPlayed = By.cssSelector("select[name=‘model[]’] option:nth-child(5)");
    public By dropdownLength = By.cssSelector("select[name=‘model[]’] option:nth-child(6)");
    public By dropdownDateAdded = By.cssSelector("select[name=‘model[]’] option:nth-child(7)");
    public By dropdownDateModified = By.cssSelector("select[name=‘model[]’] option:nth-child(8)");


    // SECOND DROP DOWN CONDITIONS - RULES
    public By dropdownIs = By.cssSelector("#mainWrapper select:nth-of-type(2) > option:first-of-type");
    public By dropdownIsNot = By.cssSelector("#mainWrapper select:nth-of-type(2) > option:nth-of-type(2)");
    public By dropdownContains = By.cssSelector("#mainWrapper select:nth-of-type(2) > option:nth-of-type(3)");
    public By dropdownDoesNotContain = By.cssSelector("#mainWrapper select:nth-of-type(2) > option:nth-of-type(4)");
    public By dropdownBeginsWith = By.cssSelector(" #mainWrapper select:nth-of-type(2) > option:nth-of-type(5)");
    public By dropdownEndsWith = By.cssSelector("#mainWrapper select:nth-of-type(2) > option:nth-of-type(6)");

    // THIRD INPUT FIELD TO SEARCH CRITERIA - RULES
    public By searchCriteria = By.cssSelector("input[name='value[]']");

    //GROUP DROPDOWNS
 public By grpTitle = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div[2]/div[2]/select[1]/option[1]");
 public By grpAlbum = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div[2]/div[2]/select[1]/option[2]");
 public By grpArtist = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div[2]/div[2]/select[1]/option[3]");
 public By grpPlays = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div[2]/div[2]/select[1]/option[4]");
 public By grpLastPlayed = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div[2]/div[2]/select[1]/option[5]");
 public By grpLength = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div[2]/div[2]/select[1]/option[6]");

 // GROUP DROP DOWN CONDITIONS - RULES
 public By grpIs = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[2]/select[2]/option[1]");
 public By grpIsNot = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[2]/select[2]/option[2]");
 public By grpContains = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[2]/select[2]/option[3]");
 public By grpDoesNotContain = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[2]/select[2]/option[4]");
 public By grpBeginsWith = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[2]/select[2]/option[5]");
 public By grpEndsWith = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[2]/select[2]/option[6]");

 // THIRD INPUT FIELD TO SEARCH CRITERIA - RULES
 public By grpSearchCriteria = By.xpath("//div[@class='rule-group']/div[@class='row']//input[@name='value[]']");



 //GENERAL BUTTONS
    public By ruleBtn = By.cssSelector(".rule-group > .btn-add-rule");
    public By groupBtn = By.cssSelector(".btn-add-group");
    public By saveBtn = By.cssSelector("footer > button:nth-of-type(1)");

    //MESSAGES
    public By noMatchMessage = By.cssSelector("section#playlistWrapper .text");
    public By successMessage = By.cssSelector(".alertify-logs.right.top > .show.success");
  //Note the get.text for successMessage shows success create and success delete same path diff. get text

    public SmartPlayListPage(WebDriver givenDriver) {
        super(givenDriver);
    }
}
