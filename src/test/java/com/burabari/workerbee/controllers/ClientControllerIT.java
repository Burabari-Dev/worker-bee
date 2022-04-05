/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.controllers;

import com.burabari.workerbee.models.Client;
import com.burabari.workerbee.services.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author codebase
 */
@WebMvcTest(ClientController.class)
public class ClientControllerIT {

    @MockBean
    private ClientService service;
    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new JsonMapper();

    @Test
    void createClient_Should_Return_Status_201_Created_Client() throws Exception {
        Client client = new Client("abc-34def", "ABC Inc.", new ArrayList<>(), new ArrayList<>());
        Client dbClient = new Client("abc-34def", "ABC Inc.", new ArrayList<>(), new ArrayList<>());
        dbClient.setId(1L);
        String clientJson = mapper.writeValueAsString(client);

        Mockito.when(service.create(client)).thenReturn(dbClient);

        mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, 
                        "/api/clients?id="+client.getClientId()));
    }

    @Test
    void createClient_Without_Body_Should_Return_Status_400_Bad_Request() throws Exception {
        Client client = null;
        String clientJson = "";

        Mockito.when(service.create(client)).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void getPage_Should_Return_Status_200() throws Exception{
        int page = 2;
        int size = 12;
        List<Client> clients = Arrays.asList(new Client[10]);
        
        Mockito.when(service.getPage(page, size)).thenReturn(clients);
        
        mockMvc.perform(get("/api/clients")
                .param("page", "2")
                .param("size", "12")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    @Test
    void getClientById_Should_Return_Status_200() throws Exception{
        Long id = 1L;
        Client dbClient = new Client("abc-34def", "ABC Inc.", new ArrayList<>(), new ArrayList<>());
        dbClient.setId(id);
        Optional<Client> opt = Optional.of(dbClient);
        
        Mockito.when(service.findById(id)).thenReturn(opt);
        
        mockMvc.perform(get("/api/clients")
                .param("id", id.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    @Test
    void updateClient_Should_Return_Status_204() throws Exception {
        Client newClient = new Client("abc-34def", "ABC Inc.", new ArrayList<>(), new ArrayList<>());
        newClient.setId(1L);
        String clientJson = mapper.writeValueAsString(newClient);
        
        Mockito.when(service.update(newClient)).thenReturn(true);
        
        mockMvc.perform(put("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson))
                .andExpect(status().isNoContent());
    }
    
    @Test
    void updateClient_With_No_Data_Should_Return_Status_400() throws Exception {
        Client newClient = null;
        String clientJson = "";
        
        mockMvc.perform(put("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void updateClient_With_Wrong_Data_Should_Return_Status_404() throws Exception {
        Client newClient = new Client("abc-34def", "ABC Inc.", new ArrayList<>(), new ArrayList<>());
        newClient.setId(1L);
        String clientJson = mapper.writeValueAsString(newClient);
        
        Mockito.when(service.update(newClient)).thenReturn(false);
        
        mockMvc.perform(put("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson))
                .andExpect(status().isNotFound());
    }
}
