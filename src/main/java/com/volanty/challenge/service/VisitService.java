package com.volanty.challenge.service;

import com.volanty.challenge.entity.Visit;
import com.volanty.challenge.repository.VisitRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class VisitService {

    private VisitRepository visitRepository;

    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    public List<Visit> getFutureVisits(Date initialDate) throws Exception {
        return visitRepository.findByTimeGreaterThanEqual(initialDate);
    }
}

