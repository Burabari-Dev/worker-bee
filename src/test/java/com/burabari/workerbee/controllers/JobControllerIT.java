/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.controllers;

import com.burabari.workerbee.models.Job;
import com.burabari.workerbee.services.JobService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author codebase
 */
@WebMvcTest(JobController.class)
public class JobControllerIT {
    
    private final String BASE_URL = "/api/jobs";
    private final MediaType APP_JSON = MediaType.APPLICATION_JSON;
    @MockBean
    private JobService service;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void createJob_Should_Return_Status_201() throws Exception {
        long projId = 1L;
        Job job = new Job();
        job.setCustomJobId("abc-123");
        Optional<Job> created = Optional.of(job);
        String json = mapper.writeValueAsString(job);
        
        when(service.create(projId, job)).thenReturn(created);
        
        mockMvc.perform(post(BASE_URL)
                .contentType(APP_JSON)
                .param("projId", ""+projId)
                .content(json))
                .andExpect(status().isCreated());
    }
    
    @Test
    void createJob_WIth_Null_Job_Should_Return_Status_400() throws Exception {
        long projId = 1L;
        String JobJson = "";
        
        mockMvc.perform(post(BASE_URL)
                .contentType(APP_JSON)
                .param("projId", ""+projId)
                .content(JobJson))
                .andExpect(status().isBadRequest());
    }
}
