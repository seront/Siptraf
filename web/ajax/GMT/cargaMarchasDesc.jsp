<%@include file="../../jslt.jsp"%>

<c:set var="idLinea" value="${param.id_linea}"></c:set>

<c:if test="${!empty idLinea}">
    <jsp:useBean class="modelo.GestorLista" id="gl"/>
     <option value="">
        Seleccione la marcha tipo
    </option>
        <c:forEach var="marTip" items="${gl.listarMarchaTipoDesc(idLinea)}">
            <option value="${marTip.idMarchaTipo}">
                ${marTip.nombreMarchaTipo}
            </option>
        </c:forEach>
</c:if>

