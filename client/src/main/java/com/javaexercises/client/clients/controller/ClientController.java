package com.javaexercises.client.clients.controller;


import com.javaexercises.__exercisies.exercise_1.DTO.ItemRegistrationRequest;
import com.javaexercises.__exercisies.exercise_1.model.Item;
import com.javaexercises.client.clients.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/items")
    public ResponseEntity<String> createItem(@RequestBody ItemRegistrationRequest itemRegistrationRequest){
        return clientService.createItem(itemRegistrationRequest.getName());

    }

    @GetMapping("/items")
    public ResponseEntity<List<Item>> getAllItems(){
        List<Item> items =clientService.getAllItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/items/{name}")
    public ResponseEntity<Item> getItemByName(@PathVariable String name) {
        Item item = clientService.getItemByName(name);
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PutMapping("/items/{name}")
    public ResponseEntity<String> updateItem(
            @PathVariable String name,
            @RequestBody ItemRegistrationRequest itemRegistrationRequest) {
        return clientService.updateItem(name, itemRegistrationRequest.getName());
    }



    @DeleteMapping("/items/{name}")
    public ResponseEntity<String> deleteItem(@PathVariable String name) {
        ResponseEntity<String> response = clientService.deleteItem(name);
        return response;
    }

    @PostMapping(value = "/palindrome", produces = "text/plain")
    public ResponseEntity<String> checkPalindrome(@RequestBody String word) {
        word = word.trim();


        String result = clientService.checkPalindrome(word);


        if ("OK".equals(result)) {
            return ResponseEntity.ok("OK");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("KO");
        }
    }


    @PostMapping(value = "/validate-brackets", produces = "text/plain")
    public ResponseEntity<String> validateBrackets(@RequestBody String sentence) {
        String result = clientService.validateBrackets(sentence);

        if ("OK".equals(result)) {
            return ResponseEntity.ok("OK");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("KO");
        }
    }

}
