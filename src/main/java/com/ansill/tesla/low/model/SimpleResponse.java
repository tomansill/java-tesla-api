package com.ansill.tesla.low.model;

import com.ansill.validation.Validation;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import static com.ansill.tesla.utility.Utility.simpleToString;

/** Simple Response */
@Immutable
public abstract class SimpleResponse<T>{

    /** Response */
    @Nonnull
    private final T response;

    /**
     * Response
     *
     * @param response response
     */
    public SimpleResponse(@Nonnull T response){
        this.response = Validation.assertNonnull(response);
    }

    /**
     * Returns response
     *
     * @return response
     */
    @Nonnull
    public T getResponse(){
        return response;
    }

    @Override
    public String toString(){
        return simpleToString(this);
    }
}
