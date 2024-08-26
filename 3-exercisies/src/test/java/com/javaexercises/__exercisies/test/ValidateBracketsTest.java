package com.javaexercises.__exercisies.test;

import com.javaexercises.__exercisies.service.ValidateBracketsService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidateBracketsTest {

    private final ValidateBracketsService validateBracketsService = new ValidateBracketsService();

    @Test
    void testValidBrackets(){
        String validBrackets1 = "({[]})";
        String validBrackets2 = "[]({})";
        String validBrackets3 = "{[()]}";

        assertThat(validateBracketsService.validateBrackets(validBrackets1)).isTrue();
        assertThat(validateBracketsService.validateBrackets(validBrackets2)).isTrue();
        assertThat(validateBracketsService.validateBrackets(validBrackets3)).isTrue();
    }

    @Test
    void testInvalidBrackets(){
        String invalidBrackets1 = "({[)]}";
        String invalidBrackets2 = "((())";
        String invalidBrackets3 = "[{]";

        assertThat(validateBracketsService.validateBrackets(invalidBrackets1)).isFalse();
        assertThat(validateBracketsService.validateBrackets(invalidBrackets2)).isFalse();
        assertThat(validateBracketsService.validateBrackets(invalidBrackets3)).isFalse();
    }

    @Test
    void testEmptyString(){
        String emptyBrackets ="";

        assertThat(validateBracketsService.validateBrackets(emptyBrackets)).isFalse();

    }

    @Test
    void testBracketsWithText(){
        String bracketWithText = "(agf(dg)d()sf)";

        assertThat(validateBracketsService.validateBrackets(bracketWithText)).isTrue();
    }
}
