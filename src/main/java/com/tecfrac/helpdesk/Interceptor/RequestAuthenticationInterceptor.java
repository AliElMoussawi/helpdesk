/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecfrac.helpdesk.Interceptor;

import com.tecfrac.helpdesk.bean.BeanSession;
import com.tecfrac.helpdesk.exception.HelpDeskException;
import com.tecfrac.helpdesk.model.ModelSession;
import com.tecfrac.helpdesk.repository.SessionRepository;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNullFields;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author CLICK ONCE
 */
@CrossOrigin
@Component
public class RequestAuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    BeanSession beanSession;//we create here the BeanSession which will became the singelton thus when i autowired it the code will be connected to that fucntion
    // and the data will stay inserted in that class thus i can use that data from any class that is autowired it 

    @Override//This is used to perform operations before sending the request to the controller. This method should return true to return the response to the clients
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //HttpServletRequest httpRequest = (HttpServletRequest) request;
        System.out.println("com.tecfrac.helpdesk.Interceptor.RequestHeaderInterceptor.preHandle() " + request.getRequestURI());
        System.out.println(request.getHeaderNames());
        String httpToken = request.getHeader("token");//here i askeed for user to insert data in the header of the request you can checkthe postmon and you will get the \
        // result 
        String sessionId = request.getHeader("sessionId");// same as above
        System.out.println("session:" + sessionId);
        System.out.println("token:" + httpToken);

        Optional<ModelSession> session = sessionRepository.findById(Integer.parseInt(sessionId));//here i'm looking if there is id of a session in the table 
        System.out.println("" + sessionId + "," + Integer.parseInt(sessionId) + "if " + session.isPresent());

        if (session.isPresent() && session.get().getToken().equals(httpToken)) {//after checking if the the given token is the same of the of the session id 
            //we insert all the data in that bean that we autowired in the first part 
            beanSession.setDateCreation(session.get().getDateCreation());
            beanSession.setDateExpired(session.get().getDateExpired());
            beanSession.setId(session.get().getId());
            beanSession.setUser(session.get().getUser());
            beanSession.setValid(session.get().getValid());
            return true;
        }
        throw new HelpDeskException(HttpStatus.FORBIDDEN, "Password Expired");
    }

    @Override // This is used to perform operations before sending the response to the client.
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }

    @Override //This is used to perform operations after completing the request and response
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception exception) throws Exception {
    }
}
