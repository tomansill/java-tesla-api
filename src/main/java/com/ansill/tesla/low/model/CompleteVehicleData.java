package com.ansill.tesla.low.model;

import javax.annotation.concurrent.Immutable;
import java.util.List;

import static com.ansill.tesla.utility.Utility.simpleToString;

@SuppressWarnings("unused")
@Immutable
public final class CompleteVehicleData extends Vehicle{

    private final GuiSettings gui_settings;

    private final DriveState drive_state;

    private final ChargeState charge_state;

    private final ClimateState climate_state;

    private final VehicleState vehicle_state;

    private final VehicleConfig vehicle_config;

    public CompleteVehicleData(
            long id,
            long vehicle_id,
            String vin,
            long user_id,
            String display_name,
            String option_codes,
            String color,
            List<String> tokens,
            String state,
            boolean in_service,
            String id_s,
            boolean calendar_enabled,
            int api_version,
            String backseat_token,
            String backseat_token_updated_at,
            GuiSettings gui_settings, DriveState drive_state, ChargeState charge_state,
            ClimateState climate_state,
            VehicleState vehicle_state, VehicleConfig vehicle_config
    ){
        super(
                id,
                vehicle_id,
                user_id,
                vin,
                display_name,
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
        this.gui_settings = gui_settings;
        this.drive_state = drive_state;
        this.charge_state = charge_state;
        this.climate_state = climate_state;
        this.vehicle_state = vehicle_state;
        this.vehicle_config = vehicle_config;
    }

    public DriveState getDriveState(){
        return drive_state;
    }

    public GuiSettings getGuiSettings(){
        return gui_settings;
    }

    public VehicleState getVehicleState(){
        return vehicle_state;
    }

    public VehicleConfig getVehicleConfig(){
        return vehicle_config;
    }

    public ChargeState getChargeState(){
        return charge_state;
    }

    public ClimateState getClimateState(){
        return climate_state;
    }

    @Override
    public String toString(){
        return simpleToString(this);
    }
}
