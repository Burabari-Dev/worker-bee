/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.services;

import com.burabari.workerbee.repos.JobsRepo;
import com.burabari.workerbee.repos.ProjectsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author codebase
 */
@Service
public class JobService {
    
    private JobsRepo repo;
    private ProjectsRepo projRepo;
    
    @Autowired
    public JobService(JobsRepo repo, ProjectsRepo projRepo){
        this.repo = repo;
        this.projRepo = projRepo;
    }
    
}
