<%-- 
    Document   : index
    Created on : 03/01/2015, 07:39:14 PM
    Author     : seront
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="estilo.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="js/jquery-1.9.1.js"></script>
        <script src="js/marchaTipo.js"></script>
        <script type="text/javascript" src="js/highcharts.js"></script>
        <script type="text/javascript" src="js/exporting.js"></script>
        <script type="text/javascript" src="js/graficos.js"></script>
        <script type="text/javascript" src="js/confirmarSalida.js"></script>
        <%@include file="jslt.jsp" %>
        <jsp:useBean class="modelo.GestorLista" id="gl"/>
        <script src="js/sesionCerrar.js" type="text/javascript"></script>
        <title>S.I.P.T.R.A.F</title>
    </head>
    <body>
        <div id="bgVentanaModal" class="dialogoModal">
            <div id="msjajax"></div>
            <div id="datos">                
            </div>

        </div>
        <header>
            <p class="titulo">Realizar Marcha Tipo</p>
        </header>
        <c:choose>
            <c:when test="${!empty sessionScope.usuario}">
                <div id="header">
                    <ul class="nav">
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
                <script src="js/sesion.js" type="text/javascript" rel=""></script>
            </c:otherwise>
        </c:choose>
        <main> 
            <div style="height: 50px">
                <input type="button" id="btnMenu" class="btnIrAlMenu" value="Ir al Menú" onclick="location.href = 'index.jsp'">
            </div>
            <c:if test="${sessionScope.nivel<='2'}">

                <div class="contDMT">
                    <div class="contenedorFormulario columna1" id="contMarTip">
                        <legend> <h2>Datos para la marcha tipo</h2></legend>
                        <form >
                            <label class="tituloFormulario">Nombre de la marcha tipo</label> 
                            <input class="campoFormulario" type="text" id="nombre_marcha_tipo">
                            <label class="tituloFormulario">Velocidad de la marcha tipo</label> 
                            <input class="campoFormulario" type="number" id="velocidad" onkeypress="document.getElementById('rmt').hide()" min="1">
                            
                            <label class="tituloFormulario">Material Rodante </label> 
                            <select id="cmb_materiales" class="campoFormulario">
                                <option value=""> Seleccione...</option>

                                <c:set var="materiales" value="${gl.listaMaterialRodante()}"/>
                                <c:forEach var="material" items="${materiales}">                       
                                    <option value="${material.idMaterialRodante}"> ${material.idMaterialRodante}-${material.nombreMaterialRodante}</option>                        
                                </c:forEach>
                            </select> 
                                <label class="tituloFormulario">Línea </label> 
                            <select id="cmb_lineas" class="campoFormulario" >
                                <option value=""> Seleccione...</option>
                                
                                <c:set var="lineas" value="${gl.listaLinea()}"/>
                                <c:forEach var="linea" items="${lineas}">                        
                                    <option value="${linea.idLinea}"> ${linea.idLinea}-${linea.nombreLinea}</option>                        
                                </c:forEach>
                            </select>
                            <p>
                                <img class="cargando" id="car1" src="img/ajax-loader.gif"/>
                            </p>
                            <div  id="estaciones">
                                <label class="tituloFormulario">Estación inicial </label> 
                                <select id="cmb_est_inicio" class="campoFormulario">
                                    <option value=""> Seleccione...</option>
                                </select>
                                
                                <label class="tituloFormulario">Estación final </label> 
                                <select id="cmb_est_final" class="campoFormulario">
                                    <option value=""> Seleccione...</option>   
                                </select>
                            </div>
                            <!--                            <div class="tituloFormulario" id="estacionesIntermediasMT">
                                                            
                                                        </div>-->

                            <div class="grp_rbt">
                                <label class="tituloFormulario">Incluir Sistema de Protección</label> 
                                <input class="campoFormulario" type="checkbox" id="atp" required>
                            </div>
                            <label class="tituloFormulario">Carga de Marcha (t)</label> 
                            <input class="campoFormulario" type="number" id="carga_marcha" min="0" required>
                            <div id="contenedorImg">
                                <!--<input type="button" id="continuar" class="botonContinuar" value="Continuar"/>--> 
                                <img src="img/icon/Button-Next-icon.png" alt="Continuar" title="Continuar" onclick="continuar()" >
                            </div>
                        </form>
                    </div>
                    <div class="contenedorFormulario marcoRestricciones" id="rmt">                    
                        <img id="car2" class="cargando2" src="img/cargando2.gif"/> 
                        <div class="columna2"  id="contRestricciones" style="padding-left: 0px">            

                        </div>
                    </div>

                </div>

                <div id="resultadoMarchaTipo" class="contenedorFormulario">
                    <img class="cargando2" src="img/cargando2.gif" id="car3"/>                

                    <div id="msjMT">                           

                    </div>

                </div>

                <div id="graficoMarchaTipo">
                    <img id="car3" class="cargando2" src="img/cargando2.gif">                
                </div>

                <div id="graficoMarchaTipoTiempoVelocidad">
                    <img id="car3" class="cargando2" src="img/cargando2.gif">                
                </div>             
            </c:if>               
            <footer>
                <%@include file="footer.jsp"%>
            </footer>

        </main>
    </body>
</html>
