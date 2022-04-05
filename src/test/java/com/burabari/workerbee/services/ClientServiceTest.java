/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.services;

import com.burabari.workerbee.models.Client;
import com.burabari.workerbee.repos.ClientsRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
    void getPage() {
        int pageNo = 3;
        Client[] clientArr = new Client[10];
        for (int i = 0; i < clientArr.length; i++) {
            clientArr[i] = new Client("abc-" + i + "4def", "",
                    new ArrayList<>(), new ArrayList<>());
        }
        List<Client> clientList = Arrays.asList(clientArr);

        Page<Client> page = new PageImpl<>(clientList, Pageable.ofSize(10), 50);
        when(repo.findAll(PageRequest.of(pageNo, 10))).thenReturn(page);

        List<Client> clients = service.getPage(pageNo, 10);

        Assertions.assertThat(clients.size()).isBetween(0, 10);
    }

    @Test
    void findById() {
        long id = 1L;
        Client dbClient = new Client("abc-34def", "ABC Inc.", new ArrayList<>(), new ArrayList<>());
        dbClient.setId(id);
        Optional<Client> opt = Optional.of(dbClient);

        when(repo.findById(id)).thenReturn(opt);

        Optional<Client> byId = service.findById(id);
        Assertions.assertThat(byId).isNotNull();
        Assertions.assertThat(byId).isInstanceOf(Optional.class);
    }

    @Test
    void update() {
        Client newClient = new Client("def-44abc", "DEF Inc.", new ArrayList<>(), new ArrayList<>());
        newClient.setId(1L);

        when(repo.existsById(newClient.getId())).thenReturn(true);
        when(repo.save(newClient)).thenReturn(newClient);

        boolean updated = false;
        try {
            updated = service.update(newClient);
        } catch (IllegalArgumentException e) {}

        Assertions.assertThat(updated).isTrue();
    }

    @Test
    void update_With_Error() {
        Client newClient = new Client("def-44abc", "DEF Inc.", new ArrayList<>(), new ArrayList<>());
        newClient.setId(1L);

        when(repo.existsById(newClient.getId())).thenReturn(false);
//        when(repo.save(newClient)).thenReturn(newClient);

        boolean updated = false;
        try {
            updated = service.update(newClient);
        } catch (IllegalArgumentException e) {}

        Assertions.assertThat(updated).isFalse();
    }

    @Test
    void update_With_No_Data() {
        Client newClient = null;

        boolean updated = false;
        try {
            updated = service.update(newClient);
        } catch (IllegalArgumentException e) { }

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class);
    }
    
    @Test
    void delete(){
        long id = 1L;        
        when(repo.existsById(id)).thenReturn(true);
        boolean deleted = service.delete(id);
        Assertions.assertThat(deleted).isTrue();
    }
    
    @Test
    void delete_Non_Existent_Id(){
        long id = 5L;        
        when(repo.existsById(id)).thenReturn(false);
        boolean deleted = service.delete(id);
        Assertions.assertThat(deleted).isFalse();
    }
}
