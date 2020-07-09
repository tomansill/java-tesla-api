package com.ansill.tesla.api.test;

import com.ansill.tesla.api.data.model.ChargeState;
import com.ansill.tesla.api.data.model.ClimateState;
import com.ansill.tesla.api.data.model.CompleteVehicle;
import com.ansill.tesla.api.data.model.DriveState;
import com.ansill.tesla.api.data.model.GuiSettings;
import com.ansill.tesla.api.data.model.MediaState;
import com.ansill.tesla.api.data.model.SoftwareUpdate;
import com.ansill.tesla.api.data.model.SpeedLimitMode;
import com.ansill.tesla.api.data.model.Vehicle;
import com.ansill.tesla.api.data.model.VehicleConfig;
import com.ansill.tesla.api.data.model.VehicleState;
import com.ansill.utility.Utility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.Nonnull;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.ansill.utility.Utility.generateString;

public final class TestUtility{

  private static final Random RANDOM = new SecureRandom((Math.random() + "").getBytes());

  private static final List<String> STATES = Arrays.stream(com.ansill.tesla.api.low.model.Vehicle.State.values()).map(
    item -> item.toString().toLowerCase()).collect(Collectors.toList());


  private TestUtility(){

  }

  @Nonnull
  public static CompleteVehicle generateCompleteVehicle(){
    Collections.shuffle(STATES);
    return new CompleteVehicle(
      RANDOM.nextLong(),
      RANDOM.nextLong(),
      RANDOM.nextLong(),
      generateString(8),
      generateString(6),
      generateString(6),
      generateString(4),
      IntStream.range(0, RANDOM.nextInt(3)).mapToObj(j -> generateString(4)).collect(Collectors.toList()),
      STATES.get(0),
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      RANDOM.nextInt(),
      RANDOM.nextBoolean() ? null : generateString(9),
      RANDOM.nextBoolean() ? null : generateString(9),
      Collections.emptyMap(),
      generateClimateState(),
      generateDriveState(),
      generateChargeState(),
      generateGuiSettings(),
      generateVehicleConfig(),
      generateVehicleState()
    );
  }

  public static VehicleConfig generateVehicleConfig(){
    return new VehicleConfig(
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      generateString(33),
      generateString(32),
      generateString(32),
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      generateString(32),
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      RANDOM.nextInt(),
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      RANDOM.nextInt(),
      generateString(32),
      RANDOM.nextBoolean(),
      generateString(32),
      generateString(32),
      generateString(32),
      generateString(32),
      generateString(32),
      RANDOM.nextLong(),
      RANDOM.nextBoolean(),
      generateString(8)
    );
  }

  public static VehicleState generateVehicleState(){
    return new VehicleState(
      RANDOM.nextInt(),
      generateString(32),
      RANDOM.nextBoolean(),
      generateString(32),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      generateMediaState(),
      RANDOM.nextBoolean(),
      RANDOM.nextDouble(),
      RANDOM.nextBoolean(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      generateSoftwareUpdate(),
      generateSpeedLimitMode(),
      RANDOM.nextLong(),
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      generateString(32)
    );
  }

  private static SpeedLimitMode generateSpeedLimitMode(){
    return new SpeedLimitMode(
      RANDOM.nextBoolean(),
      RANDOM.nextDouble(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextBoolean()
    );
  }

  private static SoftwareUpdate generateSoftwareUpdate(){
    return new SoftwareUpdate(
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      generateString(32),
      generateString(21)
    );
  }

  private static MediaState generateMediaState(){
    return new MediaState(RANDOM.nextBoolean());
  }

  public static ClimateState generateClimateState(){
    return new ClimateState(
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      generateString(32),
      RANDOM.nextInt(),
      RANDOM.nextDouble(),
      RANDOM.nextInt(),
      RANDOM.nextDouble(),
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      RANDOM.nextInt(),
      RANDOM.nextDouble(),
      RANDOM.nextDouble(),
      RANDOM.nextDouble(),
      RANDOM.nextDouble(),
      RANDOM.nextBoolean(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextBoolean(),
      RANDOM.nextLong(),
      RANDOM.nextBoolean()
    );
  }

  public static ChargeState generateChargeState(){
    return new ChargeState(
      RANDOM.nextBoolean(),
      RANDOM.nextInt(),
      RANDOM.nextDouble(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextBoolean(),
      RANDOM.nextDouble(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextDouble(),
      RANDOM.nextDouble(),
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      generateString(32),
      RANDOM.nextDouble(),
      RANDOM.nextBoolean(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      generateString(32),
      generateString(32),
      RANDOM.nextDouble(),
      generateString(32),
      RANDOM.nextBoolean(),
      generateString(32),
      RANDOM.nextDouble(),
      RANDOM.nextBoolean(),
      generateString(32),
      RANDOM.nextBoolean(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean() ? null : generateString(32),
      RANDOM.nextDouble(),
      RANDOM.nextLong(),
      RANDOM.nextBoolean(),
      RANDOM.nextInt(),
      generateString(32)
    );
  }

  public static DriveState generateDriveState(){
    return new DriveState(
      RANDOM.nextLong(),
      RANDOM.nextInt(),
      RANDOM.nextDouble(),
      RANDOM.nextDouble(),
      RANDOM.nextDouble(),
      RANDOM.nextInt(),
      RANDOM.nextDouble(),
      generateString(32),
      RANDOM.nextInt(),
      RANDOM.nextBoolean() ? null : generateString(32),
      RANDOM.nextBoolean() ? null : RANDOM.nextLong(),
      RANDOM.nextLong()
    );
  }

  public static GuiSettings generateGuiSettings(){
    return new GuiSettings(
      RANDOM.nextBoolean(),
      generateString(32),
      generateString(32),
      generateString(32),
      generateString(32),
      RANDOM.nextBoolean(),
      RANDOM.nextLong()
    );
  }

  @Nonnull
  public static Vehicle generateVehicle(){
    Collections.shuffle(STATES);
    return new Vehicle(
      RANDOM.nextLong(),
      RANDOM.nextLong(),
      RANDOM.nextLong(),
      generateString(32),
      generateString(8),
      generateString(6),
      generateString(6),
      IntStream.range(0, RANDOM.nextInt(3)).mapToObj(j -> generateString(4)).collect(Collectors.toList()),
      STATES.get(0),
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      RANDOM.nextInt(),
      RANDOM.nextBoolean() ? null : generateString(9),
      RANDOM.nextBoolean() ? null : generateString(9),
      Collections.emptyMap()
    );
  }

  public static String generateEmailAddress(){
    return Utility.generateString((int) ((Math.random() * 5) + 4)) +
           "@" +
           Utility.generateString((int) ((Math.random() * 5) + 4)) +
           "." +
           Utility.generateString((int) ((Math.random() * 5) + 3));
  }

  public static String writeToJson(ObjectMapper om, Object value){
    try{
      return om.writeValueAsString(value);
    }catch(JsonProcessingException e){
      throw new RuntimeException(e);
    }
  }
}
