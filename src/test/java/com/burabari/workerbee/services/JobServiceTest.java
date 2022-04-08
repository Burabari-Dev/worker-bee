/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.services;

import com.burabari.workerbee.models.Job;
import com.burabari.workerbee.models.Project;
import com.burabari.workerbee.repos.JobsRepo;
import com.burabari.workerbee.repos.ProjectsRepo;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;

/**
 *
 * @author codebase
 */
@ExtendWith(MockitoExtension.class)
public class JobServiceTest {
    
    @Mock
    private JobsRepo repo;
    @Mock
    private ProjectsRepo projRepo;
    @InjectMocks
    private JobService service;
    
    @Test
    void create(){
        long projId = 1L;
        Job job = new Job();
        job.setId(1L);
        Project proj = new Project();
        Optional<Project> optProj = Optional.of(proj);
        
        when(projRepo.findById(projId)).thenReturn(optProj);
        when(repo.save(job)).thenReturn(job);
        when(projRepo.save(proj)).thenReturn(proj);
        
        Optional<Job> created = service.create(projId, job);
        
        Assertions.assertThat(created.isPresent()).isTrue();
    }
    
    @Test
    void create_With_Invalid_Project_Id(){
        long projId = -1L;
        Job job = new Job();
        job.setId(1L);
        Optional<Project> optProj = Optional.empty();
        
        when(projRepo.findById(projId)).thenReturn(optProj);
        
        Optional<Job> created = service.create(projId, job);
        
        Assertions.assertThat(created.isPresent()).isFalse();
    }
    
}
