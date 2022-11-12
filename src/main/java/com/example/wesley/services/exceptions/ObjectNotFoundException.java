package com.example.wesley.services.exceptions;

public class ObjectNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1l;

    public ObjectNotFoundException(String msg){
        super(msg);
    }

    public ObjectNotFoundException(String msg, Throwable cause){
        super(msg, cause);
    }
}
