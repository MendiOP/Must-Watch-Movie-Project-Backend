package org.mircroservice.mustwatchbackend.service;

import org.mircroservice.mustwatchbackend.dto.UserRequestDTO;
import org.mircroservice.mustwatchbackend.dto.UserResponseDTO;

import java.util.Map;

public interface UserService {
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    Map<String, String> verify(UserRequestDTO userRequestDTO);
}
