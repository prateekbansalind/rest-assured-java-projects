package org.pbansal;

import org.testng.Assert;
import org.testng.annotations.Test;

import static helper.MockResponse.mockResponse;
import static helper.Parse.parseToJson;

public class ValidationTest {

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
