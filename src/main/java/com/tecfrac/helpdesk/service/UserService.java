/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecfrac.helpdesk.service;

import com.tecfrac.helpdesk.bean.BeanSession;
import com.tecfrac.helpdesk.model.ModelUser;
import com.tecfrac.helpdesk.model.ModelUserType;
import com.tecfrac.helpdesk.repository.CompanyRepository;
import com.tecfrac.helpdesk.repository.UserRepository;
import com.tecfrac.helpdesk.repository.UserTypeRepository;
import com.tecfrac.helpdesk.request.AddUser;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CLICK ONCE
 */
@Service
public class UserService {

    @Autowired
    private BeanSession beanSession;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTypeRepository userTypeRepository;
    @Autowired
    private CompanyRepository companyRepository;

    public ModelUser addUser(AddUser userInfo) {
        boolean exist1 = userRepository.findByEmail(userInfo.getEmail()) == null;
        boolean exist2 = userRepository.findByUsername(userInfo.getUsername()) == null;
        boolean exist3 = companyRepository.findByEmail(userInfo.getEmail()) == null;
        if (exist1 && exist2 && exist3) {
            ModelUser user = new ModelUser();
            user.setEmail(userInfo.getEmail());
            user.setUsername(userInfo.getUsername());
            Optional<ModelUserType> userType = userTypeRepository.findById(userInfo.getCategory());
            user.setUserType(userType.get());
            if (((int) userInfo.getCategory()) != ModelUserType.NewUser) {
                user.setCompany(beanSession.getUser().getCompany());
            }
            userRepository.save(user);

            return user;
        } else {
            return null;
        }
    }

    public List<ModelUser> allUsers() {
        return userRepository.findAll();
    }

  
}
