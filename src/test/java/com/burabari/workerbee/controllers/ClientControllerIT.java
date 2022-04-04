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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

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
}
