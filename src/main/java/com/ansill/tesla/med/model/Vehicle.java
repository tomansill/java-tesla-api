package com.ansill.tesla.med.model;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Immutable
public class Vehicle{

    @Nonnull
    private final String id;

    @Nonnull
    private final String vin;

    @Nonnull
    private final String displayName;

    @Nonnull
    private final Set<String> optionCodes;

    @Nonnull
    private final Set<String> tokens;

    @Nonnull
    private final State state;

    private final boolean inService;

    private final boolean calendarEnabled;

    private final int apiVersion;

    /* Those values are always null - revisit this when we actually have values other than null
    @Nullable
    private final String color;

    @Nullable
    private final String backseatToken;

    @Nullable
    private final String backseatTokenUpdatedAt;
     */

    private Vehicle(
            @Nonnull String id,
            @Nonnull String vin,
            @Nonnull String displayName,
            @Nonnull Set<String> optionCodes,
            @Nonnull Set<String> tokens,
            @Nonnull State state,
            boolean inService,
            boolean calendarEnabled,
            int apiVersion
    ){
        this.id = id;
        this.vin = vin;
        this.displayName = displayName;
        this.optionCodes = optionCodes;
        this.tokens = tokens;
        this.state = state;
        this.inService = inService;
        this.calendarEnabled = calendarEnabled;
        this.apiVersion = apiVersion;
    }

    @Nonnull
    public static Vehicle convert(@Nonnull com.ansill.tesla.low.model.VehicleResponse vehicle){
        return convert(vehicle.getResponse());
    }

    @Nonnull
    public static Vehicle convert(@Nonnull com.ansill.tesla.low.model.Vehicle vehicle){

        // Clean up option codes
        Set<String> optionCodes = new HashSet<>(Arrays.asList(vehicle.getOptionCodes().split(",")));

        // Parse state
        State state = State.valueOf(vehicle.getState().toUpperCase());

        // Create object and return it
        return new Vehicle(
                vehicle.getIdString(),
                vehicle.getVIN(),
                vehicle.getDisplayName(),
                optionCodes,
                Set.copyOf(vehicle.getTokens()),
                state,
                vehicle.isInService(),
                vehicle.isCalendarEnabled(),
                vehicle.getApiVersion()
        );
    }

    @Nonnull
    public String getId(){
        return id;
    }

    @Nonnull
    public String getVin(){
        return vin;
    }

    @Nonnull
    public String getDisplayName(){
        return displayName;
    }

    @Nonnull
    public Set<String> getOptionCodes(){
        return optionCodes;
    }

    @Nonnull
    public Set<String> getTokens(){
        return tokens;
    }

    @Nonnull
    public State getState(){
        return state;
    }

    public boolean isInService(){
        return inService;
    }

    public boolean isCalendarEnabled(){
        return calendarEnabled;
    }

    public int getAPIVersion(){
        return apiVersion;
    }

    public enum State{
        ASLEEP,
        ONLINE,
        DRIVING
        //Others??
    }
}
