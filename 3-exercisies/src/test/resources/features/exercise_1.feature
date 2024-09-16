Feature: Item Service

  Scenario: Create an item successfully
    Given Item registration request with name "TestItem"
    When Creating item with the registrationRequest
    Then The item is saved as "TestItem"

  Scenario: Retrieve all items
    Given I have a repository with item "TestItem"
    When I get all items
    Then I get a List with 1 item

  Scenario: Update an item successfully
    Given an item "existingItem" exists
    When I update the item with name "existingItem" to "updatedItem"
    Then the old item should be updated to "updatedItem"

  Scenario: Delete an item successfully
    Given an item "mockItem" exists
    When I delete item "mockItem"
    Then The item is removed from the repository
