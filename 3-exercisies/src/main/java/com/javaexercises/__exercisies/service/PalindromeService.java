package com.javaexercises.__exercisies.service;


import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class PalindromeService {

    public boolean palindrome(String word){

        if (word == null ||word.contains(" ") ||!word.matches("[a-zA-Z]+")){
            return false;
        }

        String lowerCaseWord= word.toLowerCase();

        for(int i=0; i < lowerCaseWord.length() / 2; i++){
            if(lowerCaseWord.charAt(i) != lowerCaseWord.charAt(lowerCaseWord.length() -i -1)){
                return false;
            }
        }
        return true;
    }
}
