package ch.fhnw.deardevbackend.controller;

import ch.fhnw.deardevbackend.entities.User;
import ch.fhnw.deardevbackend.services.UserService;
import ch.fhnw.deardevbackend.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/token")
    public ResponseEntity<String> generateToken(@RequestParam String email, @RequestParam int id) {
        Optional<User> userOptional = userService.findUserByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getId() == id) {
                String token = jwtUtil.generateToken(user);
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(401).body("User ID does not match.");
            }
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }
}
