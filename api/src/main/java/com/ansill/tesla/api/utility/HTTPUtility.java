package com.ansill.tesla.api.utility;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static com.ansill.utility.Utility.f;

/**
 * Utility Class
 */
public final class HTTPUtility{

  // Purely for debugging - remove later
  @Deprecated
  public static final AtomicReference<BiConsumer<Request,ReusableResponse>> HTTP_LOGGING = new AtomicReference<>();

  private HTTPUtility(){
    throw new AssertionError(f("No {} instances for you!", this.getClass().getName()));
  }

  @Nonnull
  public static Optional<String> getStringFromResponseBody(@Nonnull Response response) throws IOException{
    var body = response.body();
    if(body == null) return Optional.empty();
    return Optional.of(body.string());
  }

  @Nonnull
  public static ReusableResponse httpCall(
    @Nonnull Request request,
    @Nonnull Function<OkHttpClient.Builder,OkHttpClient.Builder> config
  ) throws IOException{
    var client = config.apply(new OkHttpClient.Builder()).build();
    ReusableResponse reusableResponse = null;
    try{
      reusableResponse = new ReusableResponse(client.newCall(request).execute());
    }finally{
      var consumer = HTTP_LOGGING.get();
      if(consumer != null) consumer.accept(request, reusableResponse);
    }
    return reusableResponse;
  }

}

