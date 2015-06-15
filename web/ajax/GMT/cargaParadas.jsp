<%@include file="../../jslt.jsp"%>

<c:set var="idMarchaTipo" value="${param.id_marcha_tipo}">    
</c:set>
<c:set var="estInicio" value="${param.estacion_inicio}">    
</c:set>
<c:set var="estFinal" value="${param.estacion_final}">    
</c:set>

<jsp:useBean class="modelo.GestorLista" id="gl"/>
        <c:forEach var="estParada" items="${gl.buscarParadasIntermediasMT(idMarchaTipo, estInicio, estFinal)}">
    <label class="tituloFormulario">Estación ${estParada.nombreEstacion} (min)</label>
    <input type="number" class="campoFormulario estacionesAg" id="${estParada.tiempoEstacionMarchaTipoPK.idPkEstacion}" title="Minutos de parada en esta estacion" min="0" step="1">
</c:forEach> 
       


