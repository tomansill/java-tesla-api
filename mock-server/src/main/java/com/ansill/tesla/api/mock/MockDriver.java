package com.ansill.tesla.api.mock;

import com.ansill.tesla.api.data.utility.ImperialUnits;
import com.ansill.tesla.api.mock.model.MockVehicle;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import javax.annotation.Nonnull;
import javax.measure.Quantity;
import javax.measure.quantity.Acceleration;
import javax.measure.quantity.Speed;
import javax.measure.quantity.Time;
import java.time.Duration;
import java.time.Instant;
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
      if(state != null){
        state.close();
        return null;
      }

      // Return state
      return new State(mockVehicle, interval);
    });
  }

  private enum ActionState{
    STOPPED,
    ACCELERATING,
    STEADY,
    DECELERATING
  }

  private static class State implements AutoCloseable{

    @Nonnull
    private final Timer timer = new Timer();

    private final ActionState actionState = ActionState.STOPPED;

    @Nonnull
    private final MockVehicle mockVehicle;

    @Nonnull
    private final Duration interval;

    @Nonnull
    private final Quantity<Time> seconds;

    @Nonnull
    private final Instant lastStateChange = Instant.MIN;

    @Nonnull
    private final Duration durationUntilNextStateChange = Duration.ofSeconds(3);

    @Nonnull
    private final Quantity<Acceleration> acceleration = Quantities.getQuantity(0.0, Units.METRE_PER_SQUARE_SECOND);

    @Nonnull
    private final Quantity<Speed> targetSpeed = Quantities.getQuantity(0.0, Units.KILOMETRE_PER_HOUR);

    private State(@Nonnull MockVehicle mockVehicle, @Nonnull Duration interval){

      // Save vehicle
      this.mockVehicle = mockVehicle;
      this.interval = interval;
      this.seconds = Quantities.getQuantity(interval.toMillis(), Units.SECOND.divide(1000));

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

    private void process(){

      // Get time elapsed
      var elapsed = Duration.between(lastStateChange, Instant.now());

      // If elapsed, then trigger a state change
      if(elapsed.compareTo(durationUntilNextStateChange) > 0) changeState();

      // Get vehicle current speed
      var speed = Quantities.getQuantity(
        mockVehicle.getDriveState().getSpeed().orElseThrow(),
        ImperialUnits.MILE_PER_HOUR
      );

      // Determine if we need to speed up/down or not
      if(targetSpeed.equals(speed)) return;

      // Accelerate
      var speedGain = acceleration.multiply(seconds);
    }

    private void changeState(){

    }

    @Override
    public void close(){
      this.timer.cancel();
    }
  }
}
