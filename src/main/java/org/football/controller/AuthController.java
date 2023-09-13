package org.football.controller;


import org.football.model.User;
import org.football.dto.RegisterDto;
import org.football.model.XmlUser;
import org.football.repository.UserRepository;
import org.football.repository.XmlUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private XmlUserRepository xmlUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

//    @PostMapping("/login")
//    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
//        val authentication: Authentication = authenticationManager.authenticate(
//                UsernamePasswordAuthenticationToken(loginDto.username, loginDto.password)
//        )
//        SecurityContextHolder.getContext().authentication = authentication
//        try {
//            request.login(loginDto.getUsername(), loginDto.getPassword());
//        } catch (ServletException e) {
//            System.out.println("Invalid username or password");
//        }
//        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
//    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto registerDto){

        // add check for username exists in a DB
        if(xmlUserRepository.existsByUsername(registerDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        XmlUser xmlUser = xmlUserRepository.create(registerDto.getUsername(),registerDto.getPassword());
        User user = new User(xmlUser.getId());
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

//    @PostMapping("/logout")
//    public ResponseEntity<String> logoutUser(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
//         Invalidate the session
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            session.invalidate();
//        }
//        // Clear authentication details
//        SecurityContextHolder.clearContext();
//        this.logoutHandler.logout(request, response, authentication);
//        return new ResponseEntity<>("User logged out successfully.", HttpStatus.OK);
//    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}