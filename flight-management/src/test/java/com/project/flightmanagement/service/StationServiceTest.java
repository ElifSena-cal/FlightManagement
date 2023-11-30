package com.project.flightmanagement.service;

import com.project.flightmanagement.entity.Station;
import com.project.flightmanagement.exception.EntityNotFoundException;
import com.project.flightmanagement.mapper.StationMapper;
import com.project.flightmanagement.repo.StationRepository;
import com.project.flightmanagement.request.StationRequest;
import com.project.flightmanagement.response.StationResponse;
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

class StationServiceTest {

    @Mock
    private StationRepository stationRepository;

    @Mock
    private StationMapper stationMapper;

    @InjectMocks
    private StationService stationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllStations_shouldReturnListOfStationResponses() {

        when(stationRepository.findAll()).thenReturn(Arrays.asList(new Station(), new Station()));
        when(stationMapper.stationsToStationResponse(anyList()))
                .thenReturn(Arrays.asList(new StationResponse(), new StationResponse()));


        List<StationResponse> result = stationService.getAllStations();


        assertEquals(2, result.size());
    }

    @Test
    void createStation_shouldReturnStationRequest() {

        StationRequest stationRequest = new StationRequest();
        Station station = new Station();
        when(stationMapper.stationRequestToStation(stationRequest)).thenReturn(station);
        when(stationRepository.save(station)).thenReturn(station);
        when(stationMapper.stationToStationRequest(station)).thenReturn(stationRequest);


        StationRequest result = stationService.createStation(stationRequest);


        assertEquals(stationRequest, result);
    }

    @Test
    void getStationById_existingStation_shouldReturnStationResponse() {

        Long stationId = 1L;
        Station station = new Station();
        StationResponse expectedResponse = new StationResponse();
        when(stationRepository.findById(stationId)).thenReturn(Optional.of(station));
        when(stationMapper.stationToStationResponse(station)).thenReturn(expectedResponse);

        StationResponse result = stationService.getStationById(stationId);


        assertEquals(expectedResponse, result);
    }

    @Test
    void getStationById_nonExistingStation_shouldThrowEntityNotFoundException() {

        Long stationId = 1L;
        when(stationRepository.findById(stationId)).thenReturn(Optional.empty());


        assertThrows(EntityNotFoundException.class, () -> stationService.getStationById(stationId));
    }

    @Test
    void updateStation_existingStation_shouldReturnUpdatedStationRequest() {

        StationRequest updatedStationRequest = new StationRequest();
        updatedStationRequest.setId(1L);

        Station existingStation = new Station();
        when(stationRepository.findById(updatedStationRequest.getId())).thenReturn(Optional.of(existingStation));
        when(stationRepository.save(existingStation)).thenReturn(existingStation);
        when(stationMapper.stationToStationRequest(existingStation)).thenReturn(updatedStationRequest);


        StationRequest result = stationService.updateStation(updatedStationRequest);


        assertEquals(updatedStationRequest, result);
    }

    @Test
    void updateStation_nonExistingStation_shouldThrowEntityNotFoundException() {

        StationRequest updatedStationRequest = new StationRequest();
        updatedStationRequest.setId(1L);

        when(stationRepository.findById(updatedStationRequest.getId())).thenReturn(Optional.empty());


        assertThrows(EntityNotFoundException.class, () -> stationService.updateStation(updatedStationRequest));
    }

    @Test
    void deleteStation_existingStation_shouldDeleteStation() {

        Long stationId = 1L;
        Station existingStation = new Station();
        when(stationRepository.findById(stationId)).thenReturn(Optional.of(existingStation));


        stationService.deleteStation(stationId);


        verify(stationRepository, times(1)).deleteById(stationId);
    }

    @Test
    void deleteStation_nonExistingStation_shouldThrowEntityNotFoundException() {

        Long stationId = 1L;
        when(stationRepository.findById(stationId)).thenReturn(Optional.empty());


        assertThrows(EntityNotFoundException.class, () -> stationService.deleteStation(stationId));
    }
}
