/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.services;

import com.burabari.workerbee.models.Client;
import com.burabari.workerbee.models.Project;
import com.burabari.workerbee.repos.ClientsRepo;
import com.burabari.workerbee.repos.ProjectsRepo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 *
 * @author codebase
 */
@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    @Mock
    private ProjectsRepo projRepo;
    @Mock
    private ClientsRepo clientRepo;
    @InjectMocks
    private ProjectService service;
    
    @Test
    void create(){
        long id = 1L;
        Project project = new Project();
        Client dbClient = new Client();
        dbClient.setId(id);
        dbClient.setProjects(new HashSet<>(new ArrayList<Project>()));
        Optional<Client> optC = Optional.of(dbClient);
        Optional<Project> optP = Optional.of(project);
        
        when(clientRepo.findById(id)).thenReturn(optC);
        when(projRepo.save(project)).thenReturn(project);
        when(clientRepo.save(dbClient)).thenReturn(dbClient);
        
        Optional<Project> opt = service.create(id, project);
        
        Assertions.assertThat(opt.isPresent()).isTrue();
    }
    
    @Test
    void create_With_Bad_Clinet_Id(){
        long id = 5L;
        Project project = new Project();
        Optional<Client> optC = Optional.empty();
        
        when(clientRepo.findById(id)).thenReturn(optC);
        
        Optional<Project> opt = service.create(id, project);
        
        Assertions.assertThat(opt.isPresent()).isFalse();
    }
    
    @Test
    void findByProjectId() {
        String projId = "abc-123";
        Project project = new Project();
        project.setProjectId(projId);
        Optional<Project> projectOpt = Optional.of(project);
        
        when(projRepo.findByProjectId(projId)).thenReturn(projectOpt);
        
        Optional<Project> opt = service.findByProjectId(projId);
        
        Assertions.assertThat(opt.isPresent()).isTrue();
    }
    
    @Test
    void findByProjectId_WIth_Null_Id() {
        String projId = null;
        
        Optional<Project> opt = service.findByProjectId(projId);
        
        Assertions.assertThat(opt.isPresent()).isFalse();
    }
    
    @Test
    void findByProjectId_WIth_Empty_Id() {
        String projId = "";
        
        Optional<Project> opt = service.findByProjectId(projId);
        
        Assertions.assertThat(opt.isPresent()).isFalse();
    }
    
    @Test
    void getPage(){
        int pageNo = 1;
        int pageSize = 10;
        List<Project> projects = Arrays.asList(new Project[pageSize]);
        Pageable pageable = PageRequest.of(pageNo, pageSize);                   //-> TODO: Make other pageables use this pattern
        Page<Project> page = new PageImpl(projects, pageable, 100);
        
        when(projRepo.findAll(pageable)).thenReturn(page);
        
        List<Project> projectList = service.getPage(pageNo, pageSize);
        
        Assertions.assertThat(projectList.size()).isBetween(0, 10);
    }
}
