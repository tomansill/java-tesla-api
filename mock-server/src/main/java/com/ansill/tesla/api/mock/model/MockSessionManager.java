package com.ansill.tesla.api.mock.model;

import com.ansill.validation.Validation;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static com.ansill.tesla.api.mock.MockUtility.generateString;

public class MockSessionManager{

  private final Map<MockAccount,Set<MockSession>> accountsToSessions = new ConcurrentHashMap<>();

  private final Map<MockSession,MockAccount> sessionToAccounts = new ConcurrentHashMap<>();

  private final Map<String,MockSession> accessTokensToSessions = new ConcurrentHashMap<>();

  private final Map<String,MockSession> refreshTokensToSessions = new ConcurrentHashMap<>();

  @Nonnull
  private Duration defaultSessionDuration = Duration.ofDays(45);

  @Nonnull
  public Duration getDefaultSessionDuration(){
    return defaultSessionDuration;
  }

  public void setDefaultSessionDuration(@Nonnull Duration defaultSessionDuration){
    this.defaultSessionDuration = Validation.assertNonnull(defaultSessionDuration, "defaultSessionDuration");
  }

  void pingAccount(@Nonnull MockAccount account){
    accountsToSessions.putIfAbsent(account, new HashSet<>());
  }

  @Nonnull
  private MockSession getSession(){

    // Set up reference
    var session = new AtomicReference<MockSession>();

    // Repeat until no collision for both access and refresh tokens
    var found1 = new AtomicBoolean(false);
    do{
      accessTokensToSessions.computeIfAbsent(generateString(64).toLowerCase(), accessToken -> {
        found1.set(true);
        var found2 = new AtomicBoolean(false);
        do{
          refreshTokensToSessions.computeIfAbsent(generateString(64).toLowerCase(), refreshToken -> {
            found2.set(true);
            session.set(new MockSession(accessToken, refreshToken, getDefaultSessionDuration(), this::removeSession));
            return session.get();
          });
        }while(!found2.get());
        return session.get();
      });
    }while(!found1.get());

    // Return it
    return session.get();
  }

  @Nonnull
  public MockSession createSession(@Nonnull MockAccount account){

    // Get session
    var session = getSession();

    // Put session in appropriate maps
    accountsToSessions.compute(account, (acct, sessions) -> {
      if(sessions == null) sessions = new HashSet<>();
      sessionToAccounts.put(session, account);
      sessions.add(session);
      return sessions;
    });

    // Return it
    return session;
  }

  @Nonnull
  public Optional<Set<MockSession>> getSessions(@Nonnull MockAccount account){
    return Optional.ofNullable(accountsToSessions.get(account));
  }

  @Nonnull
  public Optional<MockAccount> getAccount(@Nonnull MockSession session){
    return Optional.ofNullable(sessionToAccounts.get(session));
  }

  public synchronized boolean removeSession(@Nonnull MockSession session){
    var account = sessionToAccounts.remove(session);
    if(account == null) return false;
    accountsToSessions.get(account).remove(session);
    refreshTokensToSessions.remove(session.getRefreshToken());
    accessTokensToSessions.remove(session.getAccessToken());
    return true;
  }

  public synchronized boolean removeAllSession(@Nonnull MockAccount account){
    var sessions = accountsToSessions.get(account);
    if(sessions == null) return false;
    sessions.forEach(this::removeSession);
    return true;
  }

  @Nonnull
  public Optional<MockSession> refreshSession(@Nonnull String token){

    // Get session from token
    var session = refreshTokensToSessions.remove(token);
    if(session == null) return Optional.empty();

    // Get account
    var account = sessionToAccounts.get(session);

    // Remove old session
    removeSession(session);

    // Get new session
    var newSession = getSession();

    // Put session in appropriate maps
    accountsToSessions.compute(account, (acct, sessions) -> {
      if(sessions == null) sessions = new HashSet<>();
      sessionToAccounts.put(newSession, account);
      sessions.add(newSession);
      return sessions;
    });

    // Return it
    return Optional.of(newSession);
  }

  public boolean removeSessionWithToken(@Nonnull String token){
    var session = refreshTokensToSessions.get(token);
    if(session == null) return false;
    removeSession(session);
    return true;
  }

  @Nonnull
  public Optional<MockAccount> getAccountByToken(String token){
    var session = accessTokensToSessions.get(token);
    if(session == null) return Optional.empty();
    return Optional.ofNullable(sessionToAccounts.get(session));
  }
}
