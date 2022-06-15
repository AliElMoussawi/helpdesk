package com.tecfrac.helpdesk.service;

import com.tecfrac.helpdesk.bean.BeanSession;
import com.tecfrac.helpdesk.model.ModelGroup;
import com.tecfrac.helpdesk.model.ModelUser;
import com.tecfrac.helpdesk.model.ModelUserGroup;
import com.tecfrac.helpdesk.model.ModelUserType;
import com.tecfrac.helpdesk.repository.CompanyRepository;
import com.tecfrac.helpdesk.repository.GroupRepository;
import com.tecfrac.helpdesk.repository.UserGroupRepository;
import com.tecfrac.helpdesk.repository.UserRepository;
import com.tecfrac.helpdesk.repository.UserTypeRepository;
import com.tecfrac.helpdesk.request.AddUser;
import com.tecfrac.helpdesk.service.GroupService.PairUserInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private BeanSession beanSession;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserGroupRepository userGroupRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserTypeRepository userTypeRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private AuthenticationService authenticationService;

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

            authenticationService.forgetPassword(user.getEmail());
            ModelUserGroup userGr = new ModelUserGroup();
            userGr.setGroup(groupRepository.findById(ModelGroup.Default));
            userGr.setUser(user);
            userGroupRepository.save(userGr);
            return user;
        } else {
            return null;
        }
    }

    public List<PairUserInfo<String, Integer, String>> allUsers() {
        List<ModelUser> modelUser = userRepository.findAll();
        List<PairUserInfo<String, Integer, String>> allUsers = new ArrayList<PairUserInfo<String, Integer, String>>();
        for (ModelUser user : modelUser) {
            PairUserInfo<String, Integer, String> modeluser = new PairUserInfo(user.getUsername(), user.getId(), user.getEmail());
            allUsers.add(modeluser);
        }
        return allUsers;
    }

    public List<PairUserInfo<String, Integer, String>> allAgents() {
        List<ModelUser> modelUser = userRepository.findAllByUserTypeIdOrUserTypeId(2, 4);
        List<PairUserInfo<String, Integer, String>> agents = new ArrayList<PairUserInfo<String, Integer, String>>();
        for (ModelUser user : modelUser) {
            PairUserInfo<String, Integer, String> modeluser = new PairUserInfo(user.getUsername(), user.getId(), user.getEmail());
            agents.add(modeluser);
        }
        return agents;
    }

}
