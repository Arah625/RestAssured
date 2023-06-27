import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

;

public class APITests {

//    @Test
//    void test1() {
//
//        Response response = get("https://reqres.in/api/users?page=2");
//        int statusCode = response.getStatusCode();
//        System.out.println("Response status code: " + statusCode);
//        System.out.println("Response body: " + response.getBody().asString());
//        System.out.println("Response header: " + response.getHeaders());
//        System.out.println("Response: " + response.then().log().all());
//        System.out.println("Time of response: " + response.getTime());
//        Assert.assertEquals(statusCode, 200);
//    }

    @Test
    void test2() {
        Map map = new HashMap();
        map.put("Connection", "keep-alive");
        map.put("Accept", "*/*");
        map.put("Acceptt", "*wsda/*");
        given().headers(map);
        Response response1 = RestAssured.
                given().headers(map).body("").
                when().get("https://reqres.in/api/users?page=2");
        System.out.println("odp: " + response1.prettyPeek());
    }
}
