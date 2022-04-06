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
import java.util.HashSet;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

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
}
