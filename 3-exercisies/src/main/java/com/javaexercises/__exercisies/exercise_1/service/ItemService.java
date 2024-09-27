package com.javaexercises.__exercisies.exercise_1.service;

import com.javaexercises.__exercisies.exercise_1.DTO.ItemRegistrationRequest;

import com.javaexercises.__exercisies.exercise_1.model.Item;
import com.javaexercises.__exercisies.exercise_1.ItemRepository.ItemRepository;
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



    public boolean deleteItem(String name){
        Optional<Item> items = itemRepository.findByName(name);
        if (items.isEmpty()){
            return false;
        }
        itemRepository.delete(items.get());

        return true;
    }
}
