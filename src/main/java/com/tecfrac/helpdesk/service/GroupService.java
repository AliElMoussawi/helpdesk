/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tecfrac.helpdesk.service;

import com.tecfrac.helpdesk.bean.BeanSession;
import com.tecfrac.helpdesk.model.ModelCompany;
import com.tecfrac.helpdesk.model.ModelGroup;
import com.tecfrac.helpdesk.model.ModelUser;
import com.tecfrac.helpdesk.model.ModelUserGroup;
import com.tecfrac.helpdesk.repository.CompanyRepository;
import com.tecfrac.helpdesk.repository.GroupRepository;
import com.tecfrac.helpdesk.repository.UserGroupRepository;
import com.tecfrac.helpdesk.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    @Autowired
    private BeanSession beanSession;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserGroupRepository userGroupRepository;
    @Autowired
    private UserRepository userRepository;

    public ModelGroup addGroup(Integer companyId, String groupName) {
        ModelGroup groupExist = groupRepository.findByName(groupName);
        if (groupExist == null) {
            ModelGroup newGroup = new ModelGroup();
            newGroup.setCompany(companyRepository.findById(companyId).get());
            newGroup.setName(groupName);

            groupRepository.save(newGroup);
            return newGroup;
        }
        return null;
    }

    public List<ModelGroup> findAll() {
        return groupRepository.findAll();
    }

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

}
