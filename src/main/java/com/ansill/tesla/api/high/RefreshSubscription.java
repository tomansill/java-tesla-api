package com.ansill.tesla.api.high;

import com.ansill.tesla.api.low.exception.ReAuthenticationException;
import com.ansill.tesla.api.med.model.AccountCredentials;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.function.Consumer;

/** Refresh Subscription grouping */
@Immutable
class RefreshSubscription{

    /** Consumer that consumes new account credentials */
    @Nonnull
    private final Consumer<AccountCredentials> consumer;

    /** Consumer that processes on error */
    @Nonnull
    private final Consumer<ReAuthenticationException> onError;

    /**
     * RefreshSubscription constructor
     *
     * @param consumer consumer that consumes new account credentials
     * @param onError  consumer that consumes errors
     */
    RefreshSubscription(
            @Nonnull Consumer<AccountCredentials> consumer,
            @Nonnull Consumer<ReAuthenticationException> onError
    ){
        this.consumer = consumer;
        this.onError = onError;
    }

    /**
     * Returns credentials consumer
     *
     * @return consumer
     */
    @Nonnull
    public Consumer<AccountCredentials> getConsumer(){
        return consumer;
    }

    /**
     * Returns error consumer
     *
     * @return consumer
     */
    @Nonnull
    public Consumer<ReAuthenticationException> getOnError(){
        return onError;
    }
}
