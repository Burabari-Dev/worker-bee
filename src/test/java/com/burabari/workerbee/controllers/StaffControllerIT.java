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
import javax.persistence.EntityNotFoundException;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
    
    @Test
    void updateStaff_Should_Return_Status_204() throws Exception{
        Staff updateStaff = new Staff("staff.new@email.com", UserType.STAFF);
        updateStaff.setId(1L);
        String staffJson = mapper.writeValueAsString(updateStaff);
        
        Mockito.when(service.update(updateStaff)).thenReturn(true);
        
        mockMvc.perform(put("/api/staff")
                .contentType(MediaType.APPLICATION_JSON)
                .content(staffJson))
                .andExpect(status().isNoContent());
    }
    
    @Test
    void updateStaff_Without_Data_Should_Return_Status_400() throws Exception{
        
        Mockito.when(service.update(null)).thenThrow(NullPointerException.class);
        
        mockMvc.perform(put("/api/staff")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void updateStaff_With_Empty_Data_Should_Return_Status_404() throws Exception{
        String staffJson = "{}";
        Mockito.when(service.update(null)).thenThrow(NullPointerException.class);
        
        mockMvc.perform(put("/api/staff")
                .contentType(MediaType.APPLICATION_JSON)
                .content(staffJson))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void updateStaff_With_Wrong_Data_Should_Return_Status_404() throws Exception{
        Staff updateStaff = new Staff("staff.new@email.com", UserType.STAFF);
        updateStaff.setId(5L);
        String staffJson = mapper.writeValueAsString(updateStaff);
        Mockito.when(service.update(updateStaff)).thenReturn(false);
        
        mockMvc.perform(put("/api/staff")
                .contentType(MediaType.APPLICATION_JSON)
                .content(staffJson))
                .andExpect(status().isNotFound());
    }
    
    
    @Test
    void deleteStaff_Should_Return_Status_204() throws Exception{
        Long id = 1L;
        
        Mockito.when(service.delete(id)).thenReturn(true);
        
        mockMvc.perform(delete("/api/staff")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", id.toString()))
                .andExpect(status().isNoContent());
    }
    
    @Test
    void deleteStaff_With_Null_ID_Should_Return_Status_400() throws Exception{
        Long id = null;
        
        Mockito.when(service.delete(id)).thenThrow(IllegalArgumentException.class);
        
        mockMvc.perform(delete("/api/staff")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", ""))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void deleteStaff_With_NonExistent_ID_Should_Return_Status_404() throws Exception{
        Long id = 5L;
        
        Mockito.when(service.delete(id)).thenReturn(false);
        
        mockMvc.perform(delete("/api/staff")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", id.toString()))
                .andExpect(status().isNotFound());
    }
}
