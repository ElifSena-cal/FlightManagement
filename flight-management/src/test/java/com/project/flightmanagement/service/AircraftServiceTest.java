package com.project.flightmanagement.service;

import com.project.flightmanagement.entity.Aircraft;
import com.project.flightmanagement.exception.EntityNotFoundException;
import com.project.flightmanagement.mapper.AircraftMapper;
import com.project.flightmanagement.repo.AircraftRepository;
import com.project.flightmanagement.request.AircraftRequest;
import com.project.flightmanagement.response.AircraftResponse;
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

class AircraftServiceTest {

    @Mock
    private AircraftRepository aircraftRepository;

    @Mock
    private AircraftMapper aircraftMapper;

    @InjectMocks
    private AircraftService aircraftService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllAircrafts_shouldReturnListOfAircraftResponses() {
        when(aircraftRepository.findAll()).thenReturn(Arrays.asList(new Aircraft(), new Aircraft()));
        when(aircraftMapper.aircraftToAircraftResponse(anyList()))
                .thenReturn(Arrays.asList(new AircraftResponse(), new AircraftResponse()));

        List<AircraftResponse> result = aircraftService.getAllAircrafts();

        assertEquals(2, result.size());
    }

    @Test
    void createAircraft_shouldReturnAircraftRequest() {
        AircraftRequest aircraftRequest = new AircraftRequest();
        Aircraft aircraft = new Aircraft();
        when(aircraftMapper.aircraftRequestToAircraft(aircraftRequest)).thenReturn(aircraft);
        when(aircraftRepository.save(aircraft)).thenReturn(aircraft);
        when(aircraftMapper.aircraftToAircraftRequest(aircraft)).thenReturn(aircraftRequest);

        AircraftRequest result = aircraftService.createAircraft(aircraftRequest);

        assertEquals(aircraftRequest, result);
    }

    @Test
    void getAircraftById_existingAircraft_shouldReturnAircraftResponse() {
        Long aircraftId = 1L;
        Aircraft aircraft = new Aircraft();
        AircraftResponse expectedResponse = new AircraftResponse();
        when(aircraftRepository.findById(aircraftId)).thenReturn(Optional.of(aircraft));
        when(aircraftMapper.aircraftToAircraftResponse(aircraft)).thenReturn(expectedResponse);

        AircraftResponse result = aircraftService.getAircraftById(aircraftId);

        assertEquals(expectedResponse, result);
    }

    @Test
    void getAircraftById_nonExistingAircraft_shouldThrowEntityNotFoundException() {
        Long aircraftId = 1L;
        when(aircraftRepository.findById(aircraftId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> aircraftService.getAircraftById(aircraftId));
    }

    @Test
    void updateAircraft_existingAircraft_shouldReturnUpdatedAircraftRequest() {
        AircraftRequest updatedAircraftRequest = new AircraftRequest();
        updatedAircraftRequest.setId(1L);

        Aircraft existingAircraft = new Aircraft();
        when(aircraftRepository.findById(updatedAircraftRequest.getId())).thenReturn(Optional.of(existingAircraft));
        when(aircraftRepository.save(existingAircraft)).thenReturn(existingAircraft);
        when(aircraftMapper.aircraftToAircraftRequest(existingAircraft)).thenReturn(updatedAircraftRequest);

        AircraftRequest result = aircraftService.updateAircraft(updatedAircraftRequest);

        assertEquals(updatedAircraftRequest, result);
    }

    @Test
    void updateAircraft_nonExistingAircraft_shouldThrowEntityNotFoundException() {
        AircraftRequest updatedAircraftRequest = new AircraftRequest();
        updatedAircraftRequest.setId(1L);
        when(aircraftRepository.findById(updatedAircraftRequest.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> aircraftService.updateAircraft(updatedAircraftRequest));
    }

    @Test
    void deleteAircraft_existingAircraft_shouldDeleteAircraft() {
        Long aircraftId = 1L;
        Aircraft existingAircraft = new Aircraft();
        when(aircraftRepository.findById(aircraftId)).thenReturn(Optional.of(existingAircraft));

        aircraftService.deleteAircraft(aircraftId);

        verify(aircraftRepository, times(1)).deleteById(aircraftId);
    }

    @Test
    void deleteAircraft_nonExistingAircraft_shouldThrowEntityNotFoundException() {
        Long aircraftId = 1L;
        when(aircraftRepository.findById(aircraftId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> aircraftService.deleteAircraft(aircraftId));
    }
}
