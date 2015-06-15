/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import modelo.entity.Linea;
import modelo.entity.Restriccion;
import modelo.entity.RestriccionPK;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Conex;
import modelo.controlBD.LineaJpaController;
import modelo.controlBD.MaterialRodanteJpaController;
import modelo.controlBD.RestriccionJpaController;
import modelo.entity.MaterialRodante;
import modelo.entity.Usuario;

/**
 *
 * @author Kelvins Insua
 */
@WebServlet(name = "AdministrarRestriccion", urlPatterns = {"/AdministrarRestriccion"})
public class AdministrarRestriccion extends HttpServlet {

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
        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "Agregar":
                    agregar(request, response);
                    break;
                case "Editar":
                    editar(request, response);
                    break;
                case "Eliminar":
                    eliminar(request, response);
                    break;
                case "Generar DocTren":
                    genDocTren(request, response);
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
            Logger.getLogger(AdministrarRestriccion.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AdministrarRestriccion.class.getName()).log(Level.SEVERE, null, ex);
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

    private void agregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RestriccionJpaController rjc = new RestriccionJpaController(Conex.getEmf());
        Restriccion r = new Restriccion();
        LineaJpaController ljc = new LineaJpaController(Conex.getEmf());
        PrintWriter salida = response.getWriter();
        HttpSession session = request.getSession();
        Usuario usr = (Usuario) session.getAttribute("usuario");
        
        if(usr.getNivel()>2){
        salida.print("NO TIENES PERMISO PARA REALIZAR ESTA ACCIÓN");
        return;
        }
        try {
            String usuario = usr.toString();
            int idLinea = Integer.parseInt(request.getParameter("cmb_lineas"));
            int idRestriccion = 0;
            String observacion = request.getParameter("observacion");
            List<Integer> idRest = rjc.listarIdRestriccion(idLinea);
//            int contadorRestriccions = 0;
            for (int i = 0; i < idRest.size(); i++) {
//                Restriccion re = res.get(i);
                if (idRest.get(i) != i) {
                    idRestriccion = i;
                    break;
                }
                if (i == idRest.size() - 1) {
//                idRestriccion = contadorRestriccions;
                    idRestriccion = idRest.size();
                    break;
                }
            }
            double progInicio = Double.parseDouble(request.getParameter("prog_inicio"));
            double progFinal = Double.parseDouble(request.getParameter("prog_final"));
            double velMaxAscendente = Double.parseDouble(request.getParameter("vel_max_ascendente"));
            double velMaxDescendente = Double.parseDouble(request.getParameter("vel_max_descendente"));
            Date fechaRegistro = Date.valueOf(request.getParameter("fecha_registro"));
            RestriccionPK rpk = new RestriccionPK(idLinea, idRestriccion);
            Linea linea = ljc.findLinea(idLinea);

            if (progInicio < linea.getPkInicial()) {
                salida.print("La Restricción no puede iniciar en ese punto kilométrico");
                return;
            }
            if (progFinal > linea.getPkFinal()) {
                salida.print("La Restricción no puede finalizar en ese punto kilométrico");
                return;
            }
            if (progFinal < progInicio) {
                salida.print("La Restricción no puede finalizar en ese punto kilométrico");
                return;
            }
             if(velMaxAscendente>linea.getVelocidadLinea()||velMaxDescendente>linea.getVelocidadLinea()){
            salida.print("La restricción no puede tener velocidades mayores a las de la línea");
            return;
        }

            r.setRestriccionPK(rpk);
            r.setLinea(linea);
            r.setProgFinal(progFinal);
            r.setProgInicio(progInicio);
            r.setVelocidadMaxAscendente(velMaxAscendente);
            r.setVelocidadMaxDescendente(velMaxDescendente);
            r.setUsuario(usuario);
            r.setFechaRegistro(fechaRegistro);
            r.setObservacion(observacion);

            rjc.create(r);
            salida.print("La Restricción con progresiva de inicio " + r.getProgInicio()
                    + " ha sido creada satisfactoriamente");

        } catch (Exception ex) {
            salida.print("Uno de los Valores Ingresados No es Correcto");
            ex.printStackTrace();
        }
    }

    private void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RestriccionJpaController rjc = new RestriccionJpaController(Conex.getEmf());
        Restriccion r = new Restriccion();
        LineaJpaController ljc = new LineaJpaController(Conex.getEmf());
        PrintWriter salida = response.getWriter();
        HttpSession session = request.getSession();
        Usuario usr = (Usuario) session.getAttribute("usuario");
        
        if(usr.getNivel()>2){
        salida.print("NO TIENES PERMISO PARA REALIZAR ESTA ACCIÓN");
        return;
        }
        try {
            String usuario = usr.toString();
            int idLinea = Integer.parseInt(request.getParameter("id_linea"));
            int idRestriccion = Integer.parseInt(request.getParameter("id_restriccion"));
            double progInicio = Double.parseDouble(request.getParameter("prog_inicio"));
            double progFinal = Double.parseDouble(request.getParameter("prog_final"));
            double velMaxAscendente = Double.parseDouble(request.getParameter("vel_max_ascendente"));
            double velMaxDescendente = Double.parseDouble(request.getParameter("vel_max_descendente"));
            Date fechaRegistro = Date.valueOf(request.getParameter("fecha_registro"));
            String observacion = request.getParameter("observacion");
            RestriccionPK rpk = new RestriccionPK(idLinea, idRestriccion);
            Linea linea = ljc.findLinea(idLinea);

            if (progInicio < linea.getPkInicial()) {
                salida.print("La Restricción no puede iniciar en ese punto kilométrico");
                return;
            }
            if (progFinal > linea.getPkFinal()) {
                salida.print("La Restricción no puede finalizar en ese punto kilométrico");
                return;
            }
            if (progFinal <progInicio) {
                salida.print("La Restricción no puede finalizar en ese punto kilométrico");
                return;
            }
              if(velMaxAscendente>linea.getVelocidadLinea()||velMaxDescendente>linea.getVelocidadLinea()){
            salida.print("La restricción no puede tener velocidades mayores a las de la línea");
            return;
        }
            r.setRestriccionPK(rpk);
            r.setLinea(linea);
            r.setProgFinal(progFinal);
            r.setProgInicio(progInicio);
            r.setVelocidadMaxAscendente(velMaxAscendente);
            r.setVelocidadMaxDescendente(velMaxDescendente);
            r.setUsuario(usuario);
            r.setFechaRegistro(fechaRegistro);
            r.setObservacion(observacion);

            rjc.edit(r);
            salida.print("La Restricción con progresiva de inicio " + r.getProgInicio()
                    + " ha sido editada satisfactoriamente");

        } catch (Exception ex) {
            salida.print("Uno de los Valores Ingresados No es Correcto");

        }

    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        
        
        RestriccionJpaController rjc = new RestriccionJpaController(Conex.getEmf());
        LineaJpaController ljc = new LineaJpaController(Conex.getEmf());
        PrintWriter salida = response.getWriter();
        HttpSession session = request.getSession();
        Usuario usr = (Usuario) session.getAttribute("usuario");
        
        if(usr.getNivel()>2){
        salida.print("NO TIENES PERMISO PARA REALIZAR ESTA ACCIÓN");
        return;
        }
        try {
            int idLinea = Integer.parseInt(request.getParameter("id_linea"));
            int idRestriccion = Integer.parseInt(request.getParameter("id_restriccion"));
            Restriccion r = rjc.buscarRestriccionPK(idLinea, idRestriccion);
            RestriccionPK rpk = r.getRestriccionPK();

            r.setRestriccionPK(rpk);

            rjc.destroy(rpk);
            salida.print("La Restricción "
                    + " ha sido eliminada satisfactoriamente");

        } catch (Exception ex) {
            salida.print("Uno de los Valores Ingresados No es Correcto");

        }

    }

    public void genDocTren(HttpServletRequest request, HttpServletResponse response) throws Exception {

        int linea = Integer.parseInt(request.getParameter("idLinea"));
        //PrintWriter salida = response.getWriter();
        HttpSession session = request.getSession();
       // PrintWriter out = response.getWriter();
        Usuario usr = (Usuario) session.getAttribute("usuario");
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        try {

            String nota = new String(request.getParameter("nota").getBytes(),"UTF-8");
            String nota2 = new String(request.getParameter("nota2").getBytes(),"UTF-8");
            MaterialRodanteJpaController mrjc = new MaterialRodanteJpaController(Conex.getEmf());
            MaterialRodante mr = mrjc.findMaterialRodante(Integer.parseInt(request.getParameter("materialRodante")));
            String comunicaciones = new String(request.getParameter("comunicaciones").getBytes(),"UTF-8");
            String instrucciones = new String(request.getParameter("instrucciones").getBytes(),"UTF-8");
            String precauciones = new String(request.getParameter("precauciones").getBytes(),"UTF-8");
            String nombre = new String(request.getParameter("nombre").getBytes(),"UTF-8");
            String vigencia = request.getParameter("vigencia");
            
            String[] fecha = vigencia.split("-");
            
            LineaJpaController ljc = new LineaJpaController(Conex.getEmf());
            Linea l = ljc.findLinea(linea);
            
       
        Document documento = new Document(PageSize.A4);
        com.itextpdf.text.Font arialNegrita=FontFactory.getFont("arial",9,Font.BOLD);
        com.itextpdf.text.Font arial=FontFactory.getFont("arial",9,Font.PLAIN);
        com.itextpdf.text.Font saltoDeLinea=FontFactory.getFont("arial",5,Font.BOLD);
        Paragraph parrafo=new Paragraph();
        int numResAs=0;
        int numResDes=0;
        RestriccionJpaController rjc=new RestriccionJpaController(Conex.getEmf());
        List<Restriccion> restDes=rjc.buscarIdLineaDescendenteDocTren(linea,l.getVelocidadLinea());
        List<Restriccion> restAsc=rjc.buscarIdLineaAscendenteDocTren(linea,l.getVelocidadLinea());
        int restAscTotal=restAsc.size()-1;
        int restDescTotal=restDes.size()-1;
            System.out.println(restAscTotal);
            System.out.println(restDescTotal);
        PdfWriter.getInstance(documento, baos);
        documento.open();
       
        
        
        do{
            
        try {
             URL url = getClass().getResource("/img/cintillo_s1.png");
            Image foto = Image.getInstance(url);
            foto.scaleToFit(500,70);
            foto.setAlignment(Chunk.ALIGN_MIDDLE);
            documento.add(foto);
        } catch (Exception e) {
            e.printStackTrace();
        }    
        documento.add(new Paragraph("DOCUMENTO DE TREN: "+nombre,arialNegrita));
        
        documento.add(new Paragraph("LÍNEA: "+l.getNombreLinea()+" : Documento Válido Para Todos Los Trenes",arialNegrita));
         documento.add(new Paragraph(" ",saltoDeLinea));
         
         
        PdfPTable tabla = new PdfPTable(1);
        
        tabla.setHorizontalAlignment(10);
        tabla.setWidthPercentage(100f);
        tabla.addCell(new Paragraph("Operaciones Del Tren: circular a una velocidad no mayor de "+l.getVelocidadLinea()+ " Km/h",arial));
        tabla.addCell(new Paragraph("Nota: "+nota,arial));
           //tabla.getDefaultCell().setBorder(2);
        if(numResAs<restAscTotal){
            tabla.getDefaultCell().setBorderWidthBottom(0);
           tabla.addCell(new Paragraph("Restricciones Ascendentes ",arialNegrita));
        }
        
        
        tabla.getDefaultCell().setBorderColorBottom(BaseColor.WHITE);
        
           
           int c=0;
            for (int i = numResAs; i <=restAscTotal; i++) {
           if(c>30){
               break;
           }
              //  System.out.println("imrimiento asc: "+numResAs);
             
            int pkI = (int) (restAsc.get(numResAs).getProgInicio() / 1000);
            int pkI1 = (int) (((restAsc.get(numResAs).getProgInicio() / 1000) - pkI) * 1000);
            int pkF = (int) (restAsc.get(numResAs).getProgFinal() / 1000);
            int pkF1 = (int) (((restAsc.get(numResAs).getProgFinal() / 1000) - pkF) * 1000);

          
           
             tabla.getDefaultCell().setBorder(14);
             tabla.getDefaultCell().setBorderWidthBottom(0);
            tabla.getDefaultCell().setBorderWidthTop(0);
            tabla.addCell(new Paragraph("*PK "+pkI+"+"+pkI1+" al "+pkF+"+"+pkF1+
                    " circular a una velocidad no mayor de "+restAsc.get(numResAs).getVelocidadMaxAscendente()+" "+restAsc.get(numResAs).getObservacion(),arial));
            numResAs++;
           
            c++;           
        }
       
       
       if(c<30){
           tabla.getDefaultCell().setBorderWidthBottom(1);
            tabla.getDefaultCell().setBorderWidthTop(1);
           tabla.getDefaultCell().setBorder(14);
           
           tabla.addCell(new Paragraph("Restricciones Descendentes ",arialNegrita));
          
       }
       int d=c;
        if(c<30){
        
            for (int i = numResDes; i <= restDescTotal; i++) {
            if(d>30){
               break;
           }
            
            tabla.getDefaultCell().setBorder(14);
            tabla.getDefaultCell().setBorderWidthBottom(0);
            tabla.getDefaultCell().setBorderWidthTop(0);
            
            int pkI = (int) (restDes.get(numResDes).getProgFinal() / 1000);
            int pkI1 = (int) (((restDes.get(numResDes).getProgFinal() / 1000) - pkI) * 1000);
            int pkF = (int) (restDes.get(numResDes).getProgInicio() / 1000);
            int pkF1 = (int) (((restDes.get(numResDes).getProgInicio() / 1000) - pkF) * 1000);

            
            tabla.addCell(new Paragraph("*PK " + pkI + "+" + pkI1 + " al " + pkF + "+" + pkF1 +
                    " circular a una velocidad no mayor de "+restDes.get(numResDes).getVelocidadMaxDescendente()+" "+restDes.get(numResDes).getObservacion(),arial));
            numResDes++;
            d++;
           
        }
        }
        
        int espacios=d;
        while(espacios<30){
            tabla.getDefaultCell().setBorderWidthBottom(0);
            tabla.getDefaultCell().setBorderWidthTop(0);
        tabla.addCell(new Paragraph(" ",arial));
        espacios++;
        
        }
        tabla.getDefaultCell().setBorder(15);
        tabla.getDefaultCell().setBorderWidthBottom(1);
        tabla.getDefaultCell().setBorderWidthTop(1);
        tabla.getDefaultCell().setMinimumHeight(50f);
        tabla.addCell(new Paragraph("Instrucciones: "+instrucciones,arial));
        tabla.addCell(new Paragraph("Comunicaciones: "+comunicaciones,arial));
        tabla.addCell(new Paragraph("Precauciones: "+precauciones,arial));
        tabla.getDefaultCell().setMinimumHeight(10f);
        tabla.addCell(new Paragraph("Vigencia A Partir De: "+fecha[0]+"/"+fecha[1]+"/"+fecha[2],arial));
        tabla.getDefaultCell().setBorder(1);
        Paragraph preface=new Paragraph (Paragraph.ALIGN_CENTER,"Realizado Por: "+usr.toString()+" - Gerencia de Gestión de Tráfico",arialNegrita);
        preface.setAlignment(Element.ALIGN_CENTER);
        tabla.addCell(preface);
        tabla.getDefaultCell().setBorder(0);
        tabla.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
        preface=new Paragraph (Paragraph.ALIGN_CENTER,"EL NO CUMPLIR CON LAS LIMITACIONES PRESCRITAS EN ESTE"
                + " DOCUMENTO VA EN CONTRA DE LA SEGURIDAD EN LA CIRCULACIÓN, POR LO TANTO SERÁ  MOTIVO DE SANCIÓN",FontFactory.getFont("arial", 8f));
        tabla.addCell(preface);
        preface=new Paragraph (Paragraph.ALIGN_CENTER,"Nota: Información sustentada con el informe técnico "
                + "de limitaciones de velocidad, emitido por el CCF (Centro de Control de Fallas)",FontFactory.getFont("arial", 8f));
        tabla.addCell(preface);
        preface=new Paragraph (Paragraph.ALIGN_CENTER,"Sentido Ascendente: Sentido en el cual aumenta la progresiva ej: 0+0 -> 41+000 -- Sentido Descendente: Sentido en el cual disminuye la progresiva ej: 41+000 -> 0+0 ",FontFactory.getFont("arial", 6f));
        tabla.addCell(preface);
        preface=new Paragraph (Paragraph.ALIGN_CENTER,"En la línea Caracas-Cua el sentido ASCENDENTE corresponde a la VÍA PAR y el DESCENDENTE a la VÍA IMPAR",FontFactory.getFont("arial", 6f));
        tabla.addCell(preface);
        
        
        
        documento.add(tabla);
        
        if(numResAs<restAscTotal||numResDes<restDescTotal){
        documento.newPage();
        }
        }while(numResAs<restAscTotal||numResDes<restDescTotal);
        
            System.out.println("Termine");
        //CARACTERÍSTICAS DE MATERIAL RODANTE
            documento.newPage();
            
            try {
            URL url = getClass().getResource("/img/cintillo_s1.png");
            Image foto = Image.getInstance(url);
            foto.scaleToFit(500,70);
            foto.setAlignment(Chunk.ALIGN_MIDDLE);
            documento.add(foto);
        } catch (Exception e) {
            e.printStackTrace();
        }    
        documento.add(new Paragraph("DOCUMENTO DE TREN: "+nombre,arialNegrita));
        
        documento.add(new Paragraph("LÍNEA: "+l.getNombreLinea()+" : Documento Válido Para Todos Los Trenes",arialNegrita));
        documento.add(new Paragraph(" ",saltoDeLinea));
        
        documento.add(new Paragraph("CARACTERÍSTICAS DE LA UNIDAD: "+mr.getNombreMaterialRodante(),arialNegrita));
        documento.add(new Paragraph(" ",saltoDeLinea));
        
        PdfPTable tablaMT=new PdfPTable(2);
        tablaMT.addCell(new Paragraph("ITEM",arialNegrita));
        tablaMT.addCell(new Paragraph("DESCRIPCIÓN",arialNegrita));
        tablaMT.addCell(new Paragraph("Número de Unidades Remolque",arial));
        tablaMT.addCell(new Paragraph(mr.getNumeroRemolque()+" Unidades",arial));
        tablaMT.addCell(new Paragraph("Número de Unidades Motriz",arial));
        tablaMT.addCell(new Paragraph(mr.getNumeroMotriz()+" Unidades",arial));
        tablaMT.addCell(new Paragraph("Longitud Total",arial));
        tablaMT.addCell(new Paragraph(mr.getLongitudTotal()+" m",arial));
        tablaMT.addCell(new Paragraph("Longitud Coche Remolque",arial));
        tablaMT.addCell(new Paragraph(mr.getLongitudRemolque()+" m",arial));
        tablaMT.addCell(new Paragraph("Longitud Coche Motriz",arial));
        tablaMT.addCell(new Paragraph(mr.getLongitudMotriz()+" m",arial));
        tablaMT.addCell(new Paragraph("Alto x Ancho Coche Motriz",arial));
        tablaMT.addCell(new Paragraph(mr.getAltoXAnchoMotriz()+" m",arial));
        tablaMT.addCell(new Paragraph("Alto x Ancho Coche Remolque",arial));
        tablaMT.addCell(new Paragraph(mr.getAltoXAnchoRemolque()+" m",arial));
        tablaMT.addCell(new Paragraph("Masa o Tara Total",arial));
        tablaMT.addCell(new Paragraph(mr.getMasa()+" t",arial));
        tablaMT.addCell(new Paragraph("Masa Coche Remolque",arial));
        tablaMT.addCell(new Paragraph(mr.getMasaRemolque()+" t",arial));
        tablaMT.addCell(new Paragraph("Masa Coche Motriz",arial));
        tablaMT.addCell(new Paragraph(mr.getMasaMotriz()+" t",arial));
        tablaMT.addCell(new Paragraph("Frenado",arial));
        tablaMT.addCell(new Paragraph(mr.getFrenadoDescripcion()+"",arial));
        tablaMT.addCell(new Paragraph("Trocha",arial));
        tablaMT.addCell(new Paragraph(l.getTrocha()+" m",arial));
        tablaMT.addCell(new Paragraph("Velocidad Comercial",arial));
        tablaMT.addCell(new Paragraph(mr.getVelocidadOperativa()+" Km/h",arial));
        tablaMT.addCell(new Paragraph("Aceleración Máx.",arial));
        tablaMT.addCell(new Paragraph(mr.getAceleracionMax()+" m/s^2",arial));
        tablaMT.addCell(new Paragraph("Desaceleración de Servicio",arial));
        tablaMT.addCell(new Paragraph(mr.getDesaceleracionMax()+" m/s^2",arial));
        tablaMT.addCell(new Paragraph("Desaceleracion de Emergencia",arial));
        tablaMT.addCell(new Paragraph(mr.getDesaceleracionEmergencia()+" m/s^2",arial));
        tablaMT.addCell(new Paragraph("Voltaje",arial));
        tablaMT.addCell(new Paragraph(mr.getVoltaje()+" V",arial));
        tablaMT.addCell(new Paragraph("Voltaje de Baterías",arial));
        tablaMT.addCell(new Paragraph(mr.getVoltajeBateria()+" V",arial));
        tablaMT.addCell(new Paragraph("Presión de Trabajo",arial));
        tablaMT.addCell(new Paragraph(mr.getPresionTrabajo()+"",arial));
        if(mr.getSubTipo().equals("Tren de Viajeros")){
        tablaMT.addCell(new Paragraph("Capacidad Coche Remolque",arial));
        tablaMT.addCell(new Paragraph(mr.getCapacidadRemolque()+" pasajeros",arial));
        tablaMT.addCell(new Paragraph("Capacidad Coche Motriz",arial));
        tablaMT.addCell(new Paragraph(mr.getCapacidadMotriz()+" pasajeros",arial));
        tablaMT.addCell(new Paragraph("Capacidad Total",arial));
        tablaMT.addCell(new Paragraph(mr.getCapacidadPasajeros()+" pasajeros",arial));
        }else{
        tablaMT.addCell(new Paragraph("Capacidad Coche Remolque",arial));
        tablaMT.addCell(new Paragraph(mr.getCapacidadRemolque()+" t",arial));
        tablaMT.addCell(new Paragraph("Capacidad Coche Motriz",arial));
        tablaMT.addCell(new Paragraph(mr.getCapacidadMotriz()+" t",arial));
        tablaMT.addCell(new Paragraph("Capacidad Total",arial));
        tablaMT.addCell(new Paragraph(mr.getCapacidadPasajeros()+" t",arial));
        }
        documento.add(tablaMT);
        documento.add(new Paragraph(" ",saltoDeLinea));
        documento.add(new Paragraph(" ",saltoDeLinea));
        
        
        
        try {
            URL url = getClass().getResource("/img/"+request.getParameter("materialRodante")+".jpg");
            Image foto = Image.getInstance(url);
            foto.scaleToFit(300,300);
            foto.setAlignment(Chunk.ALIGN_MIDDLE);
            documento.add(foto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        documento.add(new Paragraph(" ",saltoDeLinea));
        documento.add(new Paragraph(" ",saltoDeLinea));
        PdfPTable t=new PdfPTable(1);
        t.setWidthPercentage(100f);
        t.addCell("Nota: "+nota2);
        documento.add(t);
        
        
        documento.add(new Paragraph(" ",saltoDeLinea));
        documento.add(new Paragraph(" ",saltoDeLinea));
        
        Paragraph preface=new Paragraph (Paragraph.ALIGN_CENTER,"Realizado Por: "+usr.toString(),arialNegrita);
        documento.add(preface);
        
        
        documento.close();
       
        response.addHeader("Content-Disposition", "attachment; filename=DocumentoDeTren.pdf");
        response.addHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.addHeader("Pragma", "public");
        response.setContentType("application/pdf");
        
            DataOutput output=new DataOutputStream(response.getOutputStream());
            
            byte[] bytes=baos.toByteArray();
            response.setContentLength(bytes.length);
       
            for (int i = 0; i < bytes.length; i++) {
                output.writeByte(bytes[i]);
                
            }

        } catch (Exception e) {
            e.printStackTrace();
//            salida.print("http://localhost:8084/MODULO2.3/img/error.png");
        }

    }

}
