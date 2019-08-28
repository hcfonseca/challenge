package com.volanty.challenge.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class Car {

    @Id
    private Integer id;
    private String brand;
    private String model;

}
