package com.volanty.challenge.repository;

import com.volanty.challenge.entity.Inspection;
import com.volanty.challenge.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InspectionRepository extends JpaRepository<Inspection, Integer> {

}
