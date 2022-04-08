/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.models;

import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "client_id")
    private String clientId;
    private String name;
    @ElementCollection
    private List<String> emails;
    @ElementCollection
    private List<String> phoneNos;
    @OneToMany(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            targetEntity=Project.class, 
            mappedBy="client", 
            fetch = FetchType.LAZY)
    private Set<Project> projects;
    

    public Client(String clientId, String name, List<String> emails, 
            List<String> phoneNos) {
        this.clientId = clientId;
        this.name = name;
        this.emails = emails;
        this.phoneNos = phoneNos;
    }
    
}
