package com.tecfrac.helpdesk.service;

import com.tecfrac.helpdesk.bean.BeanSession;
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
import java.util.Arrays;
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
            user.setCompany(beanSession.getUser().getCompany());

            ModelUserGroup userGr = new ModelUserGroup();
            if (((int) userInfo.getCategory()) != ModelUserType.NewUser) {

                userGr.setGroup(groupRepository.findByNameAndCompanyId("Support", beanSession.getUser().getCompany().getId()));
                userGr.setUser(user);
                userGroupRepository.save(userGr);
            }
            System.out.println("user : " + user);
            userRepository.save(user);
            return user;
        }
        return null;

    }

    public List<PairUserInfo<String, Integer, String>> allUsers() {
        List<ModelUser> modelUser = userRepository.findAllByCompany(beanSession.getUser().getCompany());
        List<PairUserInfo<String, Integer, String>> allUsers = new ArrayList<>();
        for (ModelUser user : modelUser) {
            PairUserInfo<String, Integer, String> modeluser = new PairUserInfo(user.getUsername(), user.getId(), user.getEmail());
            allUsers.add(modeluser);
        }
        return allUsers;
    }

    public List<PairUserInfo<String, Integer, String>> allAgents() {
        Integer a[] = new Integer[]{1};
        List<ModelUser> modelUser = userRepository.findAllByUserTypeIdNotInAndCompany(Arrays.asList(a), beanSession.getUser().getCompany());
        List<PairUserInfo<String, Integer, String>> agents = new ArrayList<>();
        for (ModelUser user : modelUser) {
            PairUserInfo<String, Integer, String> modeluser = new PairUserInfo(user.getUsername(), user.getId(), user.getEmail());
            agents.add(modeluser);
        }
        return agents;
    }

}
