package com.ansill.tesla.utility;

import okhttp3.Response;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Optional;

import static com.ansill.tesla.utility.Utility.simpleToString;

/** Reusable version of Response - allows us to read response body many times */
public class ReusableResponse implements AutoCloseable{

    @Nonnull
    final Response response;

    @Nullable
    private String bodyString = null;

    public ReusableResponse(@Nonnull Response response){

        this.response = response;
    }

    public int code(){
        return response.code();
    }

    @Nonnull
    public synchronized Optional<String> getBodyAsString() throws IOException{
        if(bodyString != null) return Optional.of(bodyString);
        bodyString = Utility.getStringFromResponseBody(response).orElse(null);
        return Optional.ofNullable(bodyString);
    }

    @Override
    public void close(){
        bodyString = null;
        response.close();
    }

    @Override
    public String toString(){
        try{
            getBodyAsString();
        }catch(IOException e){
            e.printStackTrace();
        }
        return simpleToString(this);
    }
}
