package com.volanty.challenge.service;

import com.volanty.challenge.entity.Cav;
import com.volanty.challenge.repository.CavRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CavService {

    private CavRepository cavRepository;

    public CavService(CavRepository cavRepository) {
        this.cavRepository = cavRepository;
    }

    public List<Cav> getAllCavs() throws Exception {
        return cavRepository.findAll();
    }

}

