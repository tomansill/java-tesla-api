package com.ansill.tesla.test.fake.model;

import com.ansill.tesla.low.model.FailedAuthenticationResponse;
import com.google.gson.Gson;
import io.javalin.Javalin;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicReference;

import static com.ansill.tesla.test.TestUtility.generateString;
import static com.ansill.tesla.test.TestUtility.getNextOpenPort;
import static com.ansill.tesla.utility.Utility.f;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

public class MockServer implements AutoCloseable{

    @Nonnull
    private final AtomicReference<MainStore> model;

    @Nonnull
    private final String clientId;

    @Nonnull
    private final String clientSecret;

    @Nonnegative
    private final int port;

    private Javalin server;

    public MockServer(
            @Nonnull AtomicReference<MainStore> model,
            @Nonnull String clientId,
            @Nonnull String clientSecret
    ){
        this.model = model;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.port = getNextOpenPort(1001);
        server = Javalin.create();
        this.run();
    }

    private void run(){

        server.routes(() -> {

            // OAuth
            path("oauth/token", () -> {
                post(handler -> {

                    // Get map
                    var map = handler.formParamMap();

                    // Ensure other minimum stuff is supplied
                    if(!map.containsKey("grant_type") ||
                       !map.containsKey("client_id") ||
                       !map.containsKey("client_secret")
                    ){
                        handler.status(401);
                        handler.result(new Gson().toJson(new FailedAuthenticationResponse(
                                "invalid_request",
                                "The request is missing a required parameter, includes an unsupported parameter value, or is otherwise malformed.",
                                null
                        )));
                        return;
                    }

                    // Ensure client id and secret are correct
                    if(!clientId.equals(map.get("client_id").iterator().next()) || !clientSecret.equals(map.get(
                            "client_secret").iterator().next())){
                        handler.status(401);
                        handler.result(new Gson().toJson(new FailedAuthenticationResponse(
                                "invalid_client",
                                "The request is missing a required parameter, includes an unsupported parameter value, or is otherwise malformed.",
                                // TODO get correct message?
                                null
                        )));
                        return;
                    }

                    // Get grant type
                    var type = map.get("grant_type").iterator().next();

                    // Switch
                    switch(type){

                        case "refresh_token" -> {

                        }

                        case "password" -> {

                            // Ensure email and password are provided
                            if(!map.containsKey("email") || !map.containsKey("password")){
                                handler.status(401);
                                handler.result(new Gson().toJson(new FailedAuthenticationResponse(
                                        "invalid_request",
                                        "The request is missing a required parameter, includes an unsupported parameter value, or is otherwise malformed.",
                                        null
                                )));
                                return;
                            }

                            // Now, check if credentials are correct
                            var email = map.get("email").iterator().next();
                            var password = map.get("password").iterator().next();

                            // Auth
                            var result = model.get().authenticate(email, password);

                            // Handle result
                            if(result.isPresent()){
                                handler.status(200);
                                handler.result(new Gson().toJson(result.get()));
                            }else{
                                handler.status(401);
                                handler.result(new Gson().toJson(new FailedAuthenticationResponse(
                                        null,
                                        null,
                                        f("authorization_required_for_txid_'{}'", generateString(32))
                                )));
                            }

                        }

                        default -> throw new RuntimeException(f("unexpected grant type", type));

                    }
                });

            });
        });


        // Start it
        server.start(port);
    }

    @Nonnegative
    public int getPort(){
        return port;
    }

    @Override
    public void close(){
        server.stop();
    }
}
