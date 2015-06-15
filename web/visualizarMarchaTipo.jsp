<%-- 
    Document   : visualizarMarchaTipo
    Created on : 25/02/2015, 01:18:17 PM
    Author     : Kelvins Insua
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="estilo.jsp" %>
<%@include file="jslt.jsp" %>

<!DOCTYPE html>
<html>
    <head>
        <script src="js/jquery-1.9.1.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="js/marchaTipo.js" type="text/javascript" rel=""></script>
        <script src="js/sesionCerrar.js" type="text/javascript"></script>
        <title>S.I.P.T.R.A.F</title>
        
    </head>
    <body>
        <div id="bgVentanaModal" class="dialogoModal">
            <div id="msjajax"></div>
            <div id="datos">                
            </div>

        </div>
      <div id="varGraficos">
            
        </div>  
            <c:set var="idMarchaTipo" value="${param.idMarchaTipo}"></c:set>
        <jsp:useBean class="modelo.GestorLista" id="gl"/>
        <c:set var="mt" value="${gl.buscarMarchaTipo(idMarchaTipo)}"></c:set>
        <c:set var="tiempoEstaciones" value="${gl.buscarTiemposMarchaTipo(idMarchaTipo,mt.sentido)}"></c:set>
        <c:set var="restricciones" value="${gl.buscarRestriccionesMarchaTipo(idMarchaTipo)}"></c:set>

<header>
            <p class="titulo">Marcha Tipo ${mt.nombreMarchaTipo}</p>
        </header>
        <c:choose>
                <c:when test="${!empty sessionScope.usuario}">
                    <div id="header">
			<ul class="nav">
				<li><a href="">${sessionScope.usuario}</a>
					<ul>
                                            <li onclick="cerrarSesion()"><a href="">Cerrar Sesión</a></li>
						
					</ul>
				</li>
				
			</ul>
		</div>

                </c:when>
                <c:otherwise>
                <script src="js/sesion.js" type="text/javascript" rel=""></script>
                </c:otherwise>
            </c:choose>
        <div style="height: 50px">
                <input type="button" id="btnMenu" class="btnIrAlMenu" value="Ir al Menú" onclick="location.href = 'index.jsp'">
            </div>
        
    <c:if test="${mt!='null'}">
        <div class="contenedor_tabla" style="width: 600px;">
        <table class="tablas">
            <tr>
                
                <td>Nombre De La Marcha Tipo</td>   
                <td>Velocidad</td>
                <td>Línea</td>
                <td>Material Rodante</td>
                <td>Estación Inicial</td>
                <td>Estación Final</td>
                <td>Tiempo Total</td>
                <td>Sistema de Protección</td>
                <td>Carga</td>
            </tr>
          
                
                
            <tr>
                <td> ${mt.nombreMarchaTipo}</td>
                 <td>${mt.velocidadMarchaTipo}</td>
                 <td>${mt.linea}</td>
                 <td>${mt.materialRodante}</td>
                 <td>${mt.nombreEstacionInicial}</td>
                 <td>${mt.nombreEstacionFinal}</td>
                 <td>${mt.tiempoTotal}</td>
                 <c:choose>
                     <c:when test="${mt.sistemaProteccion==true}">
                         <td>Activado</td>
                     </c:when>
                         <c:otherwise>
                             <td>Desactivado</td>
                         </c:otherwise>
                     
                 </c:choose>
                 
                 <td>${mt.cargaMarcha}</td>
                 
                 </tr>
       
                  
                
         </table>
        </div>
                 </c:if>
                 <c:if test="${tiempoEstaciones!=null}">
                  
                 
                 <div class="contenedor_tabla" style="width: 600px; margin-top: 15px;">
         <table class="tablas">
        <tr>
                <td>Tiempo Entre Estaciones</td>
            </tr>
            </table>
                     <table class="tablas">
            
            <tr>
                
                <td>Nombre De La Estación</td>   
                <td>Progresiva De la Estación</td>
                <td>Tiempo Ideal</td>
                <td>Tiempo Márgen</td>
                <td>Tiempo Estimado</td>
                <td>Tiempo Redondeo</td>
                <td>Tiempo Adicional</td>
                <td>Tiempo Asimilado</td>
            </tr>
          
                
                
            <tr>
                <c:forEach var="t" items="${tiempoEstaciones}">
                <td> ${t.nombreEstacion}</td>
                 <td>${t.tiempoEstacionMarchaTipoPK.idPkEstacion}</td>
                 <td>${t.tiempoIdeal}</td>
                 <td>${t.tiempoMargen}</td>
                 <td>${t.tiempoReal}</td>
                 <td>${t.tiempoRedondeo}</td>
                 <td>${t.tiempoAdicional}</td>
                 <td>${t.tiempoAsimilado}</td>
                
                 </tr>
            </c:forEach>
                  
                
         </table>
        </div>
         
         </c:if>
                  
                   <c:if test="${restricciones!='null'}">
                  
                 
                 <div class="contenedor_tabla" style="width: 600px; margin-top: 15px;">
                     <table class="tablas">
        <tr>
                <td>Restricciones Incluidas</td>
            </tr>
            </table>
                     <table class="tablas">
            
            <tr>
                
                <td>Progresiva de Inicio</td>   
                <td>Progresiva Final</td>
                <td>Velocidad Máxima Ascendente</td>
                <td>Velocidad Máxima Descendente</td>
            </tr>
          
                
                
            <tr>
                <c:forEach var="r" items="${restricciones}">
                <td> ${r.restriccionMarchaTipoPK.idPkInicialResMt}</td>
                 <td>${r.pkFinalResMt}</td>
                 <td>${r.velocidadMaxAscendente}</td>
                 <td>${r.velocidadMaxDescendente}</td>
                
                 </tr>
            </c:forEach>
                  
                
         </table>
        </div>
         
         </c:if>
        
     
        
    </body>
</html>
