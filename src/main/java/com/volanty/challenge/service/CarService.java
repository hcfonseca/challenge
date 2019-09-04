package com.volanty.challenge.service;

import com.volanty.challenge.entity.Car;
import com.volanty.challenge.entity.Cav;
import com.volanty.challenge.repository.CarRepository;
import com.volanty.challenge.repository.CavRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Optional<Car> getCarById(Integer id) throws Exception {
        return carRepository.findById(id);
    }
}

