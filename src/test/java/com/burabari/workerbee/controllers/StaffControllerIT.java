/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.controllers;

import com.burabari.workerbee.models.Staff;
import com.burabari.workerbee.models.dtos.StaffDTO;
import com.burabari.workerbee.models.enums.UserType;
import com.burabari.workerbee.services.StaffService;
import com.burabari.workerbee.services.StaffServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author codebase
 */
@WebMvcTest(controllers = {StaffController.class})
public class StaffControllerIT {

    @MockBean
    private StaffServiceImpl service;
    @Autowired
    private MockMvc mockMvc;
    
    private final ObjectMapper mapper = new JsonMapper();

    @Test
    void create_Staff_Should_Return_Status_201_Created_Staff_Dto() throws Exception {
        Staff staff = new Staff("staff@email.com", UserType.STAFF);
        staff.setId(1L);
        String staffJson = mapper.writeValueAsString(staff);
        StaffDTO createdStaff = new StaffDTO(1L, "staff@email.com", UserType.STAFF);

        Mockito.when(service.create(staff)).thenReturn(createdStaff);

        mockMvc.perform(post("/api/staff")
                .contentType(MediaType.APPLICATION_JSON)
                .content(staffJson))
                .andExpect(status().isCreated())
                .andExpect(header().string(
                        HttpHeaders.LOCATION, "/api/staff/1"));
    }
}
