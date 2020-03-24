package com.ansill.tesla.test;

import com.ansill.tesla.api.low.model.VehicleResponse;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MiscTest{

  @Test
  void test(){

    // Create string
    String validButNotCorrectJSON = "{\"something\":\"else\"}";

    // Test it
    assertNotNull(new Gson().fromJson(validButNotCorrectJSON, VehicleResponse.class));
  }
}
