package com.ansill.tesla.api.mock.model;

import com.ansill.validation.Validation;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

public class MockModel{

  @Nonnull
  private final Map<String,MockAccount> accounts = new ConcurrentHashMap<>();

  @Nonnull
  private final Map<MockAccount,Map<String,MockVehicle>> vehicles = new ConcurrentHashMap<>();

  @Nonnull
  private final Map<String,MockVehicle> idToVehicles = new ConcurrentHashMap<>();

  @Nonnull
  private final MockSessionManager sessionManager = new MockSessionManager();

  @Nonnull
  private String clientId;

  @Nonnull
  private String clientSecret;

  //######### SUBSCRIPTIONS #############
  private final Map<AutoCloseable,BiConsumer<String,Optional<MockSession>>> refreshTokenSubscriptions = new HashMap<>();

  private final Map<AutoCloseable,BiConsumer<String,Set<MockVehicle>>> vehiclesSubscription = new HashMap<>();

  private final Map<AutoCloseable,BiConsumer<String,Optional<MockVehicle>>> vehicleDataRequestSubscription = new HashMap<>();

  public MockModel(@Nonnull String clientId, @Nonnull String clientSecret){
    this.clientId = Validation.assertNonnull(clientId, "clientId");
    this.clientSecret = Validation.assertNonnull(clientSecret, "clientSecret");
  }

  @Nonnull
  public String getClientId(){
    return clientId;
  }

  public void setClientId(@Nonnull String clientId){
    this.clientId = Validation.assertNonnull(clientId, "clientId");
  }

  @Nonnull
  public String getClientSecret(){
    return clientSecret;
  }

  public void setClientSecret(@Nonnull String clientSecret){
    this.clientSecret = Validation.assertNonnull(clientSecret, "clientSecret");
  }

  @Nonnull
  public MockAccount createAccount(@Nonnull String emailAddress, @Nonnull String password){
    var account = new MockAccount(emailAddress.toLowerCase(), password);
    if(accounts.containsKey(emailAddress.toLowerCase())) throw new IllegalStateException(
      "Duplicate account with same email address exists!");
    accounts.put(emailAddress.toLowerCase(), account);
    return account;
  }

  @Nonnull
  public MockVehicle createVehicle(@Nonnull MockAccount account){

    // Get unique vehicle id
    var found = new AtomicBoolean(false);
    MockVehicle vehicle;
    do{
      vehicle = idToVehicles.computeIfAbsent(Math.abs(new Random().nextLong()) + "", id -> {
        found.set(true);
        return MockVehicle.randomParked(id);
      });
    }while(!found.get());

    // Add to account
    MockVehicle finalVehicle = vehicle;
    vehicles.compute(account, (acct, map) -> {
      if(map == null) map = new ConcurrentHashMap<>();
      map.put(finalVehicle.getIdString(), finalVehicle);
      return map;
    });

    // Return it
    return vehicle;
  }

  public boolean removeAccount(@Nonnull MockAccount account){
    AtomicBoolean found = new AtomicBoolean(false);
    accounts.compute(account.getEmailAddress().toLowerCase(), (email, acct) -> {

      // If null, short circuit
      if(acct == null) return null;

      // Remove all sessions
      sessionManager.removeAllSession(acct);

      // Remove all vehicles
      vehicles.remove(account);

      // Return null to delete
      return null;

    });
    return found.get();
  }

  @Nonnull
  public Optional<MockSession> authenticate(@Nonnull String emailAddress, @Nonnull String password){
    var sessionRef = new AtomicReference<MockSession>();
    var found = null != accounts.compute(emailAddress.toLowerCase(), (email, account) -> {
      if(account == null) return null;
      if(account.getPassword().equals(password)) sessionRef.set(sessionManager.createSession(account));
      return account;
    });
    if(!found || sessionRef.get() == null) return Optional.empty();
    return Optional.ofNullable(sessionRef.get());
  }

  public boolean changePassword(@Nonnull String emailAddress, @Nonnull String password, boolean dropSessions){
    return null != this.accounts.compute(emailAddress.toLowerCase(), (key, account) -> {
      if(account == null) return null;
      account.setPassword(password);
      if(dropSessions) sessionManager.removeAllSession(account);
      return account;
    });
  }

  @Nonnull
  public AutoCloseable subscribeToRefreshTokenCall(@Nonnull BiConsumer<String,Optional<MockSession>> consumer){
    var subscriber = new AutoCloseable(){
      @Override
      public void close(){
        refreshTokenSubscriptions.remove(this);
      }
    };
    refreshTokenSubscriptions.put(subscriber, consumer);
    return subscriber;
  }

  @Nonnull
  public AutoCloseable subscribeToVehiclesCall(@Nonnull BiConsumer<String,Set<MockVehicle>> consumer){
    var subscriber = new AutoCloseable(){
      @Override
      public void close(){
        vehiclesSubscription.remove(this);
      }
    };
    vehiclesSubscription.put(subscriber, consumer);
    return subscriber;
  }

  @Nonnull
  public AutoCloseable subscribeToVehicleDataRequest(@Nonnull BiConsumer<String,Optional<MockVehicle>> consumer){
    var subscriber = new AutoCloseable(){
      @Override
      public void close(){
        vehicleDataRequestSubscription.remove(this);
      }
    };
    vehicleDataRequestSubscription.put(subscriber, consumer);
    return subscriber;
  }

  @Nonnull
  public Optional<MockSession> refreshToken(@Nonnull String token){
    var result = sessionManager.refreshSession(token);
    refreshTokenSubscriptions.values().forEach(consumer -> consumer.accept(token, result));
    return result;
  }

  public boolean revokeToken(@Nonnull String token){
    return sessionManager.removeSessionWithToken(token);
  }

  @Nonnull
  public Optional<MockAccount> getAccountByEmailAddress(@Nonnull String emailAddress){
    return Optional.ofNullable(accounts.get(emailAddress.toLowerCase()));
  }

  public Optional<MockAccount> getAccountByAccessToken(String token){
    return sessionManager.getAccountByToken(token);
  }

  @Nonnull
  public Set<MockVehicle> getVehicles(@Nonnull MockAccount account){
    var vehicle = new HashSet<>(vehicles.getOrDefault(account, Collections.emptyMap()).values());
    vehiclesSubscription.values().forEach(consumer -> consumer.accept(account.getEmailAddress(), vehicle));
    return vehicle;
  }

  @Nonnull
  public Optional<MockVehicle> getVehicle(MockAccount account, String id){
    var result = Optional.ofNullable(vehicles.getOrDefault(account, Collections.emptyMap()).get(id));
    vehicleDataRequestSubscription.values().forEach(consumer -> consumer.accept(id, result));
    return result;
  }

  @Nonnull
  public Optional<Set<MockSession>> getSessions(@Nonnull MockAccount acct){
    return sessionManager.getSessions(acct);
  }
}
