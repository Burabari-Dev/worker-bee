/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.services;

import com.burabari.workerbee.models.RemoteWorker;
import com.burabari.workerbee.models.dtos.RemoteWorkerDTO;
import com.burabari.workerbee.repos.RemoteWorkersRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    
    public Optional<RemoteWorkerDTO> findById(long id){
        Optional<RemoteWorker> opt = repo.findById(id);
        if(opt.isEmpty())
            return Optional.empty();
        RemoteWorkerDTO dto = mapper.convertValue(opt.get(), RemoteWorkerDTO.class);
        return Optional.of(dto);
    }
    
    public List<RemoteWorkerDTO> getPage(int no, int size){
        Page<RemoteWorker> page = repo.findAll(PageRequest.of(no, size));
        List<RemoteWorkerDTO> dtos = page.get().map(worker -> mapper
                .convertValue(worker, RemoteWorkerDTO.class))
                .collect(Collectors.toList());
        return dtos;
    }
    
    public boolean update(RemoteWorker worker){
        if(worker == null)
            return false;
        if(! repo.existsById(worker.getId()))
            return false;
        repo.save(worker);
        return true;
    }
}
