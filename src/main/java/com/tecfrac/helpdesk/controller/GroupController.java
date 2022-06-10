/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecfrac.helpdesk.controller;

import com.tecfrac.helpdesk.bean.BeanSession;
import com.tecfrac.helpdesk.model.ModelCompany;
import com.tecfrac.helpdesk.model.ModelGroup;
import com.tecfrac.helpdesk.model.ModelUserType;
import com.tecfrac.helpdesk.request.AddUser;
import com.tecfrac.helpdesk.service.CompanyService;
import com.tecfrac.helpdesk.service.GroupService;
import java.util.List;
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
@RequestMapping("/group")
//@RestController
public class GroupController {

    @Autowired
    BeanSession beanSession;
    @Autowired
    GroupService groupService;

    @RequestMapping(method = RequestMethod.POST, value = "")
    public ResponseEntity<ModelGroup> createGroup(@RequestBody AddUser request) throws Exception {
        ModelGroup newGroup = null;
        if (beanSession.getUser().getUserType().getId() == ModelUserType.Administrator) {
            newGroup = groupService.addGroup(beanSession.getUser().getCompany().getId(), request.getUsername());
            if (newGroup != null) {
                return new ResponseEntity<>(newGroup, HttpStatus.OK);
            }

        }
        return new ResponseEntity<>(newGroup, HttpStatus.ALREADY_REPORTED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ResponseEntity<List<ModelGroup>> allgroups() throws Exception {
        List<ModelGroup> allgroups = groupService.findAll();
        return new ResponseEntity<>(allgroups, HttpStatus.OK);
    }
}
