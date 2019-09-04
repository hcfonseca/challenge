package com.volanty.challenge.controller;

import com.volanty.challenge.dto.InspectionDTO;
import com.volanty.challenge.dto.VisitDTO;
import com.volanty.challenge.entity.Car;
import com.volanty.challenge.entity.Cav;
import com.volanty.challenge.entity.Inspection;
import com.volanty.challenge.entity.Visit;
import com.volanty.challenge.service.CarService;
import com.volanty.challenge.service.CavService;
import com.volanty.challenge.service.InspectionService;
import com.volanty.challenge.service.VisitService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static net.logstash.logback.marker.Markers.append;


@Slf4j
@RestController
public class CavController {

    public CavService cavService;
    public CarService carService;
    public InspectionService inspectionService;
    public VisitService visitService;


    public CavController(CavService cavService, CarService carService, InspectionService inspectionService, VisitService visitService) {
        this.cavService = cavService;
        this.carService = carService;
        this.inspectionService = inspectionService;
        this.visitService = visitService;
    }

    @GetMapping("/v1/cav")
    public ResponseEntity getCavs() {

        final List<Cav> cavs;

        try {
            cavs = cavService.getAllCavs();
        } catch (Exception e) {
            log.error("error while retrieving all cavs.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error while retrieving all cavs");
        }

        log.info("successfully retrieve all cavs.");

        if (cavs == null || cavs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("no cavs found");
        }

        return ResponseEntity.ok(cavs);
    }

    @GetMapping("/v1/availableHours/inspection/cav/{cavId}")
    public ResponseEntity getAvailableHoursToInspection(@PathVariable Integer cavId) {

        final List<Date> availableHours;

        try {
            availableHours = inspectionService.getAvailableHoursByCav(cavId);
        } catch (Exception e) {
            log.error(append("cavId", cavId), "error while retrieving the available hours to inspection", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("error while retrieving the available hours to inspection");
        }

        log.info(append("cavId", cavId), "successfully retrieve the available hours to inspection");

        if (availableHours == null || availableHours.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("there is no time available to inspection");
        }

        Collections.sort(availableHours);
        return ResponseEntity.ok(availableHours);
    }

    @GetMapping("/v1/availableHours/visit/cav/{cavId}")
    public ResponseEntity getAvailableHoursToVisit(@PathVariable Integer cavId) {

        final List<Date> availableHours;

        try {
            availableHours = visitService.getAvailableHoursByCav(cavId);
        } catch (Exception e) {
            log.error(append("cavId", cavId), "error while retrieving the available hours to visit", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("error while retrieving the available hours to visit");
        }

        log.info(append("cavId", cavId), "successfully retrieve the available hours to visit");

        if (availableHours == null || availableHours.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("there is no time available to visit");
        }

        Collections.sort(availableHours);
        return ResponseEntity.ok(availableHours);
    }

    @PostMapping("/v1/cav/{cavId}/inspection")
    public ResponseEntity scheduleInspection(@PathVariable Integer cavId,
        @Valid @RequestBody InspectionDTO inspectionDTO) throws URISyntaxException {

        final Inspection inspection;
        try {

            inspection = inspectionService.scheduleInspection(inspectionDTO, cavId);

            log.info(append("cavId", cavId).and(append("carId", inspectionDTO.getCarId()))
                    .and(append("date", inspectionDTO.getDate())),  "inspection saved with success");

        } catch (NotFoundException e) {

            log.warn(append("cavId", cavId).and(append("carId", inspectionDTO.getCarId()))
                    .and(append("date", inspectionDTO.getDate())),  e.getMessage());

            return ResponseEntity.noContent().build();

        } catch (Exception e) {

            log.error(append("cavId", cavId).and(append("carId", inspectionDTO.getCarId()))
                    .and(append("date", inspectionDTO.getDate())),  e.getMessage(), e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("error while scheduling the inspection.");
        }

        return ResponseEntity.created(new URI("")).body(inspection);
    }

    @PostMapping("/visitv1/cav/{cavId}/visit")
    public ResponseEntity scheduleVisit(@PathVariable Integer cavId,
        @Valid @RequestBody VisitDTO visitDTO) throws URISyntaxException {

        final Visit visit;
        try {

            visit = visitService.scheduleVisit(visitDTO, cavId);

            log.info(append("cavId", cavId).and(append("carId", visitDTO.getCarId()))
                    .and(append("date", visitDTO.getDate())),  "visit saved with success");

        } catch (NotFoundException e) {

            log.warn(append("cavId", cavId).and(append("carId", visitDTO.getCarId()))
                    .and(append("date", visitDTO.getDate())),  e.getMessage());

            return ResponseEntity.noContent().build();

        } catch (Exception e) {

            log.error(append("cavId", cavId).and(append("carId", visitDTO.getCarId()))
                    .and(append("date", visitDTO.getDate())),  e.getMessage(), e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("error while scheduling the visit.");
        }

        return ResponseEntity.created(new URI("")).body(visit);
    }
}
