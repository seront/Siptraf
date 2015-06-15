<%-- 
    Document   : ingresoAfluencia
    Created on : 01/06/2015, 09:53:23 AM
    Author     : Seront
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="estilo.jsp" %>
<%@include file="jslt.jsp" %>


<!DOCTYPE html>
<html ng-app="afluApp">
    <head>
        <script src="js/jquery-1.9.1.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="js/GMT/highcharts.js"></script>        
        <script type="text/javascript" src="js/GMT/exporting-src.js"></script>
        <script type="text/javascript" src="js/GMT/angular.js"></script>    
        <script src="js/administrarAfluencia.js" type="text/javascript"></script>
        <title>S.I.P.T.R.A.F</title>        
    </head>
    <body ng-controller="afluAppCtrl as afac">
        <div id="bgVentanaModal" class="dialogoModal">
            <div id="msjajax"></div>
            <div id="datos">                
            </div>

        </div>
        <header>
            <p class="titulo">Administrar Afluencia</p>
        </header>
        <main>
            <div style="height: 50px">
                <input type="button" id="btnMenu" class="btnIrAlMenu" value="Ir al Menú" onclick="location.href = 'index.jsp'">
            </div>
            <div class="contenedorFormulario">
                <h2>Ingresar Afluencia de pasajeros</h2>                
                <form>                    
                    <label class="tituloFormulario">Linea </label> 
                    <select class="campoFormulario" id="cmb_lineas" ng-model="afac.cmb_lineas" ng-change="afac.cargarEstaciones()">
                        <option value="">Seleccione...</option>
                        <jsp:useBean class="modelo.GestorLista" id="gl"/>
                        <c:forEach var="b" items="${gl.listaLinea()}">
                            <option value="${b.idLinea}">${b.nombreLinea}</option>
                        </c:forEach>
                    </select>
                    <label class="tituloFormulario">Estación</label> 
                    <!--<select class="campoFormulario" ng-options="(estacion.nombreEstacion) for estacion in afac.estaciones" ng-model="afac.estacionActual" ng-change="afac.cargarTabla()">-->
                    <select class="campoFormulario" ng-options="(estacion.nombreEstacion) for estacion in afac.estaciones" ng-model="afac.estacionActual" ng-change="afac.cargarTablaAfluencia()">

                    </select>
                    <label class="tituloFormulario">Fecha</label> 
                    <input type="date" class="campoFormulario" step="1" ng-model="afac.fechaInicial" ng-change="afac.armarFechaInicial()"/>
<!--                    <label class="tituloFormulario">¿Consolidado?</label> 
                    <input type="checkbox" class="campoFormulario" ng-model="afac.consolidado"/>
                    <label class="tituloFormulario" ng-hide="!afac.consolidado">Fecha final</label> 
                    <input type="date" class="campoFormulario" step="1" ng-model="afac.fechaFinal" ng-hide="!afac.consolidado"/>-->
                    <label class="tituloFormulario">Hora</label> 
                    <input type="number" class="campoFormulario" ng-model="afac.horaInicial" min="0" max="23" step="1" ng-change="afac.armarRango()"/>
                    <label class="tituloFormulario">Cantidad de pasajeros {{afac.rango}}</label> 
                    <input type="number" class="campoFormulario" ng-model="afac.cantidad" min="0" step="1">
                    <div class="contenedorBoton">
                        <input type="button" value="Agregar" class="botonContinuar" ng-click="afac.agregarAfluencia()">
                    </div>
                </form>
            </div>
            <div>
                <table class="tablas">
                    <tr>
                        <td>
                            Día
                        </td>
                        <td>
                            Fecha
                        </td>
                        <td ng-repeat="celda in afac.celdasEncabezado track by $index" ng-init="index = $index">
                            {{celda.desde}}
                        </td>
                        <td>
                            Total
                        </td>
                    </tr>
                    <!--<tr ng-repeat="fila in afac.tablaAfluencias.filas">-->
                    <tr ng-repeat="fila in afac.filasTablaAfluencia track by $index">
                        <td >
                            {{fila.dia}}
                        </td>
                        <td>
                            {{fila.fecha}} 
                        </td>
                        <td ng-repeat="celda in fila.afluencias track by $index" ng-dblclick="afac.preEditarAfluencia(celda.fecha,celda.afluencia)">
                            {{celda.afluencia}}
                        </td>
                        <td>
                            
                        </td>
                    </tr>
                </table>
            </div>
        </main>
        <footer>
            <%@include file="footer.jsp" %>
        </footer>
    </body>

</html>
