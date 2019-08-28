package com.volanty.challenge.repository;

import com.volanty.challenge.entity.Cap;
import com.volanty.challenge.entity.Cav;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CavRepository extends JpaRepository<Cap, Integer> {

}
