package apiKoel;
import apiKoel.responses.IncreasePlayCountResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;

public class IncreasePlayCountTest {

    private String API_TOKEN;
    private static final String BASE_URL = "https://qa.koel.app";

    @BeforeClass
    public void setup() {
        // Create an instance of KoelLogin and retrieve the authentication token
        KoelLogin login = new KoelLogin();
        API_TOKEN = login.koelLogin();
        Assert.assertNotNull(API_TOKEN, "Auth token should not be null");

        // Ensure RestAssured is properly configured with the base URL
        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "/api/interaction/play";
    }

    @Test
    public void testIncreasePlayCountSuccess() {
        SoftAssert softAssert = new SoftAssert();
        String songId = "0b794968a26cd03bb533762affc8c0ca"; // Example song ID

        // Prepare the payload
        String payload = "{\"song\":\"" + songId + "\"}";

        // Perform POST request to increase play count
        Response response = given()
                .header("Authorization", "Bearer " + API_TOKEN)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Log the response for debugging
        System.out.println("Response Status Code: " + response.statusCode());
        System.out.println("Response Body: " + response.asString());

        // Deserialize response to POJO
        IncreasePlayCountResponse playCountResponse = response.as(IncreasePlayCountResponse.class);

        // Assertions
        softAssert.assertNotNull(playCountResponse.getSongId(), "Song ID should not be null");
        softAssert.assertEquals(playCountResponse.getSongId(), songId, "The song ID should match the requested song ID");
        softAssert.assertTrue(playCountResponse.getPlayCount() > 0, "Play count should be greater than 0");

        // Assert all soft assertions
        softAssert.assertAll();
    }

    @Test
    public void testIncreasePlayCountNotFound() {
        SoftAssert softAssert = new SoftAssert();
        String invalidSongId = "12345678"; // Example invalid song ID

        // Prepare the payload
        String payload = "{\"song\":\"" + invalidSongId + "\"}";

        // Perform POST request to increase play count
        Response response = given()
                .header("Authorization", "Bearer " + API_TOKEN)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post()
                .then()
                .statusCode(404)
                .extract()
                .response();

        // Log the response for debugging
        System.out.println("Response Status Code: " + response.statusCode());
        System.out.println("Response Body: " + response.asString());

        // Verify error message in response
        String errorMessage = response.jsonPath().getString("message");
        softAssert.assertEquals(errorMessage, "Requested song doesn’t exist", "Error message should match");

        // Assert all soft assertions
        softAssert.assertAll();
    }
}
