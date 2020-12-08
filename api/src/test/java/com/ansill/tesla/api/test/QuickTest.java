package com.ansill.tesla.api.test;

import com.ansill.tesla.api.exception.VehicleSleepingException;
import com.ansill.tesla.api.high.Client;
import com.ansill.tesla.api.high.Vehicle;
import com.ansill.tesla.api.high.model.VehicleSnapshot;

import java.util.Scanner;

import static com.ansill.utility.Utility.f;

public class QuickTest{

  public static void main(String... args){

    // Get client
    try(var client = Client.builder().build()){

      // Set up scanner
      try(var scanner = new Scanner(System.in)){

        // Get username
        String email = null;
        System.out.print("Enter username:");
        while(email == null || email.isBlank()){
          email = scanner.nextLine();
        }

        // Get password
        String password = null;
        System.out.print("Enter password:");
        while(password == null || password.isBlank()){
          password = scanner.nextLine();
        }

        // Grab account
        try(var account = client.authenticate(email, password).orElseThrow()){

          // List all vehicles
          var list = account.getVehicles();

          // Print all
          for(Vehicle vehicle : list){
            VehicleSnapshot snapshot;
            try{
              snapshot = vehicle.getVehicleSnapshot();
            }catch(VehicleSleepingException e){
              vehicle.wakeUp(true);
              snapshot = vehicle.getVehicleSnapshot();
            }
            System.out.println(f(
              "name: {} \n {}",
              vehicle.getName(),
              snapshot
            ));
          }
        }

      }

      System.out.println("Why am i not exiting");
    }
  }
}
