package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Conex;
import modelo.GestorLista;
import modelo.controlBD.CurvaEsfuerzoJpaController;
import modelo.controlBD.MaterialRodanteJpaController;
import modelo.controlBD.exceptions.NonexistentEntityException;
import modelo.entity.CurvaEsfuerzo;
import modelo.entity.CurvaEsfuerzoPK;
import modelo.entity.MaterialRodante;
import modelo.entity.Usuario;

/**
 *
 * @author seront
 */
@WebServlet(name = "AdministrarCurvas", urlPatterns = {"/AdministrarCurvas"})
public class AdministrarCurvas extends HttpServlet {

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
        
        switch (request.getParameter("accion")) {
            case "Agregar":
                agregar(request,response);
                break;
            case "Editar":
                editar(request,response);
                break;
            case "Eliminar":
                eliminar(request,response);
                break;
            case "Grafico":
             graficar(request,response);
                break;
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

    private void agregar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter salida=response.getWriter();   
        
        HttpSession session = request.getSession();
        Usuario usr = (Usuario) session.getAttribute("usuario");
        
        if(usr.getNivel()>1){
        salida.print("NO TIENES PERMISO PARA REALIZAR ESTA ACCIÓN");
        return;
        }
        try {                     
            int idMatRod= Integer.parseInt(request.getParameter("idMatRod"));
            double idVel= Integer.parseInt(request.getParameter("vel"));
            double esfuerzoTraccion;
            double esfuerzoFrenado;
            CurvaEsfuerzoPK curPK= new CurvaEsfuerzoPK(idMatRod, idVel);
            CurvaEsfuerzo cur=new CurvaEsfuerzo();
            cur.setCurvaEsfuerzoPK(curPK);
            MaterialRodanteJpaController mjc=new MaterialRodanteJpaController(Conex.getEmf());
            MaterialRodante mr= mjc.findMaterialRodante(idMatRod);
            cur.setMaterialRodante(mr);
            if(!request.getParameter("esfTrac").equals("")){
            esfuerzoTraccion=Double.parseDouble(request.getParameter("esfTrac"));
            cur.setEsfuerzoTraccion(esfuerzoTraccion);
            }
            if(!request.getParameter("esfFre").equals("")){
                esfuerzoFrenado=Double.parseDouble(request.getParameter("esfFre"));
                cur.setEsfuerzoFrenado(esfuerzoFrenado);
            }
            if(idVel>mr.getVelocidadDisenio()){
            salida.print("La velocidad de esfuerzo no puede ser mayor que la velocidad de diseño del material rodante");
            }
            if(idVel<0){
            salida.print("La velocidad de esfuerzo no puede ser negativa");
            }
            CurvaEsfuerzoJpaController cjc= new CurvaEsfuerzoJpaController(Conex.getEmf());
            cjc.create(cur);
            salida.print("Los parámetros han sido registrados satisfactoriamente "+idVel);
            
        } catch (IOException ex) {
            salida.print("No se pudo agregar");
            ex.printStackTrace();
        } catch (Exception ex) {
            salida.print("No se pudo agregar");
            ex.printStackTrace();
        }
        
    }

    private void editar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter salida=response.getWriter();
        System.out.println("Editando");
        
        HttpSession session = request.getSession();
        Usuario usr = (Usuario) session.getAttribute("usuario");
        
        if(usr.getNivel()>1){
        salida.print("NO TIENES PERMISO PARA REALIZAR ESTA ACCIÓN");
        return;
        }
        try {
            int idMatRod= Integer.parseInt(request.getParameter("idMatRod"));
            double idVel= Double.parseDouble(request.getParameter("idVel"));
            CurvaEsfuerzoPK curPK= new CurvaEsfuerzoPK(idMatRod, idVel);
            CurvaEsfuerzoJpaController cjc= new CurvaEsfuerzoJpaController(Conex.getEmf());
            CurvaEsfuerzo cur=new CurvaEsfuerzo();
            cur.setCurvaEsfuerzoPK(curPK);
            MaterialRodanteJpaController mjc=new MaterialRodanteJpaController(Conex.getEmf());
            MaterialRodante mr= mjc.findMaterialRodante(idMatRod);
            cur.setMaterialRodante(mr);
            double esfuerzoTraccion;
            double esfuerzoFrenado;
            if(!request.getParameter("esfTrac").equals("")){
                esfuerzoTraccion=Double.parseDouble(request.getParameter("esfTrac"));
                cur.setEsfuerzoTraccion(esfuerzoTraccion);
            }
            if((request.getParameter("esfFren")!=null)&&(!request.getParameter("esfFren").equals(""))){
                esfuerzoFrenado=Double.parseDouble(request.getParameter("esfFren"));
                cur.setEsfuerzoFrenado(esfuerzoFrenado);
            }
            System.out.println("Llego hasta");
            cjc.edit(cur);
            salida.print("Editado satisfactoriamente");
        } catch (Exception ex) {
            salida.print("Ha ocurrido un error");
            ex.printStackTrace();
        }
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter salida=response.getWriter();
        HttpSession session = request.getSession();
        Usuario usr = (Usuario) session.getAttribute("usuario");
        
        if(usr.getNivel()>1){
        salida.print("NO TIENES PERMISO PARA REALIZAR ESTA ACCIÓN");
        return;
        }
        try {
            int idMatRod= Integer.parseInt(request.getParameter("idMatRod"));
            double idVel= Double.parseDouble(request.getParameter("vel"));
            CurvaEsfuerzoPK curPK= new CurvaEsfuerzoPK(idMatRod, idVel);
            
            CurvaEsfuerzoJpaController cjc= new CurvaEsfuerzoJpaController(Conex.getEmf());
            cjc.destroy(curPK);
            salida.print("Eliminado satisfactoriemente");
        } catch (NonexistentEntityException ex) {
            salida.print("No se pudo eliminar");
            ex.printStackTrace();
        }
    }

    private void graficar(HttpServletRequest request, HttpServletResponse response) throws IOException {
       System.out.println("Esta haciendo algo");
        try{
        GestorLista gl=new GestorLista();
        PrintWriter out=response.getWriter();
        int idMatRod= Integer.parseInt(request.getParameter("idMatRod"));
        List<Double> traccion= gl.buscarTraccionMR(idMatRod);
        List<Double> frenado= gl.buscarFrenadoMR(idMatRod);
        List<Double> velocidades= gl.buscarVelocidadesMR(idMatRod);
        List<String> velVsTrac= new ArrayList<>();
        List<String> velVsFren= new ArrayList<>();
        for (int i = 0; i < velocidades.size(); i++) {
            double vel= velocidades.get(i);
            double trac= traccion.get(i);
            String par="["+vel+","+trac+"]";
            velVsTrac.add(par);            
        }
        for (int i = 0; i < velocidades.size(); i++) {
            double vel= velocidades.get(i);
            double fren= frenado.get(i);
            String par="["+vel+","+fren+"]";
            velVsFren.add(par);            
        }
        
        out.println("<script>");
                out.println("var trac=" + velVsTrac);
                out.println("var fren=" + velVsFren);
                //out.println("var vel=" + gl.buscarVelocidadesMR(idMatRod));
                out.println("</script>");
        
    }catch(Exception e){
    e.printStackTrace();
}
    }

}
