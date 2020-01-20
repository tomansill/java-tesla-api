package com.ansill.tesla.model;

import com.ansill.validation.Validation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * Cached Value
 * Caches value until its lifetime has passed, then that value will be dropped
 */
public class CachedValue<T>{

    /** Small delay to kick the vacuum online */
    private static final Duration VACUUM_DELAY = Duration.ofSeconds(10);

    /** Lifetime */
    @Nonnull
    private final AtomicReference<AtomicReference<Duration>> lifetime;

    /** Value */
    @Nullable
    private T value = null;

    /** Last updated */
    @Nonnull
    private Instant lastCached = Instant.MIN;

    @Nonnull
    private final AtomicReference<Timer> timer = new AtomicReference<>();

    /**
     * Creates cached value
     *
     * @param lifetime lifetime reference
     */
    public CachedValue(@Nonnull AtomicReference<AtomicReference<Duration>> lifetime){
        this.lifetime = lifetime;
    }

    /**
     * Creates cached value
     *
     * @param lifetime lifetime reference
     * @param value    current value
     */
    public CachedValue(@Nonnull AtomicReference<AtomicReference<Duration>> lifetime, @Nonnull T value){
        this.lifetime = lifetime;
        this.value = value;
        this.lastCached = Instant.now();
        resetVacuum();
    }

    /** Resets the vacuum */
    private void resetVacuum(){

        // Set up timer
        Timer timer = null;

        if(value != null){
            // Create timertask
            TimerTask tt = new TimerTask(){
                @Override
                public void run(){

                    // Check if it's time to clean up - not, reset vacuum timer
                    if(!Instant.now().isAfter(lastCached.plus(lifetime.get().get()).plus(VACUUM_DELAY))) resetVacuum();

                    // Otherwise clean up
                    value = null;
                }
            };

            // Create timer
            timer = new Timer();
            timer.schedule(
                    tt,
                    Duration.between(Instant.now(), lastCached.plus(lifetime.get().get()).plus(VACUUM_DELAY)).toMillis()
            );
        }

        // Replace timer and get old one
        timer = this.timer.getAndSet(timer);

        // Cancel it if not null
        if(timer != null) timer.cancel();
    }

    /** Invalidates the cache - removing the value from the cache */
    public synchronized void invalidate(){
        this.value = null;
        resetVacuum();
    }

    /**
     * Updates the value in the cache
     *
     * @param value new value
     */
    public synchronized void update(@Nonnull T value){
        this.value = Validation.assertNonnull(value, "value");
        this.lastCached = Instant.now();
        resetVacuum();
    }

    /**
     * Gets value in the cache if it has not been invalidated yet, or updates the value and retrieve the new value
     *
     * @param supplier supplier that updates the value
     * @return value
     */
    public synchronized T getOrUpdate(@Nonnull Supplier<T> supplier){

        // Calculate if expired or not - return if not expired
        if(value != null || !Instant.now().isAfter(lastCached.plus(lifetime.get().get()))) return value;

        // Rebuild
        value = supplier.get();
        lastCached = Instant.now();
        resetVacuum();

        // Return value
        return value;
    }
}
