package com.volanty.challenge.service;

import com.volanty.challenge.entity.Inspection;
import com.volanty.challenge.repository.InspectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InspectionService {

    private InspectionRepository inspectionRepository;

    public InspectionService(InspectionRepository inspectionRepository) {
        this.inspectionRepository = inspectionRepository;
    }


    public List<Inspection> getAllInspections() throws Exception {
        return inspectionRepository.findAll();
    }
}

