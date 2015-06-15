/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

//import controlador.EstacionJpaController;
//import controlador.LineaJpaController;
import modelo.entity.Estacion;
import modelo.entity.EstacionPK;
import modelo.entity.Linea;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Conex;
import modelo.GestorLista;
import modelo.controlBD.EstacionJpaController;
import modelo.controlBD.LineaJpaController;
import modelo.entity.Usuario;

/**
 *
 * @author Kelvins Insua
 */
@WebServlet(name = "AdministrarEstacion", urlPatterns = {"/AdministrarEstacion"})
public class AdministrarEstacion extends HttpServlet {

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
       String accion= request.getParameter("accion");
        if(accion!=null){
            switch(accion){
                case "Agregar":
                    agregar(request,response);
                    break;
                case "Editar":
                   editar(request,response);
                    break;
                case "Eliminar":
                    eliminar(request,response);
                    break;
                case "Estaciones Intermedias":
                    cargarEstacionesIntermediasMT(request,response);
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

    private void agregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EstacionJpaController ejc=new EstacionJpaController(Conex.getEmf());
        Estacion e=new Estacion();
        LineaJpaController ljc=new LineaJpaController(Conex.getEmf());
        PrintWriter salida=response.getWriter();
        HttpSession session = request.getSession();
        Usuario usr = (Usuario) session.getAttribute("usuario");
        
        if(usr.getNivel()>2){
        salida.print("NO TIENES PERMISO PARA REALIZAR ESTA ACCIÓN");
        return;
        }
        try{
            int idLinea=Integer.parseInt(request.getParameter("select_linea"));
            String nombre=request.getParameter("nombre_estacion");
            String abrev=request.getParameter("abrev_estacion");
            double idPkEstacion=Double.parseDouble(request.getParameter("id_pk_estacion"));
            
            
            EstacionPK epk=new EstacionPK(idLinea, idPkEstacion);
            Linea linea=ljc.findLinea(idLinea);
            
            
            if(idPkEstacion<linea.getPkInicial()||idPkEstacion>linea.getPkFinal()){
                salida.print("La Estación no ha sido creada, revise el punto kilométrico de la estación");
                return;
            }
            e.setEstacionPK(epk);
            e.setLinea(linea);
            e.setNombreEstacion(nombre);
            e.setAbrevEstacion(abrev);
            ejc.create(e);
            salida.print("La Estación "+e.getNombreEstacion()+
                    " ha sido creada satisfactoriamente");
            
        }catch(Exception ex){
            salida.print("Uno de los Valores Ingresados No es Correcto");
       
        }
        
    }

    private void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EstacionJpaController ejc=new EstacionJpaController(Conex.getEmf());
        Estacion e=new Estacion();
        LineaJpaController ljc=new LineaJpaController(Conex.getEmf());
        PrintWriter salida=response.getWriter();
        HttpSession session = request.getSession();
        Usuario usr = (Usuario) session.getAttribute("usuario");
        
        if(usr.getNivel()>2){
        salida.print("NO TIENES PERMISO PARA REALIZAR ESTA ACCIÓN");
        return;
        }
        try{
            int idLinea=Integer.parseInt(request.getParameter("id_linea"));
            String nombre=request.getParameter("nombre_estacion");
            String abrev=request.getParameter("abrev_estacion");
            double idPkEstacion=Double.parseDouble(request.getParameter("id_pk_estacion"));
            EstacionPK epk=new EstacionPK(idLinea, idPkEstacion);
            Linea linea=ljc.findLinea(idLinea);
            e.setEstacionPK(epk);
             if(idPkEstacion<linea.getPkInicial()||idPkEstacion>linea.getPkFinal()){
                salida.print("La Estación no ha sido creada, revise el punto kilométrico de la estación");
                return;
            }
            e.setLinea(linea);
            e.setNombreEstacion(nombre);
            e.setAbrevEstacion(abrev);
            ejc.edit(e);
            salida.print("La Estación "+e.getNombreEstacion()+
                    " ha sido editada satisfactoriamente");
            
        
        }catch(Exception ex){
            salida.print("Uno de los Valores Ingresados No es Correcto");        
        }
       
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EstacionJpaController ejc=new EstacionJpaController(Conex.getEmf());
        Estacion e=new Estacion();
        LineaJpaController ljc=new LineaJpaController(Conex.getEmf());
        PrintWriter salida=response.getWriter();
        HttpSession session = request.getSession();
        Usuario usr = (Usuario) session.getAttribute("usuario");
        
        if(usr.getNivel()>2){
        salida.print("NO TIENES PERMISO PARA REALIZAR ESTA ACCIÓN");
        return;
        }
        try{
            int idLinea=Integer.parseInt(request.getParameter("id_linea"));
            double idPkEstacion=Double.parseDouble(request.getParameter("id_pk_estacion"));
         
            EstacionPK epk=new EstacionPK(idLinea, idPkEstacion);
            
            e.setEstacionPK(epk);
            
            
            ejc.destroy(epk);
             salida.print("La Estación "+
                    " ha sido eliminada satisfactoriamente");
            
        
        }catch(Exception ex){
             salida.print("Uno de los Valores Ingresados No es Correcto");
        
        }
       
     
    }

    private void cargarEstacionesIntermediasMT(HttpServletRequest request, HttpServletResponse response) throws IOException {
        double estacionInicial=Double.parseDouble(request.getParameter("progEstacionInicial"));
        double estacionFinal=Double.parseDouble(request.getParameter("progEstacionFinal"));
        int idLinea=Integer.parseInt(request.getParameter("idLinea"));
        GestorLista gl=new GestorLista();
        List<Estacion> estaciones;
        PrintWriter salida=response.getWriter();
        if(estacionInicial<estacionFinal){
           estaciones= gl.seleccionarParadaEstacionAsc(estacionInicial, estacionFinal, idLinea);
            System.out.println("Ascendente");
        }else{
        estaciones= gl.seleccionarParadaEstacionDesc(estacionInicial, estacionFinal, idLinea);
            System.out.println("Descendente");
        }
        
        if(estaciones.size()>0){
        salida.print("Seleccione Estaciones Con Parada:");
        }
        System.out.println(estaciones);
        
        for (int i = 0; i < estaciones.size(); i++) {
            salida.print("<div><input type='checkbox' class='incluirEstacion' value='"+estaciones.get(i).getEstacionPK().getIdPkEstacion()+"'> "+estaciones.get(i).getNombreEstacion()+" </div>");
            
        }
    }

}
