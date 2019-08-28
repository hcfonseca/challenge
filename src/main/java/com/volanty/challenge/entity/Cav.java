package com.volanty.challenge.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class Cav {

    @Id
    private Integer id;
    private String name;

}
