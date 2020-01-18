package com.ansill.tesla.test.fake;

import com.ansill.tesla.low.Client;
import com.ansill.tesla.low.exception.AuthenticationException;
import com.ansill.tesla.low.exception.ClientException;
import com.ansill.tesla.low.exception.InvalidAccessTokenException;
import com.ansill.tesla.low.exception.ReAuthenticationException;
import com.ansill.tesla.test.TestUtility;
import com.ansill.tesla.test.fake.mock.MockModel;
import com.ansill.tesla.test.fake.mock.MockServer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.ansill.tesla.test.TestUtility.generateEmailAddress;
import static com.ansill.tesla.test.TestUtility.generateString;
import static org.junit.jupiter.api.Assertions.*;

class LowLevelClientTest{

    private static String CLIENT_ID;

    private static String CLIENT_SECRET;

    private static MockServer SERVER;

    private static AtomicReference<MockModel> STORE = new AtomicReference<>();

    private MockModel store;

    private Client client;

    @BeforeAll
    static void setUpServer(){
        Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.DEBUG);
        Configurator.setLevel(LogManager.getLogger(Client.class).getName(), Level.DEBUG);
        Configurator.setLevel("org.eclipse.jetty", Level.WARN);
        Configurator.setLevel("io.javalin", Level.WARN);
        CLIENT_ID = generateString(32);
        CLIENT_SECRET = generateString(32);
        SERVER = new MockServer(STORE, CLIENT_ID, CLIENT_SECRET);
    }

    @AfterAll
    static void tearDownServer(){
        SERVER.close();
    }

    @BeforeEach
    void reset(){

        store = new MockModel();
        STORE.set(store);

        // Set up connection
        client = new Client("http://localhost:" + SERVER.getPort(), CLIENT_ID, CLIENT_SECRET);
    }

    @Test
    void authenticateSuccess(){

        // Set up username and password
        var emailAddress = generateEmailAddress();
        var password = generateString(32);

        // Init store
        assertTrue(store.addAccount(emailAddress, password));

        // Run it
        var item = assertDoesNotThrow(() -> client.authenticate(emailAddress, password));

        // Assert not null
        assertNotNull(item);

    }

    @Test
    void refreshSuccess(){

        // Set up username and password
        var emailAddress = generateEmailAddress();
        var password = generateString(32);

        // Init store
        assertTrue(store.addAccount(emailAddress, password));

        // Authenticate it
        var response = store.authenticate(emailAddress, password);

        // Run it
        var item = assertDoesNotThrow(() -> client.refreshToken(response.get().getRefreshToken()));

        // Assert not null
        assertNotNull(item);

    }

    @Test
    void revokeSuccess(){

        // Set up username and password
        var emailAddress = generateEmailAddress();
        var password = generateString(32);

        // Init store
        assertTrue(store.addAccount(emailAddress, password));

        // Authenticate it
        var response = store.authenticate(emailAddress, password);

        // Assert that it exists
        assertTrue(store.refreshTokenExists(response.get().getRefreshToken()));

        // Run it
        assertDoesNotThrow(() -> client.revokeToken(response.get().getRefreshToken()));

        // Assert that it's deleted
        assertFalse(store.refreshTokenExists(response.get().getRefreshToken()));
    }

    @Test
    void refreshFailure(){

        // Run it
        assertThrows(ReAuthenticationException.class, () -> client.refreshToken(generateString(32)));

    }

    @Test
    void authenticateFailureWithInvalidClientIdAndSecret(){

        // Set up username and password
        var emailAddress = generateEmailAddress();
        var password = generateString(32);

        // Init store
        assertTrue(store.addAccount(emailAddress, password));

        // Create client with wrong client id and secret
        var client = new Client("http://localhost:" + SERVER.getPort(), generateString(32), generateString(23));

        // Run it
        var exception = assertThrows(ClientException.class, () -> client.authenticate(emailAddress, password));

        System.out.println("exception: " + exception.getMessage()); // TODO update this when we find a correct string
    }

    @Test
    void authenticateFailureWithInvalidCredentials(){

        // Set up username and password
        var emailAddress = generateEmailAddress();
        var password = generateString(32);

        // Run it
        var exception = assertThrows(AuthenticationException.class, () -> client.authenticate(emailAddress, password));

        // Ensure account name matches
        assertEquals(emailAddress, exception.getAccountName());
    }


    @Test
    void getVehiclesSuccessButEmpty(){

        // Set up username and password
        var emailAddress = generateEmailAddress();
        var password = generateString(32);

        // Init store
        assertTrue(store.addAccount(emailAddress, password));

        // Authenticate it
        var response = store.authenticate(emailAddress, password).orElseThrow();

        // Run it
        var item = assertDoesNotThrow(() -> client.getVehicles(response.getAccessToken()));

        // Assert not null
        assertNotNull(item);

        // Assert empty
        assertEquals(0, item.getCount());

        // Assert empty
        assertEquals(0, item.getResponse().size());

    }

    @Test
    void getVehiclesSuccessButMany(){

        // Set up username and password
        var emailAddress = generateEmailAddress();
        var password = generateString(32);

        // Create vehicles
        var v1 = TestUtility.createParkingVehicle();
        var v2 = TestUtility.createParkingVehicle();
        var v3 = TestUtility.createParkingVehicle();

        // Init store
        assertTrue(store.addAccount(emailAddress, password));
        assertTrue(store.addVehicleToAccount(emailAddress, v1));
        assertTrue(store.addVehicleToAccount(emailAddress, v2));
        assertTrue(store.addVehicleToAccount(emailAddress, v3));

        // Authenticate it
        var response = store.authenticate(emailAddress, password).orElseThrow();

        // Run it
        var item = assertDoesNotThrow(() -> client.getVehicles(response.getAccessToken()));

        // Assert not null
        assertNotNull(item);

        // Assert empty
        assertEquals(3, item.getCount());

        // Assert empty
        assertEquals(3, item.getResponse().size());

        // TODO check insides
    }

    @Test
    void getVehiclesInvalidToken(){

        // Set up username and password
        var emailAddress = generateEmailAddress();
        var password = generateString(32);

        // Init store
        assertTrue(store.addAccount(emailAddress, password));

        // Authenticate it
        store.authenticate(emailAddress, password).orElseThrow();

        // Run it
        assertThrows(InvalidAccessTokenException.class, () -> client.getVehicles(generateString(32)));
    }

    @Test
    void getVehicleNotFound(){

        // Set up username and password
        var emailAddress = generateEmailAddress();
        var password = generateString(32);

        // Create vehicles
        var v1 = TestUtility.createParkingVehicle();
        var v2 = TestUtility.createParkingVehicle();
        var v3 = TestUtility.createParkingVehicle();

        // Init store
        assertTrue(store.addAccount(emailAddress, password));
        assertTrue(store.addVehicleToAccount(emailAddress, v1));
        assertTrue(store.addVehicleToAccount(emailAddress, v2));
        assertTrue(store.addVehicleToAccount(emailAddress, v3));

        // Authenticate it
        var response = store.authenticate(emailAddress, password).orElseThrow();

        // Run it
        var item = assertDoesNotThrow(() -> client.getVehicle(response.getAccessToken(), generateString(32)));

        // Assert not null
        assertNotNull(item);

        // Assert empty
        assertEquals(Optional.empty(), item);

        // TODO check insides
    }


    @Test
    void getVehicleBadToken(){

        // Set up username and password
        var emailAddress = generateEmailAddress();
        var password = generateString(32);

        // Create vehicles
        var v1 = TestUtility.createParkingVehicle();
        var v2 = TestUtility.createParkingVehicle();
        var v3 = TestUtility.createParkingVehicle();

        // Init store
        assertTrue(store.addAccount(emailAddress, password));
        assertTrue(store.addVehicleToAccount(emailAddress, v1));
        assertTrue(store.addVehicleToAccount(emailAddress, v2));
        assertTrue(store.addVehicleToAccount(emailAddress, v3));

        // Authenticate it
        store.authenticate(emailAddress, password).orElseThrow();

        // Run it
        assertThrows(
                InvalidAccessTokenException.class,
                () -> client.getVehicle(generateString(23), v2.vehicleAtomicReference.get().getIdString())
        );

    }

    @Test
    void getVehicleSuccess(){

        // Set up username and password
        var emailAddress = generateEmailAddress();
        var password = generateString(32);

        // Create vehicles
        var v1 = TestUtility.createParkingVehicle();
        var v2 = TestUtility.createParkingVehicle();
        var v3 = TestUtility.createParkingVehicle();

        // Init store
        assertTrue(store.addAccount(emailAddress, password));
        assertTrue(store.addVehicleToAccount(emailAddress, v1));
        assertTrue(store.addVehicleToAccount(emailAddress, v2));
        assertTrue(store.addVehicleToAccount(emailAddress, v3));

        // Authenticate it
        var response = store.authenticate(emailAddress, password).orElseThrow();

        // Run it
        var item = assertDoesNotThrow(() -> client.getVehicle(
                response.getAccessToken(),
                v2.vehicleAtomicReference.get().getIdString()
        ));

        // Assert not null
        assertNotNull(item);

        // Assert not empty
        assertNotEquals(Optional.empty(), item);

        // TODO check insides
    }
}