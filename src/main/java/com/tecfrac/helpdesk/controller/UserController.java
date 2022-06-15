package com.tecfrac.helpdesk.controller;

import com.tecfrac.helpdesk.bean.BeanSession;
import com.tecfrac.helpdesk.model.ModelUser;
import com.tecfrac.helpdesk.request.AddUser;
import com.tecfrac.helpdesk.service.GroupService.PairUserInfo;
import com.tecfrac.helpdesk.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
//@RestController
public class UserController {

    @Autowired
    BeanSession beanSession;
    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<ModelUser> createUser(@RequestBody AddUser request) throws Exception {
        ModelUser newUser = userService.addUser(request);
        if (newUser == null) {
            return new ResponseEntity<>(newUser, HttpStatus.ALREADY_REPORTED);
        }
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ResponseEntity<List<PairUserInfo<String, Integer, String>>> allUsers() throws Exception {
        List<PairUserInfo<String, Integer, String>> allUsers = userService.allUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/allagents")
    public ResponseEntity<List<PairUserInfo<String, Integer, String>>> allAgents() throws Exception {
        List<PairUserInfo<String, Integer, String>> allAgents = userService.allAgents();
        return new ResponseEntity<>(allAgents, HttpStatus.OK);
    }
}
