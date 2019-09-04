package com.volanty.challenge.schedule;

import com.volanty.challenge.entity.Cav;
import com.volanty.challenge.entity.Inspection;
import com.volanty.challenge.entity.Visit;
import com.volanty.challenge.service.CavService;
import com.volanty.challenge.service.InspectionService;
import com.volanty.challenge.service.VisitService;
import com.volanty.challenge.utils.ParseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

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

    public ScheduleTasks(VisitService visitService, InspectionService inspectionService, CavService cavService) {
        this.visitService = visitService;
        this.inspectionService = inspectionService;
        this.cavService = cavService;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void addingAvailableHoursToVisit() {

        try {

            Map<String, Long> keys = getAllPossibleTimes();
            List<String> busyHours = getVisitBusyHours();

            for (String key : keys.keySet()) {

                if (!busyHours.contains(key)) {
                    visitService.addAvailableTime(key, keys.get(key), TimeUnit.HOURS);
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

            Map<String, Long> keys = getAllPossibleTimes();
            List<String> busyHours = getInspectionBusyHours();

            for (String key : keys.keySet()) {

                if (!busyHours.contains(key)) {
                    inspectionService.addAvailableTime(key, keys.get(key), TimeUnit.HOURS);
                }

            }

            log.info("Inspections hours added successfully");
        } catch (Exception e) {
            log.error("Error adding available hours", e);
        }
    }

    private Map<String, Long> getAllPossibleTimes() {

        List<Cav> cavs = new ArrayList<>();
        Map<String, Long> keys = new HashMap<>();

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

            Long ttl = 1L;
            for (int day = 0; day < possibleDays; day++) {

                for (int hour = openCav; hour<=closeCav; hour++) {

                    if (!(date.get(Calendar.DATE) == currentDay && currentHour>=hour)) {
                        date.set(Calendar.HOUR_OF_DAY, hour);
                        keys.put(ParseUtils.generateKey(date, cav.getId()), ttl);
                        ttl++;
                    }
                }

                ttl = ttl + ((24 - closeCav) + openCav);
                date.add(Calendar.DATE, 1);
            }

        }

        return keys;
    }

    private List<String> getVisitBusyHours() {

        List<String> busyHours = new ArrayList<>();

        try {

            List<Visit> visits = visitService.getFutureVisits(new Date());

            for (Visit visit : visits) {

                Calendar dateVisit = Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00"));
                dateVisit.setTime(visit.getTime());

                busyHours.add(ParseUtils.generateKey(dateVisit, visit.getCav().getId()));

            }

        } catch (Exception e) {
            log.error("error while getting all the visits", e);
        }

        return busyHours;
    }

    private List<String> getInspectionBusyHours() {

        List<String> busyHours = new ArrayList<>();

        try {

            List<Inspection> inspections = inspectionService.getFutureVisits(new Date());

            for (Inspection inspection : inspections) {

                Calendar dateInspection = Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00"));
                dateInspection.setTime(inspection.getTime());

                busyHours.add(ParseUtils.generateKey(dateInspection, inspection.getCav().getId()));

            }

        } catch (Exception e) {
            log.error("error while getting all the inspections", e);
        }

        return busyHours;
    }


}

