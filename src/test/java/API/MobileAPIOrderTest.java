package API;


import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Cookie;
import com.jayway.restassured.response.Cookies;
import com.jayway.restassured.response.Response;


import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import com.jayway.restassured.RestAssured;
import org.testng.annotations.Test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;

import static com.jayway.restassured.RestAssured.sessionId;
import static org.hamcrest.Matchers.*;

/**
 * Created by Nexis on 9/4/2015.
 */
public class MobileAPIOrderTest {

    String baseuri = "https://api.data.gov";
    String userName = "Vignesh1rajan@gmail.com";
    String userPass = "mpt232";

    String itemID = "25710662";

    Cookies cookies;
    Cookie cookie;
    String sessionId;

    @BeforeClass
    public void setup(){
        RestAssured.baseURI= "https://api.mobile.walmart.com";

    }

    @Test
    public void getToken(){

        Response response =
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
        extract().response();
               // sessionId().
               // path("token");
        String token = response.jsonPath().getString("token");

       sessionId = response.getSessionId();
      //cookies =  response.detailedCookie("SSID");


        System.out.println(sessionId);

    }
    @Test(dependsOnMethods = "getToken",description = " Search for item and parse the returned json for item"
            , alwaysRun = true)
    public void searchforItem() {
        RestAssured.baseURI = "https://mobile.walmart.com";
        RestAssured.sessionId = sessionId;
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
                        //cookie(cookies).
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

    @Test(dependsOnMethods = "searchforItem", alwaysRun = true)
    public void addItemToCart(){

       Response response =
       given().
                log().all().
                sessionId(sessionId).
                parameter("service","Cart").
                parameter("method","add").
                parameter("p1", "25710662").
                parameter("p2", "0").
                parameter("e", "1").
                //cookie(cookies).
        when().
                get("m/j").
        then().
                log().all().
                statusCode(200).
        extract().response();
                //.path("count");

        sessionId = response.getSessionId();
        cookie = response.detailedCookie("WMSessionID");
        //Assert.assertTrue(count == 1);

    }

    @Test(dependsOnMethods = "searchforItem", alwaysRun = true)
    public void getCartItems(){
        Response response =
        given().
                log().all().
                sessionId(sessionId).
                queryParam("service", "Cart").
                queryParam("method","get").
                queryParam("p1", "[\"ACTUAL\",\"SAVE_FOR_LATER\"]").
                queryParam("e", "1").
                cookie(cookie).
        when().
                get("m/j").
        then().
                log().all().
                statusCode(200).
        extract().response();
                //.path("items");
        //itemList.containsValue(itemID);


    }


}
