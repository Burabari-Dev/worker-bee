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
     * Creates a project record by adding to a Client record.
     * @param clientId Client database id
     * @param project Project to be saved
     * @return true if saved. False if no client record with given id
     */
    public boolean create(long clientId, Project project){              //->    TODO: Return the Project and check for ID
        Optional<Client> clientOpt = clientRepo.findById(clientId);
        if(clientOpt.isEmpty())
            return false;
        Client client = clientOpt.get();
        client.getProjects().add(project);
        clientRepo.save(client);
        return true;
    }
}
