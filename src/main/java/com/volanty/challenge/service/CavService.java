package com.volanty.challenge.service;

import com.volanty.challenge.entity.Cap;
import com.volanty.challenge.entity.Cav;
import com.volanty.challenge.repository.CavRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CavService {

    private CavRepository cavRepository;

    public CavService(CavRepository cavRepository) {
        this.cavRepository = cavRepository;
    }

    public List<Cap> getAllCavs() throws Exception {
        throw new Exception("teste");
        //return cavRepository.findAll();
    }

}

