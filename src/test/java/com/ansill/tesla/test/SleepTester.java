package com.ansill.tesla.test;

import com.ansill.tesla.api.exception.VehicleSleepingException;
import com.ansill.tesla.api.low.Client;
import com.ansill.tesla.api.low.exception.AuthenticationException;
import com.ansill.tesla.api.low.exception.VehicleIDNotFoundException;
import com.ansill.tesla.api.low.model.SuccessfulAuthenticationResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.Duration;
import java.util.Properties;

import static com.ansill.tesla.api.utility.Utility.f;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class SleepTester{

  private static final File CREDENTIALS_FILE = new File("ignored/tesla-credentials.properties");

  private static Client client;

  private static SuccessfulAuthenticationResponse creds;

  private static String id;

  @BeforeAll
  static void setup() throws AuthenticationException, IOException{

    // Ensure file exists
    if(!CREDENTIALS_FILE.exists())
      throw new RuntimeException(f("File located at '{}' does not exist!", CREDENTIALS_FILE.getPath()));

    // Open it up
    Properties properties = new Properties();
    try(Reader reader = new FileReader(CREDENTIALS_FILE)){
      properties.load(reader);
    }

    // Obtain attributes
    var username = properties.getProperty("username");
    if(username == null) throw new RuntimeException(f("Expecting '{}' in the property file", "username"));
    var password = properties.getProperty("password");
    if(password == null) throw new RuntimeException(f("Expecting '{}' in the property file", "password"));

    // Get client
    client = new Client();

    // Authenticate
    creds = client.authenticate(username, password);

    // Get vehicle id
    var list = client.getVehicles(creds.getAccessToken());

    // Exception if empty
    if(list.getCount() == 0) throw new IllegalStateException("Vehicles list is empty!");

    // Pick one
    id = list.getResponse().iterator().next().getIdString();
  }

  void getPeriod(@Nonnull RunnableWithException function) throws InterruptedException, VehicleIDNotFoundException{

    // Wake it up now
    client.wakeup(creds.getAccessToken(), id);
    boolean notsleeping = false;
    for(int i = 0; i < 5; i++){
      try{
        client.getVehicleData(creds.getAccessToken(), id);
        notsleeping = true;
        break;
      }catch(VehicleSleepingException e){
        Thread.sleep(Duration.ofSeconds(15).toMillis());
      }
    }

    assumeTrue(notsleeping, "Vehicle failed to wake up!");

    Duration duration = Duration.ofMinutes(4);
    boolean found = false;

    try{

      while(duration.compareTo(Duration.ofHours(2)) < 0){
        System.out.println("Trying " + duration);
        Thread.sleep(duration.toMillis());
        function.run();
        duration = duration.plus(duration.dividedBy(4));
      }

    }catch(VehicleSleepingException e){
      found = true;
    }

    // Report
    if(found){
      System.out.println("Found approximate duration, it is " + duration);
    }else{
      System.out.println("Failed to find duration, last tried duration is " + duration);
    }
  }

  @Test
  void getPeriodForComplete() throws InterruptedException, VehicleIDNotFoundException{
    getPeriod(() -> client.getVehicleData(creds.getAccessToken(), id));
  }

  @Test
  void getPeriodForDriveState() throws InterruptedException, VehicleIDNotFoundException{
    getPeriod(() -> client.getVehicleDriveState(creds.getAccessToken(), id));
  }

  @FunctionalInterface
  private interface RunnableWithException{
    void run() throws VehicleIDNotFoundException;
  }

}
