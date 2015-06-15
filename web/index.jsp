
<!-- Document   : index
    Created on : 01/11/2014, 10:03:32 AM
    Author     : Kelvins Insua-->


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="jslt.jsp" %>
<%@include file="estilo.jsp" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="SquareMenu.jsp" %>
        <script src="js/sesionCerrar.js" type="text/javascript"></script>
        <title>S.I.P.T.R.A.F</title>
        
    </head>
    <body>

        <div id="bgVentanaModal">
            <div id="msjajax"></div>
            <div id="datos">                
            </div>
        </div>
        <header>
            <%@include file="encabezado.jsp" %>
        </header>
        <c:choose>
            <c:when test="${!empty sessionScope.usuario}">
                <div id="header">
                    <ul class="nav">
                        <li><a href="" id="nombreUsuario">${sessionScope.usuario}</a>
                            <ul>
                                <li onclick="cerrarSesion()"><a href="">Cerrar Sesión</a></li>
                                <li onclick="cambiarContraseña()"><a >Cambiar Contraseña</a></li>

                            </ul>
                        </li>

                    </ul>
                </div>

            </c:when>
            <c:otherwise>
                <script src="js/sesion.js" type="text/javascript" rel=""></script>
            </c:otherwise>
        </c:choose>

        <h2>sistema inteligente para la planificación del tráfico ferroviario</h2>
        <p class="credit">Creado Por: Ing. Kelvins Insua e Ing. Néstor Mendoza</p>
        <div class="wrapper">
            <div class="main">


                <div class="page-container">
                    <div class="sidemenu">
                        <c:if test="${sessionScope.nivel=='1'}">
                            <nav class="left">
                                <a href="ingresoLinea.jsp">Administrar Línea</a>
                                <a href="ingresoMaterialRodante.jsp">Administrar Material Rodante</a>
                                <a href="InicioProgramacion.jsp">Nueva Programación Horaria</a>
                                <a href="ingresoMarchaTipo.jsp">Nueva Marcha Tipo</a>
                                <a href="administrarMarchaTipo.jsp">Administrar Marchas Tipo</a>
                            </nav>
                            <nav class="right">
                                <!--<a href="Concepto.jsp" target="_blank">¿Qué es S.I.P.T.R.A.F?</a>-->
                                <a href="ingresoUsuario.jsp">Administrar Usuarios</a>
                                <a href="ingresoSegmento.jsp">Administrar Segmentos</a>
                                <a href="ingresoRestriccion.jsp">Administrar Restricciones</a>
                                <a href="ingresoCircuitoVia.jsp">Administrar Circuitos de Vía</a>
                                <a href="ingresoEstacion.jsp">Administrar Estaciones</a>
                                <a href="ingresoCurvasMaterialRodante.jsp">Curvas de Esfuerzo del Material Rodante</a>
                                <a href="ingresoImagen.jsp">Cambiar Cintillo</a>

                            </nav>
                        </c:if>
                        <c:if test="${sessionScope.nivel=='2'}">
                            <nav class="left">
                                <a href="ingresoSegmento.jsp">Administrar Segmentos</a>
                                <a href="administrarMarchaTipo.jsp">Administrar Marchas Tipo</a>
                                <a href="InicioProgramacion.jsp">Nueva Programación Horaria</a>
                                <a href="ingresoMarchaTipo.jsp">Nueva Marcha Tipo</a>
                            </nav>
                            <nav class="right">
                                <!--<a href="Concepto.jsp" target="_blank">¿Qué es S.I.P.T.R.A.F?</a>-->
                                
                                <a href="ingresoRestriccion.jsp">Administrar Restricciones</a>
                                <a href="ingresoCircuitoVia.jsp">Administrar Circuitos de Vía</a>
                                <a href="ingresoEstacion.jsp">Administrar Estaciones</a>

                            </nav>
                        </c:if>
                        <c:if test="${sessionScope.nivel=='3'}">
                            <nav class="left">
                                <a href="ingresoRestriccion.jsp">Administrar Restricciones</a>
                            </nav>
                            <nav class="right">
                                

                            </nav>
                        </c:if>

                    </div>
                </div>
            </div>

        </div>
    </body>
</html>
