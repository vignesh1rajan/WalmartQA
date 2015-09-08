package API;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import com.jayway.restassured.RestAssured;
import org.testng.annotations.Test;


import javax.xml.ws.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.withArgs;
import static org.hamcrest.Matchers.*;

/**
 * Created by Nexis on 9/4/2015.
 */
public class MobileAPIOrderTest {

    String baseuri = "https://api.data.gov";
    String userName = "Vignesh1rajan@gmail.com";
    String userPass = "mpt232";

    String itemID = "25710662";

    @BeforeClass
    public void setup(){
        RestAssured.baseURI= "https://api.mobile.walmart.com";

    }

    @Test
    public void getToken(){

        String token =
        given().
                log().path().
                formParam("email", userName).
                formParam("password", userPass).
                formParam("cartHandling", 1).
        when().
                post("/mauth/getToken").
        then().
                log().ifError().
                statusCode(200).
        extract().
                path("token");

        System.out.println(token);

    }
    @Test
    public void searchforItem() {
        RestAssured.baseURI = "https://mobile.walmart.com";
        //Create a Array list of objest from the Rest call
        ArrayList<Map<String, ?>> jsonArray =
                given().
                        log().all().
                        parameter("service", "Browse").
                        parameter("method", "searchByDepartmentFiltered").
                        parameter("p1", "socks").
                        parameter("p2", "0").
                        parameter("p3", "All").
                        parameter("p4", "RELEVANCE").
                        parameter("p5", "0").
                        parameter("p6", "50").
                        parameter("p7", "[]").
                        parameter("e", "1").
                        when().

                        post("m/j").
                        then().
                        log().all().
                        statusCode(200).
                        //root("item").
                                body("item.iD", hasItem(itemID)).
                        //body("item.find {iD == '25710662' }.itemAvailability.availability",equalTo("In Stock")).extract().jsonPath().;
                                extract().
                        path("item");

        //HashMap of objects to store item objects
        HashMap<Object, Map<String, ?>> jsonHash = new HashMap<Object, Map<String, ?>>();
        //Put the hashmap as key->iD # and the item map pair for the item
        for (Map<String, ?> entry : jsonArray) {
            jsonHash.put(entry.get("iD"), entry);
        }

        //Get item availability
        Map<String, ?> itemAvail = (Map) jsonHash.get(itemID).get("itemAvailability");

        //Validate the item availability is in stock for order
        Assert.assertTrue(itemAvail.get("availability").equals("In Stock"), "Failed to validate item is 'In Stock' ");

    }

    @Test
    public void addItemToCart(){


    }
}
