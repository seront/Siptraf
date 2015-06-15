<%-- 
    Document   : DatosSegmento
    Created on : 05/11/2014, 01:00:12 PM
    Author     : Kelvins Insua
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="estilo.jsp" %>
<%@include file="jslt.jsp" %>

<%@page language="java" import="javazoom.upload.*" %>
<%@page language="java" import="java.util.*" %>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.File"%>
<!DOCTYPE html>

<%
    
   String d=getServletContext().getRealPath("/src/java/img");
   String b="build\\";
   String br="web\\";
   String a=d.replace(b, "");
    a=a.replace(br, "");
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
<jsp:useBean class="modelo.GestorLista" id="gl"/>
<html>
    <head>
        <script src="js/jquery-1.9.1.js"></script>
        <script type="text/javascript" src="js/administrarMaterialRodante.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="js/sesionCerrar.js" type="text/javascript"></script>
        <title>S.I.P.T.R.A.F</title>
    </head>
    <body >
        <div id="bgVentanaModal">
            <div id="datos">                
            </div>            
        </div>
        <header>
            <p class="titulo">Administrar Material Rodante</p>
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
                <main id="matRodanteMain">
            <div style="height: 50px">
                <input type="button" id="btnMenu" class="btnIrAlMenu" value="Ir al Menú" onclick="location.href = 'index.jsp'">
            </div>
            <c:if test="${sessionScope.nivel=='1'}">
                <div class="contenedorFormulario" id="formSeg">
                    <legend><h2>Datos del material rodante</h2></legend>
                    <form>
                        <div class="columna1">
                            <label class="tituloFormulario">Nombre</label>
                            <input class="campoFormulario" type="text" id="nombre" placeholder="Nombre del Material Rodante" required>
                            <label class="tituloFormulario">Tipo</label>
                            <input class="campoFormulario" type="text" id="tipo"  placeholder="Tipo" required >
                            <label class="tituloFormulario">Sub-Tipo</label>
                            <select class="campoFormulario" id="sub_tipo" required>
                                <option value="Tren de Viajeros">Tren de Viajeros</option>
                                <option value="Tren de Mercarcias">Tren de Mercancias</option>
                                <option value="Locomotora">Locomotora</option>
                            </select>
                            <label class="tituloFormulario">Número de Vagones</label>
                            <input class="campoFormulario" type="text" id="numero_vagones"  placeholder="Número de Coches" required>
                            <label class="tituloFormulario">Capacidad de Pasajeros</label>
                            <input class="campoFormulario" type="text" id="capacidad_pasajeros" placeholder="Capacidad de Pasajeros" required>
                            <label class="tituloFormulario">Número de Ejes</label>
                            <input class="campoFormulario" type="text" id="numero_ejes"  placeholder="Número de Ejes" required>
                            
                        </div>
                        <div class="columna2">
                            <label class="tituloFormulario">Velocidad De Diseño (Km/h)</label>
                            <input class="campoFormulario" type="text" id="velocidad_diseño" placeholder="Velocidad de Diseño (Km/h)" step="0.1" required>
                            <label class="tituloFormulario">Velocidad De Operación (Km/h)</label>
                            <input class="campoFormulario" type="text" id="velocidad_operacion" placeholder="Velocidad de Operacion (Km/h)" required>
                            <label class="tituloFormulario">Peso (t)</label>
                            <input class="campoFormulario" type="text" id="masa" placeholder="Peso (toneladas)" required>                 
                            <label class="tituloFormulario">Aceleración máxima</label>
                            <input class="campoFormulario" type="text" id="aceleracion_maxima" placeholder="Aceleración Máxima (m/s^2)" required>
                            <label class="tituloFormulario">Desaceleración de Servicio</label>
                            <input class="campoFormulario" type="text" id="desaceleracion_maxima"  placeholder="Desaceleración Máxima(m/s^2)" required>
                            <label class="tituloFormulario">Desaceleración de Emergencia</label>
                            <input class="campoFormulario" type="text" id="desaceleracion_emergencia"  placeholder="Desaceleración Máxima(m/s^2)" required>
                            <label class="tituloFormulario">Carga Máxima</label>
                            <input class="campoFormulario" type="number" id="carga_maxima"  placeholder="Carga Máxima (t)" required>
                        </div>
                        <div class="columna2">
                            <label class="tituloFormulario">Número de Unidades Remolque</label>
                            <input class="campoFormulario" type="number" id="unidades_remolque" placeholder="Número de Unidades Remolque" required>
                            <label class="tituloFormulario">Número de Unidades Motriz</label>
                            <input class="campoFormulario" type="number" id="unidades_motriz" placeholder="Número de Unidades Motriz" required>
                            <label class="tituloFormulario">Longitud Total (metros)</label>
                            <input class="campoFormulario" type="text" id="longitud_total" placeholder="Longitud Total (m)" required>
                            <label class="tituloFormulario">Longitud Unidad Remolque (metros)</label>
                            <input class="campoFormulario" type="text" id="longitud_remolque" placeholder="Longitud Unidades Remolque (m)" required>                 
                            <label class="tituloFormulario">Longitud Unidad Motriz (metros)</label>
                            <input class="campoFormulario" type="text" id="longitud_motriz" placeholder="Longitud Unidades  Motriz (m)" required>                 
                            <label class="tituloFormulario">Alto por Ancho Unidades Remolque (metros)</label>
                            <input class="campoFormulario" type="text" id="alto_x_ancho_remolque" placeholder="Ej: axb; (m)" required>                 
                            <label class="tituloFormulario">Alto por Ancho Unidades Motriz (metros)</label>
                            <input class="campoFormulario" type="text" id="alto_x_ancho_motriz" placeholder="Ej: axb; (m)" required>                 
                            
                        </div>
                        <div class="columna2">
                            <label class="tituloFormulario">Masa de Unidades Remolque (t)</label>
                            <input class="campoFormulario" type="text" id="masa_remolque" placeholder="Masa de Unidades Remolque (t)" required>
                            <label class="tituloFormulario">Masa de Unidades Motriz</label>
                            <input class="campoFormulario" type="text" id="masa_motriz" placeholder="Masa de Unidades Motriz" required>
                            <label class="tituloFormulario">Descripción del Tipo de Frenado</label>
                            <input class="campoFormulario" type="text" id="frenado_descripcion" placeholder="Descripcion del Frenado" required>
                            <label class="tituloFormulario">Voltaje (Voltios)</label>
                            <input class="campoFormulario" type="text" id="voltaje" placeholder="Voltaje (V)" required>                 
                            <label class="tituloFormulario">Voltaje de Batería (Voltios)</label>
                            <input class="campoFormulario" type="text" id="voltaje_bateria" placeholder="Voltaje de Batería (V)" required>                 
                            <label class="tituloFormulario">Capacidad de Unidades Remolque </label>
                            <input class="campoFormulario" type="text" id="capacidad_remolque" placeholder="Pasajeros o toneladas" required>                 
                            <label class="tituloFormulario">Capacidad de Unidades Motriz</label>
                            <input class="campoFormulario" type="text" id="capacidad_motriz" placeholder="Pasajeros o Toneladas" required>                 
                            <label class="tituloFormulario">Rango de Presión de Trabajo (Kpa)</label>
                            <input class="campoFormulario" type="text" id="presion_trabajo" placeholder="Presión de Trabajo (Kpa)" required>                 
                            
                        </div>
                        <div id="contenedorImg">
                            <!--<input class="botonContinuar" type="button" id="agregar" value="Agregar">-->
                            <img src="img/icon/add-icon.png" alt="Agregar" title="Agregar" onclick="agregarMaterialRodante();">
                        </div>
                    </form>
                </div>
                               <div class="contenedorFormulario" style="width: 100%;">
                <legend ><h2>Imagen de Material Rodante / Extenciones Válidas .jpg y .png</h2></legend>
                <form action="ingresoMaterialRodante.jsp" method="post" enctype="multipart/form-data">
                    
<%
try{
    
            if (MultipartFormDataRequest.isMultipartFormData(request)) {
 MultipartFormDataRequest mrequest = new MultipartFormDataRequest(request);
 String todo = null;
 
 
 
    
    
 if (mrequest != null) {
  todo = mrequest.getParameter("todo");
 }
 if ((todo != null) && (todo.equalsIgnoreCase("materialrodante"))) {
     
     String idMatRod=mrequest.getParameter("cmb_materiales");
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
  Hashtable files = mrequest.getFiles();
  if ((files != null) && (!files.isEmpty())) {
//   java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("yyMMddHHmmss");
   String archivo = ((UploadFile) mrequest.getFiles().get("uploadfileMR")).getFileName();
   int posicionPunto = archivo.indexOf(".");
   String nombreImagen;
//   String extension = archivo.substring(posicionPunto);
//   nombreImagen = nombreImagen + formato.format(new java.util.Date());
    nombreImagen = idMatRod+ ".jpg";
   ((UploadFile) mrequest.getFiles().get("uploadfileMR")).setFileName(nombreImagen);
   UploadFile file = (UploadFile) files.get("uploadfileMR");
   if (file != null) {
    out.println("<p style='f ont-size:12pt; font-weight:bold;'>El archivo se subio correctamente</p>");
//    response.sendRedirect("ingresoImagen.jsp");
   }
   upBean.store(mrequest, "uploadfileMR");
  } else {
    out.println("Archivos no subidos");
  }
 } else {
   out.println("<BR> todo=" + todo);
 }
}
}catch(Exception e){
e.printStackTrace();
}
%>
                      <label class="tituloFormulario" style="font-size: 16px; font-weight: bold;">Material Rodante: </label> 
                            <select name="cmb_materiales" class="campoFormulario">
                                <option value=""> Seleccione...</option>

                                <c:set var="materiales" value="${gl.listaMaterialRodante()}"/>
                                <c:forEach var="material" items="${materiales}">                       
                                    <option value="${material.idMaterialRodante}"> ${material.idMaterialRodante}-${material.nombreMaterialRodante}</option>                        
                                </c:forEach>
                            </select> 
                                <br>
                     <label class="tituloFormulario" style="font-size: 16px; font-weight: bold;">Ingrese Nueva Imagen de de Material Rodante:</label>
                     <input type="file" id="img_cintillo" name="uploadfileMR" class="campoFormulario" style="width: 500px; ">
                    
                     <input type="hidden" name="todo" value="materialrodante">           
                                <input class="botonContinuar" type="submit" value="Enviar" id="btn_cintillo" title="Enviar Imagen" >
                                
                    
                     
                    </form>
                </div>
                <div class="contenedorFormulario" id="msj">
                </div>

                
            </main>
                <div class="contenedorFormulario" id="marcoMR"></div>
        </c:if>
        <footer>
            <%@include file="footer.jsp" %>
        </footer>
    </body>
</html>
