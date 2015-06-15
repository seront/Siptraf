<%-- 
    Document   : ingresoEstacion
    Created on : 05/01/2015, 09:53:23 AM
    Author     : usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="estilo.jsp" %>
<%@include file="jslt.jsp" %>


<!DOCTYPE html>
<html>
    <head>
        <script src="js/jquery-1.9.1.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="js/administrarEstacion.js" type="text/javascript" rel=""></script>
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
            <p class="titulo">Administrar Estaciones</p>
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
                <div class="contenedorFormulario">
                    <legend><h2>Datos de la estacion</h2></legend>
                    <p style="font-size: 20px;">Importante: para ingresar decimales utilice puntos. Ejemplo: 10.5</p>
                    <form>                    
                        <label class="tituloFormulario">Línea </label> 
                        <select id="cmb_lineas" class="campoFormulario">
                            <option value="">Seleccione La Línea</option>
                            <c:forEach var="lin" items="${gl.listaLinea()}">
                                <option value="${lin.idLinea}">${lin.nombreLinea}</option>
                            </c:forEach>
                        </select>
                        <label class="tituloFormulario">Nombre de la estación </label> 
                        <input type="text" id="nombre_estacion" class="campoFormulario" placeholder="Nombre de la Estación" required>
                        <label class="tituloFormulario">Abreviacion de la estación</label> 
                        <input type="text" id="abrev_estacion" class="campoFormulario" placeholder="Máximo 4 Caracteres" required>
                        <label class="tituloFormulario">Ubicacion de la estación </label> 
                        <input type="number" id="id_pk_estacion" class="campoFormulario" placeholder="Punto Kilométrico de la Estación" required min="0">
                        <div id="contenedorImg" >
                            <!--<input type="button" id="agregarEstacion" value="Agregar" class="botonContinuar">-->
                            <img src="img/icon/add-icon.png" alt="Agregar" onclick='agregarEstacion($("#cmb_lineas").val(), $("#nombre_estacion").val(), $("#id_pk_estacion").val(),$("#abrev_estacion").val());' >
                        </div>
                    </form>
                </div>
                <div class="contenedorFormulario" id="msj">
                </div>
                <div class="contenedorFormulario" id="data"></div>

                <div id="tablaEstaciones" class="contenedorFormulario">
                    <p><image class="cargando" src="img/ajax-loader.gif"/></p>
                </div> 

            </main>
        </c:if>
        <footer>
            <%@include file="footer.jsp" %>
        </footer>
    </body>

</html>
