package com.tecfrac.helpdesk.repository;

import com.tecfrac.helpdesk.model.ModelTicketMessage;
import com.tecfrac.helpdesk.model.ModelUser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CLICK ONCE
 */
@Repository
public interface TicketMessageRepository extends JpaRepository<ModelTicketMessage, Integer> {

    public List<ModelTicketMessage> findAllByticketIdOrderByDateCreationDesc(Integer id);

    public List<ModelTicketMessage> findAllByticketIdAndInternalNotOrderByDateCreationDesc(Integer id, boolean b);

    public ModelTicketMessage findTopByticketIdAndUserOrderByIdDesc(Integer id, ModelUser user);

    public ModelTicketMessage findTopByticketIdAndUserAndInternalNotOrderByIdDesc(Integer id, ModelUser user, boolean b);

}
