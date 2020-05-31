package com.ansill.tesla.api.mock.endpoint.mock;

import com.ansill.tesla.api.mock.model.MockModel;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.websocket.WsHandler;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicReference;

import static io.javalin.apibuilder.ApiBuilder.ws;

public class MockController implements EndpointGroup{

  @Nonnull
  private final AtomicReference<MockModel> model;

  public MockController(@Nonnull AtomicReference<MockModel> model){
    this.model = model;
  }

  private void webSocket(WsHandler wsHandler){

  }

  @Override
  public void addEndpoints(){
    ws(this::webSocket);
  }
}
