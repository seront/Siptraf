<%@include file="../jslt.jsp" %>
<link href="css/estilo1.css" type="text/css" rel="stylesheet"/>

<c:set var="idLinea" value="${param.idLinea}"></c:set>
<c:set var="progEstacionInicial" value="${param.progEstacionInicial}"></c:set>
<c:set var="progEstacionFinal" value="${param.progEstacionFinal}"></c:set>
<jsp:useBean class="modelo.GestorLista" id="gl"/>

<c:choose>                    
                <c:when test="${progEstacionInicial<progEstacionFinal}">
                    <label class="tituloFormulario">Seleccionar Paradas</label> 
                    <c:set var="estaciones" value="${gl.seleccionarParadaEstacionAsc(progEstacionInicial, progEstacionFinal, idLinea)}"/>
                </c:when>
                <c:otherwise>
                    <label class="tituloFormulario">Seleccionar Paradas</label> 
                    <c:set var="estaciones" value="${gl.seleccionarParadaEstacionDesc(progEstacionInicial, progEstacionFinal, idLinea)}"/>
                </c:otherwise>
            </c:choose>

            <c:forEach var="estacion" items="${estaciones}">
                
            <div><input type="checkbox" class="incluirEstacion" value="${estacion.estacionPK.idPkEstacion}"> ${estacion.nombreEstacion} </div>
            </c:forEach>