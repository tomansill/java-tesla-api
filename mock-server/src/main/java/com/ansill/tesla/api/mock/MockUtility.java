package com.ansill.tesla.api.mock;

import com.ansill.validation.Validation;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.ServerSocket;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.LongStream;

public final class MockUtility{

  private static final Set<Integer> RESERVED_PORTS = new HashSet<>();

  private static final AtomicReference<Random> RANDOM_GENERATOR = new AtomicReference<>(null);

  // No instantiation allowed
  private MockUtility(){
    throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
  }


  @SuppressWarnings("SameParameterValue")
  @Nonnegative
  public static synchronized int getNextOpenPort(@Nonnegative int start){

    // Loop until maximum possible
    for(int port = start; port < Short.MAX_VALUE; port++){

      // Make sure not already reserved
      if(RESERVED_PORTS.contains(port)) continue;

      // Check if it's open
      if(!isPortOpen(port)) continue;

      // If it is indeed open, reserve it
      RESERVED_PORTS.add(port);

      // Return it
      return port;
    }

    // Throw it
    throw new RuntimeException("Cannot find any open ports!");
  }

  public static synchronized void unreservePort(@Nonnegative int port){
    RESERVED_PORTS.remove(port);
  }

  public static boolean isPortOpen(@Nonnegative int port){
    try(ServerSocket ignored = new ServerSocket(port)){
      return true;
    }catch(IOException e){
      return false;
    }
  }

  @Nonnull
  public static String generateEmailAddress(){
    return generateString(getRandom().nextInt(12) + 5) + "@fake.com";
  }

  @Nonnull
  private static Random getRandom(){
    return RANDOM_GENERATOR.updateAndGet(item -> item == null ? new SecureRandom() : item);
  }

  @Nonnull
  public static String generateString(@Nonnegative long length){

    // Check length
    Validation.assertNaturalNumber(length, "length");

    // Set up characterset
    String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String fullalphabet = alphabet + alphabet.toLowerCase() + "123456789";

    // Set up random generator
    Random random = getRandom();

    // Set up string builder
    StringBuilder sb = new StringBuilder();

    // Build random string
    LongStream.range(0, length).forEach(i -> sb.append(fullalphabet.charAt(random.nextInt(fullalphabet.length()))));

    // Return the string
    return sb.toString();

  }
}
