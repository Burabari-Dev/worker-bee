/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.burabari.workerbee.services;

import com.burabari.workerbee.models.Staff;
import com.burabari.workerbee.models.dtos.StaffDTO;
import java.util.List;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author codebase
 */
public interface StaffService {

    public StaffDTO create(Staff staff);

    public StaffDTO getById(long id);

    public List<StaffDTO> getNext10(int pos);

    public void update(Staff staff) throws EntityNotFoundException;

    public void delete(long id) throws Exception;
}
