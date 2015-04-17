package com.javamonkeys.dao.user;

public class IncorrectUserCredentialsException extends Exception {

    public IncorrectUserCredentialsException(String msg) {
        super(msg);
    }

    public IncorrectUserCredentialsException(){
        super("Incorrect user credentials!");
    }
}
