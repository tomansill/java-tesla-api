package com.ansill.tesla.low.model;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

/** Complete Vehicle Data Response */
@Immutable
public class CompleteVehicleDataResponse extends SimpleResponse<CompleteVehicleData>{

    /**
     * Response
     *
     * @param response response
     */
    public CompleteVehicleDataResponse(@Nonnull CompleteVehicleData response){
        super(response);
    }
}