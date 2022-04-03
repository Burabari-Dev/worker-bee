/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.controllers;

import com.burabari.workerbee.models.Staff;
import com.burabari.workerbee.models.dtos.StaffDTO;
import com.burabari.workerbee.models.enums.UserType;
import com.burabari.workerbee.services.StaffService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 *
 * @author codebase
 */
@WebMvcTest(controllers = {StaffController.class})
public class StaffControllerIT {

    @MockBean
    private StaffService service;
    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new JsonMapper();

    @Test
    void createStaff_Should_Return_Status_201_Created_StaffDto() throws Exception {
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

    @Test
    void createStaff_Without_Data_Should_Return_Status_400_BadRequest() throws Exception {
        Staff staff = null;
        Mockito.when(service.create(staff)).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(post("/api/staff")
                .contentType(MediaType.APPLICATION_JSON))
                //                .content(staffJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getById_Should_Return_Status_200_And_StaffDto_With_ID() throws Exception {
        Long id = 1L;
        StaffDTO staffDto = new StaffDTO(id, "staff@email.com", UserType.STAFF);
        Optional<StaffDTO> opt = Optional.of(staffDto);
        String staffJson = mapper.writeValueAsString(staffDto);

        Mockito.when(service.getById(id)).thenReturn(opt);

        mockMvc.perform(get("/api/staff")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", id.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(staffJson));
    }

    @Test
    void getById_Should_Return_Status_404_If_No_Staff_With_ID() throws Exception {
        Long id = 1L;
        Mockito.when(service.getById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/staff")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", id.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void get10Staff_Should_Return_Status_200() throws Exception {
        List<StaffDTO> tenStaff = Arrays.asList(new StaffDTO[10]);
        Mockito.when(service.getNext10(0)).thenReturn(tenStaff);

        mockMvc.perform(get("/api/staff")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
