package com.tecfrac.helpdesk.service;

import com.tecfrac.helpdesk.bean.BeanPair;
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
import com.tecfrac.helpdesk.request.RequestMessageTicket;
import com.tecfrac.helpdesk.util.HashingUtil;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserPasswordRepository userpasswordRepository;
    @Autowired
    private SessionRepository sessionRepository;

    public ModelSession login(RequestLogin request, BeanSession beanSession) throws HelpDeskException {
        BeanPair<ModelUser, ModelUserPassword> login = checkPassword(request.getUsername(), request.getPassword());
        if (login.getFirst().isBlocked()) {
            throw new HelpDeskException(HttpStatus.PRECONDITION_FAILED, "this user is Blocked ");
        }
        ModelSession session = new ModelSession();
        session.setUser(login.getFirst());
        session.setDateCreation(new Date());
        session.setValid(true);
        session.setToken(UUID.randomUUID().toString());
        sessionRepository.save(session);
        beanSession.setUser(login.getFirst());
        beanSession.setToken(session.getToken());
        beanSession.setId(session.getId());
        beanSession.setValid(true);
        return session;
    }

    private BeanPair<ModelUser, ModelUserPassword> checkPassword(@RequestParam(required = true) String username, @RequestParam(required = true) String password) throws HelpDeskException {
        ModelUser userData = userRepository.findByUsername(username);
        ModelUserPassword passwordModel = null;
        if (userData == null) {
            throw new HelpDeskException(HttpStatus.UNAUTHORIZED, "Invalid username or password: ");
        }
        passwordModel = userpasswordRepository.findTopByUserIdAndValid(userData.getId(), true);
        if (passwordModel == null) {
            throw new HelpDeskException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
        if (!(passwordModel.getPassword()).equals(HashingUtil.hashString(password))) {
            throw new HelpDeskException(HttpStatus.UNAUTHORIZED, "Invalid username or password: ");
        }
        if (passwordModel.getDateExpired() != null && passwordModel.getDateExpired().before(new Date())) {
            throw new HelpDeskException(HttpStatus.FORBIDDEN, "Password Expired");
        }
        BeanPair<ModelUser, ModelUserPassword> a = new BeanPair<>(userData, passwordModel);
        return a;
    }

    public ModelUser changePassword(RequestChangePassword request) throws Exception {

        if (request.getNewPassword() == null || request.getNewPassword().isBlank()) {
            throw new HelpDeskException(HttpStatus.BAD_REQUEST, "new password is empty!");
        }
        BeanPair<ModelUser, ModelUserPassword> login = checkPassword(request.getUsername(), request.getPassword());
        login.getSecond().setValid(false);
        login.getSecond().setDateExpired(new Date());
        userpasswordRepository.save(login.getSecond());
        ModelUserPassword password = new ModelUserPassword();
        password.setPassword(HashingUtil.hashString(request.getNewPassword()));
        password.setDateCreation(new Date());
        password.setUserId(login.getFirst().getId());
        password.setValid(true);
        userpasswordRepository.save(password);

        return login.getFirst();
    }

    public ModelSession signOut(long sessionId, BeanSession beanSession) throws HelpDeskException {
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
