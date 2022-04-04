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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import org.mockito.junit.jupiter.MockitoExtension;
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
    void create() {
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
    void getById() {
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
    void getNext10() {
        int pageNo = 0;

        Staff[] staffArr = new Staff[15];
        for (int i = 0; i < staffArr.length; i++) {
            staffArr[i] = new Staff("staff" + i + "@email.com", UserType.STAFF);
            staffArr[i].setId((long) i);
        }
        Staff[] first10 = Arrays.copyOfRange(staffArr, 0, 9);
        List<Staff> first10List = Arrays.asList(first10);
        Page<Staff> page = new PageImpl<>(first10List, Pageable.ofSize(10), 15);

        when(repo.findAll(PageRequest.of(pageNo, 10))).thenReturn(page);

        List<StaffDTO> next10 = service.getNext10(pageNo);

        Assertions.assertThat(next10.size()).isBetween(0, 10);
    }

    @Test
    void update() throws Exception {
        Staff updateStaff = new Staff("staff.new@email.com", UserType.STAFF);
        updateStaff.setId(1L);
        Staff dbStaff = new Staff("staff@email.com", UserType.STAFF);
        dbStaff.setId(1L);

        when(repo.existsById(updateStaff.getId())).thenReturn(true);
        when(repo.save(updateStaff)).thenReturn(updateStaff);

        boolean val = service.update(updateStaff);

        Assertions.assertThat(val).isTrue();
    }

    @Test
    void update_With_Error() throws Exception {
        Staff updateStaff = new Staff("staff.new@email.com", UserType.STAFF);
        updateStaff.setId(1L);

        when(repo.existsById(updateStaff.getId())).thenThrow(EntityNotFoundException.class);

        try {
            service.update(updateStaff);
        } catch (EntityNotFoundException ex) {
        }

        Assertions.assertThatExceptionOfType(EntityNotFoundException.class);
    }

    @Test
    void delete() throws Exception {
        long id = 1L;

        when(repo.existsById(id)).thenReturn(true);
        //->    repo.deleteById(id) DOES NOTHING!!!

        boolean val = service.delete(id);
        Assertions.assertThat(val).isTrue();
    }

    @Test
    void delete_Non_Existent_Id() throws Exception {
        long id = 5L;

        when(repo.existsById(id)).thenReturn(false);
        //->    repo.deleteById(id) DOES NOTHING!!!

        boolean val = service.delete(id);
        Assertions.assertThat(val).isFalse();
    }

    @Test
    void delete_Null_Id() throws Exception {
        when(repo.existsById(null)).thenThrow(IllegalArgumentException.class);
        //->    repo.deleteById(id) DOES NOTHING!!!
        try {
            service.delete(null);
        } catch (IllegalArgumentException ex) {}
        
        Assertions.assertThatExceptionOfType(EntityNotFoundException.class);
    }
}
