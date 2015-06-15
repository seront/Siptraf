<%-- 
    Document   : DatosSegmento
    Created on : 05/11/2014, 01:00:12 PM
    Author     : Kelvins Insua
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="estilo.jsp" %>
<%@include file="jslt.jsp" %>


<!DOCTYPE html>
<html>
    <head>
        <script src="js/jquery-1.9.1.js"></script>
        <script type="text/javascript" src="js/administrarSegmento.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="js/sesionCerrar.js" type="text/javascript"></script>
        <title>S.I.P.T.R.A.F</title>
        <jsp:useBean class="modelo.GestorLista" id="gl"/>

    </head>
    <body >
        <div id="bgVentanaModal" class="dialogoModal">
            <div id="msjajax"></div>
            <div id="datos">

            </div>

        </div>
        <header>
            <p class="titulo">Administrar Segmentos</p>
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
                <div class="contenedorFormulario" id="formSeg">
                    <legend><h2>Datos del segmento</h2></legend>
                    <p style="font-size: 16pt;">Importante: para ingresar decimales utilice puntos. Ejemplo: 10.5</p>
                    <form>
                        <div class="columna1">
                            <label class="tituloFormulario">Línea:</label>
                            <select class="campoFormulario" id="cmb_lineas">
                                <option value="">Seleccione la Línea</option>
                                <c:forEach var="b" items="${gl.listaLinea()}">
                                    <option value="${b.idLinea}" required>${b.nombreLinea}</option>
                                </c:forEach>
                            </select>
                            <label class="tituloFormulario" >Tipo: </label>
                            <div class="grp_rbt">
                                <label class="tituloFormulario">Recta </label>
                                <input class="campoFormulario" type="radio" id="tipo_segmento" name="tipo_segmento" value="true"  required>               
                                <label class="tituloFormulario">Curva </label>
                                <input class="campoFormulario" type="radio" id="tipo_segmento" name="tipo_segmento" value="false" required>
                            </div>
                            <label class="tituloFormulario" for="radio">Radio de Curvatura (m)</label>
                            <input class="campoFormulario" type="text" id="radio" name="radio" placeholder="Radio" >
                            <label class="tituloFormulario" for="pk_inicio">Progresiva Inicial (m) </label>
                            <input class="campoFormulario" type="text" id="pk_inicio" name="pk_inicio" placeholder="Progresiva Inicial" required>
                            <label class="tituloFormulario" for="pk_final">Progresiva Final (m)</label>
                            <input class="campoFormulario" type="text" id="pk_final" name="pk_final" placeholder="Progresiva Final" required>
                        </div>
                        <div class="columna2">
                            <label class="tituloFormulario" for="gradiente">Gradiente </label>
                            <input class="campoFormulario" type="text" id="gradiente" name="gradiente" placeholder="Gradiente" required >
                            <label class="tituloFormulario" for="velocidad_max_ascendente">Velocidad Máxima Ascendente (km/h)</label>
                            <input class="campoFormulario" type="text" id="velocidad_max_ascendente" name="velocidad_max_ascendente" min="1" placeholder="Velocidad Máxima Ascendente" required>
                            <label class="tituloFormulario">Velocidad Máxima Descendente (km/h)</label>
                            <input class="campoFormulario" type="text" id="velocidad_max_descendente" placeholder="Velocidad Máxima Descendente" min="1" required>
                            <!--                        <label class="tituloFormulario">Tunel</label>
                                                    <div class="grp_rbt">
                                                        <label for="si">Si</label>
                                                        <input class="campoFormulario" type="radio" id="tunel" name="tunel" value="true" id="si">
                                                        <label for="no">No</label> 
                                                        <input class="campoFormulario" type="radio" id="tunel" name="tunel" value="false" id="no">
                                                    </div>-->
                        </div>
                        <div id="contenedorImg">
                            <!--<input class="botonContinuar" type="button" onclick="agregar()" value="Agregar" >-->
                            
                            <img src="img/icon/add-icon.png" alt="Agregar" title="Agregar" onclick="agregar()" >
                        </div>               
                    </form>
                </div>

                <div class="contenedorFormulario" id="msj"></div>
                <div class="contenedorFormulario" id="data"></div>

                <div id="tablaSegmentos">          
                </div>
            </main>
        </c:if>
        <footer>
            <%@include file="footer.jsp" %>
        </footer>
    </body>

</html>
