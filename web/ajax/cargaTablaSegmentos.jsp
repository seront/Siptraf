<%-- 
    Document   : estaciones
    Created on : 06/01/2015, 09:16:12 AM
    Author     : seront
--%>
<%@include file="../jslt.jsp" %>

<c:set var="idLinea" value="${param.idLinea}"></c:set>
<jsp:useBean class="modelo.GestorLista" id="gl"/>
<c:choose>
    <c:when test="${!empty idLinea}">
        <div class="contenedor_tabla contenedorFormulario" style="width: 100%">
            <h2>Segmentos de la línea</h2>
            <table class="tablas">
                <tr>
                    <td>Progresiva Inicial</td>
                    <td>Progresiva Final</td>
                    <td>Curva/Recta</td>
                    <td>Gradiente</td>
                    <td>Radio</td>
                    <td>Velocidad Máx. Ascendente</td>
                    <td>Velocidad Máx. Descendente</td>
                </tr>
                <c:choose>
                    <c:when test="${gl.listaSegmento(idLinea)!='null'}">
                        <c:forEach var="a" items="${gl.listaSegmento(idLinea)}">
                            <tr>
                                
                                <!--<td ><input type="button" onmouseover="cambiarNombre('$ {a.segmentoPK.idPkInicial}')" onmouseout="valorInicial('$ {a.segmentoPK.idPkInicial}')" id="$ {a.segmentoPK.idPkInicial}" onclick="editarSegmento('$ {a.segmentoPK.idLinea}', '$ {a.segmentoPK.idPkInicial}')" value="$ {a.segmentoPK.idPkInicial}"></td>-->
                                <td ><img src="img/icon/edit-20x20.png" alt="Editar" title="Editar" onclick="editarSegmento('${a.segmentoPK.idLinea}', '${a.segmentoPK.idPkInicial}')" >${a.segmentoPK.idPkInicial}</td>
                                <td>${a.pkFinal}</td>

                                <c:choose>
                                    <c:when  test="${a.recta=='true'}">
                                        <td>Recta</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>Curva</td>
                                    </c:otherwise>
                                </c:choose>
                                <td>${a.gradiente}</td>
                                <td>${a.radioCurvatura}</td>
                                <td>${a.velocidadMaxAscendente}</td>
                                <td>${a.velocidadMaxDescendente}</td>
                                
                                <!--<td><input type="button" onclick="eliminarSegmento('$ {a.segmentoPK.idLinea}', '$ { a.segmentoPK.idPkInicial}')" value="X"></td>-->
                                <td><img src="img/icon/delete-20x20.png" onclick="eliminarSegmento('${a.segmentoPK.idLinea}', '${a.segmentoPK.idPkInicial}')" alt="Eliminar" title="Eliminar"></td>
                            </tr>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </table>
        </div>           
    </c:when>
    <c:otherwise>
        <div class="contenedor_tabla" style="width: 100%">
            <table class="tablas">
                <tr>
                    <td>Progresiva Inicial</td>
                    <td>Progresiva Final</td>
                    <td>Curva/Recta</td>
                    <td>Gradiente</td>
                    <td>Radio</td>
                    <td>Velocidad Máx. Ascendente</td>
                    <td>Velocidad Máx. Descendente</td>
                </tr>
            </table>
        </div>
    </c:otherwise>
</c:choose>
