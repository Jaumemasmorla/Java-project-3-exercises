package com.javaexercises.__exercisies.test;

import com.javaexercises.__exercisies.exercise_1.DTO.ItemRegistrationRequest;
import com.javaexercises.__exercisies.exercise_1.model.Item;
import com.javaexercises.__exercisies.exercise_1.ItemRepository.ItemRepository;
import com.javaexercises.__exercisies.exercise_1.service.ItemService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class ItemServiceSteps{

    private ItemRegistrationRequest request;
    private Item mockItem;
    private List<Item> items;

    private final ItemRepository itemRepository = mock(ItemRepository.class);
    private final ItemService itemService = new ItemService(itemRepository);

    @Given("Item registration request with name {string}")
    public void thereIsItemRegistrationRequest(String name) {
        request = new ItemRegistrationRequest();
        request.setName(name);
    }

    @When("Creating item with the registrationRequest")
    public void createItem() {
        mockItem = new Item();
        mockItem.setName(request.getName());

        when(itemRepository.save(any(Item.class))).thenReturn(mockItem);

        Item createdItem = itemService.createItem(request);

        items = Collections.singletonList(createdItem);
    }

    @Then("The item is saved as {string}")
    public void itemIsSavedAs(String expectedName) {
        assertThat(items).isNotEmpty();
        assertThat(items.get(0).getName()).isEqualTo(expectedName);
    }

    @Given("I have a repository with item {string}")
    public void repositoryWithItem(String name) {
        mockItem = new Item();
        mockItem.setName(name);

        when(itemRepository.findAll()).thenReturn(Collections.singletonList(mockItem));
    }

    @When("I get all items")
    public void getAllItems() {
        items = itemService.getAllItems();
    }

    @Then("I get a List with {int} item")
    public void createListWithItems(int count) {
        assertThat(items).hasSize(count);
    }

    @Then("the item should have name {string}")
    public void theItemHasName(String name) {
        assertThat(items).isNotEmpty();
        assertThat(items.get(0).getName()).isEqualTo(name);
    }

    @Given("an item {string} exists")
    public void itemExists(String name) {
        mockItem = new Item();
        mockItem.setName(name);
        when(itemRepository.findByName(name)).thenReturn(Optional.of(mockItem));
    }

    @When("I update the item with name {string} to {string}")
    public void updateItem(String name, String newName) {
        Item existingItem = new Item();
        existingItem.setName(name);

        Item updatedItem = new Item();
        updatedItem.setName(newName);

        when(itemRepository.findByName(name)).thenReturn(Optional.of(existingItem));
        when(itemRepository.save(any(Item.class))).thenReturn(updatedItem);

        request = new ItemRegistrationRequest();
        request.setName(newName);

        items = Collections.singletonList(itemService.updateItem(name, request).orElse(null));
    }

    @Then("the old item should be updated to {string}")
    public void itemIsUpdated(String newName) {
        assertThat(items).isNotEmpty();
        assertThat(items.get(0).getName()).isEqualTo(newName);
    }

    @When("I delete item {string}")
    public void deleteItem(String name) {
        when(itemRepository.findByName(name)).thenReturn(Optional.of(mockItem));
        doNothing().when(itemRepository).delete(any(Item.class));

        boolean result = itemService.deleteItem(name);

        assertThat(result).isTrue();
    }

    @Then("The item is removed from the repository")
    public void itemRemovedFromRepository() {
        verify(itemRepository, times(1)).delete(any(Item.class));
    }
}
