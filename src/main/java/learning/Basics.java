package learning;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Basics {

    public static void main(String[] args){
        RestAssured.baseURI="https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(Payload.AddPlace()).when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .header("Server", equalTo("Apache/2.4.18 (Ubuntu)")).extract().response().asString();

        System.out.println(response);
        JsonPath jp = new JsonPath(response); //For parsing json
        String placeId = jp.getString("place_id");
        System.out.println(placeId);

        //Update place
        String newAddress = "70 Summer walk, USA";
                 given().log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"place_id\": \"" + placeId + " \",\n" +
                        "    \"address\": \"" + newAddress +"\",\n" +
                        "    \"key\": \"qaclick123\"\n" +
                        "}")
                .when().put("maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200)
                .header("Server", equalTo("Apache/2.4.18 (Ubuntu)"))
                .body("msg", equalTo("Address successfully updated"));


        //Get Place

        String getResponse= given().log().all().queryParam("key", "qaclick123")
                .queryParam("place_id", placeId)
                .when().get("maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();
        JsonPath jsonPath = new JsonPath(getResponse);
        String actualaddress = jsonPath.getString("address");
        System.out.println(actualaddress);
        Assert.assertEquals(actualaddress, newAddress);




    }
 /*   public static void main(String[] args){
        Response response =
                given().auth().preemptive().basic("sheetal.p@whitesnow.com", "r[2^qH&KsR,LX`j%")
                        .contentType("application/json").
                        when().post("https://dev-api.processcreator.com/api/v1/login/").
                        then().log().all().extract().response();
        String jsessionidId =  response.getSessionId();//or response.cookie("JSESSIONID");
        System.out.println("Jsession id: "+ jsessionidId);
    }*/
}
