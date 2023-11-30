package com.project.flightmanagement.service;

import com.project.flightmanagement.entity.Flight;
import com.project.flightmanagement.exception.EntityNotFoundException;
import com.project.flightmanagement.mapper.AircraftMapper;
import com.project.flightmanagement.mapper.AirlineMapper;
import com.project.flightmanagement.mapper.FlightMapper;
import com.project.flightmanagement.mapper.StationMapper;
import com.project.flightmanagement.repo.FlightRepository;
import com.project.flightmanagement.request.FlightRequest;
import com.project.flightmanagement.response.AircraftResponse;
import com.project.flightmanagement.response.AirlineResponse;
import com.project.flightmanagement.response.FlightResponse;
import com.project.flightmanagement.response.StationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private AircraftService aircraftService;

    @Mock
    private AirlineService airlineService;

    @Mock
    private StationService stationService;

    @Mock
    private FlightMapper flightMapper;

    @InjectMocks
    private FlightService flightService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllFlights_shouldReturnListOfFlightResponses() {
        when(flightRepository.findAll()).thenReturn(Arrays.asList(new Flight(), new Flight()));
        when(flightMapper.flightsToFlightResponse(anyList()))
                .thenReturn(Arrays.asList(new FlightResponse(), new FlightResponse()));

        List<FlightResponse> result = flightService.getAllFlights();

        assertEquals(2, result.size());
    }

    @Test
    void createFlight_shouldReturnFlightRequest() {
        FlightRequest flightRequest = new FlightRequest();
        AirlineResponse airlineResponse = new AirlineResponse();
        AircraftResponse aircraftResponse = new AircraftResponse();
        StationResponse originStation = new StationResponse();
        StationResponse systemAirport = new StationResponse();

        when(airlineService.getAirlineById(any())).thenReturn(airlineResponse);
        when(aircraftService.getAircraftById(any())).thenReturn(aircraftResponse);
        when(stationService.getStationById(any())).thenReturn(originStation).thenReturn(systemAirport);

        Flight flight = new Flight();
        when(flightMapper.flightRequestToFlight(flightRequest)).thenReturn(flight);
        when(flightRepository.save(flight)).thenReturn(flight);
        when(flightMapper.flightToFlightRequest(flight)).thenReturn(flightRequest);

        FlightRequest result = flightService.createFlight(flightRequest);

        assertEquals(flightRequest, result);
    }

    @Test
    void getFlightById_existingFlight_shouldReturnFlightResponse() {
        Long flightId = 1L;
        Flight flight = new Flight();
        FlightResponse expectedResponse = new FlightResponse();
        when(flightRepository.findById(flightId)).thenReturn(Optional.of(flight));
        when(flightMapper.flightToFlightResponse(flight)).thenReturn(expectedResponse);

        FlightResponse result = flightService.getFlightById(flightId);

        assertEquals(expectedResponse, result);
    }

    @Test
    void getFlightById_nonExistingFlight_shouldThrowEntityNotFoundException() {
        Long flightId = 1L;
        when(flightRepository.findById(flightId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> flightService.getFlightById(flightId));
    }

    @Test
    void updateFlight_existingFlight_shouldReturnUpdatedFlightRequest() {
        FlightRequest updatedFlightRequest = new FlightRequest();
        updatedFlightRequest.setId(1L);
        AirlineResponse airlineResponse = new AirlineResponse();
        AircraftResponse aircraftResponse = new AircraftResponse();
        StationResponse originStation = new StationResponse();
        StationResponse systemAirport = new StationResponse();

        when(airlineService.getAirlineById(any())).thenReturn(airlineResponse);
        when(aircraftService.getAircraftById(any())).thenReturn(aircraftResponse);
        when(stationService.getStationById(any())).thenReturn(originStation).thenReturn(systemAirport);

        Flight existingFlight = new Flight();
        when(flightRepository.findById(updatedFlightRequest.getId())).thenReturn(Optional.of(existingFlight));
        when(flightRepository.save(existingFlight)).thenReturn(existingFlight);
        when(flightMapper.flightToFlightRequest(existingFlight)).thenReturn(updatedFlightRequest);

        FlightRequest result = flightService.updateFlight(updatedFlightRequest);

        assertEquals(updatedFlightRequest, result);
    }

    @Test
    void updateFlight_nonExistingFlight_shouldThrowEntityNotFoundException() {
        FlightRequest updatedFlightRequest = new FlightRequest();
        updatedFlightRequest.setId(1L);
        when(flightRepository.findById(updatedFlightRequest.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> flightService.updateFlight(updatedFlightRequest));
    }

    @Test
    void deleteFlight_existingFlight_shouldDeleteFlight() {
        Long flightId = 1L;
        Flight existingFlight = new Flight();
        when(flightRepository.findById(flightId)).thenReturn(Optional.of(existingFlight));

        flightService.deleteFlight(flightId);

        verify(flightRepository, times(1)).deleteById(flightId);
    }

    @Test
    void deleteFlight_nonExistingFlight_shouldThrowEntityNotFoundException() {
        Long flightId = 1L;
        when(flightRepository.findById(flightId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> flightService.deleteFlight(flightId));
    }
}
