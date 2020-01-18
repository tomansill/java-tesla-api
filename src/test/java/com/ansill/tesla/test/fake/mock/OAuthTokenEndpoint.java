package com.ansill.tesla.test.fake.mock;

import com.ansill.tesla.low.model.FailedAuthenticationResponse;
import com.google.gson.Gson;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;

import static com.ansill.tesla.test.TestUtility.generateString;
import static com.ansill.tesla.utility.Utility.f;

public class OAuthTokenEndpoint implements Handler{

    private final AtomicReference<MockModel> model;
    private final String clientId;
    private final String clientSecret;

    public OAuthTokenEndpoint(AtomicReference<MockModel> model, String clientId, String clientSecret){
        this.model = model;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception{

        // Get map
        var map = context.formParamMap();

        // Ensure other minimum stuff is supplied
        if(!map.containsKey("grant_type") ||
           !map.containsKey("client_id") ||
           !map.containsKey("client_secret")
        ){
            context.status(401);
            context.result(new Gson().toJson(new FailedAuthenticationResponse(
                    "invalid_request",
                    "The request is missing a required parameter, includes an unsupported parameter value, or is otherwise malformed.",
                    null
            )));
            return;
        }

        // Ensure client id and secret are correct
        if(!clientId.equals(map.get("client_id").iterator().next()) || !clientSecret.equals(map.get(
                "client_secret").iterator().next())){
            context.status(401);
            context.result(new Gson().toJson(new FailedAuthenticationResponse(
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

                // Ensure refresh_token are provided
                if(!map.containsKey("refresh_token")){
                    context.status(401);
                    context.result(new Gson().toJson(new FailedAuthenticationResponse(
                            "invalid_request",
                            "The request is missing a required parameter, includes an unsupported parameter value, or is otherwise malformed.",
                            null
                    )));
                    return;
                }

                // Now, check if credentials are correct
                var refreshToken = map.get("refresh_token").iterator().next();

                // Auth
                var result = model.get().refreshToken(refreshToken);

                // Handle result
                if(result.isPresent()){
                    context.status(200);
                    context.result(new Gson().toJson(result.get()));
                }else{
                    context.status(401);
                    context.result(new Gson().toJson(new FailedAuthenticationResponse(
                            "invalid_grant",
                            "The provided authorization grant is invalid, expired, revoked, does not match the redirection URI used in the authorization request, or was issued to another client.",
                            null
                    )));
                }
            }

            case "password" -> {

                // Ensure email and password are provided
                if(!map.containsKey("email") || !map.containsKey("password")){
                    context.status(401);
                    context.result(new Gson().toJson(new FailedAuthenticationResponse(
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
                    context.status(200);
                    context.result(new Gson().toJson(result.get()));
                }else{
                    context.status(401);
                    context.result(new Gson().toJson(new FailedAuthenticationResponse(
                            null,
                            null,
                            f("authorization_required_for_txid_'{}'", generateString(32))
                    )));
                }

            }

            default -> throw new RuntimeException(f("unexpected grant type {}", type));

        }
    }
}
