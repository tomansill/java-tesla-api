package com.ansill.tesla.api.mock.model;

import com.ansill.validation.Validation;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class MockSessionManager{

  private final Map<MockAccount,Set<MockSession>> accountsToSessions = new HashMap<>();

  private final Map<MockSession,MockAccount> sessionToAccounts = new HashMap<>();

  @Nonnull
  private Duration defaultSessionDuration = Duration.ofMinutes(120);

  @Nonnull
  public Duration getDefaultSessionDuration(){
    return defaultSessionDuration;
  }

  public void setDefaultSessionDuration(@Nonnull Duration defaultSessionDuration){
    this.defaultSessionDuration = Validation.assertNonnull(defaultSessionDuration, "defaultSessionDuration");
  }

  @Nonnull
  public synchronized MockSession createSession(@Nonnull MockAccount account){
    MockSession session = new MockSession(defaultSessionDuration, this::removeSession);
    accountsToSessions.compute(account, (acct, sessions) -> {
      if(sessions == null) sessions = new HashSet<>();
      sessions.add(session);
      return sessions;
    });
    sessionToAccounts.put(session, account);
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
    return true;
  }

  public synchronized boolean removeAllSession(@Nonnull MockAccount account){
    var sessions = accountsToSessions.get(account);
    if(sessions == null) return false;
    sessions.clear();
    return true;
  }
}
