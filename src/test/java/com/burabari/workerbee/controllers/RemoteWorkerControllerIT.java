/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.controllers;

import com.burabari.workerbee.models.RemoteWorker;
import com.burabari.workerbee.models.dtos.RemoteWorkerDTO;
import com.burabari.workerbee.models.enums.UserType;
import com.burabari.workerbee.services.RemoteWorkerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    
    
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
    
    @Test
    void getPage_Should_Return_Status_200() throws Exception{
        int pageNo = 0;
        int pageSize = 10;
        List<RemoteWorkerDTO> dtos = Arrays.asList(new RemoteWorkerDTO[10]);
        
        Mockito.when(service.getPage(pageNo, pageSize)).thenReturn(dtos);
        
        mockMvc.perform(get("/api/workers")
                .contentType(MediaType.APPLICATION_JSON)
                .param("size", ""+pageSize)
                .param("page", ""+pageNo))
                .andExpect(status().isOk());
    }
    
    @Test
    void updateWorker_Should_Return_Status_204() throws Exception{
        RemoteWorker worker = new RemoteWorker("worker@email.com", UserType.REMOTE, LocalDate.now());
        worker.setId(1L);
        String json = mapper.writeValueAsString(worker);
        
        Mockito.when(service.update(worker)).thenReturn(true);
        
        mockMvc.perform(put("/api/workers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNoContent());
    }
    
    @Test
    void updateWorker_With_Wrong_ID_Should_Return_Status_404() throws Exception{
        RemoteWorker worker = new RemoteWorker("worker@email.com", UserType.REMOTE, LocalDate.now());
        worker.setId(5L);
        String json = mapper.writeValueAsString(worker);
        
        Mockito.when(service.update(worker)).thenReturn(false);
        
        mockMvc.perform(put("/api/workers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void updateWorker_Without_Data_Should_Return_Status_400() throws Exception{
        RemoteWorker worker = null;
        String json = "";
        
        Mockito.when(service.update(worker)).thenReturn(true);
        
        mockMvc.perform(put("/api/workers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void deleteWorker_Should_Return_Status_204() throws Exception {
        long id = 1L;
        
        Mockito.when(service.delete(id)).thenReturn(true);
        
        mockMvc.perform(delete("/api/workers")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", ""+id))
                .andExpect(status().isNoContent());
    }
    
    @Test
    void deleteWorker_With_Bad_Id_Should_Return_Status_400() throws Exception {
        long id = 5L;
        
        Mockito.when(service.delete(id)).thenReturn(false);
        
        mockMvc.perform(delete("/api/workers")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", ""+id))
                .andExpect(status().isBadRequest());
    }
    
}
