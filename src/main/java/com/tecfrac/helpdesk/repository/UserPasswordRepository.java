/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecfrac.helpdesk.repository;
import com.tecfrac.helpdesk.model.ModelUserPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPasswordRepository extends JpaRepository<ModelUserPassword, Integer> {

    ModelUserPassword findByPasswordAndUserId(String password, long userId);

    ModelUserPassword findTopByUserIdAndValid(long userId,boolean valid);
}
