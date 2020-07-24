package com.ansill.tesla.api.high;


import com.ansill.tesla.api.raw.exception.VehicleIDNotFoundException;

@FunctionalInterface
interface FunctionWithVehicleException<T, R>{

    R apply(T object) throws VehicleIDNotFoundException;

}
