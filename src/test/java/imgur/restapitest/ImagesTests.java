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
@Feature("Images")
@DisplayName("Images test suite")
@Tag("AllTests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImagesTests {
    static Map<String, String> headers;
    static String token = System.getProperty("token");
    static String encodedImage = PropertiesProvider
            .getProperties("src/test/resources/test_data.properties").getProperty("encodedImage");
    static String myImageId;
    static String myImageDeleteHash;
    static String myImageTitle = "Title will be change";
    static String myImageDescription = "This is description should change";
    static String myImageLink;
    static String imageType = "image/jpeg";

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
    @DisplayName("Image upload test")
    @Description("That test doing sending base64 encoded image getting response body")
    @Tag("ImageUploadTest")
    void imageUploadTest() {
        Response response = ImagesSteps.uploadImage("https://api.imgur.com/3/upload", headers, encodedImage);
        myImageId = response.body().jsonPath().getString("data.id");
        myImageDeleteHash = response.body().jsonPath().getString("data.deletehash");
        myImageLink = response.body().jsonPath().getString("data.link");
        Assertions.assertTrue(myImageLink.contains(myImageId), "Link contains image ID:" + myImageId);
        Assertions.assertTrue(response.getTime() < 5000, "Response time is less than 5000ms");
    }

    @Test
    @Order(2)
    @DisplayName("Image info test")
    @Description("That test doing request and getting image info in response body")
    @Tag("ImageInfoTest")
    void imageInfoTest() {
        Response response = ImagesSteps.getImageInfo(myImageId, headers, myImageDescription, myImageTitle, imageType);
        Assertions.assertTrue(response.body().jsonPath().getString("data.link")
                .contains(myImageId), "Link is not contains image ID");
    }

    @Test
    @Order(3)
    @DisplayName("Update image info test")
    @Description("That test doing sending updated image info and getting response with update status")
    @Tag("ImageUpdateTest")
    void imageUpdateTest() {
        ImagesSteps.updateImage(myImageId, headers,
                "Yin_and_yang","This is an image downloaded by Nataly");
    }

    @Test
    @Order(4)
    @DisplayName("Delete image info test")
    @Description("That test sending request for deletion and getting response with delete status")
    @Tag("ImageDeleteTest")
    void imageDeleteTest() {
        ImagesSteps.deleteImage(myImageId, headers);
    }

}
