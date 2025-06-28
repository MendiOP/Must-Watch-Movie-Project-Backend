package org.mircroservice.mustwatchbackend.exception;

public class UserNotFoundException extends Exception {
  public UserNotFoundException(String message) {
    super(message);
  }
}
