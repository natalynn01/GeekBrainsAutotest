package imgur.restapitest;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;

public class AlbumSteps {

    @Step("POST Album create and get response")
    public static Response createAlbum(Map<String, String> headers, String myAlbumTitle, String myAlbumDescription) {
        return given()
                .headers(headers)
                .multiPart("title", myAlbumTitle)
                .multiPart("description", myAlbumDescription)
                .log().all()
                .expect()
                .body("success", is(true))
                .body("status", is(200))
                .body("data.id", is(matchesPattern("[\\w\\d]{7}")))
                .body("data.deletehash", is(matchesPattern("[\\w\\d]{15}")))
                .when()
                .post("https://api.imgur.com/3/album")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    @Step("GET Info album with ID: \"{1}\" and check response fields")
    public static void getAlbumInfo(Map<String, String> headers, String myAlbumId, String username) {
        given()
                .headers(headers)
                .log().all()
                .expect()
                .body("data.id", is(myAlbumId))
                .body("data.account_url", is(username))
                .body("data.privacy", is("hidden"))
                .when()
                .get("https://api.imgur.com/3/album/{myAlbumId}", myAlbumId)
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Step("POST Add to album with ID: \"{1}\" inexistent image")
    public static void addInexistingImage(Map<String, String> headers, String myAlbumId) {
        given()
                .headers(headers)
                .multiPart("ids[]","inexistentId")
                .log().all()
                .expect()
                .body("data.error", is("You must own all the image ids to add them to album " + myAlbumId))
                .body("data.request", is("/3/album/" + myAlbumId + "/add"))
                .body("data.method", is("POST"))
                .body("success", is(false))
                .body("status", is(403))
                .when()
                .post("https://api.imgur.com/3/album/{myAlbumId}/add", myAlbumId)
                .prettyPeek()
                .then()
                .statusCode(403);
    }

    @Step("GET From album with ID: \"{1}\" inexisted image")
    public static void getInexistingImage(Map<String, String> headers, String myAlbumId) {
        given()
                .headers(headers)
                .log().all()
                .expect()
                .body("data.error", is("Unable to find an image with the id, inexistedId"))
                .body("data.request", is("/3/album/" + myAlbumId + "/image/inexistedId"))
                .body("data.method", is("GET"))
                .body("success", is(true))
                .body("status", is(200))
                .when()
                .get("https://api.imgur.com/3/album/{myAlbumId}/image/inexistedId", myAlbumId)
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Step("PUT To album with ID: \"{1}\" updated parameters")
    public static void putUpdatedParams(Map<String, String> headers, String myAlbumId) {
        given()
                .headers(headers)
                .log().all()
                .expect()
                .body("data", is(true))
                .body("success", is(true))
                .body("status", is(200))
                .when()
                .put("https://api.imgur.com/3/album/{myAlbumId}", myAlbumId)
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Step("DELETE Album with ID: \"{1}\" ")
    public static void deleteAlbum(Map<String, String> headers, String myAlbumId) {
        given()
                .headers(headers)
                .log().all()
                .expect()
                .body("data", is(true))
                .body("success", is(true))
                .body("status", is(200))
                .when()
                .delete("https://api.imgur.com/3/album/{myAlbumId}", myAlbumId)
                .prettyPeek()
                .then()
                .statusCode(200);
    }
}
