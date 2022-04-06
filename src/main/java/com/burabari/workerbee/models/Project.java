/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.models;

import com.burabari.workerbee.models.enums.ProjectStatus;
import com.burabari.workerbee.models.pojos.Work;
import java.time.LocalDate;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "project")
public class Project extends Work{
    @Column(name = "custom_proj_id", unique = true, nullable = false)
    private String projectId;
    private ProjectStatus status;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "client_id", nullable = true, updatable = false, insertable = false) //-> TODO: insertable = true ?? 
    public Client client;
    @OneToMany(targetEntity=Job.class, mappedBy="project", 
            orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Job> jobs;

    public Project(String projectId, ProjectStatus status, Set<String> techStack,
            String title, String description, LocalDate start, LocalDate plannedEnd) {
        super(title, description, start, plannedEnd, techStack);
        this.projectId = projectId;
        this.status = status;
    }
    
}
