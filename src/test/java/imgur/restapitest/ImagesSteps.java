package imgur.restapitest;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ImagesSteps {

    @Step("POST Image upload on path {0} and get response")
    public static Response uploadImage(String path, Map<String, String> headers, String encodedImage) {
        return given()
                .headers(headers)
                .multiPart("image", encodedImage)
                .multiPart("type", "base64")
                .multiPart("title", "Title will be change")
                .multiPart("description", "This is description should change")
                .log().all()
                .expect()
                .body("success", is(true))
                .body("data.id", is(matchesPattern("[\\w\\d]{7}")))
                .body("data.deletehash", is(matchesPattern("[\\w\\d]{15}")))
                .when()
                .post(path)
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    @Step("GET Image info with ID:{0}")
    public static Response getImageInfo(String myImageId, Map<String, String> headers, String myImageDescription,
                                        String myImageTitle, String imageType) {
        return given()
                .headers(headers)
                .log().all()
                .expect()
                .body("data.description", is(myImageDescription))
                .body("data.title", is(myImageTitle))
                .body("data.type", is(imageType))
                .when()
                .get("https://api.imgur.com/3/image/{myImageId}", myImageId)
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    @Step("POST Update image with ID:{0} and get update status response")
    public static void updateImage(String myImageId, Map<String, String> headers, String newTitle,
                                   String newDescription) {
        given()
                .headers(headers)
                .multiPart("title", newTitle)
                .multiPart("description", newDescription)
                .log().all()
                .expect()
                .body("data", is(true))
                .body("success", is(true))
                .body("status", is(200))
                .when()
                .post("https://api.imgur.com/3/image/{myImageId}", myImageId)
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Step("DELETE image with ID:{0} and get delete status response")
    public static void deleteImage(String myImageId, Map<String, String> headers) {
        given()
                .headers(headers)
                .log().all()
                .expect()
                .body("data", is(true))
                .body("success", is(true))
                .body("status", is(200))
                .when()
                .delete("https://api.imgur.com/3/image/{myImageId}", myImageId)
                .prettyPeek()
                .then()
                .statusCode(200);
    }
}
