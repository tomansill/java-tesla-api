package com.ansill.tesla.low.exception;

public class VehicleUnavailableException extends RuntimeException{

    /** Serial Version ID */
    private static final long serialVersionUID = -144385432021096541L;

    /** Message */
    private static final String MESSAGE = "Vehicle unavailable (Could be sleeping)";

    /**
     * Constructor that formats exception message
     */
    public VehicleUnavailableException(){
        super(MESSAGE);
    }
}
