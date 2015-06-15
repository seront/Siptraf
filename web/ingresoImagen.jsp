<%-- 
    Document   : ingresoImagen
    Created on : 21/05/2015, 09:31:01 AM
    Author     : Kelvins Insua
--%>

<%@page import="java.io.InputStream"%>
<%@page import="java.io.File"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="estilo.jsp" %>
<%@include file="jslt.jsp" %>

<%@page language="java" import="javazoom.upload.*" %>
<%@page language="java" import="java.util.*" %>
<%--<%@page errorPage="error.jsp" %>--%>

<%
    
   String d=getServletContext().getRealPath("/imgDB");
   String b="build\\";
   String a=d.replace(b, "");
//    String direccion ="C:\\Users\\Kelvins Insua\\Documents\\NetBeansProjects\\MODULO2.3\\web\\imgDB";
   System.out.println(a);
//    System.out.println(direccion);
//    String direccion1 =getServletContext().getRealPath("/web/imgDB");
    
%>

<jsp:useBean id="upBean" scope="page" class="javazoom.upload.UploadBean" >
    <jsp:setProperty name="upBean" property="folderstore" value="<%=a%>" />
    <jsp:setProperty name="upBean" property="whitelist" value="*.jpg,*.png" />
    <jsp:setProperty name="upBean" property="overwritepolicy" value="nametimestamp"/>
</jsp:useBean>
<!DOCTYPE html>
<html>
    <head>
        <script src="js/jquery-1.9.1.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="js/sesionCerrar.js" type="text/javascript"></script>
        <title>S.I.P.T.R.A.F</title>
        <jsp:useBean class="modelo.GestorLista" id="gl"/>
    </head>
    <body >
        <div id="bgVentanaModal" class="dialogoModal">
            <div id="msjajax"></div>
            <div id="datos">                
            </div>

        </div>
        <header>
            <p class="titulo">Administrar Imagenes</p>
        </header>
        <c:choose>
            <c:when test="${!empty sessionScope.usuario}">
                <div id="header">
                    <ul class="nav">
                        <li><a href="">${sessionScope.usuario}</a>
                            <ul>
                                <li onclick="cerrarSesion()"><a href="">Cerrar Sesión</a></li>
                                <li onclick="cambiarContraseña()"><a >Cambiar Contraseña</a></li>
                            </ul>
                        </li>

                    </ul>
                </div>

            </c:when>
            <c:otherwise>
                <script src="js/sesion.js" type="text/javascript" rel=""></script>
            </c:otherwise>
        </c:choose>
        <main>


            <div style="height: 50px">
                <input type="button" id="btnMenu" class="btnIrAlMenu" value="Ir al Menú" onclick="location.href = 'index.jsp'">
            </div>
            
            <div class="contenedorFormulario" style="width: 100%;">
                <legend ><h2>Cambiar Cintillo del Sistema / Extenciones Válidas .jpg y .png</h2></legend>
                <form action="ingresoImagen.jsp" method="post" enctype="multipart/form-data">
                      <%
try{

    String e=getServletContext().getRealPath("/imgDB/cintillo.jpg");
   String f="build\\";
   String g=e.replace(f, "");
    File img=new File(g);
    
            if (MultipartFormDataRequest.isMultipartFormData(request)) {
 MultipartFormDataRequest mrequest = new MultipartFormDataRequest(request);
 String todo = null;
 if (mrequest != null) {
  todo = mrequest.getParameter("todo");
 }
 if ((todo != null) && (todo.equalsIgnoreCase("upload"))) {
     if(img.delete()){
//        System.out.println("si");
    }else {
//    System.out.println("no");
    }
  Hashtable files = mrequest.getFiles();
  if ((files != null) && (!files.isEmpty())) {
//   java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("yyMMddHHmmss");
//   String archivo = ((UploadFile) mrequest.getFiles().get("uploadfile")).getFileName();
//   int posicionPunto = archivo.indexOf(".");
   String nombreImagen;
//   String extension = archivo.substring(posicionPunto);
//   nombreImagen = nombreImagen + formato.format(new java.util.Date());
    nombreImagen = "cintillo" + ".jpg";
   ((UploadFile) mrequest.getFiles().get("uploadfile")).setFileName(nombreImagen);
   UploadFile file = (UploadFile) files.get("uploadfile");
   if (file != null) {
    out.println("<p style='font-size:12pt; font-weight:bold;'>El archivo se subio correctamente, por favor valla al menú y recargue la página</p>");
//    response.sendRedirect("ingresoImagen.jsp");
   }
   upBean.store(mrequest, "uploadfile");
  } else {
    out.println("Archivos no subidos");
  }
 } else if((todo != null) && (todo.equalsIgnoreCase("materialrodante"))){
//   out.println("<BR> todo=" + todo);
   
     String idMatRod=mrequest.getParameter("cmb_materiales");
    String er=getServletContext().getRealPath("/imgDB/"+idMatRod+".jpg");
   String fr="build\\";
   String gr=er.replace(fr, "");
    File imgr=new File(gr);
    if(imgr.delete()){
//        System.out.println("si");
    }else{
//    System.out.println("no");
    }
  Hashtable files = mrequest.getFiles();
  if ((files != null) && (!files.isEmpty())) {
//   java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("yyMMddHHmmss");
//   String archivo = ((UploadFile) mrequest.getFiles().get("uploadfileMR")).getFileName();
//   int posicionPunto = archivo.indexOf(".");
   String nombreImagen;
//   String extension = archivo.substring(posicionPunto);
//   nombreImagen = nombreImagen + formato.format(new java.util.Date());
    nombreImagen = idMatRod+ ".jpg";
   ((UploadFile) mrequest.getFiles().get("uploadfileMR")).setFileName(nombreImagen);
   UploadFile file = (UploadFile) files.get("uploadfileMR");
   if (file != null) {
    out.println("<p style='font-size:12pt; font-weight:bold;'>El archivo se subio correctamente, por favor valla al menú y recargue la página</p>");
//    response.sendRedirect("ingresoImagen.jsp");
   }
   upBean.store(mrequest, "uploadfileMR");
 }
}
}
}catch(Exception e){
e.printStackTrace();
}
%>
                     <label class="tituloFormulario" style="font-size: 16px; font-weight: bold;">Ingrese Nueva Imagen de Cintillo:</label>
                     <input type="file" id="img_cintillo" name="uploadfile" class="campoFormulario" style="width: 500px; ">
                    
                     <input type="hidden" name="todo" value="upload">           
                                <input class="botonContinuar" type="submit" value="Enviar" id="btn_cintillo" title="Enviar Imagen" >
                                
                    
                     
                    </form>
                </div>
                     

            </main>
        
        <footer>
            <%@include file="footer.jsp" %>
        </footer>
    </body>

</html>

