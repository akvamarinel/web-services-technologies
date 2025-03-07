package org.example.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
public class User {
    private int id;
    private String name;
    private int age;
    private String city;
    private double averageScore;


}
