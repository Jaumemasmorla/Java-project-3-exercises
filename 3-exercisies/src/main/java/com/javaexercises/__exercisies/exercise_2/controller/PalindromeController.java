package com.javaexercises.__exercisies.exercise_2.controller;

import com.javaexercises.__exercisies.exercise_2.service.PalindromeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
public class PalindromeController {

    private final PalindromeService palindromeService;

    @PostMapping(value = "/palindrome", produces = "text/plain")
    public ResponseEntity<String> checkPalindrome(@RequestBody String word){
        word = word.trim();
        boolean palindrome = palindromeService.palindrome(word);
        return palindrome ? ResponseEntity.ok("OK"):ResponseEntity.status(HttpStatus.BAD_REQUEST).body("KO");
    }

}
