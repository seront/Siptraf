package modelo;

import java.util.List;
import modelo.entity.CurvaEsfuerzo;
import modelo.entity.Estacion;
import modelo.entity.MaterialRodante;
import modelo.entity.Restriccion;
import modelo.entity.Segmento;
import java.util.ArrayList;
import modelo.controlBD.CurvaEsfuerzoJpaController;
import modelo.controlBD.EstacionJpaController;
import modelo.controlBD.LineaJpaController;
import modelo.controlBD.MaterialRodanteJpaController;
import modelo.controlBD.RestriccionJpaController;
import modelo.controlBD.SegmentoJpaController;
import modelo.entity.CircuitoVia;
import modelo.entity.Linea;
import modelo.entity.SegmentoPK;

/**
 *
 * @author Kelvins Insua
 */
public class CalculoMarchaTipo {

    //CREO LAS CONEXIONES Y PARAMETROS NECESARIOS PARA EL CALCULO
    MaterialRodanteJpaController mrjc = new MaterialRodanteJpaController(Conex.getEmf());
    CurvaEsfuerzoJpaController cejc = new CurvaEsfuerzoJpaController(Conex.getEmf());
    SegmentoJpaController sjc = new SegmentoJpaController(Conex.getEmf());
    RestriccionJpaController rjc = new RestriccionJpaController(Conex.getEmf());
    LineaJpaController ljc = new LineaJpaController(Conex.getEmf());
    MaterialRodante materialRodante;
    public double velocidad = 0;
    double traccion;
    List<CurvaEsfuerzo> ce;
    List<Segmento> segmento;
    double resistenciaEnCurva = 0;
    double resistenciaEnRecta = 0;
    double fuerzaResultante = 0;
    double resistenciaRampa = 0;
    double aceleracion = 0;
    double velocidadFinal = 0;
    double tiempoFinal = 0;
    double tiempo = 0;
    double progresivaActual;
    int r = 0;
    double progresivaFinal;
    EstacionJpaController ejc = new EstacionJpaController(Conex.getEmf());
    int e = 1;
    List<Double> cambiosVelocidad = new ArrayList<>();
    List<Double> cambiosProgresiva = new ArrayList<>();
    List<Double> cambiosTiempos = new ArrayList<>();
    List<Double> tiempoEstaciones = new ArrayList<>();
    List<Double> distanciaEntreEstaciones = new ArrayList<>();
    List<Double> tiempoMargen = new ArrayList<>();
    List<Double> tiempoIdeal = new ArrayList<>();
    List<Double> tiempoPerdidoEntreEst = new ArrayList<>();
    List<Boolean> parada = new ArrayList<>();
    boolean acelerar = true;
    boolean agregado = false;
    int de = 0;
    int ecp = 1;
    int idLinea = 0;
    double tiempoPerdidoRest=0;
    double tiempoAcelerarVelMax=0;

    public CalculoMarchaTipo(List<Segmento> segmentos, boolean sentido, List<Estacion> estaciones, int idLinea, int idMaterialRodante, double velocidadMarcha, List<Restriccion> restriccion, double cargaMarcha,List<Estacion> estacionesTotales) {
        //INSTANCIO EL METODO SIMULAR DENTRO DEL CONSTRUCTOR DE MARCHATIPO
        simular(segmentos, sentido, estaciones, idLinea, idMaterialRodante, velocidadMarcha, restriccion,cargaMarcha,estacionesTotales);
    }

    public void simular(List<Segmento> segmentos, boolean sentido, List<Estacion> estacionesConParada,
            int idLinea, int idMaterialRodante, double velocidadMarcha, List<Restriccion> restriccion,double cargaMarcha,List<Estacion> estaciones) {

        //GENERO LOS PARAMETROS NECESARIOS PARA INICIAR LA SIMULACION
        this.idLinea = idLinea;
        materialRodante = mrjc.findMaterialRodante(idMaterialRodante);
        ce = cejc.curvaDelMaterialRodante(idMaterialRodante);
        System.out.println(e);
        tiempoMargen.add(0.0);
        tiempoIdeal.add(0.0);
        e = 1;
        progresivaActual = estaciones.get(0).getEstacionPK().getIdPkEstacion();
        progresivaFinal = estaciones.get(estaciones.size() - 1).getEstacionPK().getIdPkEstacion();
        cambiosVelocidad.add(0.0);
        cambiosTiempos.add(0.0);
        tiempoEstaciones.add(0.0);
        tiempoPerdidoEntreEst.add(0.0);
        parada.add(false);
        cambiosProgresiva.add(progresivaActual);
        System.out.println("progresiva actual: " + progresivaActual);
        Linea lin=ljc.findLinea(idLinea);
        SegmentoPK calculoSPK=new SegmentoPK(idLinea, 00);
        Segmento calculo=new Segmento(calculoSPK, 10000, true, 0);
        calculo.setRampaAscendente(0.0);
        calculo.setRampaDescendente(0.0);
        calculo.setRadioCurvatura(0.0);
        calculo.setVelocidadMaxAscendente(lin.getVelocidadLinea());
        calculo.setVelocidadMaxDescendente(lin.getVelocidadLinea());
        tiempoAcelerarVelMax=acelerarTiempoRestriccion(calculo, lin.getVelocidadLinea(), cargaMarcha);
        System.out.println("TIEMPO QUE TARDO A LLEGAR A VEL MAX: "+tiempoAcelerarVelMax);

        //CALCULO LA DISTANCIA QUE EXISTE ENTRE UNA ESTACION CON RESPECTO A LA ANTERIOR
        //DEPENDIENDO DEL SENTIDO
        for (int j = 0; j < estaciones.size(); j++) {
            try {
                if (sentido == true) {
                    distanciaEntreEstaciones.add(estaciones.get(j + 1).getEstacionPK().getIdPkEstacion() - estaciones.get(j).getEstacionPK().getIdPkEstacion());
                    System.out.println(j);
                    System.out.println(distanciaEntreEstaciones.size());
                    System.out.println(distanciaEntreEstaciones.get(j));
                } else {
                    distanciaEntreEstaciones.add(estaciones.get(j).getEstacionPK().getIdPkEstacion() - estaciones.get(j + 1).getEstacionPK().getIdPkEstacion());
                    System.out.println(distanciaEntreEstaciones.get(j));
                }
            } catch (IndexOutOfBoundsException exe) {
                break;
            }

        }
        //BUCLE FOR UTILIZADO PARA RECORRER LOS SEGMENTOS DE LA LINEA
        for (Segmento segmentoActual : segmentos) {
            //COMPRUEBO EL SENTIDO PARA ESTABLECER CONDICIONES
            //TRUE==ASCENDENTE FALSE==DESCENDENTE
            if (sentido == true) {
                if (progresivaActual < progresivaFinal) {
                    while (progresivaActual < segmentoActual.getPkFinal()) {
                        System.out.println("--------------------------Posicion del segmento: " + segmentos.indexOf(segmentoActual) + " ---------------------------------");
                        System.out.println("progresiva actual " + progresivaActual);
                        //COMPRUEBO SI LA MARCHA TIPO POSEE RESTRICCIONES
                        if (restriccion != null) {
                            //CAMBIO DE RESTRICCION LUEGO DE LLEGAR A SU PROGRESIVA FINAL
                            if (progresivaActual > restriccion.get(r).getProgFinal()) {
                                if (r < (restriccion.size() - 1)) {
                                    r++;
                                }
                            }
                            //CAMBIO DE ESTACION AL HABER PASADO POR ESTA
                            if (progresivaActual > estaciones.get(e).getEstacionPK().getIdPkEstacion()) {
                                if (e < (estaciones.size() - 1)) {
                                    e++;
                                }
                            }
                            if (progresivaActual > estacionesConParada.get(ecp).getEstacionPK().getIdPkEstacion()) {
                                if (ecp < (estacionesConParada.size() - 1)) {
                                    ecp++;
                                }
                            }
                            //COMPRUEBO SI ESTOY EN DENTRO DE UNA RESTRICCION PARA ACELERAR HASTA ESE LIMITE DE VELOCIDAD
                            //USO DEL METODO DE ACELERACION
                            if (progresivaActual >= restriccion.get(r).getProgInicio()
                                    && progresivaActual < restriccion.get(r).getProgFinal()) {
                                if (velocidad < restriccion.get(r).getVelocidadMaxAscendente() / 3.6) {
                                    velocidad = acelerar(segmentoActual, restriccion.get(r), sentido, estaciones.get(e), velocidadMarcha,cargaMarcha,estacionesConParada.get(ecp));

                                    if (progresivaActual >= estaciones.get(estaciones.size() - 1).getEstacionPK().getIdPkEstacion()) {
                                        break;
                                    }

                                }
                                //DE NO ESTAR EN UNA RESTRICCION ACELERO HASTA LA VELOCIDAD DE AT O LA VELOCIDAD DE MARCHA
                            } else {
                                if ((velocidad < velocidadMarcha / 3.6 && segmentoActual.getVelocidadMaxAscendente() >= velocidadMarcha) || (velocidad < segmentoActual.getVelocidadMaxAscendente() / 3.6 && velocidad < velocidadMarcha / 3.6)) {
                                    velocidad = acelerar(segmentoActual, restriccion.get(r), sentido, estaciones.get(e), velocidadMarcha,cargaMarcha,estacionesConParada.get(ecp));

                                    if (progresivaActual >= estaciones.get(estaciones.size() - 1).getEstacionPK().getIdPkEstacion()) {
                                        break;
                                    }
                                }
                            }
                            //COMPRUEBO SI MI VELOCIDAD ES IGUAL A LA VELOCIDAD DE MARCHA O LA DEL ATP O LA DE UNA RESTRICCION SI ESTOY EN ELLA
                            //UNO DEL METODO VELOCIDAD CRUCERO
                            if (velocidad == velocidadMarcha / 3.6 || velocidad == segmentoActual.getVelocidadMaxAscendente() / 3.6 || velocidad == restriccion.get(r).getVelocidadMaxAscendente() / 3.6) {
                                velocidad = velocidadCrucero(segmentoActual, restriccion.get(r), sentido, estaciones.get(e),estacionesConParada.get(ecp));
                                if (progresivaActual >= estaciones.get(estaciones.size() - 1).getEstacionPK().getIdPkEstacion()) {
                                    break;
                                }
                            }
                            //COMPRUEBO SI MI VELOCIDAD ES MAYOR A LA VELOCIDAD DE MARCHA O LA DEL ATP O LA DE LA RESTRICCION PARA FRENAR
                            //USO DEL METODO FRENADO
                            if (velocidad > velocidadMarcha / 3.6
                                    || velocidad > segmentoActual.getVelocidadMaxAscendente() / 3.6
                                    || (velocidad > restriccion.get(r).getVelocidadMaxAscendente() / 3.6
                                    && progresivaActual >= restriccion.get(r).getProgInicio() && progresivaActual < restriccion.get(r).getProgFinal())) {
                                System.out.println("FRENOOOOOOOOOOOOOOOO");
                                System.out.println("velocidad " + velocidad);
                                velocidad = frenado(sentido, segmentoActual, restriccion.get(r), materialRodante, estaciones.get(e),estacionesConParada.get(ecp));

                                if (progresivaActual >= estaciones.get(estaciones.size() - 1).getEstacionPK().getIdPkEstacion()) {
                                    break;
                                }
                            }
                        } else {
                            //SI MI MARCHA TIPO NO POSEE RESTRICCIONES SE UTILIZAN LOS METODOS 
                            //ACELERAR, VELOCIDAD CRUCERO Y FRENADO SIN RESTRICCIONES
                            //ESTO CON LA FINALIDAD DE ACELERAR LOS CALCULOS
                            if (progresivaActual < estaciones.get(estaciones.size() - 1).getEstacionPK().getIdPkEstacion()) {
                                if (progresivaActual > estaciones.get(e).getEstacionPK().getIdPkEstacion()) {
                                    if (e < (estaciones.size() - 1)) {
                                        e++;
                                    }
                                }
                            if (progresivaActual < estacionesConParada.get(estacionesConParada.size() - 1).getEstacionPK().getIdPkEstacion()) {
                                if (progresivaActual > estacionesConParada.get(ecp).getEstacionPK().getIdPkEstacion()) {
                                    if (ecp < (estaciones.size() - 1)) {
                                        ecp++;
                                    }
                                }
                            }

                                if ((velocidad < velocidadMarcha / 3.6 && segmentoActual.getVelocidadMaxAscendente() >= velocidadMarcha) || (velocidad < segmentoActual.getVelocidadMaxAscendente() / 3.6 && velocidad < velocidadMarcha / 3.6)) {
                                    velocidad = acelerarSinRestriccion(segmentoActual, sentido, estaciones.get(e), velocidadMarcha,cargaMarcha,estacionesConParada.get(ecp));

                                }
                                if (velocidad == velocidadMarcha / 3.6 || velocidad == segmentoActual.getVelocidadMaxAscendente() / 3.6) {
                                    velocidad = velocidadCruceroSinRestriccion(segmentoActual, sentido, estaciones.get(e),estacionesConParada.get(ecp));

                                }
                                if (velocidad > velocidadMarcha / 3.6 || velocidad > segmentoActual.getVelocidadMaxAscendente() / 3.6) {
                                    System.out.println("FRENOOOOOOOOOOOOOOOO");
                                    System.out.println("velocidad " + velocidad);
                                    velocidad = frenadoSinRestriccion(sentido, segmentoActual, materialRodante, estaciones.get(e),estacionesConParada.get(ecp));

                                }
                            } else {
                                break;
                            }

                        }
                    }
                } else {
                    break;
                }
            } else {
                //SE REALIZA EL MISMO PROCESO PERO EN SENTIDO CONTRARIO************
                if (progresivaActual > progresivaFinal) {
                    System.out.println("--------------------------Posicion del segmento: " + segmentos.indexOf(segmentoActual) + " ---------------------------------");
                    while (progresivaActual > segmentoActual.getSegmentoPK().getIdPkInicial()) {
                        System.out.println("progresiva actual " + progresivaActual);
                        if (progresivaActual < progresivaFinal) {
                            break;
                        }
                        if (restriccion != null) {
                            if (progresivaActual < restriccion.get(r).getProgInicio()) {
                                if (r < (restriccion.size() - 1)) {
                                    r++;
                                }
                            }
                            System.out.println("ESTACIOOOOOOOOOOOON: " + estaciones.get(e).getEstacionPK().getIdPkEstacion());
                            if (progresivaActual < estaciones.get(e).getEstacionPK().getIdPkEstacion()) {
                                if (e < (estaciones.size() - 1)) {
                                    e++;
                                }
                            }
                            if (progresivaActual < estacionesConParada.get(ecp).getEstacionPK().getIdPkEstacion()) {
                                if (ecp < (estacionesConParada.size() - 1)) {
                                    ecp++;
                                }
                            }
                            if (progresivaActual > restriccion.get(r).getProgInicio() && progresivaActual <= restriccion.get(r).getProgFinal()) {
                                    if (velocidad < restriccion.get(r).getVelocidadMaxDescendente() / 3.6) {
                                    System.out.println("Entrando en el primero");
                                    velocidad = acelerar(segmentoActual, restriccion.get(r), sentido, estaciones.get(e), velocidadMarcha,cargaMarcha,estacionesConParada.get(ecp));

                                    if (progresivaActual <= estaciones.get(estaciones.size() - 1).getEstacionPK().getIdPkEstacion()) {
                                        break;
                                    }
                                }
                            } else {
                                if ((velocidad < velocidadMarcha / 3.6 && segmentoActual.getVelocidadMaxDescendente() >= velocidadMarcha) || (velocidad < segmentoActual.getVelocidadMaxDescendente() / 3.6 && velocidadMarcha > segmentoActual.getVelocidadMaxDescendente())) {

                                    velocidad = acelerar(segmentoActual, restriccion.get(r), sentido, estaciones.get(e), velocidadMarcha,cargaMarcha,estacionesConParada.get(ecp));

                                    if (progresivaActual <= estaciones.get(estaciones.size() - 1).getEstacionPK().getIdPkEstacion()) {
                                        break;
                                    }
                                }
                            }
                            if (velocidad == velocidadMarcha / 3.6 || velocidad == segmentoActual.getVelocidadMaxDescendente() / 3.6 || velocidad == restriccion.get(r).getVelocidadMaxDescendente() / 3.6) {
                                velocidad = velocidadCrucero(segmentoActual, restriccion.get(r), sentido, estaciones.get(e),estacionesConParada.get(ecp));

                            }
                            if (velocidad > velocidadMarcha / 3.6 || (velocidad > segmentoActual.getVelocidadMaxDescendente() / 3.6 && progresivaActual <= segmentoActual.getPkFinal() && velocidadMarcha > segmentoActual.getVelocidadMaxDescendente()) || (velocidad > restriccion.get(r).getVelocidadMaxDescendente() / 3.6 && progresivaActual <= restriccion.get(r).getProgFinal() && progresivaActual > restriccion.get(r).getProgInicio())) {
                                System.out.println("FRENOOOOOOOOOOOOOOOO");
                                System.out.println("velocidad " + velocidad);
                                velocidad = frenado(sentido, segmentoActual, restriccion.get(r), materialRodante, estaciones.get(e),estacionesConParada.get(ecp));

                            }

                        } else {
                            if (progresivaActual < estaciones.get(e).getEstacionPK().getIdPkEstacion()) {
                                if (e < (estaciones.size() - 1)) {
                                    e++;
                                }
                            }
                            if (progresivaActual < estacionesConParada.get(ecp).getEstacionPK().getIdPkEstacion()) {
                                if (ecp < (estacionesConParada.size() - 1)) {
                                    ecp++;
                                }
                            }

                            if ((velocidad < velocidadMarcha / 3.6 && segmentoActual.getVelocidadMaxDescendente() >= velocidadMarcha) || (velocidad < segmentoActual.getVelocidadMaxDescendente() / 3.6 && velocidadMarcha > segmentoActual.getVelocidadMaxDescendente())) {
                                velocidad = acelerarSinRestriccion(segmentoActual, sentido, estaciones.get(e), velocidadMarcha,cargaMarcha,estacionesConParada.get(ecp));
                                if (progresivaActual < estaciones.get(estaciones.size() - 1).getEstacionPK().getIdPkEstacion()) {
                                    break;
                                }

                            }
                            if (velocidad == velocidadMarcha / 3.6 || velocidad == segmentoActual.getVelocidadMaxDescendente() / 3.6) {
                                velocidad = velocidadCruceroSinRestriccion(segmentoActual, sentido, estaciones.get(e),estacionesConParada.get(ecp));

                            }
                            if (velocidad > velocidadMarcha / 3.6 || velocidad > segmentoActual.getVelocidadMaxDescendente() / 3.6) {
                                System.out.println("FRENOOOOOOOOOOOOOOOO");
                                System.out.println("velocidad " + velocidad);
                                velocidad = frenadoSinRestriccion(sentido, segmentoActual, materialRodante, estaciones.get(e),estacionesConParada.get(ecp));

                            }

                        }
                    }
                } else {
                    break;
                }
            }
        }

        //IMPRIMO LAS VELOCIDADES  Y PROGRESIVAS GUARDADAS
        for (int i = 0; i < cambiosVelocidad.size(); i++) {
            System.out.println("velocidad: " + cambiosVelocidad.get(i));
            System.out.println("progresiva: " + cambiosProgresiva.get(i));

        }
        //IMPRIMO EL TIEMPO QUE LLEVA EN EL PASO POR CADA ESTACION
        for (Double tiempoEstacione : tiempoEstaciones) {
            System.out.println("TIEMPO: " + tiempoEstacione);
        }
    }
//PROCESO DE ACELERACION CON RESTRICCIONES

    public double acelerar(Segmento segmento, Restriccion restriccion, boolean sentido, Estacion estacion, double velocidadMarcha,double cargaMarcha,Estacion estacionConParada) {
        Linea l=ljc.findLinea(idLinea);
        if (acelerar == false && agregado == false && !cambiosProgresiva.get(cambiosProgresiva.size() - 1).equals(Math.rint((progresivaActual * 10) / 10))) {
//            System.out.println("AGREGO AL ENTRAR A Acelerar");
            cambiosVelocidad.add(Math.rint(((velocidad * 3.6) * 10) / 10));
            cambiosTiempos.add(tiempo);
            cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
            agregado = true;
        }
        double distanciaFrenadoMaxima = freno(0, velocidadMarcha, materialRodante.getDesaceleracionMax())[0];
        while (true) {
            System.out.println("ACELERAR RESTRICCION");

            traccion = Calculos.seleccionarTraccion(traccion, velocidad, ce);
            if (segmento.getRecta() == false) {
                resistenciaEnCurva = Calculos.resistenciaAvanceCurva(segmento, materialRodante);
            } else {
                resistenciaEnCurva = 0;
            }
            resistenciaEnRecta = Calculos.resistenciaAlAvanceRecta(materialRodante, velocidad);
            resistenciaRampa = Calculos.fuerzaDebidoAGravedad(materialRodante, segmento, sentido);
            fuerzaResultante = ((traccion * 9.8) - resistenciaEnCurva - resistenciaEnRecta - resistenciaRampa);
            System.out.println("fuerzaResultante: " + fuerzaResultante);
//            if(fuerzaResultante<0){
//                velocidad=velocidadCrucero(segmento, restriccion, sentido, estacion);
//                return velocidad;
//            }
            aceleracion = fuerzaResultante / ((materialRodante.getMasa()+cargaMarcha) * 1000);
            velocidadFinal = Calculos.velocidadFinal(velocidad, aceleracion, 1);
            tiempoFinal = Calculos.tiempoFinal(tiempo, velocidadFinal, velocidad, aceleracion);
            velocidad = velocidadFinal;
            tiempo = tiempoFinal;
            System.out.println(" velocidad: " + velocidad + "\n progresiva actual: " + progresivaActual + "\n tiempo acumulado: " + tiempo);
            if (sentido == true) {
                progresivaActual++;
                if ((estacion.getEstacionPK().getIdPkEstacion() - progresivaActual) < distanciaFrenadoMaxima) {
                    System.out.println("Es Akiii");
                    double comprobarFreno = freno(velocidad, 0, materialRodante.getDesaceleracionMax())[0];
                    if ((estacion.getEstacionPK().getIdPkEstacion() - progresivaActual) < comprobarFreno) {
//                        if(saliendoDeRestriccion==true){
//                    tiempoPerdidoRest+=(cambiosTiempos.get(cambiosTiempos.size()-1)-(tiempo));
//                    tiempoPerdidoRest-=(((cambiosProgresiva.get(cambiosVelocidad.size() - 1))-(progresivaActual))/(l.getVelocidadLinea()/3.6));
//                    saliendoDeRestriccion=false;
//                    }
                        frenoEnParada(estacion, sentido,estacionConParada);
//                    e++;                               
                        System.out.println("Es Alla");
                        
                        if(progresivaActual>estacion.getEstacionPK().getIdPkEstacion()){
                        return velocidad;
                        }
                    }
                }
                if (progresivaActual > estacion.getEstacionPK().getIdPkEstacion()) {
                    frenoEnParada(estacion, sentido,estacionConParada);
                    return velocidad;
                }
                if (progresivaActual > segmento.getPkFinal()) {
                    agregado = false;
                    acelerar = true;
                    return velocidad;
                }
                if (velocidad > velocidadMarcha / 3.6) {
                    velocidad = velocidadMarcha / 3.6;
                    if (!cambiosVelocidad.get(cambiosVelocidad.size() - 1).equals(velocidad * 3.6)) {
                        cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                        cambiosTiempos.add(tiempo);

                        cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
                        agregado = false;
                        acelerar = true;
                    }
//                    if(saliendoDeRestriccion==true){
//                    tiempoPerdidoRest+=(cambiosTiempos.get(cambiosTiempos.size()-1)-cambiosTiempos.get(cambiosTiempos.size()-2));
//                    tiempoPerdidoRest-=(((cambiosProgresiva.get(cambiosVelocidad.size() - 1))-(cambiosProgresiva.get(cambiosVelocidad.size() - 2)))/(l.getVelocidadLinea()/3.6));
//                    saliendoDeRestriccion=false;
//                    }
                    return velocidad;
                }
                if (velocidad > (materialRodante.getVelocidadOperativa() / 3.6)) {
                    velocidad = materialRodante.getVelocidadOperativa() / 3.6;
                    if (!cambiosVelocidad.get(cambiosVelocidad.size() - 1).equals(velocidad * 3.6)) {
                        cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                        cambiosTiempos.add(tiempo);

                        cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
                        agregado = false;
                        acelerar = true;
                    }
//                    if(saliendoDeRestriccion==true){
//                    tiempoPerdidoRest+=(cambiosTiempos.get(cambiosTiempos.size()-1)-cambiosTiempos.get(cambiosTiempos.size()-2));
//                    tiempoPerdidoRest-=(((cambiosProgresiva.get(cambiosVelocidad.size() - 1))-(cambiosProgresiva.get(cambiosVelocidad.size() - 2)))/(l.getVelocidadLinea()/3.6));
//                    saliendoDeRestriccion=false;
//                    }
                    
                    return velocidad;

                }
                if (velocidad > (segmento.getVelocidadMaxAscendente() / 3.6)) {
                    velocidad = segmento.getVelocidadMaxAscendente() / 3.6;
                    if (!cambiosVelocidad.get(cambiosVelocidad.size() - 1).equals(velocidad * 3.6)) {
                        cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                        cambiosTiempos.add(tiempo);

                        cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
                        agregado = false;
                        acelerar = true;
                    }
//                    if(saliendoDeRestriccion==true){
//                    tiempoPerdidoRest+=(cambiosTiempos.get(cambiosTiempos.size()-1)-cambiosTiempos.get(cambiosTiempos.size()-2));
//                    tiempoPerdidoRest-=(((cambiosProgresiva.get(cambiosVelocidad.size() - 1))-(cambiosProgresiva.get(cambiosVelocidad.size() - 2)))/(l.getVelocidadLinea()/3.6));
//                    saliendoDeRestriccion=false;
//                    }
                    return velocidad;
                }
                if (progresivaActual >= restriccion.getProgInicio() && progresivaActual < restriccion.getProgFinal()) {
                    if (velocidad > ((restriccion.getVelocidadMaxAscendente() + 1) / 3.6)) {
                        frenado(sentido, segmento, restriccion, materialRodante, estacion,estacionConParada);
                        velocidad = restriccion.getVelocidadMaxAscendente() / 3.6;
                        if (!cambiosVelocidad.get(cambiosVelocidad.size() - 1).equals(velocidad * 3.6)) {
                            cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                            cambiosTiempos.add(tiempo);

                            cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
                            agregado = false;
                            acelerar = true;
                        }
                        return velocidad;
                    }
                    if (velocidad > (restriccion.getVelocidadMaxAscendente() / 3.6)) {
                        velocidad = restriccion.getVelocidadMaxAscendente() / 3.6;
                        if (!cambiosVelocidad.get(cambiosVelocidad.size() - 1).equals(velocidad * 3.6)) {
                            cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                            cambiosTiempos.add(tiempo);

                            cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
                            agregado = false;
                            acelerar = true;
                        }
                        
                        return velocidad;
                    }

                } else {
                }

            } else {
                if ((progresivaActual - estacion.getEstacionPK().getIdPkEstacion()) < distanciaFrenadoMaxima) {
                    double comprobarFreno = freno(velocidad, 0, materialRodante.getDesaceleracionMax())[0];
                    if ((progresivaActual - estacion.getEstacionPK().getIdPkEstacion()) < comprobarFreno) {
//                        if(saliendoDeRestriccion==true){
//                    tiempoPerdidoRest+=(cambiosTiempos.get(cambiosTiempos.size()-1)-(tiempo));
//                    tiempoPerdidoRest-=(((cambiosProgresiva.get(cambiosVelocidad.size() - 1))-(progresivaActual))/(l.getVelocidadLinea()/3.6));
//                    saliendoDeRestriccion=false;
//                    }
                        frenoEnParada(estacion, sentido,estacionConParada);                           
                       if(progresivaActual<estacion.getEstacionPK().getIdPkEstacion()){
                        return velocidad;
                        }
                    }
                }
                progresivaActual--;
                if (progresivaActual < estacion.getEstacionPK().getIdPkEstacion()) {
                    frenoEnParada(estacion, sentido,estacionConParada);                               
                    return velocidad;
                }
                if (progresivaActual < segmento.getSegmentoPK().getIdPkInicial()) {
                    agregado = false;
                    acelerar = true;
                    return velocidad;
                }
                if (velocidad > velocidadMarcha / 3.6) {
                    velocidad = velocidadMarcha / 3.6;
                    if (!cambiosVelocidad.get(cambiosVelocidad.size() - 1).equals(velocidad * 3.6)) {
                        cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                        cambiosTiempos.add(tiempo);

                        cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
                        agregado = false;
                        acelerar = true;
                    }
//                    if(saliendoDeRestriccion==true){
//                    tiempoPerdidoRest+=(cambiosTiempos.get(cambiosTiempos.size()-1)-cambiosTiempos.get(cambiosTiempos.size()-2));
//                    tiempoPerdidoRest-=(((cambiosProgresiva.get(cambiosVelocidad.size() - 1))-(cambiosProgresiva.get(cambiosVelocidad.size() - 2)))/(l.getVelocidadLinea()/3.6));
//                    saliendoDeRestriccion=false;
//                    }
                    return velocidad;
                }
                if (velocidad > (materialRodante.getVelocidadOperativa() / 3.6)) {
                    velocidad = materialRodante.getVelocidadOperativa() / 3.6;
                    if (!cambiosVelocidad.get(cambiosVelocidad.size() - 1).equals(velocidad * 3.6)) {
                        cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                        cambiosTiempos.add(tiempo);

                        cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
                        agregado = false;
                        acelerar = true;
                    }
//                    if(saliendoDeRestriccion==true){
//                    tiempoPerdidoRest+=(cambiosTiempos.get(cambiosTiempos.size()-1)-cambiosTiempos.get(cambiosTiempos.size()-2));
//                    tiempoPerdidoRest-=(((cambiosProgresiva.get(cambiosVelocidad.size() - 1))-(cambiosProgresiva.get(cambiosVelocidad.size() - 2)))/(l.getVelocidadLinea()/3.6));
//                    saliendoDeRestriccion=false;
//                    }
                    return velocidad;

                }
                if (velocidad > (segmento.getVelocidadMaxDescendente() / 3.6)) {
                    velocidad = segmento.getVelocidadMaxDescendente() / 3.6;
                    if (!cambiosVelocidad.get(cambiosVelocidad.size() - 1).equals(velocidad * 3.6)) {
                        cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                        cambiosTiempos.add(tiempo);

                        cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
                        agregado = false;
                        acelerar = true;
                    }
//                    if(saliendoDeRestriccion==true){
//                    tiempoPerdidoRest+=(cambiosTiempos.get(cambiosTiempos.size()-1)-cambiosTiempos.get(cambiosTiempos.size()-2));
//                    tiempoPerdidoRest-=(((cambiosProgresiva.get(cambiosVelocidad.size() - 1))-(cambiosProgresiva.get(cambiosVelocidad.size() - 2)))/(l.getVelocidadLinea()/3.6));
//                    saliendoDeRestriccion=false;
//                    }
                    return velocidad;
                }
                if (progresivaActual < restriccion.getProgFinal() && progresivaActual > restriccion.getProgInicio()) {
                    if (velocidad > ((restriccion.getVelocidadMaxDescendente() + 1) / 3.6)) {
                        frenado(sentido, segmento, restriccion, materialRodante, estacion,estacionConParada);
                        velocidad = restriccion.getVelocidadMaxDescendente() / 3.6;
                        if (!cambiosVelocidad.get(cambiosVelocidad.size() - 1).equals(velocidad * 3.6)) {
                            cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                            cambiosTiempos.add(tiempo);

                            cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
                            agregado = false;
                            acelerar = true;
                        }
                        return velocidad;
                    }
                    if (velocidad > ((restriccion.getVelocidadMaxDescendente()) / 3.6)) {

                        velocidad = restriccion.getVelocidadMaxDescendente() / 3.6;
                        if (!cambiosVelocidad.get(cambiosVelocidad.size() - 1).equals(velocidad * 3.6)) {
                            cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                            cambiosTiempos.add(tiempo);

                            cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
                            agregado = false;
                            acelerar = true;
                        }
                        return velocidad;
                    }
                }

            }
            System.out.println(" velocidad: " + velocidad + "\n progresiva actual: " + progresivaActual + "\n tiempo acumulado: " + tiempo);
        }

    }
//PROCESO DE ACELERACION SIN RESTRICCIONES

    public double acelerarSinRestriccion(Segmento segmento, boolean sentido, Estacion estacion, double velocidadMarcha,double cargaMarcha,Estacion estacionConParada) {
        if (acelerar == false && agregado == false && !cambiosProgresiva.get(cambiosProgresiva.size() - 1).equals(progresivaActual)) {
            cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
            cambiosTiempos.add(tiempo);
//            System.out.println("AGREGO AL ENTRAR a Acelerar Sin Restriccion");
            cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
            agregado = true;
        }

        while (true) {
            System.out.println("ACELERAR");

            traccion = Calculos.seleccionarTraccion(traccion, velocidad, ce);
            if (segmento.getRecta() == false) {
                resistenciaEnCurva = Calculos.resistenciaAvanceCurva(segmento, materialRodante);
            } else {
                resistenciaEnCurva = 0;
            }
            resistenciaEnRecta = Calculos.resistenciaAlAvanceRecta(materialRodante, velocidad);
            resistenciaRampa = Calculos.fuerzaDebidoAGravedad(materialRodante, segmento, sentido);
            //double rt=Calculos.resistenciaTotal(velocidad, segmento.getRampaAscendente());
            fuerzaResultante = ((traccion * 9.8) - resistenciaEnCurva - resistenciaEnRecta - resistenciaRampa);
            System.out.println("fuerzaResultante: " + fuerzaResultante);
//            if(fuerzaResultante<0){
//                velocidad=velocidadCruceroSinRestriccion(segmento, sentido, estacion);
//                return velocidad;
//            }
            aceleracion = fuerzaResultante / ((materialRodante.getMasa()+cargaMarcha) * 1000);
            velocidadFinal = Calculos.velocidadFinal(velocidad, aceleracion, 1);
            tiempoFinal = Calculos.tiempoFinal(tiempo, velocidadFinal, velocidad, aceleracion);
            velocidad = velocidadFinal;
            tiempo = tiempoFinal;
            System.out.println("tempo acumulado: " + tiempo);

            if (sentido == true) {
                progresivaActual++;
                if (progresivaActual > estacion.getEstacionPK().getIdPkEstacion()) {
                    frenoEnParada(estacion, sentido,estacionConParada);
                }
                if (progresivaActual > segmento.getPkFinal()) {
                    agregado = false;
                    acelerar = true;
                    return velocidad;
                }
                if (velocidad > velocidadMarcha / 3.6) {
                    velocidad = velocidadMarcha / 3.6;
                    if (!cambiosVelocidad.get(cambiosVelocidad.size() - 1).equals(velocidad * 3.6)) {
                        cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                        cambiosTiempos.add(tiempo);

                        cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
                        agregado = false;
                        acelerar = true;
                    }
                    return velocidad;
                }
                if (velocidad > (materialRodante.getVelocidadOperativa() / 3.6)) {
                    velocidad = materialRodante.getVelocidadOperativa() / 3.6;
                    if (!cambiosVelocidad.get(cambiosVelocidad.size() - 1).equals(velocidad * 3.6)) {
                        cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                        cambiosTiempos.add(tiempo);

                        cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
                        agregado = false;
                        acelerar = true;
                    }
                    return velocidad;

                }
                if (velocidad > (segmento.getVelocidadMaxAscendente() / 3.6)) {
                    velocidad = segmento.getVelocidadMaxAscendente() / 3.6;
                    if (!cambiosVelocidad.get(cambiosVelocidad.size() - 1).equals(velocidad * 3.6)) {
                        cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                        cambiosTiempos.add(tiempo);

                        cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
                        agregado = false;
                        acelerar = true;
                    }

                    return velocidad;
                }

            } else {
                progresivaActual -= 1;
                if (progresivaActual < estacion.getEstacionPK().getIdPkEstacion()) {
                    frenoEnParada(estacion, sentido,estacionConParada);
                    if (progresivaActual < estacion.getEstacionPK().getIdPkEstacion()) {
                        return velocidad;
                    }
                }
                if (progresivaActual < segmento.getSegmentoPK().getIdPkInicial()) {
                    agregado = false;
                    acelerar = true;
                    return velocidad;

                }
                if (velocidad > velocidadMarcha / 3.6) {
                    velocidad = velocidadMarcha / 3.6;
                    if (!cambiosVelocidad.get(cambiosVelocidad.size() - 1).equals(velocidad * 3.6)) {
                        cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                        cambiosTiempos.add(tiempo);

                        cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
                        agregado = false;
                        acelerar = true;
                    }
                    return velocidad;
                }

                if (velocidad > (materialRodante.getVelocidadOperativa() / 3.6)) {
                    velocidad = materialRodante.getVelocidadOperativa() / 3.6;
                    if (!cambiosVelocidad.get(cambiosVelocidad.size() - 1).equals(velocidad * 3.6)) {
                        cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                        cambiosTiempos.add(tiempo);

                        cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
                        agregado = false;
                        acelerar = true;
                    }
                    return velocidad;

                }
                if (velocidad > (segmento.getVelocidadMaxDescendente() / 3.6)) {
                    velocidad = segmento.getVelocidadMaxDescendente() / 3.6;
                    if (!cambiosVelocidad.get(cambiosVelocidad.size() - 1).equals(velocidad * 3.6)) {
                        cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                        cambiosTiempos.add(tiempo);

                        cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
                        agregado = false;
                        acelerar = true;
                    }
                    return velocidad;
                }

            }
            System.out.println(" velocidad: " + velocidad + "\n progresiva actual: " + progresivaActual + "\n tiempo acumulado: " + tiempo);
        }

    }

    //VELOCIDAD CRUCERO CON RESTRICCIONES
    public double velocidadCrucero(Segmento segmento, Restriccion restriccion, boolean sentido, Estacion estacion, Estacion estacionConParada) {
//        traccion = Calculos.seleccionarTraccion(traccion, velocidad, ce);
//            if (segmento.getRecta() == false) {
//                resistenciaEnCurva = Calculos.resistenciaAvanceCurva(segmento, materialRodante);
//            } else {
//                resistenciaEnCurva = 0;
//            }
//            resistenciaEnRecta = Calculos.resistenciaAlAvanceRecta(materialRodante, velocidad);
//            resistenciaRampa = Calculos.fuerzaDebidoAGravedad(materialRodante, segmento, sentido);
//            //double rt=Calculos.resistenciaTotal(velocidad, segmento.getRampaAscendente());
//            fuerzaResultante = ((traccion * 9.8) - resistenciaEnCurva - resistenciaEnRecta - resistenciaRampa);
//            if(fuerzaResultante<0){
//                double [] f=freno(velocidad, Calculos.velocidadPermitidaRampa(resistenciaEnCurva + resistenciaEnRecta + resistenciaRampa, ce), materialRodante.getDesaceleracionMax());
//                velocidad=Calculos.velocidadPermitidaRampa(resistenciaEnCurva + resistenciaEnRecta + resistenciaRampa, ce);
//                tiempo+=f[1];
//                progresivaActual+=f[0];
//            }
        Linea l = ljc.findLinea(idLinea);
        System.out.println("VELOCIDAD CRUCERO" + "\n velocidad: " + velocidad + "\n progresiva actual: " + progresivaActual + "\n tiempo acumulado: " + tiempo);
        if (sentido == true) {
            System.out.println(restriccion.getProgInicio());
            if (progresivaActual > restriccion.getProgInicio() && progresivaActual < restriccion.getProgFinal()) {
                tiempoFinal = tiempo + ((restriccion.getProgFinal() - progresivaActual) / velocidad);
                tiempoPerdidoRest+=(((restriccion.getProgFinal() - progresivaActual) / velocidad)-((restriccion.getProgFinal() - progresivaActual) / (l.getVelocidadLinea()/3.6)));

                acelerar = false;
                progresivaActual = restriccion.getProgFinal() + 1;
                tiempo = tiempoFinal;
                System.out.println("de inicio a fin de una restriccion" + "\n velocidad: " + velocidad + "\n progresiva actual: " + progresivaActual + "\n tiempo acumulado: " + tiempo);
                if (progresivaActual >= estacion.getEstacionPK().getIdPkEstacion()) {
                    frenoEnParada(estacion, sentido,estacionConParada);
                }
                return velocidad;
            }
            if (segmento.getPkFinal() > restriccion.getProgInicio() && progresivaActual < restriccion.getProgInicio()) {
                tiempoFinal = tiempo + (((restriccion.getProgInicio() - progresivaActual) / velocidad));
                tiempoPerdidoRest+=+ (((restriccion.getProgInicio() - progresivaActual) / velocidad)-((restriccion.getProgInicio() - progresivaActual) / (l.getVelocidadLinea()/3.6)));

                acelerar = false;
                progresivaActual = restriccion.getProgInicio() + 1;
                tiempo = tiempoFinal;
                System.out.println("Des inicio de un segmento a inicio de una restriccion" + "\n velocidad: " + velocidad + "\n progresiva actual: " + progresivaActual + "\n tiempo acumulado: " + tiempo);
                if (progresivaActual >= estacion.getEstacionPK().getIdPkEstacion()) {
                    frenoEnParada(estacion, sentido,estacionConParada);
                }
                return velocidad;

            } else {
                acelerar = false;
                tiempoFinal = tiempo + ((segmento.getPkFinal() - progresivaActual) / velocidad);
                progresivaActual = segmento.getPkFinal();
                tiempo = tiempoFinal;
                System.out.println("de inicio a fin de un segmento" + "\n velocidad: " + velocidad + "\n progresiva actual: " + progresivaActual + "\n tiempo acumulado: " + tiempo);
                if (progresivaActual >= estacion.getEstacionPK().getIdPkEstacion()) {
                    frenoEnParada(estacion, sentido,estacionConParada);
                }
                return velocidad;
            }

        } else {
            if (restriccion.getProgFinal() > progresivaActual && progresivaActual > restriccion.getProgInicio()) {
                acelerar = false;
                tiempoFinal = tiempo + ((progresivaActual - restriccion.getProgInicio()) / velocidad);
                tiempoPerdidoRest+=(((progresivaActual - restriccion.getProgInicio()) / velocidad)-((progresivaActual - restriccion.getProgInicio()) / (l.getVelocidadLinea()/3.6)));

                progresivaActual = restriccion.getProgInicio();
                tiempo = tiempoFinal;
                System.out.println("de inicio a fin de una restriccion" + "\n velocidad: " + velocidad + "\n progresiva actual: " + progresivaActual + "\n tiempo acumulado: " + tiempo);
                if (progresivaActual <= estacion.getEstacionPK().getIdPkEstacion()) {
                    frenoEnParada(estacion, sentido,estacionConParada);
                }
                return velocidad;
            }
            if (segmento.getSegmentoPK().getIdPkInicial() <= restriccion.getProgFinal() && progresivaActual > restriccion.getProgFinal()) {
                acelerar = false;
                tiempoFinal = tiempo + ((progresivaActual - restriccion.getProgFinal()) / velocidad);
                tiempoPerdidoRest+=(((progresivaActual - restriccion.getProgFinal()) / velocidad)-((progresivaActual - restriccion.getProgFinal()) / (l.getVelocidadLinea()/3.6)));

                progresivaActual = restriccion.getProgFinal() - 1;
                tiempo = tiempoFinal;
                System.out.println("De inicio de un segmento a inicio de una restriccion" + "\n velocidad: " + velocidad + "\n progresiva actual: " + progresivaActual + "\n tiempo acumulado: " + tiempo);
                if (progresivaActual <= estacion.getEstacionPK().getIdPkEstacion()) {
                    frenoEnParada(estacion, sentido,estacionConParada);
                }
                return velocidad;

            } else {
                acelerar = false;
                tiempoFinal = tiempo + ((progresivaActual - segmento.getSegmentoPK().getIdPkInicial()) / velocidad);
                progresivaActual = segmento.getSegmentoPK().getIdPkInicial();
                tiempo = tiempoFinal;
                System.out.println("De inicio a Final de una Segmento" + "\n velocidad: " + velocidad + "\n progresiva actual: " + progresivaActual + "\n tiempo acumulado: " + tiempo);
                if (progresivaActual <= estacion.getEstacionPK().getIdPkEstacion()) {
                    frenoEnParada(estacion, sentido,estacionConParada);
                }
                return velocidad;
            }

        }

    }

    //VELOCIDAD CRUCERO SIN RESTRICCIONES

    public double velocidadCruceroSinRestriccion(Segmento segmento, boolean sentido, Estacion estacion,Estacion estacionConParada) {
        System.out.println("VELOCIDAD CRUCERO" + "\n velocidad: " + velocidad + "\n progresiva actual: " + progresivaActual + "\n tiempo acumulado: " + tiempo);
        if (sentido == true) {
            acelerar = false;
            tiempoFinal = tiempo + ((segmento.getPkFinal() - progresivaActual) / velocidad);
            progresivaActual = segmento.getPkFinal();
            tiempo = tiempoFinal;
            System.out.println("Sentido Ascendente" + "\n velocidad: " + velocidad + "\n progresiva actual: " + progresivaActual + "\n tiempo acumulado: " + tiempo);
            if (progresivaActual >= estacion.getEstacionPK().getIdPkEstacion()) {
                frenoEnParada(estacion, sentido,estacionConParada);
            }
            return velocidad;

        } else {
            acelerar = false;
            tiempoFinal = tiempo + ((progresivaActual - segmento.getSegmentoPK().getIdPkInicial()) / velocidad);
            progresivaActual = segmento.getSegmentoPK().getIdPkInicial();
            tiempo = tiempoFinal;
            System.out.println("Sentido descendente" + "\n velocidad: " + velocidad + "\n progresiva actual: " + progresivaActual + "\n tiempo acumulado: " + tiempo);
            if (progresivaActual <= estacion.getEstacionPK().getIdPkEstacion()) {
                frenoEnParada(estacion, sentido,estacionConParada);
            }
            return velocidad;

        }

    }
//PROCESO DE FRENADO CON RESTRICCIONES

    public double frenado(boolean sentido, Segmento segmento, Restriccion restriccion,
            MaterialRodante materialRodante, Estacion estacion,Estacion estacionConParada) {
        Linea l=ljc.findLinea(idLinea);
        if (sentido == true) {
            if (velocidad > segmento.getVelocidadMaxAscendente() / 3.6) {
                if (estacion.getEstacionPK().getIdPkEstacion() <= segmento.getPkFinal()) {
                    double[] distanciaTiempo = freno(velocidad, 0, materialRodante.getDesaceleracionMax());
                    if (distanciaTiempo[0] > (estacion.getEstacionPK().getIdPkEstacion() - segmento.getSegmentoPK().getIdPkInicial())) {
                        frenoEnParada(estacion, sentido,estacionConParada);
                        return velocidad;
                    }
                }
                System.out.println("***** Frenando *****");
                acelerar = false;
                double[] distanciaTiempo = freno(velocidad, segmento.getVelocidadMaxAscendente() / 3.6, materialRodante.getDesaceleracionMax());
                tiempo += distanciaTiempo[1] - ((distanciaTiempo[0]) / velocidad);
                progresivaActual = segmento.getSegmentoPK().getIdPkInicial();
                progresivaActual -= distanciaTiempo[0];
                if (!cambiosProgresiva.get(cambiosProgresiva.size() - 1).equals(Math.rint((progresivaActual * 10) / 10))) {
                    cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                    cambiosTiempos.add(tiempo - ((distanciaTiempo[0]) / velocidad));
                    cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
                }
                progresivaActual = segmento.getSegmentoPK().getIdPkInicial();
                velocidad = segmento.getVelocidadMaxAscendente() / 3.6;
                System.out.println("velocidad: " + velocidad + "\n progresiva actual: " + progresivaActual + "\n Tiempo: " + tiempo);

                if (progresivaActual > estacion.getEstacionPK().getIdPkEstacion()) {
                    frenoEnParada(estacion, sentido,estacionConParada);
                    return velocidad;
                }
                if (!cambiosVelocidad.get(cambiosVelocidad.size() - 1).equals(velocidad * 3.6)) {
                    cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                    cambiosTiempos.add(tiempo);

                    cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
                }
                progresivaActual++;
                return velocidad;
            }
            if (velocidad > restriccion.getVelocidadMaxAscendente() / 3.6) {
                acelerar = false;
                if (estacion.getEstacionPK().getIdPkEstacion() <= restriccion.getProgFinal()) {
                    double[] distanciaTiempo = freno(velocidad, 0, materialRodante.getDesaceleracionMax());
                    if (distanciaTiempo[0] > (estacion.getEstacionPK().getIdPkEstacion() - restriccion.getProgInicio())) {
                        frenoEnParada(estacion, sentido,estacionConParada);
                        return velocidad;
                    }
                }
                System.out.println("***** Frenando ***** por resticcion-----------");
                double[] distanciaTiempo = freno(velocidad, restriccion.getVelocidadMaxAscendente() / 3.6, materialRodante.getDesaceleracionMax());
                tiempo += distanciaTiempo[1] - ((distanciaTiempo[0]) / velocidad);
                tiempoPerdidoRest+=(distanciaTiempo[1]-(distanciaTiempo[0]/(segmento.getVelocidadMaxAscendente()/3.6)))+(Calculos.factorReduccionTiempo(restriccion.getVelocidadMaxAscendente(),l.getVelocidadLinea(),tiempoAcelerarVelMax,segmento.getVelocidadMaxAscendente()));
                //tiempoPerdidoRest+=(distanciaTiempo[1]-(distanciaTiempo[0]/(l.getVelocidadLinea()/3.6)));
                //System.out.println((tiempoAcelerarVelMax*Calculos.factorReduccionTiempo(restriccion.getVelocidadMaxAscendente(),l.getVelocidadLinea())));

                progresivaActual = restriccion.getProgInicio();
                progresivaActual -= distanciaTiempo[0];
                System.out.println("velocidad: " + velocidad + "\n progresiva actual: " + progresivaActual);
                if (!cambiosProgresiva.get(cambiosProgresiva.size() - 1).equals(Math.rint((progresivaActual * 10) / 10))) {
                    cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                    cambiosTiempos.add(tiempo - ((distanciaTiempo[0]) / velocidad));
                    cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
                }
                if (progresivaActual > estacion.getEstacionPK().getIdPkEstacion()) {
                    frenoEnParada(estacion, sentido,estacionConParada);
                    return velocidad;
                }
                progresivaActual = restriccion.getProgInicio();
                velocidad = restriccion.getVelocidadMaxAscendente() / 3.6;
                if (!cambiosVelocidad.get(cambiosVelocidad.size() - 1).equals(velocidad * 3.6)) {
                    cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                    cambiosTiempos.add(tiempo);

                    cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
                }
                progresivaActual++;
                return velocidad;
            }
        } else {
            if (velocidad > restriccion.getVelocidadMaxDescendente() / 3.6 && progresivaActual <= restriccion.getProgFinal() && progresivaActual > restriccion.getProgInicio()) {
                acelerar = false;
                if (estacion.getEstacionPK().getIdPkEstacion() >= restriccion.getProgInicio()) {
                    double[] distanciaTiempo = freno(velocidad, 0, materialRodante.getDesaceleracionMax());
                    if (distanciaTiempo[0] > (restriccion.getProgFinal() - estacion.getEstacionPK().getIdPkEstacion())) {
                        frenoEnParada(estacion, sentido,estacionConParada);
                        return velocidad;
                    }
                }
                double[] distanciaTiempo = freno(velocidad, restriccion.getVelocidadMaxDescendente() / 3.6, materialRodante.getDesaceleracionMax());
                tiempo += distanciaTiempo[1] - ((distanciaTiempo[0]) / velocidad);
                tiempoPerdidoRest+=(distanciaTiempo[1]-(distanciaTiempo[0]/(l.getVelocidadLinea()/3.6)))+(Calculos.factorReduccionTiempo(restriccion.getVelocidadMaxDescendente(),l.getVelocidadLinea(),tiempoAcelerarVelMax,segmento.getVelocidadMaxDescendente()));

                progresivaActual = restriccion.getProgFinal();
                progresivaActual += distanciaTiempo[0];
                System.out.println("***** Frenando Akiiii*****" + "\n progresiva actual: " + progresivaActual
                        + "\n velocidad: " + velocidad + "\n tiempo " + tiempo);
                if (!cambiosProgresiva.get(cambiosProgresiva.size() - 1).equals(Math.rint((progresivaActual * 10) / 10))) {
                    cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                    cambiosTiempos.add(tiempo - ((distanciaTiempo[0]) / velocidad));
                    cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
                }
                if (progresivaActual < estacion.getEstacionPK().getIdPkEstacion()) {
                    frenoEnParada(estacion, sentido,estacionConParada);
                    return velocidad;
                }
                progresivaActual = restriccion.getProgFinal();
                velocidad = restriccion.getVelocidadMaxDescendente() / 3.6;

                if (!cambiosVelocidad.get(cambiosVelocidad.size() - 1).equals(velocidad * 3.6)) {
                    cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                    cambiosTiempos.add(tiempo);

                    cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
                }
                progresivaActual--;
                return velocidad;
            }
            if (velocidad > segmento.getVelocidadMaxDescendente() / 3.6) {
                acelerar = false;
                System.out.println("***** Frenando *****");
                double[] distanciaTiempo = freno(velocidad, segmento.getVelocidadMaxDescendente() / 3.6, materialRodante.getDesaceleracionMax());
                cambiosTiempos.add(tiempo - ((progresivaActual - segmento.getSegmentoPK().getIdPkInicial()) / velocidad));
                tiempo += distanciaTiempo[1] - (distanciaTiempo[0] / velocidad);
                
                progresivaActual = segmento.getPkFinal();
                progresivaActual += distanciaTiempo[0];
                cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                cambiosTiempos.add(tiempo);
                cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
                progresivaActual = segmento.getPkFinal();
                velocidad = segmento.getVelocidadMaxDescendente() / 3.6;

                System.out.println("velocidad: " + velocidad + "\n progresiva actual: " + progresivaActual);
                if (progresivaActual < estacion.getEstacionPK().getIdPkEstacion()) {
                    frenoEnParada(estacion, sentido,estacionConParada);
                    return velocidad;
                }
                if (!cambiosVelocidad.get(cambiosVelocidad.size() - 1).equals(velocidad * 3.6)) {
                    cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                    cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
                }
                progresivaActual--;
                return velocidad;
            }
        }

        return velocidad;

    }
//PROCESO DE FRENADO SIN RESTRICCIONES

    public double frenadoSinRestriccion(boolean sentido, Segmento segmento,
            MaterialRodante materialRodante, Estacion estacion,Estacion estacionConParada) {
        if (sentido == true) {
            if (velocidad > segmento.getVelocidadMaxAscendente() / 3.6) {
                System.out.println("***** Frenando sin restriccion*****");
                acelerar = false;
                double[] distanciaTiempo = freno(velocidad, segmento.getVelocidadMaxAscendente() / 3.6, materialRodante.getDesaceleracionMax());
                cambiosTiempos.add(tiempo - ((progresivaActual - segmento.getSegmentoPK().getIdPkInicial()) / velocidad));
                tiempo += distanciaTiempo[1] - ((progresivaActual - segmento.getSegmentoPK().getIdPkInicial()) / velocidad);
                progresivaActual = segmento.getSegmentoPK().getIdPkInicial();
                progresivaActual -= distanciaTiempo[0];
                cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                cambiosTiempos.add(tiempo);
                cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));

                progresivaActual = segmento.getSegmentoPK().getIdPkInicial();
                velocidad = segmento.getVelocidadMaxAscendente() / 3.6;

                System.out.println("velocidad: " + velocidad + "\n progresiva actual: " + progresivaActual);
                if (progresivaActual > estacion.getEstacionPK().getIdPkEstacion()) {
                    frenoEnParada(estacion, sentido,estacionConParada);
                    return velocidad;
                }
                if (!cambiosVelocidad.get(cambiosVelocidad.size() - 1).equals(velocidad * 3.6)) {
                    cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                    cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
                }
                progresivaActual++;
                return velocidad;
            }

        } else {
            if (velocidad > segmento.getVelocidadMaxDescendente() / 3.6) {
                acelerar = false;
                System.out.println("***** Frenando *****");
                double[] distanciaTiempo = freno(velocidad, segmento.getVelocidadMaxDescendente() / 3.6, materialRodante.getDesaceleracionMax());
                cambiosTiempos.add(tiempo - ((progresivaActual - segmento.getSegmentoPK().getIdPkInicial()) / velocidad));
                tiempo += distanciaTiempo[1] - (distanciaTiempo[0] / velocidad);

                progresivaActual = segmento.getPkFinal();
                progresivaActual += distanciaTiempo[0];
                cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                cambiosTiempos.add(tiempo);
                cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
                progresivaActual = segmento.getPkFinal();
                velocidad = segmento.getVelocidadMaxDescendente() / 3.6;

                System.out.println("velocidad: " + velocidad + "\n progresiva actual: " + progresivaActual);
                if (progresivaActual < estacion.getEstacionPK().getIdPkEstacion()) {
                    frenoEnParada(estacion, sentido,estacionConParada);
                    return velocidad;
                }
                if (!cambiosVelocidad.get(cambiosVelocidad.size() - 1).equals(velocidad * 3.6)) {
                    cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                    //                  cambiosTiempos.add(tiempo);
                    cambiosProgresiva.add(Math.rint((progresivaActual * 10) / 10));
                }
                progresivaActual--;
                return velocidad;
            }
        }

        return velocidad;

    }

    static double[] freno(double velInicio, double velFinal, double desaceleracion) {
        double[] distanciaTiempo = new double[2];
        distanciaTiempo[0] = Math.abs(((velFinal * velFinal) - (velInicio * velInicio)) / (2 * Math.abs(desaceleracion)));
        distanciaTiempo[1] = Math.sqrt(distanciaTiempo[0] / Math.abs(desaceleracion));
        return distanciaTiempo;
    }

//PROCESO DE FRENO EN PARADAS
    public double frenoEnParada(Estacion estacion, boolean sentido,Estacion estacionConParada) {
       // System.out.println(tiempoPerdidoRest);
       
        Linea linea=ljc.findLinea(idLinea);
        if (sentido == true) {
            if (progresivaActual >= estacion.getEstacionPK().getIdPkEstacion()) {
                acelerar = false;
                System.out.println("***** Frenando En Estacion *****" + estacion.getNombreEstacion());
              double tiempoPerdidoXEst=tiempoPerdidoRest;
            for (int i = 0; i < tiempoPerdidoEntreEst.size(); i++) {
               tiempoPerdidoXEst -= tiempoPerdidoEntreEst.get(i);
                
            }
            tiempoPerdidoEntreEst.add(tiempoPerdidoXEst);
                double[] distanciaTiempo = freno(velocidad, 0.0, materialRodante.getDesaceleracionMax());
               
                double tiempoIdeal1=0;
                if(estacion.getEstacionPK().getIdPkEstacion()==estacionConParada.getEstacionPK().getIdPkEstacion()){
                    parada.add(true);
                tiempoIdeal1 = tiempo + distanciaTiempo[1] - ((progresivaActual - estacion.getEstacionPK().getIdPkEstacion()) / velocidad);
                }else{
                    parada.add(false);
                 tiempoIdeal1 = tiempo - ((progresivaActual - estacion.getEstacionPK().getIdPkEstacion()) / velocidad);
                }
                
                for (int i = 0; i < tiempoIdeal.size(); i++) {

                    tiempoIdeal1 -= tiempoIdeal.get(i);
                    

                }
                tiempoIdeal.add(tiempoIdeal1);
                if (cambiosProgresiva.get(cambiosProgresiva.size() - 1) > (estacion.getEstacionPK().getIdPkEstacion() - distanciaTiempo[0])) {
                    cambiosProgresiva.remove(cambiosProgresiva.size() - 1);
                    cambiosVelocidad.remove(cambiosVelocidad.size() - 1);
                    cambiosTiempos.remove(cambiosTiempos.size() - 1);
                }
                if(estacion.getEstacionPK().getIdPkEstacion()==estacionConParada.getEstacionPK().getIdPkEstacion()){
                cambiosVelocidad.add(Math.rint(Math.rint((velocidad * 3.6 * 10) / 10)));
                cambiosTiempos.add(tiempo - ((progresivaActual - estacion.getEstacionPK().getIdPkEstacion()) / velocidad));
                cambiosProgresiva.add(Math.rint((estacion.getEstacionPK().getIdPkEstacion() - distanciaTiempo[0]) * 10) / 10);
                }
                if(estacion.getEstacionPK().getIdPkEstacion()==estacionConParada.getEstacionPK().getIdPkEstacion()){
                tiempo += distanciaTiempo[1] - ((progresivaActual - estacion.getEstacionPK().getIdPkEstacion()) / velocidad);
                }else{
                tiempo -= ((progresivaActual - estacion.getEstacionPK().getIdPkEstacion()) / velocidad);
                }
                
                if(linea.getTipoLinea()==true){
                tiempoMargen.add(((distanciaEntreEstaciones.get(de) * 5 * 60) / 100000));
                }else{
                tiempoMargen.add(((distanciaEntreEstaciones.get(de) * 10 * 60) / 100000));
                }
                
                System.out.println("+++++++++++++++++++++++++++" + ((distanciaEntreEstaciones.get(de) * 5 * 60) / 100000) + "++++++++++++");
                de++;
                progresivaActual = estacion.getEstacionPK().getIdPkEstacion() + 1;
                if(estacion.getEstacionPK().getIdPkEstacion()==estacionConParada.getEstacionPK().getIdPkEstacion()){
                
                velocidad = 0;
                
                }
                

                System.out.println("velocidad: " + velocidad + "\n progresiva actual: " + progresivaActual + "\n Tiempo: " + tiempo);

                tiempoEstaciones.add(tiempoIdeal.get(tiempoIdeal.size() - 1) + tiempoMargen.get(tiempoMargen.size() - 1));

                if (!cambiosVelocidad.get(cambiosVelocidad.size() - 1).equals(velocidad * 3.6)||estacion.getEstacionPK().getIdPkEstacion()!=estacionConParada.getEstacionPK().getIdPkEstacion()) {
                    cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                    cambiosTiempos.add(tiempo);
                    if (progresivaActual > progresivaFinal) {
                        cambiosProgresiva.add(progresivaFinal);
                    } else {
                        cambiosProgresiva.add(Math.rint(((progresivaActual) * 10) / 10));
                    }

                }
                return velocidad;
            }
        } else {
            acelerar = false;
            double[] distanciaTiempo = freno(velocidad, 0.0, materialRodante.getDesaceleracionMax());
            System.out.println("***** Frenando En Estacion *****" + estacion.getNombreEstacion());
            double tiempoPerdidoXEst=tiempoPerdidoRest;
            for (int i = 0; i < tiempoPerdidoEntreEst.size(); i++) {
               tiempoPerdidoXEst -= tiempoPerdidoEntreEst.get(i);
                
            }
            tiempoPerdidoEntreEst.add(tiempoPerdidoXEst);
            
             double tiempoIdeal1=0;
            if(estacion.getEstacionPK().getIdPkEstacion()==estacionConParada.getEstacionPK().getIdPkEstacion()){
            tiempoIdeal1 = tiempo + distanciaTiempo[1] - ((estacion.getEstacionPK().getIdPkEstacion() - progresivaActual) / velocidad);
            parada.add(true);
            }else{
            tiempoIdeal1 = tiempo  - ((estacion.getEstacionPK().getIdPkEstacion() - progresivaActual) / velocidad);
            parada.add(false);
            }
            for (int i = 0; i < tiempoIdeal.size(); i++) {

                tiempoIdeal1 -= tiempoIdeal.get(i);

            }
            tiempoIdeal.add(tiempoIdeal1);
            if (cambiosProgresiva.get(cambiosProgresiva.size() - 1) < (estacion.getEstacionPK().getIdPkEstacion() + distanciaTiempo[0])) {
                cambiosProgresiva.remove(cambiosProgresiva.size() - 1);
                cambiosVelocidad.remove(cambiosVelocidad.size() - 1);
                cambiosTiempos.remove(cambiosTiempos.size() - 1);

            }
            if(estacion.getEstacionPK().getIdPkEstacion()==estacionConParada.getEstacionPK().getIdPkEstacion()){
            
            cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
            cambiosTiempos.add(tiempo - ((estacion.getEstacionPK().getIdPkEstacion() - progresivaActual) / velocidad));
            cambiosProgresiva.add(Math.rint((estacion.getEstacionPK().getIdPkEstacion() + distanciaTiempo[0]) * 10) / 10);
            }
            if(estacion.getEstacionPK().getIdPkEstacion()==estacionConParada.getEstacionPK().getIdPkEstacion()){
            tiempo += distanciaTiempo[1] - ((estacion.getEstacionPK().getIdPkEstacion() - progresivaActual) / velocidad);
            }else{
            tiempo -= ((estacion.getEstacionPK().getIdPkEstacion() - progresivaActual) / velocidad);
            }
            
            progresivaActual = estacion.getEstacionPK().getIdPkEstacion() - 1;
            if(estacion.getEstacionPK().getIdPkEstacion()==estacionConParada.getEstacionPK().getIdPkEstacion()){
            
            velocidad = 0;
            }
            if(linea.getTipoLinea()==true){
                tiempoMargen.add(((distanciaEntreEstaciones.get(de) * 5 * 60) / 100000));
                }else{
                tiempoMargen.add(((distanciaEntreEstaciones.get(de) * 10 * 60) / 100000));
                }
            System.out.println("+++++++++++++++++++++++++++" + ((distanciaEntreEstaciones.get(de) * 5 * 60) / 100000) + "++++++++++++");
            de++;
            System.out.println("velocidad: " + velocidad + "\n progresiva actual: " + progresivaActual + "\n Tiempo: " + tiempo);
            tiempoEstaciones.add(tiempoIdeal.get(tiempoIdeal.size() - 1) + tiempoMargen.get(tiempoMargen.size() - 1));

            if (!cambiosVelocidad.get(cambiosVelocidad.size() - 1).equals(velocidad * 3.6)||estacion.getEstacionPK().getIdPkEstacion()!=estacionConParada.getEstacionPK().getIdPkEstacion()) {
                cambiosTiempos.add(tiempo);
                cambiosVelocidad.add(Math.rint((velocidad * 3.6 * 10) / 10));
                if (progresivaActual < 0) {
                    cambiosProgresiva.add(0.0);
                } else {
                    cambiosProgresiva.add(Math.rint(((progresivaActual) * 10) / 10));
                }

            }
            return velocidad;

        }

        return velocidad;

    }
    
    public double acelerarTiempoRestriccion(Segmento segmento, double velocidadLinea,double cargaMarcha){
        double traccion=0,resistenciaEnCurva,resistenciaEnRecta,fuerzaResultante,aceleracion,velocidadFinal,tiempoFinal,velocidad=0,tiempo=0;
        while (true) { 
            System.out.println("ACELERAR Calculo de Restriccion");

            traccion = Calculos.seleccionarTraccion(traccion, velocidad, ce);
                resistenciaEnCurva = 0;
            resistenciaEnRecta = Calculos.resistenciaAlAvanceRecta(materialRodante, velocidad);
            
            //double rt=Calculos.resistenciaTotal(velocidad, segmento.getRampaAscendente());
            fuerzaResultante = ((traccion * 9.8) - resistenciaEnCurva - resistenciaEnRecta);
           // System.out.println("fuerzaResultante: " + fuerzaResultante);
            aceleracion = fuerzaResultante / ((materialRodante.getMasa()+cargaMarcha) * 1000);
            velocidadFinal = Calculos.velocidadFinal(velocidad, aceleracion, 1);
            tiempoFinal = Calculos.tiempoFinal(tiempo, velocidadFinal, velocidad, aceleracion);
            velocidad = velocidadFinal;
            tiempo = tiempoFinal;
           // System.out.println("tempo acumulado: " + tiempo);

            //System.out.println(" velocidad: " + velocidad + "\n progresiva actual: " + progresivaActual + "\n tiempo acumulado: " + tiempo);
            if((velocidad*3.6)>=velocidadLinea){
            //velocidad=velocidadLinea;
             return tiempo;
            }
           
        }
   
    
    }
    
     
      
      
      
    public double getVelocidad() {
        return velocidad;
    }

    public double getTiempo() {
        return tiempo;
    }

    public double getProgresivaFinal() {
        return progresivaFinal;
    }

    public List<Double> getCambiosVelocidad() {
        return cambiosVelocidad;
    }

    public List<Double> getCambiosProgresiva() {
        return cambiosProgresiva;
    }

    public List<Double> getTiempoEstaciones() {
        return tiempoEstaciones;
    }

    public List<Double> getTiempoMargen() {
        return tiempoMargen;
    }

    public List<Double> getTiempoIdeal() {
        return tiempoIdeal;
    }

    public List<Double> getCambiosTiempos() {
        return cambiosTiempos;
    }

    public double getTiempoPerdidoRest() {
        return tiempoPerdidoRest;
    }

    public List<Double> getTiempoPerdidoEntreEst() {
        return tiempoPerdidoEntreEst;
    }

    public List<Boolean> getParada() {
        return parada;
    }

    
    
    
    

}
