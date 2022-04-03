/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.models;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author codebase
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "role")
public class Role {

    @Id
    private Long id;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_privileges",
            joinColumns
            = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns
            = @JoinColumn(name = "priv_id", referencedColumnName = "id")
    )
    private Set<Privilege> privileges;

    public Role(String name) {
        this.name = name;
    }
}
