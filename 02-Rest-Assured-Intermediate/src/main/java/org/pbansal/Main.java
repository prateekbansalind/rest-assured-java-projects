package org.pbansal;

import org.testng.Assert;

import static helper.Parse.parseToJson;
import static helper.MockResponse.mockResponse;

public class Main {
    public static void main(String[] args) {
        // In this project, we are testing RSA developed library API focused on common http methods (post, put, get, delete)

        var courseJSON = parseToJson(mockResponse());


        // 1. Print No of courses returned by API
        int noOfCourses = courseJSON.getInt("courses.size()"); // this method can only be applied for array.
        System.out.println("The no. of courses contains in mock json response : " + noOfCourses);

        // 2.Print Purchase Amount
        int totalPurchaseAmount = courseJSON.getInt("dashboard.purchaseAmount");
        System.out.println(totalPurchaseAmount);
        Assert.assertEquals(totalPurchaseAmount, 910);

        // 3. Print Title of the first course
        String firstCourseTitle = courseJSON.getString("courses[0].title");
        System.out.println(firstCourseTitle);
        Assert.assertEquals(firstCourseTitle, "Selenium Python");

        // 4. Print All course titles and their respective Prices
        for (var i = 0; i < noOfCourses; i++){
            System.out.println(courseJSON.getString("courses["+i+"].title")
                    + " : " + courseJSON.getString("courses["+i+"].price"));
        }

        // 5. Print no of copies sold by RPA Course
        for (var i = 0; i < noOfCourses; i++){
            if (courseJSON.getString("courses["+i+"].title").equalsIgnoreCase("RPA")){
                int noOfRPACopies = courseJSON.getInt("courses["+i+"].copies");
                System.out.println("No of RPA course copies : " + noOfRPACopies);
                break;
            }
        }

        // 6. Verify if Sum of all Course prices matches with Purchase Amount
        // this same test case is covered in the separate class called "ValidationTest" where you can use @Test annotation.
        var calculatedPurchaseAmount = 0;
        for (var i = 0; i < noOfCourses; i++){
            var product = courseJSON.getInt("courses.copies["+i+"]")
                    * courseJSON.getInt("courses.price["+i+"]");
            calculatedPurchaseAmount += product;
        }
        System.out.println("The total amount of all the book is " + calculatedPurchaseAmount + ".");
        Assert.assertEquals(calculatedPurchaseAmount, 910);


    }
}