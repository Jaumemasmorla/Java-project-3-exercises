package com.javaexercises.__exercisies.controller;

import com.javaexercises.__exercisies.DTO.ItemRegistrationRequest;
import com.javaexercises.__exercisies.model.Item;
import com.javaexercises.__exercisies.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    

}
