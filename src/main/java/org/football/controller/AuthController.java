package org.football.controller;


import org.football.dto.RegisterDto;
import org.football.model.User;
import org.football.repository.XmlUserRepository;
import org.football.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private XmlUserRepository xmlUserRepository;

    @Autowired
    private UserServiceImp userServiceImp;

    @Autowired
    private PasswordEncoder passwordEncoder;


//    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
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
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto registerDto) {
        try {
            User user = userServiceImp.create(registerDto.getUsername(), registerDto.getPassword());
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/point")
    public ResponseEntity<?> getPoint(Authentication authentication) {
        try {
            Integer point = userServiceImp.getPointByUsername(authentication.getPrincipal().toString());
            return ResponseEntity.ok("Your points: " + point);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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
    public List<User> getAllUsers() {
        return userServiceImp.findAll();
    }
}