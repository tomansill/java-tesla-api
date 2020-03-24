package com.ansill.tesla.api.model;

import com.ansill.validation.Validation;
import tech.units.indriya.function.MultiplyConverter;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.TransformedUnit;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Angle;

import static tech.units.indriya.unit.Units.RADIAN;

@Immutable
public final class Coordinate{

  private static final Unit<Angle> ANGULAR_DEGREES = new TransformedUnit<>(
    "°",
    RADIAN,
    RADIAN,
    MultiplyConverter.ofPiExponent(1).concatenate(MultiplyConverter.ofRational(1, 180))
  );

  @Nonnull
  private final Quantity<Angle> latitude;

  @Nonnull
  private final Quantity<Angle> longitude;

  public Coordinate(double latitude, double longitude){
    this(Quantities.getQuantity(latitude, ANGULAR_DEGREES), Quantities.getQuantity(longitude, ANGULAR_DEGREES));
  }

  public Coordinate(@Nonnull Quantity<Angle> latitude, @Nonnull Quantity<Angle> longitude){
    this.latitude = Validation.assertNonnull(latitude);
    this.longitude = Validation.assertNonnull(longitude);
    var lat = this.latitude.getValue().doubleValue();
    var lon = this.longitude.getValue().doubleValue();
    if(lat > 90 || lat < -90) throw new IllegalArgumentException(
      "Latitude is over the allowable limit (-90.0 <= X <= 90.0)");
    if(lon > 180 || lon < -180) throw new IllegalArgumentException(
      "Longitude is over the allowable limit (-180.0 <= X <= 180.0)");
  }

  @Nonnull
  public Quantity<Angle> getLatitude(){
    return latitude;
  }

  @Nonnull
  public Quantity<Angle> getLongitude(){
    return longitude;
  }
}
