/*
package apiKoel;

import apiKoel.responses.PlaySongResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import static org.hamcrest.Matchers.equalTo;
import org.testng.asserts.SoftAssert;
import static io.restassured.RestAssured.given;

public class PlaySongTests {

    private static final String BASE_URL = "https://qa.koel.app";
    private String API_TOKEN;
    private static final String INVALID_TOKEN = "invalid-token";
    private static final String SONG_ID = "3dfc5899aed1fb7edba2ce89d8d8ec45";
    private static final String NON_EXISTENT_SONG_ID = "1234567890123";

    @BeforeClass
    public void setup() {
        // Create an instance of KoelLogin and retrieve the authentication token
        KoelLogin login = new KoelLogin();
        API_TOKEN = login.koelLogin();
        Assert.assertNotNull(API_TOKEN, "Auth token should not be null");

        // Ensure RestAssured is properly configured with the base URL
        RestAssured.baseURI = BASE_URL;
    }

    // Positive Test: User should be able to play a song
    @Test
    public void playSong_Successful() {
        // Replace with an actual song ID if needed
        String songId = SONG_ID;
        int transcode = 1; // Set transcode as needed (1 for true)
        int bitrate = 128; // Set bitrate as needed
        SoftAssert softAssert = new SoftAssert();

        Response response = given()
                .header("Authorization", "Bearer " + API_TOKEN)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .get("/api/" + songId + "/play/" + transcode + "/" + bitrate + "?jwt-token=" + API_TOKEN)
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Deserialize JSON response into PlaySongResponse POJO
        PlaySongResponse playSongResponse = response.as(PlaySongResponse.class);

        // Validate fields in the PlaySongResponse POJO
        softAssert.assertNotNull(playSongResponse, "Response should not be null.");
        softAssert.assertEquals(playSongResponse.getStatusCode(), 200, "Expected status code should be 200.");
        softAssert.assertEquals(playSongResponse.getMessage(), "Song is now playing", "Expected message should confirm song is playing.");

        softAssert.assertAll();
    }

    // Test: Incorrect HTTP Method
    @Test
    public void testPlaySongIncorrectMethod() {
        SoftAssert softAssert = new SoftAssert();

        Response response = given()
                .basePath("/api/1/play/1/1")
                .header("Authorization", "Bearer " + API_TOKEN)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .post("/play/" + SONG_ID + "/?api_token=" + API_TOKEN)
                .then()
                .statusCode(405) // Method Not Allowed
                .extract()
                .response();

        softAssert.assertEquals(response.getStatusCode(), 405, "Expected status code for incorrect method is 405");
        softAssert.assertAll();
    }

    // Test: No Song ID
    @Test
    public void testPlaySongNoSongId() {
        SoftAssert softAssert = new SoftAssert();

        Response response = given()
                .basePath("/api/1/play/1/1")
                .header("Authorization", "Bearer " + API_TOKEN)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("/play/?api_token=" + API_TOKEN)
                .then()
                .statusCode(404) // Not Found
                .body("message", equalTo("Requested song doesn’t exist"))
                .extract()
                .response();

        softAssert.assertEquals(response.getStatusCode(), 404, "Expected status code for no song ID is 404");
        softAssert.assertEquals(response.jsonPath().getString("message"), "Requested song doesn’t exist", "Message should indicate song does not exist");
        softAssert.assertAll();
    }

    // Test: Wrong Song ID
    @Test
    public void testPlaySongNonExistentSongId() {
        SoftAssert softAssert = new SoftAssert();

        Response response = given()
                .basePath("/api/1/play/1/1")
                .header("Authorization", "Bearer " + API_TOKEN)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("/play/" + NON_EXISTENT_SONG_ID + "/?api_token=" + API_TOKEN)
                .then()
                .statusCode(404) // Not Found
                .body("message", equalTo("Requested song doesn’t exist"))
                .extract()
                .response();

        softAssert.assertEquals(response.getStatusCode(), 404, "Expected status code for non-existent song ID is 404");
        softAssert.assertEquals(response.jsonPath().getString("message"), "Requested song doesn’t exist", "Message should indicate song does not exist");
        softAssert.assertAll();
    }

    // Test: Long Song ID
    @Test
    public void testPlaySongLongSongId() {
        SoftAssert softAssert = new SoftAssert();
        String longSongId = "1234567890123456789012345678901234567890"; // 40 characters, for example

        Response response = given()
                .basePath("/api/1/play/1/1")
                .header("Authorization", "Bearer " + API_TOKEN)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("/play/" + longSongId + "/?api_token=" + API_TOKEN)
                .then()
                .statusCode(404) // Not Found
                .body("message", equalTo("Requested song doesn’t exist"))
                .extract()
                .response();

        softAssert.assertEquals(response.getStatusCode(), 404, "Expected status code for long song ID is 404");
        softAssert.assertEquals(response.jsonPath().getString("message"), "Requested song doesn’t exist", "Message should indicate song does not exist");
        softAssert.assertAll();
    }

    // Test: No Token
    @Test
    public void testPlaySongNoToken() {
        SoftAssert softAssert = new SoftAssert();

        Response response = given()
                .basePath("/api/1/play/1/1")
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("/play/" + SONG_ID)
                .then()
                .statusCode(401) // Unauthorized
                .body("message", equalTo("Authorization token is missing or invalid"))
                .extract()
                .response();

        softAssert.assertEquals(response.getStatusCode(), 401, "Expected status code for no token is 401");
        softAssert.assertEquals(response.jsonPath().getString("message"), "Authorization token is missing or invalid", "Message should indicate the token is missing or invalid");
        softAssert.assertAll();
    }

    // Test: Expired Token
    @Test
    public void testPlaySongExpiredToken() {
        SoftAssert softAssert = new SoftAssert();

        Response response = given()
                .basePath("/api/1/play/1/1")
                .header("Authorization", "Bearer " + INVALID_TOKEN)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("/play/" + SONG_ID + "/?api_token=" + INVALID_TOKEN)
                .then()
                .statusCode(401) // Unauthorized
                .body("message", equalTo("Authorization token is expired or invalid"))
                .extract()
                .response();

        softAssert.assertEquals(response.getStatusCode(), 401, "Expected status code for expired token is 401");
        softAssert.assertEquals(response.jsonPath().getString("message"), "Authorization token is expired or invalid", "Message should indicate the token is expired or invalid");
        softAssert.assertAll();
    }

    // Test: Long Token
    @Test
    public void testPlaySongLongToken() {
        SoftAssert softAssert = new SoftAssert();
        String longToken = "a".repeat(1000); // For example, a token with 1000 'a' characters

        Response response = given()
                .basePath("/api/1/play/1/1")
                .header("Authorization", "Bearer " + longToken)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("/play/" + SONG_ID + "/?api_token=" + longToken)
                .then()
                .statusCode(401) // Unauthorized
                .body("message", equalTo("Authorization token is invalid"))
                .extract()
                .response();

        softAssert.assertEquals(response.getStatusCode(), 401, "Expected status code for long token is 401");
        softAssert.assertEquals(response.jsonPath().getString("message"), "Authorization token is invalid", "Message should indicate the token is invalid");
        softAssert.assertAll();
    }
}

 */
package apiKoel;

import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import apiKoel.responses.PlaySongResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.given;

public class PlaySongTests {

    private static final String BASE_URL = "https://qa.koel.app";
    private String API_TOKEN;
    private static final String INVALID_TOKEN = "invalid-token";
    private static final String SONG_ID = "3dfc5899aed1fb7edba2ce89d8d8ec45";
    private static final String NON_EXISTENT_SONG_ID = "1234567890123";

    @BeforeClass
    public void setup() {
        // Create an instance of KoelLogin and retrieve the authentication token
        KoelLogin login = new KoelLogin();
        API_TOKEN = login.koelLogin();
        Assert.assertNotNull(API_TOKEN, "Auth token should not be null");

        // Ensure RestAssured is properly configured with the base URL
        RestAssured.baseURI = BASE_URL;
    }

    // Positive Test: User should be able to play a song
    @Test
    public void playSong_Successful() {
        SoftAssert softAssert = new SoftAssert();
        String songId = "3dfc5899aed1fb7edba2ce89d8d8ec45"; // Example song ID
        String transcode = "1"; // Replace with appropriate value
        String bitrate = "128"; // Replace with appropriate value

        // Construct the endpoint
        String endpoint = constructPlaySongEndpoint(songId, transcode, bitrate);

        // Log the endpoint for the TestNG report
        Reporter.log("Calling endpoint: " + endpoint, true);

        // Make the API request
        Response response = given()
                .header("Authorization", "Bearer " + API_TOKEN)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .get(endpoint)
                .then()
                .log().ifError()
                .statusCode(200) // Check that the status code is 200
                .extract()
                .response();

        // Assert the response
        PlaySongResponse playSongResponse = response.as(PlaySongResponse.class);
        softAssert.assertNotNull(playSongResponse, "Response should not be null.");
        softAssert.assertEquals(playSongResponse.getStatusCode(), 200, "Expected status code should be 200.");
        softAssert.assertEquals(playSongResponse.getMessage(), "Song is now playing", "Expected message should confirm song is playing.");

        // Ensure all soft assertions are checked
        softAssert.assertAll();
    }

    // Method to construct the endpoint
    private String constructPlaySongEndpoint(String songId, String transcode, String bitrate) {
        ////////////////////api/{song}/play/{transcode?}/{bitrate?}?jwt-token={{token}}
        return BASE_URL + "/api/" + songId + "/play/" + transcode + "/" + bitrate + "?jwt-token=" + API_TOKEN;
    }



    // Test: Incorrect HTTP Method
    @Test
    public void testPlaySongIncorrectMethod() {
        SoftAssert softAssert = new SoftAssert();

        Response response = given()
                .basePath("/api/1/play/1/1")
                .header("Authorization", "Bearer " + API_TOKEN)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .post("/play/" + SONG_ID + "/?api_token=" + API_TOKEN)
                .then()
                .statusCode(405) // Method Not Allowed
                .extract()
                .response();

        softAssert.assertEquals(response.getStatusCode(), 405, "Expected status code for incorrect method is 405");
        softAssert.assertAll();
    }

    // Test: No Song ID
    @Test
    public void testPlaySongNoSongId() {
        SoftAssert softAssert = new SoftAssert();

        Response response = given()
                .basePath("/api/1/play/1/1")
                .header("Authorization", "Bearer " + API_TOKEN)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("/play/?api_token=" + API_TOKEN)
                .then()
                .statusCode(404) // Not Found
                .body("message", equalTo("Requested song doesn’t exist"))
                .extract()
                .response();

        softAssert.assertEquals(response.getStatusCode(), 404, "Expected status code for no song ID is 404");
        softAssert.assertEquals(response.jsonPath().getString("message"), "Requested song doesn’t exist", "Message should indicate song does not exist");
        softAssert.assertAll();
    }

    // Test: Wrong Song ID
    @Test
    public void testPlaySongNonExistentSongId() {
        SoftAssert softAssert = new SoftAssert();

        Response response = given()
                .basePath("/api/1/play/1/1")
                .header("Authorization", "Bearer " + API_TOKEN)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("/play/" + NON_EXISTENT_SONG_ID + "/?api_token=" + API_TOKEN)
                .then()
                .statusCode(404) // Not Found
                .body("message", equalTo("Requested song doesn’t exist"))
                .extract()
                .response();

        softAssert.assertEquals(response.getStatusCode(), 404, "Expected status code for non-existent song ID is 404");
        softAssert.assertEquals(response.jsonPath().getString("message"), "Requested song doesn’t exist", "Message should indicate song does not exist");
        softAssert.assertAll();
    }

    // Test: Long Song ID
    @Test
    public void testPlaySongLongSongId() {
        SoftAssert softAssert = new SoftAssert();
        String longSongId = "1234567890123456789012345678901234567890"; // 40 characters, for example

        Response response = given()
                .basePath("/api/1/play/1/1")
                .header("Authorization", "Bearer " + API_TOKEN)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("/play/" + longSongId + "/?api_token=" + API_TOKEN)
                .then()
                .statusCode(404) // Not Found
                .body("message", equalTo("Requested song doesn’t exist"))
                .extract()
                .response();

        softAssert.assertEquals(response.getStatusCode(), 404, "Expected status code for long song ID is 404");
        softAssert.assertEquals(response.jsonPath().getString("message"), "Requested song doesn’t exist", "Message should indicate song does not exist");
        softAssert.assertAll();
    }

    // Test: No Token
    @Test
    public void testPlaySongNoToken() {
        SoftAssert softAssert = new SoftAssert();

        Response response = given()
                .basePath("/api/1/play/1/1")
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("/play/" + SONG_ID)
                .then()
                .statusCode(401) // Unauthorized
                .body("message", equalTo("Authorization token is missing or invalid"))
                .extract()
                .response();

        softAssert.assertEquals(response.getStatusCode(), 401, "Expected status code for no token is 401");
        softAssert.assertEquals(response.jsonPath().getString("message"), "Authorization token is missing or invalid", "Message should indicate the token is missing or invalid");
        softAssert.assertAll();
    }

    // Test: Expired Token
    @Test
    public void testPlaySongExpiredToken() {
        SoftAssert softAssert = new SoftAssert();

        Response response = given()
                .basePath("/api/1/play/1/1")
                .header("Authorization", "Bearer " + INVALID_TOKEN)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("/play/" + SONG_ID + "/?api_token=" + INVALID_TOKEN)
                .then()
                .statusCode(401) // Unauthorized
                .body("message", equalTo("Authorization token is expired or invalid"))
                .extract()
                .response();

        softAssert.assertEquals(response.getStatusCode(), 401, "Expected status code for expired token is 401");
        softAssert.assertEquals(response.jsonPath().getString("message"), "Authorization token is expired or invalid", "Message should indicate the token is expired or invalid");
        softAssert.assertAll();
    }

    // Test: Long Token
    @Test
    public void testPlaySongLongToken() {
        SoftAssert softAssert = new SoftAssert();
        String longToken = "a".repeat(1000); // For example, a token with 1000 'a' characters

        Response response = given()
                .basePath("/api/1/play/1/1")
                .header("Authorization", "Bearer " + longToken)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("/play/" + SONG_ID + "/?api_token=" + longToken)
                .then()
                .statusCode(401) // Unauthorized
                .body("message", equalTo("Authorization token is invalid"))
                .extract()
                .response();

        softAssert.assertEquals(response.getStatusCode(), 401, "Expected status code for long token is 401");
        softAssert.assertEquals(response.jsonPath().getString("message"), "Authorization token is invalid", "Message should indicate the token is invalid");
        softAssert.assertAll();
    }
}









