package api.tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static helper.Payload.deletePayload;
import static io.restassured.RestAssured.given;

/* This example takes static payload and let's assume we get response from our developer as JSON file. */

public class LibraryAPIStaticPayload {
    final String BASE_URI = "http://216.10.245.166";

    @Test
    public void addBook() throws IOException {
        RestAssured.baseURI = BASE_URI;
        String resInStr = given().
                log().all().
                header("Content-Type","application/json").
                body(new String(Files.readAllBytes(Paths.get("StaticPayload.json")))).
                when().
                post("/Library/Addbook.php").
                then().
                log().all().
                assertThat().statusCode(200).
                extract().response().asString();

        // parse string to json
        JsonPath resInJson = new JsonPath(resInStr);
        String id = resInJson.getString("ID");
        System.out.println(id);

        given().
                log().all().
                header("Content-Type","application/json").
                body(deletePayload(id)).
                when().
                post("/Library/DeleteBook.php").
                then().
                log().all().
                assertThat().statusCode(200);
    }

}
