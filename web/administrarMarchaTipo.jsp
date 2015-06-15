<%-- 
    Document   : administrarMarchaTipo
    Created on : 25/02/2015, 11:36:56 AM
    Author     : Kelvins Insua
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="estilo.jsp" %>
<%@include file="jslt.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <script src="js/jquery-1.9.1.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="js/marchaTipo.js" type="text/javascript" rel=""></script>
        <script src="js/highcharts.js" type="text/javascript" rel=""></script>
        <script src="js/exporting.js" type="text/javascript" rel=""></script>
        <script src="js/sesionCerrar.js" type="text/javascript"></script>
        <title>S.I.P.T.R.A.F</title>
        
    </head>
    <body>
        <main>
            <div id="bgVentanaModal" class="dialogoModal">
                <div id="msjajax"></div>
                <div id="datos">                
                </div>

            </div>
            <header>
                <p class="titulo">Administrar Marchas Tipo</p>
            </header>
            <c:choose>
                <c:when test="${!empty sessionScope.usuario}">
                    <div id="header">
                        <ul class="nav">
                            <input type="hidden" value="${sessionScope.usuario}" id="sessionActiva">
                            <li><a href="">${sessionScope.usuario}</a>
                                <ul>
                                    <li onclick="cerrarSesion()"><a href="">Cerrar Sesión</a></li>
                                    <li onclick="cambiarContraseña()"><a >Cambiar Contraseña</a></li>
                                </ul>
                            </li>

                        </ul>
                    </div>

                </c:when>
                <c:otherwise>
                    <input type="hidden" value="a" id="sessionActiva">
                    <script src="js/sesion.js" type="text/javascript" rel=""></script>
                </c:otherwise>
            </c:choose>

            <div style="height: 50px">
                <input type="button" id="btnMenu" class="btnIrAlMenu" value="Ir al Menú" onclick="location.href = 'index.jsp'">
            </div>
            <c:if test="${sessionScope.nivel<='2'}">
                <div id="tablaMarchaTipo" class="contenedorFormulario" style="width: 100%;">
                    <p><image class="cargando" src="img/ajax-loader.gif"/></p>
                </div> 
                <div id="resultadoMarchaTipo" class="contenedorFormulario">
                    <img class="cargando2" src="img/cargando2.gif" id="car3"/>                
                    
                    <div id="msjMT">                           

                    </div>

                </div>
                <div id="marchaSeleccionada"></div>
                <div id="graficoMarchaTipo" style="margin-top: 15px;">

                </div>
                <div id="graficoMarchaTipoTiempoVelocidad" style="margin-top: 15px;">

                </div>
            </c:if>
            <footer>
                <%@include file="footer.jsp" %>
            </footer>

        </main>
    </body>
</html>
