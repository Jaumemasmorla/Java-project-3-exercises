package com.javaexercises.__exercisies.exercise_1.ItemRepository;

import com.javaexercises.__exercisies.exercise_1.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
    Optional<Item> findByName(String name);
}
