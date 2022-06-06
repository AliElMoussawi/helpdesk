/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecfrac.helpdesk.request;

import java.util.Date;

/**
 *
 * @author CLICK ONCE
 */
public class RequestEndTicket {
    
    
    private String token ;
    private Date EndingDate;

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the EndingDate
     */
    public Date getEndingDate() {
        return EndingDate;
    }

    /**
     * @param EndingDate the EndingDate to set
     */
    public void setEndingDate(Date EndingDate) {
        this.EndingDate = EndingDate;
    }
    
 }
