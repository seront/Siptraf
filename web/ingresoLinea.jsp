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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/estilo_lb.css" type="text/css" rel="stylesheet">
        <script src="js/administrarLinea.js" type="text/javascript"></script>
        <script src="js/sesionCerrar.js" type="text/javascript"></script>
        <title>S.I.P.T.R.A.F</title>
    </head>
    <body >
        <div id="bgVentanaModal">           
            <div id="datos">                
            </div>            
        </div>
        <header>
            <p class="titulo">Administrar Líneas</p>
        </header>
        <c:choose>
            <c:when test="${!empty sessionScope.usuario}">
                <div id="header">
                    <ul class="nav">
                        <li><a href="">${sessionScope.usuario}</a>
                            <ul>
                                <li onclick="cerrarSesion()"><a >Cerrar Sesión</a></li>
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
            <c:if test="${sessionScope.nivel=='1'}">
                <div class="contenedorFormulario">
                    <legend><h2>Datos de la línea</h2></legend>
                    <p style="font-size: 16pt;">Importante: para ingresar decimales utilice puntos. Ejemplo: 10.5</p>
                    <form>
                        <label class="tituloFormulario">Nombre de la línea</label>
                        <input type="text" id="txt_nom_lin" class="campoFormulario"  placeholder="Nombre de la línea" required>
                        <label class="tituloFormulario">Progresiva Inicial</label>
                        <input type="number" id="num_prog_ini" class="campoFormulario"  placeholder="Punto Kilométrico Inicial (m)" required>
                        <label class="tituloFormulario">Progresiva Final</label>
                        <input type="number" id="num_prog_fin" class="campoFormulario"  placeholder="Punto Kilométrico final (m)" required>
                        <label class="tituloFormulario">Trocha</label>
                        <input type="number" id="num_tro" class="campoFormulario"  placeholder="Ancho de Vía  (m)" required>
                        <label class="tituloFormulario">Doble/Única</label>
                        <select class="campoFormulario" id="tipo_via" required>
                            <option value="true">Doble</option>
                            <option value="false">Única</option>
                        </select>
                        <label class="tituloFormulario">Velocidad Máxima</label>
                        <input type="number" id="velocidad_linea" class="campoFormulario"  placeholder="Velocidad Máxima (Km/h)" required>
                        <div id="contenedorImg">
                            <!--<input class="botonContinuar" type="button" id="btn_agr_lin" name="accion" value="Agregar"/>-->
                            <img src="img/icon/add-icon.png" alt="Agregar" title="Agregar" onclick="agregarLinea();">
                        </div>
                    </form>
                </div>

                <div class="contenedorFormulario" id="msj">

                </div>

                <div class="contenedorFormulario" id="tablaLineas">            
                </div>

            </main>
        </c:if>
        <footer>
            <%@include file="footer.jsp" %>
        </footer>
    </body>

</html>
