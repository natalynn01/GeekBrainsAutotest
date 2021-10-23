package imgur.restapitest;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.*;
import ru.geekbrains.autotest.dto.response.CommonResponseWithBooleanData;
import ru.geekbrains.autotest.dto.response.CreateAlbumResponse;
import ru.geekbrains.autotest.dto.response.ErrorResponse;
import ru.geekbrains.autotest.dto.response.GetAlbumResponse;

import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

@Epic("Imgur API testing")
@Feature("Album")
@DisplayName("Album test suite")
@Tag("AlbumTests")
@Tag("AllTests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AlbumTest extends BaseTest {
    static String nonExistingImageId = "aB1cDef";
    static String myAlbumId;
    static String myAlbumDeleteHash;
    static String myAlbumTitle = "My test album";
    static String myAlbumDescription = "This albums description will be rewrite";
    static String postImageErrorText = "You must own all the image ids to add them to album %s";
    static String postRequestText = "/3/album/%s/add";
    static String getImageErrorText = "Unable to find an image with the id, %s";
    static String getRequestText = "/3/album/%s/image/%s";
    static RequestSpecification requestSpecificationForCreateAlbum;
    static RequestSpecification requestSpecificationForAddNonExistingImage;
    static RequestSpecification requestSpecificationForPutNewAlbumInfo;
    static ResponseSpecification positiveResponseSpecificationForCreateAlbum;
    static ResponseSpecification errorResponseSpecificationForPostImage;
    static ResponseSpecification errorResponseSpecificationForGetImage;

    @BeforeAll
    static void setUp() {
        requestSpecificationForCreateAlbum = new RequestSpecBuilder()
                .addRequestSpecification(requestSpecificationWithAuth)
                .addMultiPart("title", myAlbumTitle)
                .addMultiPart("description", myAlbumDescription)
                .build();

        requestSpecificationForAddNonExistingImage = new RequestSpecBuilder()
                .addRequestSpecification(requestSpecificationWithAuth)
                .addMultiPart("ids[]",nonExistingImageId)
                .build();

        requestSpecificationForPutNewAlbumInfo = new RequestSpecBuilder()
                .addRequestSpecification(requestSpecificationWithAuth)
                .addMultiPart("deletehashes", "jvnf6djkQcG7X7h")
                .addMultiPart("title", "Updated album title")
                .addMultiPart("description", "New album description")
                .build();

        positiveResponseSpecificationForCreateAlbum = new ResponseSpecBuilder()
                .addResponseSpecification(positiveResponseSpecification)
                .expectBody("data.id", is(matchesPattern("[\\w\\d]{7}")))
                .expectBody("data.deletehash", is(matchesPattern("[\\w\\d]{15}")))
                .expectResponseTime(lessThan(5000L))
                .build();

        errorResponseSpecificationForPostImage = new ResponseSpecBuilder()
                .expectBody("status", equalTo(403))
                .expectBody("success", is(false))
                .expectContentType(JSON)
                .expectStatusCode(403)
                .build();

        errorResponseSpecificationForGetImage = new ResponseSpecBuilder()
                .expectBody("status", equalTo(200))
                .expectBody("success", is(true))
                .expectContentType(JSON)
                .expectStatusCode(200)
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("Album creation test")
    @Description("That test sending request for new album creating and getting response body")
    @Tag("AlbumCreationTest")
    void albumCreationTest() {
        CreateAlbumResponse.CreateAlbumData createAlbumData = AlbumSteps.createAlbum().getData();
        myAlbumId = createAlbumData.getId();
        myAlbumDeleteHash = createAlbumData.getDeletehash();
    }

    @Test
    @Order(2)
    @DisplayName("Album info test")
    @Description("That tes getting album info and checking response fields")
    @Tag("AlbumInfoTest")
    void albumInfoTest() {
        GetAlbumResponse.GetAlbumInfoData albumInfoData = AlbumSteps.getAlbumInfo(myAlbumId).getData();
        Assertions.assertEquals(myAlbumId, albumInfoData.getId());
        Assertions.assertEquals(username, albumInfoData.getAccountUrl());
        Assertions.assertEquals("hidden", albumInfoData.getPrivacy());
    }

    @Test
    @Order(3)
    @DisplayName("Adding non existing image to album test")
    @Description("That test sending request for addition image with non existing ID")
    @Tag("AlbumAddNonExistingImage")
    void albumAdditionNonExistingImageTest() {
        ErrorResponse.ErrorData errorData = AlbumSteps.addNonExistingImage(myAlbumId).getData();
        Assertions.assertEquals(String.format(postImageErrorText, myAlbumId), errorData.getError());
        Assertions.assertEquals(String.format(postRequestText, myAlbumId), errorData.getRequest());
        Assertions.assertEquals("POST", errorData.getMethod());
    }

    @Test
    @Order(4)
    @DisplayName("Getting non existing image from album")
    @Description("That test sending request for getting non existing image info")
    @Tag("AlbumGetNonExistingImage")
    void albumGetNonExistingImageTest() {
        ErrorResponse.ErrorData errorData = AlbumSteps.getNonExistingImage(myAlbumId, nonExistingImageId).getData();
        Assertions.assertEquals(String.format(getImageErrorText, nonExistingImageId), errorData.getError());
        Assertions.assertEquals(String.format(getRequestText, myAlbumId, nonExistingImageId), errorData.getRequest());
        Assertions.assertEquals("GET", errorData.getMethod());
    }

    @Test
    @Order(5)
    @DisplayName("Put new parameters to album")
    @Description("That test sending updated album parameters and getting status info")
    @Tag("AlbumPutUpdatedParams")
    void albumPutUpdatedParamsTest() {
        CommonResponseWithBooleanData putDataResponse = AlbumSteps.putUpdatedParams(myAlbumId);
        Assertions.assertTrue(putDataResponse.getData());
    }

    @Test
    @Order(6)
    @DisplayName("Delete album")
    @Description("That test sending request for delete album and getting status info")
    @Tag("AlbumDelete")
    void albumDelete() {
        CommonResponseWithBooleanData deleteAlbumResponse = AlbumSteps.deleteAlbum(myAlbumId);
        Assertions.assertTrue(deleteAlbumResponse.getData());
    }
}
