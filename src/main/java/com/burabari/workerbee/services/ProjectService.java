/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.burabari.workerbee.services;

import com.burabari.workerbee.models.Client;
import com.burabari.workerbee.models.Project;
import com.burabari.workerbee.repos.ClientsRepo;
import com.burabari.workerbee.repos.ProjectsRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public Optional<Project> create(long clientId, Project project){            //->    TODO: Return the Project and check for ID
        Optional<Client> clientOpt = clientRepo.findById(clientId);
        if(clientOpt.isEmpty())
            return Optional.empty();
        
        Project dbProject = repo.save(project);
        
        Client client = clientOpt.get();
        client.getProjects().add(dbProject);
        clientRepo.save(client);
        return Optional.of(project);
    }
    
    public Optional<Project> findByProjectId(String projId){
        if(projId == null || projId.isBlank())
            return Optional.empty();
        return repo.findByProjectId(projId);
    }
    
    public List<Project> getPage(int pageNo, int pageSize){
        Page<Project> page = repo.findAll(PageRequest.of(pageNo, pageSize));
        return page.toList();
    }
    
    public boolean update(Project project){
        Optional<Project> opt = repo.findById(project.getId());
        if(opt.isEmpty())
            return false;
        Project dbProject = opt.get();
        dbProject.doUpdate(project);
        repo.save(dbProject);
        return true;
    }
    
    public boolean delete(long id){
        Optional<Project> opt = repo.findById(id);
        if(opt.isEmpty())
            return false;
        Project project = opt.get();
//        if(project.getJobs() != null && ! project.getJobs().isEmpty())        //-> TODO: Add HasDependent check for all similar
//            throw new HasDependentEntityException
        repo.delete(project);
        return true;
    }
}
