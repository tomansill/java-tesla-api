package com.ansill.tesla.utility;

import okhttp3.Response;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

/**
 * Utility Class
 */
public final class Utility{

    private Utility(){
        // Prevents instantiation
    }

    @Nonnull
    public static Optional<String> getStringFromResponseBody(@Nonnull Response response) throws IOException{
        var body = response.body();
        if(body == null) return Optional.empty();
        return Optional.of(body.string());
    }

    /**
     * Formats string, replaces any '{}' with objects. But this method will add '"' between any String. If String is null. then it will simply use 'null'
     *
     * @param message message with '{}'
     * @param objects objects to replace
     * @return formatted string
     */
    @Nonnull
    public static String fs(@Nonnull String message, @Nonnull Object... objects){
        return format(message, Arrays.stream(objects).map(object -> {
            if(object == null) return "null";
            else if(object instanceof String) return "\"" + object.toString() + "\"";
            return object.toString();
        }).toArray(Object[]::new));
    }

    /**
     * Formats string, replaces any '{}' with objects
     *
     * @param message message with '{}'
     * @param objects objects to replace
     * @return formatted string
     */
    @Nonnull
    public static String f(@Nonnull String message, @Nonnull Object... objects){
        return format(message, objects);
    }

    /**
     * Formats string, replaces any '{}' with objects
     *
     * @param message message with '{}'
     * @param objects objects to replace
     * @return formatted string
     */
    @Nonnull
    public static String format(@Nullable String message, @Nullable Object... objects){

        // If any of parameters are null, then return message
        if(message == null) return "null";
        if(objects == null) return message;

        // Set up builder
        StringBuilder builder = new StringBuilder();

        // Set up index in objects
        int objects_index = 0;

        // Set up indices for message
        int previous_index = 0;
        int brace_index = 0;

        // Loop until all is replaced or all elements in object array is used
        while(objects_index < objects.length && (brace_index = message.indexOf("{}", brace_index)) != -1){

            // Copy in characters since previous index
            builder.append(message, previous_index, brace_index);

            // Update brace index to skip "{}"
            brace_index = Math.min(brace_index + 2, message.length());

            // Update the previous index
            previous_index = brace_index;

            // Format 'null' if null
            if(objects[objects_index] == null) builder.append("null");

                // Use normal string if String
            else if(objects[objects_index] instanceof String) builder.append((String) objects[objects_index]);

                // Else, use .toString() method
            else builder.append(objects[objects_index].toString());

            // Increment the array
            objects_index++;

        }

        // Finish the string if there's any remaining
        if(previous_index < message.length()) builder.append(message, previous_index, message.length());

        // Return result
        return builder.toString();
    }

}

