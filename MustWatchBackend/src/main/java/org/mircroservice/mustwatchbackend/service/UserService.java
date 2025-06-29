package org.mircroservice.mustwatchbackend.service;

import org.mircroservice.mustwatchbackend.dto.UserRequestDTO;
import org.mircroservice.mustwatchbackend.dto.UserResponseDTO;

public interface UserService {
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    String verify(UserRequestDTO userRequestDTO);
}
