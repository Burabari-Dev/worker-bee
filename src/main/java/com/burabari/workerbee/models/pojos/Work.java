/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.models.pojos;

import java.time.LocalDate;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Subselect;

/**
 *
 * @author codebase
 */
//@Entity
@MappedSuperclass
@Data
@NoArgsConstructor
public abstract class Work {
    
    protected String title;
    protected String description;
    @Column(columnDefinition = "DATE", nullable = false)
    protected LocalDate start;
    @Column(name ="planned_end", columnDefinition = "DATE", nullable = false)
    protected LocalDate plannedEnd;
    @Column(name ="actual_end", columnDefinition = "DATE")
    protected LocalDate actualEnd;
    @Column(name = "tech_stack")
    @ElementCollection
    protected Set<String> techStack;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public Work(String title, String description, LocalDate start, 
            LocalDate plannedEnd, Set<String> techStack) {
        this.title = title;
        this.description = description;
        this.start = start;
        this.plannedEnd = plannedEnd;
        this.techStack = techStack;
    }
    
    
}
