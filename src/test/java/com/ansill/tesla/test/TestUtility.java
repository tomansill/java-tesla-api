package com.ansill.tesla.test;

import com.ansill.tesla.low.model.ChargeState;
import com.ansill.tesla.low.model.Vehicle;
import com.ansill.tesla.test.fake.mock.MockModel;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static com.ansill.tesla.utility.Utility.f;

public final class TestUtility{

    private static final Set<Integer> RESERVED_PORTS = new HashSet<>();
    private static AtomicReference<Random> RANDOM_GENERATOR = new AtomicReference<>(null);


    // No instantiation allowed
    private TestUtility(){
        throw new AssertionError(f("No {} instances for you!", this.getClass().getName()));
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

    public static MockModel.VehicleAccount createParkingVehicle(){
        long id = new Random().nextLong();
        long vehicle_id = new Random().nextLong();

        // Basic vehicle info
        var vehicle = new Vehicle(
                id,
                vehicle_id,
                new Random().nextLong(),
                generateString(32),
                generateString(16),
                IntStream.range(0, new Random().nextInt(32) + 1)
                         .asLongStream()
                         .mapToObj(item -> generateString(6))
                         .collect(Collectors.joining(",")),
                null, // Always null...
                IntStream.range(0, new Random().nextInt(2) + 1)
                         .asLongStream()
                         .mapToObj(item -> generateString(16))
                         .collect(Collectors.toList()),
                "online",
                false,
                id + "",
                true,
                6,
                null,
                null

        );

        // charge state
        var chargeState = new ChargeState(
                false,
                new Random().nextInt(75) + 25,
                (new Random().nextDouble() * 250.0) + 60,
                0,
                0,
                false,
                0.0,
                80,
                100,
                60,
                70,
                20,
                22,
                true,
                false,
                "disengaged",
                0.0,
                false,
                0,
                null,
                48,
                0,
                2,
                "Disconnected",
                "<invalid>",
                124.1,
                "<invalid>",
                false,
                new Random().nextInt(310),
                "<invalid>",
                190.59,
                false,
                null,
                false,
                0,
                null,
                false,
                null,
                0.0,
                1245141241241L,
                63,
                null,
                true
        );

        return new MockModel.VehicleAccount(new AtomicReference<>(vehicle));
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
        return generateString(getRandom().nextInt(12) + 1) + "@" + generateString(getRandom().nextInt(8) + 1) + ".com";
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
