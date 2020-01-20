package com.ansill.tesla.model;

import tech.units.indriya.unit.Units;

import javax.measure.Unit;
import javax.measure.quantity.Length;

public class USUnits{

    public static final Unit<Length> MILE = Units.METRE.multiply(1609.344).asType(Length.class);

}
