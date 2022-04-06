/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.controllers;

import com.burabari.workerbee.models.Project;
import com.burabari.workerbee.services.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author codebase
 */
@WebMvcTest(ProjectController.class)
public class ProjectControllerIT {
    
    @MockBean
    private ProjectService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    
    @Test
    void createProject_Should_Return_Status_201() throws Exception{
        long clientId = 1L;
        Project project = new Project();
        project.setProjectId("abc-123");
        project.setTitle("Project 1");
        String json = mapper.writeValueAsString(project);
        Optional opt = Optional.of(project);
        
        when(service.create(clientId, project)).thenReturn(opt);
        
        mockMvc.perform(post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .param("clientId", ""+clientId)
                .content(json))
                .andExpect(status().isCreated());
    }
    
    @Test
    void createProject_With_Invalid_ClientID_Should_Return_Status_400() throws Exception{
        long clientId = 0L;
        Project project = new Project();
        project.setProjectId("abc-123");
        project.setTitle("Project 1");
        String json = mapper.writeValueAsString(project);
        
        mockMvc.perform(post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .param("clientId", ""+clientId)
                .content(json))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void createProject_Without_Data_Should_Return_Status_400() throws Exception{
        long clientId = 1L;
        Project project = null;
        String json = "";
        
        mockMvc.perform(post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .param("clientId", ""+clientId)
                .content(json))
                .andExpect(status().isBadRequest());
    }
}
