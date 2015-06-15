/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import java.lang.reflect.Type;

/**
 *
 * @author seront
 */
public class StringArrayPrecirculacionesDeserializer implements JsonDeserializer<String[]>{

    @Override
    public String[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        System.out.println("Tamaño del array "+json.getAsJsonArray().size());

        String salida[]= new String[json.getAsJsonArray().size()];
        for (int i = 0; i < json.getAsJsonArray().size(); i++) {
            String salida1 = json.getAsJsonArray().get(i).getAsJsonObject().toString();
            System.out.println(salida1);
            salida[i]=salida1;
        }
        return salida;
    }
    
}
