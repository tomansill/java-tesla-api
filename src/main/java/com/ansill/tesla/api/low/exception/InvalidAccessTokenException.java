package com.ansill.tesla.api.low.exception;

/** Authentication Exception */
public class InvalidAccessTokenException extends RuntimeException{

    /** Serial Version ID */
    private static final long serialVersionUID = 4791355088341089720L;

    /** Message */
    private static final String MESSAGE = "Invalid Access Token";

    /**
     * Constructor that formats exception message
     */
    public InvalidAccessTokenException(){
        super(MESSAGE);
    }
}
