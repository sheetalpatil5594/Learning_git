package learning;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DynamicJson {
    public static final Logger logger = LoggerFactory.getLogger(DynamicJson.class);

    @Test(priority = 0,dataProvider = "BooksData")
    public void addBook(String isbn, String aisle)
    {
        RestAssured.baseURI="http://216.10.245.166";
        String response1;
        response1 = given().log().all()
                .header("Content-Type", "application/json")
                .body(Payload.addBook(isbn, aisle)).when().post("/Library/Addbook.php")
                .then().assertThat().statusCode(200)
                .extract().response().asString();
        logger.info("{} Response ", response1);
        JsonPath json = new JsonPath(response1); //For parsing json
        String id = json.get("ID");
        logger.info("{}" , id);
    }
/*
    @Test(priority = 1)
    public void addBookNegative()
    {
        RestAssured.baseURI="http://216.10.245.166";
        String response1;
        response1 = given().log().all()
                .header("Content-Type", "application/json")
                .body(Payload.addBook("bcdz", "922972"))
                .when().post("/Library/Addbook.php")
                .then().assertThat().statusCode(404)
                .body("msg", equalTo("Add Book operation failed, looks like the book already exists"))
                .extract().response().asString();
        logger.info("{} Response ", response1);
        JsonPath json = new JsonPath(response1); //For parsing json
        String id = json.get("ID");
        logger.info("{}" , id);
    }*/

    @Test(priority = 2, dataProvider = "BooksData")
    public void deleteBook(String isbn, String ailse)
    {
        RestAssured.baseURI="http://216.10.245.166";
        String response1;
        response1 = given().log().all()
                .header("Content-Type", "application/json")
                .body(Payload.deleteBook(isbn+ailse)).when().delete("/Library/DeleteBook.php")
                .then().assertThat().statusCode(200)
                .extract().response().asString();
        logger.info("{} Response ", response1);
    }

    @DataProvider(name = "BooksData")
    public Object[][] getData(){
        //array=collection of elements
        //multidimentional array= collection of arrays
        return new Object[][]{{"qa","23"}, {"dsf", "543"}, {"vhd", "76434"}};
    }

}
