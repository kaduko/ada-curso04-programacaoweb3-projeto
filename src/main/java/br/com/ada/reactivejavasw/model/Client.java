package br.com.ada.reactivejavasw.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(value="clients")
public class Client {

    @Id
    private String id = new ObjectId().toString();

    private String name;
    @Indexed(unique = true, background = true)
    private String email;
    private Integer age;

    public Client(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }
}
