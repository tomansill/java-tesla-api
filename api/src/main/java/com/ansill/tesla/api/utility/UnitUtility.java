package com.ansill.tesla.api.utility;

import com.ansill.tesla.api.model.ImperialUnits;
import tech.units.indriya.function.MultiplyConverter;
import tech.units.indriya.unit.TransformedUnit;
import tech.units.indriya.unit.Units;

import javax.annotation.Nonnull;
import javax.measure.Unit;
import javax.measure.quantity.Angle;
import javax.measure.quantity.Length;
import javax.measure.quantity.Speed;
import javax.measure.quantity.Temperature;
import javax.measure.quantity.Time;

import static com.ansill.utility.Utility.f;
import static tech.units.indriya.unit.Units.RADIAN;

/**
 * Utility Class
 */
public final class UnitUtility{

  public static final Unit<Angle> ANGULAR_DEGREES = new TransformedUnit<>(
    "°",
    RADIAN,
    RADIAN,
    MultiplyConverter.ofPiExponent(1).concatenate(MultiplyConverter.ofRational(1, 180))
  );

  private UnitUtility(){
    throw new AssertionError(f("No {} instances for you!", this.getClass().getName()));
  }

  @Nonnull
  public static Unit<Speed> getSpeedUnit(@Nonnull String unit){

    // Split on /
    var split = unit.split("/");

    // Assert
    if(split.length != 2) throw new IllegalArgumentException(f("The unit '{}' is missing '/'!", unit));

    // Get length
    Unit<Length> len = switch(split[0]){
      case "mi" -> ImperialUnits.MILE;
      case "km" -> Units.METRE.multiply(1000).asType(Length.class);
      case "m" -> Units.METRE;
      case "ft" -> ImperialUnits.MILE.divide(5280).asType(Length.class);
      default -> throw new RuntimeException(f("Unknown unit '{}' - full rate unit '{}'", split[0], unit));
    };

    // Get time
    Unit<Time> time = switch(split[1]){
      case "hr" -> Units.HOUR;
      case "m" -> Units.MINUTE;
      case "s" -> Units.SECOND;
      default -> throw new RuntimeException(f("Unknown unit '{}' - full rate unit '{}'", split[1], unit));
    };

    // Return speed
    return len.divide(time).asType(Speed.class);
  }

  @Nonnull
  public static Unit<Temperature> getTemperatureUnit(@Nonnull String unit){
    return switch(unit){
      case "F" -> ImperialUnits.FAHRENHEIT;
      case "C" -> Units.CELSIUS;
      case "K" -> Units.KELVIN;
      default -> throw new RuntimeException(f("Unknown unit '{}'", unit));
    };
  }

}

