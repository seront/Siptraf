/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Conex;
import modelo.controlBD.UsuarioJpaController;
import modelo.controlBD.exceptions.PreexistingEntityException;
import modelo.entity.Usuario;

/**
 *
 * @author Kelvins Insua
 */
@WebServlet(name = "AdministrarUsuario", urlPatterns = {"/AdministrarUsuario"})
public class AdministrarUsuario extends HttpServlet {

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
                case "Cambiar Contraseña":
                    cambiarContraseña(request,response);
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AdministrarUsuario.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AdministrarUsuario.class.getName()).log(Level.SEVERE, null, ex);
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

    private void agregar(HttpServletRequest request, HttpServletResponse response) throws Exception {
       PrintWriter salida=response.getWriter();
        try{
        String idUsuario=request.getParameter("idUsuario");
       String nombre=request.getParameter("nombreUsuario");
       String apellido=request.getParameter("apellidoUsuario");
       String contraseña=request.getParameter("contraseñaUsuario");
       String contraseña2=request.getParameter("confirmarContraseña");
       int nivel=Integer.parseInt(request.getParameter("nivelUsuario"));
        UsuarioJpaController ujc=new UsuarioJpaController(Conex.getEmf());
       Usuario u=new Usuario();
       
       if(!contraseña.equals(contraseña2)){
       salida.print("Las contraseñas no coinciden");
       return;
       }
       u.setNombre(nombre);
       u.setApellido(apellido);
       u.setIdUsuario(idUsuario);
       u.setClave(contraseña);
       u.setNivel(nivel);
       
       ujc.create(u);
       salida.print("Usuario "+u.getNombre()+" creado satisfactoriamente");
        }catch(PreexistingEntityException ex){
            salida.print("Usuario Ya Existe");
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        salida.print("ha ocurrido un error");
        }
       
    }

    private void editar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter salida=response.getWriter();
        try{
       String nombre=request.getParameter("nombreUsuario");
       String apellido=request.getParameter("apellidoUsuario");
       String contraseña=request.getParameter("contraseñaUsuario");
       int nivel=Integer.parseInt(request.getParameter("nivelUsuario"));
        UsuarioJpaController ujc=new UsuarioJpaController(Conex.getEmf());
        HttpSession session = request.getSession();
        Usuario u= ujc.findUsuario("kelvins08");
       
       u.setNombre(nombre);
       u.setApellido(apellido);
       u.setClave(contraseña);
       u.setNivel(nivel);
       
       ujc.edit(u);
       salida.print("Usuario "+u.getNombre()+" editado satisfactoriamente");
        }catch(Exception e){
        salida.print("ha ocurrido un error");
        e.printStackTrace();
        }
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter salida=response.getWriter();
        try{
        String idUsuario=request.getParameter("idUsuario");
        UsuarioJpaController ujc=new UsuarioJpaController(Conex.getEmf());     
       
       
       ujc.destroy(idUsuario);
       salida.print("Usuario  eliminado satisfactoriamente");
        }catch(Exception e){
        salida.print("ha ocurrido un error");
        }
    }

    private void cambiarContraseña(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
        PrintWriter salida=response.getWriter();
        try{
        String contraseñaVieja=request.getParameter("viejaContraseña");
        String contraseñaNueva=request.getParameter("nuevaContraseña");
        String contraseñaNueva2=request.getParameter("nuevaContraseña2");
        HttpSession session = request.getSession();
        Usuario usr = (Usuario) session.getAttribute("usuario");
        UsuarioJpaController ujc=new UsuarioJpaController(Conex.getEmf());
        if(!contraseñaNueva.equals(contraseñaNueva2)){
            salida.print("Las Contraseñas NO coinciden");
            return;
        }
        if(!usr.getClave().equals(contraseñaVieja)){
        salida.print("Las Contraseñas NO coinciden");
            return;
        }
        usr.setClave(contraseñaNueva);
        ujc.edit(usr);
        salida.print("Clave Cambiada Exitosamente");
        }catch(Exception e){
            salida.print("Ha Ocurrido Un Error");
            e.printStackTrace();
        }
    }

}
