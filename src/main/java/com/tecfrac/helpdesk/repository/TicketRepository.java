/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecfrac.helpdesk.repository;

import com.tecfrac.helpdesk.model.ModelTicket;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CLICK ONCE
 */
@Repository
public interface TicketRepository extends JpaRepository<ModelTicket, Integer> {

    Optional<ModelTicket> findById(Integer ticketId);

    public void deleteById(Long ticketId);

    public Set<ModelTicket> findAllByValid(boolean b);

    public List<ModelTicket> findAllByAssignedUserIdAndStatusIdNot(Object userId, int Solved);

    public List<ModelTicket> findAllByUpdated(Object object);

    public List<ModelTicket> findAllByAssignedGroupIdAndStatusIdNot(int gourpId, int Solved);

    public List<ModelTicket> findAllByStatusId(int statusId);

    public List<ModelTicket> findAllByUpdatedNot(Date date);

    public List<ModelTicket> findAllByAssignedUserIdNull();

    public List<ModelTicket> findAllByRequesterId(Integer RequesterId);

    public List<ModelTicket> findAllByStatusIdOrStatusIdNot(int Solved, int Closed);

    public List<ModelTicket> findAllByAssignedUserIdNullAndStatusIdOrStatusIdNot(int Solved, int Closed);

    public List<ModelTicket> findAllByAssignedUserIdAndStatusIdOrStatusIdNot(Integer userId, int Solved, int Closed);
}
