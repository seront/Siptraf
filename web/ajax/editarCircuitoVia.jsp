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
            <h2>Editar Circuito de V�a de Progresiva ${idPkInicialCircuito}</h2>
            <form>        
                <input type="hidden" id="hdd_prog_ini_cir_ed" value="${idPkInicialCircuito}" >
                <input type="hidden" id="hdd_id_linea_ed" value="${idLinea}" >
                <label class="tituloFormulario">Punto Kilom�trico Final</label>
                <input type="text" class="campoFormulario" id="txt_prog_fin_cir_ed" value="${circuitoVia.pkFinalCircuito}">
                <div id="contenedorImg">
                    
                    <img src="img/icon/cancelar.png" alt="Cancelar" title="Cancelar"onclick="cancelarCircuitoVia()">
                    <img src="img/icon/edit-validated-icon.png" alt="Editar" title="Editar"onclick="editarCirVia()">
                </div>
            </form>
        </div>
    </c:when>
    <c:otherwise>
        Circuito de v�a no encontrado
    </c:otherwise>

</c:choose>
