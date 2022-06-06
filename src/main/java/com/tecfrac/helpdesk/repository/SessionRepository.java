/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecfrac.helpdesk.repository;

import com.tecfrac.helpdesk.model.ModelSession;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CLICK ONCE
 */
@Repository
public interface SessionRepository extends JpaRepository<ModelSession, Integer> {
       Optional<ModelSession> findByTokenAndValid(String Token,Boolean Valid);
    
    List<ModelSession> findAllByUserId(long userId);
    ModelSession findByUserIdAndValid(long UserId,Boolean Valid);
    List<ModelSession> findAllByUserIdAndValid(long UserId, Boolean Valid);

}
