package com.ansill.tesla.test.fake.mock;

import com.ansill.tesla.api.low.model.GenericErrorResponse;
import com.google.gson.Gson;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;

public class VehicleEndpoint implements Handler{

    private final AtomicReference<MockModel> model;

    public VehicleEndpoint(AtomicReference<MockModel> model){
        this.model = model;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception{

        // Get authorization
        var authorization = context.header("Authorization");

        // Ensure that authorization exists
        if(authorization == null){
            context.status(401);
            return;
        }

        // Ensure bearer exists
        if(!authorization.startsWith("Bearer ")){
            context.status(401);
            return;
        }

        // Remove bearer
        authorization = authorization.substring("Bearer ".length());

        // Ensure that access token exists
        if(!model.get().accessTokenExists(authorization)){
            context.status(401);
            return;
        }

        // Ensure that id is provided
        if(!context.pathParamMap().containsKey("id")){
            new VehiclesEndpoint(model).handle(context);
            return;
        }

        // Get vehicle
        var vehicle = model.get().getVehicle(authorization, context.pathParam("id"));
        if(vehicle.isPresent()){
            context.status(200);
            context.result(new Gson().toJson(vehicle.get()));
        }else{
            context.status(404);
            context.result(new Gson().toJson(new GenericErrorResponse("not_found", "", null)));
        }
    }
}
