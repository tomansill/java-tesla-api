package com.ansill.tesla.api.mock.endpoint;

import com.ansill.tesla.api.mock.MockUtility;
import com.ansill.tesla.api.mock.endpoint.v1.response.GenericErrorResponse;
import com.ansill.tesla.api.mock.model.MockModel;
import com.google.gson.Gson;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicReference;

import static io.javalin.apibuilder.ApiBuilder.post;

public class OAuthEndpoint implements EndpointGroup{

  @Nonnull
  private final AtomicReference<MockModel> model;

  public OAuthEndpoint(@Nonnull AtomicReference<MockModel> model){
    this.model = model;
  }

  @Override
  public void addEndpoints(){
    post("/token", this::token);
    post("/revoke", this::revoke);
  }

  private void revoke(Context context){

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

  private void token(Context context){
    // Get map
    var map = context.formParamMap();

    // Ensure other minimum stuff is supplied
    if(!map.containsKey("grant_type") ||
       !map.containsKey("client_id") ||
       !map.containsKey("client_secret")
    ){
      context.status(401);
      context.result(new Gson().toJson(new GenericErrorResponse(
        "invalid_request",
        "The request is missing a required parameter, includes an unsupported parameter value, or is otherwise malformed.",
        null
      )));
      return;
    }

    // Ensure client id and secret are correct
    if(!model.get().getClientId().equals(map.get("client_id").iterator().next()) ||
       !model.get().getClientSecret().equals(map.get(
         "client_secret").iterator().next())){
      context.status(401);
      context.result(new Gson().toJson(new GenericErrorResponse(
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
          context.result(new Gson().toJson(new GenericErrorResponse(
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
          context.result(new Gson().toJson(new GenericErrorResponse(
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
          context.result(new Gson().toJson(new GenericErrorResponse(
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
          context.result(new Gson().toJson(new GenericErrorResponse(
            null,
            null,
            "authorization_required_for_txid_'" + MockUtility.generateString(32) + "'"
          )));
        }

      }

      default -> throw new RuntimeException("unexpected grant type " + type);

    }
  }
}
