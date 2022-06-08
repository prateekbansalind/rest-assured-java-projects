package api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import static helper.MockResponse.mockResponse;
import static helper.Parse.parseToJson;

public class ValidationTest {

    @Test
    // 1. Print No of courses returned by API
    public void noOfCourses(){
        var courseJSON = parseToJson(mockResponse());
        int noOfCourses = courseJSON.getInt("courses.size()"); // this method can only be applied for array.
        System.out.println("The no. of courses contains in mock json response : " + noOfCourses);
    }

    @Test
    // 2.Print Purchase Amount
    public void totalPurchaseAmount(){
        var courseJSON = parseToJson(mockResponse());
        int totalPurchaseAmount = courseJSON.getInt("dashboard.purchaseAmount");
        System.out.println(totalPurchaseAmount);
        Assert.assertEquals(totalPurchaseAmount, 910);
    };

    @Test
    // 3. Print Title of the first course
    public void titleFirstCourse(){
        var courseJSON = parseToJson(mockResponse());
        String firstCourseTitle = courseJSON.getString("courses[0].title");
        System.out.println(firstCourseTitle);
        Assert.assertEquals(firstCourseTitle, "Selenium Python");
    };

    @Test
    // 4. Print All course titles and their respective Prices
    public void getAllCourseDetails(){
        var courseJSON = parseToJson(mockResponse());
        int noOfCourses = courseJSON.getInt("courses.size()");
        for (var i = 0; i < noOfCourses; i++){
            System.out.println(courseJSON.getString("courses["+i+"].title")
                    + " : " + courseJSON.getString("courses["+i+"].price"));
        }
    }

    @Test
    // 5. Print no of copies sold by RPA Course
    public void copiesSoldByRPA(){
        var courseJSON = parseToJson(mockResponse());
        int noOfCourses = courseJSON.getInt("courses.size()");
        for (var i = 0; i < noOfCourses; i++){
            if (courseJSON.getString("courses["+i+"].title").equalsIgnoreCase("RPA")){
                int noOfRPACopies = courseJSON.getInt("courses["+i+"].copies");
                System.out.println("No of RPA course copies : " + noOfRPACopies);
                break;
            };
        };
    }

    @Test
    public void validatePurchaseAmount(){
        var courseJSON = parseToJson(mockResponse());

        // 6. Verify if Sum of all Course prices matches with Purchase Amount
        int noOfCourses = courseJSON.getInt("courses.size()"); // this method can only be applied for array.
        var calculatedPurchaseAmount = 0;
        for (var i = 0; i < noOfCourses; i++){
            var product = courseJSON.getInt("courses.copies["+i+"]")
                    * courseJSON.getInt("courses.price["+i+"]");
            calculatedPurchaseAmount += product;
        }
        System.out.println("The total amount of all the book is " + calculatedPurchaseAmount + ".");
        Assert.assertEquals(calculatedPurchaseAmount, 910);

        // In case if the above code gives a error where it says for missing jcommander dependency
        // then visit to the below mentioned lecture to see the fix.
        // section 6 : Real Time example to solve Business logic through Json
        // https://mvnrepository.com/artifact/com.beust/jcommander

    }
}
