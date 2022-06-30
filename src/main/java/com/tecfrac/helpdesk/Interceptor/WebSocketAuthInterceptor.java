/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecfrac.helpdesk.Interceptor;

import com.tecfrac.helpdesk.model.ModelSession;
import com.tecfrac.helpdesk.repository.SessionRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ali
 */
@Component
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    @Autowired
    SessionRepository sessionRepository;

    @Override
    public Message<?> preSend(final Message<?> message, final MessageChannel channel) {
        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//        System.out.print("message :" + message);
        if (StompCommand.CONNECT == accessor.getCommand()) {
            final String sessionId = accessor.getFirstNativeHeader("sessionId");
            Optional<ModelSession> session = sessionRepository.findById(Integer.parseInt(sessionId));//here i'm looking if there is id of a session in the table 
            UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(session.get().getUser().getUsername(), session.get().getToken());
            accessor.setUser(user);
            
        }
        return message;
    }
}
