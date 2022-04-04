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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.doReturn;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author codebase
 */
@ExtendWith(MockitoExtension.class)
public class StaffServiceTest {
    @Mock
    private StaffRepo repo;         //-> repo = Mockito.mock(StaffRepo.class);
    @Mock
    private ObjectMapper mapper;    //-> mapper = Mockito.mock(ObjectMapper.class);
    @InjectMocks
    private StaffService service;   //-> service = new StaffService(repo, mapper);
    
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
    
    @Test
    void getById(){
        long id = 1L;
        Staff staff = new Staff("staff@email.com", UserType.STAFF);
        staff.setId(id);
        Optional<Staff> opt = Optional.of(staff);
        StaffDTO dto = new StaffDTO(id, "staff@email.com", UserType.STAFF);
        
        when(repo.findById(id)).thenReturn(opt);
        when(mapper.convertValue(staff, StaffDTO.class)).thenReturn(dto);
        
        Optional<StaffDTO> staffDto = service.getById(id);
        
        Assertions.assertThat(staffDto).isNotNull();
        Assertions.assertThat(staffDto.get().getId()).isEqualTo(id);
    }
    
    @Test
    void getNext10(){
        int pageNo = 0;
        
        Staff[] staffArr = new Staff[15];
        for(int i = 0; i < staffArr.length; i++){
            staffArr[i] = new Staff("staff"+i+"@email.com", UserType.STAFF);
            staffArr[i].setId((long)i);
        }
        Staff[] first10 = Arrays.copyOfRange(staffArr, 0, 9);
        List<Staff> first10List = Arrays.asList(first10);
        Page<Staff> page = new PageImpl<>(first10List, Pageable.ofSize(10), 15);
        
        when(repo.findAll(PageRequest.of(pageNo, 10))).thenReturn(page);
        
        List<StaffDTO> next10 = service.getNext10(pageNo);
        
        Assertions.assertThat(next10.size()).isBetween(0, 10);
    }
    
}
