package com.javamonkeys.dao.user;

public class IncorrectUserCredentials extends Exception {

    public IncorrectUserCredentials(String msg) {
        super(msg);
    }

    public IncorrectUserCredentials(){
        super("Incorrect user credentials!");
    }
}
