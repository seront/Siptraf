<%-- 
    Document   : cargarTablaMarchaTipo
    Created on : 25/02/2015, 11:50:46 AM
    Author     : Kelvins Insua
--%>
<%@include file="../jslt.jsp" %>
<link href="css/estilo1.css" type="text/css" rel="stylesheet"/>
<jsp:useBean class="modelo.GestorLista" id="gl"/>

<c:if test="${gl.listaMarchaTipo()!='null'}">

    
    
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
                <td>Tiempo Mínimo</td>
            </tr>
          
                
                <c:forEach var="mt" items="${gl.listaMarchaTipo()}">
            <tr>
                <!--<td><input type="button" onclick="visualizarMarchaTipo('$ {mt.idMarchaTipo}','$ {mt.sentido}')" value="$ {mt.nombreMarchaTipo}"></td>-->
                <td><img src="img/icon/Eye-icon.png" onclick="visualizarMarchaTipo('${mt.idMarchaTipo}','${mt.sentido}')" alt="Ver" title="Ver Marcha Tipo" style="float: left;">${mt.nombreMarchaTipo}</td>
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
                 <td><img src="img/icon/edit-20x20.png" onclick="editarTiempoMinimo(${mt.idMarchaTipo})" alt="Editar" title="Editar">${mt.tiempoMinimo}</td>
                 <!--<td> <input type="button" onclick="eliminoMarchaTipo($ {mt.idMarchaTipo})" value="X"></td>-->
                 <td> <img src="img/icon/delete-20x20.png" onclick="eliminoMarchaTipo(${mt.idMarchaTipo})" alt="eliminar" title="Eliminar"></td>
                 </tr>
        </c:forEach>
                  
                
         </table>
     
    
    </c:if>

