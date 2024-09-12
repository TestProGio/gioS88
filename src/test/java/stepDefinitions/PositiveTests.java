package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.HomePage;
import pages.LoginPage;

import java.time.Duration;
public class PositiveTests {


WebDriver driver;

    WebDriverWait wait;

    @Before
    public void loginTest() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Thread.sleep(2000);
    }

    @And("I open Koel Login Page")
    public void iOpenKoelLoginPage() throws InterruptedException {
        driver.get("https://qa.koel.app/");
        System.out.println("Browser opened website");
        Thread.sleep(2000);
    }


    @When("I enter email {string}")
    public void iEnterEmail(String email) throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.provideEmail(email);
        System.out.println("Email Entered");
        Thread.sleep(2000);
    }

    @And("I enter password {string}")
    public void iEnterPassword(String password) throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.providePassword(password);
        System.out.println("Password Entered");
        Thread.sleep(2000);
    }

    @And("I click submit")
    public void iClickSubmit() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickSubmit();
        System.out.println("Clicked Submit Button");
        Thread.sleep(2000);
    }

    @Then("I should be logged in")
    public void iShouldBeLoggedIn() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        homePage.getUserAvatar();
        Assert.assertTrue(homePage.getUserAvatar().isDisplayed());
        System.out.println("We are now logged in");
        Thread.sleep(2000);
    }

    @After
    public void closeBrowser(){
        driver.quit();
    }

}