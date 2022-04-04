/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.services;

import com.burabari.workerbee.models.Client;
import com.burabari.workerbee.models.Staff;
import com.burabari.workerbee.repos.ClientsRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    
    @Test
    void getPage(){
        int pageNo = 3;
        Client[] clientArr = new Client[10];
        for(int i = 0; i < clientArr.length; i++){
            clientArr[i] = new Client("abc-"+i+"4def", "", 
                    new ArrayList<>(), new ArrayList<>());
        }
        List<Client> clientList = Arrays.asList(clientArr);
        
        Page<Client> page = new PageImpl<>(clientList, Pageable.ofSize(10), 50);
        when(repo.findAll(PageRequest.of(pageNo, 10))).thenReturn(page);
        
        List<Client> clients = service.getPage(pageNo, 10);
        
        Assertions.assertThat(clients.size()).isBetween(0, 10);
    }
}
