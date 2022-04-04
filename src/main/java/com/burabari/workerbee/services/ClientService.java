/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.services;

import com.burabari.workerbee.models.Client;
import com.burabari.workerbee.repos.ClientsRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author codebase
 */
@Service
public class ClientService {
    
    private ClientsRepo repo;
    private ObjectMapper mapper;
    
    @Autowired
    public ClientService(ClientsRepo repo, ObjectMapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }
    
    public Client create(Client client) {
        Client saved = repo.save(client);
        return saved;
    }
    
    public List<Client> getPage(int pageNo, int pageSize) {
        Page<Client> page = repo.findAll(PageRequest.of(pageNo, pageSize));
        return page.toList();
    }
}
