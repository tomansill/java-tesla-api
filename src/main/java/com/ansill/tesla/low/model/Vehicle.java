package com.ansill.tesla.low.model;

import javax.annotation.concurrent.Immutable;
import java.util.Collections;
import java.util.List;

import static com.ansill.tesla.utility.Utility.simpleToString;

@SuppressWarnings({"WeakerAccess", "unused"})
@Immutable
public class Vehicle{
    protected final long id;
    protected final long vehicle_id;
    protected final String vin;
    protected final String display_name;
    protected final String option_codes;
    protected final String color;
    protected final List<String> tokens;
    protected final String state;
    protected final boolean in_service;
    protected final String id_s;
    protected final boolean calendar_enabled;
    protected final int api_version;
    protected final String backseat_token; // TODO confirm String
    protected final String backseat_token_updated_at; // TODO confirm String

    public Vehicle(
            long id,
            long vehicle_id,
            String vin,
            String display_name, String option_codes,
            String color,
            List<String> tokens,
            String state,
            boolean in_service,
            String id_s,
            boolean calendar_enabled,
            int api_version,
            String backseat_token,
            String backseat_token_updated_at
    ){
        this.id = id;
        this.vehicle_id = vehicle_id;
        this.vin = vin;
        this.display_name = display_name;
        this.option_codes = option_codes;
        this.color = color;
        this.tokens = tokens;
        this.state = state;
        this.in_service = in_service;
        this.id_s = id_s;
        this.calendar_enabled = calendar_enabled;
        this.api_version = api_version;
        this.backseat_token = backseat_token;
        this.backseat_token_updated_at = backseat_token_updated_at;
    }

    public long getId(){
        return id;
    }

    public long getVehicleId(){
        return vehicle_id;
    }

    public String getVIN(){
        return vin;
    }

    public String getOptionCodes(){
        return option_codes;
    }

    public String getColor(){
        return color;
    }

    public List<String> getTokens(){
        return Collections.unmodifiableList(tokens);
    }

    public String getState(){
        return state;
    }

    public boolean isInService(){
        return in_service;
    }

    public String getIdString(){
        return id_s;
    }

    public boolean isCalendarEnabled(){
        return calendar_enabled;
    }

    public int getApiVersion(){
        return api_version;
    }

    public String getBackseatToken(){
        return backseat_token;
    }

    public String getBackseatTokenUpdatedAt(){
        return backseat_token_updated_at;
    }

    @Override
    public String toString(){
        return simpleToString(this);
    }

    public String getDisplayName(){
        return display_name;
    }
}
