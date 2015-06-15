<%-- 
    Document   : estaciones
    Created on : 06/01/2015, 09:16:12 AM
    Author     : seront
--%>
<%@include file="../jslt.jsp" %>
<jsp:useBean class="modelo.GestorLista" id="gl"/>
<c:if test="${gl.listaLinea()!='null'}">
    <div class="contenedor_tabla" style="width: 100%">
            <table class="tablas">
                <tr>
                    <td>Nombre</td>
                    <td>Punto K. Inicial</td>
                    <td>Punto K. Final</td>
                    <td>Trocha</td>
                    <td>Doble/Única</td>
                    <td>Velocidad Máxima</td>
                </tr>                
                    <c:forEach var="l" items="${gl.listaLinea()}">
                        <tr>
                            <!--<td> <input type="button" onmouseover="cambiarNombre('$ {l.nombreLinea}')" onmouseout="valorInicial('$ {l.nombreLinea}')" id="$ {l.nombreLinea}" onclick="editarLinea('$ {l.idLinea}')" value="$ {l.nombreLinea}" ></td>-->
                            <td><img src="img/icon/edit-20x20.png" alt="Editar" title="Editar" onclick="editarLinea('${l.idLinea}')">${l.nombreLinea}</td>
                            <td>${l.pkInicial}</td>
                            <td>${l.pkFinal}</td>
                            <td>${l.trocha}</td>
                            <c:choose>
                                <c:when test="${l.tipoLinea==true}">
                                    <td>Doble</td>
                                </c:when>
                                <c:otherwise>
                                    <td>Única</td>
                                </c:otherwise>
                            </c:choose>
                           <td>${l.velocidadLinea}</td>

                            
                            <!--<td><input type="button" onclick="eliminarLinea('${l.idLinea}')" value="X"></td>-->
                            <td><img src="img/icon/delete-20x20.png" alt="Eliminar" onclick="eliminarLinea('${l.idLinea}')" title="ELiminar"></td>
                        </tr>
                    </c:forEach>
               
            </table>
        </div>
 </c:if>