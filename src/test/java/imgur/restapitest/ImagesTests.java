package imgur.restapitest;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.*;
import ru.geekbrains.autotest.dto.response.GetImageInfoResponse;
import ru.geekbrains.autotest.dto.response.UpdateOrDeleteImageResponse;
import ru.geekbrains.autotest.dto.response.UploadImageResponse;

import java.io.File;

import static org.hamcrest.Matchers.*;

@Epic("Imgur API testing")
@Feature("Images")
@DisplayName("Images test suite")
@Tag("AllTests")
@Tag("ImageTests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImagesTests extends BaseTest{
    private final String PATH_TO_IMAGE = "src/test/resources/test_image.jpeg";
    static String myImageId;
    static String myImageDeleteHash;
    static String myImageTitle = "Title will be change";
    static String myImageDescription = "This is description should change";
    static String myImageLink;
    static String imageType = "image/jpeg";
    static RequestSpecification requestSpecificationWithAuthAndMultipartImage;
    static RequestSpecification requestSpecificationWithNewInfo;
    static ResponseSpecification positiveResponseSpecificationForUploadImage;
    MultiPartSpecification multiPartSpecWithFile;

    @BeforeEach
    void setUp() {
        multiPartSpecWithFile = new MultiPartSpecBuilder(new File(PATH_TO_IMAGE))
                .controlName("image")
                .build();
        requestSpecificationWithAuthAndMultipartImage = new RequestSpecBuilder()
                .addRequestSpecification(requestSpecificationWithAuth)
                .addFormParam("type", "jpeg")
                .addFormParam("title", myImageTitle)
                .addFormParam("description", myImageDescription)
                .addMultiPart(multiPartSpecWithFile)
                .build();

        requestSpecificationWithNewInfo = new RequestSpecBuilder()
                .addRequestSpecification(requestSpecificationWithAuth)
                .addFormParam("title","Yin_and_yang")
                .addFormParam("description","This is an image downloaded by Nataly")
                .build();

        positiveResponseSpecificationForUploadImage = new ResponseSpecBuilder()
                .addResponseSpecification(positiveResponseSpecification)
                .expectBody("data.id", is(matchesPattern("[\\w\\d]{7}")))
                .expectBody("data.deletehash", is(matchesPattern("[\\w\\d]{15}")))
                .expectResponseTime(lessThan(5000L))
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("Image upload test")
    @Description("That test doing sending base64 encoded image getting response body")
    @Tag("ImageUploadTest")
    void imageUploadTest() {
        UploadImageResponse.ImageInfoPostData imageInfoPostData = ImagesSteps.uploadImage().getData();
        myImageId = imageInfoPostData.getId();
        myImageDeleteHash = imageInfoPostData.getDeletehash();
        myImageLink = imageInfoPostData.getLink();
        Assertions.assertTrue(myImageLink.contains(myImageId), "Link contains image ID:" + myImageId);
    }

    @Test
    @Order(2)
    @DisplayName("Image info test")
    @Description("That test doing request and getting image info in response body")
    @Tag("ImageInfoTest")
    void imageInfoTest() {
        GetImageInfoResponse.ImageInfoGetData imageInfoGetData = ImagesSteps.getImageInfo(myImageId).getData();
        Assertions.assertEquals(myImageDescription, imageInfoGetData.getDescription(), "Description is mismatch");
        Assertions.assertEquals(myImageTitle, imageInfoGetData.getTitle(), "Title is mismatch");
        Assertions.assertEquals(imageType, imageInfoGetData.getType(), "Type is mismatch");
        Assertions.assertTrue(imageInfoGetData.getLink().contains(myImageId), "Link is not contains image ID");
    }

    @Test
    @Order(3)
    @DisplayName("Update image info test")
    @Description("That test doing sending updated image info and getting response with update status")
    @Tag("ImageUpdateTest")
    void imageUpdateTest() {
        UpdateOrDeleteImageResponse updateImageResponse = ImagesSteps.updateImage(myImageId);
        Assertions.assertTrue(updateImageResponse.getData());
    }

    @Test
    @Order(4)
    @DisplayName("Delete image info test")
    @Description("That test sending request for deletion and getting response with delete status")
    @Tag("ImageDeleteTest")
    void imageDeleteTest() {
        UpdateOrDeleteImageResponse deleteImageResponse = ImagesSteps.deleteImage(myImageId);
        Assertions.assertTrue(deleteImageResponse.getData());
    }

}
