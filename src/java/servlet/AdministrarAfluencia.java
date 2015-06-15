/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Conex;
import modelo.GestorLista;
import modelo.controlBD.AfluenciaJpaController;
import modelo.controlBD.EstacionJpaController;
import modelo.controlBD.LineaJpaController;
import modelo.controlBD.exceptions.PreexistingEntityException;
import modelo.entity.Afluencia;
import modelo.entity.Estacion;
import modelo.entity.Linea;

/**
 *
 * @author seront
 */
@WebServlet(name = "AdministrarAfluencia", urlPatterns = {"/AdministrarAfluencia"})
public class AdministrarAfluencia extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        System.out.println(request.getParameter("accion"));
        switch (request.getParameter("accion")) {
            case "crearAfluencia":
                crearAfluencia(request, response);
                break;
            case "cargarEstaciones":
                cargarEstaciones(request, response);
                break;
            case "cargarTablaAfluencia":
                cargarTablaAfluencia(request, response);
                break;
            case "cargarAfluenciaEstacion":
                cargarAfluenciaEstacion(request, response);
                break;
            default:
                throw new AssertionError();
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
        processRequest(request, response);
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
        processRequest(request, response);
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

    private void cargarEstaciones(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter salida = null;
        try {
            int idLinea = Integer.parseInt(request.getParameter("id_linea"));
            GestorLista gl = new GestorLista();
            List listaSalida = gl.buscarEstacion(idLinea);
            for (int i = 0; i < listaSalida.size(); i++) {
                Estacion estacion = (Estacion) listaSalida.get(i);
                estacion.setLinea(null);
            }

            Gson gson = new Gson();
            String json = gson.toJson(listaSalida);
            salida = response.getWriter();
            salida.write(json);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(AdministrarAfluencia.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (salida != null) {
                salida.close();
            }
        }
    }

    private void crearAfluencia(HttpServletRequest request, HttpServletResponse response) {
        AfluenciaJpaController ajc = new AfluenciaJpaController(Conex.getEmf());

        String json = request.getParameter("afluencia");
        System.out.println(json);
        Gson gson = new Gson();
        Afluencia afluencia = gson.fromJson(json, Afluencia.class);
        System.out.println(afluencia);
        LineaJpaController ljc = new LineaJpaController(Conex.getEmf());
        Linea linea = ljc.findLinea(afluencia.getAfluenciaPK().getIdLinea());
        afluencia.setLinea(linea);
        try {
            ajc.create(afluencia);
        } catch (PreexistingEntityException ex) {
//            ex.printStackTrace();
            System.out.println("Ya existe la afluencia, se editara ");
            try {
//                afluencia.setLinea(null);
                ajc.edit(afluencia);
            } catch (Exception ex1) {
                ex1.printStackTrace();
                Logger.getLogger(AdministrarAfluencia.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(AdministrarAfluencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarTablaAfluencia(HttpServletRequest request, HttpServletResponse response) {
        int idLinea = Integer.parseInt(request.getParameter("idLinea"));
        double idPkEstacion = Double.parseDouble(request.getParameter("idPkEstacion"));
        double fechaMinima = Double.parseDouble(request.getParameter("fechaMinima"));
        // Fecha del final de la semana equivalente al domingo a las 11:59
        double fechaMaxima = Double.parseDouble(request.getParameter("fechaMaxima"));
        DateFormat dt = DateFormat.getDateTimeInstance();
        System.out.println("FEcha minima y maxima");
        System.out.println(dt.format(new Date((long) fechaMinima)));
        System.out.println(dt.format(new Date((long) fechaMaxima)));

        List semana = new ArrayList();
        AfluenciaJpaController ajc = new AfluenciaJpaController(Conex.getEmf());
        // fecha del final del dia
        double fechaMax = fechaMinima + (((23 * 60)) * 60 * 1000) + (59 * 60 * 1000);
        for (int j = 0; j < 7; j++) {
            System.out.println(dt.format(new Date((long) fechaMinima)));
            System.out.println(dt.format(new Date((long) fechaMax)));
            List dia = ajc.afluenciaDiaria(idLinea, idPkEstacion, fechaMinima, fechaMax);
            for (Object dia1 : dia) {
                Afluencia aflu = (Afluencia) dia1;
                aflu.setLinea(null);
            }
            if (dia.size() > 0) {
                semana.add(dia);
            }
            fechaMinima += (24 * 60 * 60 * 1000);
            fechaMax = fechaMinima + (23 * 60 * 60 * 1000) + (59 * 60 * 1000);
        }

        Gson gson = new Gson();
        String json = gson.toJson(semana);
        System.out.println("Tabla de afluencias");
        System.out.println(json);
        PrintWriter salida;
        try {
            salida = response.getWriter();
            salida.write(json);
            salida.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(AdministrarAfluencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Carga las afluencias para determinada estacion en un periodo de 1 dia
    private void cargarAfluenciaEstacion(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Cargar afluencia estacion");
        int idLinea = Integer.parseInt(request.getParameter("idLinea"));
//        double idPkEstacion = Double.parseDouble(request.getParameter("idPkEstacion"));
        double fechaMinima = Double.parseDouble(request.getParameter("fechaMinima"));
        // Fecha del final del dia
        double fechaMaxima = Double.parseDouble(request.getParameter("fechaMaxima"));
        AfluenciaJpaController ajc = new AfluenciaJpaController(Conex.getEmf());
        EstacionJpaController ejc= new EstacionJpaController(Conex.getEmf());
        List<Estacion> estaciones= ejc.buscarEstacion(idLinea);
        List afluenciasEstacion= new ArrayList();
        for (Estacion estacion : estaciones) {
            List dia = ajc.afluenciaDiaria(idLinea, estacion.getEstacionPK().getIdPkEstacion(), fechaMinima, fechaMaxima);
            for (Object dia1 : dia) {
                Afluencia aflu = (Afluencia) dia1;
                aflu.setLinea(null);
            }
            afluenciasEstacion.add(dia);
        }
        
        Gson gson = new Gson();
        String json = gson.toJson(afluenciasEstacion);
        PrintWriter salida;
        try {
            salida = response.getWriter();
            salida.write(json);
            salida.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(AdministrarAfluencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
