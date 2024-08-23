package com.javaexercises.__exercisies.service;

import com.javaexercises.__exercisies.DTO.ItemRegistrationRequest;

import com.javaexercises.__exercisies.model.Item;
import com.javaexercises.__exercisies.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    public Optional<Item> getItemByName(String name){
        return itemRepository.findByName(name);
    }

    public Optional<Item> updateItem(String name, ItemRegistrationRequest itemRegistrationRequest){
        Optional<Item> items = itemRepository.findByName(name);
        Item existingItem = items.get();
        existingItem.setName(itemRegistrationRequest.getName());

        Item itemUpdated = itemRepository.save(existingItem);

        return Optional.of(itemUpdated);

    }
}
