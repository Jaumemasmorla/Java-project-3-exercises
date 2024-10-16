package com.javaexercises.client.clients.service;

import com.javaexercises.__exercisies.exercise_1.DTO.ItemRegistrationRequest;
import com.javaexercises.__exercisies.exercise_1.model.Item;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    public ResponseEntity<String> createItem(String name) {
        ItemRegistrationRequest request = new ItemRegistrationRequest(name);
        try {
            return restTemplate.postForEntity("http://localhost:8080/api/v1/items", request, String.class);
        } catch (Exception e) {
            logger.error("Error creating item: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Item creation failed.");
        }
    }

    public List<Item> getAllItems() {
        try {
            Item[] items = restTemplate.getForObject("http://localhost:8080/api/v1/items", Item[].class);
            return Arrays.asList(items);
        } catch (Exception e) {
            logger.error("Error fetching items: {}", e.getMessage());
            return List.of();
        }
    }

    public Item getItemByName(String name) {
        try {
            return restTemplate.getForObject("http://localhost:8080/api/v1/items/{name}", Item.class, name);
        } catch (Exception e) {
            logger.error("Error fetching item by name '{}': {}", name, e.getMessage());
            return null;
        }
    }

    public ResponseEntity<String> updateItem(String name, String nameUpdated) {
        ItemRegistrationRequest request = new ItemRegistrationRequest(nameUpdated);
        HttpEntity<ItemRegistrationRequest> entity = new HttpEntity<>(request);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8080/api/v1/items/" + name,
                    HttpMethod.PUT,
                    entity,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok("Item updated successfully.");
            } else {
                return ResponseEntity.status(response.getStatusCode()).body("Update failed: " + response.getBody());
            }
        } catch (Exception e) {
            logger.error("Error updating item '{}': {}", name, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Item update failed.");
        }
    }


    public ResponseEntity<String> deleteItem(String name) {

        String cleanedName = name.replaceAll("[{}\"]", "").trim();

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8080/api/v1/items/" + cleanedName,
                    HttpMethod.DELETE,
                    null,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
                return ResponseEntity.ok(cleanedName);
            } else {
                return ResponseEntity.status(response.getStatusCode()).body("Item deletion failed: " + response.getBody());
            }
        } catch (Exception e) {
            logger.error("Error deleting item '{}': {}", cleanedName, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Item deletion failed.");
        }
    }

    public String checkPalindrome(String word) {
        try {

            ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/api/v2/palindrome", word, String.class);


            if (response.getStatusCode() == HttpStatus.OK) {
                return "OK";
            } else {
                return "KO";
            }
        } catch (Exception e) {
            logger.error("Error checking palindrome: {}", e.getMessage());
            return "Error checking palindrome.";
        }
    }


    public String validateBrackets(String sentence) {
        try {
            return restTemplate.postForObject("http://localhost:8080/api/v3/validate-brackets", sentence, String.class);
        } catch (Exception e) {
            logger.error("Error validating brackets: {}", e.getMessage());
            return "Error validating brackets.";
        }
    }
}
