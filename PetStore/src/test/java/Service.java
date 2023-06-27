import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Service {


    public String baseUri() {
        return "https://petstore.swagger.io/v2";
    }

    /**
     * Method that generates random number in given range
     */
    public int randomNumberGenerator(int minimum, int maximum) {
        Random random = new Random();
        return random.nextInt(maximum - minimum) + minimum;
    }

    public int randomNumberGenerator() {
        int maximum = 99999999;
        int minimum = 10000000;
        Random random = new Random();
        int randomNumber = random.nextInt(maximum - minimum) + minimum;
        System.out.println("Random number: " + randomNumber);
        return randomNumber;
    }

    public String getRandomPetType() {
        List<String> petTypes = Arrays.asList("Dog", "Cat", "Bird", "Fish", "Hamster", "Rabbit", "Mouse", "Snake", "Turtle", "Lizard");
        Random random = new Random();
        int randomIndex = random.nextInt(petTypes.size());
        String petType = petTypes.get(randomIndex);
        System.out.println("Pet type: " + petType);
        return petType;
    }


    public String getRandomPetName() {
        List<String> petNames = Arrays.asList("Max", "Bella", "Leo", "Luna", "Charlie", "Sadie", "Cooper", "Stella", "Milo", "Lucy", "Buddy", "Daisy", "Rocky", "Lily", "Teddy", "Zoe", "Duke", "Lola", "Mossberg", "Bailey");
        Random random = new Random();
        int randomIndex = random.nextInt(petNames.size());
        String petName = petNames.get(randomIndex);
        System.out.println("Pet name: " + petName);
        return petName;
    }

    String photoUrl(String petType, int petId, String petName) {
        String photoUrl = "https://photos/pets/" + petType + "/" + petId + "/" +petName;
        System.out.println("Photo url: " + photoUrl);
        return photoUrl;
    }

    public String getRandomPetTagName() {
        List<String> petTagNames = Arrays.asList("Cute", "Sweet", "Adorable", "Small", "Medium", "Big", "Friendly", "Guard", "Family", "Miniature");
        Random random = new Random();
        int randomIndex = random.nextInt(petTagNames.size());
        String petTagName = petTagNames.get(randomIndex);
        System.out.println("Pet type: " + petTagName);
        return petTagName;
    }

    public String getRandomPeStatus() {
        List<String> petNames = Arrays.asList("Available", "Pending", "Sold");
        Random random = new Random();
        int randomIndex = random.nextInt(petNames.size());
        String petName = petNames.get(randomIndex);
        System.out.println("Pet status: " + petName);
        return petName;
    }

    public JsonNode loadRequestBodiesFromFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(new File("src/test/resources/requests.json"));
    }

    public String injectVariablesIntoRequestBody(String requestBody, Map<String, Object> variables)
            throws IOException {
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(new StringReader(requestBody), "template");
        Writer writer = new StringWriter();
        mustache.execute(writer, variables);
        writer.flush();
        return writer.toString();
    }


}



