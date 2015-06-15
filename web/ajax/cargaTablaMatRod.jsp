<%-- 
    Document   : estaciones
    Created on : 06/01/2015, 09:16:12 AM
    Author     : seront
--%>
<%@include file="../jslt.jsp" %>
<jsp:useBean class="modelo.GestorLista" id="gl"/>

    <div class="contenedor_tabla" id="ingMat">
            <table class="tablas">
                <tr>
                    <td>Nombre</td>
                    <td>Tipo</td>
                    <td>Sub Tipo</td>
                    <td>Vagones/Coches</td>
                    <td>Pasajeros</td>                    
                    <td>Peso</td>
                    <td>Vel. de Diseño</td>
                    <td>Vel. de Operacion</td>
                    <td>Acel. Máx</td>
                    <td>Desacel. Máx</td>
                    <td>Número de Ejes</td>
                    <td>Carga Máxima</td>
                </tr>
                <c:if test="${gl.listaMaterialRodante()!='null'}">
                    <c:forEach var="mr" items="${gl.listaMaterialRodante()}">
                        <tr>
                            <!--<td> <input type="button" onmouseover="cambiarNombre('$ {mr.nombreMaterialRodante}')" onmouseout="valorInicial('$ {mr.nombreMaterialRodante}')" id="$ {mr.nombreMaterialRodante}" onclick="editarMaterialRodante('$ {mr.idMaterialRodante}')" value="$ {mr.nombreMaterialRodante}"/></td>-->
                            <td><img src="img/icon/edit-20x20.png" alt="Editar" title="Editar" onclick="editarMaterialRodante('${mr.idMaterialRodante}')">${mr.nombreMaterialRodante}</td>
                            <td>${mr.tipo}</td>
                            <td>${mr.subTipo}</td>
                            <td>${mr.numeroVagones}</td>
                            <td>${mr.capacidadPasajeros}</td>
                            <td>${mr.masa}</td>
                            <td>${mr.velocidadDisenio}</td>
                            <td>${mr.velocidadOperativa}</td>
                            <td>${mr.aceleracionMax}</td>
                            <td>${mr.desaceleracionMax}</td>                            
                            <td>${mr.numeroEjes}</td>                            
                            <td>${mr.cargaMaxima}</td>                            
                            <!--<td><input type="button" onclick="eliminarMaterialRodante('$ {mr.idMaterialRodante}')" value="X"></td>-->
                            <td><img src="img/icon/delete-20x20.png" alt="Eliminar" title="Eliminar" onclick="eliminarMaterialRodante('${mr.idMaterialRodante}')" ></td>
                        </tr>
                    </c:forEach>
                </c:if>
            </table>
        </div>