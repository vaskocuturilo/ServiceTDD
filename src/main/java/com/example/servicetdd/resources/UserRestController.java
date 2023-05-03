package com.example.servicetdd.resources;

import com.example.servicetdd.entity.UserEntity;
import com.example.servicetdd.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity getAllHandleUsers() {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(userService.getAllHandleUsers());
        } catch (Exception exception) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(exception.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody UserEntity userEntity, UriComponentsBuilder uriComponentsBuilder) {
        try {
            return ResponseEntity.created(uriComponentsBuilder
                            .path("/api/v1/users")
                            .build()
                            .toUri())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(userService.createUser(userEntity));
        } catch (Exception exception) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(exception.getMessage());
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity updateUser(@PathVariable("id") Long id, @RequestBody UserEntity userEntity) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(userService.updateUser(id, userEntity));
        } catch (Exception exception) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(exception.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("A user was delete");
        } catch (Exception exception) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(exception.getMessage());
        }
    }
}
