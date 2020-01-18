package com.ansill.tesla.low.model;

import javax.annotation.concurrent.Immutable;

import static com.ansill.tesla.utility.Utility.simpleToString;

@SuppressWarnings("unused")
@Immutable
public class SimpleReasonResponse{

    private final boolean result;
    private final String reason;

    public SimpleReasonResponse(boolean result, String reason){
        this.result = result;
        this.reason = reason;
    }

    public boolean getResult(){
        return result;
    }

    public String getReason(){
        return reason;
    }

    @Override
    public String toString(){
        return simpleToString(this);
    }
}
