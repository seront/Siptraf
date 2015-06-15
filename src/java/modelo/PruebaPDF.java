/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import modelo.controlBD.EstacionJpaController;
import modelo.controlBD.LineaJpaController;
import modelo.controlBD.RestriccionJpaController;
import modelo.entity.Estacion;
import modelo.entity.Linea;
import modelo.entity.Restriccion;
/**
 *
 * @author Kelvins Insua
 */
public class PruebaPDF {

//    public static void main(String[] args) throws IOException, COSVisitorException, IOException {
//        String outputFileName = "TablaHoraria.pdf";
//        PDDocument document = new PDDocument();
//        PDPage page1 = new PDPage(PDPage.PAGE_SIZE_A4);
//        PDRectangle rect = page1.getMediaBox();
//        document.addPage(page1);
//
//        PDFont fontPlain = PDType1Font.HELVETICA;
//        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
//        PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
//        PDFont fontMono = PDType1Font.COURIER;
//
//        LineaJpaController ljc = new LineaJpaController(Conex.getEmf());
//        Linea l = ljc.findLinea(01);
//
//        EstacionJpaController ejc = new EstacionJpaController(Conex.getEmf());
//        List<Estacion> estAsc = ejc.ordenarAscendente(01);
//        List<Estacion> estDes = ejc.ordenarDescendente(01);
//        
//        // Start a new content stream which will "hold" the to be created content
//        PDPageContentStream cos = new PDPageContentStream(document, page1);
//
//        try {
//            BufferedImage awtImage = ImageIO.read(new File("web/img/header ife.png"));
//            PDXObjectImage ximage = new PDPixelMap(document, awtImage);
//            float scale = 0.5f; // alter this value to set the image size
//            //cos.drawImage(ximage, ximage.getWidth(), ximage.getHeight());
//            cos.drawXObject(ximage, 60, 800, ximage.getWidth() * scale, ximage.getHeight() * scale);
//
//            cos.setLineWidth(1);
//            cos.addLine(60, 760, 550, 760);
//            cos.addLine(60, 60, 550, 60);
//            cos.addLine(60, 60, 60, 760);
//            cos.addLine(550, 60, 550, 760);
//            cos.addLine(60, 70, 550, 70);
//                
//                 cos.beginText();
//                cos.setFont(fontPlain, 10);
//                cos.moveTextPositionByAmount((rect.getWidth()/2)-120,61);
//                cos.drawString("ENTRA EN VIGENCIA A PARTIR DEL 18/03/2015");
//                cos.endText();
//                
//            if (estAsc.size() <= 4) {
//                float factorEspacio=0f;
//                switch(estAsc.size()){
//                    case 4:
//                        factorEspacio=0;
//                        break;
//                    case 3:
//                        factorEspacio=30/7;
//                        break;
//                         case 2:
//                        factorEspacio=60/6;
//                        break;
//                              case 1:
//                        factorEspacio=90/5;
//                        break;
//                        
//                }
//                //SENTIDO ASCENDENTE
//                System.out.println(factorEspacio);
//                int c = 30;
//                cos.beginText();
//                cos.setFont(fontPlain, 10);
//                cos.moveTextPositionByAmount(61, rect.getHeight() - 90);
//                cos.drawString("SENTIDO ASCENDENTE");
//                cos.endText();
//                cos.beginText();
//                cos.setFont(fontPlain, 10);
//                cos.moveTextPositionByAmount((rect.getWidth()/2)+1, rect.getHeight() - 90);
//                cos.drawString("SENTIDO DESCENDENTE");
//                cos.endText();
//                cos.beginText();
//                cos.setFont(fontPlain, 6);
//                cos.moveTextPositionByAmount(61, rect.getHeight() - 110);
//                cos.drawString("Nro de tren");
//                cos.endText();
//                c+= factorEspacio;
//                cos.beginText();
//                cos.setFont(fontPlain, 6);
//                cos.moveTextPositionByAmount((71 + c), rect.getHeight() - 110);
//                cos.drawString("Via S");
//                cos.endText();
//                cos.addLine((70 + c), 70, (70 + c),  rect.getHeight() - 98);
//                c+= factorEspacio;
//                
//                c += 30;
//                cos.addLine((60 + c), 70, (60 + c),  rect.getHeight() - 98);
//                
//                 //SENTIDO DESCENDENTE
//                int d=30;
//                cos.addLine((rect.getWidth()/2), 70,(rect.getWidth()/2), 760);
//                cos.beginText();
//                cos.setFont(fontPlain, 6);
//                cos.moveTextPositionByAmount((rect.getWidth()/2)+1, rect.getHeight() - 110);
//                cos.drawString("Nro de tren");
//                cos.endText();
//                d+=factorEspacio;
//                cos.addLine((rect.getWidth()/2)+d+10, 70, (rect.getWidth()/2)+d+10, rect.getHeight() - 98);
//                
//                cos.beginText();
//                cos.setFont(fontPlain, 6);
//                cos.moveTextPositionByAmount((rect.getWidth()/2)+11+d, rect.getHeight() - 110);
//                cos.drawString("Via S");
//                cos.endText();
//                d+=30+factorEspacio;
//                cos.addLine((rect.getWidth()/2)+d, 70,(rect.getWidth()/2)+d,  rect.getHeight() - 98);
//
//                for (int i = 0; i < estAsc.size(); i++) {
//                    //SENTIDO ASCENDENTE
//                    cos.beginText();
//                    cos.setFont(fontPlain, 6);
//                    cos.moveTextPositionByAmount((61 + c), rect.getHeight() - 110);
//                    cos.drawString("" + estAsc.get(i).getAbrevEstacion());
//                    cos.endText();
//                    c += 30+factorEspacio;
//
//                    cos.addLine((60 + c), 70, (60 + c), rect.getHeight() - 98);
//                    //SENTIDO DESCENDENTE
//                    cos.beginText();
//                    cos.setFont(fontPlain, 6);
//                    cos.moveTextPositionByAmount((rect.getWidth()/2)+1 + d, rect.getHeight() - 110);
//                    cos.drawString("" + estDes.get(i).getAbrevEstacion());
//                    cos.endText();
//                    d += 30+factorEspacio;
//
//                    cos.addLine((rect.getWidth()/2) + d, 70, (rect.getWidth()/2) + d, rect.getHeight() - 98);
//
//                }
//                 //SENTIDO ASCENDENTE
//               
//              
//                cos.beginText();
//                cos.setFont(fontPlain, 6);
//                cos.moveTextPositionByAmount((61 + c), rect.getHeight() - 110);
//                cos.drawString("Via LL");
//                cos.endText();
//                c+=factorEspacio;
//                 cos.addLine((60 + c + 20), 70, (60 + c + 20),  rect.getHeight() - 98);
//                cos.beginText();
//                cos.setFont(fontPlain, 5);
//                cos.moveTextPositionByAmount((61 + c+20), rect.getHeight() - 110);
//                cos.drawString("Observaciones");
//                cos.endText();
//                c+=40+factorEspacio;
//                cos.addLine((60 + c + 15), 70, (60 + c + 15), 760);
//                cos.addLine(60, rect.getHeight() - 98, (60 + c + 15), rect.getHeight() - 98);
//                cos.addLine(60, rect.getHeight() - 112, (60 + c + 15), rect.getHeight() - 112);
//                
//                //SENTIDO DESCENDENTE
//                cos.beginText();
//                cos.setFont(fontPlain, 6);
//                cos.moveTextPositionByAmount((rect.getWidth()/2)+1 + d, rect.getHeight() - 110);
//                cos.drawString("Via LL");
//                cos.endText();
//                d+=factorEspacio;
//                cos.addLine((rect.getWidth()/2) + d + 20, 70, (rect.getWidth()/2) + d + 20,  rect.getHeight() - 98);
//                cos.beginText();
//                cos.setFont(fontPlain, 5);
//                cos.moveTextPositionByAmount((rect.getWidth()/2)+1 + d+20, rect.getHeight() - 110);
//                cos.drawString("Observaciones");
//                cos.endText();
//                d+=40+factorEspacio;
//                
//           
//                //cos.addLine((rect.getWidth()/2) + d + 15, 70,(rect.getWidth()/2) + d + 15, 760);
//                cos.addLine((rect.getWidth()/2), rect.getHeight() - 98, 550, rect.getHeight() - 98);
//                cos.addLine((rect.getWidth()/2), rect.getHeight() - 112,550, rect.getHeight() - 112);
//                
//            }else{
//                
//                 float factorEspacio=0f;
//               if(estAsc.size()>0){
//                   factorEspacio=30*(12-estAsc.size())/(16-(12-estAsc.size()));
//               }
//                cos.beginText();
//                cos.setFont(fontPlain, 10);
//                cos.moveTextPositionByAmount((rect.getWidth()/2)-40, rect.getHeight() - 90);
//                cos.drawString("SENTIDO ASCENDENTE");
//                cos.endText();
//                cos.addLine(60, rect.getHeight() - 98, 550, rect.getHeight() - 98);
//                int c=30;
//                 cos.beginText();
//                cos.setFont(fontPlain, 6);
//                cos.moveTextPositionByAmount(61, rect.getHeight() - 110);
//                cos.drawString("Nro de tren");
//                cos.endText();
//                c+=factorEspacio;
//                cos.beginText();
//                cos.setFont(fontPlain, 6);
//                cos.moveTextPositionByAmount(71 + c, rect.getHeight() - 110);
//                cos.drawString("Via S");
//                cos.endText();
//                cos.addLine(70 + c, 70, 70 + c,  rect.getHeight() - 98);
//                c += 30+factorEspacio;
//                cos.addLine(60 + c, 70, 60 + c,  rect.getHeight() - 98);
//                
//                
//                
//                
//                for (int i = 0; i < estAsc.size(); i++) {
//                     cos.beginText();
//                    cos.setFont(fontPlain, 6);
//                    cos.moveTextPositionByAmount(61 + c, rect.getHeight() - 110);
//                    cos.drawString("" + estAsc.get(i).getAbrevEstacion());
//                    cos.endText();
//                    c += 30+factorEspacio;
//
//                    cos.addLine(60 + c, 70, 60 + c, rect.getHeight() - 98);
//                    
//                }
//               
//                cos.beginText();
//                cos.setFont(fontPlain, 6);
//                cos.moveTextPositionByAmount(61 + c, rect.getHeight() - 110);
//                cos.drawString("Via LL");
//                cos.endText();
//                c+=factorEspacio;
//                 cos.addLine(60 + c + 20, 70, 60 + c + 20,  rect.getHeight() - 98);
//                cos.beginText();
//                cos.setFont(fontPlain, 5);
//                cos.moveTextPositionByAmount(61 + c+20, rect.getHeight() - 110);
//                cos.drawString("Observaciones");
//                cos.endText();
//                c+=40+factorEspacio;
//               
//                //cos.addLine(60 + c + 15, 70, 60 + c + 15,  rect.getHeight() - 98);
//                cos.addLine(60, rect.getHeight() - 98, 550, rect.getHeight() - 98);
//                cos.addLine(60, rect.getHeight() - 112, 550, rect.getHeight() - 112);
//               cos.beginText();
//            cos.setFont(fontBold, 14);
//            cos.moveTextPositionByAmount(60, rect.getHeight() - 60);
//            cos.drawString("PROGRAMACIÓN HORARIA: PH-11_2014_08");
//            cos.endText();
//            cos.beginText();
//            cos.setFont(fontPlain, 12);
//            cos.moveTextPositionByAmount(200, rect.getHeight() - 75);
//            cos.drawString("LINEA: " + l.getNombreLinea());
//            cos.endText();
//            cos.closeAndStroke();
//            //SENTIDO DESCENDENTE
//                page1 = new PDPage(PDPage.PAGE_SIZE_A4);
//                // PDPage.PAGE_SIZE_LETTER is also possible
//
//                document.addPage(page1);
//                rect = page1.getMediaBox();
//                cos.close();
//                cos = new PDPageContentStream(document, page1);
//
//                try {
////                    BufferedImage awtImage = ImageIO.read(new File("web/img/header ife.png"));
////                    PDXObjectImage ximage = new PDPixelMap(document, awtImage);
////                    float scale = 0.5f; // alter this value to set the image size
//                    //cos.drawImage(ximage, ximage.getWidth(), ximage.getHeight());
//                    cos.drawXObject(ximage, 60, 800, ximage.getWidth() * scale, ximage.getHeight() * scale);
//
//                } catch (Exception fnfex) {
//                    fnfex.printStackTrace();
//                    System.out.println("No image for you");
//                }
//                cos.beginText();
//            cos.setFont(fontBold, 14);
//            cos.moveTextPositionByAmount(60, rect.getHeight() - 60);
//            cos.drawString("PROGRAMACIÓN HORARIA: PH-11_2014_08");
//            cos.endText();
//            cos.beginText();
//            cos.setFont(fontPlain, 12);
//            cos.moveTextPositionByAmount(200, rect.getHeight() - 75);
//            cos.drawString("LINEA: " + l.getNombreLinea());
//            cos.endText();
//
//                cos.setLineWidth(1);
//            cos.addLine(60, 760, 550, 760);
//            cos.addLine(60, 60, 550, 60);
//            cos.addLine(60, 60, 60, 760);
//            cos.addLine(550, 60, 550, 760);
//            cos.addLine(60, 70, 550, 70);
//            
//                 cos.beginText();
//                cos.setFont(fontPlain, 10);
//                cos.moveTextPositionByAmount((rect.getWidth()/2)-120,61);
//                cos.drawString("ENTRA EN VIGENCIA A PARTIR DEL 18/03/2015");
//                cos.endText();
//            
//                cos.beginText();
//                cos.setFont(fontPlain, 10);
//                cos.moveTextPositionByAmount((rect.getWidth()/2)-40, rect.getHeight() - 90);
//                cos.drawString("SENTIDO DESCENDENTE");
//                cos.endText();
//                cos.addLine(60, rect.getHeight() - 98, 550, rect.getHeight() - 98);
//                int d=30;
//                 cos.beginText();
//                cos.setFont(fontPlain, 6);
//                cos.moveTextPositionByAmount(61, rect.getHeight() - 110);
//                cos.drawString("Nro de tren");
//                cos.endText();
//                c+=factorEspacio;
//                cos.beginText();
//                cos.setFont(fontPlain, 6);
//                cos.moveTextPositionByAmount(71 + d, rect.getHeight() - 110);
//                cos.drawString("Via S");
//                cos.endText();
//                cos.addLine(70 + d, 70, 70 + d,  rect.getHeight() - 98);
//                d += 30+factorEspacio;
//                cos.addLine(60 + d, 70, 60 + d,  rect.getHeight() - 98);
//                
//                for (int i = 0; i < estDes.size(); i++) {
//                     cos.beginText();
//                    cos.setFont(fontPlain, 6);
//                    cos.moveTextPositionByAmount(61 + d, rect.getHeight() - 110);
//                    cos.drawString("" + estDes.get(i).getAbrevEstacion());
//                    cos.endText();
//                    d += 30+factorEspacio;
//
//                    cos.addLine(60 + d, 70, 60 + d, rect.getHeight() - 98);
//                    
//                }
//               
//                cos.beginText();
//                cos.setFont(fontPlain, 6);
//                cos.moveTextPositionByAmount(61 + d, rect.getHeight() - 110);
//                cos.drawString("Via LL");
//                cos.endText();
//                d+=factorEspacio;
//                 cos.addLine(60 + d + 20, 70, 60 + d + 20,  rect.getHeight() - 98);
//                cos.beginText();
//                cos.setFont(fontPlain, 5);
//                cos.moveTextPositionByAmount(61 + d+20, rect.getHeight() - 110);
//                cos.drawString("Observaciones");
//                cos.endText();
//                d+=40+factorEspacio;
//               cos.addLine(60, rect.getHeight() - 98, 550, rect.getHeight() - 98);
//                cos.addLine(60, rect.getHeight() - 112, 550, rect.getHeight() - 112);
//                
//            
//            }
//
//            
//            cos.closeAndStroke();
//
//            cos.close();
//
//            document.save(outputFileName);
//            document.close();
//
//        } catch (Exception fnfex) {
//            fnfex.printStackTrace();
//            System.out.println("No image for you");
//        }
//        
//        
//        
//        
//        
//        
//        
//        
//
//    }
//
//    public void generarDocTren(int idLinea) throws IOException, COSVisitorException {
//       
////        String outputFileName = "DocumentoDeTren.pdf";
////        String nota = "";
////        String opTren = "";
//////        int idLinea=1;
////
////        // Create a document and add a page to it
////        boolean primerEscrito = true;
////        LineaJpaController ljc = new LineaJpaController(Conex.getEmf());
////        Linea l = ljc.findLinea(idLinea);
////        PDDocument document = new PDDocument();
////        PDPage page1 = new PDPage(PDPage.PAGE_SIZE_A4);
////        // PDPage.PAGE_SIZE_LETTER is also possible
////        PDRectangle rect = page1.getMediaBox();
////        // rect can be used to get the page width and height
////        document.addPage(page1);
////
////        // Create a new font object selecting one of the PDF base fonts
////        PDFont fontPlain = PDType1Font.HELVETICA;
////        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
////        PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
////        PDFont fontMono = PDType1Font.COURIER;
////
////        // Start a new content stream which will "hold" the to be created content
////        PDPageContentStream cos = new PDPageContentStream(document, page1);
////
////        int line = 0;
////        int regAsc = 0;
////        int reg = 10;
////        int regDesc = 0;
////        // Define a text content stream using the selected font, move the cursor and draw some text
////        //  add an image
////        try {
////            BufferedImage awtImage = ImageIO.read(new File("web/img/header ife.png"));
////            PDXObjectImage ximage = new PDPixelMap(document, awtImage);
////            float scale = 0.5f; // alter this value to set the image size
////            //cos.drawImage(ximage, ximage.getWidth(), ximage.getHeight());
////            cos.drawXObject(ximage, 60, 800, ximage.getWidth() * scale, ximage.getHeight() * scale);
////
////        } catch (Exception fnfex) {
////            fnfex.printStackTrace();
////            System.out.println("No image for you");
////        }
////
////        //cuadro principal
////        cos.setLineWidth(1);
////        cos.addLine(60, 760, 550, 760);
////        cos.addLine(60, 60, 550, 60);
////        cos.addLine(60, 60, 60, 760);
////        cos.addLine(550, 60, 550, 760);
////        
////        cos.addLine(60, 70, 550, 70);
////                
////                 cos.beginText();
////                cos.setFont(fontPlain, 10);
////                cos.moveTextPositionByAmount((rect.getWidth()/2)-120,61);
////                cos.drawString("ENTRA EN VIGENCIA A PARTIR DEL 18/03/2015");
////                cos.endText();
////        cos.closeAndStroke();
////        RestriccionJpaController rjc = new RestriccionJpaController(Conex.getEmf());
////        List<Restriccion> rAsc = rjc.buscarIdLineaAscendenteDocTren(idLinea, l.getVelocidadLinea());
////        List<Restriccion> rDesc = rjc.buscarIdLineaDescendenteDocTren(idLinea, l.getVelocidadLinea());
////        List<Restriccion> rMayor;
////
////        if (rAsc.size() > rDesc.size()) {
////            rMayor = rAsc;
////        } else {
////            rMayor = rDesc;
////        }
////        cos.beginText();
////        cos.setFont(fontBold, 14);
////        cos.moveTextPositionByAmount(60, rect.getHeight() - 60);
////        cos.drawString("DOCUMENTO DE TREN N° 02.2.15");
////        cos.endText();
//////        cos.beginText();
//////        cos.setFont(fontBold, 14);
//////        cos.moveTextPositionByAmount(200, rect.getHeight() - 60 * (line));
//////        cos.drawString("Velocidad ASC");
//////        cos.endText();
////
////        cos.beginText();
////        cos.setFont(fontPlain, 12);
////        cos.moveTextPositionByAmount(200, rect.getHeight() - 75);
////        cos.drawString("LINEA: " + l.getNombreLinea());
////        cos.endText();
////        cos.beginText();
////        cos.setFont(fontPlain, 8);
////        cos.moveTextPositionByAmount(61, rect.getHeight() - 100);
////        cos.drawString("Operaciones del Tren: ");
////        cos.endText();
////        cos.addLine(60, rect.getHeight() - 102, 550, rect.getHeight() - 102);
////        cos.closeAndStroke();
////        cos.beginText();
////        cos.setFont(fontPlain, 8);
////        cos.moveTextPositionByAmount(61, rect.getHeight() - 110);
////        cos.drawString("Nota: ");
////        cos.endText();
////        cos.addLine(60, rect.getHeight() - 112, 550, rect.getHeight() - 112);
////        cos.closeAndStroke();
////        cos.beginText();
////        cos.setFont(fontPlain, 8);
////        cos.moveTextPositionByAmount(61, rect.getHeight() - 120);
////        cos.drawString("Limitaciones: ");
////        cos.endText();
////        cos.beginText();
////        cos.setFont(fontPlain, 8);
////        cos.moveTextPositionByAmount(61, rect.getHeight() - 130);
////        cos.drawString("Circular a una velocidad maxima de " + l.getVelocidadLinea() + " Km/h");
////        cos.endText();
////        cos.beginText();
////        cos.setFont(fontBold, 8);
////        cos.moveTextPositionByAmount(61, rect.getHeight() - 140);
////        cos.drawString("Sentido Ascendente");
////        cos.endText();
////
////        cos.beginText();
////        cos.setFont(fontBold, 8);
////        cos.moveTextPositionByAmount(61, rect.getHeight() - 325);
////        cos.drawString("Sentido Descendente");
////        cos.endText();
////        cos.addLine(60, rect.getHeight() - 482, 550, rect.getHeight() - 482);
////        cos.closeAndStroke();
////        cos.beginText();
////        cos.setFont(fontBold, 8);
////        cos.moveTextPositionByAmount(61, rect.getHeight() - 490);
////        cos.drawString("Instrucciones: ");
////        cos.endText();
////        cos.addLine(60, rect.getHeight() - 532, 550, rect.getHeight() - 532);
////        cos.closeAndStroke();
////        cos.beginText();
////        cos.setFont(fontBold, 8);
////        cos.moveTextPositionByAmount(61, rect.getHeight() - 540);
////        cos.drawString("Comunicaciones: ");
////        cos.endText();
////        cos.addLine(60, rect.getHeight() - 582, 550, rect.getHeight() - 582);
////        cos.closeAndStroke();
////        cos.beginText();
////        cos.setFont(fontBold, 8);
////        cos.moveTextPositionByAmount(61, rect.getHeight() - 590);
////        cos.drawString("Precauciones: ");
////        cos.endText();
////        cos.addLine(60, rect.getHeight() - 632, 550, rect.getHeight() - 632);
////        cos.closeAndStroke();
////        System.out.println(rect.getHeight());
////        int j = 0;
////        int a = 170;
////        int b = 150;
////        for (int i = 0; i <= rMayor.size() - 1; i++) {
////
////            if ((regDesc < rDesc.size() && regDesc < reg) || (regAsc < reg && regAsc < rAsc.size())) {
////                // System.out.println(regDesc);
////                if (((regAsc == (reg - 10)) && i < rAsc.size()) || (regDesc == (reg - 10) && i < rDesc.size())) {
////                    if (rAsc.isEmpty() != true) {
////                        if (regAsc == (reg - 10) && i < rAsc.size() && rAsc.get(i).getVelocidadMaxAscendente() < l.getVelocidadLinea()) {
////                            cos.beginText();
////                            cos.setFont(fontPlain, 8);
////                            cos.moveTextPositionByAmount(70, rect.getHeight() - 155);
////
////                            cos.drawString("*PK " + rAsc.get(i).getProgInicio() + " al " + rAsc.get(i).getProgFinal() + " circular a una velocidad no mayor de " + rAsc.get(i).getVelocidadMaxAscendente() + " Km/h");
////                            cos.endText();
////                            regAsc++;
////                        }
////                    } else {
////                        regAsc++;
////                    }
////                    if (rDesc.isEmpty() != true) {
////                        if (regDesc == (reg - 10) && i < rDesc.size() && rDesc.get(i).getVelocidadMaxDescendente() < l.getVelocidadLinea()) {
////                            cos.beginText();
////                            cos.setFont(fontPlain, 8);
////                            cos.moveTextPositionByAmount(70, rect.getHeight() - 335);
////                            System.out.println(i);
////                            cos.drawString("*PK " + rDesc.get(i).getProgFinal() + " al " + rDesc.get(i).getProgInicio() + " circular a una velocidad no mayor de " + rDesc.get(i).getVelocidadMaxDescendente() + " Km/h");
////                            cos.endText();
////                            regDesc++;
////                        }
////                    } else {
////                        regDesc++;
////                    }
////                } else {
////                    
////                    if (regAsc < reg && i < rAsc.size() && rAsc.get(i).getVelocidadMaxAscendente() < l.getVelocidadLinea()) {
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(70, rect.getHeight() - a);
////                        cos.drawString("*PK " + rAsc.get(i).getProgInicio() + " al " + rAsc.get(i).getProgFinal() + " circular a una velocidad no mayor de " + rAsc.get(i).getVelocidadMaxAscendente() + " Km/h");
////                        cos.endText();
////                        a += 15;
////                        regAsc++;
////                    }
////                    if (regDesc < reg && i < rDesc.size() && rDesc.get(i).getVelocidadMaxDescendente() < l.getVelocidadLinea()) {
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        if (primerEscrito == true) {
////                            cos.moveTextPositionByAmount(70, rect.getHeight() - (200 + b));
////                        } else {
////                            cos.moveTextPositionByAmount(70, rect.getHeight() - (195 + b));
////                        }
////                        cos.drawString("*PK " + rDesc.get(i).getProgFinal() + " al " + rDesc.get(i).getProgInicio() + " circular a una velocidad no mayor de " + rDesc.get(i).getVelocidadMaxDescendente() + " Km/h");
////                        cos.endText();
////                        b += 15;
////                        regDesc++;
////                    }
////
////                }
////
//////                cos.beginText();
//////                cos.setFont(fontPlain, 12);
//////                cos.moveTextPositionByAmount(200, rect.getHeight() - 25 * (line+1));
//////                cos.drawString("");
//////                cos.endText();
//////                float a= 0.5f;
//////                cos.addLine(60, 25 * (line-a), 550, 25 * (line-a));
//////                cos.closeAndStroke();
////                //cos.beginText();
////            } else {
////
////                page1 = new PDPage(PDPage.PAGE_SIZE_A4);
////                // PDPage.PAGE_SIZE_LETTER is also possible
////
////                document.addPage(page1);
////                rect = page1.getMediaBox();
////                cos.close();
////                cos = new PDPageContentStream(document, page1);
////
////                try {
////                    BufferedImage awtImage = ImageIO.read(new File("web/img/header ife.png"));
////                    PDXObjectImage ximage = new PDPixelMap(document, awtImage);
////                    float scale = 0.5f; // alter this value to set the image size
////                    //cos.drawImage(ximage, ximage.getWidth(), ximage.getHeight());
////                    cos.drawXObject(ximage, 60, 800, ximage.getWidth() * scale, ximage.getHeight() * scale);
////
////                } catch (Exception fnfex) {
////                    fnfex.printStackTrace();
////                    System.out.println("No image for you");
////                }
////                //   cos.setLineWidth(1);
////                cos.beginText();
////                cos.setFont(fontBold, 14);
////                cos.moveTextPositionByAmount(60, rect.getHeight() - 60);
////                cos.drawString("DOCUMENTO DE TREN N° 02.2.15");
////                cos.endText();
////                
////                 cos.addLine(60, 70, 550, 70);
////                 cos.beginText();
////                cos.setFont(fontPlain, 10);
////                cos.moveTextPositionByAmount((rect.getWidth()/2)-120,61);
////                cos.drawString("ENTRA EN VIGENCIA A PARTIR DEL 18/03/2015");
////                cos.endText();
//////        cos.beginText();
//////        cos.setFont(fontBold, 14);
//////        cos.moveTextPositionByAmount(200, rect.getHeight() - 60 * (line));
//////        cos.drawString("Velocidad ASC");
//////        cos.endText();
////
////                cos.beginText();
////                cos.setFont(fontPlain, 12);
////                cos.moveTextPositionByAmount(200, rect.getHeight() - 75);
////                cos.drawString("LINEA: " + l.getNombreLinea());
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 100);
////                cos.drawString("Operaciones del Tren: ");
////                cos.endText();
////                cos.addLine(60, rect.getHeight() - 102, 550, rect.getHeight() - 102);
////                cos.closeAndStroke();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 110);
////                cos.drawString("Nota: ");
////                cos.endText();
////                cos.addLine(60, rect.getHeight() - 112, 550, rect.getHeight() - 112);
////                cos.closeAndStroke();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 120);
////                cos.drawString("Limitaciones: ");
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 130);
////                cos.drawString("Circular a una velocidad maxima de " + l.getVelocidadLinea() + " Km/h");
////                cos.endText();
////
////                cos.beginText();
////                cos.setFont(fontBold, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 140);
////                cos.drawString("Sentido Ascendente");
////                cos.endText();
////
////                cos.beginText();
////                cos.setFont(fontBold, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 325);
////                cos.drawString("Sentido Descendente");
////                cos.endText();
////
////                cos.addLine(60, rect.getHeight() - 482, 550, rect.getHeight() - 482);
////                cos.closeAndStroke();
////                cos.beginText();
////                cos.setFont(fontBold, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 490);
////                cos.drawString("Instrucciones: ");
////                cos.endText();
////                cos.addLine(60, rect.getHeight() - 532, 550, rect.getHeight() - 532);
////                cos.closeAndStroke();
////                cos.beginText();
////                cos.setFont(fontBold, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 540);
////                cos.drawString("Comunicaciones: ");
////                cos.endText();
////                cos.addLine(60, rect.getHeight() - 582, 550, rect.getHeight() - 582);
////                cos.closeAndStroke();
////                cos.beginText();
////                cos.setFont(fontBold, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 590);
////                cos.drawString("Precauciones: ");
////                cos.endText();
////                cos.addLine(60, rect.getHeight() - 632, 550, rect.getHeight() - 632);
////                cos.closeAndStroke();
////                line = 0;
////                j = 0;
////                a = 154;
////                b = 154;
////                i--;
////                reg += 10;
////                primerEscrito = false;
////                //regAsc = 0;
////                //regDesc = 0;
//////                cos.beginText();
//////                cos.setFont(fontPlain, 12);
//////                cos.moveTextPositionByAmount(60, rect.getHeight() - 25 * (++line));
//////                cos.drawString("");
//////                cos.endText();
//////                cos.beginText();
//////                cos.setFont(fontPlain, 12);
//////                cos.moveTextPositionByAmount(60, rect.getHeight() - 25 * (++line));
//////                cos.drawString("");
//////                cos.endText();
////
////                cos.setLineWidth(1);
////                cos.addLine(60, 760, 550, 760);
////                cos.addLine(60, 60, 550, 60);
////                cos.addLine(60, 60, 60, 760);
////                cos.addLine(550, 60, 550, 760);
////
////                //
////                // cos.addLine(190, 60, 190, 760);
////                // cos.addLine(200, 250, 400, 250);
////                cos.closeAndStroke();
////            }
////            j++;
//////             if(j>50){
//////             break;
//////             }
////
////        }
////
//////
//////        cos.beginText();
//////        cos.setFont(fontBold, 12);
//////        cos.moveTextPositionByAmount(100, rect.getHeight() - 50*(++line));
//////        cos.drawString("Bold");
//////        cos.endText();
//////
//////        cos.beginText();
//////        cos.setFont(fontMono, 12);
//////        cos.setNonStrokingColor(Color.BLUE);
//////        cos.moveTextPositionByAmount(100, rect.getHeight() - 50*(++line));
//////        cos.drawString("Monospaced blue");
//////        cos.endText();
////        // Make sure that the content stream is closed:
////        
////        cos.close();
////
//////        PDPage page2 = new PDPage(PDPage.PAGE_SIZE_A4);
//////        document.addPage(page2);
//////        cos = new PDPageContentStream(document, page2);
//////
//////        // draw a red box in the lower left hand corner
//////        cos.setNonStrokingColor(Color.RED);
//////        cos.fillRect(10, 10, 100, 100);
//////
//////        // add two lines of different widths
//////        cos.setLineWidth(1);
//////        cos.addLine(200, 250, 400, 250);
//////        cos.closeAndStroke();
//////        cos.setLineWidth(5);
//////        cos.addLine(200, 300, 400, 300);
//////        cos.closeAndStroke();
////        // add an image
//////        try {
//////            BufferedImage awtImage = ImageIO.read(new File("Simple.jpg"));
//////            PDXObjectImage ximage = new PDPixelMap(document, awtImage);
//////            float scale = 0.5f; // alter this value to set the image size
//////            cos.drawXObject(ximage, 100, 400, ximage.getWidth()*scale, ximage.getHeight()*scale);
//////        } catch (FileNotFoundException fnfex) {
//////            System.out.println("No image for you");
//////        }
////        // close the content stream for page 2
//////        cos.close();
////        // Save the results and ensure that the document is properly closed:
////       
////        document.save(outputFileName);
////        document.close();
////       
//        
//    }
//    
//    public void documentoDetren(){
//        
////        int linea = Integer.parseInt(request.getParameter("idLinea"));
////        PrintWriter salida = response.getWriter();
////        HttpSession session = request.getSession();
////        PrintWriter out = response.getWriter();
////        Usuario usr = (Usuario) session.getAttribute("usuario");
////        try {
////
////            String nota = request.getParameter("nota");
////            MaterialRodanteJpaController mrjc = new MaterialRodanteJpaController(Conex.getEmf());
////            MaterialRodante mr = mrjc.findMaterialRodante(Integer.parseInt(request.getParameter("materialRodante")));
////            String comunicaciones = request.getParameter("comunicaciones");
////            List<String> comunicaciones1 = new ArrayList<>();
////            String instrucciones = request.getParameter("instrucciones");
////            List<String> instrucciones1 = new ArrayList<>();
////            String precauciones = request.getParameter("precauciones");
////            List<String> precauciones1 = new ArrayList<>();
////            String nombre = request.getParameter("nombre");
////            String vigencia = request.getParameter("vigencia");
////            int numeroPag = 1;
////            String[] fecha = vigencia.split("-");
////            System.out.println(instrucciones.length());
////            char es = ' ';
////            int cant = 110;
////            int cant1 = 240;
////            int cant2 = 370;
////            int cant3 = 500;
////            int cant4 = 630;
////            if (instrucciones.length() > 110) {
////                if (instrucciones.length() > 110 && instrucciones.length() <= 240) {
////
////                    while (instrucciones.charAt(cant) != es) {
////                        cant++;
////                    }
////                    instrucciones1.add(instrucciones.substring(0, cant));
////                    instrucciones1.add(instrucciones.substring(cant, instrucciones.length()));
////                }
////                if (instrucciones.length() > 240 && instrucciones.length() <= 370) {
////
////                    while (instrucciones.charAt(cant) != es) {
////                        cant++;
////                    }
////                    while (instrucciones.charAt(cant1) != es) {
////                        cant1++;
////                    }
////                    instrucciones1.add(instrucciones.substring(0, cant));
////                    instrucciones1.add(instrucciones.substring(cant, cant1));
////
////                    instrucciones1.add(instrucciones.substring(cant1, instrucciones.length()));
////                }
////                if (instrucciones.length() > 370 && instrucciones.length() <= 500) {
////
////                    while (instrucciones.charAt(cant) != es) {
////                        cant++;
////                    }
////                    while (instrucciones.charAt(cant1) != es) {
////                        cant1++;
////                    }
////                    while (instrucciones.charAt(cant2) != es) {
////                        cant2++;
////                    }
////                    instrucciones1.add(instrucciones.substring(0, cant));
////                    instrucciones1.add(instrucciones.substring(cant, cant1));
////
////                    instrucciones1.add(instrucciones.substring(cant1, cant2));
////                    instrucciones1.add(instrucciones.substring(cant2, instrucciones.length()));
////                }
////                if (instrucciones.length() > 500 && instrucciones.length() <= 630) {
////
////                    while (instrucciones.charAt(cant) != es) {
////                        cant++;
////                    }
////                    while (instrucciones.charAt(cant1) != es) {
////                        cant1++;
////                    }
////                    while (instrucciones.charAt(cant2) != es) {
////                        cant2++;
////                    }
////                    while (instrucciones.charAt(cant3) != es) {
////                        cant3++;
////                    }
////                    instrucciones1.add(instrucciones.substring(0, cant));
////                    instrucciones1.add(instrucciones.substring(cant, cant1));
////
////                    instrucciones1.add(instrucciones.substring(cant1, cant2));
////                    instrucciones1.add(instrucciones.substring(cant2, cant3));
////                    instrucciones1.add(instrucciones.substring(cant3, instrucciones.length()));
////                }
////                if (instrucciones.length() > 630) {
////
////                    while (instrucciones.charAt(cant) != es) {
////                        cant++;
////                    }
////                    while (instrucciones.charAt(cant1) != es) {
////                        cant1++;
////                    }
////                    while (instrucciones.charAt(cant2) != es) {
////                        cant2++;
////                    }
////                    while (instrucciones.charAt(cant3) != es) {
////                        cant3++;
////                    }
////                    while (instrucciones.charAt(cant4) != es) {
////                        cant4++;
////                    }
////                    instrucciones1.add(instrucciones.substring(0, cant));
////                    instrucciones1.add(instrucciones.substring(cant, cant1));
////
////                    instrucciones1.add(instrucciones.substring(cant1, cant2));
////                    instrucciones1.add(instrucciones.substring(cant2, cant3));
////                    instrucciones1.add(instrucciones.substring(cant3, cant4));
////                    instrucciones1.add(instrucciones.substring(cant4, 760));
////                }
////
////            } else {
////                instrucciones1.add(instrucciones);
////            }
////            if (precauciones.length() > 110) {
////                es = ' ';
////                cant = 110;
////                cant1 = 240;
////                cant2 = 370;
////                cant3 = 500;
////                cant4 = 630;
////                if (precauciones.length() > 110 && precauciones.length() <= 240) {
////
////                    while (precauciones.charAt(cant) != es) {
////                        cant++;
////                    }
////                    precauciones1.add(precauciones.substring(0, cant));
////                    precauciones1.add(precauciones.substring(cant, precauciones.length()));
////                }
////                if (precauciones.length() > 240 && precauciones.length() <= 370) {
////
////                    while (precauciones.charAt(cant) != es) {
////                        cant++;
////                    }
////                    while (precauciones.charAt(cant1) != es) {
////                        cant1++;
////                    }
////                    precauciones1.add(precauciones.substring(0, cant));
////                    precauciones1.add(precauciones.substring(cant, cant1));
////
////                    precauciones1.add(precauciones.substring(cant1, precauciones.length()));
////                }
////                if (precauciones.length() > 370 && precauciones.length() <= 500) {
////
////                    while (precauciones.charAt(cant) != es) {
////                        cant++;
////                    }
////                    while (precauciones.charAt(cant1) != es) {
////                        cant1++;
////                    }
////                    while (precauciones.charAt(cant2) != es) {
////                        cant2++;
////                    }
////                    precauciones1.add(precauciones.substring(0, cant));
////                    precauciones1.add(precauciones.substring(cant, cant1));
////
////                    precauciones1.add(precauciones.substring(cant1, cant2));
////                    precauciones1.add(precauciones.substring(cant2, precauciones.length()));
////                }
////                if (precauciones.length() > 500 && precauciones.length() <= 630) {
////
////                    while (precauciones.charAt(cant) != es) {
////                        cant++;
////                    }
////                    while (precauciones.charAt(cant1) != es) {
////                        cant1++;
////                    }
////                    while (precauciones.charAt(cant2) != es) {
////                        cant2++;
////                    }
////                    while (precauciones.charAt(cant3) != es) {
////                        cant3++;
////                    }
////                    precauciones1.add(precauciones.substring(0, cant));
////                    precauciones1.add(precauciones.substring(cant, cant1));
////
////                    precauciones1.add(precauciones.substring(cant1, cant2));
////                    precauciones1.add(precauciones.substring(cant2, cant3));
////                    precauciones1.add(precauciones.substring(cant3, precauciones.length()));
////                }
////                if (precauciones.length() > 630) {
////
////                    while (precauciones.charAt(cant) != es) {
////                        cant++;
////                    }
////                    while (precauciones.charAt(cant1) != es) {
////                        cant1++;
////                    }
////                    while (precauciones.charAt(cant2) != es) {
////                        cant2++;
////                    }
////                    while (precauciones.charAt(cant3) != es) {
////                        cant3++;
////                    }
////                    while (precauciones.charAt(cant4) != es) {
////                        cant4++;
////                    }
////                    precauciones1.add(precauciones.substring(0, cant));
////                    precauciones1.add(precauciones.substring(cant, cant1));
////
////                    precauciones1.add(precauciones.substring(cant1, cant2));
////                    precauciones1.add(precauciones.substring(cant2, cant3));
////                    precauciones1.add(precauciones.substring(cant3, cant4));
////                    precauciones1.add(precauciones.substring(cant4, 760));
////                }
////            } else {
////                precauciones1.add(precauciones);
////            }
////            if (comunicaciones.length() > 110) {
////                es = ' ';
////                cant = 110;
////                cant1 = 240;
////                cant2 = 370;
////                cant3 = 500;
////                cant4 = 630;
////                if (comunicaciones.length() > 110 && comunicaciones.length() <= 240) {
////
////                    while (comunicaciones.charAt(cant) != es) {
////                        cant++;
////                    }
////                    comunicaciones1.add(comunicaciones.substring(0, cant));
////                    comunicaciones1.add(comunicaciones.substring(cant, comunicaciones.length()));
////                }
////                if (comunicaciones.length() > 240 && comunicaciones.length() <= 370) {
////
////                    while (comunicaciones.charAt(cant) != es) {
////                        cant++;
////                    }
////                    while (comunicaciones.charAt(cant1) != es) {
////                        cant1++;
////                    }
////                    comunicaciones1.add(comunicaciones.substring(0, cant));
////                    comunicaciones1.add(comunicaciones.substring(cant, cant1));
////
////                    comunicaciones1.add(comunicaciones.substring(cant1, comunicaciones.length()));
////                }
////                if (comunicaciones.length() > 370 && comunicaciones.length() <= 500) {
////
////                    while (comunicaciones.charAt(cant) != es) {
////                        cant++;
////                    }
////                    while (comunicaciones.charAt(cant1) != es) {
////                        cant1++;
////                    }
////                    while (comunicaciones.charAt(cant2) != es) {
////                        cant2++;
////                    }
////                    comunicaciones1.add(comunicaciones.substring(0, cant));
////                    comunicaciones1.add(comunicaciones.substring(cant, cant1));
////
////                    comunicaciones1.add(comunicaciones.substring(cant1, cant2));
////                    comunicaciones1.add(comunicaciones.substring(cant2, comunicaciones.length()));
////                }
////                if (comunicaciones.length() > 500 && comunicaciones.length() <= 630) {
////
////                    while (comunicaciones.charAt(cant) != es) {
////                        cant++;
////                    }
////                    while (comunicaciones.charAt(cant1) != es) {
////                        cant1++;
////                    }
////                    while (comunicaciones.charAt(cant2) != es) {
////                        cant2++;
////                    }
////                    while (comunicaciones.charAt(cant3) != es) {
////                        cant3++;
////                    }
////                    comunicaciones1.add(comunicaciones.substring(0, cant));
////                    comunicaciones1.add(comunicaciones.substring(cant, cant1));
////
////                    comunicaciones1.add(comunicaciones.substring(cant1, cant2));
////                    comunicaciones1.add(comunicaciones.substring(cant2, cant3));
////                    comunicaciones1.add(comunicaciones.substring(cant3, comunicaciones.length()));
////                }
////                if (comunicaciones.length() > 630) {
////
////                    while (comunicaciones.charAt(cant) != es) {
////                        cant++;
////                    }
////                    while (comunicaciones.charAt(cant1) != es) {
////                        cant1++;
////                    }
////                    while (comunicaciones.charAt(cant2) != es) {
////                        cant2++;
////                    }
////                    while (comunicaciones.charAt(cant3) != es) {
////                        cant3++;
////                    }
////                    while (comunicaciones.charAt(cant4) != es) {
////                        cant4++;
////                    }
////                    comunicaciones1.add(comunicaciones.substring(0, cant));
////                    comunicaciones1.add(comunicaciones.substring(cant, cant1));
////
////                    comunicaciones1.add(comunicaciones.substring(cant1, cant2));
////                    comunicaciones1.add(comunicaciones.substring(cant2, cant3));
////                    comunicaciones1.add(comunicaciones.substring(cant3, cant4));
////                    comunicaciones1.add(comunicaciones.substring(cant4, 760));
////                }
////            } else {
////                comunicaciones1.add(comunicaciones);
////            }
////
////            //int idLinea=1;
////            // Create a document and add a page to it
////            boolean primerEscrito = true;
////            LineaJpaController ljc = new LineaJpaController(Conex.getEmf());
////            Linea l = ljc.findLinea(linea);
////            PDDocument document = new PDDocument();
////            
////            PDStream pds=new PDStream(document);
////            InputStream is=pds.createInputStream();
////            
////            
////            PDPage page1 = new PDPage(PDPage.PAGE_SIZE_A4);
////            // PDPage.PAGE_SIZE_LETTER is also possible
////            PDRectangle rect = page1.getMediaBox();
////            // rect can be used to get the page width and height
////            document.addPage(page1);
////
////            // Create a new font object selecting one of the PDF base fonts
////            PDFont fontPlain = PDType1Font.HELVETICA;
////            PDFont fontBold = PDType1Font.HELVETICA_BOLD;
////            PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
////            PDFont fontMono = PDType1Font.COURIER;
////
////            // Start a new content stream which will "hold" the to be created content
////            PDPageContentStream cos = new PDPageContentStream(document, page1);
////
////            int line = 0;
////            int regAsc = 0;
////            int reg = 15;
////            int regDesc = 0;
////            // Define a text content stream using the selected font, move the cursor and draw some text
////            //  add an image
////            try {
////                URL url = getClass().getResource("/img/cintillo_s1.png");
////                System.out.println(url);
////                //  URI imageURL=getClass().getResource("img/header ife.png").toURI();
////                // File imagen=new File(imageURL);
////                //System.out.println(imagen);
////                BufferedImage awtImage = ImageIO.read(url);
////                //      ImageIcon icon=new ImageIcon(getClass().getResource("web/img/header ife.png"));
////
////                PDXObjectImage ximage = new PDJpeg(document, awtImage);
////                //PDXObjectImage ximage = new PDPixelMap(document, icon);
////                float scale = 0.5f; // alter this value to set the image size
////                //cos.drawImage(ximage, ximage.getWidth(), ximage.getHeight());
////                cos.drawXObject(ximage, 60, 800, ximage.getWidth() * scale, ximage.getHeight() * scale);
////
////            } catch (Exception fnfex) {
////                fnfex.printStackTrace();
////                System.out.println("No image for you");
////            }
////
////            //cuadro principal
////            cos.setLineWidth(1);
////            cos.addLine(60, 760, 550, 760);
////            cos.addLine(60, 60, 550, 60);
////            cos.addLine(60, 60, 60, 760);
////            cos.addLine(550, 60, 550, 760);
////
////            cos.closeAndStroke();
////            RestriccionJpaController rjc = new RestriccionJpaController(Conex.getEmf());
////            List<Restriccion> rAsc = rjc.buscarIdLineaAscendenteDocTren(linea, l.getVelocidadLinea());
////            List<Restriccion> rDesc = rjc.buscarIdLineaDescendenteDocTren(linea, l.getVelocidadLinea());
////            List<Restriccion> rMayor;
////
////            if (rAsc.size() > rDesc.size()) {
////                rMayor = rAsc;
////            } else {
////                rMayor = rDesc;
////            }
////            cos.beginText();
////            cos.setFont(fontBold, 14);
////            cos.moveTextPositionByAmount(60, rect.getHeight() - 60);
////            cos.drawString("DOCUMENTO DE TREN: " + nombre);
////            cos.endText();
////            cos.addLine(60, 70, 550, 70);
////
////            cos.beginText();
////            cos.setFont(fontPlain, 10);
////            cos.moveTextPositionByAmount((rect.getWidth() / 2) - 120, 61);
////            cos.drawString("ENTRA EN VIGENCIA A PARTIR DEL " + fecha[0] + "/" + fecha[1] + "/" + fecha[2]);
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(61, 51);
////            cos.drawString("Realizado por: " + usr.toString());
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontBold, 8);
////            cos.moveTextPositionByAmount(rect.getWidth() - 165, 51);
////            cos.drawString("Gerencia de Gestión de Tráfico");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontBold, 8);
////            cos.moveTextPositionByAmount(rect.getWidth() - 62, 31);
////            cos.drawString(numeroPag + "");
////            cos.endText();
////
////            cos.beginText();
////            cos.setFont(fontPlain, 12);
////            cos.moveTextPositionByAmount(200, rect.getHeight() - 75);
////            cos.drawString("LÍNEA: " + l.getNombreLinea());
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(61, rect.getHeight() - 100);
////            cos.drawString("Operaciones del Tren: ");
////            cos.endText();
////            cos.addLine(60, rect.getHeight() - 102, 550, rect.getHeight() - 102);
////            cos.closeAndStroke();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(61, rect.getHeight() - 110);
////            cos.drawString("Nota: ");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(82, rect.getHeight() - 110);
////            System.out.println("nota: " + nota.length());
////            cos.drawString(nota);
////            cos.endText();
////
////            cos.addLine(60, rect.getHeight() - 112, 550, rect.getHeight() - 112);
////            cos.closeAndStroke();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(61, rect.getHeight() - 120);
////            cos.drawString("Limitaciones: ");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(61, rect.getHeight() - 130);
////            cos.drawString("Circular a una velocidad máxima de " + l.getVelocidadLinea() + " Km/h");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontBold, 8);
////            cos.moveTextPositionByAmount(61, rect.getHeight() - 140);
////            cos.drawString("Sentido Ascendente");
////            cos.endText();
////
////            cos.beginText();
////            cos.setFont(fontBold, 8);
////            cos.moveTextPositionByAmount(61, rect.getHeight() - 380);
////            cos.drawString("Sentido Descendente");
////            cos.endText();
////            cos.addLine(60, rect.getHeight() - 617, 550, rect.getHeight() - 617);
////            cos.closeAndStroke();
////            cos.beginText();
////            cos.setFont(fontBold, 8);
////            cos.moveTextPositionByAmount(61, rect.getHeight() - 625);
////            cos.drawString("Instrucciones: ");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(117, rect.getHeight() - 625);
////            cos.drawString(instrucciones1.get(0));
////            System.out.println("instrucciones: " + instrucciones.length());
////            cos.endText();
////            if (instrucciones.length() > 110 && instrucciones.length() <= 240) {
////
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 635);
////                cos.drawString(instrucciones1.get(1));
////                cos.endText();
////            }
////            if (instrucciones.length() > 240 && instrucciones.length() <= 370) {
////
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 635);
////                cos.drawString(instrucciones1.get(1));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 645);
////                cos.drawString(instrucciones1.get(2));
////                cos.endText();
////            }
////            if (instrucciones.length() > 370 && instrucciones.length() <= 500) {
////
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 635);
////                cos.drawString(instrucciones1.get(1));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 645);
////                cos.drawString(instrucciones1.get(2));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 655);
////                cos.drawString(instrucciones1.get(3));
////                cos.endText();
////            }
////            if (instrucciones.length() > 500 && instrucciones.length() <= 630) {
////
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 635);
////                cos.drawString(instrucciones1.get(1));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 645);
////                cos.drawString(instrucciones1.get(2));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 655);
////                cos.drawString(instrucciones1.get(3));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 655);
////                cos.drawString(instrucciones1.get(4));
////                cos.endText();
////            }
////            if (instrucciones.length() > 630) {
////
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 635);
////                cos.drawString(instrucciones1.get(1));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 645);
////                cos.drawString(instrucciones1.get(2));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 655);
////                cos.drawString(instrucciones1.get(3));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 665);
////                cos.drawString(instrucciones1.get(4));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 675);
////                cos.drawString(instrucciones1.get(5));
////                cos.endText();
////            }
////
////            cos.beginText();
////            cos.setFont(fontBold, 8);
////            cos.moveTextPositionByAmount(61, rect.getHeight() - 675);
////            cos.drawString("Comunicaciones: ");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(127, rect.getHeight() - 675);
////            cos.drawString(comunicaciones1.get(0));
////            System.out.println("comunicaciones: " + comunicaciones.length());
////            cos.endText();
////            if (comunicaciones.length() > 110 && comunicaciones.length() <= 240) {
////
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 685);
////                cos.drawString(comunicaciones1.get(1));
////                cos.endText();
////            }
////            if (comunicaciones.length() > 240 && comunicaciones.length() <= 370) {
////
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 685);
////                cos.drawString(comunicaciones1.get(1));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 695);
////                cos.drawString(comunicaciones1.get(2));
////                cos.endText();
////            }
////            if (comunicaciones.length() > 370 && comunicaciones.length() <= 500) {
////
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 685);
////                cos.drawString(comunicaciones1.get(1));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 695);
////                cos.drawString(comunicaciones1.get(2));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 705);
////                cos.drawString(comunicaciones1.get(3));
////                cos.endText();
////            }
////            if (comunicaciones.length() > 500 && comunicaciones.length() <= 630) {
////
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 685);
////                cos.drawString(comunicaciones1.get(1));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 695);
////                cos.drawString(comunicaciones1.get(2));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 705);
////                cos.drawString(comunicaciones1.get(3));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 715);
////                cos.drawString(comunicaciones1.get(4));
////                cos.endText();
////            }
////            if (comunicaciones.length() > 630) {
////
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 685);
////                cos.drawString(comunicaciones1.get(1));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 695);
////                cos.drawString(comunicaciones1.get(2));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 705);
////                cos.drawString(comunicaciones1.get(3));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 715);
////                cos.drawString(comunicaciones1.get(4));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 725);
////                cos.drawString(comunicaciones1.get(5));
////                cos.endText();
////            }
////            cos.addLine(60, rect.getHeight() - 667, 550, rect.getHeight() - 667);
////            cos.closeAndStroke();
////            cos.beginText();
////            cos.setFont(fontBold, 8);
////            cos.moveTextPositionByAmount(61, rect.getHeight() - 725);
////            cos.drawString("Precauciones: ");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(117, rect.getHeight() - 725);
////            cos.drawString(precauciones1.get(0));
////            System.out.println("precauciones: " + precauciones.length());
////            cos.endText();
////            if (precauciones.length() > 110 && precauciones.length() <= 240) {
////
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 735);
////                cos.drawString(precauciones1.get(1));
////                cos.endText();
////            }
////            if (precauciones.length() > 240 && precauciones.length() <= 370) {
////
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 735);
////                cos.drawString(precauciones1.get(1));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 745);
////                cos.drawString(precauciones1.get(2));
////                cos.endText();
////            }
////            if (precauciones.length() > 370 && precauciones.length() <= 500) {
////
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 735);
////                cos.drawString(precauciones1.get(1));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 745);
////                cos.drawString(precauciones1.get(2));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 755);
////                cos.drawString(precauciones1.get(3));
////                cos.endText();
////            }
////            if (precauciones.length() > 500 && precauciones.length() <= 630) {
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 735);
////                cos.drawString(precauciones1.get(1));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 745);
////                cos.drawString(precauciones1.get(2));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 755);
////                cos.drawString(precauciones1.get(3));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 765);
////                cos.drawString(precauciones1.get(4));
////                cos.endText();
////            }
////            if (precauciones.length() > 630) {
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 735);
////                cos.drawString(precauciones1.get(1));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 745);
////                cos.drawString(precauciones1.get(2));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 755);
////                cos.drawString(precauciones1.get(3));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 765);
////                cos.drawString(precauciones1.get(4));
////                cos.endText();
////                cos.beginText();
////                cos.setFont(fontPlain, 8);
////                cos.moveTextPositionByAmount(61, rect.getHeight() - 775);
////                cos.drawString(precauciones1.get(5));
////                cos.endText();
////            }
////            cos.addLine(60, rect.getHeight() - 717, 550, rect.getHeight() - 717);
////            cos.closeAndStroke();
////            System.out.println(rect.getHeight());
////            int j = 0;
////            int a = 170;
////            int b = 150;
////            for (int i = 0; i <= rMayor.size() - 1; i++) {
////
////                if ((regDesc < rDesc.size() && regDesc < reg) || (regAsc < reg && regAsc < rAsc.size())) {
////
////                    if (((regAsc == (reg - 15)) && i < rAsc.size()) || (regDesc == (reg - 15) && i < rDesc.size())) {
////                        if (rAsc.isEmpty() != true) {
////                            if (regAsc == (reg - 15) && i < rAsc.size() && rAsc.get(i).getVelocidadMaxAscendente() < l.getVelocidadLinea()) {
////                                cos.beginText();
////                                cos.setFont(fontPlain, 8);
////                                cos.moveTextPositionByAmount(70, rect.getHeight() - 155);
////
////                                int pkI = (int) (rAsc.get(i).getProgInicio() / 1000);
////                                int pkI1 = (int) (((rAsc.get(i).getProgInicio() / 1000) - pkI) * 1000);
////                                int pkF = (int) (rAsc.get(i).getProgFinal() / 1000);
////                                int pkF1 = (int) (((rAsc.get(i).getProgFinal() / 1000) - pkF) * 1000);
////
////                                cos.drawString("*PK " + pkI + "+" + pkI1 + " al " + pkF + "+" + pkF1 + " circular a una velocidad no mayor de " + rAsc.get(i).getVelocidadMaxAscendente() + " Km/h  " + rAsc.get(i).getObservacion());
////
////                                cos.endText();
////                                regAsc++;
////                            }
////                        } else {
////                            regAsc++;
////                        }
////                        if (rDesc.isEmpty() != true) {
////                            if (regDesc == (reg - 15) && i < rDesc.size() && rDesc.get(i).getVelocidadMaxDescendente() < l.getVelocidadLinea()) {
////                                cos.beginText();
////                                cos.setFont(fontPlain, 8);
////                                cos.moveTextPositionByAmount(70, rect.getHeight() - 390);
////                                System.out.println(i);
////
////                                int pkI = (int) (rDesc.get(i).getProgFinal() / 1000);
////                                int pkI1 = (int) (((rDesc.get(i).getProgFinal() / 1000) - pkI) * 1000);
////                                int pkF = (int) (rDesc.get(i).getProgInicio() / 1000);
////                                int pkF1 = (int) (((rDesc.get(i).getProgInicio() / 1000) - pkF) * 1000);
////
////                                cos.drawString("*PK " + pkI + "+" + pkI1 + " al " + pkF + "+" + pkF1 + " circular a una velocidad no mayor de " + rDesc.get(i).getVelocidadMaxDescendente() + " Km/h " + rDesc.get(i).getObservacion());
////
////                                cos.endText();
////                                regDesc++;
////                            }
////                        } else {
////                            regDesc++;
////                        }
////                    } else {
////                        if (regAsc < reg && i < rAsc.size() && rAsc.get(i).getVelocidadMaxAscendente() < l.getVelocidadLinea()) {
////                            cos.beginText();
////                            cos.setFont(fontPlain, 8);
////                            cos.moveTextPositionByAmount(70, rect.getHeight() - a);
////
////                            int pkI = (int) (rAsc.get(i).getProgInicio() / 1000);
////                            int pkI1 = (int) (((rAsc.get(i).getProgInicio() / 1000) - pkI) * 1000);
////                            int pkF = (int) (rAsc.get(i).getProgFinal() / 1000);
////                            int pkF1 = (int) (((rAsc.get(i).getProgFinal() / 1000) - pkF) * 1000);
////
////                            cos.drawString("*PK " + pkI + "+" + pkI1 + " al " + pkF + "+" + pkF1 + " circular a una velocidad no mayor de " + rAsc.get(i).getVelocidadMaxAscendente() + " Km/h " + rAsc.get(i).getObservacion());
////
////                            cos.endText();
////                            a += 15;
////                            regAsc++;
////                        }
////                        if (regDesc < reg && i < rDesc.size() && rDesc.get(i).getVelocidadMaxDescendente() < l.getVelocidadLinea()) {
////                            cos.beginText();
////                            cos.setFont(fontPlain, 8);
//////                        if (primerEscrito == true) {
////                            cos.moveTextPositionByAmount(70, rect.getHeight() - (255 + b));
//////                        } else {
//////                            cos.moveTextPositionByAmount(70, rect.getHeight() - (250 + b));
//////                        }
////                            int pkI = (int) (rDesc.get(i).getProgFinal() / 1000);
////                            int pkI1 = (int) (((rDesc.get(i).getProgFinal() / 1000) - pkI) * 1000);
////                            int pkF = (int) (rDesc.get(i).getProgInicio() / 1000);
////                            int pkF1 = (int) (((rDesc.get(i).getProgInicio() / 1000) - pkF) * 1000);
////
////                            cos.drawString("*PK " + pkI + "+" + pkI1 + " al " + pkF + "+" + pkF1 + " circular a una velocidad no mayor de " + rDesc.get(i).getVelocidadMaxDescendente() + " Km/h " + rDesc.get(i).getObservacion());
////                            cos.endText();
////                            b += 15;
////                            regDesc++;
////                        }
////
////                    }
////
////                } else {
////
////                    page1 = new PDPage(PDPage.PAGE_SIZE_A4);
////                    document.addPage(page1);
////                    rect = page1.getMediaBox();
////                    cos.close();
////                    cos = new PDPageContentStream(document, page1);
////
////                    try {
////                        URL url = getClass().getResource("/img/cintillo_s1.png");
////                        System.out.println(url);
////                        //  URI imageURL=getClass().getResource("img/header ife.png").toURI();
////                        // File imagen=new File(imageURL);
////                        //System.out.println(imagen);
////                        BufferedImage awtImage = ImageIO.read(url);
////                        PDXObjectImage ximage = new PDPixelMap(document, awtImage);
////                        float scale = 0.5f; // alter this value to set the image size
////                        //cos.drawImage(ximage, ximage.getWidth(), ximage.getHeight());
////                        cos.drawXObject(ximage, 60, 800, ximage.getWidth() * scale, ximage.getHeight() * scale);
////
////                    } catch (Exception fnfex) {
////                        fnfex.printStackTrace();
////                        System.out.println("No image for you");
////                    }
////                    //   cos.setLineWidth(1);
////                    cos.beginText();
////                    cos.setFont(fontBold, 14);
////                    cos.moveTextPositionByAmount(60, rect.getHeight() - 60);
////                    cos.drawString("DOCUMENTO DE TREN: " + nombre);
////                    cos.endText();
//////        cos.beginText();
////                    cos.addLine(60, 70, 550, 70);
////
////                    cos.beginText();
////                    cos.setFont(fontPlain, 10);
////                    cos.moveTextPositionByAmount((rect.getWidth() / 2) - 120, 61);
////                    cos.drawString("ENTRA EN VIGENCIA A PARTIR DEL " + fecha[0] + "/" + fecha[1] + "/" + fecha[2]);
////                    cos.endText();
////                    cos.beginText();
////                    cos.setFont(fontPlain, 8);
////                    cos.moveTextPositionByAmount(61, 51);
////                    cos.drawString("Realizado por: " + usr.toString());
////                    cos.endText();
////                    cos.beginText();
////                    cos.setFont(fontBold, 8);
////                    cos.moveTextPositionByAmount(rect.getWidth() - 165, 51);
////                    cos.drawString("Gerencia de Gestión de Tráfico");
////                    cos.endText();
////                    numeroPag += 1;
////                    cos.beginText();
////                    cos.setFont(fontPlain, 8);
////                    cos.moveTextPositionByAmount(rect.getWidth() - 62, 31);
////                    cos.drawString(numeroPag + "");
////                    cos.endText();
////                    cos.beginText();
////                    cos.setFont(fontPlain, 12);
////                    cos.moveTextPositionByAmount(200, rect.getHeight() - 75);
////                    cos.drawString("LÍNEA: " + l.getNombreLinea());
////                    cos.endText();
////                    cos.beginText();
////                    cos.setFont(fontPlain, 8);
////                    cos.moveTextPositionByAmount(61, rect.getHeight() - 100);
////                    cos.drawString("Operaciones del Tren: ");
////                    cos.endText();
////                    cos.addLine(60, rect.getHeight() - 102, 550, rect.getHeight() - 102);
////                    cos.closeAndStroke();
////                    cos.beginText();
////                    cos.setFont(fontPlain, 8);
////                    cos.moveTextPositionByAmount(61, rect.getHeight() - 110);
////                    cos.drawString("Nota: ");
////                    cos.endText();
////                    cos.beginText();
////                    cos.setFont(fontPlain, 8);
////                    cos.moveTextPositionByAmount(82, rect.getHeight() - 110);
////                    System.out.println("nota: " + nota.length());
////                    cos.drawString(nota);
////                    cos.endText();
////
////                    cos.addLine(60, rect.getHeight() - 112, 550, rect.getHeight() - 112);
////                    cos.closeAndStroke();
////                    cos.beginText();
////                    cos.setFont(fontPlain, 8);
////                    cos.moveTextPositionByAmount(61, rect.getHeight() - 120);
////                    cos.drawString("Limitaciones: ");
////                    cos.endText();
////                    cos.beginText();
////                    cos.setFont(fontPlain, 8);
////                    cos.moveTextPositionByAmount(61, rect.getHeight() - 130);
////                    cos.drawString("Circular a una velocidad máxima de " + l.getVelocidadLinea() + " Km/h");
////                    cos.endText();
////                    cos.beginText();
////                    cos.setFont(fontBold, 8);
////                    cos.moveTextPositionByAmount(61, rect.getHeight() - 140);
////                    cos.drawString("Sentido Ascendente");
////                    cos.endText();
////
////                    cos.beginText();
////                    cos.setFont(fontBold, 8);
////                    cos.moveTextPositionByAmount(61, rect.getHeight() - 380);
////                    cos.drawString("Sentido Descendente");
////                    cos.endText();
////                    cos.addLine(60, rect.getHeight() - 617, 550, rect.getHeight() - 617);
////                    cos.closeAndStroke();
////                    cos.beginText();
////                    cos.setFont(fontBold, 8);
////                    cos.moveTextPositionByAmount(61, rect.getHeight() - 625);
////                    cos.drawString("Instrucciones: ");
////                    cos.endText();
////                    cos.beginText();
////                    cos.setFont(fontPlain, 8);
////                    cos.moveTextPositionByAmount(117, rect.getHeight() - 625);
////                    cos.drawString(instrucciones1.get(0));
////                    System.out.println("instrucciones: " + instrucciones.length());
////                    cos.endText();
////                    if (instrucciones.length() > 97 && instrucciones.length() <= 207) {
////
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 635);
////                        cos.drawString(instrucciones1.get(1));
////                        cos.endText();
////                    }
////                    if (instrucciones.length() > 207 && instrucciones.length() <= 317) {
////
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 635);
////                        cos.drawString(instrucciones1.get(1));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 645);
////                        cos.drawString(instrucciones1.get(2));
////                        cos.endText();
////                    }
////                    if (instrucciones.length() > 317 && instrucciones.length() <= 427) {
////
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 635);
////                        cos.drawString(instrucciones1.get(1));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 645);
////                        cos.drawString(instrucciones1.get(2));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 655);
////                        cos.drawString(instrucciones1.get(3));
////                        cos.endText();
////                    }
////                    if (instrucciones.length() > 427 && instrucciones.length() <= 537) {
////
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 635);
////                        cos.drawString(instrucciones1.get(1));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 645);
////                        cos.drawString(instrucciones1.get(2));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 655);
////                        cos.drawString(instrucciones1.get(3));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 655);
////                        cos.drawString(instrucciones1.get(4));
////                        cos.endText();
////                    }
////                    if (instrucciones.length() > 537) {
////
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 635);
////                        cos.drawString(instrucciones1.get(1));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 645);
////                        cos.drawString(instrucciones1.get(2));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 655);
////                        cos.drawString(instrucciones1.get(3));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 665);
////                        cos.drawString(instrucciones1.get(4));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 675);
////                        cos.drawString(instrucciones1.get(5));
////                        cos.endText();
////                    }
////
////                    cos.beginText();
////                    cos.setFont(fontBold, 8);
////                    cos.moveTextPositionByAmount(61, rect.getHeight() - 675);
////                    cos.drawString("Comunicaciones: ");
////                    cos.endText();
////                    cos.beginText();
////                    cos.setFont(fontPlain, 8);
////                    cos.moveTextPositionByAmount(127, rect.getHeight() - 675);
////                    cos.drawString(comunicaciones1.get(0));
////                    System.out.println("comunicaciones: " + comunicaciones.length());
////                    cos.endText();
////                    if (comunicaciones.length() > 97 && comunicaciones.length() <= 207) {
////
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 685);
////                        cos.drawString(comunicaciones1.get(1));
////                        cos.endText();
////                    }
////                    if (comunicaciones.length() > 207 && comunicaciones.length() <= 317) {
////
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 685);
////                        cos.drawString(comunicaciones1.get(1));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 695);
////                        cos.drawString(comunicaciones1.get(2));
////                        cos.endText();
////                    }
////                    if (comunicaciones.length() > 317 && comunicaciones.length() <= 427) {
////
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 685);
////                        cos.drawString(comunicaciones1.get(1));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 695);
////                        cos.drawString(comunicaciones1.get(2));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 705);
////                        cos.drawString(comunicaciones1.get(3));
////                        cos.endText();
////                    }
////                    if (comunicaciones.length() > 427 && comunicaciones.length() <= 537) {
////
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 685);
////                        cos.drawString(comunicaciones1.get(1));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 695);
////                        cos.drawString(comunicaciones1.get(2));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 705);
////                        cos.drawString(comunicaciones1.get(3));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 715);
////                        cos.drawString(comunicaciones1.get(4));
////                        cos.endText();
////                    }
////                    if (comunicaciones.length() > 537) {
////
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 685);
////                        cos.drawString(comunicaciones1.get(1));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 695);
////                        cos.drawString(comunicaciones1.get(2));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 705);
////                        cos.drawString(comunicaciones1.get(3));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 715);
////                        cos.drawString(comunicaciones1.get(4));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 725);
////                        cos.drawString(comunicaciones1.get(5));
////                        cos.endText();
////                    }
////                    cos.addLine(60, rect.getHeight() - 667, 550, rect.getHeight() - 667);
////                    cos.closeAndStroke();
////                    cos.beginText();
////                    cos.setFont(fontBold, 8);
////                    cos.moveTextPositionByAmount(61, rect.getHeight() - 725);
////                    cos.drawString("Precauciones: ");
////                    cos.endText();
////                    cos.beginText();
////                    cos.setFont(fontPlain, 8);
////                    cos.moveTextPositionByAmount(117, rect.getHeight() - 725);
////                    cos.drawString(precauciones1.get(0));
////                    System.out.println("precauciones: " + precauciones.length());
////                    cos.endText();
////                    if (precauciones.length() > 97 && precauciones.length() <= 207) {
////
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 735);
////                        cos.drawString(precauciones1.get(1));
////                        cos.endText();
////                    }
////                    if (precauciones.length() > 207 && precauciones.length() <= 317) {
////
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 735);
////                        cos.drawString(precauciones1.get(1));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 745);
////                        cos.drawString(precauciones1.get(2));
////                        cos.endText();
////                    }
////                    if (precauciones.length() > 317 && precauciones.length() <= 427) {
////
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 735);
////                        cos.drawString(precauciones1.get(1));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 745);
////                        cos.drawString(precauciones1.get(2));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 755);
////                        cos.drawString(precauciones1.get(3));
////                        cos.endText();
////                    }
////                    if (precauciones.length() > 427 && precauciones.length() <= 537) {
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 735);
////                        cos.drawString(precauciones1.get(1));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 745);
////                        cos.drawString(precauciones1.get(2));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 755);
////                        cos.drawString(precauciones1.get(3));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 765);
////                        cos.drawString(precauciones1.get(4));
////                        cos.endText();
////                    }
////                    if (precauciones.length() > 537) {
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 735);
////                        cos.drawString(precauciones1.get(1));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 745);
////                        cos.drawString(precauciones1.get(2));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 755);
////                        cos.drawString(precauciones1.get(3));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 765);
////                        cos.drawString(precauciones1.get(4));
////                        cos.endText();
////                        cos.beginText();
////                        cos.setFont(fontPlain, 8);
////                        cos.moveTextPositionByAmount(61, rect.getHeight() - 775);
////                        cos.drawString(precauciones1.get(5));
////                        cos.endText();
////                    }
////                    cos.addLine(60, rect.getHeight() - 717, 550, rect.getHeight() - 717);
////                    cos.closeAndStroke();
////                    line = 0;
////                    j = 0;
////                    a = 170;
////                    b = 150;
////                    i--;
////                    reg += 15;
////                    primerEscrito = false;
////
////                    cos.setLineWidth(1);
////                    cos.addLine(60, 760, 550, 760);
////                    cos.addLine(60, 60, 550, 60);
////                    cos.addLine(60, 60, 60, 760);
////                    cos.addLine(550, 60, 550, 760);
////
////                    cos.closeAndStroke();
////                }
////                j++;
////
////            }
////            page1 = new PDPage(PDPage.PAGE_SIZE_A4);
////            document.addPage(page1);
////            rect = page1.getMediaBox();
////            cos.close();
////            cos = new PDPageContentStream(document, page1);
////
////            try {
////                URL url = getClass().getResource("/img/cintillo_s1.png");
////                System.out.println(url);
////                        //  URI imageURL=getClass().getResource("img/header ife.png").toURI();
////                // File imagen=new File(imageURL);
////                //System.out.println(imagen);
////                BufferedImage awtImage = ImageIO.read(url);
////                PDXObjectImage ximage = new PDPixelMap(document, awtImage);
////                float scale = 0.5f; // alter this value to set the image size
////                //cos.drawImage(ximage, ximage.getWidth(), ximage.getHeight());
////                cos.drawXObject(ximage, 60, 800, ximage.getWidth() * scale, ximage.getHeight() * scale);
////
////            } catch (Exception fnfex) {
////                fnfex.printStackTrace();
////                System.out.println("No image for you");
////            }
////                    //   cos.setLineWidth(1);
////
////            cos.setLineWidth(1);
////            cos.addLine(60, 760, 550, 760);
////            cos.addLine(60, 60, 550, 60);
////            cos.addLine(60, 60, 60, 760);
////            cos.addLine(550, 60, 550, 760);
////
////            cos.closeAndStroke();
////            cos.beginText();
////            cos.setFont(fontBold, 14);
////            cos.moveTextPositionByAmount(60, rect.getHeight() - 60);
////            cos.drawString("DOCUMENTO DE TREN: " + nombre);
////            cos.endText();
//////        cos.beginText();
////            cos.addLine(60, 70, 550, 70);
////            cos.closeAndStroke();
////            cos.beginText();
////            cos.setFont(fontPlain, 10);
////            cos.moveTextPositionByAmount((rect.getWidth() / 2) - 120, 61);
////            cos.drawString("ENTRA EN VIGENCIA A PARTIR DEL " + fecha[0] + "/" + fecha[1] + "/" + fecha[2]);
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(61, 51);
////            cos.drawString("Realizado por: " + usr.toString());
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontBold, 8);
////            cos.moveTextPositionByAmount(rect.getWidth() - 165, 51);
////            cos.drawString("Gerencia de Gestión de Tráfico");
////            cos.endText();
////            numeroPag += 1;
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(rect.getWidth() - 62, 31);
////            cos.drawString(numeroPag + "");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 12);
////            cos.moveTextPositionByAmount(200, rect.getHeight() - 75);
////            cos.drawString("LÍNEA: " + l.getNombreLinea());
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 12);
////            cos.moveTextPositionByAmount(61, rect.getHeight() - 100);
////            cos.drawString("CARACTERÍSTICAS TÉCNICAS DE LA UNIDAD " + mr.getNombreMaterialRodante());
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontBold, 8);
////            cos.moveTextPositionByAmount(150, 710);
////            cos.drawString("ITEM");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontBold, 8);
////            cos.moveTextPositionByAmount(380, 710);
////            cos.drawString("DESCRIPCIÓN");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(101, 690);
////            cos.drawString("NÚMERO DE UNIDADES REMOLQUE");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(101, 680);
////            cos.drawString("NUMERO DE UNIDADES MOTRIZ");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(101, 670);
////            cos.drawString("LONGITUD TOTAL");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(101, 660);
////            cos.drawString("LONGITUD COCHE REMOLQUE");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(101, 650);
////            cos.drawString("LONGITUD COCHE MOTRIZ");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(101, 640);
////            cos.drawString("ALTO X ANCHO COCHE REMOLQUE");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(101, 630);
////            cos.drawString("ALTO X ANCHO COCHE MOTRIZ");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(101, 620);
////            cos.drawString("MASA O TARA TOTAL");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(101, 610);
////            cos.drawString("MASA COCHE REMOLQUE");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(101, 600);
////            cos.drawString("MASA COCHE MOTRIZ");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(101, 590);
////            cos.drawString("FRENADO");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(101, 580);
////            cos.drawString("TROCHA");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(101, 570);
////            cos.drawString("VELOCIDAD COMERCIAL                                              " + mr.getVelocidadOperativa() + " Km/h");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(101, 560);
////            cos.drawString("ACELERACIÓN MÁX.                                                  " + mr.getAceleracionMax() + " m/s^2");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(101, 550);
////            cos.drawString("DESACELERACIÓN DE SERVICIO                                         " + mr.getDesaceleracionMax() + " m/s^2");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(101, 540);
////            cos.drawString("DESACELERACIÓN DE EMERGENCIA");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(101, 530);
////            cos.drawString("VOLTAJE");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(101, 520);
////            cos.drawString("VOLTAJE DE BATERÍA");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(101, 510);
////            cos.drawString("PRESIÓN DE TRABAJO");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(101, 500);
////            cos.drawString("CAPACIDAD COCHE REMOLQUE");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(101, 490);
////            cos.drawString("CAPACIDAD COCHE MOTRIZ");
////            cos.endText();
////            cos.beginText();
////            cos.setFont(fontPlain, 8);
////            cos.moveTextPositionByAmount(101, 480);
////            cos.drawString("CAPACIDAD TOTAL");
////            cos.endText();
////
////            cos.setLineWidth(1);
////            cos.addLine(100, 720, 500, 720);
////            cos.addLine(100, 479, 500, 479);
////            cos.addLine(100, 479, 100, 720);
////            cos.addLine(500, 479, 500, 720);
////            cos.addLine(300, 479, 300, 720);
////            cos.addLine(100, 709, 500, 709);
////
////            cos.closeAndStroke();
////
////            // Make sure that the content stream is closed:
////            cos.close();
////            // Save the results and ensure that the document is properly closed:
////            //URL url = getClass().getResource("/img");
//////            URL url2 = getClass().getResource("/img");
//////                System.out.println(url2);
//////                String g=url2.toString();
//////                String g1=g.substring(6, g.length());
//////                System.out.println(g1);
//////              String ruta= g1.replace('/', '\\');
//////               System.out.println(ruta);
////             File miDir = new File (".");
//////     
//////       System.out.println (miDir.getCanonicalPath());
////            String guardar = "C:\\Users\\Kelvins Insua\\Documents\\Tesis y Desarrollo Web\\documento.pdf";
//////         //   String guardar = "http://localhost:8084/MODULO2.3/reportes/Documento de tren.pdf";
//////            System.out.println(guardar);
////            FileOutputStream f = new FileOutputStream(guardar);
//////            System.out.println(f);
//////            document.save(f);
////
////            response.setContentType("application/pdf");
////            response.addHeader("Content-Type", "application/force-download");
////            response.addHeader("Content-Disposition", "attachment; filename=algo.pdf");
////            
////            
//////            OutputStream is=pds.createOutputStream();
////            document.close();
////            
////            int bytes;
////            int c=0;
////            bytes=is.read();
////            System.out.println("x: "+bytes);
////            while (bytes!=-1)  {
////                response.getOutputStream().write(bytes);
////                c++;
////                bytes=is.read();
////            }
////            response.setContentLength(c);
////
////        } catch (Exception e) {
////            e.printStackTrace();
////            salida.print("http://localhost:8084/MODULO2.3/img/error.png");
////        }
////
////    }
////
////    }

//}
}
