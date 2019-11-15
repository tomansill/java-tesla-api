package com.ansill.tesla.low.model;

import javax.annotation.concurrent.Immutable;
import java.util.List;

import static com.ansill.tesla.utility.Utility.fs;

@Immutable
public class CompleteVehicleData extends Vehicle {

    public CompleteVehicleData(
            long id,
            long vehicle_id,
            String vin,
            String option_codes,
            String color,
            List<String> tokens,
            String state,
            boolean in_service,
            String id_s,
            boolean calendar_enabled,
            int api_version,
            String backseat_token,
            String backseat_token_updated_at
    ) {
        super(id, vehicle_id, vin, option_codes, color, tokens, state, in_service, id_s, calendar_enabled, api_version, backseat_token, backseat_token_updated_at);
    }

    @Override
    public String toString() {
        return fs(
                "Vehicle(id={}, vehicle_id={}, vin={}, option_codes={}, color={}, tokens={}, state={}, "
                        + "in_service={}, id_s={}, calendar_enabled={}, backseat_token={}, backseat_token_updated_at={})",
                id,
                vehicle_id,
                vin,
                option_codes,
                color,
                tokens,
                state,
                in_service,
                id_s,
                calendar_enabled,
                api_version,
                backseat_token,
                backseat_token_updated_at
        );
    }
}
