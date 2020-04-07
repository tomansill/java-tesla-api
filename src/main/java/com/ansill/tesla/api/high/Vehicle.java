package com.ansill.tesla.api.high;

import com.ansill.tesla.api.exception.VehicleSleepingException;
import com.ansill.tesla.api.high.exception.VehicleNotFoundException;
import com.ansill.tesla.api.high.model.BatteryState;
import com.ansill.tesla.api.high.model.ChargeAdded;
import com.ansill.tesla.api.high.model.ChargeSettings;
import com.ansill.tesla.api.high.model.ChargeState;
import com.ansill.tesla.api.high.model.ClimateSettings;
import com.ansill.tesla.api.high.model.ClimateState;
import com.ansill.tesla.api.high.model.GUISettings;
import com.ansill.tesla.api.high.model.Location;
import com.ansill.tesla.api.high.model.SentryModeState;
import com.ansill.tesla.api.high.model.VehicleConfig;
import com.ansill.tesla.api.high.model.VehicleSnapshot;
import com.ansill.tesla.api.low.exception.VehicleIDNotFoundException;
import com.ansill.tesla.api.model.CachedValue;
import com.ansill.tesla.api.model.ShiftState;
import com.ansill.validation.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.measure.quantity.Power;
import javax.measure.quantity.Speed;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

/** Vehicle class */
public class Vehicle{

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(Vehicle.class);

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
  private final CachedValue<com.ansill.tesla.api.med.model.Vehicle> cachedVehicle;

  /** Cached drive state */
  @Nonnull
  private final CachedValue<com.ansill.tesla.api.med.model.DriveState> cachedDriveState;

  /** Cached charge state */
  @Nonnull
  private final CachedValue<com.ansill.tesla.api.med.model.ChargeState> cachedChargeState;

  /** Cached vehicle state */
  @Nonnull
  private final CachedValue<com.ansill.tesla.api.med.model.VehicleState> cachedVehicleState;

  /** Cached vehicle state */
  @Nonnull
  private final CachedValue<com.ansill.tesla.api.med.model.GuiSettings> cachedGuiSettings;

  /** Cached vehicle state */
  @Nonnull
  private final CachedValue<com.ansill.tesla.api.med.model.ClimateState> cachedClimateState;

  /** Cached vehicle state */
  @Nonnull
  private final CachedValue<com.ansill.tesla.api.med.model.VehicleConfig> cachedVehicleConfig;

  /** Cached complete data */
  @Nonnull
  private final CachedValue<com.ansill.tesla.api.med.model.CompleteData> cachedCompleteData;

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
    @Nonnull com.ansill.tesla.api.med.model.Vehicle vehicle,
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
    this.cachedVehicleState = new CachedValue<>(fastChangingDataLifetime);
    this.cachedGuiSettings = new CachedValue<>(slowChangingDataLifetime);
    this.cachedClimateState = new CachedValue<>(fastChangingDataLifetime);
    this.cachedVehicleConfig = new CachedValue<>(slowChangingDataLifetime);
    this.cachedCompleteData = new CachedValue<>(fastChangingDataLifetime);
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
  static Vehicle convert(@Nonnull com.ansill.tesla.api.med.model.Vehicle vehicle, @Nonnull Account parent){
    return new Vehicle(
      vehicle.getId(),
      vehicle.getVin(),
      parent,
      vehicle,
      parent.getFastChangingDataLifetime(),
      parent.getSlowChangingDataLifetime()
    );
  }

  /**
   * Sets the cache lifetime of fast-changing data
   * <p>
   * This applies to:
   * VehicleSnapshot
   * DriveState
   * ChargeState
   * VehicleState
   * ClimateState
   * Vehicle
   *
   * @param duration lifetime
   */
  public void setFastChangingDataCacheLifetime(@Nonnull AtomicReference<Duration> duration){
    Validation.assertNonnull(duration.get(), "duration.get()");
    fastChangingDataLifetime.set(Validation.assertNonnull(duration, "duration"));
  }

  /**
   * Sets the cache lifetime of slow-changing data
   * <p>
   * This applies to:
   * GUI Settings
   * Vehicle Config
   *
   * @param duration lifetime
   */
  public void setSlowChangingDataCacheLifetime(@Nonnull AtomicReference<Duration> duration){
    Validation.assertNonnull(duration.get(), "duration.get()");
    slowChangingDataLifetime.set(Validation.assertNonnull(duration, "duration"));
  }

  /**
   * Wakes the vehicle up
   *
   * @throws VehicleNotFoundException in a rare event if vehicle gets removed from the account, this exception will be thrown
   */
  public void wakeUp(boolean lazy) throws VehicleNotFoundException{

    // Success flag
    boolean success = false;

    // Lock it
    try(var ignored = parent.getReadLock().doLock()){

      // Exception catcher
      AtomicReference<VehicleNotFoundException> exceptionCatcher = new AtomicReference<>();

      // Get iteration number
      final int iterations = lazy ? 1 : 15;


      // Set up loop to repeat until awoken or fail
      for(int i = 0; i < iterations; i++){

        // Make the call
        var vehicle = parent.performOnClient(client -> {
          try{
            return client.wakeup(parent.getToken(), id);
          }catch(VehicleIDNotFoundException e){
            exceptionCatcher.set(new VehicleNotFoundException(id));
          }
          return null;
        });

        // Check exception
        if(exceptionCatcher.get() != null) throw exceptionCatcher.get();

        // Check if vehicle state is not sleeping anymore
        if(vehicle.getState() != com.ansill.tesla.api.med.model.Vehicle.State.ASLEEP){

          // Update
          success = true;
          break;
        }

        // Otherwise sleep and try again
        if(i == 0) continue;
        Thread.sleep(Duration.ofSeconds(2).toMillis());
      }
    }catch(InterruptedException e){
      LOGGER.warn("Attempted to sleep on awaiting for vehicle wake-up, but got interrupted", e);
    }

    // Report warning if it doesn't still wake up
    if(!success) LOGGER.warn("After issuing a wake-up command, the vehicle is still asleep...");

    // Ensure that vehicle is waking up
    /*
    boolean awoke = false;
    for(int i = 0; i < 5; i++){
      try{
        this.getVehicleSnapshot();
        awoke = true;
        break;
      }catch(VehicleSleepingException e){
        try{
          Thread.sleep(Duration.ofSeconds(15).toMillisPart());
        }catch(InterruptedException ex){
          ex.printStackTrace();
        }
      }
    }

    // Report warning if it doesn't still wake up
    if(!awoke) LOGGER.warn("After issuing a wake-up command, the vehicle is still asleep...");
     */
  }

  /**
   * Gets odometer
   *
   * @return odometer
   * @throws VehicleNotFoundException in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  @Nonnull
  public Quantity<Length> getOdometer() throws VehicleNotFoundException, VehicleSleepingException{
    return getRawVehicleState().getOdometer();
  }

  /**
   * Checks if the vehicle is in service
   *
   * @return true if vehicle is in service, false if it is not
   * @throws VehicleNotFoundException    in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  public boolean isInService() throws VehicleNotFoundException, VehicleSleepingException{
    return getRawVehicle().isInService();
  }

  /**
   * Returns shift state
   *
   * @return shift state
   * @throws VehicleNotFoundException    in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  @Nonnull
  public ShiftState getShiftState() throws VehicleNotFoundException, VehicleSleepingException{
    return getRawDriveState().getShiftState();
  }


  /**
   * Returns power usage
   *
   * @return power usage
   * @throws VehicleNotFoundException    in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  @Nonnull
  public Quantity<Power> getPowerUsage() throws VehicleNotFoundException, VehicleSleepingException{
    return getRawDriveState().getPower();
  }

  /**
   * Returns vehicle speed
   *
   * @return speed
   * @throws VehicleNotFoundException    in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  @Nonnull
  public Quantity<Speed> getSpeed() throws VehicleNotFoundException, VehicleSleepingException{
    return getRawDriveState().getSpeed();
  }

  /**
   * Returns vehicle location
   *
   * @return location
   * @throws VehicleNotFoundException    in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  @Nonnull
  public Location getLocation() throws VehicleNotFoundException, VehicleSleepingException{
    var state = getRawDriveState();
    var heading = state.getHeading();
    var lat = state.getLatitude();
    var lon = state.getLatitude();
    return new Location(heading, lat, lon);
  }

  /**
   * Returns charge state
   *
   * @return charge state
   * @throws VehicleNotFoundException    in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  @Nonnull
  public ChargeState getChargeState() throws VehicleNotFoundException, VehicleSleepingException{
    return ChargeState.convert(getRawChargeState());
  }

  /**
   * Returns charge added information
   *
   * @return charge added information
   * @throws VehicleNotFoundException    in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  @Nonnull
  public ChargeAdded getChargeAdded() throws VehicleNotFoundException, VehicleSleepingException{
    return ChargeAdded.convert(getRawChargeState());
  }

  /**
   * Returns vehicle config
   *
   * @return vehicle config
   * @throws VehicleNotFoundException    in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  @Nonnull
  public VehicleConfig getVehicleConfig() throws VehicleNotFoundException, VehicleSleepingException{
    return VehicleConfig.convert(getRawVehicleConfig());
  }


  /**
   * Returns GUI settings
   *
   * @return settings
   * @throws VehicleNotFoundException    in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  @Nonnull
  public GUISettings getGUISettings() throws VehicleNotFoundException, VehicleSleepingException{
    return GUISettings.convert(getRawGuiSettings());
  }

  /**
   * Returns vehicle snapshot
   *
   * @return snapshot
   * @throws VehicleNotFoundException    in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  @Nonnull
  public VehicleSnapshot getVehicleSnapshot() throws VehicleNotFoundException, VehicleSleepingException{
    return VehicleSnapshot.convert(getRawCompleteVehicleData());
  }

  /**
   * Returns battery state
   *
   * @return battery state
   * @throws VehicleNotFoundException    in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  @Nonnull
  public BatteryState getBatteryState() throws VehicleNotFoundException, VehicleSleepingException{
    return BatteryState.convert(getRawChargeState());
  }

  /**
   * Returns climate settings
   *
   * @return climate settings
   * @throws VehicleNotFoundException    in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  @Nonnull
  public ClimateSettings getClimateSettings() throws VehicleNotFoundException, VehicleSleepingException{
    return ClimateSettings.convert(getRawClimateState());
  }


  /**
   * Returns climate state
   *
   * @return climate state
   * @throws VehicleNotFoundException    in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  @Nonnull
  public ClimateState getClimateState() throws VehicleNotFoundException, VehicleSleepingException{
    return ClimateState.convert(getRawClimateState());
  }

  /**
   * Returns charge settings
   *
   * @return charge settings
   * @throws VehicleNotFoundException    in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  @Nonnull
  public ChargeSettings getChargeSettings() throws VehicleNotFoundException, VehicleSleepingException{
    return ChargeSettings.convert(getRawChargeState());
  }

  /**
   * Returns vehicle
   *
   * @return charge settings
   * @throws VehicleNotFoundException    in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  @Nonnull
  private com.ansill.tesla.api.med.model.Vehicle getRawVehicle()
  throws VehicleNotFoundException, VehicleSleepingException{

    // Lock it
    try(var ignored = parent.getReadLock().doLock()){

      // Exception catcher
      AtomicReference<VehicleNotFoundException> exceptionCatcher = new AtomicReference<>();

      // Retrieve from cache if any or send new call
      var state = cachedVehicle.getOrUpdate(() -> {
        var item = parent.performOnClient(client -> client.getVehicle(parent.getToken(), id));
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
   * Returns charge state
   *
   * @return charge state
   * @throws VehicleNotFoundException    in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  @Nonnull
  private com.ansill.tesla.api.med.model.DriveState getRawDriveState()
  throws VehicleNotFoundException, VehicleSleepingException{

    // Lock it
    try(var ignored = parent.getReadLock().doLock()){

      // Exception catcher
      AtomicReference<VehicleNotFoundException> exceptionCatcher = new AtomicReference<>();

      // Retrieve from cache if any or send new call
      var state = cachedDriveState.getOrUpdate(() -> {
        try{
          return parent.performOnClientWithVehicleException(client -> client.getVehicleDriveState(
            parent.getToken(),
            id
          ));
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
   * Returns charge state
   *
   * @return charge state
   * @throws VehicleNotFoundException    in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  @Nonnull
  private com.ansill.tesla.api.med.model.ChargeState getRawChargeState()
  throws VehicleNotFoundException, VehicleSleepingException{

    // Lock it
    try(var ignored = parent.getReadLock().doLock()){

      // Exception catcher
      AtomicReference<VehicleNotFoundException> exceptionCatcher = new AtomicReference<>();

      // Retrieve from cache if any or send new call
      var state = cachedChargeState.getOrUpdate(() -> {
        try{
          return parent.performOnClientWithVehicleException(client -> client.getVehicleChargeState(
            parent.getToken(),
            id
          ));
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
   * Returns gui settings
   *
   * @return gui settings
   * @throws VehicleNotFoundException    in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  @Nonnull
  private com.ansill.tesla.api.med.model.GuiSettings getRawGuiSettings()
  throws VehicleNotFoundException, VehicleSleepingException{

    // Lock it
    try(var ignored = parent.getReadLock().doLock()){

      // Exception catcher
      AtomicReference<VehicleNotFoundException> exceptionCatcher = new AtomicReference<>();

      // Retrieve from cache if any or send new call
      var state = cachedGuiSettings.getOrUpdate(() -> {
        try{
          return parent.performOnClientWithVehicleException(client -> client.getVehicleGuiSettings(
            parent.getToken(),
            id
          ));
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
   * Returns complete data
   *
   * @return data
   * @throws VehicleNotFoundException    in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  @Nonnull
  private com.ansill.tesla.api.med.model.CompleteData getRawCompleteVehicleData()
  throws VehicleNotFoundException, VehicleSleepingException{

    // Lock it
    try(var ignored = parent.getReadLock().doLock()){

      // Exception catcher
      AtomicReference<VehicleNotFoundException> exceptionCatcher = new AtomicReference<>();

      // Retrieve from cache if any or send new call
      var state = cachedCompleteData.getOrUpdate(() -> {
        try{

          // Get data
          var data = parent.performOnClientWithVehicleException(client -> client.getVehicleCompleteData(parent
            .getToken(), id));

          // Propagate other data
          cachedVehicle.update(data.getVehicle());
          cachedVehicleConfig.update(data.getVehicleConfig());
          cachedClimateState.update(data.getClimateState());
          cachedGuiSettings.update(data.getGuiSettings());
          cachedDriveState.update(data.getDriveState());
          cachedChargeState.update(data.getChargeState());
          cachedVehicleState.update(data.getVehicleState());

          // Return data
          return data;

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
   * Returns vehicle state
   *
   * @return vehicle state
   * @throws VehicleNotFoundException    in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  @Nonnull
  private com.ansill.tesla.api.med.model.VehicleState getRawVehicleState()
  throws VehicleNotFoundException, VehicleSleepingException{

    // Lock it
    try(var ignored = parent.getReadLock().doLock()){

      // Exception catcher
      AtomicReference<VehicleNotFoundException> exceptionCatcher = new AtomicReference<>();

      // Retrieve from cache if any or send new call
      var state = cachedVehicleState.getOrUpdate(() -> {
        try{
          return parent.performOnClientWithVehicleException(client -> client.getVehicleVehicleState(
            parent.getToken(),
            id
          ));
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
   * Returns climate state
   *
   * @return climate state
   * @throws VehicleNotFoundException    in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  @Nonnull
  private com.ansill.tesla.api.med.model.ClimateState getRawClimateState()
  throws VehicleNotFoundException, VehicleSleepingException{

    // Lock it
    try(var ignored = parent.getReadLock().doLock()){

      // Exception catcher
      AtomicReference<VehicleNotFoundException> exceptionCatcher = new AtomicReference<>();

      // Retrieve from cache if any or send new call
      var state = cachedClimateState.getOrUpdate(() -> {
        try{
          return parent.performOnClientWithVehicleException(client -> client.getVehicleClimateState(
            parent.getToken(),
            id
          ));
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
   * Returns vehicle config
   *
   * @return vehicle config
   * @throws VehicleNotFoundException    in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  @Nonnull
  private com.ansill.tesla.api.med.model.VehicleConfig getRawVehicleConfig()
  throws VehicleNotFoundException, VehicleSleepingException{

    // Lock it
    try(var ignored = parent.getReadLock().doLock()){

      // Exception catcher
      AtomicReference<VehicleNotFoundException> exceptionCatcher = new AtomicReference<>();

      // Retrieve from cache if any or send new call
      var state = cachedVehicleConfig.getOrUpdate(() -> {
        try{
          return parent.performOnClientWithVehicleException(client -> client.getVehicleVehicleConfig(
            parent.getToken(),
            id
          ));
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
   * @throws VehicleNotFoundException    in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  @Nonnull
  public String getName() throws VehicleNotFoundException, VehicleSleepingException{

    // Get it and convert it to high level and return
    return getRawVehicle().getDisplayName();
  }

  /**
   * Returns vehicle's id
   *
   * @return id
   * @throws VehicleNotFoundException    in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  @Nonnull
  public String getId() throws VehicleNotFoundException, VehicleSleepingException{

    // Get it and convert it to high level and return
    return getRawVehicle().getId();
  }

  /**
   * Returns vehicle's lock state
   *
   * @return true if vehicle is locked, false if it is not
   * @throws VehicleNotFoundException    in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  public boolean isLocked() throws VehicleNotFoundException, VehicleSleepingException{

    // Get it and convert it to high level and return
    return getRawVehicleState().isLocked();
  }

  /**
   * Returns if an user is in the car
   *
   * @return true if user is inside, false if it is not
   * @throws VehicleNotFoundException    in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  public boolean isUserPresent() throws VehicleNotFoundException, VehicleSleepingException{

    // Get it and convert it to high level and return
    return getRawVehicleState().isUserPresent();
  }

  /**
   * Returns sentry mode state
   *
   * @return sentry mode state
   * @throws VehicleNotFoundException    in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  @Nonnull
  public SentryModeState getSentryModeState() throws VehicleNotFoundException, VehicleSleepingException{

    // Get it and convert it to high level and return
    return !getRawVehicleState().isSentryModeAvailable() ? SentryModeState.NOT_AVAILABLE : getRawVehicleState().isSentryMode() ? SentryModeState.ACTIVE : SentryModeState.INACTIVE;
  }

  /**
   * Returns vehicle version
   *
   * @return true if sentry mode is available, false if it is not
   * @throws VehicleNotFoundException    in a rare event if vehicle gets removed from the account, this exception will be thrown
   * @throws VehicleSleepingException when the vehicle is possibly in sleeping state, this exception will be thrown
   */
  @Nonnull
  public String getVersion() throws VehicleNotFoundException, VehicleSleepingException{

    // Get it and convert it to high level and return
    return getRawVehicleState().getCarVersion();
  }
}
