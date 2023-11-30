package com.project.flightmanagement.repo;

import com.project.flightmanagement.entity.Aircraft;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;


@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AircraftRepositoryTest {

    @Autowired
    private AircraftRepository aircraftRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void shouldSaveAircraft() {
        Aircraft aircraft = Aircraft.builder()
                .code("test code")
                .description("test description")
                .build();
        aircraftRepository.save(aircraft);

        assertNotNull(aircraft.getId());
        assertTrue(aircraft.getId() > 0);
    }

    @Test
    @Order(2)
    public void shouldReturnAircraftWhenValidIdIsGiven() {
        Aircraft aircraft = aircraftRepository.findById(1L).orElse(null);
        assertNotNull(aircraft);
        assertEquals(1L, aircraft.getId());
    }

    @Test
    @Order(3)
    public void shouldReturnAllAircraft() {
        List<Aircraft> aircraftList = aircraftRepository.findAll();
        assertNotNull(aircraftList);
        assertEquals(1, aircraftList.size());
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void shouldUpdateAircraft() {
        Aircraft aircraft = aircraftRepository.findById(1L).orElse(null);
        assertNotNull(aircraft);
        aircraft.setCode("updated code");
        aircraft.setDescription("updated description");
        Aircraft updatedAircraft = aircraftRepository.save(aircraft);
        assertEquals("updated code", updatedAircraft.getCode());
        assertEquals("updated description", updatedAircraft.getDescription());
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void shouldDeleteAircraft() {
        Aircraft aircraft = aircraftRepository.findById(1L).orElse(null);
        assertNotNull(aircraft);
        aircraftRepository.deleteById(aircraft.getId());
        assertTrue(aircraftRepository.findById(aircraft.getId()).isEmpty());
    }
}