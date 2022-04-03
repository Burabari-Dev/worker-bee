/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.controllers;

import com.burabari.workerbee.models.Staff;
import com.burabari.workerbee.models.dtos.StaffDTO;
import com.burabari.workerbee.services.StaffService;
import com.burabari.workerbee.services.StaffServiceImpl;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author codebase
 */
@RequestMapping("/api/staff")
@RestController
public class StaffController {
    
    @Autowired
    private StaffServiceImpl service;
    
//    public StaffController(StaffService service){
//        this.service = service;
//    }
    
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<StaffDTO> createStaff(@RequestBody Staff staff){
        StaffDTO staffDto = service.create(staff);
        return ResponseEntity.created(
                URI.create("/api/staff/"+staffDto.getId()))
                .body(staffDto);
    }
    
    @GetMapping
    public ResponseEntity<List<StaffDTO>> get10Staff(){
        List<StaffDTO> next10 = service.getNext10(0);
        return ResponseEntity.ok(next10);
    }
}
