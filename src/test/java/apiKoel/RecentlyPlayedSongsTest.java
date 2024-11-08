package apiKoel;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static io.restassured.RestAssured.given;

public class RecentlyPlayedSongsTest {

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
        RestAssured.basePath = "/api/interaction";
    }

    @Test
    public void testGetRecentlyPlayedSongs() {
        SoftAssert softAssert = new SoftAssert();

        int count = 10; // Set the number of songs to be returned, if required.

        // Perform GET request
        Response response = given()
                .header("Authorization", "Bearer " + API_TOKEN)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .queryParam("count", count)
                .when()
                .get("/recently-played/" + count)
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Log the JSON response for debugging
        System.out.println("Response Status Code: " + response.statusCode());
        System.out.println("Response Body: " + response.asString());

        // Deserialize response directly as List<String>
        List<String> recentlyPlayedSongs = response.jsonPath().getList("$", String.class);

        // Soft assertions
        softAssert.assertNotNull(recentlyPlayedSongs, "Response song list should not be null");
        if (recentlyPlayedSongs.isEmpty()) {
            softAssert.assertTrue(recentlyPlayedSongs.isEmpty(), "Expected empty list when no songs are played recently");
        } else {
            softAssert.assertTrue(recentlyPlayedSongs.size() <= count,
                    "The number of songs should not exceed the requested count");
            softAssert.assertTrue(recentlyPlayedSongs.stream().allMatch(id -> id.matches("[a-f0-9]{32}")),
                    "All song IDs should be valid 32-character alphanumeric strings");
        }

        // Assert all soft assertions
        softAssert.assertAll();
    }
}
