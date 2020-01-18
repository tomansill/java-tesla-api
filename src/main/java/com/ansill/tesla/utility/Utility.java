package com.ansill.tesla.utility;

import com.ansill.validation.Validation;
import okhttp3.Response;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Utility Class
 */
public final class Utility{

    private Utility(){
        throw new AssertionError(f("No {} instances for you!", this.getClass().getName()));
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

    @Nonnull
    public static String simpleToString(@Nonnull Object object){

        // Ensure no null
        Validation.assertNonnull(object, "object");

        // Print class name and its fields
        return object.getClass().getSimpleName() + "(" + Arrays.stream(object.getClass().getDeclaredFields())
                                                               .map(field -> {
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
                                                                           ex.printStackTrace();
                                                                           return "inaccessible";
                                                                       }finally{
                                                                           field.setAccessible(false);
                                                                       }
                                                                   }
                                                               })
                                                               .collect(Collectors.joining(", ")) + ")";
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

    public static String getClassValues(@Nullable Object object){

        // Short circuit if null
        if(object == null) return "null";
        switch(object.getClass().getName()){
            case "java.lang.Boolean":
            case "java.lang.Integer":
            case "java.lang.Long":
            case "java.lang.Short":
            case "java.lang.Byte":
            case "java.lang.Double":
            case "java.lang.Float":
                return object.toString();
            case "java.lang.String":
                return "\"" + object.toString() + "\"";
            default:
                break;
        }

        // Set up builder
        StringBuilder sb = new StringBuilder();

        // Get object's class
        sb.append(object.getClass().getSimpleName());

        // Start the fields
        sb.append("(");

        // Get fields
        boolean first = true;
        for(Field field : object.getClass().getDeclaredFields()){

            // First
            if(first) first = false;
            else sb.append(", ");

            // Add field name
            sb.append(field.getName());

            // Add equals
            sb.append("=");

            // Get value
            try{
                sb.append(getClassValues(field.get(object)));
            }catch(IllegalAccessException e){

                // Try again but with accessible set to true
                field.setAccessible(true);
                try{
                    sb.append(getClassValues(field.get(object)));
                }catch(IllegalAccessException ex){
                    throw new RuntimeException(e);
                }finally{
                    //field.setAccessible(false); // Undo it
                }
            }

        }


        // End the fields
        sb.append(")");

        // Finish the string
        return sb.toString();
    }
}

