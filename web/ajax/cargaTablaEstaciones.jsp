<%-- 
    Document   : estaciones
    Created on : 06/01/2015, 09:16:12 AM
    Author     : seront
--%>
<%@include file="../jslt.jsp" %>
<link href="css/estilo1.css" type="text/css" rel="stylesheet"/>

<c:set var="idLinea" value="${param.idLinea}"></c:set>
<c:choose>
    <c:when test="${!empty idLinea}">
        <div class="contenedor_tabla">
        <table class="tablas">
            <tr>
                
                <td>Progresiva</td>   
                <td>Nombre de la Estación</td>
                <td>Abreviación de la Estación</td>
            </tr>
          
                <jsp:useBean class="modelo.GestorLista" id="gl"/>
            <c:set var="estaciones" value="${gl.buscarEstacion(idLinea)}"/>
                <c:forEach var="e" items="${estaciones}">
            <tr>
                <!--<td>  <input type="button" onmouseover="cambiarNombre('$ {e.estacionPK.idPkEstacion}')" onmouseout="valorInicial('$ {e.estacionPK.idPkEstacion}')" id="$ {e.estacionPK.idPkEstacion}"  onclick="editarEstacion('$ {e.estacionPK.idLinea}','$ {e.estacionPK.idPkEstacion}')" value="$ {e.estacionPK.idPkEstacion}"></td>-->
                <td><img src="img/icon/edit-20x20.png" alt="Editar" title="Editar Estación" onclick="editarEstacion('${e.estacionPK.idLinea}','${e.estacionPK.idPkEstacion}')">${e.estacionPK.idPkEstacion}</td>
                 <td>${e.nombreEstacion}</td>
                 <td>${e.abrevEstacion}</td>
                 <!--<td> <input type="button" onclick="eliminarEstacion('$ {e.estacionPK.idLinea}','$ {e.estacionPK.idPkEstacion}')" value="X"></td>-->
                 <td><img src="img/icon/delete-20x20.png" alt="Eliminar" onclick="eliminarEstacion('${e.estacionPK.idLinea}','${e.estacionPK.idPkEstacion}')" title="ELiminar Estación"></td>
                 </tr>
        </c:forEach>
                  
                
         </table>
            </div>           
            
    </c:when>
    <c:otherwise>
         <div class="contenedor_tabla">
        <table class="tablas">
            <tr>
                
                <td>Progresiva</td>      
                <td>Nombre de la Estacion</td>
                <td>Abreviación de la Estación</td>
            </tr>
        </table>
             </div>
    </c:otherwise>
</c:choose>
