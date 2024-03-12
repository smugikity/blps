package org.football.service;

import org.football.model.User;
import org.football.model.XmlUser;

import java.util.List;

public interface UserService {
    User create(String username, String password) throws Exception;

    List<User> findAll();

    XmlUser findByUsername(String username) throws Exception;

    Integer getPointByUsername(String username) throws Exception;

    User findById(Long id) throws Exception;

    User save(User user) throws Exception;
}
