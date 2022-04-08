/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.services;

import com.burabari.workerbee.models.Job;
import com.burabari.workerbee.models.Project;
import com.burabari.workerbee.repos.JobsRepo;
import com.burabari.workerbee.repos.ProjectsRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    
    public Optional<Job> create(long projId, Job job){
        Optional<Project> optProj = projRepo.findById(projId);
        if(optProj.isEmpty() || job == null)
            return Optional.empty();
        
        Job dbJob = repo.save(job);
        
        Project project = optProj.get();
        project.addJob(job);
        projRepo.save(project);
        
        return Optional.of(dbJob);
    }
    
    public Optional<Job> getByCustomId(String customId){
        if(customId == null || customId.isBlank())
            return Optional.empty();
        
        Optional<Job> opt = repo.findBy_CustomJobId(customId);
        return opt;
    }
    
    public List<Job> getPage(int page, int size){
        Page<Job> jobs = repo.findAll(PageRequest.of(page, size));
        return jobs.toList();
    }
}
