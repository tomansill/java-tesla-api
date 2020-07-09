package com.ansill.tesla.api.mock.endpoint.tesla.v1.response;

import com.ansill.tesla.api.data.model.response.SimpleResponse;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

public class CountResponse<T> extends SimpleResponse<List<T>>{

  private final int count;

  public CountResponse(){
    super(new LinkedList<>());
    count = 0;
  }

  public CountResponse(@Nonnull List<T> list){
    super(list);
    count = list.size();
  }
}
