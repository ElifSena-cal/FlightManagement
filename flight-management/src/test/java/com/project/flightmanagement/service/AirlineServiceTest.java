package com.project.flightmanagement.service;

import com.project.flightmanagement.entity.Airline;
import com.project.flightmanagement.exception.EntityNotFoundException;
import com.project.flightmanagement.mapper.AirlineMapper;
import com.project.flightmanagement.repo.AirlineRepository;
import com.project.flightmanagement.request.AirlineRequest;
import com.project.flightmanagement.response.AirlineResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AirlineServiceTest {

    @Mock
    private AirlineRepository airlineRepository;

    @Mock
    private AirlineMapper airlineMapper;

    @InjectMocks
    private AirlineService airlineService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllAirlines_shouldReturnListOfAirlineResponses() {
        when(airlineRepository.findAll()).thenReturn(Arrays.asList(new Airline(), new Airline()));
        when(airlineMapper.airlinesToAirlineResponse(anyList()))
                .thenReturn(Arrays.asList(new AirlineResponse(), new AirlineResponse()));

        List<AirlineResponse> result = airlineService.getAllAirlines();

        assertEquals(2, result.size());
    }

    @Test
    void createAirline_shouldReturnAirlineRequest() {
        AirlineRequest airlineRequest = new AirlineRequest();
        Airline airline = new Airline();
        when(airlineMapper.airlineRequestToAirline(airlineRequest)).thenReturn(airline);
        when(airlineRepository.save(airline)).thenReturn(airline);
        when(airlineMapper.airlineToAirlineRequest(airline)).thenReturn(airlineRequest);

        AirlineRequest result = airlineService.createAirline(airlineRequest);

        assertEquals(airlineRequest, result);
    }

    @Test
    void getAirlineById_existingAirline_shouldReturnAirlineResponse() {
        Long airlineId = 1L;
        Airline airline = new Airline();
        AirlineResponse expectedResponse = new AirlineResponse();
        when(airlineRepository.findById(airlineId)).thenReturn(Optional.of(airline));
        when(airlineMapper.airlineToAirlineResponse(airline)).thenReturn(expectedResponse);

        AirlineResponse result = airlineService.getAirlineById(airlineId);

        assertEquals(expectedResponse, result);
    }

    @Test
    void getAirlineById_nonExistingAirline_shouldThrowEntityNotFoundException() {
        Long airlineId = 1L;
        when(airlineRepository.findById(airlineId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> airlineService.getAirlineById(airlineId));
    }

    @Test
    void updateAirline_existingAirline_shouldReturnUpdatedAirlineRequest() {
        AirlineRequest updatedAirlineRequest = new AirlineRequest();
        updatedAirlineRequest.setId(1L);

        Airline existingAirline = new Airline();
        when(airlineRepository.findById(updatedAirlineRequest.getId())).thenReturn(Optional.of(existingAirline));
        when(airlineRepository.save(existingAirline)).thenReturn(existingAirline);
        when(airlineMapper.airlineToAirlineRequest(existingAirline)).thenReturn(updatedAirlineRequest);

        AirlineRequest result = airlineService.updateAirline(updatedAirlineRequest);

        assertEquals(updatedAirlineRequest, result);
    }

    @Test
    void updateAirline_nonExistingAirline_shouldThrowEntityNotFoundException() {
        AirlineRequest updatedAirlineRequest = new AirlineRequest();
        updatedAirlineRequest.setId(1L);
        when(airlineRepository.findById(updatedAirlineRequest.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> airlineService.updateAirline(updatedAirlineRequest));
    }

    @Test
    void deleteAirline_existingAirline_shouldDeleteAirline() {
        Long airlineId = 1L;
        Airline existingAirline = new Airline();
        when(airlineRepository.findById(airlineId)).thenReturn(Optional.of(existingAirline));

        airlineService.deleteAirline(airlineId);

        verify(airlineRepository, times(1)).deleteById(airlineId);
    }

    @Test
    void deleteAirline_nonExistingAirline_shouldThrowEntityNotFoundException() {
        Long airlineId = 1L;
        when(airlineRepository.findById(airlineId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> airlineService.deleteAirline(airlineId));
    }
}
