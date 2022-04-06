/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.services;

import com.burabari.workerbee.models.RemoteWorker;
import com.burabari.workerbee.models.dtos.RemoteWorkerDTO;
import com.burabari.workerbee.repos.RemoteWorkersRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
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
    
    @Test
    void findById(){
        long id = 1L;
        RemoteWorker dbWorker = new RemoteWorker();
        RemoteWorkerDTO dto = new RemoteWorkerDTO();
        dbWorker.setId(id);
        dto.setId(id);
        Optional opt = Optional.of(dbWorker);
        
        when(repo.findById(id)).thenReturn(opt);
        when(mapper.convertValue(dbWorker, RemoteWorkerDTO.class)).thenReturn(dto);
        
        Optional optWorker = service.findById(id);
        
        Assertions.assertThat(optWorker.isPresent()).isTrue();
    }
    
    @Test
    void getPage(){
        int pageNo = 3;
        int size = 8;
        List<RemoteWorker> workers = Arrays.asList(new RemoteWorker[8]);
        Page page = new PageImpl(workers, Pageable.ofSize(size), 50);
        
        when(repo.findAll(PageRequest.of(pageNo, size))).thenReturn(page);
        when(mapper.convertValue(any(), eq(RemoteWorkerDTO.class)))
                .thenReturn(any(RemoteWorkerDTO.class));
        
        List<RemoteWorkerDTO> dtos = service.getPage(pageNo, size);
        
        Assertions.assertThat(dtos.size()).isBetween(0, 8);
    }
}
