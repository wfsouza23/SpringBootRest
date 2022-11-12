package com.example.wesley.services.exceptions;

public class DataIntegrityException extends RuntimeException{
    private static final long serialVersionUID = 1l;

    public DataIntegrityException(String msg){
        super(msg);
    }

    public DataIntegrityException(String msg, Throwable cause){
        super(msg, cause);
    }
}
