package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SmartPlayListPage extends BasePage {

    // NAME THE PLAYLIST
    public By playlistName = By.cssSelector("input[name='name']");
    //switch (optionText2)
    public By dropdownLocatorTextTitle = By.xpath("//div[@class='rule-group']/div[2]/select[@name='model[]']");
    public By dropdownLocatorTextAlbum = By.xpath("//div[@class='rule-group']/div[3]/select[@name='model[]']"); // Second dropdown
    public By dropdownLocatorTextArtist = By.xpath("//div[@class='rule-group']/div[4]/select[@name='model[]']"); // Third dropdown
    public By dropdownLocatorTextPlays = By.xpath("//div[@class='rule-group']/div[5]/select[@name='model[]']"); // Fourth dropdown

    public By optionLocatorTextTitle =By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[2]/select[1]/option[1]");
    public By optionLocatorTextAlbum = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[3]/select[1]/option[2]");
    public By optionLocatorTextArtist = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[4]/select[1]/option[3]");
    public By optionLocatorTextPlays = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[5]/select[1]/option[4]");

    public By inputFieldLocatorTextTitle = By.xpath("//div/form//div[@class='rule-group']/div[2]//input[@name='value[]']");
    public By inputFieldLocatorTextAlbum = By.xpath("//div/form//div[@class='rule-group']/div[3]//input[@name='value[]']"); // Album input field
    public By inputFieldLocatorTextArtist = By.xpath("//div/form//div[@class='rule-group']/div[4]//input[@name='value[]']"); // Artist input field
    public By inputFieldLocatorTextPlays = By.xpath("//div/form//div[@class='rule-group']/div[5]//input[@name='value[]']"); // Plays input field



    //switch (optionText1)
    public By dropdownLocatorTitle = By.xpath("//div/form//div[@class='form-row rules']/div[1]/div[2]/select[@name='model[]']");
    public By dropdownLocatorAlbum = By.xpath("//div/form//div[@class='form-row rules']/div[1]/div[3]/select[@name='model[]']");
    public By dropdownLocatorArtist = By.xpath("//div/form//div[@class='form-row rules']/div[1]/div[4]/select[@name='model[]']");
    public By dropdownLocatorPlays = By.xpath("//div/form//div[@class='form-row rules']/div[1]/div[5]/select[@name='model[]']");

    //switch (optionText)
    public By optionLocatorTitle = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[2]/select[1]/option[1]");
    public By optionLocatorAlbum = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[3]/select[1]/option[2]");
    public By optionLocatorArtist = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[4]/select[1]/option[3]");
    public By optionLocatorPlays = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[5]/select[1]/option[4]");


    // FIRST DROP DOWN - RULES
    public By dropdownRuleTitle = By.cssSelector("select[name='model[]'] option:nth-child(1)");
    public By dropdownRuleAlbum = By.cssSelector("select[name='model[]'] option:nth-child(2)");
    public By dropdownRuleArtist = By.cssSelector("select[name='model[]'] option:nth-child(3)");
    public By dropdownRulePlays = By.cssSelector("select[name='model[]'] option:nth-child(4)");
    public By dropdownRuleLastPlayed = By.cssSelector("select[name='model[]'] option:nth-child(5)");
    public By dropdownRuleLength = By.cssSelector("select[name='model[]'] option:nth-child(6)");

    // NO DATA AVAILABLE TO TEST THE BELOW OPTIONS
    public By dropdownRuleDateAdded = By.cssSelector("select[name='model[]'] option:nth-child(7)");
    public By dropdownRuleDateModified = By.cssSelector("select[name='model[]'] option:nth-child(8)");

    // SECOND DROP DOWN CONDITIONS - RULES
    public By dropdownRuleIs = By.cssSelector("#mainWrapper select:nth-of-type(2) > option:first-of-type");
    public By dropdownRuleIsNot = By.cssSelector("#mainWrapper select:nth-of-type(2) > option:nth-of-type(2)");
    public By dropdownRuleContains = By.cssSelector("#mainWrapper select:nth-of-type(2) > option:nth-of-type(3)");
    public By dropdownRuleDoesNotContain = By.cssSelector("#mainWrapper select:nth-of-type(2) > option:nth-of-type(4)");
    public By dropdownRuleBeginsWith = By.cssSelector("#mainWrapper select:nth-of-type(2) > option:nth-of-type(5)");
    public By dropdownRuleEndsWith = By.cssSelector("#mainWrapper select:nth-of-type(2) > option:nth-of-type(6)");

    // THIRD INPUT FIELD TO SEARCH CRITERIA - RULES
    public By ruleSearchCriteria = By.cssSelector("input[name='value[]']");

    // GROUP DROPDOWNS
    public By dropdownGrpTitle = By.xpath("//select[1]/option[1]");
    public By dropdownGrpAlbum = By.xpath("//select[1]/option[2]");
    public By dropdownGrpArtist = By.xpath("//select[1]/option[3]");
    public By dropdownGrpPlays = By.xpath("//select[1]/option[4]");
    public By dropdownGrpLastPlayed = By.xpath("//select[1]/option[5]");
    public By dropdownGrpLength = By.xpath("//select[1]/option[6]");

    // GROUP DROP DOWN CONDITIONS - RULES
    public By dropdownGrpIs = By.xpath("//select[2]/option[1]");
    public By dropdownGrpIsNot = By.xpath("//select[2]/option[2]");
    public By dropdownGrpContains = By.xpath("//select[2]/option[3]");
    public By dropdownGrpDoesNotContain = By.xpath("//select[2]/option[4]");
    public By dropdownGrpBeginsWith = By.xpath("//select[2]/option[5]");
    public By dropdownGrpEndsWith = By.xpath("//select[2]/option[6]");

    // THIRD INPUT FIELD TO SEARCH CRITERIA -GROUP SECTION
    public By grpSearchCriteria = By.xpath("//div[@class='rule-group']//input[@name='value[]']");

    // GENERAL BUTTONS
    public By deleteBtn = By.xpath("//div/form//div[@class='form-row rules']/div[1]/div[@class='row']/button[@class='remove-rule']/i[@class='fa fa-times']");
    public By ruleBtn = By.cssSelector(".rule-group > .btn-add-rule");
    public By ruleBtn2 =By.xpath("//div/form//div[@class='form-row rules']/div[1]/button[@class='btn-add-rule']");
    public By groupBtn = By.cssSelector(".btn-add-group");
    public By saveBtn = By.cssSelector("footer > button:nth-of-type(1)");
    // HEADERS
    public By h1SmartResultsPage = By.xpath("//*[@id='playlistWrapper']/header/div[2]/h1");
    // MESSAGES
    public By noMatchMessage = By.cssSelector("section#playlistWrapper .text");
    public By successMessage = By.cssSelector(".alertify-logs.right.top > .show.success");
    public By noSongsMatch = By.xpath ("//section[@id='playlistWrapper']/div[@class='screen-placeholder']//div[@class='text']");
    /////////////---CONSTRUCTOR-----//////////////////
    public SmartPlayListPage(WebDriver givenDriver) {
        super(givenDriver);
    }


    ////////////----METHODS-----////////////////////////

    // ENTER PLAYLIST NAME
    public void enterPlaylistName(String enterName) {
        WebElement inputName = findElement(playlistName);
        inputName.sendKeys(enterName);
    }

    /////////////////--RULE SECTION--///////////////////////
    public WebElement getRuleDropdownTitle() { return driver.findElement(dropdownRuleTitle); }
    public WebElement getRuleDropdownAlbum() { return driver.findElement(dropdownRuleAlbum); }
    public WebElement getRuleDropdownArtist() { return driver.findElement(dropdownRuleArtist); }
    public WebElement getRuleDropdownPlays() { return driver.findElement(dropdownRulePlays); }
    public WebElement getRuleDropdownLastPlayed() { return driver.findElement(dropdownRuleLastPlayed); }
    public WebElement getRuleDropdownLengthOfSong() { return driver.findElement(dropdownRuleLength); }

    public WebElement getRuleDropdownIs() { return driver.findElement(dropdownRuleIs); }
    public WebElement getRuleDropdownIsNot() { return driver.findElement(dropdownRuleIsNot); }
    public WebElement getRuleDropdownContains() { return driver.findElement(dropdownRuleContains); }
    public WebElement getRuleDropdownDoesNotContain() { return driver.findElement(dropdownRuleDoesNotContain); }
    public WebElement getRuleDropdownBeginsWith() { return driver.findElement(dropdownRuleBeginsWith); }
    public WebElement getRuleDropdownEndsWith() { return driver.findElement(dropdownRuleEndsWith); }

    public WebElement searchRuleCriteria() { return findElement(ruleSearchCriteria); }

    public void selectRule(String ruleType) {
        // Open the dropdown
        WebElement dropdown = findElement(By.cssSelector("select[name='model[]']"));
        dropdown.click();

        // Select the appropriate option based on the rule type
        switch (ruleType.toLowerCase()) {
            case "title":
                findElement(dropdownRuleTitle).click();
                break;
            case "album":
                findElement(dropdownRuleAlbum).click();
                break;
            case "artist":
                findElement(dropdownRuleArtist).click();
                break;
            case "plays":
                findElement(dropdownRulePlays).click();
                break;
            case "last played":
                findElement(dropdownRuleLastPlayed).click();
                break;
            case "length":
                findElement(dropdownRuleLength).click();
                break;
            default:
                throw new IllegalArgumentException("Invalid rule type: " + ruleType);
        }
    }

    /////////////////--GROUP SECTION--///////////////////////
    public WebElement getGrpDropdownTitle() { return driver.findElement(dropdownGrpTitle); }
    public WebElement getGrpDropdownAlbum() { return driver.findElement(dropdownGrpAlbum); }
    public WebElement getGrpDropdownArtist() { return driver.findElement(dropdownGrpArtist); }
    public WebElement getGrpDropdownPlays() { return driver.findElement(dropdownGrpPlays); }
    public WebElement getGrpDropdownLastPlayed() { return driver.findElement(dropdownGrpLastPlayed); }
    public WebElement getGrpDropdownLengthOfSong() { return driver.findElement(dropdownGrpLength); }

    public WebElement getGrpDropdownIs() { return driver.findElement(dropdownGrpIs); }
    public WebElement getGrpDropdownIsNot() { return driver.findElement(dropdownGrpIsNot); }
    public WebElement getGrpDropdownContains() { return driver.findElement(dropdownGrpContains); }
    public WebElement getGrpDropdownDoesNotContain() { return driver.findElement(dropdownGrpDoesNotContain); }
    public WebElement getGrpDropdownBeginsWith() { return driver.findElement(dropdownGrpBeginsWith); }
    public WebElement getGrpDropdownEndsWith() { return driver.findElement(dropdownGrpEndsWith); }

    public void searchGrpCriteria(String grpSearchName) {
        WebElement inputName = findElement(grpSearchCriteria);
        inputName.sendKeys(grpSearchName);
    }

    // BUTTONS
    public void createRule() { driver.findElement(ruleBtn).click(); }
    public void createGroup() { driver.findElement(groupBtn).click(); }
    public void clickSaveButton() { driver.findElement(saveBtn).click(); }

    // VERIFICATION MESSAGES
    public String getSuccessMessageText() {
        try {
            WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            return successMsg.getText();
        } catch (TimeoutException e) {
            return "";
        }
    }

    public String getNoMatchMessageText() {
        try {
            WebElement noMatchMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(noMatchMessage));
            return noMatchMsg.getText();
        } catch (TimeoutException e) {
            return "";
        }
    }
    public String getNoSongsMatchMessageText() {
        try {
            WebElement noMatchMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(noSongsMatch));
            return noMatchMsg.getText();
        } catch (TimeoutException e) {
            return "";
        }

    }
    public String getH1ResultsText() {
        try {
            WebElement noMatchMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(h1SmartResultsPage));
            return noMatchMsg.getText();
        } catch (TimeoutException e) {
            return "";
        }
    }

}

