/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.services;

import com.burabari.workerbee.repos.ClientsRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    
}
