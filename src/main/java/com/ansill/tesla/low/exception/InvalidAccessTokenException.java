package com.ansill.tesla.low.exception;

/** Authentication Exception */
public class InvalidAccessTokenException extends RuntimeException{

    /** Serial Version ID */
    private static final long serialVersionUID = 4791355088341089720L;

    /** Message */
    private static final String MESSAGE = "Invalid Access Token";

    /**
     * Constructor that takes in emailAddress and formats exception message
     */
    public InvalidAccessTokenException(){
        super(MESSAGE);
    }
}
