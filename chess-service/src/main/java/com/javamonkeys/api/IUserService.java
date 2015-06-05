package com.javamonkeys.api;

import com.javamonkeys.dao.user.*;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public interface IUserService {

    public ResponseEntity register(String authorization);

    public ResponseEntity<String> login(String authorization);

    public void logout(String token);

    public User getUser(String id);

    public void deleteUser(String id) throws UserNotFoundException;

    public void updateUser(String id, User sourceUser) throws UserNotFoundException;

}
