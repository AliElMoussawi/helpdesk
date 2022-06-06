/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tecfrac.helpdesk.service;

import com.tecfrac.helpdesk.bean.BeanSession;
import com.tecfrac.helpdesk.model.ModelCompany;
import com.tecfrac.helpdesk.repository.CompanyRepository;
import com.tecfrac.helpdesk.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    private BeanSession beanSession;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private UserRepository userRepository;

    public ModelCompany addCompany(String companyEmail, String companyName) {
        boolean exist1 = companyRepository.findByEmail(companyEmail) == null;
        boolean exist2 = companyRepository.findByUsername(companyName) == null;
        boolean exist3 = userRepository.findByEmail(companyEmail) == null;
        if (exist1 && exist2 && exist3) {

            ModelCompany company = new ModelCompany();
            company.setEmail(companyEmail);
            company.setUsername(companyName);
            companyRepository.save(company);
            return company;
        } else {
            return null;
        }
    }

    public List<ModelCompany> findAll() {
        return companyRepository.findAll();
    }
}
