package com.javamonkeys.api;

import com.javamonkeys.dao.user.*;
import org.springframework.http.ResponseEntity;

public interface IUserService {

    public ResponseEntity register(String authorization);

    public ResponseEntity<User> login(String authorization);

    public ResponseEntity logout(Integer id);

    public ResponseEntity<User> getUser(Integer id);

    public ResponseEntity deleteUser(Integer id);

    public ResponseEntity updateUser(Integer id, User sourceUser);

}
