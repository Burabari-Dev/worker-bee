/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.services;

import com.burabari.workerbee.models.Client;
import com.burabari.workerbee.repos.ClientsRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author codebase
 */
@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    
    @Mock
    private ClientsRepo repo;
    @Mock
    private ObjectMapper mapper;
    @InjectMocks
    private ClientService service;
    
    @Test
    void create() {
        Client client = new Client("abc-34def", "ABC Inc.", new ArrayList<>(), new ArrayList<>());
        Client createdClient = new Client("abc-34def", "ABC Inc.", new ArrayList<>(), new ArrayList<>());
        createdClient.setId(1L);
        
        when(repo.save(client)).thenReturn(createdClient);

        Client created = service.create(client);

        Assertions.assertThat(created).isNotNull();
        Assertions.assertThat(created.getId()).isNotNull();
        Assertions.assertThat(created.getClientId()).isEqualTo(client.getClientId());
    }
}
