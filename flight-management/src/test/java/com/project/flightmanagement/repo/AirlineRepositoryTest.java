package com.project.flightmanagement.repo;

import com.project.flightmanagement.entity.Airline; // Değişiklik
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AirlineRepositoryTest {

    @Autowired
    private AirlineRepository airlineRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void shouldSaveAirline() {
        Airline airline = Airline.builder()
                .code("test code")
                .description("test description")
                .build();
        airlineRepository.save(airline);

        assertNotNull(airline.getId());
        assertTrue(airline.getId() > 0);
    }

    @Test
    @Order(2)
    public void shouldReturnAirlineWhenValidIdIsGiven() {
        Airline airline = airlineRepository.findById(1L).orElse(null);
        assertNotNull(airline);
        assertEquals(1L, airline.getId());
    }

    @Test
    @Order(3)
    public void shouldReturnAllAirlines() {
        List<Airline> airlineList = airlineRepository.findAll();
        assertNotNull(airlineList);
        assertEquals(1, airlineList.size());
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void shouldUpdateAirline() {
        Airline airline = airlineRepository.findById(1L).orElse(null);
        assertNotNull(airline);
        airline.setCode("updated code");
        airline.setDescription("updated description");
        Airline updatedAirline = airlineRepository.save(airline);
        assertEquals("updated code", updatedAirline.getCode());
        assertEquals("updated description", updatedAirline.getDescription());
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void shouldDeleteAirline() {
        Airline airline = airlineRepository.findById(1L).orElse(null);
        assertNotNull(airline);
        airlineRepository.deleteById(airline.getId());
        assertTrue(airlineRepository.findById(airline.getId()).isEmpty());
    }
}
