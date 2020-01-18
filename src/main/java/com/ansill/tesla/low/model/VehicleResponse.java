package com.ansill.tesla.low.model;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

/** Complete Vehicle Data Response */
@Immutable
public class VehicleResponse extends SimpleResponse<Vehicle>{

    /**
     * Response
     *
     * @param response response
     */
    public VehicleResponse(@Nonnull Vehicle response){
        super(response);
    }
}