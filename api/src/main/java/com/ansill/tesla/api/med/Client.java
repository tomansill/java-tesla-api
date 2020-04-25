package com.ansill.tesla.api.med;

import com.ansill.tesla.api.low.exception.AuthenticationException;
import com.ansill.tesla.api.low.exception.ClientException;
import com.ansill.tesla.api.low.exception.InvalidAccessTokenException;
import com.ansill.tesla.api.low.exception.ReAuthenticationException;
import com.ansill.tesla.api.low.exception.VehicleIDNotFoundException;
import com.ansill.tesla.api.low.model.CompleteVehicleDataResponse;
import com.ansill.tesla.api.med.model.AccountCredentials;
import com.ansill.tesla.api.med.model.ChargeState;
import com.ansill.tesla.api.med.model.ClimateState;
import com.ansill.tesla.api.med.model.CompleteData;
import com.ansill.tesla.api.med.model.DriveState;
import com.ansill.tesla.api.med.model.GuiSettings;
import com.ansill.tesla.api.med.model.Vehicle;
import com.ansill.tesla.api.med.model.VehicleConfig;
import com.ansill.tesla.api.med.model.VehicleState;
import com.ansill.validation.Validation;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ansill.tesla.api.utility.Constants.*;


/** Slightly opinionated client */
public class Client{

  /** Low-level client */
  private final com.ansill.tesla.api.low.Client client;

  /** Sets up high-level client with default URL, client ID, and client secret */
  public Client(){
    this(URL, CLIENT_ID, CLIENT_SECRET);
  }

  /**
   * Sets up high-level client with custom URL, client ID, and client secret
   *
   * @param url          URL
   * @param clientId     client id
   * @param clientSecret client secret
   */
  public Client(@Nonnull String url, @Nonnull String clientId, @Nonnull String clientSecret){

    // Assign it
    this.client = new com.ansill.tesla.api.low.Client(url, clientId, clientSecret);
  }

  @Nonnull
  public AccountCredentials authenticate(
    @Nonnull String emailAddress,
    @Nonnull String password
  ) throws AuthenticationException, ClientException{

    // Check parameters
    Validation.assertNonnull(emailAddress, "emailAddress");
    Validation.assertNonnull(password, "password");

    // Get response
    return AccountCredentials.convert(client.authenticate(emailAddress, password));
  }

  @Nonnull
  public AccountCredentials refreshToken(@Nonnull String refreshToken)
  throws ReAuthenticationException, ClientException{

    // Check parameters
    Validation.assertNonnull(refreshToken, "refreshToken");

    // Get response
    return AccountCredentials.convert(client.refreshToken(refreshToken));
  }

  public void revokeToken(@Nonnull String refreshToken){

    // Check parameters
    Validation.assertNonnull(refreshToken, "refreshToken");

    // Revoke it
    client.revokeToken(refreshToken);
  }

  @Nonnull
  public Collection<Vehicle> getVehicles(@Nonnull String accessToken) throws InvalidAccessTokenException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Get vehicles
    return client.getVehicles(accessToken)
                 .getResponse()
                 .stream()
                 .map(Vehicle::convert)
                 .collect(Collectors.toUnmodifiableList());
  }

  @Nonnull
  public Optional<Vehicle> getVehicle(@Nonnull String accessToken, @Nonnull String idString)
  throws InvalidAccessTokenException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Get vehicle
    return client.getVehicle(accessToken, idString).map(Vehicle::convert);
  }

  public Vehicle wakeup(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Call it
    return Vehicle.convert(this.client.wakeup(accessToken, idString).getResponse());
  }

  public void unlockDoors(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Call it
    this.client.unlockDoors(accessToken, idString);
  }

  public void lockDoors(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Call it
    this.client.lockDoors(accessToken, idString);
  }

  public void honkHorn(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Call it
    this.client.honkHorn(accessToken, idString);
  }

  public void flashLights(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Call it
    this.client.flashLights(accessToken, idString);
  }

  public void startHVACSystem(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Call it
    this.client.startHVACSystem(accessToken, idString);
  }

  public void stopHVACSystem(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Call it
    this.client.stopHVACSystem(accessToken, idString);
  }

  public void setMaxRangeChargeLimit(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Call it
    this.client.setMaxRangeChargeLimit(accessToken, idString);
  }

  public void setStandardChargeLimit(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Call it
    this.client.setStandardChargeLimit(accessToken, idString);
  }

  public void openChargePortDoor(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Call it
    this.client.openChargePortDoor(accessToken, idString);
  }

  public void closeChargePortDoor(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Call it
    this.client.closeChargePortDoor(accessToken, idString);
  }

  public void startCharge(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Call it
    this.client.startCharge(accessToken, idString);
  }

  public void stopCharge(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Call it
    this.client.stopCharge(accessToken, idString);
  }

  public void toggleMediaPlayback(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Call it
    this.client.toggleMediaPlayback(accessToken, idString);
  }

  public void nextMediaTrack(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Call it
    this.client.nextMediaTrack(accessToken, idString);
  }

  public void previousMediaTrack(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Call it
    this.client.previousMediaTrack(accessToken, idString);
  }

  public void nextFavoriteMedia(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Call it
    this.client.nextFavoriteMedia(accessToken, idString);
  }

  public void previousFavoriteMedia(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Call it
    this.client.previousFavoriteMedia(accessToken, idString);
  }

  public void turnMediaVolumeUp(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Call it
    this.client.turnMediaVolumeUp(accessToken, idString);
  }

  public void turnMediaVolumeDown(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Call it
    this.client.turnMediaVolumeDown(accessToken, idString);
  }


  public void cancelSoftwareUpdate(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Call it
    this.client.cancelSoftwareUpdate(accessToken, idString);
  }

  @Nonnull
  public CompleteVehicleDataResponse getVehicleData(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Get the data
    //return client.getVehicleData(accessToken, idString);
    return null; // TODO
  }

  @Nonnull
  public ChargeState getVehicleChargeState(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Get the data
    return ChargeState.convert(client.getVehicleChargeState(accessToken, idString));
  }


  @Nonnull
  public ClimateState getVehicleClimateState(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Get the data
    return ClimateState.convert(client.getVehicleClimateState(accessToken, idString));
  }

  @Nonnull
  public DriveState getVehicleDriveState(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Get the data
    return DriveState.convert(client.getVehicleDriveState(accessToken, idString));
  }

  @Nonnull
  public VehicleState getVehicleVehicleState(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Get the data
    return VehicleState.convert(client.getVehicleVehicleState(accessToken, idString));
  }

  @Nonnull
  public GuiSettings getVehicleGuiSettings(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Get the data
    return GuiSettings.convert(client.getVehicleGuiSettings(accessToken, idString));
  }

  @Nonnull
  public VehicleConfig getVehicleVehicleConfig(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Get the data
    return VehicleConfig.convert(client.getVehicleVehicleConfig(accessToken, idString));
  }


  @Nonnull
  public CompleteData getVehicleCompleteData(@Nonnull String accessToken, @Nonnull String idString)
  throws VehicleIDNotFoundException{

    // Check parameters
    Validation.assertNonnull(accessToken, "accessToken");

    // Check parameters
    Validation.assertNonnull(idString, "idString");

    // Get the data
    return CompleteData.convert(client.getVehicleData(accessToken, idString).getResponse());
  }
}
