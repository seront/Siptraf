<%-- 
    Document   : ingresoRestriccion
    Created on : 05/01/2015, 09:57:19 AM
    Author     : usuario
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="estilo.jsp" %>
<%@include file="jslt.jsp" %>

<!DOCTYPE html>
<html>
    <head>
        <script src="js/jquery-1.9.1.js"></script>
        <script type="text/javascript" src="js/administrarRestriccion.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="js/sesionCerrar.js" type="text/javascript"></script>
        <title>S.I.P.T.R.A.F</title>
        <jsp:useBean class="modelo.GestorLista" id="gl"/>
    </head>
    <body >
        <div id="bgVentanaModal" class="dialogoModal">
            <div id="msjajax"><p class="cargando"><image class="cargando" src="img/ajax-loader.gif"/></p></div>
            <div id="datos">                
            </div>
        </div>
        <header>
            <p class="titulo">Administrar Restricciones</p>
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
                <input type="button" class="btnIrAlMenu" value="Ir al Menú" onclick="location.href = 'index.jsp'">  
            </div>

            <div class="contenedorFormulario">
                <legend><h2>Datos de la restricción</h2></legend>
                <form>
                    <label class="tituloFormulario">Línea</label>
                    <select  id="cmb_lineas" class="campoFormulario">
                        <option value="">Seleccione La Línea</option>
                        <c:forEach var="lin" items="${gl.listaLinea()}">
                            <option value="${lin.idLinea}">${lin.nombreLinea}</option>
                        </c:forEach>
                    </select>
                    <label class="tituloFormulario">Progresiva Inicial</label>
                    <input type="text" id="prog_inicio" name="prog_inicio" class="campoFormulario" placeholder="Progresiva Inicial" required>
                    <label class="tituloFormulario">Progresiva Final</label>
                    <input type="text" id="prog_final" name="prog_final" class="campoFormulario" placeholder="Progresiva Final" required>
                    <label class="tituloFormulario">Velocidad Máxima Ascendente</label>
                    <input type="text" id="vel_max_ascendente" name="vel_max_ascendente" class="campoFormulario" placeholder="Velocidad Máx. Ascendente" required min="1">
                    <label class="tituloFormulario">Velocidad Máxima Descendente</label>
                    <input type="text" id="vel_max_descendente" name="vel_max_descendente" class="campoFormulario" placeholder="Velocidad Máx. Descendente" required min="1">
                    <label class="tituloFormulario">Fecha de Registro</label>
                    <input type="date" id="fecha_registro" name="fecha_registro" class="campoFormulario" placeholder="AAAA-MM-DD" required >
                    <label class="tituloFormulario">Observación</label>
                    <input type="text" id="observacion"  class="campoFormulario" placeholder="Observación" maxlength="50">
                    <c:if test="${sessionScope.nivel<='2'}">

                        <div id="contenedorImg">
                            <!--<input type="button" id="agregar" value="Agregar" class="botonContinuar" >-->
                            <img src="img/icon/add-icon.png" alt="Agregar" title="Agregar" onclick="agregarRestriccion();">
                        </div> 
                    </c:if>
                </form>
            </div>

            <div class="contenedorFormulario" id="msj">

            </div>
            <div class="contenedorFormulario" id="data">

            </div>
            <div class="marcoRestricciones contenedorFormulario" id="contRestricciones">
            </div>
        </main>

        <footer>
            <%@include file="footer.jsp" %>
        </footer>

    </body>

</html>
