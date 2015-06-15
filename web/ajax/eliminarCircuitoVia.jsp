<%@page import="modelo.GestorLista"%>
<%--<%@include file="../css/estilo.css" %>--%>
<%@include file="../jslt.jsp" %>
<c:set var="idLinea" value="${param.idLinea}"/>
<c:set var="idPkInicialCircuito" value="${param.idPkInicialCircuito}"/>
<c:choose>
    <c:when test="${!empty idLinea}">
        <jsp:useBean class="modelo.GestorLista" id="gl"/>
        <c:set var="circuitoVia" value="${gl.buscarCircuitoViaPorPK(idLinea, idPkInicialCircuito)}"/>
        <div class="contenedorFormulario">
            <h2>Eliminar Circuito de Vía con Progresiva Inicial ${idPkInicialCircuito}</h2>
            <form>
                <input type="hidden" id="hdd_prog_ini_cir" value="${idPkInicialCircuito}" >
                <input type="hidden" id="hdd_id_linea" value="${idLinea}" >
                <p>¿Está seguro que quieres eliminar el circuito de via con progresiva inicial ${idPkInicialCircuito}?,
                pulse Eliminar para continuar, pulse Cancelar para salir</p>
                
                <div id="contenedorImg">
                    
                    <img src="img/icon/cancelar.png" alt="Cancelar" title="Cancelar"onclick="cancelarCircuitoVia()">
                    <img src="img/icon/delete-icon.png" alt="Eliminar" title="Eliminar"onclick="eliminarCirVia()">
                </div>
            </form>
        </div>
      
       
    </c:when>
    <c:otherwise>
        Circuito de Via No Encontrado
    </c:otherwise>
    
</c:choose>