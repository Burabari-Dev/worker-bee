/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.services;

import com.burabari.workerbee.repos.ClientsRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
}
