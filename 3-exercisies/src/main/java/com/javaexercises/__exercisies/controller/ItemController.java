package com.javaexercises.__exercisies.controller;

import com.javaexercises.__exercisies.DTO.ItemRegistrationRequest;
import com.javaexercises.__exercisies.model.Item;
import com.javaexercises.__exercisies.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ItemController {

    private final ItemService itemService;




    @PostMapping("/items")
    public ResponseEntity<Item> createItem(@RequestBody ItemRegistrationRequest itemRegistrationRequest){
        itemService.createItem(itemRegistrationRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/items")
    public ResponseEntity<List<Item>> getAllItems(){
        List<Item> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/items/{name}")
    public ResponseEntity<Optional<Item>> getItemByName(@PathVariable String name){
        Optional<Item> item = itemService.getItemByName(name);
        return ResponseEntity.ok(item);
    }

    @PutMapping("/items/{name}")
    public Optional<ResponseEntity<Item>> updateItem(@PathVariable String name, @RequestBody ItemRegistrationRequest itemRegistrationRequest){
        Optional<Item> updatedItem = itemService.updateItem(name, itemRegistrationRequest);
        return updatedItem.map(item-> ResponseEntity.ok(item));
    }

    @DeleteMapping("/items/{name}")
    public ResponseEntity<Void> deleteItem(@PathVariable String name){
        boolean deletedItem= itemService.deleteItem(name);
        return ResponseEntity.noContent().build();
    }

}
