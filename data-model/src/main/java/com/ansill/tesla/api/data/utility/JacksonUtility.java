package com.ansill.tesla.api.data.utility;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.ansill.utility.Utility.f;

public final class JacksonUtility{

  private JacksonUtility(){
    throw new AssertionError("No instantiation");
  }

  @Nonnull
  public static Map<String,Optional<Object>> createUnknownFieldsMap(
    @Nonnull ObjectNode node,
    @Nonnull ObjectCodec codec,
    @Nonnull Set<String> usedKeyNames
  ){
    // Error if there's unused keys
    var keys = new HashSet<String>();
    var iterator = node.fieldNames();
    while(iterator.hasNext()) keys.add(iterator.next());

    // Perform a usedKeysSet difference
    keys.removeAll(usedKeyNames);

    // Create unused keys map
    var unknownFields = new HashMap<String,Optional<Object>>();

    // Process each field
    for(var key : keys){

      // Get node
      var itemNode = node.get(key);

      if(itemNode.isNull()) unknownFields.put(key, Optional.empty());
      else if(itemNode.isTextual()) unknownFields.put(key, Optional.of(itemNode.asText()));
      else if(itemNode.isDouble()) unknownFields.put(key, Optional.of(itemNode.asDouble()));
      else if(itemNode.isInt()) unknownFields.put(key, Optional.of(itemNode.asInt()));
      else if(itemNode.isLong()) unknownFields.put(key, Optional.of(itemNode.asLong()));
      else if(itemNode.isBoolean()) unknownFields.put(key, Optional.of(itemNode.asBoolean()));
      else if(itemNode.isArray()) unknownFields.put(key, Optional.of(itemNode.asText()));
      else if(itemNode.isContainerNode()) unknownFields.put(key, Optional.of(itemNode.asText()));

    }

    // Return map
    return unknownFields;
  }

  @Nonnull
  public static String getString(@Nonnull ObjectNode node, @Nonnull String keyName, @Nonnull Set<String> usedKeyNames){
    var itemNode = node.required(keyName);
    if(!itemNode.isTextual()) throw new RuntimeException(f("Key name '{}' is not a String!", keyName));
    var item = itemNode.asText();
    usedKeyNames.add(keyName);
    return item;
  }


  public static boolean getBoolean(
    @Nonnull ObjectNode node,
    @Nonnull String keyName,
    @Nonnull Set<String> usedKeyNames
  ){
    var itemNode = node.required(keyName);
    if(!itemNode.isBoolean()) throw new RuntimeException(f("Key name '{}' is not a Boolean!", keyName));
    var item = itemNode.asBoolean();
    usedKeyNames.add(keyName);
    return item;
  }

  @Nullable
  public static Boolean getBooleanNullable(
    @Nonnull ObjectNode node,
    @Nonnull String keyName,
    @Nonnull Set<String> usedKeyNames
  ){
    var itemNode = node.get(keyName);
    if(itemNode == null) return null;
    if(!itemNode.isNull() && !itemNode.isBoolean()) throw new RuntimeException(f(
      "Key name '{}' is not a Boolean!",
      keyName
    ));
    var item = itemNode.isNull() ? null : itemNode.asBoolean();
    usedKeyNames.add(keyName);
    return item;
  }

  @Nonnull
  public static <T> T getObject(
    @Nonnull ObjectCodec codec,
    @Nonnull String keyName,
    @Nonnull ObjectNode node,
    @Nonnull Set<String> usedKeyNames,
    @Nonnull Class<T> clazz
  ) throws IOException{
    var itemParser = node.required(keyName).traverse();
    itemParser.setCodec(codec);
    T item = itemParser.readValueAs(clazz);
    usedKeyNames.add(keyName);
    return item;
  }

  @Nonnull
  public static <T> T getObject(
    @Nonnull ObjectCodec codec,
    @Nonnull String keyName,
    @Nonnull ObjectNode node,
    @Nonnull Set<String> usedKeyNames,
    @Nonnull TypeReference<T> reference
  ) throws IOException{
    var itemParser = node.required(keyName).traverse();
    itemParser.setCodec(codec);
    T item = itemParser.readValueAs(reference);
    usedKeyNames.add(keyName);
    return item;
  }

  @Nullable
  public static String getStringNullable(
    @Nonnull ObjectNode node,
    @Nonnull String keyName,
    @Nonnull Set<String> usedKeyNames
  ){
    var itemNode = node.get(keyName);
    if(itemNode == null) return null;
    if(!itemNode.isNull() && !itemNode.isTextual()) throw new RuntimeException(f(
      "Key name '{}' is not a String!",
      keyName
    ));
    var item = itemNode.isNull() ? null : itemNode.asText();
    usedKeyNames.add(keyName);
    return item;
  }

  public static long getLong(@Nonnull ObjectNode node, @Nonnull String keyName, @Nonnull Set<String> usedKeyNames){
    var itemNode = node.required(keyName);
    if(!itemNode.isLong() && !itemNode.isIntegralNumber()) throw new RuntimeException(f(
      "Key name '{}' is not a Long!",
      keyName
    ));
    var item = itemNode.asLong();
    usedKeyNames.add(keyName);
    return item;
  }

  @Nullable
  public static Long getLongNullable(
    @Nonnull ObjectNode node,
    @Nonnull String keyName,
    @Nonnull Set<String> usedKeyNames
  ){
    var itemNode = node.get(keyName);
    if(itemNode == null) return null;
    if(!itemNode.isNull() && !itemNode.isLong() && !itemNode.isIntegralNumber()) throw new RuntimeException(f(
      "Key name '{}' is not a Long!",
      keyName
    ));
    var item = itemNode.isNull() ? null : itemNode.asLong();
    usedKeyNames.add(keyName);
    return item;
  }

  @Nullable
  public static Integer getIntegerNullable(
    @Nonnull ObjectNode node,
    @Nonnull String keyName,
    @Nonnull Set<String> usedKeyNames
  ){
    var itemNode = node.get(keyName);
    if(itemNode == null) return null;
    if(!itemNode.isNull() && !itemNode.isLong() && !itemNode.isIntegralNumber()) throw new RuntimeException(f(
      "Key name '{}' is not a Integer!",
      keyName
    ));
    var item = itemNode.isNull() ? null : itemNode.asInt();
    usedKeyNames.add(keyName);
    return item;
  }

  public static int getInteger(@Nonnull ObjectNode node, @Nonnull String keyName, @Nonnull Set<String> usedKeyNames){
    var itemNode = node.required(keyName);
    if(!itemNode.isInt()) throw new RuntimeException(f("Key name '{}' is not an Integer!", keyName));
    var item = itemNode.asInt();
    usedKeyNames.add(keyName);
    return item;
  }

  public static double getDouble(@Nonnull ObjectNode node, @Nonnull String keyName, @Nonnull Set<String> usedKeyNames){
    var itemNode = node.required(keyName);
    if(!itemNode.isDouble()) throw new RuntimeException(f("Key name '{}' is not a Double!", keyName));
    var item = itemNode.asDouble();
    usedKeyNames.add(keyName);
    return item;
  }

}
