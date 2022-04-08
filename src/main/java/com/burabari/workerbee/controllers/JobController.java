/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.controllers;

import com.burabari.workerbee.models.Job;
import com.burabari.workerbee.services.JobService;
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
        path = "/api/jobs",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class JobController {
    
    @Autowired
    private JobService service;
    private final String BASE_URI = "/api/jobs/";
    
    @PostMapping
    public ResponseEntity<Job> createJob(
            @RequestParam(name = "projId", required = true) long id,    // primitive long prefered to Object Long to avoid nulls
            @RequestBody(required = true) Job job){
        
        if(id <= 0 || job == null)
            return ResponseEntity.badRequest().build();
        
        Optional<Job> created = service.create(id, job);
        if(created.isEmpty())
            return ResponseEntity.badRequest().build();
        
        Job dbJob = created.get();
        return ResponseEntity.created( URI.create(BASE_URI+dbJob.getCustomJobId()) ).body(job);
    }
    
    @GetMapping("/{customId}")
    public ResponseEntity<Job> getByCustomId(
            @PathVariable(name = "customId", required = true) String customId){
        
        if(customId == null || customId.isBlank())
            return ResponseEntity.badRequest().build();
        
        Optional<Job> opt = service.getByCustomId(customId);
        return ResponseEntity.of(opt);
    }
    
    @GetMapping
    public ResponseEntity<List<Job>> getJobPage(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        
        List<Job> jobs = service.getPage(page, size);
        return ResponseEntity.ok(jobs);
    }
    
    @PutMapping
    public ResponseEntity updateJob(@RequestBody(required = true) Job job){
        
        if(job == null)
            return ResponseEntity.badRequest().build();
        
        boolean updated = service.update(job);
        
        if(updated)
            return ResponseEntity.noContent().build();
        return ResponseEntity.badRequest().build();
    }
    
    @DeleteMapping
    public ResponseEntity deleteJob(@RequestParam(name = "id", required = true) long id){
        
        if(id <= 0)
            return ResponseEntity.badRequest().build();
        
        boolean deleted = service.delete(id);
        if(deleted)
            return ResponseEntity.noContent().build();
        
        return ResponseEntity.badRequest().build();
    }
    
}
