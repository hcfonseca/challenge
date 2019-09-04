package com.volanty.challenge.service;

import com.volanty.challenge.dto.InspectionDTO;
import com.volanty.challenge.dto.VisitDTO;
import com.volanty.challenge.entity.Car;
import com.volanty.challenge.entity.Cav;
import com.volanty.challenge.entity.Inspection;
import com.volanty.challenge.entity.Visit;
import com.volanty.challenge.repository.CarRepository;
import com.volanty.challenge.repository.CavRepository;
import com.volanty.challenge.repository.VisitRepository;
import com.volanty.challenge.repository.cache.VisitCacheRepository;
import com.volanty.challenge.utils.ParseUtils;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class VisitService {

    private CavRepository cavRepository;
    private CarRepository carRepository;
    private VisitRepository visitRepository;
    private VisitCacheRepository visitCacheRepository;


    public VisitService(CavRepository cavRepository, CarRepository carRepository, VisitRepository visitRepository,
                        VisitCacheRepository visitCacheRepository) {
        this.cavRepository = cavRepository;
        this.carRepository = carRepository;
        this.visitRepository = visitRepository;
        this.visitCacheRepository = visitCacheRepository;
    }

    public List<Visit> getFutureVisits(Date initialDate) throws Exception {
        return visitRepository.findByTimeGreaterThanEqual(initialDate);
    }

    public void addAvailableTime(String time, Long timeout, TimeUnit timeUnit) {
        visitCacheRepository.insertItem(time, "", timeout, timeUnit);
    }

    public List<Date> getAvailableHoursByCav(Integer cavId) throws ParseException {
        Set<String> keys = visitCacheRepository.getKeys(cavId + "*");
        return ParseUtils.parseKeysToDate(keys);
    }

    public Boolean removeAvailableHour(Integer cavId, Date date) {
        Calendar calendarDate = Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00"));
        calendarDate.setTime(date);

        return visitCacheRepository.deleteItem(ParseUtils.generateKey(calendarDate, cavId));
    }

    public Visit scheduleVisit (VisitDTO visitDTO, Integer cavId) throws Exception {

        final Optional<Cav> cav = cavRepository.findById(cavId);
        if (!cav.isPresent()) {
            throw new NotFoundException("no cav was found to schedule visit");
        }

        final Optional<Car> car = carRepository.findById(visitDTO.getCarId());
        if (!car.isPresent()) {
            throw new NotFoundException("no car was found to schedule visit");
        }

        Long expirationTime = getExpiration(cavId, visitDTO.getDate());

        if (!removeAvailableHour(cavId, visitDTO.getDate())) {
            throw new NotFoundException("no time is available to schedule visit");
        }

        final Visit visit = visitRepository.save(new Visit(visitDTO.getDate(), car.get(), cav.get()));

        if (visit == null) {
            Calendar calendarDate = Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00"));
            calendarDate.setTime(visitDTO.getDate());

            addAvailableTime(ParseUtils.generateKey(calendarDate, cavId), expirationTime, TimeUnit.SECONDS);

            throw new RuntimeException("error while scheduling the visit");
        }

        return visitRepository.save(visit);
    }

    private Long getExpiration (Integer cavId, Date date) {
        Calendar calendarDate = Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00"));
        calendarDate.setTime(date);

        return visitCacheRepository.getExpire(ParseUtils.generateKey(calendarDate, cavId));
    }
}

