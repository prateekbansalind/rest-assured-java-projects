package api.tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static helper.Payload.*;
import static io.restassured.RestAssured.*;

public class LibraryAPIDynamicPayload {

    final String BASE_URI = "http://216.10.245.166";

    /* this is a project which contains api test for all the common methods exposed by Library API's. */
    @Test(dataProvider = "addBookPayloadData")
    public void addBook(String isbn, String aisle){
        RestAssured.baseURI = BASE_URI;
        String resInStr = given().
                log().all().
                header("Content-Type","application/json").
                body(payload(isbn, aisle)).
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

    @DataProvider(name = "addBookPayloadData")
    public static Object[][] payloadParametersArray(){
        return new Object[][] {{"may", "2022"},{"june", "2022"},{"july", "2021"}};
    }





//    @Test
//    public void getBookById(){
//        RestAssured.baseURI = BASE_URI;
//        given().
//                log().all().
//                header("Content-Type","application/json").
//                when().
//                get("/Library/GetBook.php?ID="+addBook()+"").
//                then().
//                log().all().
//                assertThat().statusCode(200);
//
//    }

//    @Test
//    public void deleteBook(){
//        RestAssured.baseURI = BASE_URI;
//        given().
//                log().all().
//                header("Content-Type","application/json").
//                body(deletePayload(addBook())).
//                when().
//                post("/Library/DeleteBook.php").
//                then().
//                log().all().
//                assertThat().statusCode(200);
//    }

}
