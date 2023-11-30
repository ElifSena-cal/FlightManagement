package com.project.flightmanagement.controller;

import com.project.flightmanagement.controller.StationController;
import com.project.flightmanagement.request.StationRequest;
import com.project.flightmanagement.response.StationResponse;
import com.project.flightmanagement.service.StationService;
import com.project.flightmanagement.util.JsonUtils;
import com.project.flightmanagement.util.MockMvcUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StationControllerTest {

    @Mock
    private StationService stationService;

    @InjectMocks
    private StationController stationController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcUtils.buildMockMvc(stationController);
    }

    @Test
    void listStations_shouldReturnListOfStationResponses() throws Exception {
        List<StationResponse> stationList = Arrays.asList(new StationResponse(), new StationResponse());
        when(stationService.getAllStations()).thenReturn(stationList);

        mockMvc.perform(MockMvcRequestBuilders.get("/stations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));

        verify(stationService, times(1)).getAllStations();
    }

    @Test
    void createStation_shouldReturnCreatedStationRequest() throws Exception {
        StationRequest request = new StationRequest();
        StationRequest response = new StationRequest();
        when(stationService.createStation(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/stations/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(stationService, times(1)).createStation(any());
    }

    @Test
    void getStationById_existingStation_shouldReturnStationResponse() throws Exception {
        Long stationId = 1L;
        StationResponse response = new StationResponse();
        when(stationService.getStationById(stationId)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/stations/{stationId}", stationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(stationService, times(1)).getStationById(stationId);
    }

    @Test
    void updateStation_shouldReturnUpdatedStationRequest() throws Exception {
        StationRequest request = new StationRequest();
        StationRequest response = new StationRequest();
        when(stationService.updateStation(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.put("/stations/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(stationService, times(1)).updateStation(any());
    }

    @Test
    void deleteStation_existingStation_shouldReturnNoContent() throws Exception {
        Long stationId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/stations/delete/{stationId}", stationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(stationService, times(1)).deleteStation(stationId);
    }

    @Test
    void listStationResponseForDropdown_shouldReturnListOfStationResponses() throws Exception {
        List<StationResponse> stationList = Arrays.asList(new StationResponse(), new StationResponse());
        when(stationService.getAllStations()).thenReturn(stationList);

        mockMvc.perform(MockMvcRequestBuilders.get("/stations/stations-dropdown")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));

        verify(stationService, times(1)).getAllStations();
    }
}
