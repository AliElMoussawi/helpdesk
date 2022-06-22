/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecfrac.helpdesk.repository;

import com.tecfrac.helpdesk.bean.BeanSession;
import com.tecfrac.helpdesk.model.ModelTicket;
import com.tecfrac.helpdesk.model.ModelUser;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CLICK ONCE
 */
@Repository
public interface TicketRepository extends JpaRepository<ModelTicket, Integer> {

    public Set<ModelTicket> findAllByValidAndAssignedGroupId(boolean b, Integer i);

    public List<ModelTicket> findAllByAssignedUserIdAndStatusIdNot(Object userId, int Solved);

    public List<ModelTicket> findAllByUpdatedAndAssignedGroupId(Object object, Integer i);

    public List<ModelTicket> findAllByAssignedGroupIdAndStatusIdNot(int gourpId, int solved);

    //   public List<ModelTicket> findAllByUpdatedNot(Date date);
    public List<ModelTicket> findAllByAssignedGroupIdAndAssignedUserIdNull(Integer i);

    public List<ModelTicket> findAllByRequesterIdAndAssignedGroupId(Integer i, Integer j);

    public List<ModelTicket> findAllByStatusIdOrStatusIdNot(int solved, int closed);

    public List<ModelTicket> findAllByStatusIdNotInAndAssignedUser(List<Integer> statusId, ModelUser userId);

    public List<ModelTicket> findAllByStatusIdAndAssignedGroupId(Integer i, Integer j);

    public List<ModelTicket> findAllByAssignedGroupIdAndStatusId(Integer groupId, int NEW);

    public void deleteByIdAndAssignedGroupId(Integer id, Integer userGroupId);

    public List<ModelTicket> findAllByStatusIdNotInAndUpdatedGreaterThanEqual(List<Integer> asList, Date recentlyUpdated);

    public List<ModelTicket> findAllByStatusIdNotInAndAssignedGroupId(List<Integer> asList, Integer userGroupId);

    @Query(value = "(select id ,sum(counter) as count from (select id,counter from user_ticket_status f union\n"
            + "(SELECT  t.status_id,if(t.status_id=1, sum(if(t.assigned_group_id=:groupId,1,0)) ,count(*)) FROM helpdesk.ticket t group by t.status_id))\n"
            + "a group by id)", nativeQuery = true)
    public List<Object[]> countAllByStatusId(@Param("groupId") Integer groupId);

    @Query(value = "SELECT \n"
            + "sum( if(t.assigned_user_id=1, if(t.assigned_group_id=1 ,1,0),0)) 'your unsolved tickets',\n"
            + " sum(if(t.assigned_user_id is null ,if(t.assigned_group_id=1 ,1,0),0))'unassigned tickets',\n"
            + " sum(if(t.assigned_group_id=1,1,0)) 'unsolved tickets'\n"
            + " FROM helpdesk.ticket t where t.status_id not in (0,4,5);", nativeQuery = true)
    public List<Object[]> countAllUnsolvedUnassigned(@Param("userId") Integer userId, @Param("groupId") Integer groupId);

}
