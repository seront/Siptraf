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
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import modelo.entity.EstacionTramoParada;
import modelo.entity.EstacionTramoParadaPK;
import modelo.entity.PreCirculacion;

/**
 *
 * @author seront
 */
public class PreCirculacionDeserializer implements JsonDeserializer<PreCirculacion>{
//public class PreCirculacionDeserializer implements JsonDeserializer<Serializable>{

    public PreCirculacionDeserializer() {
    }
    

    @Override
    public PreCirculacion deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        PreCirculacion pc=new PreCirculacion();
        JsonObject jo=json.getAsJsonObject();
        pc.setColor(jo.get("color").getAsString());
        pc.setEstacionLlegada(jo.get("estacionLlegada").getAsInt());
        pc.setEstacionSalida(jo.get("estacionSalida").getAsInt());
        pc.setHoraInicial(jo.get("horaInicio").getAsDouble());
        pc.setMarchaTipo(jo.get("marchaTipo").getAsInt());
        pc.setPrefijoNumeracion(jo.get("prefijoNumeracion").getAsInt());
//        System.out.println(json.getAsJsonObject().toString());
        JsonArray ja= jo.getAsJsonArray("teps");
//        System.out.println("Deserializando TEPS: "+ja);
        ArrayList<EstacionTramoParada> estacionTramoParadaList= new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            EstacionTramoParadaPK tepPK= new EstacionTramoParadaPK();
            tepPK.setIdEstacion( ja.get(i).getAsJsonObject().get("estacion").getAsDouble());
            EstacionTramoParada tep= new EstacionTramoParada();
            tep.setEstacionTramoParadaPK(tepPK);
//            EstacionTramoParada tep= new EstacionTramoParada(pc.getPreCirculacionPK().getIdProgramacionHoraria(), pc.getPreCirculacionPK().getIdPreCirculacion(), ja.get(i).getAsJsonObject().get("estacion").getAsDouble());
           
            tep.setTiempo(ja.get(i).getAsJsonObject().get("tramo").getAsInt());
//            tep.setParada(ja.get(i).getAsJsonObject().get("parada").getAsInt());
             try{
            if(ja.get(i).getAsJsonObject().get("parada").isJsonNull()==true){
                tep.setParada(0);
            }else{
                tep.setParada(ja.get(i).getAsJsonObject().get("parada").getAsInt());
            }   
             }catch(NullPointerException e){
           tep.setParada(0);
             }
              estacionTramoParadaList.add(tep);
        }
        pc.setEstacionTramoParadaList(estacionTramoParadaList);        
        return pc;
    }
}

