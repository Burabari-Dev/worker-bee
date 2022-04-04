/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.controllers;

import com.burabari.workerbee.models.Client;
import com.burabari.workerbee.services.ClientService;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
