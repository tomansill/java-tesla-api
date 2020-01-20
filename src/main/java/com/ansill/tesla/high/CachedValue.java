package com.ansill.tesla.high;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

class CachedValue<T>{

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
    CachedValue(@Nonnull AtomicReference<AtomicReference<Duration>> lifetime){
        this.lifetime = lifetime;
    }

    /**
     * Creates cached value
     *
     * @param lifetime lifetime reference
     * @param value    current value
     */
    CachedValue(@Nonnull AtomicReference<AtomicReference<Duration>> lifetime, @Nonnull T value){
        this.lifetime = lifetime;
        this.value = value;
        this.lastCached = Instant.now();
    }

    synchronized T getOrUpdate(@Nonnull Supplier<T> supplier){

        // Calculate if expired or not - return if not expired
        if(value != null || !Instant.now().isAfter(lastCached.plus(lifetime.get().get()))) return value;

        // Rebuild
        value = supplier.get();
        lastCached = Instant.now();

        // Return value
        return value;
    }
}
