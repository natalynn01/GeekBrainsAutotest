package imgur.restapitest;

import io.qameta.allure.Step;
import ru.geekbrains.autotest.dto.response.GetImageInfoResponse;
import ru.geekbrains.autotest.dto.response.UpdateOrDeleteImageResponse;
import ru.geekbrains.autotest.dto.response.UploadImageResponse;

import static imgur.restapitest.ImagesTests.*;
import static io.restassured.RestAssured.given;
import static ru.geekbrains.autotest.utils.Endpoints.IMAGE;
import static ru.geekbrains.autotest.utils.Endpoints.UPLOAD_IMAGE;

public class ImagesSteps {

    @Step("POST Image upload and get response")
    public static UploadImageResponse uploadImage() {
        return given()
                .spec(requestSpecificationWithAuthAndMultipartImage)
                .log().all()
                .expect()
                .spec(positiveResponseSpecificationForUploadImage)
                .when()
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .then()
                .extract()
                .body().as(UploadImageResponse.class);
    }

    @Step("GET Image info with ID={0}")
    public static GetImageInfoResponse getImageInfo(String myImageId) {
        return given()
                .spec(requestSpecificationWithAuth)
                .log().all()
                .expect()
                .spec(positiveResponseSpecification)
                .when()
                .get(IMAGE, myImageId)
                .prettyPeek()
                .then()
                .extract()
                .body().as(GetImageInfoResponse.class);
    }

    @Step("POST Update image with ID={0} and get update status response")
    public static UpdateOrDeleteImageResponse updateImage(String myImageId) {
        return given()
                .spec(requestSpecificationWithNewInfo)
                .log().all()
                .expect()
                .spec(positiveResponseSpecification)
                .when()
                .post(IMAGE, myImageId)
                .prettyPeek()
                .then()
                .extract()
                .body().as(UpdateOrDeleteImageResponse.class);
    }

    @Step("DELETE image with ID={0} and get delete status response")
    public static UpdateOrDeleteImageResponse deleteImage(String myImageId) {
        return given()
                .spec(requestSpecificationWithNewInfo)
                .log().all()
                .expect()
                .spec(positiveResponseSpecification)
                .when()
                .delete(IMAGE, myImageId)
                .prettyPeek()
                .then()
                .extract()
                .body().as(UpdateOrDeleteImageResponse.class);
    }
}
