package com.ansill.tesla.test.fake.mock;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;

public class OAuthRevokeEndpoint implements Handler{

    private final AtomicReference<MockModel> model;

    public OAuthRevokeEndpoint(AtomicReference<MockModel> model){
        this.model = model;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception{

        // Get map
        var map = context.formParamMap();

        // Ensure other minimum stuff is supplied
        if(!map.containsKey("token")){
            context.status(200);
            return;
        }

        // Get token
        var token = map.get("token").iterator().next();

        // Run it
        model.get().revoke(token);

        // Return something
        context.status(200);
    }
}
