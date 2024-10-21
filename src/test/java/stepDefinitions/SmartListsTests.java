package stepDefinitions;
//IMPORT LIBRARIES LINKED TO GRADLE
import java.time.Duration;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;
import pages.*;
import utils.WebDriverManagerUtil;

import java.util.List;
import java.util.Map;

public class SmartListsTests {
    //INSTANCE VARIABLE DECLARATIONS
    //variables that belong to an instance of the class and hold references to objects
    private static WebDriverManagerUtil webDriverManager;
    private static LoginPage loginPage;
    private static SmartPlayListPage smartPlaylistPage;
    private static HomePage homePage;
    private static SoftAssert softAssert;

    //CUCUMBER ANNOTATIONS OF @BEFORE AND @AFTER
    @Before
    public void setUp() throws InterruptedException {
        webDriverManager = WebDriverManagerUtil.getInstance();
        loginPage = new LoginPage(webDriverManager.getDriver());
        smartPlaylistPage = new SmartPlayListPage(webDriverManager.getDriver());
        homePage = new HomePage(webDriverManager.getDriver());
        softAssert = new SoftAssert();
        Reporter.log("Step: Setup completed.", true);
    }

    @After
    public void tearDown() {
        webDriverManager.tearDown();
        Reporter.log("Step: Teardown completed.", true);
    }

    public SmartListsTests() {
        softAssert = new SoftAssert(); // Initialize SoftAssert in the constructor
    }

    @Given("the user is logged into the app")
    public void UserIsLoggedIn() {
        webDriverManager.getDriver().get("https://qa.koel.app");
        loginPage.validLogin();
        webDriverManager.getWait().until(ExpectedConditions.urlContains("/home"));
        Reporter.log("Step: I am logged in.", true);
    }

    // 1- Create Smart Playlist with One Rule
    @When("the user clicks playlist creation")
    public void theUserNavigatesToThePage() throws InterruptedException {
        webDriverManager.getDriver().get("https://qa.koel.app/");
        System.out.println("Browser opened website");
        Thread.sleep(1000);
        homePage.initiateNewPlaylistCreation();
        System.out.println("Clicked to select 'Smart Playlist' option");
    }

    @And("the user selects New Smart Playlist from menu")
    public void UserSelectsNewSmartPlaylist() throws InterruptedException {
        homePage.selectPlaylistType(true); // Assuming 'false' represents 'New Playlist'
        Thread.sleep(1000);
        System.out.println("The user is on the New Smart Playlist Form");
    }

    @And("the user sets the playlist name as {string}")
    public void theUserSetsThePlaylistNameAs(String playlistName) {
        smartPlaylistPage.enterPlaylistName(playlistName); // Use the method from the SmartPlayListPage class
    }
// Selects from the dropdown of choices
    @And("the user selects {string}")
    public void theUserSelects(String ruleType) {
        switch (ruleType.toLowerCase()) {
            case "title":
                smartPlaylistPage.getRuleDropdownTitle().click();
                break;
            case "album":
                smartPlaylistPage.getRuleDropdownAlbum().click();
                break;
            case "artist":
                smartPlaylistPage.getRuleDropdownArtist().click();
                break;
            case "plays":
                smartPlaylistPage.getRuleDropdownPlays().click();
                break;
            case "last played":
                smartPlaylistPage.getRuleDropdownLastPlayed().click();
                break;
            case "length":
                smartPlaylistPage.getRuleDropdownLengthOfSong().click();
                break;
            default:
                throw new IllegalArgumentException("Invalid rule type: " + ruleType);
        }
    }

    @And("the user inputs {string}")
    public void theUserInputs(String searchValue) {
        // Retrieve the input field for search criteria
        WebElement inputField = smartPlaylistPage.searchRuleCriteria();

        // Clear any existing text in the input field (optional)
        inputField.clear();

        // Send the new value to the input field
        inputField.sendKeys(searchValue);
    }


    @And("the user saves the Smart playlist")
    public void theUserSavesTheSmartPlaylist() throws InterruptedException {
        // Wait for the save button to be clickable
        WebDriverWait wait = new WebDriverWait(webDriverManager.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("footer > button:nth-of-type(1)")));

        // Click the save button
        smartPlaylistPage.clickSaveButton();

        // Optional: Wait for a moment to let the confirmation message appear
        Thread.sleep(2000);

        // Assert the confirmation message appears
        By confirmationMessageLocator = By.cssSelector(".alertify-logs.right.top > .show.success");
        WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationMessageLocator));

        // Assert that the confirmation message contains the expected text
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(confirmationMessage.getText().contains("Created playlist"), "Confirmation message not displayed correctly");
         // Ensure all soft asserts are executed
    }

    @Then("the Smart playlist {string} should be created successfully")
    public void theSmartPlaylistShouldBeCreatedSuccessfully(String expectedPlaylistName) {
        // Retrieve the success message from the page
        String successMessage = smartPlaylistPage.getSuccessMessageText();

        // Check if the success message contains the expected text
        boolean isCreatedSuccessfully = successMessage.contains("Created playlist") &&
                successMessage.contains(expectedPlaylistName);

        // Assert the condition using SoftAssert
        softAssert.assertTrue(isCreatedSuccessfully,
                "Expected success message to contain 'Created playlist' and playlist name '" + expectedPlaylistName + "', but got: " + successMessage);

        // Log the result for reporting purposes
        Reporter.log("Step: Verified that the Smart playlist '" + expectedPlaylistName + "' was created successfully.", true);
    }

    @Then("the results should be verified")
    public void verifyResults() {
        // Perform final assertion check
        softAssert.assertAll();
    }

    @And("the user adds multiple different rules with options and inputs")
    public void theUserAddsMultipleDifferentRules(DataTable dataTable) throws InterruptedException {
        List<Map<String, String>> optionInputList = dataTable.asMaps(String.class, String.class);

        // Define the locator for the 'Add rule' button (adjust as per your UI)
        By continueButtonLocator = By.xpath("//div/form//div[@class='form-row rules']/div[1]/button[@class='btn-add-rule']");

        // Call the method to fill the dropdowns and inputs
        fillMultipleDynamicDropdowns(webDriverManager.getDriver(), optionInputList, continueButtonLocator);
    }

    public void fillMultipleDynamicDropdowns(WebDriver driver, List<Map<String, String>> optionInputList, By continueButtonLocator) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        SoftAssert softAssert = new SoftAssert(); // Create a SoftAssert instance

        // Loop through each entry in the list of maps (dropdown option -> input value)
        for (int i = 0; i < optionInputList.size(); i++) {
            Map<String, String> entry = optionInputList.get(i);  // Get the current map
            String optionText = entry.get("Rule Options");        // Fetch the dropdown option
            String inputValue = entry.get("Input");               // Fetch the corresponding input value

            By dropdownLocator;

            // Determine the correct XPath based on the dropdown option
            switch (optionText) {
                case "Title":// 1st dropdown to include title
                    //dropdownLocator = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[2]/select[1]");
                    //
                    dropdownLocator = By.xpath("//div/form//div[@class='form-row rules']/div[1]/div[2]/select[@name='model[]']");
                    break;
                case "Album": // 2nd dropdown to include album
                    //dropdownLocator = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[3]/select[1]");
                    dropdownLocator = By.xpath("//div/form//div[@class='form-row rules']/div[1]/div[3]/select[@name='model[]']");
                    break;
                case "Artist": // 3rd dropdown to include artist
                    // dropdownLocator = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[4]/select[1]");
                    dropdownLocator = By.xpath("//div/form//div[@class='form-row rules']/div[1]/div[4]/select[@name='model[]']");
                    break;
                case "Plays": // 4th dropdown to include Plays
                    //dropdownLocator = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[5]/select[1]");
                    dropdownLocator = By.xpath("//div/form//div[@class='form-row rules']/div[1]/div[5]/select[@name='model[]']");
                    break;
                default:
                    throw new IllegalArgumentException("Invalid rule option: " + optionText);
            }

            // Wait for the dropdown to be visible and clickable, then select the option
            WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownLocator));
            dropdown.click();

            // Generate the option locator based on the current dropdown selection
            By optionLocator = null;
            switch (optionText) {
                case "Title":
                    optionLocator = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[2]/select[1]/option[1]");
                    break;
                case "Album":
                    optionLocator = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[3]/select[1]/option[2]");
                    break;
                case "Artist":
                    optionLocator = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[4]/select[1]/option[3]");
                    break;
                case "Plays":
                    optionLocator = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[5]/select[1]/option[4]");
                    break;
            }

            // Wait for the option to be visible and select it
            WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(optionLocator));
            option.click();

            // Pause for observation
            Thread.sleep(2000); // Sleep for 2 seconds

            // Retrieve the selected option's text
            String selectedOptionText = dropdown.findElement(By.xpath("option[.//text()='" + optionText + "']")).getText(); // Get the selected option's text
            softAssert.assertEquals(selectedOptionText, optionText, "Dropdown selection mismatch for: " + optionText); // Soft assert

            // Wait for the corresponding input field based on the current index
            By inputFieldLocator = By.xpath("//div[@class='rule-group']/div[" + (i + 2) + "]//input[@name='value[]']"); // Note: i + 2 to match divs
            WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(inputFieldLocator));
            inputField.clear();
            inputField.sendKeys(inputValue);

            // Pause for observation
            Thread.sleep(2000); // Sleep for 2 seconds

            // Verify that the input value has been set correctly
            String enteredValue = inputField.getAttribute("value");
            softAssert.assertEquals(enteredValue, inputValue, "Input value mismatch for: " + optionText); // Soft assert

            // Click the continue button only if this is not the last entry
            if (i < optionInputList.size() - 1) {
                WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(continueButtonLocator));
                continueButton.click();
            }
        }

    }



    @And("the user adds Group option with multiple rules options and inputs")
    public void theUserAddsGroupOptionWithMultipleRules(DataTable dataTable) throws InterruptedException {
        List<Map<String, String>> optionInputList = dataTable.asMaps(String.class, String.class);

        // Add Group button (adjust as per your UI)
        By groupButtonLocator = By.cssSelector(".btn-add-group");
        By deleteRuleBtn = By.xpath("//div/form//div[@class='form-row rules']/div[1]/div[@class='row']/button[@class='remove-rule']/i[@class='fa fa-times']");

        // Call the method to fill the dropdowns and inputs for Group
        fillGroupDynamicDropdowns(webDriverManager.getDriver(), optionInputList, groupButtonLocator, deleteRuleBtn);
    }
    public void fillGroupDynamicDropdowns(WebDriver driver, List<Map<String, String>> optionInputList, By deleteRuleBtn, By groupButtonLocator) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        SoftAssert softAssert = new SoftAssert();

        // If there's an existing rule, delete it first
        try {
            WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(deleteRuleBtn));
            deleteButton.click();
            Thread.sleep(1000);
        } catch (TimeoutException e) {
            System.out.println("No existing rule to delete, proceeding with group addition.");
        }

        // Click the group button to add the first set of rules
        WebElement groupButton = wait.until(ExpectedConditions.elementToBeClickable(groupButtonLocator));
        groupButton.click();

        // Wait for the UI to load the initial rule set
        Thread.sleep(3000); // Adjust if necessary

        // Loop through the list of rules and corresponding inputs
        for (int i = 0; i < optionInputList.size(); i++) {
            Map<String, String> entry = optionInputList.get(i);
            String optionText = entry.get("Rule Options");
            String inputValue = entry.get("Input");

            // Locators for each dropdown and corresponding option based on rule type
            By dropdownLocator;
            By optionLocator;
            By inputFieldLocator;  // To store the unique input field locator

            // Determine the correct dropdown, option, and input field locators based on the rule
            switch (optionText) {
                case "Title":////div[@class='smart-playlist-form']/div/form//div[@class='rule-group']/div[2]/select[@name='model[]']
                    dropdownLocator = By.xpath("//div[@class='rule-group']/div[2]/select[@name='model[]']"); // First dropdown
                    optionLocator = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[2]/select[1]/option[1]");
                    inputFieldLocator = By.xpath("//div/form//div[@class='rule-group']/div[2]//input[@name='value[]']"); // Title input field
                    break;
                case "Album":
                    dropdownLocator = By.xpath("//div[@class='rule-group']/div[3]/select[@name='model[]']"); // Second dropdown
                    optionLocator = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[3]/select[1]/option[2]");
                    inputFieldLocator = By.xpath("//div/form//div[@class='rule-group']/div[3]//input[@name='value[]']"); // Album input field
                    break;
                case "Artist":
                    dropdownLocator = By.xpath("//div[@class='rule-group']/div[4]/select[@name='model[]']"); // Third dropdown
                    optionLocator = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[4]/select[1]/option[3]");
                    inputFieldLocator = By.xpath("//div/form//div[@class='rule-group']/div[4]//input[@name='value[]']"); // Artist input field
                    break;
                case "Plays":
                    dropdownLocator = By.xpath("//div[@class='rule-group']/div[5]/select[@name='model[]']"); // Fourth dropdown
                    optionLocator = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[5]/select[1]/option[4]");
                    inputFieldLocator = By.xpath("//div/form//div[@class='rule-group']/div[5]//input[@name='value[]']"); // Plays input field
                    break;
                default:
                    throw new IllegalArgumentException("Invalid group rule option: " + optionText);
            }

            // Select the dropdown and the correct option
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
            dropdown.click();
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            option.click();

            // Validate the selection in the dropdown
            String selectedOptionText = dropdown.findElement(By.xpath("option[.//text()='" + optionText + "']")).getText();
            softAssert.assertEquals(selectedOptionText, optionText, "Dropdown selection mismatch for: " + optionText);

            // Handle the corresponding input field
            WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(inputFieldLocator));
            inputField.clear();
            inputField.sendKeys(inputValue);

            // Validate the input
            String enteredValue = inputField.getAttribute("value");
            softAssert.assertEquals(enteredValue, inputValue, "Input value mismatch for: " + optionText);

            // Add the next rule if not the last one
            if (i < optionInputList.size() - 1) {
                By ruleButtonLocator = By.cssSelector(".rule-group > .btn-add-rule");
                WebElement addRuleButton = wait.until(ExpectedConditions.elementToBeClickable(ruleButtonLocator));
                addRuleButton.click();
                Thread.sleep(3000); // Wait for the next rule to appear
            }
        }

    }

    // # Creating a playlist with no matching songs
    @Then("{string} should appear")
    public void noSongsMatchThePlaylistSCriteriaShouldAppear(String expectedMessage) {
        // Initialize SoftAssert
        SoftAssert softAssert = new SoftAssert();

        // Get the actual message from the page
        String actualMessage = smartPlaylistPage.getNoSongsMatchMessageText();

        // Perform the soft assertion
        softAssert.assertEquals(actualMessage, expectedMessage, "The expected message does not match the actual message.");


    }
    // Creating Mixed and Group Playlist with valid data
    @And("{string} should not appear for valid entries")
    public void noSongsMatchThePlaylistSCriteriaShouldNotAppear(String notExpectedMessage) {
        // Initialize SoftAssert
        SoftAssert softAssert = new SoftAssert();

        // Get the actual message from the page
        String actualMessage = smartPlaylistPage.getNoSongsMatchMessageText();

        // Check if the actual message matches the expected message
        if (actualMessage.equals(notExpectedMessage)) {
            // Log defect message to console
            System.out.println("Defect: Valid data used but the playlist is being created with no songs.");
        }

        // Perform the soft assertion
        softAssert.assertNotEquals(actualMessage, notExpectedMessage,
                "The expected message should not match for valid entries, but it does.");
    }


    @And("the playlist name cannot have more than {int} characters")
    public void the_playlist_name_cannot_have_more_than_characters(Integer maxCharacters) {
        // Initialize SoftAssert
        SoftAssert softAssert = new SoftAssert();

        // Retrieve the actual playlist name from the page
        String playlistName = smartPlaylistPage.getH1ResultsText();

        // Get the length of the playlist name
        int playlistNameLength = playlistName.length();

        // Print the number of characters in the playlist name
        System.out.println("The playlist name has " + playlistNameLength + " characters.");

        // Check if the playlist name exceeds the maximum allowed characters
        if (playlistNameLength > maxCharacters) {
            // Print a defect message if it exceeds the limit
            System.out.println("Defect: The playlist name has " + playlistNameLength + " characters, which exceeds the allowed limit of " + maxCharacters + " characters, and was created in error.");

            // Assert failure if the playlist name is too long
            softAssert.fail("Playlist name exceeds " + maxCharacters + " characters: " + playlistName);
        } else {
            // Print success message if the playlist name is within the limit
            System.out.println("The playlist name is within the allowed limit of " + maxCharacters + " characters.");

            // Assert success if the playlist name length is valid
            softAssert.assertTrue(playlistNameLength <= maxCharacters, "Playlist name has " + playlistNameLength + " characters, which is within the allowed limit.");
        }

    }
}
