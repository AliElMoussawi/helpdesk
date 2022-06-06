/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecfrac.helpdesk.repository;

import com.tecfrac.helpdesk.model.ModelTicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author CLICK ONCE
 */
public interface TicketStatusRepository extends JpaRepository<ModelTicketStatus, Integer> {

    public ModelTicketStatus findById(int statusId);

}
