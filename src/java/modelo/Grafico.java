/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

//import controlador.CurvaEsfuerzoJpaController;
//import controlador.SegmentoJpaController;
//import entity.CurvaEsfuerzo;
//import entity.Segmento;
import java.util.List;
import modelo.controlBD.CurvaEsfuerzoJpaController;
import modelo.controlBD.SegmentoJpaController;
import modelo.entity.CurvaEsfuerzo;
import modelo.entity.Segmento;

/**
 *
 * @author usuario
 */
public class Grafico {
    CurvaEsfuerzoJpaController cejc=new CurvaEsfuerzoJpaController(Conex.getEmf());
    GestorLista gl=new GestorLista();
    List<CurvaEsfuerzo> ce;
    List<Segmento> segmentos;
    
    public String velocidadesGrafico(int idMaterialRodante) {
        String velocidades = "";

        ce = cejc.curvaDelMaterialRodante(idMaterialRodante);

        for (int i = 0; i < ce.size(); i++) {
            if (i == ce.size() - 1) {
                velocidades += ce.get(i).getCurvaEsfuerzoPK().getIdVelocidadCurvaEsfuerzo();

            } else {
                velocidades += ce.get(i).getCurvaEsfuerzoPK().getIdVelocidadCurvaEsfuerzo() + ",";
            }

        }

        System.out.println("velocidades: " + velocidades);
        return velocidades;

    }
      public String traccionGrafico(int idMaterialRodante) {
        String esfuerzoTraccion="";
   
        ce = cejc.curvaDelMaterialRodante(idMaterialRodante);

        for (int i = 0; i < ce.size(); i++) {
            if (i == ce.size() - 1) {
                esfuerzoTraccion += ce.get(i).getEsfuerzoTraccion();

            } else {
                esfuerzoTraccion += ce.get(i).getEsfuerzoTraccion() + ",";
            }

        }

        System.out.println("traccion: " + esfuerzoTraccion);
        return esfuerzoTraccion;

    }
      public String frenadoGrafico(int idMaterialRodante) {
        String esfuerzoFrenado="";
   
        ce = cejc.curvaDelMaterialRodante(idMaterialRodante);

        for (int i = 0; i < ce.size(); i++) {
            if (i == ce.size() - 1) {
                esfuerzoFrenado += ce.get(i).getEsfuerzoFrenado();

            } else {
                esfuerzoFrenado += ce.get(i).getEsfuerzoFrenado() + ",";
            }

        }

        System.out.println("frenado: " + esfuerzoFrenado);
        return esfuerzoFrenado;

    }
      
      public String velocidadesMarchaTipo(int idLinea, boolean sentido) {
        String velocidadesMarchaTipo="";
          System.out.println(sentido);
          SegmentoJpaController sjc=new SegmentoJpaController(Conex.getEmf());
        if(sentido==true){
        segmentos=sjc.buscarIdLineaAscendente(idLinea);
        }else{
        segmentos=sjc.buscarIdLineaDescendente(idLinea);
        }
        for (int i = 0; i < segmentos.size(); i++) {
            if(sentido==true){
            if (i == segmentos.size() - 1) {
                velocidadesMarchaTipo += segmentos.get(i).getVelocidadMaxAscendente();

            } else {
                 velocidadesMarchaTipo += segmentos.get(i).getVelocidadMaxAscendente() + ",";
            }
            

        }else{
            if (i == segmentos.size() - 1) {
                velocidadesMarchaTipo += segmentos.get(i).getVelocidadMaxDescendente();

            } else {
                 velocidadesMarchaTipo += segmentos.get(i).getVelocidadMaxDescendente() + ",";
            }
            }
        }
        System.out.println("velocidades Marcha Tipo: " + velocidadesMarchaTipo);
        return velocidadesMarchaTipo;

    }
      
      public String progresivasMarchaTipo(int idLinea, boolean sentido) {
        String progresivasMarchaTipo="";
          SegmentoJpaController sjc=new SegmentoJpaController(Conex.getEmf());
        System.out.println(sentido);
        if(sentido==true){
        segmentos=sjc.buscarIdLineaAscendente(idLinea);
        }else{
        segmentos=sjc.buscarIdLineaDescendente(idLinea);
        }
        for (int i = 0; i < segmentos.size(); i++) {
            if(sentido==true){
            if (i == segmentos.size() - 1) {
                
                progresivasMarchaTipo += segmentos.get(i).getSegmentoPK().getIdPkInicial();

            } else {
                
                 progresivasMarchaTipo += segmentos.get(i).getSegmentoPK().getIdPkInicial() + ",";
            }
            

        }else{
            if (i == segmentos.size() - 1) {
                progresivasMarchaTipo += segmentos.get(i).getPkFinal();

            } else {
                 progresivasMarchaTipo += segmentos.get(i).getPkFinal() + ",";
            }
            }
        }
        System.out.println("progresivass Marcha Tipo: " + progresivasMarchaTipo);
        return progresivasMarchaTipo;

    }
//    public static void main(String[] args) {
//        velocidadesGrafico(01);
//        traccionGrafico(01);
//        frenadoGrafico(01);
//    }
      
}
