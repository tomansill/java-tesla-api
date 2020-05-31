package com.ansill.tesla.api.mock.model;

import com.ansill.validation.Validation;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class MockSession{

  @Nonnull
  private final String accessToken;

  @Nonnull
  private final String refreshToken;

  @Nonnull
  private final Consumer<MockSession> expirationConsumer;

  @Nonnull
  private final AtomicReference<Timer> timer;

  @Nonnull
  private Instant creationTime;

  @Nonnull
  private Duration expiresIn;

  public MockSession(
    @Nonnull String accessToken,
    @Nonnull String refreshToken,
    @Nonnull Duration expiresIn,
    @Nonnull Consumer<MockSession> expirationConsumer
  ){
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.creationTime = Instant.now();
    this.expiresIn = Validation.assertNonnull(expiresIn, "expiresIn");
    this.expirationConsumer = Validation.assertNonnull(expirationConsumer, "expirationRunnable");
    this.timer = new AtomicReference<>(buildTimer());
  }

  @Nonnull
  private Timer buildTimer(){

    // Set up timer task
    TimerTask tt = new TimerTask(){
      @Override
      public void run(){
        expirationConsumer.accept(MockSession.this);
      }
    };

    // Set up timer
    Timer timer = new Timer();

    // Schedule it
    timer.schedule(tt, new Date(creationTime.plus(expiresIn).toEpochMilli()));

    // Return it
    return timer;
  }

  private void resetTimer(){
    Timer timer = this.timer.getAndSet(buildTimer());
    if(timer != null) timer.cancel();
  }

  @Nonnull
  public Instant getCreationTime(){
    return creationTime;
  }

  public void setCreationTime(@Nonnull Instant creationTime){
    this.creationTime = Validation.assertNonnull(creationTime, "creationTime");
    resetTimer();
  }

  @Nonnull
  public Duration getExpiresIn(){
    return expiresIn;
  }

  public void setExpiresIn(@Nonnull Duration expiresIn){
    this.expiresIn = Validation.assertNonnull(expiresIn, "expiresIn");
    resetTimer();
  }

  @Nonnull
  public String getAccessToken(){
    return accessToken;
  }

  @Override
  public int hashCode(){
    return accessToken.hashCode();
  }

  @Override
  public boolean equals(Object o){
    if(this == o) return true;
    if(o == null || getClass() != o.getClass()) return false;

    MockSession that = (MockSession) o;

    if(!accessToken.equals(that.accessToken)) return false;
    if(!creationTime.equals(that.creationTime)) return false;
    return expiresIn.equals(that.expiresIn);
  }

  @Nonnull
  public String getRefreshToken(){
    return refreshToken;
  }
}
