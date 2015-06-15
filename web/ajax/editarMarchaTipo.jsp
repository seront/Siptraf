<%-- 
    Document   : editarMarchaTipo
    Created on : 10/03/2015, 09:37:59 AM
    Author     : Kelvins Insua
--%>

<%@page import="modelo.GestorLista"%>
<%@include file="../jslt.jsp" %>
<c:set var="idMarchaTipo" value="${param.idMarchaTipo}"/>
<c:set var="idPkEstacion" value="${param.idPkEstacion}"/>
<c:choose>
    <c:when test="${!empty idMarchaTipo}">
        <jsp:useBean class="modelo.GestorLista" id="gl"/>
        <c:set var="mt" value="${gl.buscarMarchaTipo(idMarchaTipo)}"/>   
        <c:set var="t" value="${gl.buscarTiempoEstacionMTPK(idMarchaTipo, idPkEstacion)}"></c:set>
        <div class="contenedorFormulario">
            <legend><h2>Editar Tiempo Adicional De La Estación ${t.nombreEstacion}</h2></legend>
            <form>
                <input type="hidden" id="hdd_id_marcha_tipo_ed" value="${idMarchaTipo}" >
                <input type="hidden" id="hdd_id_pk_est_ed" value="${idPkEstacion}" >
                
                <label class="tituloFormulario">Tiempo Adicional En Minutos Enteros</label>
                <input class="campoFormulario" type="number" id="num_tiempo_adicional_ed" >
                
                
                
<!--                <div class="contenedorBoton">
                    <input class="botonContinuar" type="button" value="Editar" onclick="editarMarchaTipo($ {idMarchaTipo},$ {idPkEstacion},$ {mt.sentido})">
                </div>                
                <div class="contenedorBoton">
                    <input class="botonContinuar" type="button" value="Cancelar" onclick="cancelarMarchaTipo()">
                </div>-->
                
                <div id="contenedorImg">
                    
                    <img src="img/icon/cancelar.png" alt="Cancelar" title="Cancelar"onclick="cancelarMarchaTipo()">
                    <img src="img/icon/edit-validated-icon.png" alt="Editar" title="Editar"onclick="editarMarchaTipo(${idMarchaTipo},${idPkEstacion},${mt.sentido})">
                </div>
            </form>
        </div>
    </c:when>
    <c:otherwise>
        Marcha Tipo no Encontrada
    </c:otherwise>

</c:choose>
