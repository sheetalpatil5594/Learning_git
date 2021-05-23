package learning;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OauthLearning {
    public static void main(String args[]) {
        String[] courseTitles= {"Selenium Webdriver Java", "Cypress", "Protractor"};

      /*  System.setProperty("webdriver.chrome.driver", "C://chromedriver.exe");
        Webdriver driver = new ChromeDriver();*/
        String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AY0e-g4wuvkaYvthsxw44Bn_dxkDo5fmhbp1vBBdJILm94rhVwAw_2rPT_0j7Ka29K_r7w&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent#";
        String partialcode = url.split("code=")[1];
        String code = partialcode.split("&scope")[0];
        System.out.println(code);


        String accessTokenResponse = given()
                .queryParams("code", code)
                .queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                .queryParams("grant_type", "authorization_code")
                .when().log().all()
                .post("https://www.googleapis.com/oauth2/v4/token").asString();
        JsonPath jsonPath = new JsonPath(accessTokenResponse);
        String accessToken = jsonPath.getString("access_token");


        GetCourse gc = given().queryParam("access_token", "accessToken").expect().defaultParser(Parser.JSON)
                .when()
                .get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
        System.out.println(gc.getLinkedIn());
        System.out.println(gc.getInstructor());
        System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());

        List<Api> apiCourse = gc.getCourses().getApi();
        for (int i = 0; i < apiCourse.size(); i++) {
            if (apiCourse.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
                System.out.println(apiCourse.get(i).getPrice());
            }
        }

        //get course names of web automation
        ArrayList<String> a = new ArrayList<String>();

        List<WebAutomation> webAutomation = gc.getCourses().getWebAutomation();
        for (int i=0; i< webAutomation.size(); i++)
        {
            a.add(webAutomation.get(i).getCourseTitle());
        }

        List<String> expectedList = Arrays.asList(courseTitles);

        Assert.assertTrue(a.equals(expectedList));

    }
}
