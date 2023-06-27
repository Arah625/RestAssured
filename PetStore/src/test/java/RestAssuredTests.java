import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;


public class RestAssuredTests {

    Service service = new Service();


    @BeforeMethod
    public void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(); // Enable logging
    }

    @Test
    public void testCreatePet() throws IOException {

        //Loading requests from file
        JsonNode requestBodies = service.loadRequestBodiesFromFile();

        String requestKey = "addPetSingleTags"; // Key of the desired request body from requests.json file
        JsonNode requestBody = requestBodies.get(requestKey);

        //Setting randomized values for request
        int petId = service.randomNumberGenerator();
        String petType = service.getRandomPetType();
        String petName = service.getRandomPetName();
        String petStatus = service.getRandomPeStatus();
        String photoUrl = service.photoUrl(petType, petId, petName);

        //Assigned values to key, which represents fields in request
        Map<String, Object> variables = new HashMap<>();
        variables.put("petId", petId);
        variables.put("petType", petType);
        variables.put("petName", petName);
        variables.put("petStatus", petStatus);
        variables.put("photoUrl", photoUrl);

        //Method that inject variables from HashMap to request
        String requestBodyWithValues = service.injectVariablesIntoRequestBody(requestBody.toString(), variables);

        // Send the POST request
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBodyWithValues)
                .when()
                .post("/pet")
                .then()
                .assertThat()
                .statusCode(200).log().all(); // Assert the response status code
    }

    @Test
    void testCreatePetAndGetPetByIdSingleTag() throws IOException {
        // Loading requests from file
        JsonNode requestBodies = service.loadRequestBodiesFromFile();

        String requestKey = "addPetSingleTags"; // Key of the desired request body from requests.json file
        JsonNode requestBody = requestBodies.get(requestKey);

        // Setting randomized values for request
        int petId = service.randomNumberGenerator();
        String petType = service.getRandomPetType();
        String petName = service.getRandomPetName();
        String petStatus = service.getRandomPeStatus();
        String photoUrl = service.photoUrl(petType, petId, petName);

        // Assigned values to keys, which represent fields in the request
        Map<String, Object> variables = new HashMap<>();
        variables.put("petId", petId);
        variables.put("petType", petType);
        variables.put("petName", petName);
        variables.put("petStatus", petStatus);
        variables.put("photoUrl", photoUrl);

        // Method that injects variables from HashMap into the request body
        String requestBodyWithValues = service.injectVariablesIntoRequestBody(requestBody.toString(), variables);

        // Send the POST request and assert the response status code
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBodyWithValues)
                .when()
                .post("/pet")
                .then()
                .statusCode(200)
                .log()
                .all();

        // Send the GET request and assert the response body
        String response = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get(service.baseUri() + "/pet/" + petId)
                .then()
                .statusCode(200)
                .log()
                .all()
                .extract()
                .response()
                .asString();

        // Assert individual fields in the response body
        assertEquals(petId, JsonPath.from(response).getInt("id"));
        assertEquals(petId, JsonPath.from(response).getInt("category.id"));
        assertEquals(petName, JsonPath.from(response).getString("category.name"));
        assertEquals(petType, JsonPath.from(response).getString("name"));
        assertEquals(photoUrl, JsonPath.from(response).getString("photoUrls[0]"));
        assertEquals(petId, JsonPath.from(response).getInt("tags[0].id"));
        assertEquals(petName, JsonPath.from(response).getString("tags[0].name"));
        assertEquals(petStatus, JsonPath.from(response).getString("status"));
    }

    @Test
    void testCreatePetAndGetPetByIdMultipleTags() throws IOException {
        // Loading requests from file
        JsonNode requestBodies = service.loadRequestBodiesFromFile();

        String requestKey = "addPetMultipleTags"; // Key of the desired request body from requests.json file
        JsonNode requestBody = requestBodies.get(requestKey);

        // Setting randomized values for request
        int petId = service.randomNumberGenerator();
        int petTagId1 = service.randomNumberGenerator();
        int petTagId2 = service.randomNumberGenerator();
        int petTagId3 = service.randomNumberGenerator();
        String petType = service.getRandomPetType();
        String petName = service.getRandomPetName();
        String petTagName1 = service.getRandomPetTagName();
        String petTagName2 = service.getRandomPetTagName();
        String petTagName3 = service.getRandomPetTagName();
        String petStatus = service.getRandomPeStatus();
        String photoUrl = service.photoUrl(petType, petId, petName);

        // Assigned values to keys, which represent fields in the request
        Map<String, Object> variables = new HashMap<>();
        variables.put("petId", petId);
        variables.put("petTagId1", petTagId1);
        variables.put("petTagId2", petTagId2);
        variables.put("petTagId3", petTagId3);
        variables.put("petType", petType);
        variables.put("petName", petName);
        variables.put("petTagName1", petTagName1);
        variables.put("petTagName2", petTagName2);
        variables.put("petTagName3", petTagName3);
        variables.put("petStatus", petStatus);
        variables.put("photoUrl", photoUrl);

        // Method that injects variables from HashMap into the request body
        String requestBodyWithValues = service.injectVariablesIntoRequestBody(requestBody.toString(), variables);

        // Send the POST request and assert the response status code
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBodyWithValues)
                .when()
                .post("/pet")
                .then()
                .statusCode(200)
                .log()
                .all();

        // Send the GET request and assert the response body
        String response = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get(service.baseUri() + "/pet/" + petId)
                .then()
                .statusCode(200)
                .log()
                .all()
                .extract()
                .response()
                .asString();

        // Assert individual fields in the response body
        assertEquals(petId, JsonPath.from(response).getInt("id"));
        assertEquals(petId, JsonPath.from(response).getInt("category.id"));
        assertEquals(petName, JsonPath.from(response).getString("category.name"));
        assertEquals(petType, JsonPath.from(response).getString("name"));
        assertEquals(photoUrl, JsonPath.from(response).getString("photoUrls[0]"));
        assertEquals(petTagId1, JsonPath.from(response).getInt("tags[0].id"));
        assertEquals(petTagName1, JsonPath.from(response).getString("tags[0].name"));
        assertEquals(petTagId2, JsonPath.from(response).getInt("tags[1].id"));
        assertEquals(petTagName2, JsonPath.from(response).getString("tags[1].name"));
        assertEquals(petTagId3, JsonPath.from(response).getInt("tags[2].id"));
        assertEquals(petTagName3, JsonPath.from(response).getString("tags[2].name"));
        assertEquals(petStatus, JsonPath.from(response).getString("status"));
    }

}
