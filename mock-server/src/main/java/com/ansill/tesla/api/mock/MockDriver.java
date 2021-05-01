package com.ansill.tesla.api.mock;

import com.ansill.tesla.api.data.utility.ImperialUnits;
import com.ansill.tesla.api.mock.model.MockVehicle;
import tech.units.indriya.ComparableQuantity;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import javax.annotation.Nonnull;
import javax.measure.Quantity;
import javax.measure.quantity.Acceleration;
import javax.measure.quantity.Length;
import javax.measure.quantity.Speed;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class MockDriver{

  @Nonnull
  private final Duration interval;

  @Nonnull
  private final Map<MockVehicle,State> stateMap = new ConcurrentHashMap<>();

  public MockDriver(@Nonnull Duration interval){
    this.interval = interval;
  }

  public boolean toggleDriveMode(@Nonnull MockVehicle mockVehicle){
    return null == this.stateMap.compute(mockVehicle, (k, state) -> {

      // Cancel and exit
      if (state != null) {
        state.close();
        return null;
      }

      // Return state
      return new State(mockVehicle, interval);
    });
  }

  public boolean getDriveState(@Nonnull MockVehicle vehicle) {
    return this.stateMap.containsKey(vehicle);
  }

  private enum ActionState {
    MOVING,
    STOPPING
  }

  private static class State implements AutoCloseable {

    @Nonnull
    private final Timer timer = new Timer();

    @Nonnull
    private final MockVehicle mockVehicle;

    @Nonnull
    private Instant lastStateChange = Instant.now();

    @Nonnull
    private Duration durationUntilNextStateChange = Duration.ofSeconds(3);

    private double turningRate = 0.0;

    @Nonnull
    private ComparableQuantity<Acceleration> acceleration = Quantities.getQuantity(0.0, Units.METRE_PER_SQUARE_SECOND);

    @Nonnull
    private ComparableQuantity<Speed> targetSpeed = Quantities.getQuantity(0.0, Units.KILOMETRE_PER_HOUR);

    private State(@Nonnull MockVehicle mockVehicle, @Nonnull Duration interval) {

      // Save vehicle
      this.mockVehicle = mockVehicle;
      //this.seconds = Quantities.getQuantity(interval.toMillis(), Units.SECOND.divide(1000));

      // Prep the vehicle to start driving
      mockVehicle.getDriveState().setShiftState("D");
      mockVehicle.getDriveState().setSpeed(0L);

      // Set up task
      var tt = new TimerTask(){
        @Override
        public void run(){
          process();
        }
      };

      // Schedule it
      timer.scheduleAtFixedRate(tt, 0, interval.toMillis());
    }

    private void process() {

      // Get time elapsed
      var elapsed = Duration.between(lastStateChange, Instant.now());

      // If elapsed, then trigger a state change
      if (elapsed.compareTo(durationUntilNextStateChange) > 0) changeState();

      // Get drive state instance
      var driveState = mockVehicle.getDriveState();

      // Get vehicle current speed
      var speed = Quantities.getQuantity(
        driveState.getSpeed().orElseThrow(),
        ImperialUnits.MILE_PER_HOUR
      );

      // Determine if we need to speed up/down or not
      if (targetSpeed.equals(speed)) return;

      // duration
      var elapsedTime = Quantities.getQuantity(elapsed.toMillis(), Units.SECOND.divide(1_000));

      // Accelerate
      //noinspection unchecked the cast should work fine
      var speedGain = ((ComparableQuantity<Speed>) acceleration.multiply(elapsedTime)).to(Units.KILOMETRE_PER_HOUR);
      speed = speed.add(speedGain);

      // Clamp if exceed the target speed
      if (speed.isGreaterThan(targetSpeed)) speed = targetSpeed;

      // Get distance
      //noinspection unchecked
      Quantity<Length> distance = (Quantity<Length>) speed.multiply(elapsedTime);

      // Get coordinate
      var latitude = driveState.getLatitude();
      var longitude = driveState.getLongitude();
      var heading = driveState.getHeading();

      // Add heading
      heading += turningRate * elapsed.toMillis() / (1_000);

      // Clamp heading (shift heading to positive space)
      while (heading < 0) heading += 360;
      while (heading > 360) heading -= 360; // Wrap around

      // Save heading
      driveState.setHeading(heading);

      // Get x/y components
      var latMultipler = Math.sin(heading);
      var lonMultipler = Math.cos(heading);

      // Multiply distance to get new components
      var latDistance = distance.multiply(latMultipler).to(ImperialUnits.MILE).getValue().doubleValue() * 69.127;
      var lonDistance = distance.multiply(lonMultipler).to(ImperialUnits.MILE).getValue().doubleValue() * 69.127;

      // Move
      var newLat = (latitude + latDistance);
      var newLon = (longitude + lonDistance);

      // Slide into positive space
      while (newLat < -90.0) newLat += 90;
      while (newLon < -180.0) newLon += 180;
      newLat += 90.0;
      newLon += 180.0;

      // Wrap around if exceeds the limits
      while (newLat > 180.0) newLat -= 180.0;
      while (newLon > 360.0) newLon -= 360.0;

      // Slide back to normal
      newLat -= 90.0;
      newLon -= 180.0;

      // Add to location
      driveState.setLatitude(newLat);
      driveState.setLatitude(newLon);
      driveState.setNativeLatitude(newLat);
      driveState.setNativeLongitude(newLon);

      // Add to speed
      driveState.setSpeed(Math.abs(speed.to(ImperialUnits.MILE_PER_HOUR).getValue().longValue()));

      // Random power and add it
      var power = (speed.to(Units.KILOMETRE_PER_HOUR).getValue().doubleValue() / 200) * 2;
      driveState.setPower((int) power);
    }

    private void changeState() {

      // Randomize state
      var states = new ArrayList<>(Arrays.asList(ActionState.values()));
      states.add(ActionState.MOVING); // Biased towards to moving
      Collections.shuffle(states);
      var state = states.iterator().next();

      // Randomize turning rate
      var add = ((Math.random() * 4.0) - 2.0);
      turningRate += add;
      if (turningRate < -10.0) turningRate = -10.0;
      if (turningRate > 10.0) turningRate = 10.0;

      // Randomize next state change
      var seconds = (Math.random() * 30) + 1;
      this.durationUntilNextStateChange = Duration.ofSeconds((long) seconds);

      var unitOnKMPHsq = Units.METRE_PER_SQUARE_SECOND.multiply(1_000).divide(60).divide(60);

      // Switch on state
      switch (state) {
        case MOVING -> {

          // Just change target speed and acceleration
          var speedKmph = (Math.random() * 159.0) + 1;
          this.targetSpeed = Quantities.getQuantity(speedKmph, Units.KILOMETRE_PER_HOUR);
          var acceleration = (Math.random() * 2.0) + 1;
          this.acceleration = Quantities.getQuantity(acceleration, unitOnKMPHsq);

        }
        case STOPPING -> {

          // If not already stopped, then slow down
          if (!this.targetSpeed.isEquivalentTo(Quantities.getQuantity(0, Units.KILOMETRE_PER_HOUR))) {
            this.targetSpeed = Quantities.getQuantity(0, Units.KILOMETRE_PER_HOUR);
            var deceleration = -(Math.random() * 20.0) + 1;
            this.acceleration = Quantities.getQuantity(deceleration, unitOnKMPHsq);
          }
        }
      }

      // Save last state
      lastStateChange = Instant.now();
    }

    @Override
    public void close(){
      this.timer.cancel();
    }
  }
}
