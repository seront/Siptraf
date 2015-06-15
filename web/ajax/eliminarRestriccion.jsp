<%@page import="modelo.GestorLista"%>
<%--<%@include file="../css/estilo.css" %>--%>
<%@include file="../jslt.jsp" %>
<c:set var="idLinea" value="${param.idLinea}"/>
<c:set var="idRestriccion" value="${param.idRestriccion}"/>
<c:choose>
    <c:when test="${!empty idLinea}">
        <jsp:useBean class="modelo.GestorLista" id="gl"/>
        <c:set var="restriccion" value="${gl.buscarRestriccionPorPK(idLinea, idRestriccion)}"/>

<div class="contenedorFormulario">
    <legend><h2>Eliminar Restricciín de Progresiva Inicial ${restriccion.progInicio}</h2></legend>
            <form>
                <input type="hidden" id="id_restriccion_el" value="${idRestriccion}" >
                <input type="hidden" id="id_linea_el" value="${idLinea}" >
                <p>¿Está seguro que quieres eliminar la restricción de progresiva ${restriccion.progInicio}?,
                pulse Eliminar para continuar, pulse Cancelar para salir</p>
                
                <div id="contenedorImg">
                    
                    <img src="img/icon/cancelar.png" alt="Cancelar" title="Cancelar"onclick="cancelarRestriccion()">
                    <img src="img/icon/delete-icon.png" alt="Eliminar" title="Eliminar"onclick="eliminarRes()">
                </div>
            </form>
</div>             
       
    </c:when>
    <c:otherwise>
        Restriccion No Encontrada
    </c:otherwise>
    
</c:choose>
