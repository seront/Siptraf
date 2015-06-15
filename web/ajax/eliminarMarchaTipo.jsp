<%-- 
    Document   : eliminarMarchaTipo
    Created on : 25/02/2015, 07:56:43 PM
    Author     : Kelvins Insua
--%>

<%@page import="modelo.GestorLista"%>
<%--<%@include file="../css/estilo.css" %>--%>
<%@include file="../jslt.jsp" %>
<c:set var="idMarchaTipo" value="${param.idMarchaTipo}"/>
<jsp:useBean class="modelo.GestorLista" id="gl"/>
<c:set var="mt" value="${gl.buscarMarchaTipo(idMarchaTipo)}"></c:set>
<c:choose>
    <c:when test="${!empty idMarchaTipo}">
        
        <div class="contenedorFormulario">
             <legend><h2>Eliminar Marcha Tipo ${mt.nombreMarchaTipo}</h2></legend>
            <form>
                <input type="hidden" id="id_marcha_tipo" value="${idMarchaTipo}" >
                <input type="hidden" id="ocultar" value="${0}" >
                <p>¿Está seguro que quieres eliminar la Marcha Tipo ${mt.nombreMarchaTipo}?,
                pulse Eliminar para continuar, pulse Cancelar para salir</p>
<!--                <div class="contenedorBoton">
                    <input type="button" class="botonContinuar" value="Eliminar" onclick="eliminarMarchaTipo()">
                </div>                
                <div class="contenedorBoton">
                    <input type="button" class="botonContinuar" value="Cancelar" onclick="cancelarMarchaTipo()">
                </div>-->
                <div id="contenedorImg">
                    
                    <img src="img/icon/cancelar.png" alt="Cancelar" title="Cancelar"onclick="cancelarMarchaTipo()">
                    <img src="img/icon/delete-icon.png" alt="Eliminar" title="Eliminar"onclick="eliminarMarchaTipo()">
                </div>
            </form>
        </div>
    </c:when>
    <c:otherwise>
        Marcha Tipo No Encontrada
    </c:otherwise>
    
</c:choose>
