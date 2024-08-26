package com.javaexercises.__exercisies.test;

import com.javaexercises.__exercisies.service.PalindromeService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

public class PalindromeTest {

    private final PalindromeService palindromeService = new PalindromeService();

    @Test
    void testPalindrome(){
        String palindrome = "Madam";

        boolean result = palindromeService.palindrome(palindrome);

        assertThat(result).isTrue();
    }

    @Test
    void testNotPalindrom(){
        String notPalindrome = "Car";

        boolean result = palindromeService.palindrome(notPalindrome);

        assertThat(result).isFalse();
    }

    @Test
    void palindromeSentence(){
        String palindromeSentence= "Noel sees Leon";

        boolean result = palindromeService.palindrome(palindromeSentence);

        assertThat(result).isTrue();
    }

    @Test
    void testPalindromeSpecialCharacters(){
        String palindromeSpecialCharacters= "fdhj29387";

        boolean result = palindromeService.palindrome(palindromeSpecialCharacters);

        assertThat(result).isFalse();
    }

    @Test
    void testPalindromeEmptyString(){

        String palindromeEmpty ="";

        boolean result = palindromeService.palindrome(palindromeEmpty);

        assertThat(result).isFalse();
    }

    @Test
    void testPalindromeIsNull(){

        String palindromeIsNull= null;

        boolean result = palindromeService.palindrome(palindromeIsNull);

        assertThat(result).isFalse();
    }
}
