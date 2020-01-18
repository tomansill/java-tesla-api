package com.ansill.tesla.high;

import com.ansill.lock.autolock.ALock;
import com.ansill.lock.autolock.LockedAutoLock;
import com.ansill.tesla.high.model.Vehicle;
import com.ansill.tesla.low.Client;
import com.ansill.tesla.low.exception.ReAuthenticationException;
import com.ansill.tesla.low.model.SuccessfulAuthenticationResponse;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TeslaAccount{

    @Nonnull
    private static final Duration DEFAULT_TIME_OFFSET_BEFORE_REFRESH = Duration.ofMinutes(2);

    @Nonnull
    private final Client client;
    @Nonnull
    private final ReentrantReadWriteLock rrwl = new ReentrantReadWriteLock(true);
    @Nonnull
    private final AtomicReference<Timer> timer = new AtomicReference<>();
    @Nonnull
    private SuccessfulAuthenticationResponse response;

    TeslaAccount(@Nonnull Client client, @Nonnull SuccessfulAuthenticationResponse response){
        this.client = client;
        this.response = response;
        resetTimer();
    }

    @Nonnull
    Client getClient(){
        return client;
    }

    private void resetTimer(){

        // Set up TimerTask
        TimerTask task = new TimerTask(){
            @Override
            public void run(){
                try{
                    refresh();
                }catch(ReAuthenticationException e){
                    e.printStackTrace(); // TODO!!
                }
            }
        };

        // Figure out the delay
        var delay = response.getExpiresIn() -
                    Instant.now().toEpochMilli() -
                    DEFAULT_TIME_OFFSET_BEFORE_REFRESH.toMillis();

        // If delay is not positive, fire it now
        if(delay <= 0){
            try{
                refresh();
            }catch(ReAuthenticationException e){
                e.printStackTrace(); // TODO!!
            }
            return;
        }

        // Create new timer and schedule it
        var timer = new Timer();
        timer.schedule(task, delay);

        // Cancel the timer if previously set
        timer = this.timer.getAndSet(timer);
        if(timer != null) timer.cancel();

    }

    private void refresh() throws ReAuthenticationException{

        // Lock it
        try(LockedAutoLock ignored = new ALock(rrwl.writeLock()).doLock()){

            // Refresh it and update response
            response = client.refreshToken(response.getRefreshToken());
        }

        // Reset the timer
        resetTimer();

    }

    @Nonnull
    public Set<Vehicle> getVehicles(){
        return null;
    }

    @Nonnull
    public Optional<Vehicle> getVehicleByName(@Nonnull String name){
        return null;
    }

    @Nonnull
    public Optional<Vehicle> getVehicleByID(@Nonnull String id){
        return null;
    }

    @Nonnull
    public Optional<Vehicle> getVehicleByVIN(@Nonnull String vin){
        return null;
    }
}
