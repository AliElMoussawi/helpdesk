package com.tecfrac.helpdesk.repository;

import com.tecfrac.helpdesk.bean.BeanPair;
import com.tecfrac.helpdesk.model.ModelGroup;
import com.tecfrac.helpdesk.model.ModelTicket;
import com.tecfrac.helpdesk.model.ModelUser;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CLICK ONCE
 */
@Repository
public interface TicketRepository extends JpaRepository<ModelTicket, Long> {

    public List<ModelTicket> findAllByAssignedGroupInAndStatusId(List<ModelGroup> i, int j);

    public void deleteByAssignedGroupInAndId(List<ModelGroup> groups, Long id);

    public List<ModelTicket> findAllByStatusIdNotInAndUpdatedGreaterThanEqual(List<Integer> asList, Date recentlyUpdated);

    public List<ModelTicket> findAllByStatusIdNotInAndAssignedGroupId(List<Integer> asList, Long i);

    @Query(value = "(select new com.tecfrac.helpdesk.bean.BeanPair<Long, Long>(id ,sum(counter)) as count from (select id,counter from user_ticket_status f union\n"
            + "(SELECT  t.status_id,if(t.status_id=1, sum(if(t.assigned_group_id IN:groupId,1,0)) ,count(*)) FROM helpdesk.ticket t group by t.status_id))\n"
            + "a group by id)", nativeQuery = true)
    public List<BeanPair<Long, Long>> countAllByStatusId(@Param("groupId") List<ModelGroup> groupId);

    @Query(value = "SELECT \n"
            + "sum( if(t.assigned_user_id=:userId, if(t.assigned_group_id IN:groupId ,1,0),0)) 'your unsolved tickets',\n"
            + " sum(if(t.assigned_user_id is null ,if(t.assigned_group_id IN:groupId ,1,0),0))'unassigned tickets',\n"
            + " sum(if(t.assigned_group_id IN:groupId,1,0)) 'unsolved tickets'\n"
            + " FROM helpdesk.ticket t where t.status_id not in (0,4,5);", nativeQuery = true)
    public List<Object[]> countAllUnsolvedUnassigned(@Param("userId") Long userId, @Param("groupId") List<ModelGroup> groupId);

    // public List<ModelTicket> findAllByStatusIdNotInAndAssignedUser(List<Integer> asList);
    public List<ModelTicket> findAllByStatusIdNotInAndAssignedUserAndAssignedGroupId(List<Integer> asList, Object object, Long i);

    @Override
    public void deleteAllInBatch();

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> ids);

    @Override
    public void deleteAllInBatch(Iterable<ModelTicket> entities);

    @Override
    public <S extends ModelTicket> List<S> saveAllAndFlush(Iterable<S> entities);

    @Override
    public <S extends ModelTicket> S saveAndFlush(S entity);

    @Override
    public <S extends ModelTicket> List<S> saveAll(Iterable<S> entities);

    @Override
    public void deleteAll();

    @Override
    public void deleteAll(Iterable<? extends ModelTicket> entities);

    @Override
    public void deleteAllById(Iterable<? extends Long> ids);

    @Override
    public void delete(ModelTicket entity);

    @Override
    public void deleteById(Long id);

    @Override
    public <S extends ModelTicket> S save(S entity);

    public List<ModelTicket> findAllByStatusIdAndRequester(Long statusId, ModelUser requester);

    public List<ModelTicket> findAllByAssignedGroupInAndRequesterId(List<ModelGroup> userGroupsId, Long id);

    public List<ModelTicket> findAllByStatusIdNotInAndAssignedUserAndAssignedGroupIn(List<Integer> asList, ModelUser user, List<ModelGroup> userGroupsId);

    public List<ModelTicket> findAllByStatusIdNotInAndAssignedGroupIn(List<Integer> asList, List<ModelGroup> userGroupsId);

    public List<ModelTicket> findAllByAssignedGroupAndStatusIdNot(int groupId, int SOLVED);

    public List<ModelTicket> findAllByStatusIdNotInAndRequester(List<Integer> statusIds, ModelUser user);

    public List<ModelTicket> findAllByStatusIdNotInAndAssignedGroupInAndUpdatedGreaterThanEqual(List<Integer> notStatus, List<ModelGroup> userGroupsId, Date recentlyUpdated);

    public List<ModelTicket> findAllByStatusIdNotInAndAssignedGroupInAndAssignedUser(List<Integer> asList, List<ModelGroup> userGroupsId, ModelUser user);

    public List<ModelTicket> findAllByStatusIdNotInAndAssignedGroupInAndAssignedUserAndUpdatedGreaterThanEqual(List<Integer> notStatus, List<ModelGroup> userGroupsId, ModelUser user, Date recentlyUpdated);

}
