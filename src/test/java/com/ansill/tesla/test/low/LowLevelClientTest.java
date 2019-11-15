package com.ansill.tesla.test.low;

import com.ansill.tesla.low.LowLevelClient;
import com.ansill.tesla.low.exception.APIProtocolException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

import static com.ansill.tesla.utility.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

class LowLevelClientTest{

    private static String username = "";

    private static String password = "";

    private static String test_vehicle = "";

    //@BeforeAll
    static void askCredentials(){

        // Announce
        System.out.println("To run the tests, the tesla credentials are required");

        // Set up scanner
        Scanner scanner = new Scanner(System.in);

        // Ask for username
        do{
            System.out.print("Your Tesla email (used for testing): ");
            String input = scanner.nextLine().trim();
            if(!input.isEmpty()){
                username = input;
                break;
            }
        }while(true);

        // Ask for password
        do{
            System.out.print("Your Tesla password (used for testing): ");
            String input = scanner.nextLine().trim();
            if(!input.isEmpty()){
                password = input;
                break;
            }
        }while(true);

    }

    @Test
    void testValidAuthentication() throws IOException{

        // Use default
        LowLevelClient client = new LowLevelClient();

        // Send it
        assertNotEquals(Optional.empty(), client.authenticate(username, password));
    }

    @Test
    void testInvalidAuthenticationWithWrongPassword() throws IOException{

        // Use default
        LowLevelClient client = new LowLevelClient();

        // Send it
        assertEquals(Optional.empty(), client.authenticate(username, password + "a"));
    }

    @Test
    void testInvalidAuthenticationWithWrongURL(){

        // Use custom
        LowLevelClient client = new LowLevelClient("http://www.example.com/", CLIENT_ID, CLIENT_SECRET);

        // Send it
        assertThrows(APIProtocolException.class, () -> client.authenticate(username, password));
    }

    @Test
    void testInvalidAuthenticationWithWrongClientID(){

        // Use custom
        LowLevelClient client = new LowLevelClient(URL, CLIENT_ID + "awfw", CLIENT_SECRET);

        // Send it
        assertThrows(APIProtocolException.class, () -> client.authenticate(username, password));
    }

    @Test
    void testValidTokenRefresh() throws IOException{

        // Use default
        LowLevelClient client = new LowLevelClient();

        // Send it
        var response = client.authenticate(username, password);

        // Assert
        assertNotEquals(Optional.empty(), response);

        // Refresh it
        assertNotEquals(Optional.empty(), client.refreshToken(response.orElseThrow().getRefreshToken()));
    }

    @Test
    void testInvalidTokenRefreshWithWrongPassword() throws IOException{

        // Use default
        LowLevelClient client = new LowLevelClient();

        // Send it
        var response = client.authenticate(username, password);

        // Assert
        assertNotEquals(Optional.empty(), response);

        // Refresh it
        assertEquals(Optional.empty(), client.refreshToken(response.orElseThrow().getRefreshToken() + "a"));
    }

    @Test
    void testInvalidTokenRefreshWithWrongURL() throws IOException{

        // Use default
        LowLevelClient client1 = new LowLevelClient();

        // Send it
        var response = client1.authenticate(username, password);

        // Assert
        assertNotEquals(Optional.empty(), response);

        // Use custom
        LowLevelClient client2 = new LowLevelClient("http://www.example.com/", CLIENT_ID, CLIENT_SECRET);

        // Send it
        assertThrows(APIProtocolException.class, () -> client2.refreshToken(response.orElseThrow().getRefreshToken()));
    }

    @Test
    void testInvalidTokenRefreshWithWrongClientID() throws IOException{

        // Use default
        LowLevelClient client1 = new LowLevelClient();

        // Send it
        var response = client1.authenticate(username, password);

        // Assert
        assertNotEquals(Optional.empty(), response);

        // Use custom
        LowLevelClient client2 = new LowLevelClient(URL, CLIENT_ID + "something", CLIENT_SECRET);

        // Send it
        assertThrows(APIProtocolException.class, () -> client2.refreshToken(response.orElseThrow().getRefreshToken()));
    }

    @Test
    void testGetVehiclesValid() throws IOException{


        // Use default
        LowLevelClient client1 = new LowLevelClient();

        // Send it
        var response = client1.authenticate(username, password);

        // Assert
        assertNotEquals(Optional.empty(), response);

        // Now obtain vehicles list
        var vehicles = client1.getVehicles(response.orElseThrow().getAccessToken());

        // Assert count
        assertEquals(1, vehicles.size());

        // Assert name
        System.out.println(vehicles);
    }

    @Test
    void testGetVehicleValid() throws IOException{


        // Use default
        LowLevelClient client1 = new LowLevelClient();

        // Send it
        var response = client1.authenticate(username, password);

        // Assert
        assertNotEquals(Optional.empty(), response);

        // Now obtain vehicles list
        var vehicles = client1.getVehicles(response.orElseThrow().getAccessToken());

        // Assert count
        assertEquals(1, vehicles.size());

        // Get a id
        String id_s = vehicles.iterator().next().getIdString();

        // Send it again
        var vehicle = client1.getVehicle(response.orElseThrow().getAccessToken(), id_s);

        // Assert
        assertNotEquals(Optional.empty(), vehicle);

        // Assert count
        assertEquals(id_s, vehicle.orElseThrow().getIdString());

    }
}