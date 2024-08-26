package com.javaexercises.__exercisies.service;

import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;

@Service
public class ValidateBracketsService {

    public boolean validateBrackets(String sentence) {
        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < sentence.length(); i++) {
            char ch = sentence.charAt(i);

            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
                continue;
            }
            if (ch == ')' || ch == '}' || ch == ']') {
                if (stack.isEmpty()) {
                    return false;
                }
                char check = stack.pop();
                if (!isMatchingPair(check, ch)) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    private boolean isMatchingPair(char open, char close) {
        return (open == '(' && close == ')') ||
                (open == '{' && close == '}') ||
                (open == '[' && close == ']');
    }
}
