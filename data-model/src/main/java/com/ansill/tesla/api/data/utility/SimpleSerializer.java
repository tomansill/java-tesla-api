package com.ansill.tesla.api.data.utility;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

import static com.ansill.utility.Utility.f;

public class SimpleSerializer extends StdSerializer<Object>{

  private static final long serialVersionUID = -6006044835289650773L;

  public SimpleSerializer(){
    super(Object.class);
  }

  public static String snakeCase(@Nonnull String camelCase){

    // Set up new string
    StringBuilder sb = new StringBuilder();

    // Iterate through original
    for(var byteChar : camelCase.getBytes()){

      // Convert to char
      var character = (char) byteChar;

      // Check if its alphanumeric and uppercase
      if(Character.isAlphabetic(character) && Character.isUpperCase(character)){

        // Add underscore
        sb.append('_');

        // Lowercase it and add it
        sb.append(Character.toLowerCase(character));

      }else{
        sb.append(character);
      }

    }

    // Return string
    return sb.toString();
  }

  @Override
  public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException{

    // Set up start
    gen.writeStartObject();

    // Build fields
    var fields = new HashSet<Field>();
    Class<?> clazz = value.getClass();
    while(clazz != Object.class){

      // Add all fields
      fields.addAll(Arrays.stream(clazz.getDeclaredFields())
                          .filter(item -> !Modifier.isStatic(item.getModifiers()))
                          .filter(item -> !item.getName().startsWith("$") && !item.getName().startsWith("_"))
                          .collect(Collectors.toList()));

      // Get superclass
      clazz = clazz.getSuperclass();
    }

    // Get fields
    for(var field : fields){

      Object object;
      try{
        object = field.get(value);
      }catch(IllegalAccessException e){

        // Check if it's access issue
        if(!e.getMessage().contains("modifiers \"private")){
          throw new RuntimeException(f("Field '{}' is unaccessible", field.getName()), e);
        }

        // Temporarily change access
        field.setAccessible(true);
        try{

          // Access it
          object = field.get(value);

        }catch(IllegalAccessException ex){

          // Throw it
          throw new RuntimeException(f("Field '{}' is unaccessible", field.getName()), e);

        }finally{
          field.setAccessible(false);
        }
      }

      // Write object
      gen.writeObjectField(snakeCase(field.getName()), object);
    }

    // End start
    gen.writeEndObject();
  }
}
