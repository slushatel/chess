package com.javamonkeys.UserDAO;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String msg) {
        super(msg);
    }

    public UserNotFoundException(){
        super("User not found!");
    }

}
