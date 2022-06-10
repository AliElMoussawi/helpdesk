/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tecfrac.helpdesk.service;

import com.tecfrac.helpdesk.bean.BeanSession;
import com.tecfrac.helpdesk.model.ModelGroup;
import com.tecfrac.helpdesk.model.ModelTicket;
import com.tecfrac.helpdesk.model.ModelUser;
import com.tecfrac.helpdesk.model.ModelUserGroup;
import com.tecfrac.helpdesk.repository.GroupRepository;
import com.tecfrac.helpdesk.repository.UserGroupRepository;
import com.tecfrac.helpdesk.repository.UserRepository;
import com.tecfrac.helpdesk.service.AuthenticationService.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGroupService {

    @Autowired
    private BeanSession beanSession;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserGroupRepository userGroupRepository;
    @Autowired
    private UserRepository userRepository;

    public ModelUserGroup addUserGroup(Integer groupId, Integer userId) {
        ModelGroup groupExist = groupRepository.findById(groupId).get();
        ModelUser userExist = userRepository.findById(userId).get();
        if (groupExist != null && userExist != null) {
            ModelUserGroup addUserToGroup = new ModelUserGroup();
            addUserToGroup.setUser(userExist);
            addUserToGroup.setGroup(groupExist);
            userGroupRepository.save(addUserToGroup);
            return addUserToGroup;
        }
        return null;
    }

//    public HashMap<ModelGroup, List<ModelUser>> findAll() {
//        List<ModelGroup> groups = groupRepository.findAll();
//        HashMap<ModelGroup, List<ModelUser>> hash = new HashMap<ModelGroup, List<ModelUser>>();
//        for (ModelGroup group : groups) {
//            List<ModelUser> value = userGroupRepository.findAllByGroupId(group.getId());
//            hash.put(group, value);
//        }
//        return hash;
//    }
}
