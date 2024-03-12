package org.football.service.imp;

import org.football.model.User;
import org.football.repository.UserRepository;
import org.football.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User create(String username, String password) throws Exception {
        if (userRepository.existsByUsername(username))
            throw new Exception("Username is already taken!");
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword);
        user = userRepository.save(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) throws Exception {
        User user = userRepository.findByUsername(username).get();
        if (user == null) throw new Exception("User not found in file");
        return user;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) throws Exception {
        String encodedPassword = passwordEncoder.encode(password);
        User user = userRepository.findByUsernameAndPassword(username, encodedPassword).get();
        if (user == null) throw new Exception("User not found in file");
        return user;
    }

    @Override
    public Integer getPointByUsername(String username) throws Exception {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new Exception("User not found in database"));
        return user.getPoint();
    }

    @Override
    public User findById(Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(() -> new Exception("User not found in database"));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
