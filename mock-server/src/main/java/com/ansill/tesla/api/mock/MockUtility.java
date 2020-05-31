package com.ansill.tesla.api.mock;

import com.ansill.validation.Validation;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.ServerSocket;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public final class MockUtility{

  private static final Set<Integer> RESERVED_PORTS = new HashSet<>();

  private static final AtomicReference<Random> RANDOM_GENERATOR = new AtomicReference<>(null);

  // No instantiation allowed
  private MockUtility(){
    throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
  }


  @Nonnull
  public static String simpleToString(@Nonnull Object object){

    // Ensure no null
    Validation.assertNonnull(object, "object");

    // Set up mapper
    Function<Field,String> mapper = field -> {
      try{
        return field.getName() + "=" + sensibleToString(
          field.get(object));
      }catch(IllegalAccessException e){

        // Check if it's access issue
        if(!e.getMessage()
             .contains("modifiers \"private"))
          return "error";

        // Temporarily change access
        field.setAccessible(true);
        try{

          // Access it
          return field.getName() +
                 "=" +
                 sensibleToString(field.get(object));

        }catch(IllegalAccessException ex){
          //ex.printStackTrace();
          return "inaccessible";
        }finally{
          field.setAccessible(false);
        }
      }
    };

    // Collect fields
    var fields = Arrays.stream(object.getClass().getDeclaredFields())
                       .map(mapper)
                       .collect(Collectors.joining(", "));

    // Print class name and its fields
    return f("{}({})", object.getClass().getSimpleName(), fields);
  }

  @Nonnull
  public static String sensibleToString(@Nullable Object object){
    if(object == null) return "null";
    if(object instanceof String) return "\"" + object.toString() + "\"";
    else return object.toString();
  }

  /**
   * Formats string, replaces any '{}' with objects
   *
   * @param message message with '{}'
   * @param objects objects to replace
   * @return formatted string
   */
  @Nonnull
  public static String f(@Nonnull String message, @Nonnull Object... objects){
    return format(message, objects);
  }

  /**
   * Formats string, replaces any '{}' with objects
   *
   * @param message message with '{}'
   * @param objects objects to replace
   * @return formatted string
   */
  @Nonnull
  public static String format(@Nullable String message, @Nullable Object... objects){

    // If any of parameters are null, then return message
    if(message == null) return "null";
    if(objects == null) return message;

    // Set up builder
    StringBuilder builder = new StringBuilder();

    // Set up index in objects
    int objects_index = 0;

    // Set up indices for message
    int previous_index = 0;
    int brace_index = 0;

    // Loop until all is replaced or all elements in object array is used
    while(objects_index < objects.length && (brace_index = message.indexOf("{}", brace_index)) != -1){

      // Copy in characters since previous index
      builder.append(message, previous_index, brace_index);

      // Update brace index to skip "{}"
      brace_index = Math.min(brace_index + 2, message.length());

      // Update the previous index
      previous_index = brace_index;

      // Format 'null' if null
      if(objects[objects_index] == null) builder.append("null");

        // Use normal string if String
      else if(objects[objects_index] instanceof String) builder.append((String) objects[objects_index]);

        // Else, use .toString() method
      else builder.append(objects[objects_index].toString());

      // Increment the array
      objects_index++;

    }

    // Finish the string if there's any remaining
    if(previous_index < message.length()) builder.append(message, previous_index, message.length());

    // Return result
    return builder.toString();
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
