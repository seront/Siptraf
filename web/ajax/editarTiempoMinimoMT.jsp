<%-- 
    Document   : editarTiempoMinimoMT
    Created on : 20/05/2015, 10:01:53 AM
    Author     : Kelvins Insua
--%>
<%@page import="modelo.GestorLista"%>
<%@include file="../jslt.jsp" %>
<c:set var="idMarchaTipo" value="${param.idMarchaTipo}"/>
<c:choose>
    <c:when test="${!empty idMarchaTipo}">
        <jsp:useBean class="modelo.GestorLista" id="gl"/>
        <c:set var="mt" value="${gl.buscarMarchaTipo(idMarchaTipo)}"/>   
        
        <div class="contenedorFormulario">
            <legend><h2>Editar Tiempo Minimo De La Marcha Tipo ${mt.nombreMarchaTipo}</h2></legend>
            <form>
                <input type="hidden" id="hdd_id_marcha_tipo_tm" value="${idMarchaTipo}" >
                
                
                <label class="tituloFormulario">Tiempo MinimoEn Minutos Enteros</label>
                <input class="campoFormulario" type="number" id="num_tiempo_minimo_ed" >
                
                
                
<!--                <div class="contenedorBoton">
                    <input class="botonContinuar" type="button" value="Editar" onclick="editarMarchaTipo($ {idMarchaTipo},$ {idPkEstacion},$ {mt.sentido})">
                </div>                
                <div class="contenedorBoton">
                    <input class="botonContinuar" type="button" value="Cancelar" onclick="cancelarMarchaTipo()">
                </div>-->
                
                <div id="contenedorImg">
                    
                    <img src="img/icon/cancelar.png" alt="Cancelar" title="Cancelar"onclick="cancelarMarchaTipo()">
                    <img src="img/icon/edit-validated-icon.png" alt="Editar" title="Editar"onclick="editarTiempoMinimoMT(${idMarchaTipo})">
                </div>
            </form>
        </div>
    </c:when>
    <c:otherwise>
        Marcha Tipo no Encontrada
    </c:otherwise>
        </c:choose>
