package com.ansill.tesla.test.fake.mock;

import com.ansill.tesla.low.exception.InvalidAccessTokenException;
import com.ansill.tesla.low.model.SuccessfulAuthenticationResponse;
import com.ansill.tesla.low.model.Vehicle;
import com.ansill.tesla.low.model.VehiclesResponse;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.ansill.tesla.test.TestUtility.generateString;

public class MockModel{

    private final Map<String,String> usernameToPassword = new HashMap<>();

    private final Map<String,Map<String,VehicleAccount>> usernameToVehicleAccount = new HashMap<>();

    private final Map<String,Auth> refreshToAuth = new HashMap<>();

    private final Map<String,Auth> accessToAuth = new HashMap<>();

    public synchronized boolean addAccount(String email, String password){
        if(usernameToPassword.containsKey(email)) return false;
        usernameToPassword.put(email, password);
        usernameToVehicleAccount.put(email, new HashMap<>());
        return true;
    }

    public synchronized boolean addVehicleToAccount(String email, VehicleAccount account){
        if(!usernameToVehicleAccount.containsKey(email)) return false;
        usernameToVehicleAccount.get(email).put(account.vehicleAtomicReference.get().getIdString(), account);
        return true;
    }

    public synchronized boolean vehicleExists(String email, String account){
        return usernameToVehicleAccount.get(email).containsKey(account);
    }

    public synchronized boolean accountExists(String email){
        return usernameToPassword.containsKey(email);
    }

    public synchronized boolean refreshTokenExists(String token){
        return refreshToAuth.containsKey(token);
    }

    public synchronized boolean accessTokenExists(String token){
        return accessToAuth.containsKey(token);
    }

    public synchronized Optional<SuccessfulAuthenticationResponse> authenticate(String email, String password){

        if(!usernameToPassword.containsKey(email)) return Optional.empty();

        if(!usernameToPassword.get(email).equals(password)) return Optional.empty();

        // All ok, create object
        var response = new SuccessfulAuthenticationResponse(
                generateString(32),
                "bearer",
                Duration.ofDays(60).toMillis(),
                generateString(32),
                Instant.now().toEpochMilli()
        );

        // Wrap it
        var auth = new Auth(email, response);

        // Attach access token and refresh tokens
        refreshToAuth.put(response.getRefreshToken(), auth);
        accessToAuth.put(response.getAccessToken(), auth);

        // Set expiry
        TimerTask tt = new TimerTask(){
            @Override
            public void run(){
                refreshToAuth.remove(response.getRefreshToken());
                accessToAuth.remove(response.getAccessToken());
                System.out.println("removed auth " + response.getRefreshToken());
            }
        };
        // Create timer
        var timer = new Timer();
        timer.schedule(tt, response.getExpiresIn());

        // Return it
        return Optional.of(response);
    }

    public synchronized Optional<SuccessfulAuthenticationResponse> refreshToken(String token){

        if(!refreshToAuth.containsKey(token)) return Optional.empty();

        // Remove old
        var old = refreshToAuth.remove(token);
        accessToAuth.remove(old.response.getRefreshToken());

        // All ok, create object
        var response = new SuccessfulAuthenticationResponse(
                generateString(32),
                "bearer",
                Duration.ofDays(60).toMillis(),
                generateString(32),
                Instant.now().toEpochMilli()
        );

        // Wrap it
        var auth = new Auth(old.email, response);

        // Attach access token and refresh tokens
        refreshToAuth.put(response.getRefreshToken(), auth);
        accessToAuth.put(response.getAccessToken(), auth);

        // Set expiry
        TimerTask tt = new TimerTask(){
            @Override
            public void run(){
                refreshToAuth.remove(response.getRefreshToken());
                accessToAuth.remove(response.getAccessToken());
            }
        };
        // Create timer
        var timer = new Timer();
        timer.schedule(tt, response.getExpiresIn());

        // Return it
        return Optional.of(response);
    }

    public synchronized VehiclesResponse getVehicles(String token) throws InvalidAccessTokenException{

        if(!accessToAuth.containsKey(token)) throw new InvalidAccessTokenException();

        // Get account
        var email = accessToAuth.get(token).email;

        // Get vehicles
        var vehicles = usernameToVehicleAccount.get(email)
                                               .values()
                                               .stream()
                                               .map(item -> item.vehicleAtomicReference.get())
                                               .collect(Collectors.toList());

        // Respond
        return new VehiclesResponse(vehicles, vehicles.size());
    }

    public synchronized void revoke(String token){

        if(!refreshToAuth.containsKey(token)) return;

        // Remove old
        var old = refreshToAuth.remove(token);
        accessToAuth.remove(old.response.getRefreshToken());
    }

    public Optional<Vehicle> getVehicle(String token, String id){

        if(!accessToAuth.containsKey(token)) throw new InvalidAccessTokenException();

        // Get account
        var email = accessToAuth.get(token).email;

        // Get vehicle
        return usernameToVehicleAccount.get(email)
                                       .values()
                                       .stream()
                                       .map(item -> item.vehicleAtomicReference.get())
                                       .filter(item -> item.getIdString().equals(id))
                                       .findAny();

    }

    public static class Auth{

        public final String email;

        public final SuccessfulAuthenticationResponse response;

        public Auth(String email, SuccessfulAuthenticationResponse response){
            this.email = email;
            this.response = response;
        }
    }

    public static class VehicleAccount{
        public final AtomicReference<Vehicle> vehicleAtomicReference;

        public VehicleAccount(AtomicReference<Vehicle> vehicleAtomicReference){
            this.vehicleAtomicReference = vehicleAtomicReference;
        }
    }
}
