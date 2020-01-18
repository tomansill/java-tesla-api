package com.ansill.tesla.test;

import com.ansill.tesla.high.Client;
import com.ansill.tesla.low.exception.APIProtocolException;
import com.ansill.tesla.low.exception.AuthenticationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Optional;
import java.util.Properties;

import static com.ansill.tesla.utility.Constants.*;
import static com.ansill.tesla.utility.Utility.f;
import static org.junit.jupiter.api.Assertions.*;

class HighLevelClientTest{

    private static final File CREDENTIALS_FILE = new File("ignored/tesla-credentials.properties");

    private static String username = "";

    private static String password = "";

    private static String test_vehicle = "";

    @BeforeAll
    static void askCredentials() throws IOException{

        // Ensure file exists
        if(!CREDENTIALS_FILE.exists())
            throw new RuntimeException(f("File located at '{}' does not exist!", CREDENTIALS_FILE.getPath()));

        // Open it up
        Properties properties = new Properties();
        try(Reader reader = new FileReader(CREDENTIALS_FILE)){
            properties.load(reader);
        }

        // Obtain attributes
        username = properties.getProperty("username");
        if(username == null) throw new RuntimeException(f("Expecting '{}' in the property file", "username"));
        password = properties.getProperty("password");
        if(password == null) throw new RuntimeException(f("Expecting '{}' in the property file", "password"));
    }

    @Test
    void testValidAuthentication() throws IOException, AuthenticationException{

        // Use default
        com.ansill.tesla.low.Client client = new com.ansill.tesla.low.Client();

        // Send it
        assertNotEquals(Optional.empty(), client.authenticate(username, password));
    }

    @Test
    void testInvalidAuthenticationWithWrongPassword() throws IOException{

        // Use default
        Client client = new Client();

        // Send it
        assertEquals(Optional.empty(), client.authenticate(username, password + "a"));
    }

    @Test
    void testInvalidAuthenticationWithWrongURL(){

        // Use custom
        Client client = new Client("http://www.example.com/", CLIENT_ID, CLIENT_SECRET);

        // Send it
        assertThrows(APIProtocolException.class, () -> client.authenticate(username, password));
    }

    @Test
    void testInvalidAuthenticationWithWrongClientID(){

        // Use custom
        Client client = new Client(URL, CLIENT_ID + "awfw", CLIENT_SECRET);

        // Send it
        assertThrows(APIProtocolException.class, () -> client.authenticate(username, password));
    }
}