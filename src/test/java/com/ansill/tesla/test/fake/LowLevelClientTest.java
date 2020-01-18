package com.ansill.tesla.test.fake;

import com.ansill.tesla.low.Client;
import com.ansill.tesla.low.exception.AuthenticationException;
import com.ansill.tesla.test.fake.model.MainStore;
import com.ansill.tesla.test.fake.model.MockServer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static com.ansill.tesla.test.TestUtility.generateEmailAddress;
import static com.ansill.tesla.test.TestUtility.generateString;
import static org.junit.jupiter.api.Assertions.*;

class LowLevelClientTest{

    private static String CLIENT_ID;

    private static String CLIENT_SECRET;

    private static MockServer SERVER;

    private static AtomicReference<MainStore> STORE = new AtomicReference<>();

    private MainStore store;

    private Client client;

    @BeforeAll
    static void setUpServer(){
        Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.DEBUG);
        Configurator.setLevel(LogManager.getLogger(Client.class).getName(), Level.DEBUG);
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

        store = new MainStore();
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
        store.addAccount(emailAddress, password);

        // Run it
        var item = assertDoesNotThrow(() -> client.authenticate(emailAddress, password));

        // Assert not null
        assertNotNull(item);

    }

    @Test
    void authenticateFailure(){

        // Set up username and password
        var emailAddress = generateEmailAddress();
        var password = generateString(32);

        // Init store
        store.addAccount(emailAddress, password);

        // Set up connection
        var client = new Client();

        // Run it
        var exception = assertThrows(AuthenticationException.class, () -> client.authenticate(emailAddress, password));

        // Ensure account name matches
        assertEquals(emailAddress, exception.getAccountName());
    }
}