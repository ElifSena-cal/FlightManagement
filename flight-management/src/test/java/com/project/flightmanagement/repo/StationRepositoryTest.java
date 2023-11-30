package com.project.flightmanagement.repo;

import com.project.flightmanagement.entity.Station;
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
public class StationRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void shouldSaveStation() {
        Station station = Station.builder()
                .code("test code")
                .description("test description")
                .build();
        stationRepository.save(station);

        assertNotNull(station.getId());
        assertTrue(station.getId() > 0);
    }

    @Test
    @Order(2)
    public void shouldReturnStationWhenValidIdIsGiven() {
        Station station = stationRepository.findById(1L).orElse(null);
        assertNotNull(station);
        assertEquals(1L, station.getId());
    }

    @Test
    @Order(3)
    public void shouldReturnAllStations() {
        List<Station> stationList = stationRepository.findAll();
        assertNotNull(stationList);
        assertEquals(1, stationList.size());
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void shouldUpdateStation() {
        Station station = stationRepository.findById(1L).orElse(null);
        assertNotNull(station);
        station.setCode("updated code");
        station.setDescription("updated name");
        Station updatedStation = stationRepository.save(station);
        assertEquals("updated code", updatedStation.getCode());
        assertEquals("updated name", updatedStation.getDescription());
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void shouldDeleteStation() {
        Station station = stationRepository.findById(1L).orElse(null);
        assertNotNull(station);
        stationRepository.deleteById(station.getId());
        assertTrue(stationRepository.findById(station.getId()).isEmpty());
    }
}
