package com.tecfrac.helpdesk.repository;

import com.tecfrac.helpdesk.model.ModelTicketMessage;
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

}
