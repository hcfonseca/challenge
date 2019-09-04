package com.volanty.challenge.repository;

import com.volanty.challenge.entity.Inspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InspectionRepository extends JpaRepository<Inspection, Integer> {

    List<Inspection> findByTimeGreaterThanEqual(Date startDate);

}
