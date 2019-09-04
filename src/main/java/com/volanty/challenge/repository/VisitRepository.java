package com.volanty.challenge.repository;

import com.volanty.challenge.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Integer> {

    List<Visit> findByTimeGreaterThanEqual(Date startDate);

}
