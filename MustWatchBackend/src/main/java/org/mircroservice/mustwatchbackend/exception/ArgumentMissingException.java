package org.mircroservice.mustwatchbackend.exception;

public class ArgumentMissingException extends IllegalArgumentException{
    public ArgumentMissingException(String message) {
        super(message);
    }
}
