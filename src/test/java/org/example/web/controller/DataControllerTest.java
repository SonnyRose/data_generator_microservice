package org.example.web.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.model.Data;
import org.example.model.measurementType.MeasurementType;
import org.example.model.test.DataTestOptions;
import org.example.service.interfaces.KafkaDataService;
import org.example.service.interfaces.TestDataService;
import org.example.web.DTO.DataDTO;
import org.example.web.DTO.DataTestOptionsDTO;
import org.example.web.mapper.DataMapper;
import org.example.web.mapper.DataTestOptionsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DataControllerTest {
    MockMvc mockMvc;
    @InjectMocks
    private DataController dataController;
    @Mock
    private  KafkaDataService kafkaDataService;
    @Mock
    private DataTestOptionsMapper dataTestOptionsMapper;
    @Mock
    private TestDataService testDataService;
    @Mock
    private  DataMapper dataMapper;
    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(dataController).build();
    }
    @Test
    public void send_withValidDataDTO_callsKafkaDataService() throws Exception {
        DataDTO dataDTO = new DataDTO();
        dataDTO.setMeasurementType(MeasurementType.TEMPERATURE);
        dataDTO.setTimeStamp(LocalDateTime.now());
        dataDTO.setSensorId(1L);
        dataDTO.setMeasurement(10.0);

        Data dataEntity = new Data();
        when(dataMapper.toEntity(dataDTO)).thenReturn(dataEntity);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/data/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dataDTO))
                )
                .andExpect(status().isOk());
        verify(kafkaDataService, times(1)).send(any());
    }
    @Test
    public void shouldReturn4xxStatusWhenInvalidRequest() throws Exception {
        DataDTO dataDTO = new DataDTO();
       mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/data/sen")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(dataDTO))
                )
                .andExpect(status().is4xxClientError())
                .andReturn();
        verify(kafkaDataService, never()).send(any());
    }
    @Test
    public void send_withValidDataTestOptionsDTO_callsTestDataService() throws Exception {
        DataTestOptionsDTO testOptionDTO = new DataTestOptionsDTO();
        DataTestOptions dataTestOptionsEntity = new DataTestOptions();
        when(dataTestOptionsMapper.toEntity(testOptionDTO)).thenReturn(dataTestOptionsEntity);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/data/test/send")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(testOptionDTO))
                )
                .andExpect(status().isOk());
        verify(testDataService, times(1)).sendMessage(any());
    }
    @Test
    public void shouldReturn4xxStatusWhenInvalidTestRequest() throws Exception {
        DataTestOptionsDTO testOptionDTO = new DataTestOptionsDTO();
        DataTestOptions dataTestOptionsEntity = new DataTestOptions();
        when(dataTestOptionsMapper.toEntity(testOptionDTO)).thenReturn(dataTestOptionsEntity);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/data/test/sen")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(testOptionDTO))
                )
                .andExpect(status().is4xxClientError());
        verify(testDataService, never()).sendMessage(any());
    }
    private String asJsonString(Object object) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(object);
    }
}
