package com.ansill.tesla.api.mock.endpoint.mock;

import com.ansill.tesla.api.mock.model.MockAccount;
import com.ansill.tesla.api.mock.model.MockModel;
import com.ansill.tesla.api.mock.model.MockSession;
import com.ansill.tesla.api.mock.model.MockVehicle;
import com.ansill.utility.Utility;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;
import io.javalin.websocket.WsHandler;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;

import static com.ansill.utility.Utility.f;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.ws;

public class MockController implements EndpointGroup{

  private static final String TITLE = "Mock Tesla API";

  @Nonnull
  private final AtomicReference<MockModel> model;

  public MockController(@Nonnull AtomicReference<MockModel> model){
    this.model = model;
  }

  private void webSocket(WsHandler wsHandler){

  }

  @Override
  public void addEndpoints(){
    ws(this::webSocket);
    get("user&create", this::createUser);
    post("user&create", this::createUser);
    get("user", this::getUsers);
    post("user", this::createUser);
    get("user/:username", this::getUser);
    get("user/:username/session&create", this::createSession);
    get("user/:username/session/:session", this::deleteSessionConfirm);
    post("user/:username/session/:session", this::deleteSession);
    get("user/:username/vehicle&create", this::createVehicle);
    get("user/:username/vehicle/:vehicle", this::getVehicle);
    post("user/:username/vehicle/:vehicle/namechange", this::changeVehicleName);
    post("user/:username/vehicle/:vehicle/drive", this::driveVehicle);
    post("user/:username/vehicle/:vehicle", this::deleteVehicle);
  }

  private void driveVehicle(Context context){

    // Get user
    var user = getUsername(context);
    if(user == null) return;

    // Get vehicle
    var vehicle = getVehicle(user, context);
    if(vehicle == null) return;

    // Get new state
    var driveMode = model.get().toggleDriveMode(vehicle);

    // Switch on html or json
    if(!"json".equalsIgnoreCase(context.header("type"))){

      // Set up JSoup
      var document = Document.createShell("");

      // Add title
      document.head().appendElement("title").text(f(TITLE + ": Vehicle '{}'", vehicle.getIdString()));

      // Add header
      var message = driveMode ? "Driving mode is activated for Vehicle {}" : "Driving mode is deactivated for Vehicle {}";
      document.body().appendElement("h1").text(f(message, vehicle.getIdString()));

      // Go back
      document.body().appendElement("a").attr("href", f("../{}", vehicle.getIdString())).text("Go Back");

      // Return it
      context.contentType("text/html");
      context.result(document.toString());

    }else throw new UnsupportedOperationException();
  }

  private void changeVehicleName(Context context){

    // Get user
    var user = getUsername(context);
    if(user == null) return;

    // Get vehicle
    var vehicle = getVehicle(user, context);
    if(vehicle == null) return;

    // Get new name
    var name = context.formParam("new_name");
    if(name == null || name.isBlank()) return;

    // Set display name
    vehicle.setDisplayName(name);

    // Switch on html or json
    if(!"json".equalsIgnoreCase(context.header("type"))){

      // Set up JSoup
      var document = Document.createShell("");

      // Add title
      document.head().appendElement("title").text(f(TITLE + ": Vehicle '{}'", vehicle.getIdString()));

      // Add header
      document.body().appendElement("h1").text(f("Vehicle {} has been changed", vehicle.getIdString()));

      // Go back
      document.body().appendElement("a").attr("href", f("../{}", vehicle.getIdString())).text("Go Back");

      // Return it
      context.contentType("text/html");
      context.result(document.toString());

    }else throw new UnsupportedOperationException();
  }

  private void createVehicle(Context context){

    // Redirect if slash
    if(context.path().endsWith("/")) context.redirect(context.path().substring(0, context.path().length() - 1));

    // Get user
    var user = getUsername(context);
    if(user == null) return;

    // Create vehicle
    var vehicle = model.get().createVehicle(user);

    // Switch on html or json
    if(!"json".equalsIgnoreCase(context.header("type"))){

      // Set up JSoup
      var document = Document.createShell("");

      // Add title
      document.head().appendElement("title").text(f(TITLE + ": User '{}'", user.getEmailAddress()));

      // Add header
      document.body().appendElement("h1").text(f("Vehicle for User '{}' Created", user.getEmailAddress()));

      // Session info
      document.body().appendElement("div").appendElement("span").text("Id: " + vehicle.getIdString());
      document.body().appendElement("div").appendElement("span").text("VIN: " + vehicle.getVIN());
      document.body().appendElement("div").appendElement("span").text("Name: " + vehicle.getDisplayName());

      // Link to go back
      document.body().appendElement("hr");
      document.body().appendElement("a").attr("href", ".").text("Go back");

      // Return it
      context.contentType("text/html");
      context.result(document.toString());

    }else throw new UnsupportedOperationException();
  }

  @Nullable
  private MockAccount getUsername(Context context){

    // Get user
    var username = context.pathParam("username");

    // Get user if it exists
    var user = model.get().getAccountByEmailAddress(username);

    // Error if user does not exist
    if(user.isEmpty()){

      // Switch on html or json
      if(!"json".equalsIgnoreCase(context.header("type"))){

        // Set up JSoup
        var document = Document.createShell("");

        // Add title
        document.head().appendElement("title").text(f(TITLE + ": User '{}'", username));

        // Add header
        document.body().appendElement("h1").text(f("User '{}' not found", username));

        // Link to go back
        document.body().appendElement("hr");
        document.body().appendElement("a").attr("href", ".").text("Go back");

        // Return it
        context.contentType("text/html");
        context.status(404);
        context.result(document.toString());
        return null;

      }else{
        throw new UnsupportedOperationException();
      }
    }

    // Return user
    return user.orElseThrow();
  }

  private void createSession(Context context){

    // Redirect if slash
    if(context.path().endsWith("/")) context.redirect(context.path().substring(0, context.path().length() - 1));

    // Get user
    var user = getUsername(context);
    if(user == null) return;

    // Create session
    var session = model.get().authenticate(user.getEmailAddress(), user.getPassword()).orElseThrow();

    // Switch on html or json
    if(!"json".equalsIgnoreCase(context.header("type"))){

      // Set up JSoup
      var document = Document.createShell("");

      // Add title
      document.head().appendElement("title").text(f(TITLE + ": User '{}'", user.getEmailAddress()));

      // Add header
      document.body().appendElement("h1").text(f("Session for User '{}' Created", user.getEmailAddress()));

      // Session info
      document.body().appendElement("div").appendElement("span").text("Access Token: " + session.getAccessToken());
      document.body().appendElement("div").appendElement("span").text("Refresh Token: " + session.getRefreshToken());
      document.body().appendElement("div").appendElement("span").text("Creation Time: " +
                                                                      DateTimeFormatter.ISO_INSTANT.format(session.getCreationTime()));
      document.body().appendElement("div").appendElement("span").text("Time left: " +
                                                                      Duration.between(
                                                                        Instant.now(),
                                                                        session.getCreationTime()
                                                                               .plus(session.getExpiresIn())
                                                                      ).toSeconds() +
                                                                      " seconds");
      document.body().appendElement("div").appendElement("span").text("Expiration Time: " +
                                                                      DateTimeFormatter.ISO_INSTANT.format(session.getCreationTime()
                                                                                                                  .plus(
                                                                                                                    session
                                                                                                                      .getExpiresIn())));

      // Link to go back
      document.body().appendElement("hr");
      document.body().appendElement("a").attr("href", ".").text("Go back");

      // Return it
      context.contentType("text/html");
      context.result(document.toString());

    }else throw new UnsupportedOperationException();

  }

  @Nullable
  private MockSession getSession(MockAccount user, Context context){

    // Get session
    var session = context.pathParam("session");
    var mockSession = model.get()
                           .getSessions(user)
                           .orElseThrow()
                           .stream()
                           .filter(item -> item.getAccessToken()
                                               .equals(session))
                           .findAny();

    // Error if user does not exist
    if(mockSession.isEmpty()){

      // Switch on html or json
      if(!"json".equalsIgnoreCase(context.header("type"))){

        // Set up JSoup
        var document = Document.createShell("");

        // Add title
        document.head().appendElement("title").text(f(TITLE + ": Session '{}'", session));

        // Add header
        document.body().appendElement("h1").text(f("Session '{}' not found", session));

        // Add return
        document.body().appendElement("a").attr("href", ".").text("Go Back");

        // Return it
        context.contentType("text/html");
        context.status(404);
        context.result(document.toString());
        return null;

      }else{
        throw new UnsupportedOperationException();
      }
    }

    // Return it
    return mockSession.orElseThrow();
  }

  @Nullable
  private MockVehicle getVehicle(MockAccount user, Context context){

    // Get session
    var vehicleId = context.pathParam("vehicle");
    var mockVehicle = model.get()
                           .getVehicles(user)
                           //.orElseThrow()
                           .stream()
                           .filter(item -> item.getIdString().equals(vehicleId))
                           .findAny();

    // Error if user does not exist
    if(mockVehicle.isEmpty()){

      // Switch on html or json
      if(!"json".equalsIgnoreCase(context.header("type"))){

        // Set up JSoup
        var document = Document.createShell("");

        // Add title
        document.head().appendElement("title").text(f(TITLE + ": Vehicle Id '{}'", vehicleId));

        // Add header
        document.body().appendElement("h1").text(f("Vehicle Id '{}' not found", vehicleId));

        // Add return
        document.body().appendElement("a").attr("href", ".").text("Go Back");

        // Return it
        context.contentType("text/html");
        context.status(404);
        context.result(document.toString());
        return null;

      }else{
        throw new UnsupportedOperationException();
      }
    }

    // Return it
    return mockVehicle.orElseThrow();
  }

  private void deleteSessionConfirm(Context context){

    // Get user
    var user = getUsername(context);
    if(user == null) return;

    // Get session
    var session = getSession(user, context);
    if(session == null) return;

    // Switch on html or json
    if(!"json".equalsIgnoreCase(context.header("type"))){

      // Set up JSoup
      var document = Document.createShell("");

      // Add title
      document.head().appendElement("title").text(f(TITLE + ": User '{}'", user.getEmailAddress()));

      // Add header
      document.body().appendElement("h1").text(f("Session for User '{}'", user.getEmailAddress()));

      // Confirm dialog
      document.body().appendElement("h4").text("Really delete session?");
      var form = document.body().appendElement("form").attr("method", "POST");
      form.appendElement("a").attr("href", "..").text("Cancel");
      form.appendElement("input").attr("type", "submit").attr("value", "Delete");

      // Return it
      context.contentType("text/html");
      context.result(document.toString());

    }else throw new UnsupportedOperationException();
  }

  private void getVehicle(Context context){

    // Get user
    var user = getUsername(context);
    if(user == null) return;

    // Get vehicle
    var vehicle = getVehicle(user, context);
    if(vehicle == null) return;

    // Switch on html or json
    if(!"json".equalsIgnoreCase(context.header("type"))){

      // Set up JSoup
      var document = Document.createShell("");

      // Add title
      document.head().appendElement("title").text(f(TITLE + ": User '{}'", user.getEmailAddress()));

      // Add header
      document.body().appendElement("h1").text(f(
        "Vehicle id '{}' for User '{}'",
        vehicle.getIdString(),
        user.getEmailAddress()
      ));

      // Name Change dialog
      document.body().appendElement("h4").text(f("Change vehicle name: Current: {}", vehicle.getDisplayName()));
      var form = document.body().appendElement("form").attr("method", "POST").attr(
        "action",
        f("/model/user/{}/vehicle/{}/namechange", user.getEmailAddress(), vehicle.getIdString())
      );
      form.appendElement("input")
        .attr("type", "text")
        .attr("name", "new_name")
        .attr("value", vehicle.getDisplayName())
        .attr("placeholder", "New Name");
      form.appendElement("input").attr("type", "submit").attr("value", "Change Name");
      document.body().appendElement("p").text(Utility.simpleToString(vehicle.getDriveState()));


      // Autodrive dialog
      document.body().appendElement("h4").text(f("Autodrive state: Current: {}", model.get().getAutoDriveState(vehicle)));
      form = document.body().appendElement("form").attr("method", "POST").attr(
        "action",
        f("/model/user/{}/vehicle/{}/drive", user.getEmailAddress(), vehicle.getIdString())
      );
      form.appendElement("input").attr("type", "submit").attr("value", "Toggle");

      // Delete dialog
      document.body().appendElement("h4").text("Delete vehicle?");
      form = document.body().appendElement("form").attr("method", "POST");
      form.appendElement("input").attr("type", "submit").attr("value", "Delete");

      // Return it
      context.contentType("text/html");
      context.result(document.toString());

    }else throw new UnsupportedOperationException();
  }

  private void deleteSession(Context context){

    // Get user
    var user = getUsername(context);
    if(user == null) return;

    // Get session
    var session = getSession(user, context);
    if(session == null) return;

    // Delete it
    model.get().revokeToken(session.getRefreshToken());

    // Switch on html or json
    if(!"json".equalsIgnoreCase(context.header("type"))){

      // Set up JSoup
      var document = Document.createShell("");

      // Add title
      document.head().appendElement("title").text(f(TITLE + ": User '{}'", user.getEmailAddress()));

      // Add header
      document.body().appendElement("h1").text(f("Session for User '{}' has been deleted", user.getEmailAddress()));

      // Go back
      document.body().appendElement("a").attr("href", "..").text("Go Back");

      // Return it
      context.contentType("text/html");
      context.result(document.toString());

    }else throw new UnsupportedOperationException();
  }

  private void deleteVehicle(Context context){

    // Get user
    var user = getUsername(context);
    if(user == null) return;

    // Get vehicle
    var vehicle = getVehicle(user, context);
    if(vehicle == null) return;

    // Delete it
    model.get().removeVehicle(vehicle);

    // Switch on html or json
    if(!"json".equalsIgnoreCase(context.header("type"))){

      // Set up JSoup
      var document = Document.createShell("");

      // Add title
      document.head().appendElement("title").text(f(TITLE + ": User '{}'", user.getEmailAddress()));

      // Add header
      document.body().appendElement("h1").text(f("Vehicle for User '{}' has been deleted", user.getEmailAddress()));

      // Go back
      document.body().appendElement("a").attr("href", "..").text("Go Back");

      // Return it
      context.contentType("text/html");
      context.result(document.toString());

    }else throw new UnsupportedOperationException();
  }

  private void getUser(Context context){

    // Redirect if slash
    if(context.path().endsWith("/")) context.redirect(context.path().substring(0, context.path().length() - 1));

    // Get user
    var user = getUsername(context);
    if(user == null) return;

    // Switch on html or json
    if(!"json".equalsIgnoreCase(context.header("type"))){

      // Set up JSoup
      var document = Document.createShell("");

      // Add title
      document.head().appendElement("title").text(f(TITLE + ": User '{}'", user.getEmailAddress()));

      // Add header
      document.body().appendElement("h1").text(f("User '{}'", user.getEmailAddress()));

      // Set up div for active sessions
      var sessions = model.get().getSessions(user).orElseThrow();
      var div = document.body().appendElement("div");
      div.appendElement("h2").text("Active Sessions");
      div.appendElement("a").attr("href", user.getEmailAddress() + "/session&create").text("Create session");
      var table = div.appendElement("table");
      var tr = table.appendElement("tr");
      tr.appendElement("th").text("Access and Refresh Tokens");
      tr.appendElement("th").text("Creation Time");
      tr.appendElement("th").text("Expires At");
      tr.appendElement("th").text("Expires Time");
      for(var session : sessions){
        tr = table.appendElement("tr");
        var row = tr.appendElement("td");
        row.appendElement("a").text(session.getAccessToken()).attr(
          "href",
          user.getEmailAddress() + "/session/" + session.getAccessToken()
        );
        row.appendElement("br");
        row.appendElement("span").text(session.getRefreshToken());
        tr.appendElement("td").text(DateTimeFormatter.ISO_INSTANT.format(session.getCreationTime()));
        tr.appendElement("td").text("Time left: " +
                                    Duration.between(
                                      Instant.now(),
                                      session.getCreationTime().plus(session.getExpiresIn())
                                    ).toSeconds() +
                                    " seconds");
        tr.appendElement("td").text(DateTimeFormatter.ISO_INSTANT.format(session.getCreationTime()
                                                                                .plus(session.getExpiresIn())));
      }

      // Set up div for vehicles
      var vehicles = model.get().getVehicles(user);
      div = document.body().appendElement("div");
      div.appendElement("h2").text("Active Sessions");
      div.appendElement("a").attr("href", user.getEmailAddress() + "/vehicle&create").text("Create vehicle");
      table = div.appendElement("table");
      tr = table.appendElement("tr");
      tr.appendElement("th").text("Id");
      tr.appendElement("th").text("VIN");
      tr.appendElement("th").text("Name");
      for(var vehicle : vehicles){
        tr = table.appendElement("tr");
        var row = tr.appendElement("td");
        row.appendElement("a").text(vehicle.getIdString()).attr(
          "href",
          user.getEmailAddress() + "/vehicle/" + vehicle.getIdString()
        );
        tr.appendElement("td").text(vehicle.getVIN());
        tr.appendElement("td").text(vehicle.getDisplayName());
      }

      // Return it
      context.contentType("text/html");
      context.result(document.toString());

    }else throw new UnsupportedOperationException();

  }

  private void createUser(Context context){

    // Redirect if slash
    if(context.path().endsWith("/")) context.redirect(context.path().substring(0, context.path().length() - 1));

    // Find out if supplied any params
    var username = context.formParam("username", (String) null);
    var password = context.formParam("password", (String) null);

    // Set up error bin
    var errors = new LinkedList<String>();

    // Check
    if(username != null && !username.isEmpty()){

      if(username.length() < 4) errors.add("Username is too short");
      if(model.get().getAccountByEmailAddress(username).isPresent()) errors.add("Email address taken");

    }
    if(password != null && !password.isEmpty()){

      if(password.length() < 8) errors.add("Password is too short");

    }

    // if no errors, continue
    if(errors.isEmpty() && username != null && password != null){

      // Create user
      model.get().createAccount(username, password);

      // Send user back to users directory
      context.redirect("user");
    }

    // Switch on html or json
    if(!"json".equalsIgnoreCase(context.header("type"))){

      // Set up JSoup
      var document = Document.createShell("");

      // Add title
      document.head().appendElement("title").text(TITLE + ": User Creation");

      // Add header
      document.body().appendElement("h1").text("User Creation");

      // Add form
      var form = document.body().appendElement("form");

      // Add where form goes to
      form.attr("method", "post").attr("action", "user?create");

      // Add fields
      Element div;
      if(!errors.isEmpty()){
        div = form.appendElement("h5").text("Form has errors");
        var list = div.appendElement("ul");
        errors.forEach(item -> list.appendElement("li").text(item));
      }
      div = form.appendElement("div");
      div.appendElement("span").text("Email address: ");
      div.appendElement("input")
         .attr("name", "username")
         .attr("type", "email")
         .attr("minlength", "4")
         .attr("required", true)
         .attr("placeholder", "Email Address");
      div = form.appendElement("div");
      div.appendElement("span").text("Password: ");
      div.appendElement("input")
         .attr("name", "password")
         .attr("type", "password")
         .attr("minlength", "8")
         .attr("required", true)
         .attr("placeholder", "Password");
      form.appendElement("div")
          .appendElement("input")
          .attr("type", "submit")
          .attr("value", "Submit");

      // Return it
      context.contentType("text/html");
      context.result(document.toString());
    }else throw new UnsupportedOperationException();
  }

  private void getUsers(@Nonnull Context context){

    // Redirect if slash
    if(context.path().endsWith("/")) context.redirect(context.path().substring(0, context.path().length() - 1));

    // Get all users
    var accounts = model.get().getAllAccounts();

    // Switch on html or json
    if(!"json".equalsIgnoreCase(context.header("type"))){

      // Set up JSoup
      var document = Document.createShell("");

      // Add title
      document.head().appendElement("title").text(TITLE + ": Users List");

      // Add header
      document.body().appendElement("h1").text("Users List");

      // Add list
      var list = document.body().appendElement("ul");

      // Add items in the list
      for(var account : accounts){

        // Add account listing
        var link = list.appendElement("li").appendElement("a");

        // Set text and href
        link.text(account.getEmailAddress()).attr("href", "user/" + account.getEmailAddress());

      }

      // Inform if empty
      if(accounts.isEmpty()) list.appendElement("li").text("Empty accounts list");

      // Add link to create
      document.body().appendElement("a").attr("href", "user&create").text("Create User");

      // Return it
      context.contentType("text/html");
      context.result(document.toString());

    }else{
      throw new UnsupportedOperationException();
    }
  }
}
