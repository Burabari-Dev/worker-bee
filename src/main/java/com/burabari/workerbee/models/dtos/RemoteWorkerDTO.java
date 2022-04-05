/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.models.dtos;

import com.burabari.workerbee.models.Job;
import com.burabari.workerbee.models.Role;
import com.burabari.workerbee.models.enums.UserState;
import com.burabari.workerbee.models.enums.UserType;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author codebase
 */
@Data
@AllArgsConstructor
public class RemoteWorkerDTO {
    private long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private UserType type;
    private UserState state;
    private LocalDate dateRegistered;
    private List<Job> jobs;
    private Set<Role> roles;
    
    public RemoteWorkerDTO(String email, UserType type, 
            LocalDate dateRegistered){
        this.email = email;
        this.type = type;
        this.dateRegistered = dateRegistered;
    }
    
    public RemoteWorkerDTO(String email, String password, UserType type, 
            LocalDate dateRegistered){
        this.email = email;
        this.password = password;
        this.type = type;
        this.dateRegistered = dateRegistered;
    }
}
