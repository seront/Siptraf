<%@include file="../../jslt.jsp"%>

<c:set var="idLinea" value="${param.id_linea}"></c:set>

<c:if test="${!empty idLinea}">
    <jsp:useBean class="modelo.GestorLista" id="gl"/>

    <c:forEach var="estParada" items="${gl.buscarEstacion(idLinea)}" begin="1" end="${gl.buscarEstacion(idLinea).size()-2}">
        <label class="tituloFormulario">Estación ${estParada.nombreEstacion} (min)</label>
        <input type="number" class="campoFormulario estacionesAsc" id="${estParada.estacionPK.idPkEstacion}" title="Minutos de parada estandar en esta estacion" min="0" step="1"/>
    </c:forEach>        
</c:if>

