/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import modelo.controlBD.CircuitoViaJpaController;
import modelo.controlBD.CurvaEsfuerzoJpaController;
import modelo.controlBD.LineaJpaController;
import modelo.controlBD.RestriccionJpaController;
import modelo.entity.CircuitoVia;
import modelo.entity.CurvaEsfuerzo;
import modelo.entity.Linea;
import modelo.entity.Restriccion;

/**
 *
 * @author usuario
 */
public class MarchaPrueba1 {

    public static void main(String[] args) throws FileNotFoundException, DocumentException {
        
        CurvaEsfuerzoJpaController cejc=new CurvaEsfuerzoJpaController(Conex.getEmf());
        List<CurvaEsfuerzo> ce=cejc.curvaDelMaterialRodante(1);
        
        System.out.println(ce);
        System.out.println(ce.isEmpty());
//        CircuitoViaJpaController cvjc=new CircuitoViaJpaController(Conex.getEmf());
//        List<CircuitoVia> cv=cvjc.buscarCircuitoVia(2);
//       
//        LineaJpaController ljc=new LineaJpaController(Conex.getEmf());
//         Linea l=ljc.findLinea(1);
//       
//        String tiempoTotal1 = ("00:01:00");
//            Time uno = Time.valueOf(tiempoTotal1);
//             System.out.println(uno);
    }

}
