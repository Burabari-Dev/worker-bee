/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.models.dtos;

import com.burabari.workerbee.models.Role;
import com.burabari.workerbee.models.enums.UserState;
import com.burabari.workerbee.models.enums.UserType;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author codebase
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffDTO {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private UserType type;
    private UserState userState;
    private Set<Role> roles;
    
    public StaffDTO(Long id, String email, UserType type){
        this.id = id;
        this.email = email;
        this.type = type;
    }
}
