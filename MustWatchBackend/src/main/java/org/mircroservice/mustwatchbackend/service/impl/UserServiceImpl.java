package org.mircroservice.mustwatchbackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.mircroservice.mustwatchbackend.dto.UserRequestDTO;
import org.mircroservice.mustwatchbackend.dto.UserResponseDTO;
import org.mircroservice.mustwatchbackend.entity.User;
import org.mircroservice.mustwatchbackend.exception.UserNotFoundException;
import org.mircroservice.mustwatchbackend.repository.UserRepository;
import org.mircroservice.mustwatchbackend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final ModelMapper modelMapper;
  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;
  BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

  @Override
  public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {

    User user = modelMapper.map(userRequestDTO, User.class);
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    User savedUser = userRepository.save(user);
    UserResponseDTO userResponseDTO = modelMapper.map(savedUser, UserResponseDTO.class);
    userResponseDTO.setPassword(null);
    return userResponseDTO;
  }

  @Override
  public UserResponseDTO verify(UserRequestDTO userRequestDTO) {
      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      userRequestDTO.getUsername(), userRequestDTO.getPassword()));

      User authenticatedUser = userRepository.findByUsername(userRequestDTO.getUsername());
      UserResponseDTO userResponseDTO = modelMapper.map(authenticatedUser, UserResponseDTO.class);
      userResponseDTO.setPassword(null);
      return userResponseDTO;
  }

}
