package com.project.flightmanagement.controller;

import com.project.flightmanagement.request.FlightRequest;
import com.project.flightmanagement.response.FlightResponse;
import com.project.flightmanagement.service.FlightService;
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

class FlightControllerTest {

    @Mock
    private FlightService flightService;

    @InjectMocks
    private FlightController flightController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcUtils.buildMockMvc(flightController);
    }

    @Test
    void listFlights_shouldReturnListOfFlightResponses() throws Exception {
        List<FlightResponse> flightList = Arrays.asList(new FlightResponse(), new FlightResponse());
        when(flightService.getAllFlights()).thenReturn(flightList);

        mockMvc.perform(MockMvcRequestBuilders.get("/flights")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));

        verify(flightService, times(1)).getAllFlights();
    }

    @Test
    void createFlight_shouldReturnCreatedFlightRequest() throws Exception {
        FlightRequest request = new FlightRequest();
        FlightRequest response = new FlightRequest();
        when(flightService.createFlight(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/flights/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(flightService, times(1)).createFlight(any());
    }

    @Test
    void getFlightById_existingFlight_shouldReturnFlightResponse() throws Exception {
        Long flightId = 1L;
        FlightResponse response = new FlightResponse();
        when(flightService.getFlightById(flightId)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/flights/{flightId}", flightId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(flightService, times(1)).getFlightById(flightId);
    }

    @Test
    void updateFlight_shouldReturnUpdatedFlightRequest() throws Exception {
        FlightRequest request = new FlightRequest();
        FlightRequest response = new FlightRequest();
        when(flightService.updateFlight(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.put("/flights/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(flightService, times(1)).updateFlight(any());
    }

    @Test
    void deleteFlight_existingFlight_shouldReturnNoContent() throws Exception {
        Long flightId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/flights/delete/{flightId}", flightId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(flightService, times(1)).deleteFlight(flightId);
    }
}
