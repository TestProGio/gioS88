package apiKoel;

import apiKoel.requests.SongRequest;
import apiKoel.responses.SongResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UnlikeSongTest {

    // Base URL of the API
    private static final String BASE_URL = System.getenv("KOEL_BASE_URL") != null ?
            System.getenv("KOEL_BASE_URL") :
            "https://qa.koel.app"; // Default URL

    private String authToken;

    @BeforeClass
    public void setup() {
        // Create an instance of KoelLogin (assuming koelLogin method returns auth token)
        KoelLogin login = new KoelLogin();
        authToken = login.koelLogin();
        Assert.assertNotNull(authToken, "Auth token should not be null");

        // Set RestAssured base URI globally
        RestAssured.baseURI = BASE_URL;
    }

    // Test method using POJOs or Plain Old Java Objects
    @Test
    public void likeAndUnlikeSongTestWithPOJO() {
        // Create request body using POJO
        String songId = "3dfc5899aed1fb7edba2ce89d8d8ec45";
        SongRequest songRequest = new SongRequest(songId);

        // Perform the like action
        Response likeResponse = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + authToken)
                .body(songRequest)
                .post("/api/interaction/like");

        likeResponse.print();
        SongResponse songResponse = likeResponse.as(SongResponse.class);

        // Verifications - Like action
        Assert.assertEquals(likeResponse.getStatusCode(), 200, "Status code should be 200, but it's not");
        Assert.assertTrue(songResponse.isLiked(), "The song should be marked as liked");

        // Perform the unlike action
        Response unlikeResponse = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + authToken)
                .body(songRequest)
                .post("/api/interaction/like");

        unlikeResponse.print();
        songResponse = unlikeResponse.as(SongResponse.class);

        // Verifications - Unlike action
        Assert.assertEquals(unlikeResponse.getStatusCode(), 200, "Status code should be 200, but it's not");
        Assert.assertFalse(songResponse.isLiked(), "The song should not be marked as liked after unliking");
    }
}
