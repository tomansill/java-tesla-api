package com.ansill.tesla.api.test;

import com.ansill.tesla.api.low.model.ChargeState;
import com.ansill.tesla.api.low.model.ClimateState;
import com.ansill.tesla.api.low.model.CompleteVehicleData;
import com.ansill.tesla.api.low.model.DriveState;
import com.ansill.tesla.api.low.model.GuiSettings;
import com.ansill.tesla.api.low.model.Vehicle;
import com.ansill.tesla.api.low.model.VehicleConfig;
import com.ansill.tesla.api.low.model.VehicleState;
import com.ansill.utility.Utility;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

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

  private static final List<String> STATES = Arrays.stream(com.ansill.tesla.api.med.model.Vehicle.State.values()).map(
    item -> item.toString().toLowerCase()).collect(Collectors.toList());


  private TestUtility(){

  }

  @Nonnull
  public static CompleteVehicleData generateCompleteVehicle(){
    Collections.shuffle(STATES);
    return new CompleteVehicleData(
      RANDOM.nextLong(),
      RANDOM.nextLong(),
      generateString(32),
      RANDOM.nextLong(),
      generateString(8),
      generateString(6),
      generateString(6),
      IntStream.range(0, RANDOM.nextInt(3)).mapToObj(j -> generateString(4)).collect(Collectors.toList()),
      STATES.get(0),
      RANDOM.nextBoolean(),
      generateString(32),
      RANDOM.nextBoolean(),
      RANDOM.nextInt(),
      null,
      null,
      generateGuiSettings(),
      generateDriveState(),
      generateChargeState(),
      generateClimateState(),
      generateVehicleState(),
      generateVehicleConfig()
    );
  }

  private static VehicleConfig generateVehicleConfig(){
    return new VehicleConfig(
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      generateString(33),
      generateString(32),
      generateString(32),
      RANDOM.nextBoolean(),
      generateString(32),
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      RANDOM.nextInt(),
      RANDOM.nextBoolean(),
      generateString(32),
      RANDOM.nextBoolean(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextBoolean(),
      generateString(32),
      RANDOM.nextInt(),
      generateString(32),
      RANDOM.nextInt(),
      generateString(32),
      RANDOM.nextInt(),
      generateString(32),
      generateString(32),
      RANDOM.nextBoolean()
    );
  }

  private static VehicleState generateVehicleState(){
    return new VehicleState(
      RANDOM.nextInt(),
      generateString(32),
      generateString(32),
      RANDOM.nextBoolean(),
      generateString(32),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextBoolean() ? null : RANDOM.nextBoolean(),
      RANDOM.nextBoolean() ? null : RANDOM.nextInt(),
      RANDOM.nextBoolean(),
      generateString(32),
      RANDOM.nextBoolean(),
      generateMediaState(),
      RANDOM.nextBoolean(),
      RANDOM.nextDouble(),
      RANDOM.nextBoolean(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      RANDOM.nextInt(),
      RANDOM.nextBoolean(),
      generateSoftwareUpdate(),
      generateSpeedLimitMode(),
      RANDOM.nextBoolean() ? null : RANDOM.nextInt(),
      generateString(32),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextBoolean(),
      generateString(32),
      RANDOM.nextLong(),
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      generateString(32)
    );
  }

  private static VehicleState.SpeedLimitMode generateSpeedLimitMode(){
    return new VehicleState.SpeedLimitMode(
      RANDOM.nextBoolean(),
      RANDOM.nextDouble(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextBoolean()
    );
  }

  private static VehicleState.SoftwareUpdate generateSoftwareUpdate(){
    return new VehicleState.SoftwareUpdate(
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      generateString(32),
      RANDOM.nextLong(),
      generateString(21)
    );
  }

  private static VehicleState.MediaState generateMediaState(){
    return new VehicleState.MediaState(RANDOM.nextBoolean());
  }

  private static ClimateState generateClimateState(){
    return new ClimateState(
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      generateString(32),
      RANDOM.nextDouble(),
      RANDOM.nextInt(),
      RANDOM.nextBoolean() ? null : RANDOM.nextDouble(),
      RANDOM.nextBoolean() ? null : RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      generateString(32),
      RANDOM.nextDouble(),
      RANDOM.nextDouble(),
      RANDOM.nextBoolean() ? null : RANDOM.nextDouble(),
      RANDOM.nextDouble(),
      RANDOM.nextBoolean(),
      generateString(32),
      RANDOM.nextBoolean() ? null : RANDOM.nextInt(),
      RANDOM.nextBoolean() ? null : RANDOM.nextInt(),
      RANDOM.nextBoolean() ? null : RANDOM.nextInt(),
      RANDOM.nextBoolean() ? null : RANDOM.nextInt(),
      RANDOM.nextBoolean() ? null : RANDOM.nextInt(),
      RANDOM.nextBoolean() ? null : RANDOM.nextInt(),
      RANDOM.nextBoolean() ? null : RANDOM.nextInt(),
      RANDOM.nextBoolean() ? null : RANDOM.nextBoolean(),
      RANDOM.nextBoolean() ? null : RANDOM.nextBoolean(),
      RANDOM.nextBoolean() ? null : RANDOM.nextBoolean(),
      RANDOM.nextLong(),
      RANDOM.nextBoolean() ? null : RANDOM.nextBoolean(),
      RANDOM.nextInt()
    );
  }

  private static ChargeState generateChargeState(){
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
      generateString(32),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      RANDOM.nextInt(),
      generateString(32),
      generateString(32),
      RANDOM.nextDouble(),
      generateString(32),
      RANDOM.nextBoolean(),
      RANDOM.nextLong(),
      generateString(32),
      RANDOM.nextDouble(),
      RANDOM.nextBoolean(),
      generateString(32),
      RANDOM.nextBoolean(),
      RANDOM.nextInt(),
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean(),
      RANDOM.nextBoolean() ? null : generateString(32),
      RANDOM.nextDouble(),
      RANDOM.nextLong(),
      RANDOM.nextInt(),
      generateString(32),
      RANDOM.nextBoolean()
    );
  }

  private static DriveState generateDriveState(){
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
      RANDOM.nextBoolean() ? null : RANDOM.nextInt(),
      RANDOM.nextLong()
    );
  }

  private static GuiSettings generateGuiSettings(){
    return new GuiSettings(
      RANDOM.nextBoolean(),
      generateString(32),
      generateString(32),
      generateString(32),
      generateString(32),
      RANDOM.nextLong(),
      RANDOM.nextBoolean()
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
      generateString(32),
      RANDOM.nextBoolean(),
      RANDOM.nextInt(),
      null,
      null
    );
  }

  public static String generateEmailAddress(){
    return Utility.generateString((int) ((Math.random() * 5) + 4)) +
           "@" +
           Utility.generateString((int) ((Math.random() * 5) + 4)) +
           "." +
           Utility.generateString((int) ((Math.random() * 5) + 3));
  }

  public static String writeToJson(Gson om, Object value){
    try{
      return om.toJson(value);
    }catch(JsonParseException e){
      throw new RuntimeException(e);
    }
  }
}
