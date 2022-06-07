/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecfrac.helpdesk.service;

import com.tecfrac.helpdesk.bean.BeanSession;
import com.tecfrac.helpdesk.exception.HelpDeskException;
import com.tecfrac.helpdesk.model.ModelSession;
import com.tecfrac.helpdesk.model.ModelUser;
import com.tecfrac.helpdesk.model.ModelUserPassword;
import com.tecfrac.helpdesk.repository.SessionRepository;
import com.tecfrac.helpdesk.repository.UserPasswordRepository;
import com.tecfrac.helpdesk.request.RequestLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tecfrac.helpdesk.repository.UserRepository;
import com.tecfrac.helpdesk.request.RequestChangePassword;
import com.tecfrac.helpdesk.util.HashingUtil;
import java.util.Date;
import java.util.Optional;

import java.util.UUID;
import jdk.internal.net.http.common.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author CLICK ONCE
 */
@Service
public class AuthenticationService {
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserPasswordRepository userpasswordRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private BeanSession beanSession;
    
    public static class Pair<T, S> {
        
        T first;
        S second;
        
        public Pair(T first, S second) {
            this.first = first;
            this.second = second;
        }
        
        public T getFirst() {
            return first;
        }
        
        public S getSecond() {
            return second;
        }
        
        public Pair() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }
    
    public ModelSession login(RequestLogin request) throws HelpDeskException {
        Pair<ModelUser, ModelUserPassword> login = checkPassword(request.getUsername(), request.getPassword());
        ModelSession session = new ModelSession();
        session.setUser(login.first);
        session.setDateCreation(new Date());
        session.setValid(true);
        session.setToken(UUID.randomUUID().toString());
        sessionRepository.save(session);
        beanSession.setUser(login.first);
        beanSession.setToken(session.getToken());
        beanSession.setId(session.getId());
        beanSession.setValid(true);
        return session;
    }
    
    private Pair<ModelUser, ModelUserPassword> checkPassword(@RequestParam(required = true) String username, @RequestParam(required = true) String password) throws HelpDeskException {
        ModelUser userData = userRepository.findByUsername(username);
        ModelUserPassword passowrdModel = null;
        if (userData == null) {
            throw new HelpDeskException(HttpStatus.UNAUTHORIZED, "Invalid username or password: ");
        }
        passowrdModel = userpasswordRepository.findTopByUserIdAndValid(userData.getId(), true);
        if (passowrdModel == null) {
            throw new HelpDeskException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
        if (!(passowrdModel.getPassword()).equals(HashingUtil.hashString(password))) {
            throw new HelpDeskException(HttpStatus.UNAUTHORIZED, "Invalid username or password: ");
        }
        if (passowrdModel.getDateExpired() != null && passowrdModel.getDateExpired().before(new Date())) {
            throw new HelpDeskException(HttpStatus.FORBIDDEN, "Password Expired");
        }
        Pair<ModelUser, ModelUserPassword> a = new Pair<>(userData, passowrdModel);
        return a;
    }
    
    public ModelUser changePassword(@RequestParam(required = true) RequestChangePassword request) throws Exception {
        
        if (request.getNewPassword() == null || request.getNewPassword().isBlank()) {
            throw new HelpDeskException(HttpStatus.BAD_REQUEST, "new password is empty!");
        }
        Pair<ModelUser, ModelUserPassword> login = checkPassword(request.getUsername(), request.getPassword());
        //setValid(boolean valid)
        //ModelSession update_session = new ModelSession();
        login.second.setValid(false);
        userpasswordRepository.save(login.second);
        ModelUserPassword password = new ModelUserPassword();
        password.setPassword(HashingUtil.hashString(request.getNewPassword()));
        password.setDateCreation(new Date());
        password.setUserId(login.first.getId());
        password.setValid(true);
        userpasswordRepository.save(password);
        
        return login.first;
    }
    
    public static void main(String[] args) {
        System.out.print(HashingUtil.hashString("test1"));
    }
    
    public ModelSession signOut(int sessionId) throws HelpDeskException {
        Optional<ModelSession> session = sessionRepository.findById(sessionId);
        if (session.isEmpty()) {
            throw new HelpDeskException(HttpStatus.BAD_REQUEST, "Bad Request");
        }
        session.get().setDateExpired(new Date());
        session.get().setValid(false);
        sessionRepository.save(session.get());
        beanSession.setValid(Boolean.FALSE);
        beanSession.setDateExpired(new Date());
        return session.get();
    }
}
