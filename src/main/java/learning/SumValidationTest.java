package learning;

import io.restassured.path.json.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SumValidationTest {
    public static final Logger logger = LoggerFactory.getLogger(SumValidationTest.class);
    @Test
    public  void sumOfCourses()
    {
        JsonPath jsonPath = new JsonPath(Payload.CoursePrice());
        int count = jsonPath.getInt("courses.size()");
        logger.info("{} is the number of available courses", count);

        int purchaseAmount = jsonPath.getInt("dashboard.purchaseAmount");
        logger.info("{} is the purchase amount of courses", purchaseAmount);

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
