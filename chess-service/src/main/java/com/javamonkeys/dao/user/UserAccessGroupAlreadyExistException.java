package com.javamonkeys.dao.user;

public class UserAccessGroupAlreadyExistException extends Exception {

    public UserAccessGroupAlreadyExistException(String msg) {
        super(msg);
    }

    public UserAccessGroupAlreadyExistException(){
        super("User access group with same name already exist!");
    }

}
