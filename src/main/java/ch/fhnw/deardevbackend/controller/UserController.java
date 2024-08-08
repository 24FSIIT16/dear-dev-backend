package ch.fhnw.deardevbackend.controller;

import ch.fhnw.deardevbackend.dto.UserAndProviderDTO;
import ch.fhnw.deardevbackend.dto.UserDTO;
import ch.fhnw.deardevbackend.entities.User;
import ch.fhnw.deardevbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @GetMapping("/user-with-provider/{id}")
    public ResponseEntity<UserAndProviderDTO> getUserByProviderById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(userService.getUserWithProviderById(id));
    }

    @PutMapping("user/update")
    public void updateUser(@RequestBody UserDTO request) {
        userService.updateUser(request.getId(), request.getUsername(), request.getGithubUserName());
    }

    @DeleteMapping("user/delete/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }
}
