package com.leanlogistics.squotem.webapp.screenobjects;

import org.hibernate.validator.constraints.NotEmpty;

public class Login {

    @NotEmpty
    private String userName;
    @NotEmpty
    private String userPassword;
    

    public Login() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;        
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    
}
