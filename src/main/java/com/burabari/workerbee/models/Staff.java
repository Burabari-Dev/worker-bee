/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.models;

import com.burabari.workerbee.models.enums.UserType;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.NoArgsConstructor;

/**
 *
 * @author codebase
 */
@Entity
@NoArgsConstructor
@Table(name = "staff")
public class Staff extends User {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "staff_roles", 
            joinColumns = @JoinColumn(name = "staff_id", referencedColumnName = "id"), 
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    protected Set<Role> roles;

    public Staff(String email, UserType type) {
        super(email, type);
    }

    public Staff(String email, String password, String firstName,
            String lastName, UserType type) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
    }

}
