package com.ansill.tesla.api.test;

@FunctionalInterface
public interface CheckedBiFunction<A, B, R>{

  R apply(A one, B two) throws Exception;

}
