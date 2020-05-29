package com.ansill.tesla.api.high.model;

import com.ansill.utility.Utility;
import com.ansill.validation.Validation;
import tech.units.indriya.function.MultiplyConverter;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.TransformedUnit;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Angle;

import static com.ansill.utility.Utility.f;
import static tech.units.indriya.unit.Units.RADIAN;

/** Location that includes latitude and longitude coordinate and heading */
@Immutable
public final class Location{

  /** Angular degrees */
  private static final Unit<Angle> ANGULAR_DEGREES = new TransformedUnit<>(
    "°",
    RADIAN,
    RADIAN,
    MultiplyConverter.ofPiExponent(1).concatenate(MultiplyConverter.ofRational(1, 180))
  );

  /** Heading */
  @Nonnull
  private final Quantity<Angle> heading;

  /** Latitude */
  @Nonnull
  private final Quantity<Angle> latitude;

  /** Longitude */
  @Nonnull
  private final Quantity<Angle> longitude;

  /**
   * Location constructor using doubles
   *
   * @param heading   heading
   * @param latitude  latitude
   * @param longitude longitude
   */
  public Location(double heading, double latitude, double longitude){
    this(
      Quantities.getQuantity(heading, ANGULAR_DEGREES),
      Quantities.getQuantity(latitude, ANGULAR_DEGREES),
      Quantities.getQuantity(longitude, ANGULAR_DEGREES)
    );
  }

  /**
   * Location constructor using quantity
   *
   * @param heading   heading
   * @param latitude  latitude
   * @param longitude longitude
   */
  public Location(
    @Nonnull Quantity<Angle> heading,
    @Nonnull Quantity<Angle> latitude,
    @Nonnull Quantity<Angle> longitude
  ){
    this.heading = Validation.assertNonnull(heading);
    this.latitude = Validation.assertNonnull(latitude);
    this.longitude = Validation.assertNonnull(longitude);
    var head = this.heading.getValue().doubleValue();
    var lat = this.latitude.getValue().doubleValue();
    var lon = this.longitude.getValue().doubleValue();
    if(head > 360 || head < 0) throw new IllegalArgumentException(
      f("Heading is over the allowable limit (0.0 <= X <= 360.0), Given: {}", head));
    if(lat > 90 || lat < -90) throw new IllegalArgumentException(
      f("Latitude is over the allowable limit (-90.0 <= X <= 90.0), Given: {}", lat));
    if(lon > 180 || lon < -180) throw new IllegalArgumentException(
      f("Longitude is over the allowable limit (-180.0 <= X <= 180.0), Given: {}", lon));
  }

  /**
   * Returns heading
   *
   * @return heading in angle quantity
   */
  @Nonnull
  public Quantity<Angle> getHeading(){
    return heading;
  }

  /**
   * Returns latitude
   *
   * @return latitude in angle quantity
   */
  @Nonnull
  public Quantity<Angle> getLatitude(){
    return latitude;
  }

  /**
   * Returns longitude
   *
   * @return longitude in angle quantity
   */
  @Nonnull
  public Quantity<Angle> getLongitude(){
    return longitude;
  }

  @Override
  public String toString(){
    return Utility.simpleToString(this);
  }

  @Override
  public boolean equals(Object o){
    if(this == o) return true;
    if(o == null || getClass() != o.getClass()) return false;

    Location location = (Location) o;

    if(!heading.equals(location.heading)) return false;
    if(!latitude.equals(location.latitude)) return false;
    return longitude.equals(location.longitude);
  }

  @Override
  public int hashCode(){
    int result = heading.hashCode();
    result = 31 * result + latitude.hashCode();
    result = 31 * result + longitude.hashCode();
    return result;
  }
}
