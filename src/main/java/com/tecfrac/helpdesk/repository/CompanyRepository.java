/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecfrac.helpdesk.repository;

import com.tecfrac.helpdesk.model.ModelCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<ModelCompany, Long> {

    public String findByEmail(String companyEmail);

    public ModelCompany findByUsername(String companyUsername);

}
