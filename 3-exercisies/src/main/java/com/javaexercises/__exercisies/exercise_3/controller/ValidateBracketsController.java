package com.javaexercises.__exercisies.exercise_3.controller;


import com.javaexercises.__exercisies.exercise_3.service.ValidateBracketsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ValidateBracketsController {

    private final ValidateBracketsService validateBracketsService;

    @PostMapping("/validate-brackets")
    public ResponseEntity<String> validateBrackets(@RequestBody String sentence){
        boolean isValidBrackets= validateBracketsService.validateBrackets(sentence);

        return isValidBrackets ? ResponseEntity.ok("OK") : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("KO");
    }
}
