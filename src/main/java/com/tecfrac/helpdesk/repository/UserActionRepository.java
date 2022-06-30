/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecfrac.helpdesk.repository;

import com.tecfrac.helpdesk.model.ModelUserAction;
import com.tecfrac.helpdesk.model.ModelUserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActionRepository extends JpaRepository<ModelUserAction, Long> {

    ModelUserType findById(long id);

    public ModelUserAction findByUserTypeIdAndActionId(Long userType, Long id);

}
