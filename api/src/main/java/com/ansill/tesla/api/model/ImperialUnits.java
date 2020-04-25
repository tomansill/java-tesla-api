package com.ansill.tesla.api.model;

import tech.units.indriya.function.AddConverter;
import tech.units.indriya.function.MultiplyConverter;
import tech.units.indriya.unit.TransformedUnit;
import tech.units.indriya.unit.Units;

import javax.measure.Unit;
import javax.measure.quantity.Length;
import javax.measure.quantity.Speed;
import javax.measure.quantity.Temperature;

public class ImperialUnits{

  /** Imperial Mile */
  public static final Unit<Length> MILE = new TransformedUnit<>(
    "mi",
    Units.METRE,
    Units.METRE,
    MultiplyConverter.of(1609.344)
  );

  /** Imperial Mile per hour */
  public static final Unit<Speed> MILE_PER_HOUR = MILE.divide(Units.HOUR).asType(Speed.class);

  /** Imperial Fahrenheit */
  public static final Unit<Temperature> FAHRENHEIT = new TransformedUnit<>(
    "F",
    Units.KELVIN,
    MultiplyConverter.ofRational(5, 9).concatenate(new AddConverter(459.67))
  );
}
