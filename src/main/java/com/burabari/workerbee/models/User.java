/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.models;

import com.burabari.workerbee.models.enums.UserState;
import com.burabari.workerbee.models.enums.UserType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author codebase
 */
@MappedSuperclass
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String email;
    protected String password;
    @Column(name = "first_name")
    protected String firstName;
    @Column(name = "last_name")
    protected String lastName;
    protected UserType type;
    @Column(name = "user_state")
    protected UserState userState;
    protected String phone;
    
    public User(String email, UserType type){
        this.email = email;
        this.type = type;
    }
}
