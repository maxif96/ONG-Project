package com.alkemy.ong.exception;

public class UnauthorizedException extends Exception {
    public UnauthorizedException(String errorMessage) {
        super(errorMessage);
    }
}
