/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.controllers;

import com.burabari.workerbee.models.RemoteWorker;
import com.burabari.workerbee.models.dtos.RemoteWorkerDTO;
import com.burabari.workerbee.services.RemoteWorkerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 *
 * @author codebase
 */
@WebMvcTest(RemoteWorkerController.class)
public class RemoteWorkerControllerIT {
    @MockBean
    private RemoteWorkerService service;
    @Autowired
    private MockMvc mockMvc;
    
    private final ObjectMapper mapper = new ObjectMapper();
    
    @Test
    void createWorker_Should_Return_Status_201_Created_Client() throws Exception {
        RemoteWorker worker = new RemoteWorker();
        RemoteWorkerDTO dto = new RemoteWorkerDTO();
        worker.setId(1L);
        dto.setId(1);
        String workerJson = mapper.writeValueAsString(dto);
        Optional<RemoteWorkerDTO> opt = Optional.of(dto);
        
        Mockito.when(service.create(worker)).thenReturn(opt);
        
        mockMvc.perform(post("/api/workers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(workerJson))
                .andExpect(status().isCreated())
                .andExpect(header().string(
                        HttpHeaders.LOCATION, "/api/workers/1"));
    }
    
    @Test
    void createWorker_Without_Data_Should_Return_Status_400() throws Exception {
        RemoteWorker worker = null;
        String workerJson = "";
        
        Mockito.when(service.create(worker)).thenReturn(Optional.empty());
        
        mockMvc.perform(post("/api/workers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(workerJson))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void findWorkerById_Should_Return_Status_200() throws Exception{
        long id = 1L;
        RemoteWorkerDTO workerDto = new RemoteWorkerDTO();
        workerDto.setId(id);
        Optional opt = Optional.of(workerDto);
        String json = mapper.writeValueAsString(workerDto);
        
        Mockito.when(service.findById(id)).thenReturn(opt);
        
        mockMvc.perform(get("/api/workers/"+id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(json))
                .andExpect(status().isOk());
    }
    
    @Test
    void findWorkerById_WIth_NonExisting_Id_Should_Return_Status_404() throws Exception{
        long id = 1L;
        Mockito.when(service.findById(id)).thenReturn(Optional.empty());
        
        mockMvc.perform(get("/api/workers/"+id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    
}
