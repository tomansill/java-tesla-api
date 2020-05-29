package com.ansill.tesla.api.utility;

import com.ansill.validation.Validation;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

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

  @Nonnull
  public static String simpleToString(@Nonnull Object object){

    // Ensure no null
    Validation.assertNonnull(object, "object");

    // Set up mapper
    Function<Field,String> mapper = field -> {
      try{
        return field.getName() + "=" + sensibleToString(
          field.get(object));
      }catch(IllegalAccessException e){

        // Check if it's access issue
        if(!e.getMessage()
             .contains("modifiers \"private"))
          return "error";

        // Temporarily change access
        field.setAccessible(true);
        try{

          // Access it
          return field.getName() +
                 "=" +
                 sensibleToString(field.get(object));

        }catch(IllegalAccessException ex){
          //ex.printStackTrace();
          return "inaccessible";
        }finally{
          field.setAccessible(false);
        }
      }
    };

    // Collect fields
    var fields = Arrays.stream(object.getClass().getDeclaredFields())
                       .map(mapper)
                       .collect(Collectors.joining(", "));

    // Print class name and its fields
    return f("{}({})", object.getClass().getSimpleName(), fields);
  }

  @Nonnull
  public static String sensibleToString(@Nullable Object object){
    if(object == null) return "null";
    if(object instanceof String) return "\"" + object.toString() + "\"";
    else return object.toString();
  }

}

