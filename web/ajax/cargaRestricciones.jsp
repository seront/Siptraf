<%-- 
    Document   : estaciones
    Created on : 06/01/2015, 09:16:12 AM
    Author     : seront
--%>
<%@include file="../jslt.jsp" %>

<c:set var="idLinea" value="${param.idLinea}"></c:set>
<c:set var="progEstacionInicial" value="${param.progEstacionInicial}"></c:set>
<c:set var="progEstacionFinal" value="${param.progEstacionFinal}"></c:set>
<c:set var="velMax" value="${param.velMax}"></c:set>
<jsp:useBean class="modelo.GestorLista" id="gl"/>
    <h2>Restricciones</h2>
<c:choose>
    <c:when test="${progEstacionInicial<progEstacionFinal}">
        <table id="tablaRestricciones" class="tablas" >
        <tbody>          
            <tr >
                <td>Incluir en la Simulación</td>
                <td>Progresiva Inicio</td>
                <td>Progresiva Final</td>                              
                <td>Velocidad Máxima Ascendente</td>
            </tr>
            
            <c:choose>                    
                <c:when test="${progEstacionInicial<progEstacionFinal}">
                    <c:set var="restricciones" value="${gl.buscarRestriccionEntreEstacionesAscendente(idLinea, progEstacionInicial, progEstacionFinal, velMax)}"/>
                </c:when>
                <c:otherwise>
                    <c:set var="restricciones" value="${gl.buscarRestriccionEntreEstacionesDescendente(idLinea, progEstacionInicial, progEstacionFinal, velMax)}"/>
                </c:otherwise>
            </c:choose>

            <c:forEach var="restriccion" items="${restricciones}">
                <tr class="campoFormulario">
                    <td><input type="checkbox" class="incluir" value="${restriccion.restriccionPK.idRestriccion}"></td>
                    <td>${restriccion.progInicio}</td>                              
                    <td>${restriccion.progFinal}</td> 
                    <td>${restriccion.getVelocidadMaxAscendente()}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <div id="contenedorImg">
        <!--<input type="button" id="simular" class="botonContinuar" value="Simular" onclick="simular()"/>--> 
        <img src="img/icon/train-icon.png" alt="Simular" title="Simular" onclick="simular()" style="margin-top: 15px;">
    </div>
        
    </c:when>
    <c:otherwise>
        <table id="tablaRestricciones" class="tablas" >
        <tbody>          
            <tr >
                <td>Incluir en la Simulación</td>
                <td>Progresiva Final</td>
                <td>Progresiva Inicio</td>
                <td>Velocidad Máxima Descendente</td>
            </tr>
          
            <c:choose>                    
                <c:when test="${progEstacionInicial<progEstacionFinal}">
                    <c:set var="restricciones" value="${gl.buscarRestriccionEntreEstacionesAscendente(idLinea, progEstacionInicial, progEstacionFinal, velMax)}"/>
                </c:when>
                <c:otherwise>
                    <c:set var="restricciones" value="${gl.buscarRestriccionEntreEstacionesDescendente(idLinea, progEstacionInicial, progEstacionFinal, velMax)}"/>
                </c:otherwise>
            </c:choose>

            <c:forEach var="restriccion" items="${restricciones}">
                <tr class="campoFormulario">
                    <td><input type="checkbox" class="incluir" value="${restriccion.restriccionPK.idRestriccion}"></td>
                    <td>${restriccion.progFinal}</td>                              
                    <td>${restriccion.progInicio}</td>                              
                    <td>${restriccion.getVelocidadMaxDescendente()}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <div class="contenedorBoton">
        <input type="button" id="simular" class="botonContinuar" value="Simular" onclick="simular()"/> 
    </div>
    </c:otherwise>
</c:choose>
    
<!--</div>-->

