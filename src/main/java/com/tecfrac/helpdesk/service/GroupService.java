/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tecfrac.helpdesk.service;

import com.tecfrac.helpdesk.bean.BeanSession;
import com.tecfrac.helpdesk.model.ModelGroup;
import com.tecfrac.helpdesk.model.ModelUser;
import com.tecfrac.helpdesk.model.ModelUserGroup;
import com.tecfrac.helpdesk.repository.CompanyRepository;
import com.tecfrac.helpdesk.repository.GroupRepository;
import com.tecfrac.helpdesk.repository.UserGroupRepository;
import com.tecfrac.helpdesk.repository.UserRepository;
import com.tecfrac.helpdesk.request.RequestGroupsUsers;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tecfrac.helpdesk.service.GroupService.PairUserInfo;

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

    public List<RequestGroupsUsers> findAll() {
        List<RequestGroupsUsers> object = new ArrayList<RequestGroupsUsers>();
        List<ModelGroup> result = groupRepository.findAll();
        for (ModelGroup modelGroup : result) {
            RequestGroupsUsers e = new RequestGroupsUsers();
            e.setName(modelGroup.getName());
            e.setId(modelGroup.getId());
            List<PairUserInfo<String, Integer, String>> groupusers = new ArrayList<PairUserInfo<String, Integer, String>>();
            System.out.println(modelGroup.getUser().size());
            for (ModelUserGroup modelUserGroup : modelGroup.getUser()) {
                PairUserInfo<String, Integer, String> user = new PairUserInfo(modelUserGroup.getUser().getUsername(), modelUserGroup.getUser().getId(),modelUserGroup.getUser().getEmail());
                groupusers.add(user);
            }
            e.setUser(groupusers);
            object.add(e);
        }
        return object;
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

    public static class PairUserInfo<T, S, D> {

        T userName;
        S id;
        D email;

        public PairUserInfo(T userName, S id, D email) {
            this.userName = userName;
            this.id = id;
            this.email = email;
        }

        public T getUserName() {
            return userName;
        }

        public D getEmail() {
            return email;
        }

        public void setEmail(D email) {
            this.email = email;
        }

        public S getId() {
            return id;
        }
    }
}
