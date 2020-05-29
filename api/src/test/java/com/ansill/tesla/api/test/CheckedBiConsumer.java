package com.ansill.tesla.api.test;

@FunctionalInterface
public interface CheckedBiConsumer<A, B>{

  void accept(A one, B two) throws Exception;

}
