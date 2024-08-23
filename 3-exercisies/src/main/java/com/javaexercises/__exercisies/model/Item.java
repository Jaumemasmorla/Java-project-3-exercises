package com.javaexercises.__exercisies.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="items")
public class Item {
    @Id
    private ObjectId id;

    private String name;
}
