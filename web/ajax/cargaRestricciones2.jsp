<%-- 
    Document   : estaciones
    Created on : 06/01/2015, 09:16:12 AM
    Author     : seront
--%>
<%@include file="../jslt.jsp" %>

<c:set var="idLinea" value="${param.idLinea}"></c:set>

<!--<div class="contenedorFormulario" id="contRestricciones">-->
<!--<div id="contRestricciones">-->
<jsp:useBean class="modelo.GestorLista" id="gl"/>            
    <h2>Restricciones</h2>
<c:choose>
    <c:when test="${!empty idLinea}">
        <table class="tablas" >
        <tbody>          
            <c:if test="${sessionScope.nivel<='2'}">
            <tr >
                <td>Progresiva Inicio</td>
                <td>Progresiva Final</td>
                <td>Registrada Por</td>                              
                <td>Velocidad Máxima Ascendente</td>
                <td>Velocidad Máxima Descendente</td>
                <td>Fecha de Registro</td>
            </tr>
            
                    <c:set var="restricciones" value="${gl.buscarRestriccion(idLinea)}"/>               
            <c:forEach var="restriccion" items="${restricciones}">
                <tr class="campoFormulario">
                    <!--<td>$ {restriccion.restriccionPK.idRestriccion}</td>-->
                    <!--<td><input type="button" onmouseover="cambiarNombre('$ {restriccion.progInicio}')" onmouseout="valorInicial('$ {restriccion.progInicio}')" id="$ {restriccion.progInicio}" onclick="editarRestriccion('$ {idLinea}','$ {restriccion.restriccionPK.idRestriccion}')" value="$ {restriccion.progInicio}"></td>-->                              
                    <td><img alt="Editar" title="Editar"  src="img/icon/edit-20x20.png" onclick="editarRestriccion('${idLinea}','${restriccion.restriccionPK.idRestriccion}')" value="${restriccion.progInicio}">${restriccion.progInicio}</td>                              
                    <td>${restriccion.progFinal}</td>                              
                    <td>${restriccion.getUsuario()}</td> 
                    <td>${restriccion.getVelocidadMaxAscendente()}</td>
                    <td>${restriccion.getVelocidadMaxDescendente()}</td>
                    <td>${restriccion.fechaRegistro}</td>
                    <!--<td><input type="button" onclick="eliminarRestriccion('${idLinea}','${restriccion.restriccionPK.idRestriccion}')" value="X"></td>-->
                    <td><img src="img/icon/delete-20x20.png" alt="Eliminar" title="Eliminar" onclick="eliminarRestriccion('${idLinea}','${restriccion.restriccionPK.idRestriccion}')" ></td>
                </tr>
            </c:forEach>
            </c:if>
            <c:if test="${sessionScope.nivel=='3'}">
            <tr >
                <td>Progresiva Inicio</td>
                <td>Progresiva Final</td>
                <td>Registrada Por</td>                              
                <td>Velocidad Máxima Ascendente</td>
                <td>Velocidad Máxima Descendente</td>
                <td>Fecha de Registro</td>
            </tr>
            
                    <c:set var="restricciones" value="${gl.buscarRestriccion(idLinea)}"/>               
            <c:forEach var="restriccion" items="${restricciones}">
                <tr class="campoFormulario">
                    <!--<td>$ {restriccion.restriccionPK.idRestriccion}</td>-->
                    <td>${restriccion.progInicio}</td>                              
                    <td>${restriccion.progFinal}</td>                              
                    <td>${restriccion.getUsuario()}</td> 
                    <td>${restriccion.getVelocidadMaxAscendente()}</td>
                    <td>${restriccion.getVelocidadMaxDescendente()}</td>
                    <td>${restriccion.fechaRegistro}</td>
                </tr>
            </c:forEach>
            </c:if>
        
                   
        </tbody>
    </table>
        <input type="button" onclick="datosDocTren(${idLinea})" value="Generar Documento de Tren" class="botonContinuar" style="margin-top: 15px; float: right;">
    </c:when>
    <c:otherwise>
        <h3>Restricciones no encontradas</h3>
    </c:otherwise>
</c:choose>
    
    
<!--</div>-->

