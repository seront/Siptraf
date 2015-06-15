/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import modelo.entity.Estacion;
import modelo.entity.Restriccion;
import modelo.entity.RestriccionPK;
import modelo.entity.Segmento;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.CalculoMarchaTipo;
import modelo.Conex;
import modelo.GestorLista;
import modelo.controlBD.CircuitoViaJpaController;
import modelo.controlBD.CurvaEsfuerzoJpaController;
import modelo.controlBD.EstacionJpaController;
import modelo.controlBD.GraficoMarchaTipoJpaController;
import modelo.controlBD.LineaJpaController;
import modelo.controlBD.MarchaTipoJpaController;
import modelo.controlBD.MaterialRodanteJpaController;
import modelo.controlBD.RestriccionJpaController;
import modelo.controlBD.RestriccionMarchaTipoJpaController;
import modelo.controlBD.SegmentoJpaController;
import modelo.controlBD.TiempoEstacionMarchaTipoJpaController;
import modelo.controlBD.exceptions.IllegalOrphanException;
import modelo.controlBD.exceptions.NonexistentEntityException;
import modelo.entity.CircuitoVia;
import modelo.entity.CurvaEsfuerzo;
import modelo.entity.EstacionPK;
import modelo.entity.GraficoMarchaTipo;
import modelo.entity.GraficoMarchaTipoPK;
import modelo.entity.Linea;
import modelo.entity.MaterialRodante;
import modelo.entity.RestriccionMarchaTipo;
import modelo.entity.RestriccionMarchaTipoPK;
import modelo.entity.TiempoEstacionMarchaTipo;
import modelo.entity.TiempoEstacionMarchaTipoPK;
import modelo.entity.Usuario;

/**
 *
 * @author Kelvins Insua
 */
@WebServlet(name = "MarchaTipo", urlPatterns = {"/MarchaTipo"})
public class MarchaTipo extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        //   System.out.println("Entrando en el metodo de simular la marcha");
        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "Editar":
                    editar(request, response);
                    break;
                case "Eliminar":
                    eliminar(request, response);
                    break;
                case "Simular":
                    simularMarchaTipo(request, response);
                    break;
                case "Generar Variables":
                    generarVariables(request, response);
                    break;
                case "Editar Tiempo Minimo":
                    editarTiempoMinimo(request, response);
                    break;
            }
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("do get Entrando en el metodo de simular la marcha");
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(MarchaTipo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("doPost Entrando en el metodo de simular la marcha");
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(MarchaTipo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void simularMarchaTipo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception {

        PrintWriter salida = response.getWriter();
        HttpSession session = request.getSession();
        Usuario usr = (Usuario) session.getAttribute("usuario");
        
        if(usr.getNivel()>2){
        salida.print("NO TIENES PERMISO PARA REALIZAR ESTA ACCIÓN");
        return;
        }
        //OBTENGO LOS PARAMETROS NECESARIOS DESDE EL JSP INGRESOMARCHATIPO.JSO Y CREO LAS CONEXIONES NECESARIAS
        MaterialRodanteJpaController mrjc = new MaterialRodanteJpaController(Conex.getEmf());
        LineaJpaController ljc=new LineaJpaController(Conex.getEmf());
        CurvaEsfuerzoJpaController cejc = new CurvaEsfuerzoJpaController(Conex.getEmf());
        SegmentoJpaController sjc = new SegmentoJpaController(Conex.getEmf());
        RestriccionJpaController rjc = new RestriccionJpaController(Conex.getEmf());
        EstacionJpaController ejc = new EstacionJpaController(Conex.getEmf());
        int idMaterialRodante = Integer.parseInt(request.getParameter("materialRodante"));
        double velocidadMarcha = Double.parseDouble(request.getParameter("vel"));
        int idLinea = Integer.parseInt(request.getParameter("idLinea"));
        boolean sentido;
        double progEstInicio = Double.parseDouble(request.getParameter("progInicial"));
        double progEstFinal = Double.parseDouble(request.getParameter("progFinal"));
        double cargaMarcha = Double.parseDouble(request.getParameter("carga_marcha"));
        boolean atp=Boolean.parseBoolean(request.getParameter("atp"));
        List<Boolean> parada1 = new ArrayList<>();
        //LE DOY VALOR AL SENTIDO DE LA MARCHA
        if (progEstInicio > progEstFinal) {
            sentido = false;

        } else {

            sentido = true;
        }
        //COMPRUEBO QUE LA VELOCIDAD INTRODUCIDA NO SUPERE LA PERMITIDA POR EL MATERIAL RODANTE
        if (mrjc.findMaterialRodante(idMaterialRodante).getVelocidadOperativa() < velocidadMarcha||ljc.findLinea(idLinea).getVelocidadLinea()<velocidadMarcha) {
            if(mrjc.findMaterialRodante(idMaterialRodante).getVelocidadOperativa()<=ljc.findLinea(idLinea).getVelocidadLinea()){
                velocidadMarcha = mrjc.findMaterialRodante(idMaterialRodante).getVelocidadOperativa();
            }else{
            velocidadMarcha = ljc.findLinea(idLinea).getVelocidadLinea();
            }
            
        }
        if(cargaMarcha>mrjc.findMaterialRodante(idMaterialRodante).getCargaMaxima()){
            cargaMarcha=mrjc.findMaterialRodante(idMaterialRodante).getCargaMaxima();
        }
        
//        CurvaEsfuerzoJpaController cejc=new CurvaEsfuerzoJpaController(Conex.getEmf());
        List<CurvaEsfuerzo> ce=cejc.curvaDelMaterialRodante(idMaterialRodante);
        
       
        if(ce.isEmpty()==true){
        
        salida.print("EL MATERIAL RODANTE NO POSEE DEFINIDAS CURVAS DE ESFUERZO DE TRACCION Y FRENADO, "
                + "POR FAVOR INGRESE DICHAS CURVAS Y VUELVA A INTENTARLO");
        return;
        }
        List<Segmento> segmento;
        List<Estacion> estaciones;
        List<Estacion> estacionesConParada =new ArrayList<>();
        List<Restriccion> restriccionesMarchaTipo = new ArrayList<>();
        String restriccion = request.getParameter("restricciones");
        String estacionParada = request.getParameter("estacionesConParada");
        String[] idRestricciones;
        String[] idPkEstacion;
        //COMPRUEBO Y AGREGO RESTRICCIONES
        if (!restriccion.equals("")) {

            idRestricciones = restriccion.split(" ");
            for (int i = 0; i < idRestricciones.length; i++) {
                RestriccionPK rpk = new RestriccionPK(idLinea, Integer.parseInt(idRestricciones[i]));
                restriccionesMarchaTipo.add(rjc.findRestriccion(rpk));

            }
        } else {
            restriccionesMarchaTipo = null;
        }
        
        
        //ORDENO POR SENTIDO LAS ESTACIONES Y SEGMENTOS DE LA LINEA
        if (sentido == true) {
            segmento = sjc.buscarIdLineaAscendente(idLinea);
            estaciones = ejc.buscarEstacionesMTAsc(progEstInicio, progEstFinal, idLinea);
            estacionesConParada.add(estaciones.get(0));
            if (!estacionParada.equals("")) {

            idPkEstacion = estacionParada.split(" ");
            for (int i = 0; i < idPkEstacion.length; i++) {
                EstacionPK epk = new EstacionPK(idLinea, Double.parseDouble(idPkEstacion[i]));
                estacionesConParada.add(ejc.findEstacion(epk));

            }
        }
            estacionesConParada.add(estaciones.get(estaciones.size()-1));

        } else {
            segmento = sjc.buscarIdLineaDescendente(idLinea);
            estaciones = ejc.buscarEstacionesMTDesc(progEstInicio, progEstFinal, idLinea);
            estacionesConParada.add(estaciones.get(0));
            if (!estacionParada.equals("")) {

            idPkEstacion = estacionParada.split(" ");
            for (int i = 0; i < idPkEstacion.length; i++) {
                EstacionPK epk = new EstacionPK(idLinea, Double.parseDouble(idPkEstacion[i]));
                estacionesConParada.add(ejc.findEstacion(epk));

            }
        }
            estacionesConParada.add(estaciones.get(estaciones.size()-1));
        }
        //COMPRUEBO SI LA MARCHA TIPO DEBE TOMAR EN CUENTA EL ATP
        if (atp == false) {
            for (Segmento segment : segmento) {
                segment.setVelocidadMaxAscendente(100.0);
                segment.setVelocidadMaxDescendente(100.0);
            }
        }
        //INSTANCIO UN OBJETO DE LA CLASE CALCULO MARCHATIPO PARA GENERAR LOS TIEMPOS Y VALORES NECESARIOS
        CalculoMarchaTipo cmt = new CalculoMarchaTipo(segmento, sentido, estacionesConParada, idLinea, idMaterialRodante, velocidadMarcha, restriccionesMarchaTipo,cargaMarcha,estaciones);

        System.out.println("TIEMPOS PERDIDOS: "+cmt.getTiempoPerdidoRest());
        //OBTENGO EL TIEMPO TITAL DE MARGEN
        double tiempoTotalMargen = 0;
        for (int i = 0; i < cmt.getTiempoMargen().size(); i++) {
            tiempoTotalMargen += cmt.getTiempoMargen().get(i);

        }
        //OBTENGO EL TIEMPO TOTAL DE MARGEN
        int horasM, minutosM, segundosM;
        horasM = (int) (tiempoTotalMargen / 3600);
        minutosM = (int) (tiempoTotalMargen % 3600 / 60);
        segundosM = (int) (tiempoTotalMargen % 3600 % 60);
        String tiempoTotalM = (horasM + ":" + minutosM + ":" + segundosM);
        Time tiempoTotalSimulacionMargen = Time.valueOf(tiempoTotalM);

        //OBTENGO EL TIEMPO TOTAL IDEAL
        double tiempoTotalIdeal = cmt.getTiempo();
        int horasI, minutosI, segundosI;
        horasI = (int) (tiempoTotalIdeal / 3600);
        minutosI = (int) (tiempoTotalIdeal % 3600 / 60);
        segundosI = (int) (tiempoTotalIdeal % 3600 % 60);
        String tiempoTotalI = (horasI + ":" + minutosI + ":" + segundosI);
        Time tiempoTotalSimulacionIdeal = Time.valueOf(tiempoTotalI);

        //OBTENGO EL TIEMPO TOTAL PERDIDO POR LIMITACIONES
        double tiempoTotalRest = cmt.getTiempoPerdidoRest();
        int horasR, minutosR, segundosR;
        horasR = (int) (tiempoTotalRest / 3600);
        minutosR = (int) (tiempoTotalRest % 3600 / 60);
        segundosR = (int) (tiempoTotalRest % 3600 % 60);
        String tiempoTotalR = (horasR + ":" + minutosR + ":" + segundosR);
        Time tiempoTotalSimulacionR = Time.valueOf(tiempoTotalR);
        
        //OBTENGO EL TIEMPO DE MARGEN + EL TIEMPO IDEAL
        double totalEnSegundos = cmt.getTiempo() + tiempoTotalMargen;
        int horas, minutos, segundos;
        horas = (int) (totalEnSegundos / 3600);
        minutos = (int) (totalEnSegundos % 3600 / 60);
        segundos = (int) (totalEnSegundos % 3600 % 60);
        String tiempoTotal = (horas + ":" + minutos + ":" + segundos);
        Time tiempoTotalSimulacion = Time.valueOf(tiempoTotal);

        //CREO LOS LIST QUE GUARDARAN LOS TIEMPOS OBTENIDOS
        List<Time> tiemposIdeales = new ArrayList<>();
        List<Time> tiemposMargen = new ArrayList<>();
        List<Time> tiemposReales = new ArrayList<>();
        //List<Time> tiemposAsimilados = new ArrayList<>();
        List<Time> tiemposRedondeo = new ArrayList<>();
        List<Time> tiemposPerdidoXRest = new ArrayList<>();
        List<String> tiemposProg = new ArrayList<>();

        //AGREGO LOS TIEMPOS IDEALES AL ARRAYLIST
        for (double ti : cmt.getTiempoIdeal()) {
            double totalIdeal = ti;
            int horas1, minutos1, segundos1;
            horas1 = (int) (totalIdeal / 3600);
            minutos1 = (int) (totalIdeal % 3600 / 60);
            segundos1 = (int) (totalIdeal % 3600 % 60);
            String tiempoTotal1 = (horas1 + ":" + minutos1 + ":" + segundos1);
            Time tiempoTotalSimulacion1 = Time.valueOf(tiempoTotal1);
            tiemposIdeales.add(tiempoTotalSimulacion1);

        }
        //AGREGO LOS TIEMPOS MARGEN AL ARRAYLIST
        for (double ti : cmt.getTiempoMargen()) {
            double totalIdeal = ti;
            int horas1, minutos1, segundos1;
            horas1 = (int) (totalIdeal / 3600);
            minutos1 = (int) (totalIdeal % 3600 / 60);
            segundos1 = (int) (totalIdeal % 3600 % 60);
            String tiempoTotal1 = (horas1 + ":" + minutos1 + ":" + segundos1);
            Time tiempoTotalSimulacion1 = Time.valueOf(tiempoTotal1);
            tiemposMargen.add(tiempoTotalSimulacion1);

        }
        //TIEMPOS PERDIDOS ENTRE ESTACIONES
       
        for (double ti : cmt.getTiempoPerdidoEntreEst()) {
            double totalIdeal = ti;
            int horas1, minutos1, segundos1;
            horas1 = (int) (totalIdeal / 3600);
            minutos1 = (int) (totalIdeal % 3600 / 60);
            segundos1 = (int) (totalIdeal % 3600 % 60);
            
            String tiempoTotal1 = (horas1 + ":" + minutos1 + ":" + segundos1);
            Time tiempoTotalSimulacion1 = Time.valueOf(tiempoTotal1);
            tiemposPerdidoXRest.add(tiempoTotalSimulacion1);

        }

        //OBTENGO LOS TIEMPOS QUE IRAN AL GRAFICO VELOCIDAD VS TIEMPO
        for (int i = 0; i < cmt.getCambiosTiempos().size(); i++) {
            double totalIdeal = cmt.getCambiosTiempos().get(i);
            double velocidad = cmt.getCambiosVelocidad().get(i);
            int horas1, minutos1, segundos1;
            horas1 = (int) (totalIdeal / 3600);
            minutos1 = (int) (totalIdeal % 3600 / 60);
            segundos1 = (int) (totalIdeal % 3600 % 60);
            String tiempoTotal1 = ("[Date.UTC(2015,01,01," + horas1 + "," + minutos1 + "," + segundos1 + ")," + velocidad + "]");
            //Time tiempoTotalSimulacion1 = Time.valueOf(tiempoTotal1);
            tiemposProg.add(tiempoTotal1);

        }
        int horaTotal=0;
        int minutoTotal=0;
        Time tiempoTotalRedondeado;
        //OBTENGO LOS TIEMPOS IDEALES+REALES EN LAS ESTACIONES
        for (double ti : cmt.getTiempoEstaciones()) {
            double totalIdeal = ti;
            int horas1, minutos1, segundos1;
            horas1 = (int) (totalIdeal / 3600);
            minutos1 = (int) (totalIdeal % 3600 / 60);
            segundos1 = (int) (totalIdeal % 3600 % 60);
            String tiempoTotal1 = (horas1 + ":" + minutos1 + ":" + segundos1);
            Time tiempoTotalSimulacion1 = Time.valueOf(tiempoTotal1);

            tiemposReales.add(tiempoTotalSimulacion1);

        }
        for (double ti : cmt.getTiempoEstaciones()) {
            double totalIdeal = ti;
            int horas1, minutos1, segundos1;
            horas1 = (int) (totalIdeal / 3600);
            minutos1 = (int) (totalIdeal % 3600 / 60);
            segundos1 = (int) (totalIdeal % 3600 % 60);
            
            
            
            if(segundos1>0){
                segundos1=0;
                minutos1++;
            }
            horaTotal+=horas1;
            minutoTotal+=minutos1;
            
            String tiempoTotal1 = (horas1 + ":" + minutos1 + ":" + segundos1);
            Time tiempoTotalSimulacion1 = Time.valueOf(tiempoTotal1);

            tiemposRedondeo.add(tiempoTotalSimulacion1);

        }
        
        if(minutoTotal>60){
            int a=(minutoTotal/60);
            int b=(minutoTotal%60);
            
            horaTotal+=a;
            minutoTotal=b;
           // minutoTotal=b*60;
            
        }
        String tiempoTotalRedondeadoS = (horaTotal + ":" + minutoTotal + ":" + 0);
        tiempoTotalRedondeado = Time.valueOf(tiempoTotalRedondeadoS);
        System.out.println("Hora Total: "+horaTotal);
            System.out.println("Minuto Total "+minutoTotal);
            
        //CREO LAS CONEXIONES Y OBTENGO LOS VALORES NECESARIOS PARA GUARDAR LA MARCHA TIPO
        GestorLista gl = new GestorLista();
        MarchaTipoJpaController mtjc = new MarchaTipoJpaController(Conex.getEmf());
        List<modelo.entity.MarchaTipo> mt = mtjc.findMarchaTipoEntities();
        int contadorMarchaTipo = 0;
        int idMT = 0;
        String nombreMarchaTipo = request.getParameter("nombre_marcha_tipo");
        Linea l = ljc.findLinea(idLinea);
        String linea = l.getNombreLinea();
        MaterialRodante mr = mrjc.findMaterialRodante(idMaterialRodante);
        String materialRodante = mr.getNombreMaterialRodante();
        Estacion ei = gl.buscarEstacionPorPK(l.getIdLinea(), progEstInicio);
        Estacion ef = gl.buscarEstacionPorPK(l.getIdLinea(), progEstFinal);
        String estacionInicial = ei.getNombreEstacion();
        String estacionFinal = ef.getNombreEstacion();

       List<Integer> listaIdMarchas= mtjc.listarIdMarchas();
//        for (int i = 0; i < mt.size(); i++) {
        for (int i = 0; i < listaIdMarchas.size(); i++) {
            double mtipo = listaIdMarchas.get(i);
            if (mtipo != i) {
                idMT = i;
                break;
            } 
            if (i == listaIdMarchas.size() - 1) {
                idMT = listaIdMarchas.size();
                break;
            }
        }
        Time tiempoMinimoCircuito;
        CircuitoViaJpaController cvjc=new CircuitoViaJpaController(Conex.getEmf());
            List<CircuitoVia> cv=cvjc.buscarCircuitoVia(idLinea);
        if(cv.size()!=0&&cv.size()>=4){
            
            double tiempoMinimoCV=encontrarDistMin(cv, 4);
            
            double totalMinimo= tiempoMinimoCV;
            int horas1, minutos1, segundos1;
            horas1 = (int) (totalMinimo / 3600);
            minutos1 = (int) (totalMinimo % 3600 / 60);
            segundos1 = (int) (totalMinimo % 3600 % 60);
            if(segundos1>0){
                segundos1=0;
                minutos1+=1;
            }
            String tiempoTotal1 = (horas1 + ":" + minutos1 + ":" + segundos1);
            tiempoMinimoCircuito = Time.valueOf(tiempoTotal1);
        
        }else{
            String tiempoTotal1 = ("00:05:00");
            tiempoMinimoCircuito = Time.valueOf(tiempoTotal1);
        }
        
        modelo.entity.MarchaTipo marchaTipo=new modelo.entity.MarchaTipo();
        marchaTipo.setIdMarchaTipo(idMT);
        marchaTipo.setLinea(linea);
        marchaTipo.setMaterialRodante(materialRodante);
        marchaTipo.setNombreEstacionFinal(estacionFinal);
        marchaTipo.setNombreEstacionInicial(estacionInicial);
        marchaTipo.setNombreMarchaTipo(nombreMarchaTipo);
        marchaTipo.setTiempoTotal(tiempoTotalRedondeado);
        marchaTipo.setVelocidadMarchaTipo(velocidadMarcha);
        marchaTipo.setCargaMarcha(cargaMarcha);
        marchaTipo.setSistemaProteccion(atp);
        marchaTipo.setProgEstacionFinal(progEstFinal);
        marchaTipo.setProgEstacionInicial(progEstInicio);
        
       
//            String tiempoMinimo = ("00:04:00");
//            Time tm = Time.valueOf(tiempoMinimo);
        marchaTipo.setTiempoMinimo(tiempoMinimoCircuito);
        if(progEstInicio<progEstFinal){
        marchaTipo.setSentido(true);
        }else{
        marchaTipo.setSentido(false);
        }
        mtjc.create(marchaTipo);
        
        //Guardo Los tiempos en las estaciones de la marcha tipo creada
        TiempoEstacionMarchaTipo temt= new modelo.entity.TiempoEstacionMarchaTipo();
        TiempoEstacionMarchaTipoJpaController temtjc=new TiempoEstacionMarchaTipoJpaController(Conex.getEmf());
        
        for (int i = 0; i < tiemposReales.size(); i++) {
            boolean parada=cmt.getParada().get(i);
            if(i==tiemposReales.size()-1){
            parada=false;
            }
            System.out.println("******* Tiempos *******");
            temt.setMarchaTipo(marchaTipo);
            temt.setNombreEstacion(estaciones.get(i).getNombreEstacion());
            temt.setTiempoIdeal(tiemposIdeales.get(i));
            System.out.println(temt.getTiempoIdeal());
            temt.setTiempoMargen(tiemposMargen.get(i));
            System.out.println(temt.getTiempoMargen());
            temt.setTiempoReal(tiemposReales.get(i));
            System.out.println(temt.getTiempoReal());
            temt.setTiempoRedondeo(tiemposRedondeo.get(i));
            System.out.println(temt.getTiempoRedondeo());
            temt.setTiempoAdicional(Time.valueOf("00:00:00"));
            System.out.println(temt.getTiempoAdicional());
            temt.setTiempoAsimilado(tiemposRedondeo.get(i));
            System.out.println(temt.getTiempoAsimilado());
            temt.setParada(parada);
            
            System.out.println(tiemposPerdidoXRest.get(i));
            temt.setTiempoPerdidoRest(tiemposPerdidoXRest.get(i));
            double pkEstacion=estaciones.get(i).getEstacionPK().getIdPkEstacion();
            int idMarchaTipo=idMT;
            TiempoEstacionMarchaTipoPK temtpk=new TiempoEstacionMarchaTipoPK(pkEstacion, idMarchaTipo);
            temt.setTiempoEstacionMarchaTipoPK(temtpk);
            temtjc.create(temt);
            parada1.add(parada);
            
            
        }
        
        //GUARDO LOS VALORES PARA LOS GRAFICOS DE LA MARCHA TIPO
        
         int idGMT=0;
          GraficoMarchaTipo gmt=new GraficoMarchaTipo();
            GraficoMarchaTipoJpaController gmtjc=new GraficoMarchaTipoJpaController(Conex.getEmf());
        for (int i = 0; i < cmt.getCambiosProgresiva().size(); i++) {
           
            List<modelo.entity.GraficoMarchaTipo> gmtl = gmtjc.findGraficoMarchaTipoEntities();
            gmt.setMarchaTipo(marchaTipo);
            gmt.setProgresiva(cmt.getCambiosProgresiva().get(i));
            gmt.setVelocidad(cmt.getCambiosVelocidad().get(i));
            gmt.setTiempo(tiemposProg.get(i));
            
             
           GraficoMarchaTipoPK gmtpk=new GraficoMarchaTipoPK(idGMT, idMT);
           gmt.setGraficoMarchaTipoPK(gmtpk);
           gmtjc.create(gmt);
            idGMT++;   
            
        }
        System.out.println(" aaaaaaaaaaaaaaaaaaaaa");
        
        //GUARDO LOS VALORES DE RESTRICCIONES EN LA MARCHA TIPO SI EXISTEN
        RestriccionMarchaTipo rmt=new RestriccionMarchaTipo();
        RestriccionMarchaTipoJpaController rmtjc=new RestriccionMarchaTipoJpaController(Conex.getEmf());
        
        if(restriccionesMarchaTipo!=null){
            for (int i = 0; i <restriccionesMarchaTipo.size() ; i++) {
                rmt.setMarchaTipo(marchaTipo);
                rmt.setPkFinalResMt(restriccionesMarchaTipo.get(i).getProgFinal());
                rmt.setVelocidadMaxAscendente(restriccionesMarchaTipo.get(i).getVelocidadMaxAscendente());
                rmt.setVelocidadMaxDescendente(restriccionesMarchaTipo.get(i).getVelocidadMaxDescendente());
                RestriccionMarchaTipoPK rmtpk=new RestriccionMarchaTipoPK(restriccionesMarchaTipo.get(i).getProgInicio(),
                        idMT);
                rmt.setRestriccionMarchaTipoPK(rmtpk);
                rmtjc.create(rmt);
                
                
            }
        
        
        }
        
        //OBTENGO LAS PROGRESIVAS QUE IRAN AL GRAFICO VELOCIDAD VS DISTANCIA
        List<String> arr2 = new ArrayList<>();
        for (int i = 0; i < cmt.getCambiosProgresiva().size(); i++) {
            String prog = cmt.getCambiosProgresiva().get(i).toString();
            String vel = cmt.getCambiosVelocidad().get(i).toString();
            String par = "[" + prog + "," + vel + "]";
            arr2.add(par);
        }
        System.out.println(" aaaaaaaaaaaaaaaaaaaaa");
        PrintWriter out = response.getWriter();
     //out.println("Tiempo total: "+horas+" horas "+ minutos+ " minutos \n"+ segundos+" segundos\n Velocidad: "+velocidadMarcha+" Simulacion Finalizada");
        //out.println("Tiempo total: "+tiempoTotalSimulacion+" Velocidad: "+velocidadMarcha+" Simulacion Finalizada");

        //GENERO LAS TABLAS QUE IRAN COMO RESPUESTA AL JSP INGRESOMARCHATIPO
        
        out.print("<table class='tablas'>");
        out.print("<tr>");
        out.print("<td>" + "Estación" + "</td>");
        out.print("<td>" + "Tiempo Ideal" + "</td>");
        out.print("<td>" + "Tiempo Margen" + "</td>");
        out.print("<td>" + "Tiempo Real" + "</td>");
        out.print("<td>" + "Tiempo Redondeado" + "</td>");
        out.print("<td>" + "Tiempo Adicional" + "</td>");
        out.print("<td>" + "Tiempo Asimilado" + "</td>");
        out.print("<td>" + "Tiempo Perdido Rest" + "</td>");
        out.print("<td>" + "Parada" + "</td>");
        out.print("</tr>");
        
        for (int i = 0; i < cmt.getTiempoEstaciones().size(); i++) {
            out.print("<tr>");
            out.print("<td>" + estaciones.get(i).getNombreEstacion() + "</td>");
            out.print("<td>" + tiemposIdeales.get(i) + "</td>");
            out.print("<td>" + tiemposMargen.get(i) + "</td>");
            out.print("<td>" + tiemposReales.get(i) + "</td>");
            out.print("<td>" + tiemposRedondeo.get(i) + "</td>");
            out.print("<td>" + "00:00:00" + "</td>");
            out.print("<td>" + tiemposRedondeo.get(i) + "</td>");
            out.print("<td>" + tiemposPerdidoXRest.get(i)+ "</td>");
            if(parada1.get(i)==true){
             out.print("<td>" +"Parada" + "</td>");
            }else{
                if(i==0){
                out.print("<td>" +"Salida" + "</td>");
                }else if(i==cmt.getTiempoEstaciones().size()-1){
                    out.print("<td>" +"Llegada" + "</td>");
                }else{
             out.print("<td>" +"Paso" + "</td>");}
            }
           
            out.print("</tr>");

        }

        out.print("<table class='tablas' style='margin-top:10px;'>");
        out.print("<tr>");
        out.print("<td>" + "Tiempo Total Ideal" + "</td>");
        out.print("<td>" + "Tiempo Total Real" + "</td>");
        out.print("<td>" + "Tiempo Total Asimilado" + "</td>");
        out.print("<td>" + "Velocidad de Marcha (Km/h)" + "</td>");
        out.print("<td>" + "Carga (t)" + "</td>");
        out.print("<td>" + "Sist. Protección" + "</td>");
        out.print("<td>" + "Tiempo Perdido Rest." + "</td>");
        out.print("<td>" + "Tiempo Mínimo" + "</td>");
        out.print("</tr>");
        out.print("<tr>");
        out.print("<td>" + tiempoTotalSimulacionIdeal + "</td>");
        out.print("<td>" + tiempoTotalSimulacion + "</td>");
        out.print("<td>" + tiempoTotalRedondeado + "</td>");
        out.print("<td>" + velocidadMarcha + "</td>");
        out.print("<td>" + cargaMarcha + "</td>");
        
        
        if(atp==true){
        out.print("<td>" + "ACT." + "</td>");
        }else{
        out.print("<td>" + "NO ACT." + "</td>");
        }
        out.print("<td>" + tiempoTotalSimulacionR + "</td>");
        out.print("<td>" + tiempoMinimoCircuito + "</td>");
        out.print("</tr>");

        out.print("</table>");

//                 
        out.print("<table class='tablas' style='margin-top:10px;' id='preguntaMT'>");
        out.print("<tr>");
        out.print("<td>" + "¿Desea Guardar La Marcha Tipo?" + "</td>");
        out.print("</tr>");
        out.print("</table>");
        out.print("<div class='contenedorBoton'><input type='button' value='Guardar' id='agregarMT' onclick='agregarMarchaTipo()' class='botonContinuar'></div>");
        out.print("<div class='contenedorBoton'><input type='button' value='No Guardar' id='eliminarMT' onclick='eliminoMarchaTipo("+idMT+")' class='botonContinuar'></div>");
        out.print("<input type='hidden' value='"+idMT+"'id='id_marcha_tipo'>");
        out.print("<input type='hidden' id='ocultar' value='1' >");
                
        //ENVIO LAS VARIABLES NECESARIAS PARA GENERAR LOS GRAFICOS DE LA MARCHA TIPO
        out.println("<script type='text/javascript'>");
        out.println("var arr2=" + arr2 + ";");
        out.println("var tiempos=" + tiemposProg + ";");
        out.println("</script>");

    }

    private void editar(HttpServletRequest request, HttpServletResponse response) throws IOException {
         PrintWriter out= response.getWriter();
         HttpSession session = request.getSession();
        Usuario usr = (Usuario) session.getAttribute("usuario");
        
        if(usr.getNivel()>2){
        out.print("NO TIENES PERMISO PARA REALIZAR ESTA ACCIÓN");
        return;
        }
         
         try {
            int idMarchaTipo=Integer.parseInt(request.getParameter("idMarchaTipo"));
            int minutosAdicionales=Integer.parseInt(request.getParameter("minutoAdicional"));
            double idPkEstacion=Double.parseDouble(request.getParameter("idPkEstacion"));
            TiempoEstacionMarchaTipoJpaController temtjc=new TiempoEstacionMarchaTipoJpaController(Conex.getEmf());
            TiempoEstacionMarchaTipo temt= temtjc.buscarPorPKTiempoEstacion(idMarchaTipo, idPkEstacion);
             Time ad=new Time(temt.getTiempoAdicional().getTime());
             System.out.println(ad);
             Time asimilado=new Time(temt.getTiempoAsimilado().getTime());
             temt.setTiempoAsimilado(sumarTiempo(asimilado, minutosAdicionales));
            temt.setTiempoAdicional(sumarTiempo(ad,minutosAdicionales ));
            Time cero=Time.valueOf("00:00:00");
            if(temt.getTiempoAsimilado().before(temt.getTiempoRedondeo())){
            out.print("El Tiempo No Puede Ser Menor Que El Tiempo Redondeado");
            return;
            }
            
            temtjc.edit(temt);
            out.print("Tiempos Editados Satisfactoriamente");
        } catch (Exception e) {
            out.print("Ha Ocurrido Una Excepción");
            e.printStackTrace();
        }

    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws IllegalOrphanException, NonexistentEntityException, IOException {
        PrintWriter out= response.getWriter();
        HttpSession session = request.getSession();
        Usuario usr = (Usuario) session.getAttribute("usuario");
        
        if(usr.getNivel()>2){
        out.print("NO TIENES PERMISO PARA REALIZAR ESTA ACCIÓN");
        return;
        }
        try{
            
            
            
        int idMarchaTipo=Integer.parseInt(request.getParameter("idMarchaTipo"));
        
        MarchaTipoJpaController mtjc=new MarchaTipoJpaController(Conex.getEmf());
        
        mtjc.destroy(idMarchaTipo);
        out.print("Marcha Tipo Eliminada Satisfactoriamente");
        }catch(Exception e){
            e.printStackTrace();
            out.print("Ha Ocurrido Un Error");
        }

    }

    private void generarVariables(HttpServletRequest request, HttpServletResponse response) throws IOException {
       int idMarchaTipo=Integer.parseInt(request.getParameter("idMarchaTipo"));
       MarchaTipoJpaController mtjc=new MarchaTipoJpaController(Conex.getEmf());
       modelo.entity.MarchaTipo mt=mtjc.findMarchaTipo(idMarchaTipo);
       GraficoMarchaTipoJpaController gmtjc=new GraficoMarchaTipoJpaController(Conex.getEmf());
       List<GraficoMarchaTipo> gmtl= gmtjc.buscarPorIdMarchaTipo(idMarchaTipo);
       List<String> velocidadProgresiva=new ArrayList<>();
        
       List<String> tiempos=new ArrayList<>();
        for (int i = 0; i < gmtl.size(); i++) {
            String vel = ""+gmtl.get(i).getVelocidad();
            String prog = ""+gmtl.get(i).getProgresiva();
            
            String par = "[" + prog + "," + vel + "]";
            velocidadProgresiva.add(par);
            tiempos.add(gmtl.get(i).getTiempo());
//            System.out.println(prog);
        }
        
        PrintWriter out = response.getWriter();
        
        out.println("<script type='text/javascript'>");
        out.println("var arr2=" + velocidadProgresiva + ";");
        out.println("var tiempos=" + tiempos+ ";");
        out.print("var sentido1=" + mt.isSentido()+ ";");
        out.println("</script>");
        
    }
    
    public java.sql.Time sumarTiempo(java.sql.Time time, int minutos){
        Calendar cal=new GregorianCalendar();
        cal.setTimeInMillis(time.getTime());
        cal.add(cal.MINUTE, minutos);
        
        return new Time(cal.getTimeInMillis());
    
    
    }
    
     private double encontrarDistMin(List<CircuitoVia> circuitos, int circuitosOcupados) {
        CircuitoVia cvArr[] = new CircuitoVia[circuitosOcupados];
        double distTotal = 0;
        double distActual = 0;
        double velMarchaTipo = 100;
        System.out.println("Cant circuitos de via: " + circuitos.size());
//        for (int i = 0; i < circuitos.size()-2; i++) {
        for (int i = 0; i < circuitos.size() - (circuitosOcupados - 1); i++) {
            for (int j = 0; j < cvArr.length; j++) {
                cvArr[j] = circuitos.get(i + j);
            }
            for (int j = 0; j < cvArr.length; j++) {
                distActual += cvArr[j].getPkFinalCircuito() - cvArr[j].getCircuitoViaPK().getIdPkInicialCircuito();
            }
            System.out.println("Distancia de la suma de los ultimos circuitos: " + distActual);
            if (distActual > distTotal) {
                distTotal = distActual;
                System.out.println("Nueva distancia para calcular: " + distTotal);
            }
            distActual = 0;
            System.out.println("Vuelta Nยบ " + i);
        }
        double tiempoMin = distTotal / (velMarchaTipo / 3.6);
        System.out.println("Tiempo minimo: " + tiempoMin);
        double tiempoMar = distTotal * 300 / 100000;
        System.out.println("Tiempo margen: " + tiempoMar);
        System.out.println("Tiempo recomendado: " + (tiempoMar + tiempoMin) + " s");
        System.out.println("Tiempo recomendado: " + ((tiempoMar + tiempoMin) / 60) + " min");
        return ((tiempoMar + tiempoMin) );
    }

    private void editarTiempoMinimo(HttpServletRequest request, HttpServletResponse response) throws IOException {
         PrintWriter out= response.getWriter();
         HttpSession session = request.getSession();
        Usuario usr = (Usuario) session.getAttribute("usuario");
        
        if(usr.getNivel()>2){
        out.print("NO TIENES PERMISO PARA REALIZAR ESTA ACCIÓN");
        return;
        }
         
         try {
            int idMarchaTipo=Integer.parseInt(request.getParameter("idMarchaTipo"));
            int minutosAdicionales=Integer.parseInt(request.getParameter("minutoAdicional"));
            MarchaTipoJpaController mtjc=new MarchaTipoJpaController(Conex.getEmf());
            modelo.entity.MarchaTipo mt=mtjc.bucarMarchaTipo(idMarchaTipo);
            
             Time ad=new Time(mt.getTiempoMinimo().getTime());
             System.out.println(ad);
             mt.setTiempoMinimo(sumarTiempo(ad, minutosAdicionales));
             String tiempoTotal1 = ("00:01:00");
            Time uno = Time.valueOf(tiempoTotal1);
             
            if(mt.getTiempoMinimo().before(uno)){
            out.print("El Tiempo No Puede Ser Menor A Un (1) Minuto");
            return;
            }
            
            mtjc.edit(mt);
            out.print("Tiempos Editados Satisfactoriamente");
        } catch (Exception e) {
            out.print("Ha Ocurrido Una Excepción");
            e.printStackTrace();
        }
        
    }
  
}
