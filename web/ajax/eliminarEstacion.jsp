<%@page import="modelo.GestorLista"%>
<%--<%@include file="../css/estilo.css" %>--%>
<%@include file="../jslt.jsp" %>
<c:set var="idLinea" value="${param.idLinea}"/>
<c:set var="idPkEstacion" value="${param.idPkEstacion}"/>
<c:choose>
    <c:when test="${!empty idLinea}">
        <jsp:useBean class="modelo.GestorLista" id="gl"/>
        <c:set var="estacion" value="${gl.buscarEstacionPorPK(idLinea, idPkEstacion)}"/>
        <div class="contenedorFormulario">
             <legend><h2>Eliminar Estación ${estacion.nombreEstacion}</h2></legend>
            <form>
                <input type="hidden" id="id_pk_estacion_el" value="${idPkEstacion}" >
                <input type="hidden" id="id_linea_el" value="${idLinea}" >
                <p>¿Está seguro que quieres eliminar la estación ${estacion.nombreEstacion}?,
                pulse Eliminar para continuar, pulse Cancelar para salir</p>
                
                <div id="contenedorImg">
                    
                    <img src="img/icon/cancelar.png" alt="Cancelar" title="Cancelar"onclick="cancelarEstacion()">
                    <img src="img/icon/delete-icon.png" alt="Eliminar" title="Eliminar"onclick="eliminar()">
                </div>
            </form>
        </div>
    </c:when>
    <c:otherwise>
        Estacion No Encontrada
    </c:otherwise>
    
</c:choose>