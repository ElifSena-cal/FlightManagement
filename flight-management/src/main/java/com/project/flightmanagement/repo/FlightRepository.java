package com.project.flightmanagement.repo;

import com.project.flightmanagement.entity.Airline;
import com.project.flightmanagement.entity.Flight;
import com.project.flightmanagement.enums.ArrDepEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface FlightRepository extends JpaRepository<Flight,Long> {

    int countByAirlineAndFlightNoAndFlightLegAndFlightDate(
            Airline airline, String flightNo, ArrDepEnum flightLeg, LocalDateTime flightDate
    );

}
