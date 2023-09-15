package org.football.service.imp;

import org.football.model.User;
import org.football.model.XmlUser;
import org.football.repository.UserRepository;
import org.football.repository.XmlUserRepository;
import org.football.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    XmlUserRepository xmlUserRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User create(String username, String password) throws Exception {
        if(xmlUserRepository.existsByUsername(username))
        throw new Exception("Username is already taken!");

        XmlUser xmlUser = xmlUserRepository.create(username,password);
        User user = new User(xmlUser.getId());
        user = userRepository.save(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public XmlUser findByUsername(String username) throws Exception {
        XmlUser user = xmlUserRepository.findByUsername(username);
        if (user==null) throw new Exception("User not found in file");
        return user;
    }

    @Override
    public User findById(Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(()->new Exception("User not found in database"));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
