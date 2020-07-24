package com.ansill.tesla.api.data.model.response;

import com.ansill.tesla.api.data.utility.SimpleSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import static com.ansill.utility.Utility.simpleToString;

@JsonSerialize(using = SimpleSerializer.class)
@SuppressWarnings("unused")
public final class SimpleReasonResponse{

  @JsonProperty
  private final boolean result;

  @JsonProperty
  private final String reason;

  public SimpleReasonResponse(@JsonProperty boolean result, @JsonProperty String reason){
    this.result = result;
    this.reason = reason;
  }

  public boolean getResult(){
    return result;
  }

  public String getReason(){
    return reason;
  }

  @Override
  public String toString(){
    return simpleToString(this);
  }
}
