package com.project.flightmanagement.repo;

import com.project.flightmanagement.entity.Aircraft;
import com.project.flightmanagement.entity.Airline;
import com.project.flightmanagement.entity.Flight;
import com.project.flightmanagement.entity.Station;
import com.project.flightmanagement.enums.ArrDepEnum;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FlightRepositoryTest {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private StationRepository stationRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void shouldSaveFlight() {
        Airline airline = Airline.builder().code("test airline code").description("test airline description").build();
        airlineRepository.save(airline);

        Aircraft aircraft = Aircraft.builder().code("test aircraft code").description("test aircraft description").build();
        aircraftRepository.save(aircraft);

        Station systemAirport = Station.builder().code("system airport code").description("system airport description").build();
        stationRepository.save(systemAirport);

        Station originStation = Station.builder().code("origin station code").description("origin station description").build();
        stationRepository.save(originStation);

        Flight flight = Flight.builder()
                .airline(airline)
                .flightNo("test flight no")
                .aircraft(aircraft)
                .flightLeg(ArrDepEnum.Arr)
                .flightDate(LocalDateTime.now())
                .systemAirport(systemAirport)
                .originStation(originStation)
                .createTime(LocalDateTime.now())
                .build();

        flightRepository.save(flight);

        assertNotNull(flight.getId());
        assertTrue(flight.getId() > 0);
    }

    @Test
    @Order(2)
    public void shouldReturnFlightWhenValidIdIsGiven() {
        Flight flight = flightRepository.findById(1L).orElse(null);
        assertNotNull(flight);
        assertEquals(1L, flight.getId());
    }

    @Test
    @Order(3)
    public void shouldReturnAllFlights() {
        List<Flight> flightList = flightRepository.findAll();
        assertNotNull(flightList);
        assertEquals(1, flightList.size());
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void shouldUpdateFlight() {
        Flight flight = flightRepository.findById(1L).orElse(null);
        assertNotNull(flight);
        flight.setFlightNo("updated flight no");
        flight.setFlightLeg(ArrDepEnum.Dep);
        Flight updatedFlight = flightRepository.save(flight);
        assertEquals("updated flight no", updatedFlight.getFlightNo());
        assertEquals(ArrDepEnum.Dep, updatedFlight.getFlightLeg());
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void shouldDeleteFlight() {
        Flight flight = flightRepository.findById(1L).orElse(null);
        assertNotNull(flight);
        flightRepository.deleteById(flight.getId());
        assertTrue(flightRepository.findById(flight.getId()).isEmpty());
    }
}


