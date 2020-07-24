package com.ansill.tesla.api.mock;

import com.ansill.tesla.api.mock.endpoint.mock.MockController;
import com.ansill.tesla.api.mock.endpoint.tesla.OAuthEndpoint;
import com.ansill.tesla.api.mock.endpoint.tesla.v1.APIEndpoint;
import com.ansill.tesla.api.mock.model.MockModel;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.NotFoundResponse;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.plugin.openapi.OpenApiOptions;
import io.javalin.plugin.openapi.OpenApiPlugin;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.ui.ReDocOptions;
import io.javalin.plugin.openapi.ui.SwaggerOptions;
import io.swagger.v3.oas.models.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class MockServer implements AutoCloseable{

  private static final Logger LOGGER = LoggerFactory.getLogger(MockServer.class);

  public MockServer(int port, @Nonnull AtomicReference<MockModel> model){
    this.model = model;
    this.port = port;
    server = Javalin.create(config -> {
      config.registerPlugin(new OpenApiPlugin(getOpenApiOptions()));
      config.defaultContentType = "application/json";
    });
    run();
  }

  @Nonnull
  private final AtomicReference<MockModel> model;

  @Nonnegative
  private final int port;

  @Nonnull
  private final Javalin server;

  private boolean closed = false;

  public MockServer(@Nonnull AtomicReference<MockModel> model){
    this(MockUtility.getNextOpenPort(1001), model);
  }

  public static void main(String... args) throws InterruptedException{

    // Set up model
    var model = new AtomicReference<>(new MockModel("wefw", "sefew"));

    // Run it
    var cdl = new CountDownLatch(1);
    try(var server = new MockServer(model)){
      System.out.println("Port: " + server.getPort());
      LOGGER.info("Mock Server started");
      cdl.await();
    }
  }

  private static OpenApiOptions getOpenApiOptions(){
    /*
    Info info = new Info().version("0.5.0").description("Tesla API");
    OpenApiOptions options = new OpenApiOptions(info)
      //.activateAnnotationScanningFor("com.ansill.tesla.api.mock")
      .path("/swagger-docs") // endpoint for OpenAPI json
      .swagger(new SwaggerOptions("/swagger-ui")) // endpoint for swagger-ui
      .reDoc(new ReDocOptions("/redoc")) // endpoint for redoc
      .defaultDocumentation(doc -> {
        doc.json("500", ErrorResponse.class);
      });
    return new OpenApiPlugin(options);
     */
    var openapi = new Info().version("0.5.0").description("Tesla API");
    return new OpenApiOptions(openapi).path("/swagger-docs")
                                      .defaultDocumentation(doc -> {
                                        doc.json("500", ErrorResponse.class);
                                      })
                                      .swagger(new SwaggerOptions("/swagger-ui")).reDoc(new ReDocOptions("/redoc"));
  }

  @OpenApi(
    path = "/",            // only necessary to include when using static method references
    method = HttpMethod.GET,    // only necessary to include when using static method references
    ignore = true // Not important endpoint
  )
  public void root(Context context){
    context.status(200);
    context.contentType("text/html");
    context.result("<h1>You have reached Mock Tesla Service!</h1>");
  }

  private void run(){

    // Set up exception
    server.exception(Exception.class, (exception, ctx) -> {
      exception.printStackTrace(); // TODO
      var error = new ErrorResponse();
      error.name = exception.getClass().getSimpleName();
      error.method = ctx.method();
      error.path = ctx.path();
      error.message = exception.getMessage();
      error.stackTrace = Arrays.stream(exception.getStackTrace())
                               .map(StackTraceElement::toString)
                               .collect(Collectors.joining("\n"));
      ctx.json(error);
      ctx.status(500);
    });

    // Set up exceptions
    server.exception(UnauthorizedResponse.class, (exception, ctx) -> {
      if(ctx.path().contains("api") || ctx.path().contains("oauth")){
        ctx.contentType("application/json");
        var map = new HashMap<>();
        map.put("code", 401);
        map.put("message", "unauthorized");
        ctx.json(map);
        System.out.println("Passed thru");
      }
    });
    server.exception(ForbiddenResponse.class, (exception, ctx) -> {
      if(ctx.path().contains("api") || ctx.path().contains("oauth")){
        ctx.contentType("application/json");
        var map = new HashMap<>();
        map.put("code", 403);
        map.put("message", "forbidden");
        ctx.json(map);
      }
    });
    server.exception(NotFoundResponse.class, (exception, ctx) -> {
      if(ctx.path().contains("api") || ctx.path().contains("oauth")){
        ctx.contentType("application/json");
        var map = new HashMap<>();
        map.put("code", 404);
        map.put("message", "not found");
        ctx.json(map);
      }
    });

    // Set up routes
    server.routes(() -> {

      // Default route
      get(this::root);

      // OAuth endpoint
      path("oauth", new OAuthEndpoint(model));

      // API v1 endpoint
      path("api/1", new APIEndpoint(model));

      // Model endpoint
      path("model", new MockController(model));
    });

    //server.after(ctx -> System.out.println("Value: " + ctx.resultString()));

    // Start it
    server.start(port);
  }

  @Nonnegative
  public int getPort(){
    return port;
  }

  @Override
  public synchronized void close(){
    if(closed) return;
    server.stop();
    closed = true;
  }

  public static class ErrorResponse{
    public String name;

    public String message;

    public String stackTrace;

    public String path;

    public String method;
  }
}
