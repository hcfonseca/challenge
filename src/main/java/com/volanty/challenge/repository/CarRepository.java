package com.volanty.challenge.repository;

import com.volanty.challenge.entity.Car;
import com.volanty.challenge.entity.Cav;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

}
