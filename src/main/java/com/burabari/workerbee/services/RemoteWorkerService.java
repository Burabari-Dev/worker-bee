/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.services;

import com.burabari.workerbee.models.RemoteWorker;
import com.burabari.workerbee.models.dtos.RemoteWorkerDTO;
import com.burabari.workerbee.repos.RemoteWorkersRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author codebase
 */
@Service
public class RemoteWorkerService {

    private RemoteWorkersRepo repo;
    private ObjectMapper mapper;
    
    @Autowired
    public RemoteWorkerService(RemoteWorkersRepo repo, ObjectMapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }
    
    public Optional<RemoteWorkerDTO> create(RemoteWorker worker){
        //TODO: -> Use this format for StaffService & ClientService
        if(worker == null)
            return Optional.empty();
        RemoteWorker saved = repo.save(worker);
        RemoteWorkerDTO dto = mapper.convertValue(saved, RemoteWorkerDTO.class);
        return Optional.of(dto);
    }
}
