package com.project.aftas.Exceptions;

public class MemberAlreadyExistsException extends RuntimeException {

    public MemberAlreadyExistsException(String message) {
        super(message);
    }
}
