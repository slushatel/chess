package com.javamonkeys.api;

import com.javamonkeys.dao.user.*;
import org.springframework.http.ResponseEntity;

public interface IUserService {

    public ResponseEntity register(String authorization);

    public ResponseEntity<User> login(String authorization);

    public void logout(String id);

    public User getUser(String id);

    public ResponseEntity deleteUser(String id) throws UserNotFoundException;

    public void updateUser(String id, User sourceUser) throws UserNotFoundException;

}
