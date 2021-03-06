/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.controllers;

import com.burabari.workerbee.models.RemoteWorker;
import com.burabari.workerbee.models.dtos.RemoteWorkerDTO;
import com.burabari.workerbee.services.RemoteWorkerService;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author codebase
 */
@RestController
@RequestMapping(
        path = "/api/workers",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class RemoteWorkerController {
    
    @Autowired
    private RemoteWorkerService service;
    
    @PostMapping
    public ResponseEntity<RemoteWorkerDTO> createWorker(
            @RequestBody(required = true) RemoteWorker worker){
        Optional<RemoteWorkerDTO> opt = service.create(worker);
        if(opt.isEmpty())
            return ResponseEntity.badRequest().build();
        RemoteWorkerDTO dto = opt.get();
        return ResponseEntity.created(
                URI.create("/api/workers/"+dto.getId())).build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RemoteWorkerDTO> findWorkerById(
            @PathVariable(required = true) long id){
        Optional<RemoteWorkerDTO> opt = service.findById(id);
        return ResponseEntity.of(opt);
    }
    
    @GetMapping
    public ResponseEntity<List<RemoteWorkerDTO>> getPage(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size){
        List<RemoteWorkerDTO> dtos = service.getPage(page, size);
        return ResponseEntity.ok(dtos);
    }
    
    @PutMapping
    public ResponseEntity updateWorker(
            @RequestBody(required = true) RemoteWorker worker){
        if(worker == null)
            return ResponseEntity.badRequest().build();
        boolean updated = service.update(worker);
        if(updated)
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping
    public ResponseEntity deleteWorker(
            @RequestParam(name = "id", required = true) long id){
        boolean deleted = service.delete(id);
        if(deleted)
            return ResponseEntity.noContent().build();
        return ResponseEntity.badRequest().build();
    }
}
