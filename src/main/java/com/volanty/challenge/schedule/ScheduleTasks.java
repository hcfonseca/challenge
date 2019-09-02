package com.volanty.challenge.schedule;

import com.volanty.challenge.entity.AvailableDays;
import com.volanty.challenge.entity.Cav;
import com.volanty.challenge.entity.Visit;
import com.volanty.challenge.repository.AvailableDaysRepository;
import com.volanty.challenge.service.CavService;
import com.volanty.challenge.service.InspectionService;
import com.volanty.challenge.service.VisitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class ScheduleTasks {

    @Value("${application.scheduleDays}")
    private Integer possibleDays;

    @Value("${application.openCav}")
    private Integer openCav;

    @Value("${application.closeCav}")
    private Integer closeCav;

    private VisitService visitService;

    private InspectionService inspectionService;

    private CavService cavService;

    private AvailableDaysRepository availableDaysRepository;


    public ScheduleTasks(VisitService visitService, InspectionService inspectionService, CavService cavService,
                         AvailableDaysRepository availableDaysRepository) {
        this.visitService = visitService;
        this.inspectionService = inspectionService;
        this.cavService = cavService;
        this.availableDaysRepository = availableDaysRepository;
    }

    //   @Scheduled(cron = "0 0 * * * *")
    @Scheduled(fixedRate = 1000 * 30)
    public void addingAvailableHoursToVisit() {

        try {

            List<String> keys = getAllPossibleTimes();
            List<String> busyHours = getVisitBusyHours();

            for (String key : keys) {

                if (!busyHours.contains(key)) {
                    AvailableDays availableDays = new AvailableDays();
                    availableDays.setKey(key);
                    availableDaysRepository.save(availableDays);
                }

            }

            log.info("Visit hours added successfully");
        } catch (Exception e) {
            log.error("Error adding available hours", e);
        }
    }

    @Scheduled(cron = "0 0 * * * *")
    public void addingAvailableHoursToInspection() {

        try {
            log.info("Inspections hours added successfully");
        } catch (Exception e) {
            log.error("Error adding available hours", e);
        }
    }

    private List<String> getAllPossibleTimes() {

        List<Cav> cavs = new ArrayList<>();
        List<String> keys = new ArrayList<>();

        Calendar currentDate = Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00"));
        Integer currentDay = currentDate.get(Calendar.DATE);
        Integer currentHour = currentDate.get(Calendar.HOUR_OF_DAY);

        try {
            cavs = cavService.getAllCavs();
        } catch (Exception e) {
            log.error("error while retrieving all cavs.", e);
        }

        for (Cav cav : cavs) {

            Calendar date = Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00"));
            date.setTime(new Date());

            for (int day = 0; day < possibleDays; day++) {

                for (int hour = openCav; hour<=closeCav; hour++) {

                    if (!(date.get(Calendar.DATE) == currentDay && currentHour>=hour)) {
                        date.set(Calendar.HOUR_OF_DAY, hour);
                        keys.add(generateKey(date, cav.getId()));
                    }
                }

                date.add(Calendar.DATE, 1);
            }

        }

        return keys;
    }

    private String generateKey(Calendar date, Integer cavId) {
        return Integer.toString(cavId) + "_" + Integer.toString(date.get(Calendar.YEAR)) +
                Integer.toString(date.get(Calendar.MONTH)+1) + Integer.toString(date.get(Calendar.DATE)) + "_" +
                Integer.toString(date.get(Calendar.HOUR_OF_DAY));
    }

    private List<String> getVisitBusyHours() {

        List<String> busyHours = new ArrayList<>();

        try {

            List<Visit> visits = visitService.getFutureVisits(new Date());

            for (Visit visit : visits) {

                Calendar dateVisit = Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00"));
                dateVisit.setTime(visit.getTime());

                busyHours.add(generateKey(dateVisit, visit.getCav().getId()));

            }

        } catch (Exception e) {
            log.error("error while getting all the visits", e);
        }

        return busyHours;
    }

}

