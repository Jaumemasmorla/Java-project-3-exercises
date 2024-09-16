package com.javaexercises.__exercisies.test;

import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@Configuration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestConfig {


    @Bean
    public TestRestTemplate testRestTemplate() {
        return new TestRestTemplate();
    }
}
