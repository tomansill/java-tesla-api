package com.ansill.tesla.api.mock;

import com.ansill.validation.Validation;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.ServerSocket;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.LongStream;

public final class MockUtility{

  private static final Set<Integer> RESERVED_PORTS = new HashSet<>();

  private static final AtomicReference<Random> RANDOM_GENERATOR = new AtomicReference<>(null);

  private static final List<String> RANDOM_WORDS = Arrays.asList(
    "affect",
    "report",
    "rather",
    "all",
    "dried",
    "zoo",
    "dollar",
    "broken",
    "jack",
    "five",
    "out",
    "concerned",
    "loud",
    "vapor",
    "rule",
    "does",
    "trick",
    "local",
    "floating",
    "hard",
    "political",
    "tales",
    "cold",
    "tip",
    "teach",
    "safe",
    "week",
    "species",
    "nature",
    "rocky",
    "giving",
    "announced",
    "dig",
    "leader",
    "strange",
    "push",
    "single",
    "machinery",
    "earlier",
    "roof",
    "people",
    "recent",
    "vegetable",
    "day",
    "balance",
    "weigh",
    "clock",
    "knowledge",
    "branch",
    "press",
    "square",
    "increase",
    "which",
    "influence",
    "skill",
    "buffalo",
    "successful",
    "equipment",
    "this",
    "plates",
    "thou",
    "factor",
    "ruler",
    "trunk",
    "furniture",
    "percent",
    "chart",
    "eight",
    "fort",
    "freedom",
    "fellow",
    "go",
    "course",
    "substance",
    "whistle",
    "social",
    "extra",
    "pine",
    "upper",
    "design",
    "when",
    "mean",
    "stranger",
    "these",
    "real",
    "beat",
    "lonely",
    "farm",
    "milk",
    "north",
    "badly",
    "shaking",
    "rest",
    "frighten",
    "harder",
    "slight",
    "collect",
    "column",
    "brother",
    "sweet",
    "cost",
    "vote",
    "studying",
    "sure",
    "desk",
    "dish",
    "strange",
    "reader",
    "sure",
    "garage",
    "wash",
    "medicine",
    "low",
    "door",
    "command",
    "none",
    "log",
    "bread",
    "ate",
    "country",
    "somehow",
    "are",
    "headed",
    "tape",
    "task",
    "case",
    "shinning",
    "discover",
    "does",
    "eager",
    "price",
    "load",
    "upward",
    "stream",
    "feature",
    "lot",
    "pleasure",
    "political",
    "although",
    "wild",
    "nation",
    "luck",
    "smooth",
    "experience",
    "everybody",
    "motion",
    "package",
    "lead",
    "everybody",
    "organized",
    "shake",
    "doing",
    "pen",
    "lonely",
    "depth",
    "paper",
    "danger",
    "piece",
    "possibly",
    "track",
    "happy",
    "fog",
    "massage",
    "board",
    "kind",
    "there",
    "shorter",
    "cook",
    "journey",
    "settlers",
    "were",
    "guide",
    "silly",
    "atmosphere",
    "species",
    "handsome",
    "tell",
    "second",
    "closer",
    "prevent",
    "announced",
    "ourselves",
    "or",
    "gulf",
    "honor",
    "ever",
    "especially",
    "away",
    "handle",
    "interest",
    "putting",
    "could",
    "daily",
    "fair",
    "exercise",
    "south",
    "college",
    "serious",
    "thus",
    "ancient",
    "cry",
    "fur",
    "mile",
    "oldest",
    "desk",
    "jump",
    "police",
    "diameter",
    "college",
    "was",
    "mental",
    "weigh",
    "gone",
    "somewhere",
    "citizen",
    "rising",
    "board",
    "land",
    "function",
    "muscle",
    "scientist",
    "solid",
    "leaving",
    "country",
    "completely",
    "stranger",
    "mile",
    "donkey",
    "anyone",
    "announced",
    "strength",
    "goose",
    "telephone",
    "stronger",
    "accurate",
    "ready",
    "sang",
    "reason",
    "chosen",
    "brass",
    "fierce",
    "sense",
    "sweet",
    "shut",
    "result",
    "joy",
    "sure",
    "bend",
    "fort",
    "sail",
    "five",
    "now",
    "throat",
    "exchange",
    "letter",
    "signal",
    "block",
    "date",
    "good",
    "crack",
    "determine",
    "difference",
    "joined",
    "raw",
    "slow",
    "became",
    "dish",
    "atom",
    "doubt",
    "camp",
    "whenever",
    "strange",
    "wise",
    "laugh",
    "unusual",
    "though",
    "glad",
    "species",
    "giant",
    "putting",
    "teach",
    "universe",
    "report",
    "close",
    "flower",
    "shirt",
    "either",
    "silver",
    "complete",
    "anybody",
    "contain",
    "ship",
    "managed",
    "opinion",
    "dot",
    "sweet",
    "shirt",
    "lady",
    "purple",
    "shells",
    "herself"
  );

  // No instantiation allowed
  private MockUtility(){
    throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
  }


  @SuppressWarnings("SameParameterValue")
  @Nonnegative
  public static synchronized int getNextOpenPort(@Nonnegative int start){

    // Loop until maximum possible
    for(int port = start; port < Short.MAX_VALUE; port++){

      // Make sure not already reserved
      if(RESERVED_PORTS.contains(port)) continue;

      // Check if it's open
      if(!isPortOpen(port)) continue;

      // If it is indeed open, reserve it
      RESERVED_PORTS.add(port);

      // Return it
      return port;
    }

    // Throw it
    throw new RuntimeException("Cannot find any open ports!");
  }

  public static synchronized void unreservePort(@Nonnegative int port){
    RESERVED_PORTS.remove(port);
  }

  public static boolean isPortOpen(@Nonnegative int port){
    try(ServerSocket ignored = new ServerSocket(port)){
      return true;
    }catch(IOException e){
      return false;
    }
  }

  @Nonnull
  public static String generateEmailAddress(){
    return generateString(getRandom().nextInt(12) + 5) + "@fake.com";
  }

  @Nonnull
  private static Random getRandom(){
    return RANDOM_GENERATOR.updateAndGet(item -> item == null ? new SecureRandom() : item);
  }

  @Nonnull
  public static String generateString(@Nonnegative long length){

    // Check length
    Validation.assertNaturalNumber(length, "length");

    // Set up characterset
    String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String fullalphabet = alphabet + alphabet.toLowerCase() + "123456789";

    // Set up random generator
    Random random = getRandom();

    // Set up string builder
    StringBuilder sb = new StringBuilder();

    // Build random string
    LongStream.range(0, length).forEach(i -> sb.append(fullalphabet.charAt(random.nextInt(fullalphabet.length()))));

    // Return the string
    return sb.toString();

  }

  @Nonnull
  public static String generateLegibleString(){

    // Get word
    var words = getRandom().nextInt(3) + 2;

    // List
    var usedWords = new HashSet<String>();

    // StringBuilder
    var sb = new StringBuilder();

    // Loop until all words received
    while(usedWords.size() < words){

      // Draw from random list
      var index = getRandom().nextInt(RANDOM_WORDS.size());

      // Get word
      var word = RANDOM_WORDS.get(index);

      // Add to used words
      if(!usedWords.add(word)) continue;

      // Captialize the first letter
      word = Character.toUpperCase(word.charAt(0)) + word.substring(1);

      // Add to string
      sb.append(word);
    }

    // Return string
    return sb.toString();
  }
}
