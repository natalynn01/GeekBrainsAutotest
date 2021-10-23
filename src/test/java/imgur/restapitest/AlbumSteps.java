package imgur.restapitest;

import io.qameta.allure.Step;
import ru.geekbrains.autotest.dto.response.CommonResponseWithBooleanData;
import ru.geekbrains.autotest.dto.response.CreateAlbumResponse;
import ru.geekbrains.autotest.dto.response.ErrorResponse;
import ru.geekbrains.autotest.dto.response.GetAlbumResponse;

import static imgur.restapitest.AlbumTest.*;
import static imgur.restapitest.BaseTest.positiveResponseSpecification;
import static imgur.restapitest.BaseTest.requestSpecificationWithAuth;
import static io.restassured.RestAssured.given;
import static ru.geekbrains.autotest.utils.Endpoints.*;

public class AlbumSteps {

    @Step("POST Album create and get response")
    public static CreateAlbumResponse createAlbum() {
        return given()
                .spec(requestSpecificationForCreateAlbum)
                .log().all()
                .expect()
                .spec(positiveResponseSpecificationForCreateAlbum)
                .when()
                .post(ALBUM)
                .prettyPeek()
                .then()
                .extract()
                .body().as(CreateAlbumResponse.class);
    }

    @Step("GET Info album with ID: \"{0}\" and check response fields")
    public static GetAlbumResponse getAlbumInfo(String myAlbumId) {
        return given()
                .spec(requestSpecificationWithAuth)
                .log().all()
                .expect()
                .spec(positiveResponseSpecification)
                .when()
                .get(ALBUM_INFO, myAlbumId)
                .prettyPeek()
                .then()
                .extract()
                .body().as(GetAlbumResponse.class);
    }

    @Step("POST Add to album with ID: \"{0}\" non existing image")
    public static ErrorResponse addNonExistingImage(String myAlbumId) {
        return given()
                .spec(requestSpecificationForAddNonExistingImage)
                .log().all()
                .expect()
                .spec(errorResponseSpecificationForPostImage)
                .when()
                .post(ALBUM_ADD_NEW_IMAGE, myAlbumId)
                .prettyPeek()
                .then()
                .extract()
                .body().as(ErrorResponse.class);
    }

    @Step("GET From album with ID: \"{0}\" non existing image with ID: \"{1}\"")
    public static ErrorResponse getNonExistingImage(String myAlbumId, String imageId) {
        return given()
                .spec(requestSpecificationWithAuth)
                .log().all()
                .expect()
                .spec(errorResponseSpecificationForGetImage)
                .when()
                .get(IMAGE_FROM_ALBUM, myAlbumId, imageId)
                .prettyPeek()
                .then()
                .extract()
                .body().as(ErrorResponse.class);
    }

    @Step("PUT To album with ID: \"{0}\" updated parameters")
    public static CommonResponseWithBooleanData putUpdatedParams(String myAlbumId) {
        return given()
                .spec(requestSpecificationForPutNewAlbumInfo)
                .log().all()
                .expect()
                .spec(positiveResponseSpecification)
                .when()
                .put(ALBUM_INFO, myAlbumId)
                .prettyPeek()
                .then()
                .extract()
                .body().as(CommonResponseWithBooleanData.class);
    }

    @Step("DELETE Album with ID: \"{0}\" ")
    public static CommonResponseWithBooleanData deleteAlbum(String myAlbumId) {
        return given()
                .spec(requestSpecificationWithAuth)
                .log().all()
                .expect()
                .spec(positiveResponseSpecification)
                .when()
                .delete(ALBUM_INFO, myAlbumId)
                .prettyPeek()
                .then()
                .extract()
                .body().as(CommonResponseWithBooleanData.class);
    }
}
