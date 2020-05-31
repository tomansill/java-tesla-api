package com.ansill.tesla.api.mock.endpoint.tesla.v1.response;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class CountResponse<T> extends Response<List<T>>{
  public int count;

  public CountResponse(){
    count = 0;
    response = new LinkedList<>();
  }

  public CountResponse(@Nonnull Collection<T> list){
    count = list.size();
    response = new LinkedList<>(list);
  }
}
