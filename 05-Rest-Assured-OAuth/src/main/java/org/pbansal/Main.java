package org.pbansal;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static io.restassured.RestAssured.given;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5000));

        driver.get("https://accounts.google.com/o/oauth2/v2/auth/identifier?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&auth_url=https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fv2%2Fauth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https%3A%2F%2Frahulshettyacademy.com%2FgetCourse.php&flowName=GeneralOAuthFlow");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@type='email']")).sendKeys("yourgoogleaccountusername");
        String buttonXpath = "//span[contains(text(), 'Next')]";
        driver.findElement(By.xpath(buttonXpath)).click();
        Thread.sleep(20000);
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("yourgoogleaccountpassword");
        driver.findElement(By.xpath(buttonXpath)).click();
        Thread.sleep(5000);
        String resLinkFromAuthSer = driver.getCurrentUrl();
        System.out.println(resLinkFromAuthSer);
        String auth_code = resLinkFromAuthSer.split("&scope")[0].split("code=")[1];
        System.out.println(auth_code);

        RestAssured.baseURI = "https://www.googleapis.com";
        String resFromResSer = given().
                urlEncodingEnabled(false).
                log().all().
                queryParam("code",auth_code).
                queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com").
                queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W").
                queryParam("redirect_uri","https://rahulshettyacademy.com/getCourse.php").
                queryParam("grant_type", "authorization_code").
        when().
                post("/oauth2/v4/token").
        then().
                log().all().
                assertThat().statusCode(200).
                extract().response().asString();
        JsonPath resFromResSevJson = new JsonPath(resFromResSer);
        String access_token = resFromResSevJson.getString("access_token");
        System.out.println(access_token);

        given().
                contentType("application/json").
                queryParam("access_token", access_token)
                .expect().defaultParser(Parser.JSON).
        when()
                .get("https://rahulshettyacademy.com/getCourse.php").
        then().
                log().all().
                extract().response();
    }
}
