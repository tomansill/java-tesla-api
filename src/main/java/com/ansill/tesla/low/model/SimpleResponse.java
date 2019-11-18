package com.ansill.tesla.low.model;

import javax.annotation.concurrent.Immutable;

@SuppressWarnings("unused")
@Immutable
public class SimpleResponse{

    private final boolean result;
    private final String reason;

    public SimpleResponse(boolean result, String reason){
        this.result = result;
        this.reason = reason;
    }

    public boolean getResult(){
        return result;
    }

    public String getReason(){
        return reason;
    }
}
