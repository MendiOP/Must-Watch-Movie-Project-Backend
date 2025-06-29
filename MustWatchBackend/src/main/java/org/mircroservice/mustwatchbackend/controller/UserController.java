package org.mircroservice.mustwatchbackend.controller;

import lombok.RequiredArgsConstructor;
import org.mircroservice.mustwatchbackend.dto.UserRequestDTO;
import org.mircroservice.mustwatchbackend.dto.UserResponseDTO;
import org.mircroservice.mustwatchbackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {
  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
    UserResponseDTO createdUser = userService.createUser(userRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
  }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserRequestDTO userRequestDTO) {
        try {
            Map<String, String> user = userService.verify(userRequestDTO);
            return ResponseEntity.ok(user);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

}
