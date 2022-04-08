/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.controllers;

import com.burabari.workerbee.models.Job;
import com.burabari.workerbee.services.JobService;
import java.net.URI;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    
    @GetMapping
    public ResponseEntity<Job> getByCustomId(
            @RequestParam(name = "custId", required = true) String customId){
        
        if(customId == null || customId.isBlank())
            return ResponseEntity.badRequest().build();
        
        Optional<Job> opt = service.getByCustomId(customId);
        return ResponseEntity.of(opt);
    }
}
