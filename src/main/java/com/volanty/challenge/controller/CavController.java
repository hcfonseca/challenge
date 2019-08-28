package com.volanty.challenge.controller;

import com.volanty.challenge.entity.Cap;
import com.volanty.challenge.entity.Cav;
import com.volanty.challenge.service.CavService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static net.logstash.logback.marker.Markers.append;


@Slf4j
@RestController
public class CavController {

    public CavService cavService;

    public CavController(CavService cavService) {
        this.cavService = cavService;
    }

    @GetMapping("/v1/cav")
    public ResponseEntity<List<Cap>> getCavs() {

        List<Cap> cavs;
        try {
            cavs = cavService.getAllCavs();
        } catch (Exception e) {
            log.error("error while retrieving all cavs.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        log.info("successfully retrieve all cavs.");
        return ResponseEntity.ok(cavs);
    }

   /* @GetMapping("/v1/cav/{cavId}")
    public ResponseEntity<List<Cav>> getCavsById() {

        List<Cav> cavs = new ArrayList<>();
        try {
            cavs = cavService.getAllCavs();
        } catch (Exception e) {
            log.error("An error occurred to get all the cavs.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        log.info("Request to recovery all the cavs.");
        return ResponseEntity.ok(cavs);
    }*/
}
