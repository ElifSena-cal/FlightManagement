package com.project.flightmanagement.controller;

import com.project.flightmanagement.request.AircraftRequest;
import com.project.flightmanagement.response.AircraftResponse;
import com.project.flightmanagement.service.AircraftService;
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

class AircraftControllerTest {

    @Mock
    private AircraftService aircraftService;

    @InjectMocks
    private AircraftController aircraftController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcUtils.buildMockMvc(aircraftController);
    }

    @Test
    void listAircrafts_shouldReturnListOfAircraftResponses() throws Exception {
        List<AircraftResponse> aircraftList = Arrays.asList(new AircraftResponse(), new AircraftResponse());
        when(aircraftService.getAllAircrafts()).thenReturn(aircraftList);

        mockMvc.perform(MockMvcRequestBuilders.get("/aircrafts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));

        verify(aircraftService, times(1)).getAllAircrafts();
    }

    @Test
    void createAircraft_shouldReturnCreatedAircraftRequest() throws Exception {
        AircraftRequest request = new AircraftRequest();
        AircraftRequest response = new AircraftRequest();
        when(aircraftService.createAircraft(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/aircrafts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(aircraftService, times(1)).createAircraft(any());
    }

    @Test
    void getAircraftById_existingAircraft_shouldReturnAircraftResponse() throws Exception {
        Long aircraftId = 1L;
        AircraftResponse response = new AircraftResponse();
        when(aircraftService.getAircraftById(aircraftId)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/aircrafts/{aircraftId}", aircraftId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(aircraftService, times(1)).getAircraftById(aircraftId);
    }

    @Test
    void updateAircraft_shouldReturnUpdatedAircraftRequest() throws Exception {
        AircraftRequest request = new AircraftRequest();
        AircraftRequest response = new AircraftRequest();
        when(aircraftService.updateAircraft(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.put("/aircrafts/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());


        verify(aircraftService, times(1)).updateAircraft(any());
    }

    @Test
    void deleteAircraft_existingAircraft_shouldReturnNoContent() throws Exception {
        Long aircraftId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/aircrafts/delete/{aircraftId}", aircraftId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(aircraftService, times(1)).deleteAircraft(aircraftId);
    }

    @Test
    void listAircraftResponseForDropdown_shouldReturnListOfAircraftResponses() throws Exception {
        List<AircraftResponse> aircraftList = Arrays.asList(new AircraftResponse(), new AircraftResponse());
        when(aircraftService.getAllAircrafts()).thenReturn(aircraftList);

        mockMvc.perform(MockMvcRequestBuilders.get("/aircrafts/aircrafts-dropdown")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));

        verify(aircraftService, times(1)).getAllAircrafts();
    }
}
