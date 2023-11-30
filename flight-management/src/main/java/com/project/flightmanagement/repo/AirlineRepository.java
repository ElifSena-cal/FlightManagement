package com.project.flightmanagement.repo;

import com.project.flightmanagement.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirlineRepository  extends JpaRepository<Airline,Long> {

}
