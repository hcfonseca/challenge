package com.volanty.challenge.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date time;
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
    @ManyToOne
    @JoinColumn(name = "cav_id")
    private Cav cav;

    public Visit(Date time, Car car, Cav cav) {
        this.time = time;
        this.car = car;
        this.cav = cav;
    }
}
