package com.flightmanagementconsumer.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collation = "User")
@Getter
@Setter
public class User {
    @Id
    private String id;
    private String userName;

}

