package com.javaexercises.__exercisies.test;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = TestConfig.class)
public class ValidateBracketsSteps {

    private final TestRestTemplate testRestTemplate;
    private String sentence;
    private ResponseEntity<String> response;

    @Autowired
    public ValidateBracketsSteps(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
    }

    @Given("I have the sentence {string}")
    public void iHaveTheSentence(String sentence) {
        this.sentence = sentence;
    }

    @When("I send the sentence to the validate brackets service")
    public void sendSentenceToValidateBrackets() {
        HttpEntity<String> request = new HttpEntity<>(sentence);
        response = testRestTemplate.postForEntity("http://localhost:8080/api/v1/validate-brackets", request, String.class);
    }

    @Then("I should receive a {string} response OK or KO")
    public void validateBrackets(String expectedResponse) {

        assertEquals(expectedResponse, response.getBody().trim());


        if (expectedResponse.equals("OK")) {
            assertEquals(HttpStatus.OK, response.getStatusCode());
        } else if (expectedResponse.equals("KO")) {
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }
    }
}
