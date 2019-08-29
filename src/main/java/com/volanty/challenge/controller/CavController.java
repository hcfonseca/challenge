package com.volanty.challenge.controller;

import com.volanty.challenge.entity.Cav;
import com.volanty.challenge.service.CavService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
public class CavController {

    public CavService cavService;

    public CavController(CavService cavService) {
        this.cavService = cavService;
    }

    @GetMapping("/v1/cav")
    public ResponseEntity<List<Cav>> getCavs() {

        List<Cav> cavs;
        try {
            cavs = cavService.getAllCavs();
        } catch (Exception e) {
            log.error("error while retrieving all cavs.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        log.info("successfully retrieve all cavs.");

        if (cavs == null || cavs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(cavs);
        }

        return ResponseEntity.ok(cavs);
    }


}
