package com.javaexercises.__exercisies.test;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ContextConfiguration(classes = TestConfig.class)
public class PalindromeServiceSteps {

    private final TestRestTemplate testRestTemplate;
    private String word;
    private ResponseEntity<String> response;

    @Autowired
    public PalindromeServiceSteps(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
    }

    @Given("I have the word {string}")
    public void i_have_the_word(String word) {
        this.word = word;
    }

    @Given("I have no word")
    public void i_have_no_word() {
        this.word = null;
    }

    @When("I send the word to the palindrome checker")
    public void i_send_the_word_to_the_palindrome_checker() {
        HttpEntity<String> request = new HttpEntity<>(word);
        response = testRestTemplate.postForEntity("http://localhost:8080/api/v1/palindrome", request, String.class);
    }

    @Then("I should receive a {string} response")
    public void i_should_receive_a_response(String expectedResponse) {
        if (expectedResponse.equals("OK")) {
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(expectedResponse, response.getBody().trim());
        }

        if (expectedResponse.equals("KO")){
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals(expectedResponse, "KO");
        }

}

}
