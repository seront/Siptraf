/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Conex;
import modelo.controlBD.UsuarioJpaController;
import modelo.entity.Usuario;

/**
 *
 * @author seront
 */
@WebServlet(name = "SessionAdmin", urlPatterns = {"/SessionAdmin"})
public class SessionAdmin extends HttpServlet {

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
        String accion=request.getParameter("accion");
        if(accion!=null){
            switch(accion){
                case "Iniciar":
                    iniciarSesion(request,response);
                    break;
                case "Cerrar":
                    cerrarSesion(request, response);
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

    private void iniciarSesion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("entra");
        HttpSession session=request.getSession();
        PrintWriter out = response.getWriter();

        try {
            Usuario usr=(Usuario) session.getAttribute("usuario");
            if(usr==null){
            usr=new Usuario();
                
            usr.setIdUsuario(request.getParameter("idUsuario"));
            usr.setClave(request.getParameter("clave"));
            System.out.println(usr.getIdUsuario()+" "+usr.getClave());
                UsuarioJpaController ujc= new UsuarioJpaController(Conex.getEmf());
                System.out.println("llega");
                usr=ujc.validarUsuario(usr);
                if (usr!=null) {
                    out.print("Bienvenido "+usr.toString());
                    session.setAttribute("usuario", usr);
                    session.setAttribute("nivel", usr.getNivel());
//                    response.sendRedirect("index.jsp");
                    
                } else {
                   out.print("USUARIO Y CLAVE NO COINCIDEN");
//                    response.sendError(1, "USUARIO Y CLAVE NO COINCIDEN");
//                    response.sendRedirect("index.jsp");
             
                }
            }else{
                out.print("YA EXISTE OTRA SESION ABIERTA POR "+usr.toString());
             
            }
        } catch (Exception e) {
//           response.sendError(1, "USUARIO Y CLAVE NO COINCIDEN");
//           response.sendRedirect("index.jsp");
           out.print("USUARIO Y CLAVE NO COINCIDEN");
            e.printStackTrace();
        }
    }

    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession();
        PrintWriter out = response.getWriter();
        try {
            Usuario usr=(Usuario) session.getAttribute("usuario");
            if(usr!=null){
                System.out.println("CIERRA");
                out.print("Sesión cerrada, hasta luego "+usr.toString());
            session.invalidate();
            }else{
                out.print( "No se pudo cerrar la sesión");
            }
        } catch (Exception e) {
             out.print( "No se pudo cerrar la sesión");
            e.printStackTrace();
        }
    }

}
