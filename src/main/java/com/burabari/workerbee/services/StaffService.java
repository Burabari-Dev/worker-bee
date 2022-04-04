/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.services;

import com.burabari.workerbee.models.Staff;
import com.burabari.workerbee.models.dtos.StaffDTO;
import com.burabari.workerbee.repos.StaffRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author codebase
 */
@Service
//@RequiredArgsConstructor(onConstructor_= {@Autowired} )
public class StaffService {
    
    private final StaffRepo repo;
    private final ObjectMapper mapper;
    
    @Autowired
    public StaffService(StaffRepo repo, ObjectMapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }

    public StaffDTO create(Staff staff) {
        Staff saved = repo.save(staff);
        StaffDTO dto = mapper.convertValue(saved, StaffDTO.class);
        return dto;
    }

    public Optional<StaffDTO> getById(long id) {
        Optional<Staff> opt = repo.findById(id);
        if(opt.isEmpty())
            return Optional.empty();
        StaffDTO dto = mapper.convertValue(opt.get(), StaffDTO.class);
        return Optional.of(dto);
    }

    public List<StaffDTO> getNext10(int pageNo) {
        Page<Staff> page = repo.findAll(PageRequest.of(pageNo, 10));
        return page.stream().map(s -> mapper.convertValue(s, StaffDTO.class))
                .collect(Collectors.toList());
    }

    public boolean update(Staff staff) throws Exception{
        if(staff == null)
            throw new NullPointerException();
        if(! repo.existsById(staff.getId()))
            return false;
        repo.save(staff);
        return true;
    }

    public boolean delete(Long id) throws Exception{
        if(repo.existsById(id)){
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}
