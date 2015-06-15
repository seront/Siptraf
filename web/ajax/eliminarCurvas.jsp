<%@page import="modelo.GestorLista"%>
<%--<%@include file="../css/estilo.css" %>--%>
<%@include file="../jslt.jsp" %>
<c:set var="idMatRod" value="${param.idMatRod}"/>
<c:set var="vel" value="${param.vel}"/>
<c:choose>
    <c:when test="${!empty idMatRod}">
        <jsp:useBean class="modelo.GestorLista" id="gl"/>
        <%--<c:set var="curva" value="${gl.buscarEstacionPorPK(idLinea, idNombreEstacion)}"/>--%>
        <c:set var="curva" value="${gl.buscarCurva(idMatRod, vel)}"/>
        <div class="contenedorFormulario">
             <legend><h2>Eliminar ${vel}</h2></legend>
            <form>
                <input type="hidden" id="id_mat_rod_el" value="${idMatRod}" >
                <input type="hidden" id="id_vel_el" value="${vel}" >
                <p>¿Está seguro que quieres eliminar el registro?,
                pulse Eliminar para continuar, pulse Cancelar para salir</p>
                
                <div id="contenedorImg">
                    
                    <img src="img/icon/cancelar.png" alt="Cancelar" title="Cancelar"onclick="cancelarCurva()">
                    <img src="img/icon/delete-icon.png" alt="Eliminar" title="Eliminar"onclick="eliminarC()">
                </div>
            </form>
        </div>
    </c:when>
    <c:otherwise>
        Error
    </c:otherwise>
    
</c:choose>