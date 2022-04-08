/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.controllers;

import com.burabari.workerbee.models.Project;
import com.burabari.workerbee.services.ProjectService;
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
        path = "/api/projects",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController {

    @Autowired
    private ProjectService service;

    @PostMapping
    public ResponseEntity<Project> createProject(
            @RequestParam(name = "clientId", required = true) long clientId,
            @RequestBody(required = true) Project project) {

        if (clientId == 0 || project == null) 
            return ResponseEntity.badRequest().build();

        Optional<Project> created = service.create(clientId, project);

        if (created.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.created(
                    URI.create("/api/projects/" + created.get().getProjectId()))
                    .build();
        }
    }

    @GetMapping("/{projId}")
    public ResponseEntity<Project> findByProjectId(
            @PathVariable(required = true) String projId) {
        
        if (projId == null || projId.isBlank())
            return ResponseEntity.badRequest().build();
        
        Optional<Project> opt = service.findByProjectId(projId);
        return ResponseEntity.of(opt);
    }
    
    @GetMapping
    public ResponseEntity<List<Project>> getPage(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        
        List<Project> projects = service.getPage(page, size);
        return ResponseEntity.ok(projects);
    }
    
    @PutMapping
    public ResponseEntity updateProject(@RequestBody(required = true) Project project){
        if(project == null)
            return ResponseEntity.badRequest().build();
        boolean updated = service.update(project);
        if(updated)
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping
    public ResponseEntity deleteProject(@RequestParam(name = "id", required = true) long id){
        if(id <= 0)     //-> TODO: Do this test for all delete controller methods
            return ResponseEntity.badRequest().build();
        boolean deleted = service.delete(id);
        if(deleted)
            return ResponseEntity.noContent().build();
        return ResponseEntity.badRequest().build();
    }

}
