package com.ansill.tesla.api.model;

import com.ansill.validation.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * Cached Value
 * Caches value until its lifetime has passed, then that value will be dropped
 */
public class CachedValue<T>{

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(CachedValue.class);

  /** Lifetime */
  @Nonnull
  private final AtomicReference<AtomicReference<Duration>> lifetime;

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
  }

  /** Invalidates the cache - removing the value from the cache */
  public synchronized void invalidate(){
    this.value = null;
  }

  /**
   * Updates the value in the cache
   *
   * @param value new value
   */
  public synchronized void update(@Nonnull T value){
    this.value = Validation.assertNonnull(value, "value");
    this.lastCached = Instant.now();
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

    // Return value
    return value;
  }

  /**
   * Returns optional object that may contain value - does not perform the update
   *
   * @return optional object that may contain the value
   */
  @Nonnull
  public Optional<T> get(){
    return Optional.ofNullable(this.value);
  }
}
