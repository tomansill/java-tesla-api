package com.ansill.tesla.api.model;

import com.ansill.validation.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(CachedValue.class);

  /** Small delay to kick the vacuum online */
  private static final Duration VACUUM_DELAY = Duration.ofSeconds(10);

  /** Lifetime */
  @Nonnull
  private final AtomicReference<AtomicReference<Duration>> lifetime;

  @Nonnull
  private final AtomicReference<Timer> timer = new AtomicReference<>();

  /** Value */
  @Nullable
  private T value = null;

  /** Last updated */
  @Nonnull
  private Instant lastCached = Instant.MIN;

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

  /**
   * Resets the vacuum
   * NOT INTENDED TO BE AN INVALIDATOR - INTENDED TO JUST CLEAN UP OLD VALUE THAT HAS NOT BEEN TOUCHED FOR A WHILE
   */
  private void resetVacuum(){

    // Set up timer
    Timer timer = null;

    // Only run if value is not null
    if(value != null){

      // Create timertask
      TimerTask tt = new TimerTask(){
        @Override
        public void run(){

          // Check if it's time to clean up - not, reset vacuum timer
          if(!Instant.now().isAfter(lastCached.plus(lifetime.get().get()))){
            resetVacuum();
            return;
          }

          // Otherwise clean up
          value = null;
        }
      };

      // Calculate delay
      // NOTE: This method is not for actual invalidating, this method is just for cleaning up value that is unused for
      // a significant amount of time, the actual invalidating process is done at getOrUpdate(Supplier) method
      var delay = Duration.between(Instant.now(), lastCached.plus(lifetime.get().get().plus(VACUUM_DELAY)))
                          .toMillis();

      // Skip if zero or negative
      if(delay <= 0){

        // Clean up
        value = null;
      }

      // Otherwise create timer and schedule it
      else{

        // Create timer
        timer = new Timer();
        timer.schedule(
          tt,
          delay
        );
      }
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
    if(value != null && !Instant.now().isAfter(lastCached.plus(lifetime.get().get()))){
      LOGGER.debug("The current value is valid, returning current value");
      return value;
    }

    // Rebuild
    value = supplier.get();
    lastCached = Instant.now();
    resetVacuum();

    // Return value
    return value;
  }
}
