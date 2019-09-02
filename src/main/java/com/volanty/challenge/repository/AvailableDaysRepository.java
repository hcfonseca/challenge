package com.volanty.challenge.repository;

import com.volanty.challenge.entity.AvailableDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableDaysRepository extends JpaRepository<AvailableDays, String> {

}
