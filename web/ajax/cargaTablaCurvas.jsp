<%-- 
    Document   : estaciones
    Created on : 06/01/2015, 09:16:12 AM
    Author     : seront
--%>
<%@include file="../jslt.jsp" %>
<link href="css/estilo1.css" type="text/css" rel="stylesheet"/>
<jsp:useBean class="modelo.GestorLista" id="gl"/>
<c:set var="idMatRod" value="${param.idMatRod}"></c:set>
<c:choose>
    <c:when test="${!empty idMatRod}">
        <div class="contenedor_tabla">
            <h2>
                Curva Velocidad vs Esfuerzo
            </h2>
            <table class="tablas">
                <tr>
                    <td>Velocidad (km/h)</td>
                    <td>Esfuerzo de Tracción (kgf)</td>                              
                    <td>Esfuerzo de Frenado (kgf)</td>                              
                    <td>Eliminar</td>                              
                </tr>
                
                <c:set var="curvas" value="${gl.listaEsfuerzos(idMatRod)}"/>
                <c:forEach var="cur" items="${curvas}">
                    <tr>
                        <!--<td>  <input type="button" onmouseover="cambiarNombre('${cur.curvaEsfuerzoPK.idVelocidadCurvaEsfuerzo}')" onmouseout="valorInicial('${cur.curvaEsfuerzoPK.idVelocidadCurvaEsfuerzo}')" id="${cur.curvaEsfuerzoPK.idVelocidadCurvaEsfuerzo}"  onclick="editarCurva('${cur.curvaEsfuerzoPK.idMaterialRodante}', '${cur.curvaEsfuerzoPK.idVelocidadCurvaEsfuerzo}')" value="${cur.curvaEsfuerzoPK.idVelocidadCurvaEsfuerzo}"></td>-->
                        <td><img src="img/icon/edit-20x20.png" alt="editar" onclick="editarCurva('${cur.curvaEsfuerzoPK.idMaterialRodante}', '${cur.curvaEsfuerzoPK.idVelocidadCurvaEsfuerzo}')">${cur.curvaEsfuerzoPK.idVelocidadCurvaEsfuerzo}</td>
                        <td>${cur.esfuerzoTraccion}</td>
                        <td>${cur.esfuerzoFrenado}</td>
                        <!--<td> <input type="button" onclick="eliminarCurva('${cur.curvaEsfuerzoPK.idMaterialRodante}', '${cur.curvaEsfuerzoPK.idVelocidadCurvaEsfuerzo}')" value="X"></td>-->
                        <td><img src="img/icon/delete-20x20.png" onclick="eliminarCurva('${cur.curvaEsfuerzoPK.idMaterialRodante}', '${cur.curvaEsfuerzoPK.idVelocidadCurvaEsfuerzo}')" alt="Eliminar" title="Eliminar"></td>
                    </tr>
                </c:forEach>
            </table>
<!--        </div>
                <div id="graficoEsfuerzos" style="width: 65%; height: 500px; ">
            </div>-->
    </c:when>
    <c:otherwise>
        <div class="contenedor_tabla">
            <h2>
                Curva Velocidad vs Esfuerzo
            </h2>
            <table class="tablas">
                <tr>
                    <td>Velocidad (km/h)</td>
                    <td>Esfuerzo de Tracción (kgf)</td>                              
                    <td>Esfuerzo de Frenado (kgf)</td>                              
                    <td>Eliminar</td>                               
                </tr>
            </table>
        </div>
    </c:otherwise>
</c:choose>
