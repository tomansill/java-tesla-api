package com.ansill.tesla.test.fake.model;

import com.ansill.tesla.low.model.SuccessfulAuthenticationResponse;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.ansill.tesla.test.TestUtility.generateString;

public class MainStore{

    private Map<String,String> accounts = new ConcurrentHashMap<>();

    private Map<String,Set<String>> vehicles = new ConcurrentHashMap<>();

    private Map<String,SuccessfulAuthenticationResponse> accessToAccount = new ConcurrentHashMap<>();

    private Map<String,SuccessfulAuthenticationResponse> refreshToAccount = new ConcurrentHashMap<>();

    public MainStore(){
    }

    public void addAccount(String email, String password){
        accounts.put(email, password);
    }

    public Optional<SuccessfulAuthenticationResponse> authenticate(String email, String password){

        if(!password.equals(accounts.get(email))){

            // return string
            return Optional.empty();

        }

        // Build
        String accessToken = generateString(32);
        String refreshToken = generateString(32);
        Instant now = Instant.now();
        Instant expiry = Instant.now().plus(5, ChronoUnit.MINUTES);

        var response = new SuccessfulAuthenticationResponse(
                accessToken,
                "bearer",
                expiry.toEpochMilli(),
                refreshToken,
                now.toEpochMilli()
        );

        // Update stuff
        accessToAccount.put(accessToken, response);
        refreshToAccount.put(refreshToken, response);

        return Optional.of(response);

    }

    public Optional<SuccessfulAuthenticationResponse> refresh(String refreshToken){

        // Check if it exists
        if(!refreshToAccount.containsKey(refreshToken)) return Optional.empty();

        // Delete both access and refresh
        var response = refreshToAccount.remove(refreshToken);
        accessToAccount.remove(response.getAccessToken());

        // Build
        String accessToken = generateString(32);
        refreshToken = generateString(32);
        Instant now = Instant.now();
        Instant expiry = Instant.now().plus(5, ChronoUnit.MINUTES);

        response = new SuccessfulAuthenticationResponse(
                accessToken,
                "bearer",
                expiry.toEpochMilli(),
                refreshToken,
                now.toEpochMilli()
        );

        // Update stuff
        accessToAccount.put(accessToken, response);
        refreshToAccount.put(refreshToken, response);

        return Optional.of(response);

    }

    public String addVehicle(String email){
        var idString = generateString(32);
        vehicles.compute(email, (key, item) -> {
            if(item == null) item = new HashSet<>();
            item.add(idString);
            return item;
        });
        return idString;
    }
}
