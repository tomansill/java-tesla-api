package com.ansill.tesla.api.model;

import tech.units.indriya.function.AddConverter;
import tech.units.indriya.function.MultiplyConverter;
import tech.units.indriya.unit.TransformedUnit;
import tech.units.indriya.unit.Units;

import javax.measure.Unit;
import javax.measure.quantity.Length;
import javax.measure.quantity.Speed;
import javax.measure.quantity.Temperature;

public class USUnits{

  public static final Unit<Length> MILE = Units.METRE.multiply(1609.344).asType(Length.class);

  public static final Unit<Speed> MILE_PER_HOUR = MILE.divide(Units.HOUR).asType(Speed.class);

  public static final Unit<Temperature> FAHRENHEIT = new TransformedUnit<>(
    Units.KELVIN,
    MultiplyConverter.ofRational(5, 9).concatenate(new AddConverter(459.67))
  );
}
