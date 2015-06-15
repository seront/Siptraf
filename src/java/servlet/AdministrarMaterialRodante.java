/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.entity.MaterialRodante;
import modelo.Conex;
import modelo.controlBD.MaterialRodanteJpaController;
import modelo.controlBD.exceptions.IllegalOrphanException;
import modelo.controlBD.exceptions.NonexistentEntityException;
import modelo.controlBD.exceptions.PreexistingEntityException;
import modelo.entity.Usuario;

/**
 *
 * @author Kelvins Insua
 */
@WebServlet(name = "AdministrarMaterialRodante", urlPatterns = {"/AdministrarMaterialRodante"})
public class AdministrarMaterialRodante extends HttpServlet {

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
            Logger.getLogger(AdministrarMaterialRodante.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AdministrarMaterialRodante.class.getName()).log(Level.SEVERE, null, ex);
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
        HttpSession session = request.getSession();
        Usuario usr = (Usuario) session.getAttribute("usuario");
        
        if(usr.getNivel()>1){
        salida.print("NO TIENES PERMISO PARA REALIZAR ESTA ACCIÓN");
        return;
        }
        try{
        MaterialRodanteJpaController mrjc=new MaterialRodanteJpaController(Conex.getEmf());
        String nombre=request.getParameter("nombre");
        String tipo=request.getParameter("tipo");
        String subTipo=request.getParameter("sub_tipo");
        String altoXAnchoRemolque=request.getParameter("alto_x_ancho_remolque");
        String altoXAnchoMotriz=request.getParameter("alto_x_ancho_motriz");
        String frenadoDescripcion=request.getParameter("frenado_descripcion");
        String presionTrabajo=request.getParameter("presion_trabajo");
        int numeroVagones=Integer.parseInt(request.getParameter("numero_vagones"));
        int numeroEjes=Integer.parseInt(request.getParameter("numero_ejes"));
        int capacidadPasajeros=Integer.parseInt(request.getParameter("capacidad_pasajeros"));
        double velocidadDiseño=Double.parseDouble(request.getParameter("velocidad_diseño"));
        double velocidadOperacion=Double.parseDouble(request.getParameter("velocidad_operacion"));
        double masa=Double.parseDouble(request.getParameter("masa"));
        double aceleracionMaxima=Double.parseDouble(request.getParameter("aceleracion_maxima"));
        double desaceleracionMaxima=Double.parseDouble(request.getParameter("desaceleracion_maxima"));
        double cargaMaxima=Double.parseDouble(request.getParameter("carga_maxima"));
        double desaceleracionEmergencia=Double.parseDouble(request.getParameter("desaceleracion_emergencia"));
        double longitudTotal=Double.parseDouble(request.getParameter("longitud_total"));
        double longitudRemolque=Double.parseDouble(request.getParameter("longitud_remolque"));
        double masaRemolque=Double.parseDouble(request.getParameter("masa_remolque"));
        double longitudMotriz=Double.parseDouble(request.getParameter("longitud_motriz"));
        double masaMotriz=Double.parseDouble(request.getParameter("masa_motriz"));
        double voltaje=Double.parseDouble(request.getParameter("voltaje"));
        double voltajeBateria=Double.parseDouble(request.getParameter("voltaje_bateria"));
        int numeroRemolque=Integer.parseInt(request.getParameter("unidades_remolque"));
        int numeroMotriz=Integer.parseInt(request.getParameter("unidades_motriz"));
        int capacidadMotriz=Integer.parseInt(request.getParameter("capacidad_motriz"));
        int capacidadRemolque=Integer.parseInt(request.getParameter("capacidad_remolque"));
        int idMaterialRodante = 0;
         List<Integer> idMR = mrjc.listarIdMR(); 
//            int contadorMaterialRodantes = 0;
            for (int i = 0; i < idMR.size(); i++) {
//                MaterialRodante re = res.get(i);
//                if (re.getIdMaterialRodante() != contadorMaterialRodantes) {
                if (idMR.get(i) != i) {
                    idMaterialRodante = i;
                    break;
                } 
                if(i == idMR.size()-1){
                idMaterialRodante = idMR.size();
                    break;
                }
            }
        MaterialRodante mr=new MaterialRodante();
        mr.setIdMaterialRodante(idMaterialRodante);
        mr.setNombreMaterialRodante(nombre);
        mr.setTipo(tipo);
        mr.setSubTipo(subTipo);
        mr.setNumeroVagones(numeroVagones);
        mr.setCapacidadPasajeros(capacidadPasajeros);
        mr.setVelocidadDisenio(velocidadDiseño);
        mr.setVelocidadOperativa(velocidadOperacion);
        mr.setMasa(masa);
        mr.setAceleracionMax(aceleracionMaxima);
        mr.setDesaceleracionMax(desaceleracionMaxima);
        mr.setNumeroEjes(numeroEjes);
        mr.setCargaMaxima(cargaMaxima);
        mr.setDesaceleracionEmergencia(desaceleracionEmergencia);
        mr.setLongitudMotriz(longitudMotriz);
        mr.setLongitudRemolque(longitudRemolque);
        mr.setLongitudTotal(longitudTotal);
        mr.setMasaMotriz(masaMotriz);
        mr.setMasaRemolque(masaRemolque);
        mr.setVoltaje(voltaje);
        mr.setVoltajeBateria(voltajeBateria);
        mr.setPresionTrabajo(presionTrabajo);
        mr.setNumeroMotriz(numeroMotriz);
        mr.setNumeroRemolque(numeroRemolque);
        mr.setCapacidadRemolque(capacidadRemolque);
        mr.setCapacidadMotriz(capacidadMotriz);
        mr.setFrenadoDescripcion(frenadoDescripcion);
        mr.setAltoXAnchoMotriz(altoXAnchoMotriz);
        mr.setAltoXAnchoRemolque(altoXAnchoRemolque);
        mrjc.create(mr);
        
        salida.print("Material Rodante "+mr.getNombreMaterialRodante()+
                    " ha sido creado satisfactoriamente");
         }catch(PreexistingEntityException e){
             salida.print("El Material Rodante "+
                    " ya existe");
        }catch (Exception e) {
            salida.print("No se pudo agregar el material rodante");
        }
    }

    private void editar(HttpServletRequest request, HttpServletResponse response) throws NonexistentEntityException, Exception {
        PrintWriter salida=response.getWriter();
        HttpSession session = request.getSession();
        Usuario usr = (Usuario) session.getAttribute("usuario");
        
        if(usr.getNivel()>1){
        salida.print("NO TIENES PERMISO PARA REALIZAR ESTA ACCIÓN");
        return;
        }
        try{
        MaterialRodanteJpaController mrjc=new MaterialRodanteJpaController(Conex.getEmf());
        String nombre=request.getParameter("nombre");
        String tipo=request.getParameter("tipo");
        String subTipo=request.getParameter("sub_tipo");
        int numeroVagones=Integer.parseInt(request.getParameter("numero_vagones"));
        int numeroEjes=Integer.parseInt(request.getParameter("numero_ejes"));
        double capacidadPasajeros=Double.parseDouble(request.getParameter("capacidad_pasajeros"));
        double velocidadDiseño=Double.parseDouble(request.getParameter("velocidad_diseño"));
        double velocidadOperacion=Double.parseDouble(request.getParameter("velocidad_operacion"));
        double masa=Double.parseDouble(request.getParameter("masa"));
        double aceleracionMaxima=Double.parseDouble(request.getParameter("aceleracion_maxima"));
        double desaceleracionMaxima=Double.parseDouble(request.getParameter("desaceleracion_maxima"));        
        int idMaterialRodante=Integer.parseInt(request.getParameter("id_material_rodante"));
        double cargaMaxima=Double.parseDouble(request.getParameter("carga_maxima"));
        double desaceleracionEmergencia=Double.parseDouble(request.getParameter("desaceleracion_emergencia"));
        double longitudTotal=Double.parseDouble(request.getParameter("longitud_total"));
        double longitudRemolque=Double.parseDouble(request.getParameter("longitud_remolque"));
        double masaRemolque=Double.parseDouble(request.getParameter("masa_remolque"));
        double longitudMotriz=Double.parseDouble(request.getParameter("longitud_motriz"));
        double masaMotriz=Double.parseDouble(request.getParameter("masa_motriz"));
        double voltaje=Double.parseDouble(request.getParameter("voltaje"));
        double voltajeBateria=Double.parseDouble(request.getParameter("voltaje_bateria"));
        int numeroRemolque=Integer.parseInt(request.getParameter("unidades_remolque"));
        int numeroMotriz=Integer.parseInt(request.getParameter("unidades_motriz"));
        int capacidadMotriz=Integer.parseInt(request.getParameter("capacidad_motriz"));
        int capacidadRemolque=Integer.parseInt(request.getParameter("capacidad_remolque"));
        String altoXAnchoRemolque=request.getParameter("alto_x_ancho_remolque");
        String altoXAnchoMotriz=request.getParameter("alto_x_ancho_motriz");
        String frenadoDescripcion=request.getParameter("frenado_descripcion");
        String presionTrabajo=request.getParameter("presion_trabajo");
        
        MaterialRodante mr=new MaterialRodante();
        mr.setIdMaterialRodante(idMaterialRodante);
        mr.setNombreMaterialRodante(nombre);
        mr.setTipo(tipo);
        mr.setSubTipo(subTipo);
        mr.setNumeroVagones(numeroVagones);
        mr.setCapacidadPasajeros(capacidadPasajeros);
        mr.setVelocidadDisenio(velocidadDiseño);
        mr.setVelocidadOperativa(velocidadOperacion);
        mr.setMasa(masa);
        mr.setAceleracionMax(aceleracionMaxima);
        mr.setDesaceleracionMax(desaceleracionMaxima);
        mr.setNumeroEjes(numeroEjes);
        mr.setCargaMaxima(cargaMaxima);
        mr.setDesaceleracionEmergencia(desaceleracionEmergencia);
        mr.setLongitudMotriz(longitudMotriz);
        mr.setLongitudRemolque(longitudRemolque);
        mr.setLongitudTotal(longitudTotal);
        mr.setMasaMotriz(masaMotriz);
        mr.setMasaRemolque(masaRemolque);
        mr.setVoltaje(voltaje);
        mr.setVoltajeBateria(voltajeBateria);
        mr.setPresionTrabajo(presionTrabajo);
        mr.setNumeroMotriz(numeroMotriz);
        mr.setNumeroRemolque(numeroRemolque);
        mr.setCapacidadRemolque(capacidadRemolque);
        mr.setCapacidadMotriz(capacidadMotriz);
        mr.setFrenadoDescripcion(frenadoDescripcion);
        mr.setAltoXAnchoMotriz(altoXAnchoMotriz);
        mr.setAltoXAnchoRemolque(altoXAnchoRemolque);
        
        mrjc.edit(mr);
        salida.print("Material Rodante "+mr.getNombreMaterialRodante()+
                    " ha sido editado satisfactoriamente");
        
         }catch(PreexistingEntityException e){
             salida.print("El Material Rodante "+
                    " ya existe");             
        }catch (Exception e) {
            salida.print("No se pudo editar el material rodante");
            e.printStackTrace();
                    }
    }

    

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws IllegalOrphanException, NonexistentEntityException, ServletException, IOException {
       PrintWriter salida=response.getWriter();
       HttpSession session = request.getSession();
        Usuario usr = (Usuario) session.getAttribute("usuario");
        
        if(usr.getNivel()>1){
        salida.print("NO TIENES PERMISO PARA REALIZAR ESTA ACCIÓN");
        return;
        }
        int idMaterialRodante=Integer.parseInt(request.getParameter("id_material_rodante"));
      String idMatRod=request.getParameter("id_material_rodante");
    String er=getServletContext().getRealPath("/src/java/img/"+idMatRod+".jpg");
   String fr="build\\";
   String frr="web\\";
   String gr=er.replace(fr, "");
   gr=gr.replace(frr, "");
    File imgr=new File(gr);
    if(imgr.delete()){
//        System.out.println("si");
    }else{
//    System.out.println("no");
    }
        MaterialRodanteJpaController mrjc=new MaterialRodanteJpaController(Conex.getEmf());
        
        mrjc.destroy(idMaterialRodante);
        salida.print("Material Rodante  ha sido eliminado satisfactoriamente");
              
    }

}
