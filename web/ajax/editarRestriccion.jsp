<%@page import="modelo.GestorLista"%>
<%--<%@include file="../css/estilo.css" %>--%>

<%@include file="../jslt.jsp" %>
<c:set var="idLinea" value="${param.idLinea}"/>
<c:set var="idRestriccion" value="${param.idRestriccion}"/>

<%--<c:set var="segmentoPk" value="${id}"/>--%>
<c:choose>
    <c:when test="${!empty idLinea}">
        <jsp:useBean class="modelo.GestorLista" id="gl"/>

        <c:set var="restriccion" value="${gl.buscarRestriccionPorPK(idLinea, idRestriccion)}"/>        
        <div class="contenedorFormulario">
            <legend><h2>Editar Restricción de progresiva Inicial ${restriccion.progInicio}</h2></legend>            
            <form>
                <input type="hidden" id="id_restriccion_ed" value="${restriccion.restriccionPK.idRestriccion}" >
                <input type="hidden" id="id_linea_ed" value="${idLinea}" >
                <label class="tituloFormulario">Velocidad Máx. Ascendente</label>
                <input class="campoFormulario" type="text" id="vel_max_ascendente_ed" value="${restriccion.velocidadMaxAscendente}" min="1">
                <label class="tituloFormulario">Velocidad Máx. Descendente</label>
                <input class="campoFormulario" type="text" id="vel_max_descendente_ed"  value="${restriccion.velocidadMaxDescendente}" min="1">
                <label class="tituloFormulario">Progresiva Inicio</label>
                <input class="campoFormulario" type="text" id="prog_inicio_ed" value="${restriccion.progInicio}">
                <label class="tituloFormulario">Progresiva Final</label>
                <input class="campoFormulario" type="text" id="prog_final_ed" value="${restriccion.progFinal}">
                <label class="tituloFormulario">Fecha de Registro</label>
                <input class="campoFormulario" type="date" id="fecha_registro_ed" value="${restriccion.fechaRegistro}">
                <label class="tituloFormulario">Observación</label>
                <input type="text" id="observacion_ed"  class="campoFormulario" placeholder="observacion" value="${restriccion.observacion}" maxlength="50">
                
                <div id="contenedorImg">
                    
                    <img src="img/icon/cancelar.png" alt="Cancelar" title="Cancelar"onclick="cancelarRestriccion()">
                    <img src="img/icon/edit-validated-icon.png" alt="Editar" title="Editar"onclick="editarRes()">
                </div>
            </form>
        </div>
    </c:when>
    <c:otherwise>
        Restricción No Encontrada
    </c:otherwise>

</c:choose>