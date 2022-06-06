/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecfrac.helpdesk.controller;

import com.tecfrac.helpdesk.bean.BeanSession;
import com.tecfrac.helpdesk.model.ModelCompany;
import com.tecfrac.helpdesk.model.ModelUser;
import com.tecfrac.helpdesk.request.AddUser;
import com.tecfrac.helpdesk.service.CompanyService;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin
@Controller
@RequestMapping("/company")
//@RestController
public class CompanyController {

    @Autowired
    BeanSession beanSession;
    @Autowired
    CompanyService companyService;

    @RequestMapping(method = RequestMethod.POST, value = "/addCompany")
    public ResponseEntity<ModelCompany> createCompany(@RequestBody AddUser request) throws Exception {
        ModelCompany newCompany = companyService.addCompany(request.getEmail(), request.getUsername());
        if (newCompany == null) {

            return new ResponseEntity<>(newCompany, HttpStatus.ALREADY_REPORTED);
        }
        return new ResponseEntity<>(newCompany, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/allCompanies")
    public ResponseEntity<List<ModelCompany>> allCompanies() throws Exception {
        List<ModelCompany> allCompanies = companyService.findAll();
        return new ResponseEntity<>(allCompanies, HttpStatus.OK);
    }

}
