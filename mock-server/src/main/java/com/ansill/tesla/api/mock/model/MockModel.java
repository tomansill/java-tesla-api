package com.ansill.tesla.api.mock.model;

import com.ansill.validation.Validation;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MockModel{

  @Nonnull
  private final Map<String,MockAccount> accounts = new HashMap<>();

  @Nonnull
  private final Map<MockAccount,Map<String,Object>> vehicles = new HashMap<>();

  @Nonnull
  private final MockSessionManager sessionManager = new MockSessionManager();

  @Nonnull
  private String clientId;

  @Nonnull
  private String clientSecret;

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
  public synchronized MockAccount createAccount(@Nonnull String emailAddress, @Nonnull String password){
    var account = new MockAccount(emailAddress, password);
    if(accounts.containsKey(emailAddress)) throw new IllegalStateException(
      "Duplicate account with same email address exists!");
    accounts.put(emailAddress.toLowerCase(), account);
    return account;
  }

  @Nonnull
  public synchronized Optional<MockAccount> getAccountByEmailAddress(@Nonnull String emailAddress){
    return Optional.ofNullable(accounts.get(emailAddress.toLowerCase()));
  }

  public synchronized boolean removeAccount(@Nonnull MockAccount account){
    if(accounts.remove(account.getEmailAddress().toLowerCase()) == null) return false;
    sessionManager.removeAllSession(account);
    return true;
  }
}
