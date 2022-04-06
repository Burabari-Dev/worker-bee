/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.services;

import com.burabari.workerbee.models.Client;
import com.burabari.workerbee.models.Project;
import com.burabari.workerbee.repos.ClientsRepo;
import com.burabari.workerbee.repos.ProjectsRepo;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author codebase
 */
@Service
public class ProjectService {
    
    private final ProjectsRepo repo;
    private final ClientsRepo clientRepo;
    
    @Autowired
    public ProjectService(ProjectsRepo repo, ClientsRepo clientRepo){
        this.repo = repo;
        this.clientRepo = clientRepo;
    }
     
    /**
     * Creates a project record by adding to a Client record. The method is
     * annotated @Transactional so that the Project save and Client update 
     * (merge) operation either both succeed or both fail.
     * @param clientId Client database id
     * @param project Project to be saved
     * @return true if saved. False if no client record with given id
     */
    @Transactional
    public Optional<Project> create(long clientId, Project project){              //->    TODO: Return the Project and check for ID
        Optional<Client> clientOpt = clientRepo.findById(clientId);
        if(clientOpt.isEmpty())
            return Optional.empty();
        
        Project dbProject = repo.save(project);
        
        Client client = clientOpt.get();
        client.getProjects().add(dbProject);
        clientRepo.save(client);
        return Optional.of(project);
    }
}
