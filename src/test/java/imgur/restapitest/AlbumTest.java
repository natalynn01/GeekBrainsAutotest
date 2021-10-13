package imgur.restapitest;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.Map;

@Epic("Imgur API testing")
@Feature("Album")
@DisplayName("Album test suite")
@Tag("AlbumTests")
@Tag("AllTests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AlbumTest {
    static Map<String, String> headers;
    static String token = System.getProperty("token");
    static String username = System.getProperty("username");
    static String myAlbumId;
    static String myAlbumDeleteHash;
    static String myAlbumTitle = "My test album";
    static String myAlbumDescription = "This albums description will be rewrite";

    @BeforeAll
    static void setUp() {
        headers = Map.of(
                "Authorization", "Bearer " + token,
                "Accept-Encoding", "gzip, deflate, br",
                "Host", "api.imgur.com",
                "Connection", "keep-alive");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.filters(new AllureRestAssured());
    }

    @Test
    @Order(1)
    @DisplayName("Album creation test")
    @Description("That test sending request for new album creating and getting response body")
    @Tag("AlbumCreationTest")
    void albumCreationTest() {
        Response response = AlbumSteps.createAlbum(headers, myAlbumTitle, myAlbumDescription);
        myAlbumId = response.body().jsonPath().getString("data.id");
        myAlbumDeleteHash = response.body().jsonPath().getString("data.deletehash");
    }

    @Test
    @Order(2)
    @DisplayName("Album info test")
    @Description("That tes getting album info and checking response fields")
    @Tag("AlbumInfoTest")
    void albumInfoTest() {
        AlbumSteps.getAlbumInfo(headers, myAlbumId, username);
    }

    @Test
    @Order(3)
    @DisplayName("Adding inexisten image to album test")
    @Description("That test sending request for addition image with inexited ID")
    @Tag("AlbumAddInexistedImage")
    void albumAdditionInexistedImageTest() {
        AlbumSteps.addInexistingImage(headers, myAlbumId);
    }

    @Test
    @Order(4)
    @DisplayName("Getting inexisting image from album")
    @Description("That test sending request for getting inexisted image info")
    @Tag("AlbumGetInexistedImage")
    void albumGetInexistedImageTest() {
        AlbumSteps.getInexistingImage(headers, myAlbumId);
    }

    @Test
    @Order(5)
    @DisplayName("Put new parameters to album")
    @Description("That test sending updated album parameters and getting status info")
    @Tag("AlbumPutUpdatedParams")
    void albumPutUpdatedParamsTest() {
        AlbumSteps.putUpdatedParams(headers, myAlbumId);
    }

    @Test
    @Order(6)
    @DisplayName("Delete album")
    @Description("That test sending request for delete album and getting status info")
    @Tag("AlbumDelete")
    void albumDelete() {
        AlbumSteps.deleteAlbum(headers, myAlbumId);
    }
}
