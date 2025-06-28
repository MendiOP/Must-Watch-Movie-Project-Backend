package org.mircroservice.mustwatchbackend.Authentication;

import lombok.RequiredArgsConstructor;
import org.mircroservice.mustwatchbackend.entity.User;
import org.mircroservice.mustwatchbackend.entity.UserPrincipal;
import org.mircroservice.mustwatchbackend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }
    return new UserPrincipal(user);
  }
}
