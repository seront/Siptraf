<%@include file="../jslt.jsp" %>

<link href="css/estilo1.css" type="text/css" rel="stylesheet"/>
<c:set var="idMarchaTipo" value="${param.idMarchaTipo}"></c:set>
<jsp:useBean class="modelo.GestorLista" id="gl"/>
<c:set var="mt" value="${gl.buscarMarchaTipo(idMarchaTipo)}"></c:set>
<c:set var="sentido" value="${mt.sentido}"></c:set>
<c:set var="tiempoEstaciones" value="${gl.buscarTiemposMarchaTipo(idMarchaTipo,sentido)}"></c:set>
<c:set var="restricciones" value="${gl.buscarRestriccionesMarchaTipo(idMarchaTipo)}"></c:set>
<c:set var="x" value="0"></c:set>

    <p class="titulo" style="position: relative;">Marcha Tipo ${mt.nombreMarchaTipo}</p>


<div id="varGraficos">

</div>        
<c:if test="${mt!='null'}">
    <div class="contenedorFormulario" style="width: 100%;">
    </c:if>
    <c:if test="${tiempoEstaciones!=null}">


        
            <table class="tablas">
                <tr >
                    <td style="width: 100%;">Tiempo Entre Estaciones</td>
                </tr>
            </table>
            <table class="tablas" id="tiempoEstaciones">

                <tr>

                    <td>Nombre De La Estación</td>   
                    <td>Tiempo Ideal</td>
                    <td>Tiempo Márgen</td>
                    <td>Tiempo Estimado</td>
                    <td>Tiempo Redondeo</td>
                    <td>Tiempo Adicional</td>
                    <td>Tiempo Asimilado</td>
                    <td>Tiempo Por Limitaciones</td>
                    <td>Parada</td>
                </tr>



                <tr>
                    
                    <c:forEach var="t" items="${tiempoEstaciones}" begin="0" end="0">
                        <td> ${t.nombreEstacion}</td>
                        <td>${t.tiempoIdeal}</td>
                        <td>${t.tiempoMargen}</td>
                        <td>${t.tiempoReal}</td>
                        <td>${t.tiempoRedondeo}</td>
                        <td>${t.tiempoAdicional}</td>
                        <td>${t.tiempoAsimilado}</td>
                        <td>${t.tiempoPerdidoRest}</td>
                        <c:choose>
                            <c:when test="${t.parada==true}">
                                <td>Si</td>
                            </c:when>
                            <c:otherwise>
                                <td>No</td>
                            </c:otherwise>
                        </c:choose>


                    </tr>
                  
                </c:forEach>
                    <c:forEach var="t" items="${tiempoEstaciones}" begin="1">
                        <td> ${t.nombreEstacion}</td>
                        <td>${t.tiempoIdeal}</td>
                        <td>${t.tiempoMargen}</td>
                        <td>${t.tiempoReal}</td>
                        <td>${t.tiempoRedondeo}</td>
                        <td><img src="img/icon/edit-20x20.png" onclick="editarTiempoAdicional(${idMarchaTipo},${t.tiempoEstacionMarchaTipoPK.idPkEstacion})" alt="Editar" title="Editar">${t.tiempoAdicional}</td>
                        <td>${t.tiempoAsimilado}</td>
                        <td>${t.tiempoPerdidoRest}</td>
                        <c:choose>
                            <c:when test="${t.parada==true}">
                                <td>Si</td>
                            </c:when>
                            <c:otherwise>
                                <td>No</td>
                            </c:otherwise>
                        </c:choose>


                    </tr>
                  
                </c:forEach>
                


            </table>
        

    </c:if>

    <c:if test="${restricciones!=null}">


        
            <table class="tablas">
                <tr>
                    <td>Restricciones Incluidas</td>
                </tr>
            </table>
            <table class="tablas">

                <tr>

                    <td>Progresiva de Inicio</td>   
                    <td>Progresiva Final</td>
                    <td>Velocidad Máxima Ascendente</td>
                    <td>Velocidad Máxima Descendente</td>
                </tr>



                <tr>
                    <c:forEach var="r" items="${restricciones}">
                        <td> ${r.restriccionMarchaTipoPK.idPkInicialResMt}</td>
                        <td>${r.pkFinalResMt}</td>
                        <td>${r.velocidadMaxAscendente}</td>
                        <td>${r.velocidadMaxDescendente}</td>

                    </tr>
                </c:forEach>


            </table>
        

    </c:if>

</div>



