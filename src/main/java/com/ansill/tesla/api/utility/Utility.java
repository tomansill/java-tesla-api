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

/**
 * Utility Class
 */
public final class Utility{

    // Purely for debugging - remove later
    @Deprecated
    public static final AtomicReference<BiConsumer<Request,ReusableResponse>> HTTP_LOGGING = new AtomicReference<>();

    private Utility(){
        throw new AssertionError(f("No {} instances for you!", this.getClass().getName()));
    }

    @Nonnull
    public static Optional<String> getStringFromResponseBody(@Nonnull Response response) throws IOException{
        var body = response.body();
        if(body == null) return Optional.empty();
        return Optional.of(body.string());
    }

    @Nonnull
    public static ReusableResponse httpCall(@Nonnull Request request) throws IOException{
        ReusableResponse reusableResponse = null;
        try{
            reusableResponse = new ReusableResponse(new OkHttpClient().newCall(request).execute());
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

