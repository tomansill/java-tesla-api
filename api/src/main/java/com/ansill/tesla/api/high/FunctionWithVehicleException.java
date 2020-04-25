package com.ansill.tesla.api.high;

import com.ansill.tesla.api.low.exception.VehicleIDNotFoundException;

@FunctionalInterface
interface FunctionWithVehicleException<T, R>{

    R apply(T object) throws VehicleIDNotFoundException;

}
