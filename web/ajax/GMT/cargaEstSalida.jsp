<%@include file="../../jslt.jsp"%>

<c:set var="idMT" value="${param.id_marcha_tipo}">    
</c:set>

<jsp:useBean class="modelo.GestorLista" id="gl"/>
<c:set var="estSalida" value="${gl.buscarParadasSalida(idMT)}">                    
</c:set>
<option value="">
    Seleccione...
</option> 
<option value="${estSalida.tiempoEstacionMarchaTipoPK.idPkEstacion}">
    ${estSalida.nombreEstacion}
</option>

<c:forEach var="estSalida" items="${gl.buscarParadasMT(idMT)}">
    <option value="${estSalida.tiempoEstacionMarchaTipoPK.idPkEstacion}">
        ${estSalida.nombreEstacion}
    </option>
</c:forEach>     



