/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.models;

import com.burabari.workerbee.models.pojos.Work;
import java.time.LocalDate;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "job")
public class Job extends Work {
    @Column(name = "custom_job_id", unique = true, nullable = false)
    private String customJobId;
    private boolean assigned;
    @Column(name = "job_value", nullable = false)
    private String jobValue;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "rem_wkr_id", nullable = true, updatable = false, insertable = false)
    public RemoteWorker remote_worker;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "proj_id", nullable = false, updatable = false, insertable = false)
    public Project project;

    public Job(String customJobId, String title, String description, Set<String> techStack,
            LocalDate start, LocalDate plannedEnd, String jobValue, Project project) {
        super(title, description, start, plannedEnd, techStack);
        this.jobValue = jobValue;
        this.project = project;
    }

}
