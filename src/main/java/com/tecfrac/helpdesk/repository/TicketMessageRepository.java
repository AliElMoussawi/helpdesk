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
public interface TicketMessageRepository extends JpaRepository<ModelTicketMessage, Long> {

    public List<ModelTicketMessage> findAllByticketIdOrderByDateCreationDesc(Long id);

    public List<ModelTicketMessage> findAllByticketIdAndInternalNotOrderByDateCreationDesc(Long id, boolean b);

    public ModelTicketMessage findTopByticketIdAndInternalNotOrderByIdDesc(Long id, boolean b);

    public ModelTicketMessage findTopByticketIdOrderByIdDesc(Long id);

}
