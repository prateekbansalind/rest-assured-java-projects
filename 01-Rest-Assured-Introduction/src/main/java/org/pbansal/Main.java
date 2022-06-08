package org.pbansal;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static helper.Helper.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Main {
    public static void main(String[] args) {
        // In this project, we are testing RSA custom made developed maps API.

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        // POST method - add address
        // Validating the rest api responses.
        // resInStr : response in string form
        System.out.println("POST HTTP Request : add new address");
        String postResInStr = given()
                .log().all()
                .queryParam("key","qaclick123")
                .header("Content-Type","application/json")
                .body(payload("128A Lyttenton Street, Christchurch")).
                when()
                .post("/maps/api/place/add/json?key=qaclick123").
                then()
                .assertThat()
                .statusCode(200)
                .body("scope", equalTo("APP"))
                .header("Server","Apache/2.4.41 (Ubuntu)")
                .extract().response().asString();
        System.out.println("The generated response from the server in string form: \n" + postResInStr);
        // parse string (text format) into json
        // JsonPath is a class within RestAssured Package
        var postResInJSON = parseToJson(postResInStr); // resInJson : response in JSON form.
        String place_id = postResInJSON.getString("place_id");
        System.out.println("The place_id is : " + place_id + "\n");

        // PUT method to update address
        System.out.println("PUT HTTP Request : update exisiting address");
        String updated_address = "1/34 Brougham Street, Addington";
        String putResInStr = given()
                .log().all()
                .body(payload(place_id, ""+updated_address+""))
                .header("Content-Type", "application/json")
                .queryParam("key", "qaclick123").
                when()
                .put("/maps/api/place/update/json").
                then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("msg", equalTo("Address successfully updated"))
                .extract().response().asString();
        // parse to json
        var putResInJSON = parseToJson(putResInStr);
        System.out.println(updated_address);

        // GET method to retrieve updated address
        System.out.println("GET HTTP Request : retrieve updated address");
        String getResInStr = given()
                .log().all()
                .queryParam("place_id", place_id)
                .queryParam("key", "qaclick123").
        when()
                .get("/maps/api/place/get/json").
        then()
                .log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();
        // Parse to json
        var getResInJSON = parseToJson(getResInStr);
        String get_address = getResInJSON.getString("address");
        System.out.println(get_address);
        // assert if the new address got updated or not.
        Assert.assertEquals(get_address, updated_address);

        // DELETE method to delete record.
        System.out.println("DELETE HTTP Request : delete record");
        given()
                .log().all()
                .queryParam("key", "qaclick123")
                .body(deletePayload(place_id)).
        when()
                .delete("/maps/api/place/delete/json").
        then()
                .log().all()
                .assertThat().statusCode(200)
                .body("status", equalTo("OK"));

    }

}