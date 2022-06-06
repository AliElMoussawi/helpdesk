/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecfrac.helpdesk.exception;

import org.springframework.http.HttpStatus;

/**
 *
 * @author CLICK ONCE
 */
public class HelpDeskException extends Exception{
    HttpStatus status;
    String message;

    public HelpDeskException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    
    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
