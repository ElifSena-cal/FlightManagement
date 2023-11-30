package com.project.flightmanagement.repo;

import com.project.flightmanagement.entity.Station;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StationRepository extends JpaRepository<Station,Long> {

}
