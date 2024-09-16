Feature: Palindrome checker
  As a user
  I want to check if a word is a palindrome
  So that I can get correct results for valid and invalid words

  Scenario: Check a valid palindrome word
    Given I have the word "madam"
    When I send the word to the palindrome checker
    Then I should receive a "OK" response

  Scenario: Check a word that is not a palindrome
    Given I have the word "hello"
    When I send the word to the palindrome checker
    Then I should receive a "KO" response

  Scenario: Check a word with spaces
    Given I have the word "race car"
    When I send the word to the palindrome checker
    Then I should receive a "KO" response

  Scenario: Check a word with null value
    Given I have no word
    When I send the word to the palindrome checker
    Then I should receive a "KO" response

  Scenario: Check a word with non-alphabet characters
    Given I have the word "madam123"
    When I send the word to the palindrome checker
    Then I should receive a "KO" response
