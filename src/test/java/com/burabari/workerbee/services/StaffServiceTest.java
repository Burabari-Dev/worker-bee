/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.services;

import com.burabari.workerbee.models.Staff;
import com.burabari.workerbee.models.dtos.StaffDTO;
import com.burabari.workerbee.models.enums.UserType;
import com.burabari.workerbee.repos.StaffRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author codebase
 */
@ExtendWith(MockitoExtension.class)
public class StaffServiceTest {
    @Mock
    private StaffRepo repo;
    @Mock
    private ObjectMapper mapper;
    @InjectMocks
    private StaffService service;
    
//    @BeforeEach 
//    void setUp(){
//        repo = Mockito.mock(StaffRepo.class);
//        mapper = Mockito.mock(ObjectMapper.class);
//        service = new StaffService(repo, mapper);
//    }
    
    @Test
    void create(){
        Staff staff = new Staff("staff@email.com", UserType.STAFF);
        Staff createdStaff = new Staff("staff@email.com", UserType.STAFF);
        createdStaff.setId(1L);
        StaffDTO dto = new StaffDTO(1L, "staff@email.com", UserType.STAFF);
        when(repo.save(staff)).thenReturn(createdStaff);
        when(mapper.convertValue(createdStaff, StaffDTO.class)).thenReturn(dto);
        
        StaffDTO created = service.create(staff);
        
        Assertions.assertThat(created).isNotNull();
        Assertions.assertThat(created.getId()).isNotNull();
        Assertions.assertThat(created.getEmail()).isEqualTo(staff.getEmail());
    }
}
