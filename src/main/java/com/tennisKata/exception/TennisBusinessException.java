package com.tennisKata.exception;

public class TennisBusinessException extends Exception {
    public TennisBusinessException(String errorMessage) {
        super(errorMessage);
    }
}
