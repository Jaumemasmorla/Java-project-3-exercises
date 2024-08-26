package com.javaexercises.__exercisies.test;

import com.javaexercises.__exercisies.DTO.ItemRegistrationRequest;
import com.javaexercises.__exercisies.model.Item;
import com.javaexercises.__exercisies.repository.ItemRepository;
import com.javaexercises.__exercisies.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateItem() {
        Item mockItem = new Item();
        mockItem.setName("TestItem");

        when(itemRepository.save(any(Item.class))).thenReturn(mockItem);

        ItemRegistrationRequest request = new ItemRegistrationRequest();
        request.setName("TestItem");

        Item result = itemService.createItem(request);

        assertThat(result.getName()).isEqualTo("TestItem");
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    void testGetAllItems() {
        Item mockItem = new Item();
        mockItem.setName("TestItem");

        when(itemRepository.findAll()).thenReturn(Collections.singletonList(mockItem));

        assertThat(itemService.getAllItems()).hasSize(1);
        assertThat(itemService.getAllItems().get(0).getName()).isEqualTo("TestItem");
    }

    @Test
    void testGetItemByName() {
        Item mockItem = new Item();
        mockItem.setName("TestItem");

        when(itemRepository.findByName("TestItem")).thenReturn(Optional.of(mockItem));

        Optional<Item> result = itemService.getItemByName("TestItem");

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("TestItem");
    }

    @Test
    void testUpdateItem() {
        Item existingItem = new Item();
        existingItem.setName("TestItem");

        Item updatedItem = new Item();
        updatedItem.setName("UpdatedItem");

        when(itemRepository.findByName("TestItem")).thenReturn(Optional.of(existingItem));
        when(itemRepository.save(any(Item.class))).thenReturn(updatedItem);

        ItemRegistrationRequest request = new ItemRegistrationRequest();
        request.setName("UpdatedItem");

        Optional<Item> result = itemService.updateItem("TestItem", request);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("UpdatedItem");
    }

    @Test
    void testDeleteItem() {
        Item mockItem = new Item();
        mockItem.setName("TestItem");

        when(itemRepository.findByName("TestItem")).thenReturn(Optional.of(mockItem));
        doNothing().when(itemRepository).delete(any(Item.class));

        boolean result = itemService.deleteItem("TestItem");

        assertThat(result).isTrue();
        verify(itemRepository, times(1)).delete(any(Item.class));
    }
}
