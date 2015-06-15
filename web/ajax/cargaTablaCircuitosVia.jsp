<%-- 
    Document   : estaciones
    Created on : 06/01/2015, 09:16:12 AM
    Author     : seront
--%>
<%@include file="../jslt.jsp" %>
<jsp:useBean class="modelo.GestorLista" id="gl"/>
<c:set var="idLinea" value="${param.idLinea}"></c:set>
<c:choose>
    <c:when test="${!empty idLinea}">
        <div class="contenedor_tabla">
            <h2>Circuitos de vía de la línea</h2>
            <table class="tablas">
                <tr>
                    <td>Progresiva Inicial</td>
                    <td>Progresiva Final</td>
                </tr>
                <c:if test="${gl.buscarCircuitoVia(idLinea)!='null'}">
                    <c:forEach var="c" items="${gl.buscarCircuitoVia(idLinea)}">
                        <tr>
                            <!--<td> <input type="button" onmouseover="cambiarNombre('$ {c.circuitoViaPK.idPkInicialCircuito}')" onmouseout="valorInicial('$ {c.circuitoViaPK.idPkInicialCircuito}')" id="$ {c.circuitoViaPK.idPkInicialCircuito}" onclick="editarCircuitoVia('$ {c.circuitoViaPK.idLinea}', '$ {c.circuitoViaPK.idPkInicialCircuito}')" value="$ {c.circuitoViaPK.idPkInicialCircuito}"></td>-->
                            <td><img src="img/icon/edit-20x20.png" alt="editar" title="Editar" onclick="editarCircuitoVia('${c.circuitoViaPK.idLinea}', '${c.circuitoViaPK.idPkInicialCircuito}')">${c.circuitoViaPK.idPkInicialCircuito}</td>
                            <td>${c.pkFinalCircuito}</td>
                            <!--<td> <input type="button" onclick="eliminarCircuitoVia('$ {c.circuitoViaPK.idLinea}', '$ {c.circuitoViaPK.idPkInicialCircuito}')" value="X"></td>-->
                            <td><img src="img/icon/delete-20x20.png" alt="Eliminar" title="Eliminar" onclick="eliminarCircuitoVia('${c.circuitoViaPK.idLinea}', '${c.circuitoViaPK.idPkInicialCircuito}')"></td>
                        </tr>
                    </c:forEach>
                </c:if>
            </table>
        </div>           

    </c:when>
    <c:otherwise>
        No se han encontrado circuitos de vía
    </c:otherwise>
</c:choose>
