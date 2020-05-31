package com.ansill.tesla.api.mock.endpoint.tesla;

import com.ansill.tesla.api.mock.MockUtility;
import com.ansill.tesla.api.mock.endpoint.tesla.v1.response.EmptyResponse;
import com.ansill.tesla.api.mock.endpoint.tesla.v1.response.GenericErrorResponse;
import com.ansill.tesla.api.mock.endpoint.tesla.v1.response.SuccessfulAuthenticationResponse;
import com.ansill.tesla.api.mock.model.MockModel;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiContent;
import io.javalin.plugin.openapi.annotations.OpenApiFormParam;
import io.javalin.plugin.openapi.annotations.OpenApiParam;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;

import javax.annotation.Nonnull;
import java.util.Collections;
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
    post("token", this::token);
    post("revoke", this::revoke);
  }

  @OpenApi(
    path = "/oauth/revoke",            // only necessary to include when using static method references
    method = HttpMethod.POST,    // only necessary to include when using static method references
    summary = "Revokes tokens",
    description = """
      Revokes created credentials. Doesn't currently work right as official Tesla API will return 500
      """,
    operationId = "revoke",
    tags = {"Account"},
    headers = {
      @OpenApiParam(
        name = "Authorization",
        required = true,
        description = "Bearer {access_token}"
      )
    },
    responses = {
      @OpenApiResponse(status = "200", content = {@OpenApiContent(from = EmptyResponse.class)}),
    }
  )
  public void revoke(Context context){

    // ALWAYS 200, never anything else
    context.status(200);
    context.json(Collections.emptyMap());

    // Get map
    var authorization = context.header("Authorization");

    // Ensure other minimum stuff is supplied
    if(authorization != null){
      if(authorization.startsWith("Bearer ")){
        var token = authorization.substring("Bearer ".length());
        if(model.get().revokeToken(token.trim())){
          // Tesla's actual response as of 2020-05-30
          context.status(500);
          context.result("""
            <!DOCTYPE html>
            <html>
            <head>
              <title>We're sorry, but something went wrong (500)</title>
              <style type="text/css">
                body { background-color: #fff; color: #666; text-align: center; font-family: arial, sans-serif; }
                div.dialog {
                  width: 25em;
                  padding: 0 4em;
                  margin: 4em auto 0 auto;
                  border: 1px solid #ccc;
                  border-right-color: #999;
                  border-bottom-color: #999;
                }
                h1 { font-size: 100%; color: #f00; line-height: 1.5em; }
              </style>
            </head>
                        
            <body>
              <!-- This file lives in public/500.html -->
              <div class="dialog">
                <h1>We're sorry, but something went wrong.</h1>
              </div>
            </body>
            </html>
            """);
        }
      }
    }
  }

  @OpenApi(
    path = "/oauth/token",            // only necessary to include when using static method references
    method = HttpMethod.POST,    // only necessary to include when using static method references
    summary = "Creates or refreshes tokens",
    description = "Creates credentials with email address and password, or refreshes credentials with refresh token. " +
                  "Form parameter 'grant_type' must be either 'password' or 'refresh_token'. " +
                  "If using email address and password, grant_type must be 'password' and form parameters 'email' " +
                  "and 'password' must be on the form. " +
                  "If using refresh token, grant_type must be 'refresh_token' and form parameter 'refresh_token' " +
                  "must be used.",
    formParams = {
      @OpenApiFormParam(
        name = "grant_type",
        required = true
      ),
      @OpenApiFormParam(
        name = "email"
      ),
      @OpenApiFormParam(
        name = "password"
      ),
      @OpenApiFormParam(
        name = "refresh_token"
      ),
      @OpenApiFormParam(
        name = "client_id",
        required = true
      ),
      @OpenApiFormParam(
        name = "client_secret",
        required = true
      )
    },
    operationId = "token",
    tags = {"Account"},
    responses = {
      @OpenApiResponse(status = "200", content = {@OpenApiContent(from = SuccessfulAuthenticationResponse.class)}),
      @OpenApiResponse(status = "401", content = {@OpenApiContent(from = GenericErrorResponse.class)})
    }
  )
  public void token(Context context){

    // Get map
    var map = context.formParamMap();

    // Ensure other minimum stuff is supplied
    if(!map.containsKey("grant_type") ||
       !map.containsKey("client_id") ||
       !map.containsKey("client_secret")
    ){
      context.status(401);
      context.json(new GenericErrorResponse(
        "invalid_request",
        "The request is missing a required parameter, includes an unsupported parameter value, or is otherwise malformed."
      ));
      return;
    }

    // Ensure client id and secret are correct
    if(!model.get().getClientId().equals(map.get("client_id").iterator().next()) ||
       !model.get().getClientSecret().equals(map.get(
         "client_secret").iterator().next())){
      context.status(401);
      context.json(new GenericErrorResponse(
        "invalid_client",
        "The request is missing a required parameter, includes an unsupported parameter value, or is otherwise malformed."
        // TODO get correct message?
      ));
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
          context.json(new GenericErrorResponse(
            "invalid_request",
            "The request is missing a required parameter, includes an unsupported parameter value, or is otherwise malformed."
          ));
          return;
        }

        // Now, check if credentials are correct
        var refreshToken = map.get("refresh_token").iterator().next();

        // Auth
        var result = model.get().refreshToken(refreshToken);

        // Handle result
        if(result.isPresent()){
          context.status(200);
          context.json(SuccessfulAuthenticationResponse.convert(result.get()));
        }else{
          context.status(401);
          context.json(new GenericErrorResponse(
            "invalid_grant",
            "The provided authorization grant is invalid, expired, revoked, does not match the redirection URI used in the authorization request, or was issued to another client."
          ));
        }
      }

      case "password" -> {

        // Ensure email and password are provided
        if(!map.containsKey("email") || !map.containsKey("password")){
          context.status(401);
          context.json(new GenericErrorResponse(
            "invalid_request",
            "The request is missing a required parameter, includes an unsupported parameter value, or is otherwise malformed."
          ));
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
          context.json(SuccessfulAuthenticationResponse.convert(result.get()));
        }else{
          context.status(401);
          context.json(new GenericErrorResponse(
            "authorization_required_for_txid_'" + MockUtility.generateString(32) + "'"
          ));
        }

      }

      default -> throw new RuntimeException("unexpected grant type " + type);

    }
  }
}
