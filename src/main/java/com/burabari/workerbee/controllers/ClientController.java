/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.controllers;

import com.burabari.workerbee.models.Client;
import com.burabari.workerbee.services.ClientService;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(
        path = {"/api/clients"},
        consumes = {"application/json"},
        produces = {"application/json"})
@RestController
public class ClientController {
    
    @Autowired
    private ClientService service;
    
    @PostMapping
    public ResponseEntity<Client> createClient(
            @RequestBody(required = true) Client client){
        if(client == null)
            return ResponseEntity.badRequest().build();
        Client created = service.create(client);
        return ResponseEntity.created(
                URI.create("/api/clients?id="+created.getClientId()))
                .body(created);
    }
    
    @GetMapping
    public ResponseEntity<List<Client>> getPage(
            @RequestParam(name = "page", required = false, defaultValue = "0")int page, 
            @RequestParam(name = "size", required = false, defaultValue = "10")int size){
        List<Client> clients = service.getPage(page, size);
        return ResponseEntity.ok(clients);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(
            @PathVariable(required = true) long id){
        Optional<Client> clientOpt = service.findById(id);
        return ResponseEntity.of(clientOpt);
    }
    
    @PutMapping
    public ResponseEntity updateClient(@RequestBody(required = true) Client client){
        if(client == null)
            return ResponseEntity.badRequest().build();
        boolean update = service.update(client);
        if(update)
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping
    public ResponseEntity deleteClient(
            @RequestParam(name = "id", required = true) Long id){
        if(id == null)
            return ResponseEntity.badRequest().build();
        boolean deleted = service.delete(id);
        if(deleted)
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
