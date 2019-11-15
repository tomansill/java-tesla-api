package com.ansill.tesla.low.exception;

public class APIProtocolException extends RuntimeException{
    private static final long serialVersionUID = 1790174781409056143L;

    public APIProtocolException(String message){
        super(message);
    }
}
