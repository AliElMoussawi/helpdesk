/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecfrac.helpdesk.handler;

import com.tecfrac.helpdesk.exception.HelpDeskException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author CLICK ONCE
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(HelpDeskException.class)
    public ResponseEntity handleException(HelpDeskException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(e.getMessage());
    }
}
