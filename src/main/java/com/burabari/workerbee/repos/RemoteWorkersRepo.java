/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.burabari.workerbee.repos;

import com.burabari.workerbee.models.RemoteWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author codebase
 */
@Repository
public interface RemoteWorkersRepo extends JpaRepository<RemoteWorker, Long>{
    
}
