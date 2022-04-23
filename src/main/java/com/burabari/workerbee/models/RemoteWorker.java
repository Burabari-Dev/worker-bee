/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.models;

import com.burabari.workerbee.models.enums.UserType;
import java.time.LocalDate;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author codebase
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "remote_worker")
public class RemoteWorker extends User{
    
    @Column(name = "date_reg", columnDefinition = "DATE")
    private LocalDate dateRegistered;
    @OneToOne(optional = true)
    @JoinColumn(
    	name="contr_id", nullable=true, updatable=false)
    private Contract contract;
    @OneToMany(targetEntity=Job.class, mappedBy="remote_worker")
    private Set<Job> jobs;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "worker_roles", 
            joinColumns = @JoinColumn(name = "worker_id", referencedColumnName = "id"), 
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    protected Set<Role> roles;
    @Column(name = "tech_stack")
    @ElementCollection                  //-> TODO: Consider changing this to embeddable
    private Set<String> techStack;

    public RemoteWorker(String email, UserType type, LocalDate dateRegistered){
        super(email, type);
        this.dateRegistered = dateRegistered;
    }
    
    public RemoteWorker(String email, String password, UserType type, 
            LocalDate dateRegistered, Set<String> techStack) {
        super(email, type);
        this.password = password;
        this.dateRegistered = dateRegistered;
        this.techStack = techStack;
    }

}
