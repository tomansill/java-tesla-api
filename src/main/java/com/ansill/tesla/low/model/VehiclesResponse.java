package com.ansill.tesla.low.model;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.Collections;
import java.util.List;

import static com.ansill.tesla.utility.Utility.simpleToString;

@Immutable
public class VehiclesResponse{

    /** Response */
    @Nonnull
    private final List<Vehicle> response;

    /** Count */
    private final int count;

    /**
     * Constructor
     *
     * @param response response
     * @param count    count
     */
    public VehiclesResponse(@Nonnull List<Vehicle> response, @Nonnegative int count){
        this.response = response;
        this.count = count;
    }

    /**
     * Returns count of vehicles
     *
     * @return count
     */
    @Nonnegative
    public int getCount(){
        return count;
    }

    /**
     * Returns response item
     *
     * @return response
     */
    @Nonnull
    public List<Vehicle> getResponse(){
        return Collections.unmodifiableList(response);
    }

    @Override
    public String toString(){
        return simpleToString(this);
    }
}