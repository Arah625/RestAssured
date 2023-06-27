import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;


public class RestAssuredHamcrestTests {

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
    void testCreatePetAndGetPetByIdSingleTagHamcrest() throws IOException {
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
                .statusCode(200).log().all(); // Assert the response status code

        // Send the GET request
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get(service.baseUri() + "/pet/" + petId)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .assertThat()
                .body("id", is(petId))
                .body("category.id", is(petId))
                .body("category.name", is(petName))
                .body("name", is(petType))
                .body("photoUrls[0]", is(photoUrl))
                .body("tags[0].id", is(petId))
                .body("tags[0].name", is(petName))
                .body("status", is(petStatus))
                .log().all();
    }

    @Test
    void testCreatePetAndGetPetByIdMultipleTagsHamcrest() throws IOException {
        //Loading requests from file
        JsonNode requestBodies = service.loadRequestBodiesFromFile();

        String requestKey = "addPetMultipleTags"; // Key of the desired request body from requests.json file
        JsonNode requestBody = requestBodies.get(requestKey);

        //Setting randomized values for request
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

        //Assigned values to key, which represents fields in request
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

        //Method that inject variables from HashMap to request
        String requestBodyWithValues = service.injectVariablesIntoRequestBody(requestBody.toString(), variables);

        // Send the POST request
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBodyWithValues)
                .when()
                .post("/pet")
                .then()
                .statusCode(200).log().all(); // Assert the response status code

        // Send the GET request
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get(service.baseUri() + "/pet/" + petId)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .assertThat()
                .body("id", is(petId))
                .body("category.id", is(petId))
                .body("category.name", is(petName))
                .body("name", is(petType))
                .body("photoUrls[0]", is(photoUrl))
                .body("tags", hasSize(3))
                .body("tags.id", contains(petTagId1, petTagId2, petTagId3)) //contains specified values in exact given order
                .body("tags.id", not(contains(petTagId2, petTagId3, petTagId1))) //not contains specified values in exact given order
                .body("tags.name", containsInAnyOrder(petTagName1, petTagName2, petTagName3)) //contains specified values in any order (does not check order, only existence of values). It must contain all (in this case) three elements to pass.
                .body("tags.name", hasItems(petTagName1, petTagName3)) //contains specified values in any order (does not check order, only existence of values). It may contain (in this case) one, two or three elements to pass
                .body("status", is(petStatus))
                .log().all();
    }

}
