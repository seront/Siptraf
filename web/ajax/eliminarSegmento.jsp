<%@page import="modelo.GestorLista"%>
<%--<%@include file="../css/estilo.css" %>--%>
<%@include file="../jslt.jsp" %>
<c:set var="idLinea" value="${param.idLinea}"/>
<c:set var="idPkInicial" value="${param.idPkInicial}"/>
<c:choose>
    <c:when test="${!empty idLinea}">
        <jsp:useBean class="modelo.GestorLista" id="gl"/>
        <c:set var="segmento" value="${gl.buscarSegmentoPorPK(idLinea, idPkInicial)}"/>
<div class="contenedorFormulario">
     <legend class="tituloFormulario">Eliminar Segmento de Progresiva Inicial ${idPkInicial}</legend>
            <form>
                <input type="hidden" id="progresiva" value="${idPkInicial}" >
                <input type="hidden" id="linea" value="${idLinea}" >
                <p>¿Está seguro que quieres eliminar el segmento de progresiva inicial ${idPkInicial}?,
                pulse Eliminar para continuar, pulse Cancelar para salir</p>
               
                <div id="contenedorImg">
                    
                    <img src="img/icon/cancelar.png" alt="Cancelar" title="Cancelar"onclick="cancelarSegmento()">
                    <img src="img/icon/delete-icon.png" alt="Eliminar" title="Eliminar"onclick="eliminarS('${idPkInicial}','${idLinea}')">
                </div>
            </form>
</div>
            
       
    </c:when>
    <c:otherwise>
        Segmento No Encontrado
    </c:otherwise>
    
</c:choose>
