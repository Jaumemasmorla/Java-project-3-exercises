package com.javaexercises.__exercisies.service;

import com.javaexercises.__exercisies.DTO.ItemRegistrationRequest;

import com.javaexercises.__exercisies.model.Item;
import com.javaexercises.__exercisies.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item createItem(ItemRegistrationRequest itemRegistrationRequest) {
        Item item = Item.builder()
                .name(itemRegistrationRequest.getName())
                .build();
        return itemRepository.save(item);
    }
}
