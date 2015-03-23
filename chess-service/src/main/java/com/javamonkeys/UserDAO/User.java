package com.javamonkeys.UserDAO;

import java.util.Date;

public class User {

    private String eMail;
    private String login;
    private Date birthDate;
    private UserAccessGroup userAccessGroup;

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public UserAccessGroup getUserAccessGroup() {
        return userAccessGroup;
    }

    public void setUserAccessGroup(UserAccessGroup userAccessGroup) {
        this.userAccessGroup = userAccessGroup;
    }

}
