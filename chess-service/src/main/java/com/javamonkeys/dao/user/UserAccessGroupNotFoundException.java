package com.javamonkeys.dao.user;

public class UserAccessGroupNotFoundException extends Exception {

    public UserAccessGroupNotFoundException(String msg) {
        super(msg);
    }

    public UserAccessGroupNotFoundException(){
        super("User access group not found!");
    }

}
