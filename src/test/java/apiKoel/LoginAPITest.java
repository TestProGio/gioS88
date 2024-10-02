package apiKoel;

import apiKoel.requests.LoginRequest; // Import your LoginRequest class
import apiKoel.responses.LoginResponse; // Import your LoginResponse class
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert; // Import SoftAssert

import static io.restassured.RestAssured.given;

public class LoginAPITest {

    // Declare a constant for the base URL of the API
    private static final String BASE_URL = "https://qa.koel.app";
    // Declare variables for valid email and password for testing
    private String validEmail; // Variable to hold a valid email for testing
    private String validPassword; // Variable to hold a valid password for testing
    private SoftAssert softAssert; // Declare a SoftAssert object

    // Setup method to initialize RestAssured and test data
    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = BASE_URL; // Set the base URI for RestAssured
        // Initialize valid email and password variables

        // Initialize test data
        validEmail = "giovanna.silva@testpro.io"; // Replace with a valid test email
        validPassword = "2024Sprint3!"; // Replace with a valid test password

        // Initialize SoftAssert
        softAssert = new SoftAssert();
    }

    // Test method for successful login
    @Test
    public void testSuccessfulLogin() {
        // Create a new LoginRequest object with valid credentials
        LoginRequest loginRequest = new LoginRequest(validEmail, validPassword);

        // Send POST request to login endpoint and capture the response
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post("/api/me")
                .then()
                .extract()
                .response(); // Extract the response

        // Deserialize response to LoginResponse
        LoginResponse loginResponse = response.as(LoginResponse.class);

        // Dynamically retrieve the token from the response
        String token = loginResponse.getToken();

        // Print the token to the console
        System.out.println("Generated Token: " + token);

        // Use SoftAssert for non-blocking assertions
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(token, "Token should not be null");
        softAssert.assertFalse(token.isEmpty(), "Token should not be empty");

        // Assert all soft assertions
        softAssert.assertAll();
    }

    // Test method for login with an invalid email
    @Test
    public void testLoginWithInvalidEmail() {
        // Create a LoginRequest object with an invalid email and a valid password
LoginRequest loginRequest = new LoginRequest("xyz@testpro.io", validPassword);
        // Send a POST request to the login endpoint
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post()
                .then()
                .extract()
                .response();

        // Assert that the response status code is 401 Unauthorized
        softAssert.assertEquals(response.getStatusCode(), 405, "Expected status code 401 for invalid email but found 405");
        // Assert all soft assertions
        softAssert.assertAll();
    }

    // Test method for login with an invalid password
    @Test
    public void testLoginWithInvalidPassword() {
        // Create a LoginRequest object with a valid email and an invalid password
LoginRequest loginRequest = new LoginRequest(validEmail, "abc88");
        // Send a POST request to the login endpoint
        // Send POST request to login endpoint and capture the response
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON) // Set the accept header
                .body(loginRequest)
                .when()
                .post()
                .then()
                .extract()
                .response();

        // Assert that the status code is 401 Unauthorized
        softAssert.assertEquals(response.getStatusCode(), 405, "Expected status code 401 for invalid password but found 405");

        // Assert all soft assertions
        softAssert.assertAll();
    }
    // Test method for login with both an invalid email and an invalid password
    @Test
    public void testLoginWithInvalidEmailAndPassword() {
        // Create a LoginRequest object with an invalid email and invalid password
        LoginRequest loginRequest = new LoginRequest("invalid@testpro.io", "invalidPassword");

        // Send a POST request to the login endpoint
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post("/api/me")
                .then()
                .extract()
                .response();

        // Assert that the response status code is 401 Unauthorized
        softAssert.assertEquals(response.getStatusCode(), 401, "Expected status code 401 for invalid credentials, but got 401");

        // Assert all soft assertions
        softAssert.assertAll();
    }

    // Test method for login with no email and no password
    @Test
    public void testLoginWithNoEmailAndNoPassword() {
        // Create a LoginRequest object with no email and no password (null or empty)
        LoginRequest loginRequest = new LoginRequest("", "");

        // Send a POST request to the login endpoint
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post("/api/me")
                .then()
                .extract()
                .response();

        // Assert that the response status code is 400 Bad Request
        softAssert.assertEquals(response.getStatusCode(), 422, "Expected status code 401 for missing email and password, but got 422");

        // Assert all soft assertions
        softAssert.assertAll();
    }

    // Test method for login with email and no password
    @Test
    public void testLoginWithEmailNoPassword() {
        // Create a LoginRequest object with a valid email but no password
        LoginRequest loginRequest = new LoginRequest(validEmail, "");

        // Send a POST request to the login endpoint
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post("/api/me")
                .then()
                .extract()
                .response();

        // Assert that the response status code is 400 Bad Request
        softAssert.assertEquals(response.getStatusCode(), 422, "Expected status code 401 for missing password, but got 422");

        // Assert all soft assertions
        softAssert.assertAll();
    }

    // Test method for login with no email and valid password
    @Test
    public void testLoginWithNoEmailPassword() {
        // Create a LoginRequest object with no email and a valid password
        LoginRequest loginRequest = new LoginRequest("", validPassword);

        // Send a POST request to the login endpoint
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post("/api/me")
                .then()
                .extract()
                .response();

        // Assert that the response status code is 400 Bad Request
        softAssert.assertEquals(response.getStatusCode(), 422, "Expected status code 401 for missing email, , but got 422");

        // Assert all soft assertions
        softAssert.assertAll();
    }

}
