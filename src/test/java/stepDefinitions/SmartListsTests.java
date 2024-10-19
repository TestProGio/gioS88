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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;
import pages.*;
import utils.WebDriverManagerUtil;

import java.time.Duration;
import java.util.Arrays;
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
        smartPlaylistPage= new SmartPlayListPage(webDriverManager.getDriver());
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
    public void UserSelectsNewSmartPlaylist() throws InterruptedException{
        homePage.selectPlaylistType(true); // Assuming 'false' represents 'New Playlist'
        Thread.sleep(1000);
        System.out.println("The user is on the New Smart Playlist Form");
}
    @And("the user sets the playlist name as {string}")
    public void theUserSetsThePlaylistNameAs(String playlistName) {
        smartPlaylistPage.enterPlaylistName(playlistName); // Use the method from the SmartPlayListPage class
    }

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
        softAssert.assertAll(); // Ensure all soft asserts are executed
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

//Scenario Outline: Create Smart Playlist with Multiple Rules
/*
@And("the user adds multiple different rules with options and inputs")

public void theUserAddsMultipleDifferentRules() throws InterruptedException {
    // Hardcoded values for dropdown options and inputs
    List<Map<String, String>> optionInputList = Arrays.asList(
            Map.of("Rule Options", "Title", "Input", "M33 Project - Emotional Soundtrack"),
            Map.of("Rule Options", "Album", "Input", "Midnight in Mississippi"),
            Map.of("Rule Options", "Artist", "Input", "Lobo Loco"),
            Map.of("Rule Options", "Plays", "Input", "5"),
            Map.of("Rule Options", "Title", "Input", "Epic Song")
    );

    // Proceed with filling the dropdowns and inputs
    fillMultipleDynamicDropdowns(webDriverManager.getDriver(), optionInputList, By.cssSelector(".rule-group > .btn-add-rule"));
}

 */
@And("the user adds multiple different rules with options and inputs")
public void theUserAddsMultipleDifferentRules(DataTable dataTable) throws InterruptedException {
    // Convert the Gherkin table into a list of maps (key: Rule Options, value: Input)
    List<Map<String, String>> optionInputList = dataTable.asMaps(String.class, String.class);

    // Proceed with filling the dropdowns and inputs
    fillMultipleDynamicDropdowns(webDriverManager.getDriver(), optionInputList, By.cssSelector(".rule-group > .btn-add-rule"));
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
                case "Title":
                    dropdownLocator = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[2]/select[1]");
                    break;
                case "Album":
                    dropdownLocator = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[3]/select[1]");
                    break;
                case "Artist":
                    dropdownLocator = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[4]/select[1]");
                    break;
                case "Plays":
                    dropdownLocator = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[5]/select[1]");
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

        // Assert all SoftAssert validations
        softAssert.assertAll();
    }

/*
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
                case "Title":
                    dropdownLocator = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[2]/select[1]");
                    break;
                case "Album":
                    dropdownLocator = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[3]/select[1]");
                    break;
                case "Artist":
                    dropdownLocator = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[4]/select[1]");
                    break;
                case "Plays":
                    dropdownLocator = By.xpath("//*[@id='mainWrapper']/div/div/div/form/div/div[2]/div/div[5]/select[1]");
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

        // Call the softAssert to verify all assertions
        //softAssert.assertAll();


    }

 */

}
