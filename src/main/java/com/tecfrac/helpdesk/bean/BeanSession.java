package com.tecfrac.helpdesk.bean;

import com.tecfrac.helpdesk.model.*;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author CLICK ONCE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BeanSession {

    // @Value("${id}")
    private Integer id;

    private ModelUser user;

    private String token;

    private Date dateCreation;

    private Date dateExpired;

    private Boolean valid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ModelUser getUser() {
        return user;
    }

    public void setUser(ModelUser user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateExpired() {
        return dateExpired;
    }

    public void setDateExpired(Date dateExpired) {
        this.dateExpired = dateExpired;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

}
