package org.example.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.example.model.Data;
import org.example.model.test.DataTestOptions;
import org.example.service.interfaces.KafkaDataService;
import org.example.service.interfaces.TestDataService;
import org.example.web.DTO.DataDTO;
import org.example.web.DTO.DataTestOptionsDTO;
import org.example.web.mapper.DataMapper;
import org.example.web.mapper.DataTestOptionsMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/data")
@RequiredArgsConstructor
public class DataController {
    private final KafkaDataService kafkaDataService;
    private final TestDataService testDataService;
    private final DataMapper dataMapper;
    private final DataTestOptionsMapper dataTestOptionsMapper;

    @PostMapping("/send")
    public void send(@RequestBody DataDTO dataDTO) {
        Data data = dataMapper.toEntity(dataDTO);
        // перетворює з DataDTO в Data
        kafkaDataService.send(data);
    }
    @PostMapping("/test/send")
    public void testSend(@RequestBody DataTestOptionsDTO testOptionDTO) {
        DataTestOptions dataTestOptions = dataTestOptionsMapper.toEntity(testOptionDTO);
        testDataService.sendMessage(dataTestOptions);
    }
}
