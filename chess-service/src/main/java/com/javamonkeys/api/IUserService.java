package com.javamonkeys.api;

import com.javamonkeys.dao.user.*;
import org.springframework.http.ResponseEntity;

public interface IUserService {

    public ResponseEntity register(String authorization);

    public ResponseEntity<User> login(String authorization);

    public ResponseEntity logout(String id);

    public ResponseEntity getUser(String id);

    public ResponseEntity deleteUser(String id);

    public ResponseEntity updateUser(String id, User sourceUser);

}
