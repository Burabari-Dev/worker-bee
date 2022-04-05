/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.services;

import com.burabari.workerbee.models.RemoteWorker;
import com.burabari.workerbee.models.dtos.RemoteWorkerDTO;
import com.burabari.workerbee.models.enums.UserType;
import com.burabari.workerbee.repos.RemoteWorkersRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.isA;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author codebase
 */
@ExtendWith(MockitoExtension.class)
public class RemoteWorkerServiceTest {
    @Mock
    private RemoteWorkersRepo repo;
    @Mock
    private ObjectMapper mapper;
    @InjectMocks
    RemoteWorkerService service;
    
    @Test
    void create(){
        RemoteWorker worker = new RemoteWorker();
        RemoteWorkerDTO dto = new RemoteWorkerDTO();
        worker.setEmail("worker@email.com");
        dto.setEmail("worker@email.com");
        
        when(repo.save(worker)).thenReturn(worker);
        when(mapper.convertValue(worker, RemoteWorkerDTO.class)).thenReturn(dto);
        
        Optional<RemoteWorkerDTO> opt = service.create(worker);
        
        Assertions.assertThat(opt.isPresent()).isTrue();
        Assertions.assertThat(opt.get().getEmail()).isEqualTo(dto.getEmail());
    }
    
    @Test
    void create_With_Null_Worker(){
        RemoteWorker worker = null;
        
        Optional<RemoteWorkerDTO> opt = service.create(worker);
        
        Assertions.assertThat(opt.isPresent()).isFalse();
    }
}
