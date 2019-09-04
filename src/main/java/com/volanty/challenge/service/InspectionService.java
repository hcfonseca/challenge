package com.volanty.challenge.service;

import com.volanty.challenge.dto.InspectionDTO;
import com.volanty.challenge.entity.Car;
import com.volanty.challenge.entity.Cav;
import com.volanty.challenge.entity.Inspection;
import com.volanty.challenge.repository.CarRepository;
import com.volanty.challenge.repository.CavRepository;
import com.volanty.challenge.repository.InspectionRepository;
import com.volanty.challenge.repository.cache.InspectionCacheRepository;
import com.volanty.challenge.utils.ParseUtils;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class InspectionService {

    private CavRepository cavRepository;
    private CarRepository carRepository;
    private InspectionRepository inspectionRepository;
    private InspectionCacheRepository inspectionCacheRepository;

    public InspectionService(CavRepository cavRepository, CarRepository carRepository, InspectionRepository
            inspectionRepository, InspectionCacheRepository inspectionCacheRepository) {
        this.cavRepository = cavRepository;
        this.carRepository = carRepository;
        this.inspectionRepository = inspectionRepository;
        this.inspectionCacheRepository = inspectionCacheRepository;
    }

    public List<Inspection> getFutureVisits(Date initialDate) throws Exception {
        return inspectionRepository.findByTimeGreaterThanEqual(initialDate);
    }

    public void addAvailableTime(String key, Long timeout, TimeUnit timeUnit) {
        inspectionCacheRepository.insertItem(key, "", timeout, timeUnit);
    }

    public List<String> getAvailableHoursByCav(Integer cavId) throws ParseException {
        Set<String> keys = inspectionCacheRepository.getKeys(cavId + "*");
        return ParseUtils.parseKeysToDateString(keys);
    }

    public Boolean removeAvailableHour(Integer cavId, Date date) {
        Calendar calendarDate = Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00"));
        calendarDate.setTime(date);

        return inspectionCacheRepository.deleteItem(ParseUtils.generateKey(calendarDate, cavId));
    }

    public Inspection scheduleInspection (InspectionDTO inspectionDTO, Integer cavId) throws Exception {

        final Optional<Cav> cav = cavRepository.findById(cavId);
        if (!cav.isPresent()) {
            throw new NotFoundException("no cav was found to schedule inspection");
        }

        final Optional<Car> car = carRepository.findById(inspectionDTO.getCarId());
        if (!car.isPresent()) {
            throw new NotFoundException("no car was found to schedule inspection");
        }

        Long expirationTime = getExpiration(cavId, inspectionDTO.getDate());

        if (!removeAvailableHour(cavId, inspectionDTO.getDate())) {
            throw new NotFoundException("no time is available to schedule inspection");
        }

        final Inspection inspection = inspectionRepository.save(new Inspection(inspectionDTO.getDate(), car.get(), cav.get()));;

        if (inspection == null) {
            Calendar calendarDate = Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00"));
            calendarDate.setTime(inspectionDTO.getDate());

            addAvailableTime(ParseUtils.generateKey(calendarDate, cavId), expirationTime, TimeUnit.SECONDS);

            throw new RuntimeException("error while scheduling the inspection");
        }

        return inspection;
    }

    private Long getExpiration (Integer cavId, Date date) {
        Calendar calendarDate = Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00"));
        calendarDate.setTime(date);

        return inspectionCacheRepository.getExpire(ParseUtils.generateKey(calendarDate, cavId));
    }

}

