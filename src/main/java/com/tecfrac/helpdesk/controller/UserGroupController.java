/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecfrac.helpdesk.controller;

import com.tecfrac.helpdesk.bean.BeanSession;
import com.tecfrac.helpdesk.model.ModelGroup;
import com.tecfrac.helpdesk.model.ModelUser;
import com.tecfrac.helpdesk.model.ModelUserGroup;
import com.tecfrac.helpdesk.model.ModelUserType;
import com.tecfrac.helpdesk.repository.UserRepository;
import com.tecfrac.helpdesk.request.AddUserToGroup;
import com.tecfrac.helpdesk.service.GroupService;
import com.tecfrac.helpdesk.service.UserGroupService;
import java.util.HashMap;
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
@RequestMapping("/UserGroup")
//@RestController
public class UserGroupController {

    @Autowired
    BeanSession beanSession;
    @Autowired
    GroupService groupService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserGroupService userGroupService;

//    @RequestMapping(method = RequestMethod.GET, value = "/")
//    public ResponseEntity<HashMap<ModelGroup, List<ModelUser>>> allUsergroup() throws Exception {
//       HashMap<ModelGroup, List<ModelUser>> allgroups = userGroupService.findAll();
//        return new ResponseEntity<>(allgroups, HttpStatus.OK);
//    }
    @RequestMapping(method = RequestMethod.POST, value = "/AddUserToGroup")
    public ResponseEntity<ModelUserGroup> addUserGroup(@RequestBody AddUserToGroup request) throws Exception {
        Boolean isStaff = userRepository.findById(request.getUserId()).get().getUserType().getId() != ModelUserType.NewUser;
        ModelUserGroup addedUser = null;
        if (beanSession.getUser().getUserType().getId() == ModelUserType.Administrator && isStaff) {
            addedUser = userGroupService.addUserGroup(request.getUserId(), request.getCompanyId());
            if (addedUser != null) {
                return new ResponseEntity<>(addedUser, HttpStatus.OK);
            }

        }
        return new ResponseEntity<>(addedUser, HttpStatus.BAD_REQUEST);
    }
}
