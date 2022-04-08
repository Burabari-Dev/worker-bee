/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.services;

import com.burabari.workerbee.models.Job;
import com.burabari.workerbee.models.Project;
import com.burabari.workerbee.repos.JobsRepo;
import com.burabari.workerbee.repos.ProjectsRepo;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    
    @Test
    void getByCustomId(){
        String custId = "abc-123";
        Job job = new Job();
        job.setCustomJobId(custId);
        Optional<Job> opt = Optional.of(job);
        
        when(repo.findBy_CustomJobId(custId)).thenReturn(opt);
        Optional<Job> found = service.getByCustomId(custId);
        
        Assertions.assertThat(found.isPresent()).isTrue();
    }
    
    @Test
    void getByCustomId_With_Null_Custom_Id(){
        String custId = null;
        
        Optional<Job> found = service.getByCustomId(custId);
        
        Assertions.assertThat(found.isPresent()).isFalse();
    }
    
    @Test
    void getPage(){
        int no = 4;
        int size = 12;
        List<Job> jobs = Arrays.asList(new Job[size]);
        Pageable pageable = PageRequest.of(no, size);                   //-> TODO: Make other pageables use this pattern
        Page<Job> page = new PageImpl(jobs, pageable, 500);
        
        when(repo.findAll(pageable)).thenReturn(page);
        List<Job> jobPage = service.getPage(no, size);
        
        Assertions.assertThat(jobPage.size()).isBetween(0, size);
    }
    
    @Test
    void update(){
        Job job = new Job();
        job.setId(1L);
        job.setTitle("Test Job");
        Job dbJob = new Job();
        dbJob.setId(1L);
        Optional<Job> opt = Optional.of(dbJob);
        
        when(repo.findById(job.getId())).thenReturn(opt);
        boolean updated = service.update(job);
        
        Assertions.assertThat(updated).isTrue();
    }
    
    @Test
    void update_With_Null_Data(){
        Job job = null;
        
        boolean updated = service.update(job);
        
        Assertions.assertThat(updated).isFalse();
    }
    
    @Test
    void delete(){
        long id = 1L;
        
        when(repo.existsById(id)).thenReturn(true);
        boolean deleted = service.delete(id);
        
        Assertions.assertThat(deleted).isTrue();
    }
    
    @Test
    void delete_Invalid_Id(){
        long id = 1L;
        
        when(repo.existsById(id)).thenReturn(false);
        boolean deleted = service.delete(id);
        
        Assertions.assertThat(deleted).isFalse();
    }
}
