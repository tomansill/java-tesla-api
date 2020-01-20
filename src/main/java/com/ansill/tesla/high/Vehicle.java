package com.ansill.tesla.high;

import com.ansill.tesla.high.exception.VehicleNotFoundException;
import com.ansill.tesla.high.model.BatteryState;
import com.ansill.tesla.high.model.ChargeSettings;
import com.ansill.tesla.high.model.ChargeState;
import com.ansill.tesla.high.model.DriveState;
import com.ansill.tesla.low.exception.VehicleIDNotFoundException;
import com.ansill.tesla.model.CachedValue;
import com.ansill.validation.Validation;

import javax.annotation.Nonnull;
import javax.measure.Quantity;
import javax.measure.quantity.Length;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

/** Vehicle class */
public class Vehicle{

    /** Id of this vehicle */
    @Nonnull
    private final String id;

    /** VIN of this vehicle */
    @Nonnull
    private final String vin;

    /** Parent to call upon */
    @Nonnull
    private final Account parent;

    /** Lifetime for fast-changing data */
    @Nonnull
    private final AtomicReference<AtomicReference<Duration>> fastChangingDataLifetime;

    /** Lifetime for slow-changing data */
    @Nonnull
    private final AtomicReference<AtomicReference<Duration>> slowChangingDataLifetime;

    /** Cached vehicle data */
    @Nonnull
    private final CachedValue<com.ansill.tesla.med.model.Vehicle> cachedVehicle;

    /** Cached drive state */
    @Nonnull
    private final CachedValue<com.ansill.tesla.med.model.DriveState> cachedDriveState;

    /** Cached charge state */
    @Nonnull
    private final CachedValue<com.ansill.tesla.med.model.ChargeState> cachedChargeState;

    /**
     * Vehicle constructor
     *
     * @param id                       id
     * @param vin                      VIN
     * @param parent                   parent to call upon
     * @param fastChangingDataLifetime lifetime of fast-changing data
     * @param slowChangingDataLifetime lifetime of slow-changing data
     */
    private Vehicle(
            @Nonnull String id,
            @Nonnull String vin,
            @Nonnull Account parent,
            @Nonnull com.ansill.tesla.med.model.Vehicle vehicle,
            @Nonnull AtomicReference<AtomicReference<Duration>> fastChangingDataLifetime,
            @Nonnull AtomicReference<AtomicReference<Duration>> slowChangingDataLifetime
    ){
        this.id = id;
        this.vin = vin;
        this.fastChangingDataLifetime = fastChangingDataLifetime;
        this.slowChangingDataLifetime = slowChangingDataLifetime;
        this.parent = parent;
        this.cachedVehicle = new CachedValue<>(slowChangingDataLifetime, vehicle);
        this.cachedDriveState = new CachedValue<>(fastChangingDataLifetime);
        this.cachedChargeState = new CachedValue<>(fastChangingDataLifetime);
    }

    /**
     * Converts medium-level vehicle to high-level
     *
     * @param vehicle vehicle
     * @param parent  parent to call upon
     * @return vehicle
     * @throws VehicleNotFoundException in a rare event if vehicle gets removed from the account, this exception will be thrown
     */
    @Nonnull
    static Vehicle convert(@Nonnull com.ansill.tesla.med.model.Vehicle vehicle, @Nonnull Account parent){
        return new Vehicle(
                vehicle.getId(),
                vehicle.getVin(),
                parent,
                vehicle,
                parent.getFastChangingDataLifetime(),
                parent.getSlowChangingDataLifetime()
        );
    }

    public void setFastChangingDataCacheLifetime(@Nonnull AtomicReference<Duration> duration){
        Validation.assertNonnull(duration.get(), "duration.get()");
        fastChangingDataLifetime.set(Validation.assertNonnull(duration, "duration"));
    }

    public void setSlowChangingDataCacheLifetime(@Nonnull AtomicReference<Duration> duration){
        Validation.assertNonnull(duration.get(), "duration.get()");
        slowChangingDataLifetime.set(Validation.assertNonnull(duration, "duration"));
    }

    /**
     * Gets odometer
     *
     * @return odometer
     * @throws VehicleNotFoundException in a rare event if vehicle gets removed from the account, this exception will be thrown
     */
    @Nonnull
    public Quantity<Length> getOdometer() throws VehicleNotFoundException{
        return null;
    }

    /**
     * Checks if the vehicle is in service
     *
     * @return true if vehicle is in service, false if it is not
     * @throws VehicleNotFoundException in a rare event if vehicle gets removed from the account, this exception will be thrown
     */
    public boolean isInService() throws VehicleNotFoundException{

        // Get it and convert it to high level and return
        return getMediumVehicle().isInService();
    }

    /**
     * Returns drive state
     *
     * @return drive state
     * @throws VehicleNotFoundException in a rare event if vehicle gets removed from the account, this exception will be thrown
     */
    @Nonnull
    public DriveState getDriveState() throws VehicleNotFoundException{

        // Lock it
        com.ansill.tesla.med.model.DriveState state;
        try(var ignored = parent.getReadLock().doLock()){

            // Exception catcher
            AtomicReference<VehicleNotFoundException> exceptionCatcher = new AtomicReference<>();

            // Retrieve from cache if any or send new call
            state = cachedDriveState.getOrUpdate(() -> {
                try{
                    return parent.getClient().getVehicleDriveState(parent.getToken(), id);
                }catch(VehicleIDNotFoundException e){
                    exceptionCatcher.set(new VehicleNotFoundException(id));
                    return null;
                }
            });

            // Check exception
            if(exceptionCatcher.get() != null) throw exceptionCatcher.get();
        }

        // Get it and convert it to high level and return
        return DriveState.convert(state);
    }

    /**
     * Returns charge state
     *
     * @return charge state
     * @throws VehicleNotFoundException in a rare event if vehicle gets removed from the account, this exception will be thrown
     */
    @Nonnull
    public ChargeState getChargeState() throws VehicleNotFoundException{

        // Get it and convert it to high level and return
        return ChargeState.convert(getMediumChargeState());
    }

    /**
     * Returns battery state
     *
     * @return battery state
     * @throws VehicleNotFoundException in a rare event if vehicle gets removed from the account, this exception will be thrown
     */
    @Nonnull
    public BatteryState getBatteryState() throws VehicleNotFoundException{

        // Get it and convert it to high level and return
        return BatteryState.convert(getMediumChargeState());
    }

    /**
     * Returns charge settings
     *
     * @return charge settings
     * @throws VehicleNotFoundException in a rare event if vehicle gets removed from the account, this exception will be thrown
     */
    @Nonnull
    public ChargeSettings getChargeSettings() throws VehicleNotFoundException{

        // Get it and convert it to high level and return
        return ChargeSettings.convert(getMediumChargeState());
    }

    /**
     * Returns vehicle
     *
     * @return charge settings
     * @throws VehicleNotFoundException in a rare event if vehicle gets removed from the account, this exception will be thrown
     */
    @Nonnull
    public com.ansill.tesla.med.model.Vehicle getMediumVehicle() throws VehicleNotFoundException{

        // Lock it
        try(var ignored = parent.getReadLock().doLock()){

            // Exception catcher
            AtomicReference<VehicleNotFoundException> exceptionCatcher = new AtomicReference<>();

            // Retrieve from cache if any or send new call
            var state = cachedVehicle.getOrUpdate(() -> {
                var item = parent.getClient().getVehicle(parent.getToken(), id);
                if(item.isPresent()) return item.get();
                exceptionCatcher.set(new VehicleNotFoundException(id));
                return null;
            });

            // Check exception
            if(exceptionCatcher.get() != null) throw exceptionCatcher.get();

            // Return it
            return state;
        }
    }

    /**
     * Returns charge settings
     *
     * @return charge settings
     * @throws VehicleNotFoundException in a rare event if vehicle gets removed from the account, this exception will be thrown
     */
    @Nonnull
    public com.ansill.tesla.med.model.ChargeState getMediumChargeState() throws VehicleNotFoundException{

        // Lock it
        try(var ignored = parent.getReadLock().doLock()){

            // Exception catcher
            AtomicReference<VehicleNotFoundException> exceptionCatcher = new AtomicReference<>();

            // Retrieve from cache if any or send new call
            var state = cachedChargeState.getOrUpdate(() -> {
                try{
                    return parent.getClient().getVehicleChargeState(parent.getToken(), id);
                }catch(VehicleIDNotFoundException e){
                    exceptionCatcher.set(new VehicleNotFoundException(id));
                    return null;
                }
            });

            // Check exception
            if(exceptionCatcher.get() != null) throw exceptionCatcher.get();

            // Return it
            return state;
        }
    }

    /**
     * Returns vehicle's VIN
     *
     * @return VIN
     */
    @Nonnull
    public String getVIN(){
        return vin;
    }

    /**
     * Returns vehicle's name
     *
     * @return name
     * @throws VehicleNotFoundException in a rare event if vehicle gets removed from the account, this exception will be thrown
     */
    @Nonnull
    public String getName() throws VehicleNotFoundException{

        // Get it and convert it to high level and return
        return getMediumVehicle().getDisplayName();
    }
}
