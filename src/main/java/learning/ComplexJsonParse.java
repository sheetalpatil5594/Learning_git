package learning;

import org.slf4j.Logger;
import io.restassured.path.json.JsonPath;
import org.slf4j.LoggerFactory;
import org.testng.Assert;


public class ComplexJsonParse {
    public static final Logger logger = LoggerFactory.getLogger(ComplexJsonParse.class);

    public static void main(String[] args) {


        JsonPath jsonPath = new JsonPath(Payload.CoursePrice());

        // Print No of courses returned by API

        int count = jsonPath.getInt("courses.size()");
        logger.info("{} is the number of available courses", count);

        //Print Purchase Amount

        int purchaseAmount = jsonPath.getInt("dashboard.purchaseAmount");
        logger.info("{} is the purchase amount of courses", purchaseAmount);

        //3. Print Title of the first course

        String courseTitle = jsonPath.get("courses[0].title");
        logger.info("{} is the title of first course", courseTitle);

//       4. Print All course titles and their respective Prices

        for (int i = 0; i < count; i++) {
            String newCourseTitle = jsonPath.get("courses[" + i + "].title");
            int coursePrice = jsonPath.get("courses[" + i + "].price");
            logger.info("{} : {} \t {}", i, newCourseTitle, coursePrice);
        }
//     Print no of copies sold by RPA Course
        int noOfCopies = jsonPath.getInt("courses[2].copies");
        logger.info("{} number of copies",noOfCopies);

//        Verify if Sum of all Course prices matches with Purchase Amount
        int sum=0;
        for (int i = 0; i < count; i++) {
            int coursePrice = jsonPath.get("courses[" + i + "].price");
            int copies = jsonPath.getInt("courses["+ i +"].copies");
            int localMultiplication= coursePrice * copies;
            sum += localMultiplication;
        }
        logger.info("{} is sum of prices",sum);
        Assert.assertEquals(purchaseAmount, sum);

    }
}
