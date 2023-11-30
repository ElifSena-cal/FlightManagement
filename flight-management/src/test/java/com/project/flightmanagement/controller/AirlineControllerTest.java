package com.project.flightmanagement.controller;

import com.project.flightmanagement.request.AirlineRequest;
import com.project.flightmanagement.response.AirlineResponse;
import com.project.flightmanagement.service.AirlineService;
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

class AirlineControllerTest {

    @Mock
    private AirlineService airlineService;

    @InjectMocks
    private AirlineController airlineController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcUtils.buildMockMvc(airlineController);
    }

    @Test
    void listAirlines_shouldReturnListOfAirlineResponses() throws Exception {
        List<AirlineResponse> airlineList = Arrays.asList(new AirlineResponse(), new AirlineResponse());
        when(airlineService.getAllAirlines()).thenReturn(airlineList);

        mockMvc.perform(MockMvcRequestBuilders.get("/airlines")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));

        verify(airlineService, times(1)).getAllAirlines();
    }

    @Test
    void createAirline_shouldReturnCreatedAirlineRequest() throws Exception {
        AirlineRequest request = new AirlineRequest();
        AirlineRequest response = new AirlineRequest();
        when(airlineService.createAirline(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/airlines/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(airlineService, times(1)).createAirline(any());
    }

    @Test
    void getAirlineById_existingAirline_shouldReturnAirlineResponse() throws Exception {
        Long airlineId = 1L;
        AirlineResponse response = new AirlineResponse();
        when(airlineService.getAirlineById(airlineId)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/airlines/{airlineId}", airlineId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(airlineService, times(1)).getAirlineById(airlineId);
    }

    @Test
    void updateAirline_shouldReturnUpdatedAirlineRequest() throws Exception {
        AirlineRequest request = new AirlineRequest();
        AirlineRequest response = new AirlineRequest();
        when(airlineService.updateAirline(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.put("/airlines/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(airlineService, times(1)).updateAirline(any());
    }

    @Test
    void deleteAirline_existingAirline_shouldReturnNoContent() throws Exception {
        Long airlineId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/airlines/delete/{airlineId}", airlineId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(airlineService, times(1)).deleteAirline(airlineId);
    }

    @Test
    void listAirlineResponseForDropdown_shouldReturnListOfAirlineResponses() throws Exception {
        List<AirlineResponse> airlineList = Arrays.asList(new AirlineResponse(), new AirlineResponse());
        when(airlineService.getAllAirlines()).thenReturn(airlineList);

        mockMvc.perform(MockMvcRequestBuilders.get("/airlines/airlines-dropdown")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));

        verify(airlineService, times(1)).getAllAirlines();
    }
}
