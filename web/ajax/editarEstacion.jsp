<%@page import="modelo.GestorLista"%>
<%--<%@include file="../css/estilo.css" %>--%>

<%@include file="../jslt.jsp" %>
<c:set var="idLinea" value="${param.idLinea}"/>
<c:set var="idPkEstacion" value="${param.idPkEstacion}"/>

<%--<c:set var="segmentoPk" value="${id}"/>--%>
<c:choose>
    <c:when test="${!empty idLinea}">
        <jsp:useBean class="modelo.GestorLista" id="gl"/>
        <c:set var="estacion" value="${gl.buscarEstacionPorPK(idLinea, idPkEstacion)}"/>
        <div class="contenedorFormulario">
            <legend>
                <h2>
                    Editar Estación ${estacion.nombreEstacion}
                </h2>
            </legend>
            <form>
                <input type="hidden" id="id_pk_estacion_ed" value="${idPkEstacion}" >
                <input type="hidden" id="id_linea_ed" value="${idLinea}" >
                Nombre De La Estación: <input class="campoFormulario" type="text" id="nombre_estacion_ed" value="${estacion.nombreEstacion}"><br/><br/>
                Abreviación De La Estación: <input class="campoFormulario" type="text" id="abrev_estacion_ed" value="${estacion.abrevEstacion}"><br/><br/>

<div id="contenedorImg">
                    
                    <img src="img/icon/cancelar.png" alt="Cancelar" title="Cancelar"onclick="cancelarEstacion()">
                    <img src="img/icon/edit-validated-icon.png" alt="Editar" title="Editar"onclick="editar()">
                </div>
            </form>
        </div>

    </fieldset>
</c:when>
<c:otherwise>
    Estación No Encontrada
</c:otherwise>

</c:choose>
